package uk.co.ourfriendirony.medianotifier.db.movie;

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
import uk.co.ourfriendirony.medianotifier.mediaitem.movie.Movie;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.dateToString;

public class MovieDatabase implements Database {
    private static final String SELECT_MOVIES = "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " ORDER BY " + MovieDatabaseDefinition.TITLE + " ASC;";

    private static final String GET_MOVIE_WATCHED_STATUS = "SELECT " + MovieDatabaseDefinition.WATCHED + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=?;";

    private static final String COUNT_UNWATCHED_MOVIES_RELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_MOVIES_TOTAL = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final MovieDatabaseDefinition databaseHelper;
    private final Context context;

    public MovieDatabase(Context context) {
        this.databaseHelper = new MovieDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(MediaItem mediaItem) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, mediaItem, true);
        dbWritable.close();
    }

    @Override
    public void update(MediaItem mediaItem) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, mediaItem, false);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, MediaItem mediaItem, boolean isNewItem) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, mediaItem);
        ContentValues dbRow = new ContentValues();
        dbRow.put(MovieDatabaseDefinition.ID, mediaItem.getId());
        dbRow.put(MovieDatabaseDefinition.TITLE, cleanTitle(mediaItem.getTitle()));
        dbRow.put(MovieDatabaseDefinition.EXTERNAL_URL, mediaItem.getExternalLink());
        dbRow.put(MovieDatabaseDefinition.RELEASE_DATE, dateToString(mediaItem.getReleaseDate()));
        dbRow.put(MovieDatabaseDefinition.DESCRIPTION, mediaItem.getDescription());
        dbRow.put(MovieDatabaseDefinition.SUBTITLE, mediaItem.getSubtitle());
        if (markWatchedIfReleased(isNewItem, mediaItem)) {
            dbRow.put(MovieDatabaseDefinition.WATCHED, MovieDatabaseDefinition.WATCHED_TRUE);
        } else {
            dbRow.put(MovieDatabaseDefinition.WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT MOVIE]", dbRow.toString());
        dbWritable.replace(MovieDatabaseDefinition.TABLE_MOVIES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem mediaItem) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return MovieDatabaseDefinition.WATCHED_TRUE.equals(watchedStatus);
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + ";");
        dbWritable.close();
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=" + id + ";");
        dbWritable.close();
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatchedEpisodes(COUNT_UNWATCHED_MOVIES_RELEASED);
    }

    @Override
    public List<MediaItem> getUnwatchedReleased() {
        return getUnwatched(GET_UNWATCHED_MOVIES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_MOVIES_TOTAL, "UNWATCHED TOTAL");
    }

    private int countUnwatchedEpisodes(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
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
    public List<MediaItem> getUnwatched(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
        String query = Helper.replaceTokens(getQuery, "@OFFSET@", offset);
        List<MediaItem> mediaItems = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = buildItemFromDB(cursor);
                mediaItems.add(mediaItem);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return mediaItems;
    }

    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        return new Movie(cursor);
    }

    @Override
    public List<MediaItem> getAll() {
        List<MediaItem> mediaItems = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_MOVIES, null);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return mediaItems;
    }

    @Override
    public List<MediaItem> getAllSubitems(String id) {
        return new ArrayList<>();
    }

    @Override
    public void updateWatchedStatus(MediaItem mediaItem, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseDefinition.WATCHED, watchedStatus);
        String where = MovieDatabaseDefinition.ID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId()};
        dbWriteable.update(MovieDatabaseDefinition.TABLE_MOVIES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean markWatchedIfReleased(boolean isNewItem, MediaItem mediaItem) {
        return isNewItem && alreadyReleased(mediaItem) && getMarkWatchedIfAlreadyReleased(context);
    }

    private boolean alreadyReleased(MediaItem mediaItem) {
        return mediaItem.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

}
