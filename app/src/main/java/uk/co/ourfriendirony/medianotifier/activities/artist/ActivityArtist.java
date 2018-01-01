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
import uk.co.ourfriendirony.medianotifier._objects.artist.Artist;
import uk.co.ourfriendirony.medianotifier.clients.DiscogsDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryArtist;

public class ActivityArtist extends AppCompatActivity {

    private Spinner artistSpinner;
    private ListView artistList;
    private List<Artist> artists;
    private ProgressBar loadPageProgressBar;
    private int currentArtistPosition;
    private ArtistDatabase database;
    private DiscogsDatabaseClient client = new DiscogsDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        getSupportActionBar().setTitle(R.string.title_library_artist);

        database = new ArtistDatabase(getApplicationContext());

        artistSpinner = (Spinner) findViewById(R.id.artist_spinner);
        artistList = (ListView) findViewById(R.id.artist_list);
        loadPageProgressBar = (ProgressBar) findViewById(R.id.artist_progress);
        new ArtistListAsyncTask().execute();

        artistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int artistPosition, long id) {
                displayArtists(artistPosition);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Artist currentArtist = artists.get(currentArtistPosition);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new ArtistUpdateAsyncTask().execute(currentArtist);
                restart();
                return true;

            case R.id.action_remove:
                database.deleteArtist(currentArtist.getId());
                restart();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayArtists() {
        if (artists.size() > 0) {
            ListAdapterSummaryArtist listAdapterSummaryArtist = new ListAdapterSummaryArtist(getBaseContext(), R.layout.list_item_artist_title, artists);
            artistSpinner.setAdapter(listAdapterSummaryArtist);
            displayArtists(0);
        }
    }

    private void displayArtists(int artistPosition) {
        currentArtistPosition = artistPosition;
        ListAdapterSummaryArtist artistListAdapter = new ListAdapterSummaryArtist(getBaseContext(), R.layout.list_item_artist, Collections.singletonList(artists.get(artistPosition)));
        artistList.setAdapter(artistListAdapter);
    }

    private void restart() {
        finish();
        startActivity(getIntent());
    }

    private class ArtistListAsyncTask extends AsyncTask<String, Void, Void> {
        /* Responsible for retrieving all tv shows
         * and displaying them
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadPageProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            artists = database.getAllArtists();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            loadPageProgressBar.setVisibility(View.GONE);
            displayArtists();
        }
    }
}
