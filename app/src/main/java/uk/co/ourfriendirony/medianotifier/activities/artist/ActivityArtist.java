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

import java.util.Collections;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.ArtistUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityArtist extends AppCompatActivity {
    private Spinner spinner;
    private ListView listView;
    private List<MediaItem> artists;
    private ProgressBar progressBar;
    private int currentArtistPosition;
    private ArtistDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_artist);
        super.setContentView(R.layout.activity_artist);

        db = new ArtistDatabase(getApplicationContext());

        spinner = (Spinner) findViewById(R.id.artist_spinner);
        listView = (ListView) findViewById(R.id.artist_list);
        progressBar = (ProgressBar) findViewById(R.id.artist_progress);
        new ArtistListAsyncTask().execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                displayReleases(itemPos);
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
        MediaItem mediaItem = artists.get(currentArtistPosition);
        switch (menuItem.getItemId()) {
            case R.id.action_refresh:
                new ArtistUpdateAsyncTask().execute(mediaItem);
                this.recreate();
                return true;

            case R.id.action_remove:
                db.delete(mediaItem.getId());
                this.recreate();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void display() {
        if (artists.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, artists, db);
            spinner.setAdapter(listAdapterSummary);
            displayReleases(0);
        }
    }

    private void displayReleases(int itemPos) {
//        currentArtistPosition = itemPos;
//        ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_toggle, Collections.singletonList(mediaItems.get(itemPos)), db);
//        listView.setAdapter(listAdapterSummary);
        currentArtistPosition = itemPos;
        List<MediaItem> mediaItems = artists.get(currentArtistPosition).getChildren();
        if (mediaItems.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_toggle, mediaItems, db);
            listView.setAdapter(listAdapterSummary);
            listView.setSelection(mediaItems.size());
        } else {
            listView.setAdapter(null);
        }
    }

    private class ArtistListAsyncTask extends AsyncTask<String, Void, Void> {
        /* Responsible for retrieving all tv shows
         * and displaying them
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            artists = db.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            progressBar.setVisibility(View.GONE);
            display();
        }
    }
}
