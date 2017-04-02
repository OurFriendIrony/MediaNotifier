package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

public class TVShowDatabase {

    private final SQLiteDatabase dbWritable;
    private final SQLiteDatabase dbReadable;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        dbWritable = databaseHelper.getWritableDatabase();
        dbReadable = databaseHelper.getReadableDatabase();
    }

    public void saveTVShow(int tvShowId) {
        MovieDatabaseClient client = new MovieDatabaseClient();
        TVShow tvShow;
        try {
            tvShow = client.getTVShow(tvShowId);
        } catch (IOException e) {
            tvShow = new TVShow();
        }

        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(episode);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(season);
        }
        insertTVShow(tvShow);
    }

    private void insertTVShow(TVShow tvShow) {
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TVShowDatabaseDefinition.TT_ID, tvShow.getId());
        tvShowRow.put(TVShowDatabaseDefinition.TT_TITLE, tvShow.getName());
        tvShowRow.put(TVShowDatabaseDefinition.TT_IMDB, tvShow.getExternalIds().getImdbId());
        tvShowRow.put(TVShowDatabaseDefinition.TT_DATE, tvShow.getFirstAirDate());
        tvShowRow.put(TVShowDatabaseDefinition.TT_OVERVIEW, tvShow.getOverview());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TVShowDatabaseDefinition.TTS_ID, season.getId());
        seasonRow.put(TVShowDatabaseDefinition.TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TVShowDatabaseDefinition.TTS_DATE, season.getAirDate());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(TVEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TVShowDatabaseDefinition.TTSE_ID, episode.getId());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_TITLE, episode.getName());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_DATE, episode.getAirDate());
        episodeRow.put(TVShowDatabaseDefinition.TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }

    public String selectTVShow() {
        StringBuilder result = new StringBuilder();

        String sql = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                result.append(getColumnValue(cursor, TVShowDatabaseDefinition.TT_ID))
                        .append(" | ").append(getColumnValue(cursor, TVShowDatabaseDefinition.TT_DATE))
                        .append(" | ").append(getColumnValue(cursor, TVShowDatabaseDefinition.TT_TITLE))
                        .append(" | ").append(getColumnValue(cursor, TVShowDatabaseDefinition.TT_IMDB))
                        .append(" | ").append(getColumnValue(cursor, TVShowDatabaseDefinition.TT_OVERVIEW))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }

        String sqlSeasons = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_SEASONS + ";";
        Cursor cursorSeasons = dbReadable.rawQuery(sqlSeasons, null);
        try {
            while (cursorSeasons.moveToNext()) {
                result.append(">>> ").append(getColumnValue(cursorSeasons, TVShowDatabaseDefinition.TTS_ID))
                        .append(" | ").append(getColumnValue(cursorSeasons, TVShowDatabaseDefinition.TTS_SEASON_NO))
                        .append(" | ").append(getColumnValue(cursorSeasons, TVShowDatabaseDefinition.TTS_DATE))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }

        String sqlEpisodes = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + ";";
        Cursor cursorEpisodes = dbReadable.rawQuery(sqlEpisodes, null);
        try {
            while (cursorEpisodes.moveToNext()) {
                result.append(">>>>> ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_ID))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_SEASON_NO))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_EPISODE_NO))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_DATE))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_TITLE))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TVShowDatabaseDefinition.TTSE_OVERVIEW))
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

    public void deleteAllTVShows() {
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_SEASONS + ";");
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + ";");
    }

}
