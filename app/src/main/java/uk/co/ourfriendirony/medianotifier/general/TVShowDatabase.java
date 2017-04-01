package uk.co.ourfriendirony.medianotifier.general;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDLookupTVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDSeason;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

public class TVShowDatabase {

    private final SQLiteDatabase dbWritable;
    private final SQLiteDatabase dbReadable;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        dbWritable = databaseHelper.getWritableDatabase();
        dbReadable = databaseHelper.getReadableDatabase();
    }

    public String selectTVShow() {
        ContentValues tvShowRow = new ContentValues();

        Cursor cursor = dbReadable.rawQuery("SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(TVShowDatabaseDefinition.TT_OVERVIEW));
     
    }

    public void saveTVShow(int tvShowId) {
        MovieDatabaseClient client = new MovieDatabaseClient();
        MDLookupTVShow tvShow;
        try {
            tvShow = client.getTVShow(tvShowId);
        } catch (IOException e) {
            tvShow = new MDLookupTVShow();
        }

        for (MDSeason season : tvShow.getSeasons()) {
            for (MDEpisode episode : season.getEpisodes()) {
                insertTVShowEpisode(episode);
            }
            insertTVShowSeason(season);
        }
        insertTVShow(tvShow);
    }

    private void insertTVShow(MDLookupTVShow tvShow) {
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TVShowDatabaseDefinition.TT_ID, tvShow.getId());
        tvShowRow.put(TVShowDatabaseDefinition.TT_TITLE, tvShow.getName());
        tvShowRow.put(TVShowDatabaseDefinition.TT_IMDB, "");
        tvShowRow.put(TVShowDatabaseDefinition.TT_DATE, tvShow.getFirstAirDate());
        tvShowRow.put(TVShowDatabaseDefinition.TT_OVERVIEW, tvShow.getOverview());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(MDSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TVShowDatabaseDefinition.TTS_ID, season.getId());
        seasonRow.put(TVShowDatabaseDefinition.TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TVShowDatabaseDefinition.TTS_DATE, season.getAirDate());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(MDEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TVShowDatabaseDefinition.TTSE_ID, episode.getId());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_TITLE, episode.getName());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_DATE, episode.getAirDate());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }
}
