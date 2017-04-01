package uk.co.ourfriendirony.medianotifier.general;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDLookupTVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDSeason;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

import static uk.co.ourfriendirony.medianotifier.general.TVShowDatabaseDefinition.*;

public class TVShowDatabase {

    private final SQLiteDatabase dbWritable;
    private final SQLiteDatabase dbReadable;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        dbWritable = databaseHelper.getWritableDatabase();
        dbReadable = databaseHelper.getReadableDatabase();
    }

    public String selectTVShow() {
        String sql = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + ";";
        StringBuilder result = new StringBuilder();
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                result.append(getColumnValue(cursor, TT_ID)).append(" | ")
                        .append(getColumnValue(cursor, TT_DATE)).append(" | ")
                        .append(getColumnValue(cursor, TT_TITLE)).append(" | ")
                        .append(getColumnValue(cursor, TT_OVERVIEW))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }
        return result.toString();

    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
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
        tvShowRow.put(TT_ID, tvShow.getId());
        tvShowRow.put(TT_TITLE, tvShow.getName());
        tvShowRow.put(TT_IMDB, "");
        tvShowRow.put(TT_DATE, tvShow.getFirstAirDate());
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());
        dbWritable.insert(TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(MDSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, season.getAirDate());
        dbWritable.insert(TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(MDEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, episode.getAirDate());
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }
}
