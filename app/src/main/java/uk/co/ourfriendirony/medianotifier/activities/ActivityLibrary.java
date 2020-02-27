package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.async.ListChildren;
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary;
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

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);

        setTheme(PropertyHelper.getTheme(getBaseContext()));
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle(intentKey + " Library");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseFactory().getDatabase(getBaseContext(), intentKey);
        client = new ClientFactory().getClient(intentKey);

        spinnerView = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                currentItemPos = itemPos;
                new ListChildren(getBaseContext(), progressBar, listView, db).execute(mediaItems.get(itemPos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        loadPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MediaItem mediaItem = mediaItems.get(currentItemPos);
        switch (menuItem.getItemId()) {
            case R.id.action_refresh:
                new UpdateMediaItem(getApplicationContext(), progressBar, db, client).execute(mediaItem);
                new ListChildren(getBaseContext(), progressBar, listView, db).execute(mediaItem.getId());
                return true;

            case R.id.action_remove:
                db.delete(mediaItem.getId());
                this.recreate();
                return true;

            case R.id.action_lookup:
                if (mediaItem.getExternalLink() != null) {
                    Intent intent = IntentGenerator.getWebPageIntent(mediaItem.getExternalLink());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No External Link", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void loadPage() {
        progressBar.setIndeterminate(true);
        mediaItems = db.readAllParentItems();
        if (mediaItems.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, mediaItems, db);
            spinnerView.setAdapter(listAdapterSummary);
        }
        progressBar.setIndeterminate(true);
    }
}
