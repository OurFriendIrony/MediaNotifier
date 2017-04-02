package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    private TVShowDatabase database;
    private ListView showList;
    private ListView seasonList;
    private ListView episodeList;
    private List<TVShow> tvShows;
    private List<TVSeason> tvSeasons;
    private List<TVEpisode> tvEpisodes;
    private TVShowEpisodeListAdapter episodeListAdapter;
    private TVShowSeasonListAdapter seasonListAdapter;
    private TVShowListAdapter tvShowListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvlist);

        TVShowDatabaseDefinition databaseHelper = new TVShowDatabaseDefinition(getApplicationContext());
        database = new TVShowDatabase(databaseHelper);

        showList = (ListView) findViewById(R.id.find_list_tv);

        tvShows = database.getTVShows();
        if (tvShows.size() > 0) {
            tvShowListAdapter = new TVShowListAdapter(getBaseContext(), R.layout.find_item, tvShows);
            showList.setAdapter(tvShowListAdapter);
        }


        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (episodeListAdapter != null) {
                    episodeListAdapter.clear();
                    episodeListAdapter.notifyDataSetChanged();
                }

                seasonList = (ListView) findViewById(R.id.find_list_seasons);

                tvSeasons = tvShows.get(position).getSeasons();
                if (tvSeasons.size() > 0) {
                    seasonListAdapter = new TVShowSeasonListAdapter(getBaseContext(), R.layout.find_item_seasons, tvSeasons);
                    seasonList.setAdapter(seasonListAdapter);
                }

                seasonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        episodeList = (ListView) findViewById(R.id.find_list_episodes);
                        tvEpisodes = tvSeasons.get(position).getEpisodes();
                        if (tvEpisodes.size() > 0) {
                            episodeListAdapter = new TVShowEpisodeListAdapter(getBaseContext(), R.layout.find_item_episodes, tvEpisodes);
                            episodeList.setAdapter(episodeListAdapter);
                        }
                    }
                });
            }
        });
    }

}

