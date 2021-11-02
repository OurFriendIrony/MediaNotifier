package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.async.ListChildren;
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem;
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class ActivityLibrary extends AppCompatActivity {
    private Spinner spinnerView;
    private ListView listView;
    private List<MediaItem> mediaItems;
    private ProgressBar progressBar;
    private int currentItemPos;
    private Database db;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);
        getSupportActionBar().setTitle(intentKey + " Library");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseFactory().getDatabase(getBaseContext(), intentKey);
        client = new ClientFactory().getClient(intentKey);

        spinnerView = findViewById(R.id.spinner);
        listView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                currentItemPos = itemPos;
                new ListChildren(parent.getContext(), progressBar, listView, db)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItems.get(itemPos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mediaItems = db.readAllParentItems();
        if (mediaItems.size() > 0) {
            progressBar.setIndeterminate(true);
            ArrayAdapter<?> listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, mediaItems, db);
            spinnerView.setAdapter(listAdapterSummary);
        } else {
            findViewById(R.id.spinner_progress).setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(currentItemPos);
            if (menuItem.getItemId() == R.id.action_refresh) {
                new UpdateMediaItem(ActivityLibrary.this, progressBar, db, client).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItem);
                new ListChildren(ActivityLibrary.this, progressBar, listView, db).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItem.getId());
                return true;
            } else if (menuItem.getItemId() == R.id.action_remove) {
                db.delete(mediaItem.getId());
                this.recreate();
                return true;
            } else if (menuItem.getItemId() == R.id.action_lookup) {
                if (mediaItem.getExternalLink() != null) {
                    Intent intent = IntentGenerator.getWebPageIntent(mediaItem.getExternalLink());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No External Link", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else {
                return super.onOptionsItemSelected(menuItem);
            }
        }
        return false;
    }
}
