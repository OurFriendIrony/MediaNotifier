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

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier._objects.tv.ItemTV;
import uk.co.ourfriendirony.medianotifier._objects.tv.ItemTVEpisode;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.general.StringHandler;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class TVShowDatabase implements Database {
    private static final String SELECT_TVSHOWS = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " ORDER BY " + TVShowDatabaseDefinition.TT_TITLE + " ASC;";
    private static final String SELECT_TVEPISODES = "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " WHERE " + TVShowDatabaseDefinition.TTSE_ID + "=? ORDER BY " + TVShowDatabaseDefinition.TTSE_SUBTITLE + " ASC;";

    private static final String GET_TVEPISODE_WATCHED_STATUS = "SELECT " + TVShowDatabaseDefinition.TTSE_WATCHED + " FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " WHERE " + TVShowDatabaseDefinition.TTSE_ID + "=? AND " + TVShowDatabaseDefinition.TTSE_SUBTITLE + "=?;";

    private static final String COUNT_UNWATCHED_EPISODES_RELEASED = "SELECT COUNT(*) FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.TTSE_WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " AND " + TVShowDatabaseDefinition.TTSE_DATE + " <= @OFFSET@;";

    private static final String GET_UNWATCHED_EPISODES_RELEASED = "SELECT " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + "." + TVShowDatabaseDefinition.TTSE_ID + "," + TVShowDatabaseDefinition.TTSE_TITLE + "," + TVShowDatabaseDefinition.TTSE_SUBTITLE + "," + TVShowDatabaseDefinition.TTSE_OVERVIEW + "," + TVShowDatabaseDefinition.TTSE_DATE + "," + TVShowDatabaseDefinition.TTSE_IMDB + " " +
            "FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.TTSE_WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " AND " + TVShowDatabaseDefinition.TTSE_DATE + " <= @OFFSET@ ORDER BY " + TVShowDatabaseDefinition.TTSE_DATE + " ASC;";

    private static final String GET_UNWATCHED_EPISODES_TOTAL = "SELECT " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + "." + TVShowDatabaseDefinition.TTSE_ID + "," + TVShowDatabaseDefinition.TTSE_TITLE + "," + TVShowDatabaseDefinition.TTSE_SUBTITLE + "," + TVShowDatabaseDefinition.TTSE_OVERVIEW + "," + TVShowDatabaseDefinition.TTSE_DATE + "," + TVShowDatabaseDefinition.TTSE_IMDB + " " +
            "FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " " +
            "WHERE " + TVShowDatabaseDefinition.TTSE_WATCHED + "=" + TVShowDatabaseDefinition.WATCHED_FALSE + " ORDER BY " + TVShowDatabaseDefinition.TTSE_DATE + " ASC;";

    private final TVShowDatabaseDefinition databaseHelper;
    private final Context context;

    public TVShowDatabase(Context context) {
        this.databaseHelper = new TVShowDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(Item show) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (Item episode : show.getChildren()) {
            insertEpisode(dbWritable, episode, true);
        }
        insert(dbWritable, show);
        dbWritable.close();
    }

    @Override
    public void update(Item show) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (Item episode : show.getChildren()) {
            insertEpisode(dbWritable, episode, false);
        }
        insert(dbWritable, show);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, Item item) {
        ContentValues dbRow = new ContentValues();
        dbRow.put(TVShowDatabaseDefinition.TT_ID, item.getId());
        dbRow.put(TVShowDatabaseDefinition.TT_TITLE, cleanTitle(item.getTitle()));
        dbRow.put(TVShowDatabaseDefinition.TT_SUBTITLE, item.getSubtitle());
        dbRow.put(TVShowDatabaseDefinition.TT_IMDB, item.getExternalLink().toString());
        dbRow.put(TVShowDatabaseDefinition.TT_DATE, dateToString(item.getReleaseDate()));
        dbRow.put(TVShowDatabaseDefinition.TT_OVERVIEW, item.getDescription());
        Log.d("[DB INSERT TV]", dbRow.toString());
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_TVSHOWS, null, dbRow);

    }

    private void insertEpisode(SQLiteDatabase dbWritable, Item episode, boolean isNewTVShow) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, episode);
        ContentValues dbRow = new ContentValues();
        dbRow.put(TVShowDatabaseDefinition.TTSE_ID, episode.getId());
        dbRow.put(TVShowDatabaseDefinition.TTSE_TITLE, cleanTitle(episode.getTitle()));
        dbRow.put(TVShowDatabaseDefinition.TTSE_SUBTITLE, episode.getSubtitle());
        dbRow.put(TVShowDatabaseDefinition.TTSE_DATE, dateToString(episode.getReleaseDate()));
        dbRow.put(TVShowDatabaseDefinition.TTSE_OVERVIEW, episode.getDescription());
        if (markWatchedIfReleased(isNewTVShow, episode)) {
            dbRow.put(TVShowDatabaseDefinition.TTSE_WATCHED, TVShowDatabaseDefinition.WATCHED_TRUE);
        } else {
            dbRow.put(TVShowDatabaseDefinition.TTSE_WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT TV EPISODE]", dbRow.toString());
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, Item item) {
        String[] args = new String[]{item.getId(), item.getSubtitle()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = TVShowDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(Item item) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{item.getId(), item.getSubtitle()};
        Cursor cursor = dbReadable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args);
        String watchedStatus = TVShowDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_WATCHED);
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
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + ";");
        dbWritable.close();
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " WHERE " + TVShowDatabaseDefinition.TT_ID + "=" + id + ";");
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES + " WHERE " + TVShowDatabaseDefinition.TTSE_ID + "=" + id + ";");
        dbWritable.close();
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatched(COUNT_UNWATCHED_EPISODES_RELEASED);
    }

    @Override
    public List<Item> getUnwatchedReleased() {
        return getUnwatched(GET_UNWATCHED_EPISODES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<Item> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_EPISODES_TOTAL, "UNWATCHED TOTAL");
    }

    private int countUnwatched(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetTV(context) + " days')";
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
        String offset = "date('now','-" + getNotificationDayOffsetTV(context) + " days')";
        String query = StringHandler.replaceTokens(getQuery, "@OFFSET@", offset);
        List<Item> items = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Item item = buildSubItemFromDB(cursor);
                Log.v(logTag, item.toString());
                items.add(item);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return items;
    }

    @NonNull
    private Item buildSubItemFromDB(Cursor cursor) {
        Item item = new ItemTVEpisode(
                getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_ID),
                getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_TITLE),
                getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_SUBTITLE),
                getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_OVERVIEW),
                stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_DATE)),
                getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_IMDB),
                new ArrayList<Item>()
        );
        Log.d("[DB READ]", item.toString());
        return item;
    }

    @NonNull
    private Item buildItemFromDB(Cursor cursor) {
        // TODO: When building an item, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.

        String id = getColumnValue(cursor, TVShowDatabaseDefinition.TT_ID);
        List<Item> episodes = new ArrayList<>();

        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor tvSeasonCursor = dbReadable.rawQuery(SELECT_TVEPISODES, new String[]{id});
        try {
            while (tvSeasonCursor.moveToNext()) {
                episodes.add(buildSubItemFromDB(tvSeasonCursor));
            }
        } finally {
            tvSeasonCursor.close();
        }
        dbReadable.close();
        Item item = new ItemTV(
                id,
                getColumnValue(cursor, TVShowDatabaseDefinition.TT_TITLE),
                getColumnValue(cursor, TVShowDatabaseDefinition.TT_SUBTITLE),
                getColumnValue(cursor, TVShowDatabaseDefinition.TT_OVERVIEW),
                stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.TT_DATE)),
                getColumnValue(cursor, TVShowDatabaseDefinition.TT_IMDB),
                episodes
        );

        Log.d("[DB READ]", item.toString());
        return item;
    }

    @Override
    public List<Item> getAll() {
        List<Item> shows = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor tvShowCursor = dbReadable.rawQuery(SELECT_TVSHOWS, null);
        try {
            while (tvShowCursor.moveToNext()) {
                shows.add(buildItemFromDB(tvShowCursor));
            }
        } finally {
            tvShowCursor.close();
        }
        dbReadable.close();
        return shows;
    }

    @Override
    public List<Item> getAllSubitems(String id) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = {id};
        Cursor tvShowCursor = dbReadable.rawQuery(SELECT_TVEPISODES, args);
        try {
            while (tvShowCursor.moveToNext()) {
                items.add(buildItemFromDB(tvShowCursor));
            }
        } finally {
            tvShowCursor.close();
        }
        dbReadable.close();
        return items;
    }

    @Override
    public void updateWatchedStatus(Item item, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TVShowDatabaseDefinition.TTSE_WATCHED, watchedStatus);
        String where = TVShowDatabaseDefinition.TTSE_ID + "=? and " + TVShowDatabaseDefinition.TTSE_SUBTITLE + "=?";
        String[] whereArgs = new String[]{item.getId(), item.getSubtitle()};
        dbWriteable.update(TVShowDatabaseDefinition.TABLE_TVSHOWS_EPISODES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean markWatchedIfReleased(boolean isNewTVShow, Item episode) {
        return isNewTVShow && alreadyReleased(episode) && getMarkWatchedIfAlreadyReleased(context);
    }

    private boolean alreadyReleased(Item episode) {
        return episode.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

}
