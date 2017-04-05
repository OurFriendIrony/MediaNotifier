package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowEpisodeListAdapter;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowListAdapter;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowSeasonListAdapter;

public class TVShowActivity extends AppCompatActivity {

    private ListView showList;
    private ListView seasonList;
    private ListView episodeList;
    private List<TVShow> tvShows;
    private List<TVSeason> tvSeasons;
    private List<TVEpisode> tvEpisodes;
    private ProgressBar showProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvlist);

        showList = (ListView) findViewById(R.id.find_list_tv);
        seasonList = (ListView) findViewById(R.id.find_list_seasons);
        episodeList = (ListView) findViewById(R.id.find_list_episodes);
        showProgressBar = (ProgressBar) findViewById(R.id.progress_tvlist);
        new TVShowListAsyncTask().execute();

        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int showPosition, long id) {
                displaySeasons(showPosition);

                seasonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int seasonPosition, long id) {
                        displayEpisodes(seasonPosition);
                    }
                });
            }
        });
    }

    private void displayShows() {
        if (tvShows.size() > 0) {
            TVShowListAdapter tvShowListAdapter = new TVShowListAdapter(getBaseContext(), R.layout.find_item, tvShows);
            showList.setAdapter(tvShowListAdapter);
            showList.performItemClick(null, 0, 0);
            displaySeasons(0);
        }
    }

    private void displaySeasons(int showPosition) {
        tvSeasons = tvShows.get(showPosition).getSeasons();
        if (tvSeasons.size() > 0) {
            TVShowSeasonListAdapter seasonListAdapter = new TVShowSeasonListAdapter(getBaseContext(), R.layout.find_item_season, tvSeasons);
            seasonList.setAdapter(seasonListAdapter);
            seasonList.performItemClick(null, 0, 0);
            displayEpisodes(0);
        }
    }

    private void displayEpisodes(int seasonPosition) {
        tvEpisodes = tvSeasons.get(seasonPosition).getEpisodes();
        if (tvEpisodes.size() > 0) {
            TVShowEpisodeListAdapter episodeListAdapter = new TVShowEpisodeListAdapter(getBaseContext(), R.layout.find_item_episode, tvEpisodes);
            episodeList.setAdapter(episodeListAdapter);
            episodeList.performItemClick(null, 0, 0);
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
            displaySeasons(0);
            displayEpisodes(0);
        }
    }
}
