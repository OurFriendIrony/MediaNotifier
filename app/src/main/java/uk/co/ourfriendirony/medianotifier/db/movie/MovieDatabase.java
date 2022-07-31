package uk.co.ourfriendirony.medianotifier.db.movie;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_FALSE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.dateToString;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.general.Helper;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.movie.Movie;

public class MovieDatabase implements Database {
    private static final String SELECT_MOVIES = "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " ORDER BY " + MovieDatabaseDefinition.TITLE + " ASC;";
    private static final String SELECT_MOVIES_BY_ID = "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=? ORDER BY " + MovieDatabaseDefinition.ID + " ASC;";

    private static final String GET_MOVIE_WATCHED_STATUS = "SELECT " + MovieDatabaseDefinition.PLAYED + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=?;";

    private static final String COUNT_UNWATCHED_MOVIES_RELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_MOVIES_TOTAL = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final Context context;
    private final SQLiteDatabase dbWritable;

    public MovieDatabase(Context context) {
        this.dbWritable = new MovieDatabaseDefinition(context).getWritableDatabase();
        this.context = context;
    }

    @Override
    public void add(MediaItem mediaItem) {
        insert(mediaItem, true);
    }

    @Override
    public void update(MediaItem mediaItem) {
        insert(mediaItem, false);
    }

    private void insert(MediaItem mediaItem, boolean isNewItem) {
        String currentWatchedStatus = getWatchedStatus(mediaItem);
        ContentValues dbRow = new ContentValues();
        dbRow.put(MovieDatabaseDefinition.ID, mediaItem.getId());
        dbRow.put(MovieDatabaseDefinition.SUBID, mediaItem.getSubId());
        dbRow.put(MovieDatabaseDefinition.TITLE, cleanTitle(mediaItem.getTitle()));
        dbRow.put(MovieDatabaseDefinition.EXTERNAL_URL, mediaItem.getExternalLink());
        dbRow.put(MovieDatabaseDefinition.RELEASE_DATE, dateToString(mediaItem.getReleaseDate()));
        dbRow.put(MovieDatabaseDefinition.DESCRIPTION, mediaItem.getDescription());
        dbRow.put(MovieDatabaseDefinition.SUBTITLE, mediaItem.getSubtitle());
        if (markPlayedIfReleased(isNewItem, mediaItem)) {
            dbRow.put(MovieDatabaseDefinition.PLAYED, DB_TRUE);
        } else {
            dbRow.put(MovieDatabaseDefinition.PLAYED, currentWatchedStatus);
        }
        Log.d("[DB INSERT]", "Movie: " + dbRow);
        dbWritable.replace(MovieDatabaseDefinition.TABLE_MOVIES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbWritable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String playedStatus = DB_FALSE;
        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, MovieDatabaseDefinition.PLAYED);
            }
        } finally {
            cursor.close();
        }
        return playedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbWritable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String playedStatus = DB_FALSE;

        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, MovieDatabaseDefinition.PLAYED);
            }
        } finally {
            cursor.close();
        }
        return DB_TRUE.equals(playedStatus);
    }

    @Override
    public void deleteAll() {
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + ";");
    }

    @Override
    public void delete(String id) {
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=" + id + ";");
    }

    @Override
    public int countUnplayedReleased() {
        return countUnplayed(COUNT_UNWATCHED_MOVIES_RELEASED);
    }

    @Override
    public List<MediaItem> getUnplayedReleased() {
        return getUnplayed(GET_UNWATCHED_MOVIES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnplayedTotal() {
        return getUnplayed(GET_UNWATCHED_MOVIES_TOTAL, "UNWATCHED TOTAL");
    }

    private int countUnplayed(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
        String query = Helper.replaceTokens(countQuery, "@OFFSET@", offset);
        Cursor cursor = dbWritable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    @Override
    public List<MediaItem> getUnplayed(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
        String query = Helper.replaceTokens(getQuery, "@OFFSET@", offset);
        List<MediaItem> mediaItems = new ArrayList<>();
        try (Cursor cursor = dbWritable.rawQuery(query, null)) {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = buildItemFromDB(cursor);
                mediaItems.add(mediaItem);
            }
        }
        return mediaItems;
    }

    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        return new Movie(cursor);
    }

    @Override
    public List<MediaItem> readAllItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        try (Cursor cursor = dbWritable.rawQuery(SELECT_MOVIES, null)) {
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor));
            }
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readAllParentItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        try (Cursor cursor = dbWritable.rawQuery(SELECT_MOVIES, null)) {
            while (cursor.moveToNext()) {
                mediaItems.add(new Movie(cursor));
            }
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readChildItems(String id) {
        // TODO: Movies don't have child items. Currently just return the main item as if it was the child until the display is updated
        List<MediaItem> mediaItems = new ArrayList<>();
        String[] args = {id};
        try (Cursor cursor = dbWritable.rawQuery(SELECT_MOVIES_BY_ID, args)) {
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor));
            }
        }
        return mediaItems;
    }

    @Override
    public void updatePlayedStatus(MediaItem mediaItem, String playedStatus) {
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseDefinition.PLAYED, playedStatus);
        String where = MovieDatabaseDefinition.ID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId()};
        dbWritable.update(MovieDatabaseDefinition.TABLE_MOVIES, values, where, whereArgs);
    }

    @Override
    public boolean markPlayedIfReleased(boolean isNew, MediaItem mediaItem) {
        return isNew && alreadyReleased(mediaItem) && getMarkWatchedIfAlreadyReleased(context);
    }

    @Override
    public String getCoreType() {
        // TODO: This is a lazy and unneeded implementation. It should be removed
        return "Movie";
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
