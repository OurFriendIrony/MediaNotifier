package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private ProgressBar showProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        getSupportActionBar().setTitle(R.string.title_library_tvshow);

        showList = (Spinner) findViewById(R.id.find_list_tv);
        episodeList = (ListView) findViewById(R.id.find_list_episodes);
        showProgressBar = (ProgressBar) findViewById(R.id.progress_tvlist);
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
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh Pressed", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_remove:
                Toast.makeText(this, "Remove Pressed", Toast.LENGTH_SHORT).show();
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
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        for (TVSeason season : tvShows.get(showPosition).getSeasons()) {
            tvEpisodes.addAll(season.getEpisodes());
        }
        if (tvEpisodes.size() > 0) {
            ListAdapterTVEpisode episodeListAdapter = new ListAdapterTVEpisode(getBaseContext(), R.layout.list_item_tv_episode, tvEpisodes, false);
            episodeList.setAdapter(episodeListAdapter);
            episodeList.setSelection(tvEpisodes.size());
        }
    }

    private class TVShowListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));
            tvShows = database.getTVShows();
            return null;
        }

        protected void onPostExecute(Void x) {
            showProgressBar.setVisibility(View.GONE);
            displayShows();
        }
    }
}
