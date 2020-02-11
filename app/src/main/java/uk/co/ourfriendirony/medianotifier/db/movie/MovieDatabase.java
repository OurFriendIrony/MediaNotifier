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

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier._objects.movie.ItemMovie;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.general.StringHandler;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class MovieDatabase implements Database {
    private static final String SELECT_MOVIES = "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " ORDER BY " + MovieDatabaseDefinition.TM_TITLE + " ASC;";

    private static final String GET_MOVIE_TITLE_BY_ID = "SELECT " + MovieDatabaseDefinition.TM_TITLE + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=?;";

    private static final String GET_MOVIE_WATCHED_STATUS = "SELECT " + MovieDatabaseDefinition.TM_WATCHED + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=?;";

    private static final String COUNT_UNWATCHED_MOVIES_UNRELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " > @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_UNRELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " > @OFFSET@ ORDER BY " + MovieDatabaseDefinition.TM_DATE + " ASC;";

    private static final String COUNT_UNWATCHED_MOVIES_RELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " <= @OFFSET@ ORDER BY " + MovieDatabaseDefinition.TM_DATE + " ASC;";


    private static final String COUNT_UNWATCHED_MOVIES_TOTAL = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + ";";
    private static final String GET_UNWATCHED_MOVIES_TOTAL = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " ORDER BY " + MovieDatabaseDefinition.TM_DATE + " ASC;";


    private final MovieDatabaseDefinition databaseHelper;
    private final Context context;

    public MovieDatabase(Context context) {
        this.databaseHelper = new MovieDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(Item item) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, item, true);
        dbWritable.close();
    }

    @Override
    public void update(Item item) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, item, false);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, Item item, boolean isNewItem) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, item);
        ContentValues dbRow = new ContentValues();
        dbRow.put(MovieDatabaseDefinition.TM_ID, item.getId());
        dbRow.put(MovieDatabaseDefinition.TM_TITLE, cleanTitle(item.getTitle()));
        dbRow.put(MovieDatabaseDefinition.TM_IMDB, item.getExternalLink());
        dbRow.put(MovieDatabaseDefinition.TM_DATE, dateToString(item.getReleaseDate()));
        dbRow.put(MovieDatabaseDefinition.TM_OVERVIEW, item.getDescription());
        dbRow.put(MovieDatabaseDefinition.TM_TAGLINE, "");
        dbRow.put(MovieDatabaseDefinition.TM_COLLECTION, item.getSubtitle());
        if (markWatchedIfReleased(isNewItem, item)) {
            dbRow.put(MovieDatabaseDefinition.TM_WATCHED, MovieDatabaseDefinition.WATCHED_TRUE);
        } else {
            dbRow.put(MovieDatabaseDefinition.TM_WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT MOVIE]", dbRow.toString());
        dbWritable.replace(MovieDatabaseDefinition.TABLE_MOVIES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, Item item) {
        String[] args = new String[]{item.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.TM_WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(Item item) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{item.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.TM_WATCHED);
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
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=" + id + ";");
        dbWritable.close();
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatchedEpisodes(COUNT_UNWATCHED_MOVIES_RELEASED);
    }

    @Override
    public List<Item> getUnwatchedReleased() {
        return getUnwatched(GET_UNWATCHED_MOVIES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<Item> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_MOVIES_TOTAL, "UNWATCHED TOTAL");
    }

    private int countUnwatchedEpisodes(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
        String query = StringHandler.replaceTokens(countQuery, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    @Override
    public List<Item> getUnwatched(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
        String query = StringHandler.replaceTokens(getQuery, "@OFFSET@", offset);
        List<Item> items = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Item item = buildItemFromDB(cursor);
                items.add(item);
                Log.v(logTag, "Id=" + item.getId() + " | Title=" + item.getTitle() + " | Date=" + item.getReleaseDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return items;
    }

    @NonNull
    private Item buildItemFromDB(Cursor cursor) {
        Item item = new ItemMovie(
                getColumnValue(cursor, MovieDatabaseDefinition.TM_ID),
                getColumnValue(cursor, MovieDatabaseDefinition.TM_TITLE),
                getColumnValue(cursor, MovieDatabaseDefinition.TM_COLLECTION),
                getColumnValue(cursor, MovieDatabaseDefinition.TM_OVERVIEW),
                stringToDate(getColumnValue(cursor, MovieDatabaseDefinition.TM_DATE)),
                getColumnValue(cursor, MovieDatabaseDefinition.TM_IMDB),
                new ArrayList<Item>()
        );

        Log.d("BUILD_MOVIE", item.getId() + " " + item.getTitle());

        return item;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_MOVIES, null);
        try {
            while (cursor.moveToNext()) {
                items.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return items;
    }

    @Override
    public List<Item> getAllSubitems(String id) {
        return new ArrayList<>();
    }

    @Override
    public void updateWatchedStatus(Item item, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseDefinition.TM_WATCHED, watchedStatus);
        String where = MovieDatabaseDefinition.TM_ID + "=?";
        String[] whereArgs = new String[]{item.getId()};
        dbWriteable.update(MovieDatabaseDefinition.TABLE_MOVIES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean markWatchedIfReleased(boolean isNewItem, Item item) {
        return isNewItem && alreadyReleased(item) && getMarkWatchedIfAlreadyReleased(context);
    }

    private boolean alreadyReleased(Item item) {
        return item.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

}
