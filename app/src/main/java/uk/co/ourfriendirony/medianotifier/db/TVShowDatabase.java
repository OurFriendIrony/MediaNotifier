package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShowExternalIds;
import uk.co.ourfriendirony.medianotifier.general.StringHandler;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffset;
import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.*;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class TVShowDatabase {
    private static final String SELECT_TVSHOWS = "SELECT * FROM " + TABLE_TVSHOWS + " ORDER BY " + TT_TITLE + " ASC;";
    private static final String SELECT_TVSEASONS = "SELECT * FROM " + TABLE_TVSHOWS_SEASONS + " WHERE " + TTS_ID + "=? ORDER BY " + TTS_SEASON_NO + " ASC;";
    private static final String SELECT_TVEPISODES = "SELECT * FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_ID + "=? AND " + TTSE_SEASON_NO + "=? ORDER BY " + TTSE_EPISODE_NO + " ASC;";

    private static final String GET_TVSHOW_TITLE_BY_ID = "SELECT " + TT_TITLE + " FROM " + TABLE_TVSHOWS + " WHERE " + TT_ID + "=?;";

    private static final String GET_TVEPISODE_WATCHED_STATUS = "SELECT " + TTSE_WATCHED + " FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_ID + "=? AND " + TTSE_SEASON_NO + "=? AND " + TTSE_EPISODE_NO + "=?;";

    private static final String COUNT_UNWATCHED_EPISODES_UNRELEASED = "SELECT COUNT(*) FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " > @OFFSET@;";
    private static final String GET_UNWATCHED_EPISODES_UNRELEASED = "SELECT " + TABLE_TVSHOWS + "." + TT_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + "," + TTSE_TITLE + "," + TTSE_OVERVIEW + "," + TTSE_DATE + "," + TT_TITLE + " " +
            "FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "INNER JOIN " + TABLE_TVSHOWS + " ON " + TABLE_TVSHOWS + "." + TT_ID + " = " + TABLE_TVSHOWS_EPISODES + "." + TTSE_ID + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " > @OFFSET@ ORDER BY " + TTSE_DATE + " ASC;";

    private static final String COUNT_UNWATCHED_EPISODES_RELEASED = "SELECT COUNT(*) FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_EPISODES_RELEASED = "SELECT " + TABLE_TVSHOWS + "." + TT_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + "," + TTSE_TITLE + "," + TTSE_OVERVIEW + "," + TTSE_DATE + "," + TT_TITLE + " " +
            "FROM " + TABLE_TVSHOWS_EPISODES + " " +
            "INNER JOIN " + TABLE_TVSHOWS + " ON " + TABLE_TVSHOWS + "." + TT_ID + " = " + TABLE_TVSHOWS_EPISODES + "." + TTSE_ID + " " +
            "WHERE " + TTSE_WATCHED + "=" + WATCHED_FALSE + " AND " + TTSE_DATE + " <= @OFFSET@ ORDER BY " + TTSE_DATE + " ASC;";

    private final TVShowDatabaseDefinition databaseHelper;
    private final Context context;

    public TVShowDatabase(Context context) {
        this.databaseHelper = new TVShowDatabaseDefinition(context);
        this.context = context;
    }

    public void addTVShow(TVShow tvShow) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(dbWritable, episode, true);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(dbWritable, season);
        }
        insertTVShow(dbWritable, tvShow);
        dbWritable.close();
    }

    public void updateTVShow(TVShow tvShow) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(dbWritable, episode, false);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(dbWritable, season);
        }
        insertTVShow(dbWritable, tvShow);
        dbWritable.close();
    }

    private void insertTVShow(SQLiteDatabase dbWritable, TVShow tvShow) {
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TT_ID, tvShow.getId());
        tvShowRow.put(TT_TITLE, cleanTitle(tvShow.getName()));
        tvShowRow.put(TT_IMDB, tvShow.getExternalIds().getImdbId());
        tvShowRow.put(TT_DATE, dateToString(tvShow.getFirstAirDate()));
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());

        dbWritable.replace(TABLE_TVSHOWS, null, tvShowRow);
        Log.d("INSERT_TV_SHOW:", tvShow.getName());
    }

    private void insertTVShowSeason(SQLiteDatabase dbWritable, TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, dateToString(season.getAirDate()));

        dbWritable.replace(TABLE_TVSHOWS_SEASONS, null, seasonRow);
        Log.d("INSERT_TV_SEASON", "S" + season.getSeasonNumber());
    }

    private void insertTVShowEpisode(SQLiteDatabase dbWritable, TVEpisode episode, boolean newShow) {
        String currentWatchedStatus = getEpisodeWatchedStatus(dbWritable, episode);
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, dateToString(episode.getAirDate()));
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        if (newShow && alreadyReleased(episode)) {
            episodeRow.put(TTSE_WATCHED, WATCHED_TRUE);
        } else {
            episodeRow.put(TTSE_WATCHED, currentWatchedStatus);
        }

        dbWritable.replace(TABLE_TVSHOWS_EPISODES, null, episodeRow);
        Log.d("INSERT_TV_EPISODE", "S" + episode.getSeasonNumberAsString() + " E" + episode.getEpisodeNumberAsString());
    }

    public String getEpisodeWatchedStatus(SQLiteDatabase dbReadable, TVEpisode episode) {
        String[] args = new String[]{episode.getIdAsString(), episode.getSeasonNumberAsString(), episode.getEpisodeNumberAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TTSE_WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    public boolean getEpisodeWatchedStatusAsBoolean(TVEpisode episode) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{episode.getIdAsString(), episode.getSeasonNumberAsString(), episode.getEpisodeNumberAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TTSE_WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return WATCHED_TRUE.equals(watchedStatus);
    }

    public void deleteAllTVShows() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + ";");
        dbWritable.close();
    }

    public void deleteTVShow(Integer showId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS + " WHERE " + TT_ID + "=" + showId + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + " WHERE " + TTS_ID + "=" + showId + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_ID + "=" + showId + ";");
        dbWritable.close();
    }

    public String getTitleById(int showId) {
        String title = "";
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(GET_TVSHOW_TITLE_BY_ID, new String[]{String.valueOf(showId)});
        try {
            while (cursor.moveToNext()) {
                title = getColumnValue(cursor, TT_TITLE);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return title;
    }

    public int countUnwatchedReleasedEpisodes() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(COUNT_UNWATCHED_EPISODES_RELEASED, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public int countUnwatchedUnreleasedEpisodes() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(COUNT_UNWATCHED_EPISODES_UNRELEASED, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public List<TVEpisode> getUnwatchedReleasedEpisodes() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(GET_UNWATCHED_EPISODES_RELEASED, "@OFFSET@", offset);
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                TVEpisode tvEpisode = buildTVEpisode(cursor);
                tvEpisodes.add(tvEpisode);
                Log.d("UNWATCHED AIRED", "Id=" + tvEpisode.getId() + "| S" + tvEpisode.getSeasonNumber() + "E" + tvEpisode.getEpisodeNumber() + " | Title=" + tvEpisode.getName() + " | Date=" + tvEpisode.getAirDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return tvEpisodes;
    }

    public List<TVEpisode> getUnwatchedUnreleasedEpisodes() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(GET_UNWATCHED_EPISODES_UNRELEASED, "@OFFSET@", offset);
        List<TVEpisode> tvEpisodes = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                TVEpisode tvEpisode = buildTVEpisode(cursor);
                tvEpisodes.add(tvEpisode);
                Log.d("UNWATCHED UNAIRED", "Id=" + tvEpisode.getId() + "| S" + tvEpisode.getSeasonNumber() + "E" + tvEpisode.getEpisodeNumber() + " | Title=" + tvEpisode.getName() + " | Date=" + tvEpisode.getAirDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return tvEpisodes;
    }

    @NonNull
    private TVEpisode buildTVEpisode(Cursor cursor) {
        TVEpisode tvEpisode = new TVEpisode();
        tvEpisode.setId(Integer.parseInt(getColumnValue(cursor, TTSE_ID)));
        tvEpisode.setSeasonNumber(Integer.parseInt(getColumnValue(cursor, TTSE_SEASON_NO)));
        tvEpisode.setEpisodeNumber(Integer.parseInt(getColumnValue(cursor, TTSE_EPISODE_NO)));
        tvEpisode.setName(getColumnValue(cursor, TTSE_TITLE));
        tvEpisode.setOverview(getColumnValue(cursor, TTSE_OVERVIEW));
        tvEpisode.setAirDate(stringToDate(getColumnValue(cursor, TTSE_DATE)));

        Log.d("BUILD_TV_EPISODE", tvEpisode.getId() + " " + tvEpisode.getName());
        return tvEpisode;
    }

    @NonNull
    private TVSeason buildTVSeason(Cursor tvSeasonCursor) {
        TVSeason tvSeason = new TVSeason();
        tvSeason.setId(Integer.parseInt(getColumnValue(tvSeasonCursor, TTS_ID)));
        tvSeason.setSeasonNumber(Integer.parseInt(getColumnValue(tvSeasonCursor, TTS_SEASON_NO)));

        List<TVEpisode> tvEpisodes = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor tvEpisodeCursor = dbReadable.rawQuery(SELECT_TVEPISODES, new String[]{String.valueOf(tvSeason.getId()), String.valueOf(tvSeason.getSeasonNumber())});
        try {
            while (tvEpisodeCursor.moveToNext()) {
                tvEpisodes.add(buildTVEpisode(tvEpisodeCursor));
            }
        } finally {
            tvEpisodeCursor.close();
        }
        dbReadable.close();
        tvSeason.setEpisodes(tvEpisodes);

        Log.d("BUILD_TV_SEASON", tvSeason.getId() + " " + tvSeason.getSeasonNumber());
        return tvSeason;
    }

    @NonNull
    private TVShow buildTVShow(Cursor tvShowCursor) {
        TVShow tvShow = new TVShow();
        TVShowExternalIds externalIds = new TVShowExternalIds();

        tvShow.setId(Integer.parseInt(getColumnValue(tvShowCursor, TT_ID)));
        tvShow.setName(getColumnValue(tvShowCursor, TT_TITLE));
        tvShow.setFirstAirDate(stringToDate(getColumnValue(tvShowCursor, TT_DATE)));
        tvShow.setOverview(getColumnValue(tvShowCursor, TT_OVERVIEW));

        externalIds.setImdbId(getColumnValue(tvShowCursor, TT_IMDB));
        tvShow.setExternalIds(externalIds);

        List<TVSeason> tvSeasons = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor tvSeasonCursor = dbReadable.rawQuery(SELECT_TVSEASONS, new String[]{String.valueOf(tvShow.getId())});
        try {
            while (tvSeasonCursor.moveToNext()) {
                tvSeasons.add(buildTVSeason(tvSeasonCursor));
            }
        } finally {
            tvSeasonCursor.close();
        }
        dbReadable.close();
        tvShow.setSeasons(tvSeasons);

        Log.d("BUILD_TV_SHOW", tvShow.getId() + " " + tvShow.getName());
        return tvShow;
    }

    public List<TVShow> getAllTVShows() {
        List<TVShow> tvShows = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor tvShowCursor = dbReadable.rawQuery(SELECT_TVSHOWS, null);
        try {
            while (tvShowCursor.moveToNext()) {
                tvShows.add(buildTVShow(tvShowCursor));
            }
        } finally {
            tvShowCursor.close();
        }
        dbReadable.close();
        return tvShows;
    }

    public void updateTVEpisodeWatchedStatus(TVEpisode episode, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TTSE_WATCHED, watchedStatus);
        String where = TTSE_ID + "=? and " + TTSE_SEASON_NO + "=? and " + TTSE_EPISODE_NO + "=?";
        String[] whereArgs = new String[]{episode.getIdAsString(), episode.getSeasonNumberAsString(), episode.getEpisodeNumberAsString()};
        dbWriteable.update(TABLE_TVSHOWS_EPISODES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean alreadyReleased(TVEpisode episode) {
        return episode.getAirDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    public void debug() {
        int batesMotelId = 46786;
        int seasonId = 5;

        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + " WHERE " + TTS_ID + "=" + batesMotelId + " AND " + TTS_SEASON_NO + "=" + seasonId + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_ID + "=" + batesMotelId + " AND " + TTSE_SEASON_NO + "=" + seasonId + ";");

        dbWritable.close();
    }

    public void debugDatabaseEntry() {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Log.d("DATABASE_RESULT", "_START_");
        String[] sqlArgs = new String[]{"46786"};

        Log.d("DATABASE_RESULT", "****EPISODE****");
        String sql = "SELECT * FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_ID + "=?;";
        Cursor cursor = dbReadable.rawQuery(sql, sqlArgs);
        try {
            while (cursor.moveToNext()) {
                Log.d("DATABASE_EPISODE", getColumnValue(cursor, TTSE_ID) + " " + getColumnValue(cursor, TTSE_SEASON_NO) + " " + getColumnValue(cursor, TTSE_EPISODE_NO));
            }
        } finally {
            cursor.close();
        }

        Log.d("DATABASE_RESULT", "****SEASON****");
        sql = "SELECT * FROM " + TABLE_TVSHOWS_SEASONS + " WHERE " + TTS_ID + "=?;";
        cursor = dbReadable.rawQuery(sql, sqlArgs);
        try {
            while (cursor.moveToNext()) {
                Log.d("DATABASE_RESULT_SEASON", getColumnValue(cursor, TTS_ID) + " " + getColumnValue(cursor, TTS_SEASON_NO));
            }
        } finally {
            cursor.close();
        }

        Log.d("DATABASE_RESULT", "****SHOW****");
        sql = "SELECT * FROM " + TABLE_TVSHOWS + " WHERE " + TT_ID + "=?;";
        cursor = dbReadable.rawQuery(sql, sqlArgs);
        try {
            while (cursor.moveToNext()) {
                Log.d("DATABASE_RESULT_SHOW", getColumnValue(cursor, TT_ID));
            }
        } finally {
            cursor.close();
        }
        Log.d("DATABASE_RESULT", "_END_");
        dbReadable.close();
    }
}
