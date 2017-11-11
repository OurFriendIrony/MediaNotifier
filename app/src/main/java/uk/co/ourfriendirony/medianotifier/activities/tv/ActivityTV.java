package uk.co.ourfriendirony.medianotifier.activities.tv;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.TVShowUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.autogen.tv.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tv.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tv.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryTV;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTVEpisode;

public class ActivityTV extends AppCompatActivity {

    private Spinner showList;
    private ListView episodeList;
    private List<TVShow> tvShows;
    private ProgressBar loadPageProgressBar;
    private int currentShowPosition;
    private TVShowDatabase database;
    private MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        getSupportActionBar().setTitle(R.string.title_library_tvshow);

        database = new TVShowDatabase(getApplicationContext());

        showList = (Spinner) findViewById(R.id.tv_spinner);
        episodeList = (ListView) findViewById(R.id.tv_list);
        loadPageProgressBar = (ProgressBar) findViewById(R.id.tv_progress);
        new TVShowListAsyncTask().execute();

        showList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int showPosition, long id) {
                displayEpisodes(showPosition);
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
        TVShow currentShow = tvShows.get(currentShowPosition);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new TVShowUpdateAsyncTask().execute(currentShow);
                restart();
                return true;

            case R.id.action_remove:
                database.deleteTVShow(currentShow.getId());
                restart();
                return true;

            case R.id.action_imdb:
                Intent intent = IntentGenerator.getWebPageIntent("http://www.imdb.com/title/" + currentShow.getExternalIds().getImdbId() + "/");
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayShows() {
        if (tvShows.size() > 0) {
            ListAdapterSummaryTV listAdapterSummaryTV = new ListAdapterSummaryTV(getBaseContext(), R.layout.list_item_tv_title, tvShows);
            showList.setAdapter(listAdapterSummaryTV);
            displayEpisodes(0);
        }
    }

    private void displayEpisodes(int showPosition) {
        currentShowPosition = showPosition;
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        for (TVSeason season : tvShows.get(showPosition).getSeasons()) {
            Log.d("LISTING_SEASON:", "Season: " + season.getSeasonNumber() + " Episodes: " + season.getEpisodes().size());
            tvEpisodes.addAll(season.getEpisodes());
        }
        if (tvEpisodes.size() > 0) {
            ListAdapterTVEpisode episodeListAdapter = new ListAdapterTVEpisode(getBaseContext(), R.layout.list_item_tv_episode, tvEpisodes, false);
            episodeList.setAdapter(episodeListAdapter);
            episodeList.setSelection(tvEpisodes.size());
        }
    }

    private void restart() {
        finish();
        startActivity(getIntent());
    }

    private class TVShowListAsyncTask extends AsyncTask<String, Void, Void> {
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
            tvShows = database.getAllTVShows();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            loadPageProgressBar.setVisibility(View.GONE);
            displayShows();
        }
    }
}
