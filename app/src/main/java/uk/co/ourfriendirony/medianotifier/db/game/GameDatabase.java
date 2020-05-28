package uk.co.ourfriendirony.medianotifier.db.game;

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
import uk.co.ourfriendirony.medianotifier.mediaitem.game.Game;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetGame;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_FALSE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.dateToString;

public class GameDatabase implements Database {
    private static final String SELECT_GAMES = "SELECT * FROM " + GameDatabaseDefinition.TABLE_GAMES + " ORDER BY " + GameDatabaseDefinition.TITLE + " ASC;";
    private static final String SELECT_GAMES_BY_ID = "SELECT * FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=? ORDER BY " + GameDatabaseDefinition.ID + " ASC;";

    private static final String GET_GAME_WATCHED_STATUS = "SELECT " + GameDatabaseDefinition.WATCHED + " FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=?;";

    private static final String COUNT_UNWATCHED_GAMES_RELEASED = "SELECT COUNT(*) FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
            "WHERE " + GameDatabaseDefinition.WATCHED + "=" + DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_GAMES_RELEASED = "SELECT * " +
            "FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
            "WHERE " + GameDatabaseDefinition.WATCHED + "=" + DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + GameDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_GAMES_TOTAL = "SELECT * " +
            "FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
            "WHERE " + GameDatabaseDefinition.WATCHED + "=" + DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + GameDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final Context context;
    private final SQLiteDatabase dbWritable;

    public GameDatabase(Context context) {
        this.dbWritable = new GameDatabaseDefinition(context).getWritableDatabase();
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
        dbRow.put(GameDatabaseDefinition.ID, mediaItem.getId());
        dbRow.put(GameDatabaseDefinition.SUBID, mediaItem.getSubId());
        dbRow.put(GameDatabaseDefinition.TITLE, cleanTitle(mediaItem.getTitle()));
        dbRow.put(GameDatabaseDefinition.EXTERNAL_URL, mediaItem.getExternalLink());
        dbRow.put(GameDatabaseDefinition.RELEASE_DATE, dateToString(mediaItem.getReleaseDate()));
        dbRow.put(GameDatabaseDefinition.DESCRIPTION, mediaItem.getDescription());
        dbRow.put(GameDatabaseDefinition.SUBTITLE, mediaItem.getSubtitle());
        if (markWatchedIfReleased(isNewItem, mediaItem)) {
            dbRow.put(GameDatabaseDefinition.WATCHED, DB_TRUE);
        } else {
            dbRow.put(GameDatabaseDefinition.WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT]", "Game: " + dbRow.toString());
        dbWritable.replace(GameDatabaseDefinition.TABLE_GAMES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbWritable.rawQuery(GET_GAME_WATCHED_STATUS, args);
        String watchedStatus = DB_FALSE;
        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, GameDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }
        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem mediaItem) {
        String[] args = new String[]{mediaItem.getId()};
        Cursor cursor = dbWritable.rawQuery(GET_GAME_WATCHED_STATUS, args);
        String watchedStatus = DB_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, GameDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }
        return DB_TRUE.equals(watchedStatus);
    }

    @Override
    public void deleteAll() {
        dbWritable.execSQL("DELETE FROM " + GameDatabaseDefinition.TABLE_GAMES + ";");
    }

    @Override
    public void delete(String id) {
        dbWritable.execSQL("DELETE FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=" + id + ";");
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatched(COUNT_UNWATCHED_GAMES_RELEASED);
    }

    @Override
    public List<MediaItem> getUnwatchedReleased() {
        return getUnwatched(GET_UNWATCHED_GAMES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_GAMES_TOTAL, "UNWATCHED TOTAL");
    }

    private int countUnwatched(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetGame(context) + " days')";
        String query = Helper.replaceTokens(countQuery, "@OFFSET@", offset);
        Cursor cursor = dbWritable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    @Override
    public List<MediaItem> getUnwatched(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetGame(context) + " days')";
        String query = Helper.replaceTokens(getQuery, "@OFFSET@", offset);
        List<MediaItem> mediaItems = new ArrayList<>();
        Cursor cursor = dbWritable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = buildItemFromDB(cursor);
                mediaItems.add(mediaItem);
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        return new Game(cursor);
    }

    @Override
    public List<MediaItem> readAllItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        Cursor cursor = dbWritable.rawQuery(SELECT_GAMES, null);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readAllParentItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        Cursor cursor = dbWritable.rawQuery(SELECT_GAMES, null);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(new Game(cursor));
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readChildItems(String id) {
        // TODO: Games don't have child items. Currently just return the main item as if it was the child until the display is updated
        List<MediaItem> mediaItems = new ArrayList<>();
        String[] args = {id};
        Cursor cursor = dbWritable.rawQuery(SELECT_GAMES_BY_ID, args);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public void updateWatchedStatus(MediaItem mediaItem, String watchedStatus) {
        ContentValues values = new ContentValues();
        values.put(GameDatabaseDefinition.WATCHED, watchedStatus);
        String where = GameDatabaseDefinition.ID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId()};
        dbWritable.update(GameDatabaseDefinition.TABLE_GAMES, values, where, whereArgs);
    }

    @Override
    public boolean markWatchedIfReleased(boolean isNew, MediaItem mediaItem) {
        return isNew && alreadyReleased(mediaItem) && getMarkWatchedIfAlreadyReleased(context);
    }

    @Override
    public String getCoreType() {
        // TODO: This is a lazy and unneeded implementation. It should be removed
        return "Game";
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
