package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTV;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTVEpisode;

public class ActivityTV extends AppCompatActivity {

    private Spinner showList;
    private ListView episodeList;
    private List<TVShow> tvShows;
    private ProgressBar loadPageProgressBar;
    private int currentShowPosition;
    private TVShowDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        getSupportActionBar().setTitle(R.string.title_library_tvshow);

        database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));

        showList = (Spinner) findViewById(R.id.find_list_tv);
        episodeList = (ListView) findViewById(R.id.find_list_episodes);
        loadPageProgressBar = (ProgressBar) findViewById(R.id.progress_tvlist);
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
        getMenuInflater().inflate(R.menu.library_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TVShow currentShow = tvShows.get(currentShowPosition);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new TVShowUpdateAsyncTask().execute(String.valueOf(currentShow.getId()), currentShow.getName());
                return true;

            case R.id.action_remove:
                database.deleteTVShow(currentShow.getId());
                restart();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayShows() {
        if (tvShows.size() > 0) {
            ListAdapterTV listAdapterTV = new ListAdapterTV(getBaseContext(), R.layout.list_item_tv_title, tvShows);
            showList.setAdapter(listAdapterTV);
            displayEpisodes(0);
        }
    }

    private void displayEpisodes(int showPosition) {
        currentShowPosition = showPosition;
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        for (TVSeason season : tvShows.get(showPosition).getSeasons()) {
            Log.w("LISTING SEASON:", "Season: " + season.getSeasonNumber() + " Episodes: " + season.getEpisodes().size());
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
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadPageProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));
            tvShows = database.getAllTVShows();
            return null;
        }

        protected void onPostExecute(Void x) {
            loadPageProgressBar.setVisibility(View.GONE);
            displayShows();
        }
    }


    private class TVShowUpdateAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String tvShowId = params[0];
            String tvShowTitle = params[1];
            database.saveTVShow(Integer.parseInt(tvShowId));
            return tvShowTitle;
        }

        protected void onPostExecute(String tvShowTitle) {
//            findProgressBar.setVisibility(View.GONE);
            String toastMsg = "'" + tvShowTitle + "' " + getResources().getString(R.string.toast_db_updated);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
