package uk.co.ourfriendirony.medianotifier.db.tv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.general.Helper;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVEpisode;
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVShow;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.dateToString;

public class TVShowDatabase implements Database {
    private static final String SELECT_TVSHOWS = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " ORDER BY " + TVShowDatabaseDefinition.TITLE + " ASC;";
    private static final String SELECT_TVEPISODES = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=? ORDER BY " + TVShowDatabaseDefinition.SUBTITLE + " ASC;";

    private static final String GET_TVEPISODE_WATCHED_STATUS = "SELECT " + TVShowDatabaseDefinition.WATCHED + " FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=? AND " + TVShowDatabaseDefinition.SUBTITLE + "=?;";

    private static final String COUNT_UNWATCHED_EPISODES_RELEASED = "SELECT COUNT(*) FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " AND " + TVShowDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";

    private static final String GET_UNWATCHED_EPISODES_RELEASED = "SELECT * " +
            "FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " AND " + TVShowDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + TVShowDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_EPISODES_TOTAL = "SELECT * " +
            "FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " ORDER BY " + TVShowDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final TVShowDatabaseDefinition databaseHelper;
    private final Context context;

    public TVShowDatabase(Context context) {
        this.databaseHelper = new TVShowDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(MediaItem show) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (MediaItem episode : show.getChildren()) {
            insertEpisode(dbWritable, episode, true);
        }
        insert(dbWritable, show);
        dbWritable.close();
    }

    @Override
    public void update(MediaItem show) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (MediaItem episode : show.getChildren()) {
            insertEpisode(dbWritable, episode, false);
        }
        insert(dbWritable, show);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, MediaItem mediaItem) {
        ContentValues dbRow = new ContentValues();
        dbRow.put(TVShowDatabaseDefinition.ID, mediaItem.getId());
        dbRow.put(TVShowDatabaseDefinition.SUBID, mediaItem.getSubId());
        dbRow.put(TVShowDatabaseDefinition.TITLE, cleanTitle(mediaItem.getTitle()));
        dbRow.put(TVShowDatabaseDefinition.SUBTITLE, mediaItem.getSubtitle());
        dbRow.put(TVShowDatabaseDefinition.EXTERNAL_URL, mediaItem.getExternalLink());
        dbRow.put(TVShowDatabaseDefinition.RELEASE_DATE, dateToString(mediaItem.getReleaseDate()));
        dbRow.put(TVShowDatabaseDefinition.DESCRIPTION, mediaItem.getDescription());
        Log.d("[DB INSERT TV]", dbRow.toString());
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_TVSHOWS, null, dbRow);
    }

    private void insertEpisode(SQLiteDatabase dbWritable, MediaItem episode, boolean isNewTVShow) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, episode);
        ContentValues dbRow = new ContentValues();
        dbRow.put(TVShowDatabaseDefinition.ID, episode.getId());
        dbRow.put(TVShowDatabaseDefinition.SUBID, episode.getSubId());
        dbRow.put(TVShowDatabaseDefinition.TITLE, cleanTitle(episode.getTitle()));
        dbRow.put(TVShowDatabaseDefinition.SUBTITLE, episode.getSubtitle());
        dbRow.put(TVShowDatabaseDefinition.RELEASE_DATE, dateToString(episode.getReleaseDate()));
        dbRow.put(TVShowDatabaseDefinition.DESCRIPTION, episode.getDescription());
        if (markWatchedIfReleased(isNewTVShow, episode)) {
            dbRow.put(TVShowDatabaseDefinition.WATCHED, TVShowDatabaseDefinition.WATCHED_TRUE);
        } else {
            dbRow.put(TVShowDatabaseDefinition.WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT TV EPISODE]", dbRow.toString());
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_EPISODES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId(), mediaItem.getSubtitle()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = TVShowDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TVShowDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem mediaItem) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{mediaItem.getId(), mediaItem.getSubtitle()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = TVShowDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TVShowDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return TVShowDatabaseDefinition.WATCHED_TRUE.equals(watchedStatus);
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + ";");
        dbWritable.close();
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " WHERE " + TVShowDatabaseDefinition.ID + "=" + id + ";");
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=" + id + ";");
        dbWritable.close();
    }

    @NonNull
    private MediaItem buildSubItemFromDB(Cursor cursor) {
        return new TVEpisode(cursor);
    }

    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        // TODO: When building an mediaItem, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.

        String id = getColumnValue(cursor, TVShowDatabaseDefinition.ID);
        List<MediaItem> episodes = new ArrayList<>();

        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor subCursor = dbReadable.rawQuery(SELECT_TVEPISODES, new String[]{id});
        try {
            while (subCursor.moveToNext()) {
                episodes.add(buildSubItemFromDB(subCursor));
            }
        } finally {
            subCursor.close();
        }
        dbReadable.close();
        return new TVShow(cursor, episodes);
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatched(COUNT_UNWATCHED_EPISODES_RELEASED);
    }

    private int countUnwatched(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetTV(context) + " days')";
        String query = Helper.replaceTokens(countQuery, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    @Override
    public List<MediaItem> getUnwatchedReleased() {
        return getUnwatched(GET_UNWATCHED_EPISODES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_EPISODES_TOTAL, "UNWATCHED TOTAL");
    }

    @Override
    public List<MediaItem> getUnwatched(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetTV(context) + " days')";
        String query = Helper.replaceTokens(getQuery, "@OFFSET@", offset);
        List<MediaItem> mediaItems = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = buildSubItemFromDB(cursor);
                mediaItems.add(mediaItem);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return mediaItems;
    }

    @Override
    public List<MediaItem> getAll() {
        List<MediaItem> shows = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_TVSHOWS, null);
        try {
            while (cursor.moveToNext()) {
                shows.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return shows;
    }

    @Override
    public List<MediaItem> getAllSubitems(String id) {
        List<MediaItem> mediaItems = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = {id};
        Cursor tvShowCursor = dbReadable.rawQuery(SELECT_TVEPISODES, args);
        try {
            while (tvShowCursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(tvShowCursor));
            }
        } finally {
            tvShowCursor.close();
        }
        dbReadable.close();
        return mediaItems;
    }

    @Override
    public void updateWatchedStatus(MediaItem mediaItem, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TVShowDatabaseDefinition.WATCHED, watchedStatus);
        String where = TVShowDatabaseDefinition.ID + "=? and " + TVShowDatabaseDefinition.SUBID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId(), mediaItem.getSubtitle()};
        dbWriteable.update(TVShowDatabaseDefinition.TABLE_EPISODES, values, where, whereArgs);
        dbWriteable.close();
    }

    @Override
    public boolean markWatchedIfReleased(boolean isNew, MediaItem mediaItem) {
        return isNew && alreadyReleased(mediaItem) && getMarkWatchedIfAlreadyReleased(context);
    }

    private boolean alreadyReleased(MediaItem mediaItem) {
        if (mediaItem.getReleaseDate() == null) {
            return true;
        }
        return mediaItem.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

}








