package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.*;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class TVShowDatabase {
    private static final String SELECT_TVSHOWS = "SELECT " + TT_RAWJSON + " FROM " + TABLE_TVSHOWS + " ORDER BY " + TT_TITLE + " ASC;";

    private static final String COUNT_UNWATCHED_EPISODES_UNAIRED = "SELECT COUNT(*) FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " > date('now');";
    private static final String GET_UNWATCHED_EPISODES_UNAIRED = "SELECT " + TABLE_TVSHOWS + "." + TT_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + "," + TTSE_TITLE + "," + TTSE_OVERVIEW + "," + TTSE_DATE + "," + TT_TITLE + " " +
            "FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "INNER JOIN " + TABLE_TVSHOWS + " ON " + TABLE_TVSHOWS + "." + TT_ID + " = " + TABLE_TVSHOWS_EPISODES + "." + TTSE_ID + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " > date('now') ORDER BY " + TTSE_DATE + " ASC;";

    private static final String COUNT_UNWATCHED_EPISODES_AIRED = "SELECT COUNT(*) FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " <= date('now');";
    private static final String GET_UNWATCHED_EPISODES_AIRED = "SELECT " + TABLE_TVSHOWS + "." + TT_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + "," + TTSE_TITLE + "," + TTSE_OVERVIEW + "," + TTSE_DATE + "," + TT_TITLE + " " +
            "FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "INNER JOIN " + TABLE_TVSHOWS + " ON " + TABLE_TVSHOWS + "." + TT_ID + " = " + TABLE_TVSHOWS_EPISODES + "." + TTSE_ID + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " <= date('now') ORDER BY " + TTSE_DATE + " ASC;";

    private final TVShowDatabaseDefinition databaseHelper;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void saveTVShow(int tvShowId) {
        MovieDatabaseClient client = new MovieDatabaseClient();
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        TVShow tvShow;
        try {
            tvShow = client.getTVShow(tvShowId);
        } catch (IOException e) {
            tvShow = new TVShow();
        }

        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(dbWritable, episode);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(dbWritable, season);
        }
        insertTVShow(dbWritable, tvShow);
        dbWritable.close();
    }

    private void insertTVShow(SQLiteDatabase dbWritable, TVShow tvShow) {
        String rawJson = null;
        try {
            rawJson = new ObjectMapper().writeValueAsString(tvShow);
        } catch (JsonProcessingException e) {
            rawJson = "";
        }
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TT_ID, tvShow.getId());
        tvShowRow.put(TT_TITLE, cleanTitle(tvShow.getName()));
        tvShowRow.put(TT_IMDB, tvShow.getExternalIds().getImdbId());
        tvShowRow.put(TT_DATE, dateToString(tvShow.getFirstAirDate()));
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());
        tvShowRow.put(TT_RAWJSON, rawJson);
        dbWritable.insert(TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(SQLiteDatabase dbWritable, TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, dateToString(season.getAirDate()));
        dbWritable.insert(TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(SQLiteDatabase dbWritable, TVEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, dateToString(episode.getAirDate()));
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        if (airsAfterToday(episode)) {
            episodeRow.put(TTSE_WATCHED, WATCHED_FALSE);
        } else {
            episodeRow.put(TTSE_WATCHED, WATCHED_TRUE);
        }
        dbWritable.insert(TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }

    public void deleteAllTVShows() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + ";");
        dbWritable.close();
    }

    public int countUnwatchedEpisodes() {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(COUNT_UNWATCHED_EPISODES_AIRED, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public int countUnwatchedUnairedEpisodes() {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(COUNT_UNWATCHED_EPISODES_UNAIRED, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public List<TVEpisode> getUnwatchedEpisodes() {
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(GET_UNWATCHED_EPISODES_AIRED, null);
        try {
            while (cursor.moveToNext()) {
                TVEpisode tvEpisode = new TVEpisode();
                tvEpisode.setId(Integer.parseInt(getColumnValue(cursor, TTSE_ID)));
                tvEpisode.setSeasonNumber(Integer.parseInt(getColumnValue(cursor, TTSE_SEASON_NO)));
                tvEpisode.setEpisodeNumber(Integer.parseInt(getColumnValue(cursor, TTSE_EPISODE_NO)));
                tvEpisode.setName(getColumnValue(cursor, TTSE_TITLE));
                tvEpisode.setOverview(getColumnValue(cursor, TTSE_OVERVIEW));
                tvEpisode.setAirDate(stringToDate(getColumnValue(cursor, TTSE_DATE)));
                tvEpisode.setTitle(getColumnValue(cursor, TT_TITLE));
                tvEpisodes.add(tvEpisode);
                Log.v("*****IMHERE*****", "UNWATCHED AIRED EPISODES: Title=" + tvEpisode.getTitle() + " |Id=" + tvEpisode.getId() + "| S" + tvEpisode.getSeasonNumber() + "E" + tvEpisode.getEpisodeNumber() + " | Title=" + tvEpisode.getName() + " | Date=" + tvEpisode.getAirDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return tvEpisodes;
    }

    public List<TVEpisode> getUnwatchedUnairedEpisodes() {
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(GET_UNWATCHED_EPISODES_UNAIRED, null);
        try {
            while (cursor.moveToNext()) {
                TVEpisode tvEpisode = new TVEpisode();
                tvEpisode.setId(Integer.parseInt(getColumnValue(cursor, TTSE_ID)));
                tvEpisode.setSeasonNumber(Integer.parseInt(getColumnValue(cursor, TTSE_SEASON_NO)));
                tvEpisode.setEpisodeNumber(Integer.parseInt(getColumnValue(cursor, TTSE_EPISODE_NO)));
                tvEpisode.setName(getColumnValue(cursor, TTSE_TITLE));
                tvEpisode.setOverview(getColumnValue(cursor, TTSE_OVERVIEW));
                tvEpisode.setAirDate(stringToDate(getColumnValue(cursor, TTSE_DATE)));
                tvEpisode.setTitle(getColumnValue(cursor, TT_TITLE));
                tvEpisodes.add(tvEpisode);
                Log.v("*****IMHERE*****", "UNWATCHED UNAIRED EPISODES: Title=" + tvEpisode.getTitle() + " |Id=" + tvEpisode.getId() + "| S" + tvEpisode.getSeasonNumber() + "E" + tvEpisode.getEpisodeNumber() + " | Title=" + tvEpisode.getName() + " | Date=" + tvEpisode.getAirDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return tvEpisodes;
    }

    public List<TVShow> getTVShows() {
        List<TVShow> tvShows = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_TVSHOWS, null);
        try {
            while (cursor.moveToNext()) {
                ObjectMapper mapper = new ObjectMapper();
                TVShow tvShow = new TVShow();
                try {
                    tvShow = mapper.readValue(getColumnValue(cursor, TT_RAWJSON), TVShow.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tvShows.add(tvShow);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return tvShows;
    }

    public void updateTVEpisodeWatchedStatus(TVEpisode episode, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TTSE_WATCHED, watchedStatus);
        String where = TTSE_ID + "=? and " + TTSE_SEASON_NO + "=? and " + TTSE_EPISODE_NO + "=?";
        String[] whereArgs = new String[]{String.valueOf(episode.getId()), String.valueOf(episode.getSeasonNumber()), String.valueOf(episode.getEpisodeNumber())};
        dbWriteable.update(TABLE_TVSHOWS_EPISODES, values, where, whereArgs);
        dbWriteable.close();
    }

    public boolean getEpisodeWatchedStatus(TVEpisode tvEpisode) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String sql = "SELECT " + TTSE_WATCHED + " FROM " + TABLE_TVSHOWS_EPISODES + " " +
                "WHERE " + TTSE_ID + "=" + tvEpisode.getId() + " AND " + TTSE_SEASON_NO + "=" + tvEpisode.getSeasonNumber() + " AND " + TTSE_EPISODE_NO + "=" + tvEpisode.getEpisodeNumber() + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        cursor.moveToFirst();
        boolean watchedStatus = cursor.getInt(0) == 1;
        cursor.close();
        dbReadable.close();
        return watchedStatus;
    }

    public void debugDatabaseEntry(TVEpisode tvEpisode) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TVSHOWS_EPISODES + " " +
                "WHERE " + TTSE_ID + "=" + tvEpisode.getId() + " AND " + TTSE_SEASON_NO + "=" + tvEpisode.getSeasonNumber() + " AND " + TTSE_EPISODE_NO + "=" + tvEpisode.getEpisodeNumber() + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Log.v("DATABASE RESULT", DatabaseUtils.dumpCursorToString(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
    }

    public void debugDatabaseEntry(TVSeason tvSeason) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TVSHOWS_SEASONS + " " +
                "WHERE " + TTS_ID + "=" + tvSeason.getId() + " AND " + TTS_SEASON_NO + "=" + tvSeason.getSeasonNumber() + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Log.v("DATABASE RESULT", DatabaseUtils.dumpCursorToString(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
    }

    public void debugDatabaseEntry(TVShow tvShow) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TVSHOWS + " " +
                "WHERE " + TT_ID + "=" + tvShow.getId() + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Log.v("DATABASE RESULT", DatabaseUtils.dumpCursorToString(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
    }

    private boolean airsAfterToday(TVEpisode episode) {
        return episode.getAirDate().compareTo(new Date()) >= 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

}
