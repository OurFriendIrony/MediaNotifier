package uk.co.ourfriendirony.medianotifier.activities.artist;

import android.os.AsyncTask;
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
import uk.co.ourfriendirony.medianotifier.async.ListChildrenAsyncTask;
import uk.co.ourfriendirony.medianotifier.async.UpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.clients.ArtistClient;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityArtist extends AppCompatActivity {
    private Spinner spinnerView;
    private ListView listView;
    private List<MediaItem> artists;
    private ProgressBar progressBar;
    private int currentItemPos;
    private Database db;
    private Client client = new ArtistClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_artist);
        super.setContentView(R.layout.activity_list);

        db = new ArtistDatabase(getBaseContext());

        spinnerView = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        new ArtistListAsyncTask().execute();

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                currentItemPos = itemPos;
                new ListChildrenAsyncTask(getBaseContext(), db, progressBar, listView).execute(artists.get(itemPos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MediaItem mediaItem = artists.get(currentItemPos);
        switch (menuItem.getItemId()) {
            case R.id.action_refresh:
                new UpdateAsyncTask(getApplicationContext(), db, client).execute(mediaItem);
                new ListChildrenAsyncTask(getBaseContext(), db, progressBar, listView).execute(mediaItem.getId());
                return true;

            case R.id.action_remove:
                db.delete(mediaItem.getId());
                this.recreate();
                return true;

            case R.id.action_lookup:
                Toast.makeText(ActivityArtist.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    // TODO: Another Async task to migrate
    private class ArtistListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            artists = db.readAllParentItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            progressBar.setVisibility(View.GONE);
            if (artists.size() > 0) {
                ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, artists, db);
                spinnerView.setAdapter(listAdapterSummary);
            }
        }
    }
}
