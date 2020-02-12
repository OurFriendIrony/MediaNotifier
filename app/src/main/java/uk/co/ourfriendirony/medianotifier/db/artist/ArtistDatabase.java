package uk.co.ourfriendirony.medianotifier.db.artist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;

public class ArtistDatabase implements Database {
    private static final String SELECT_ARTISTS = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.TA_TITLE + " ASC;";

    private static final String GET_ARTIST_TITLE_BY_ID = "SELECT " + ArtistDatabaseDefinition.TA_TITLE + " FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=?;";

    private static final String GET_ARTIST_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.TA_WATCHED + " FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=?;";

    private final ArtistDatabaseDefinition databaseHelper;
    private final Context context;

    public ArtistDatabase(Context context) {
        this.databaseHelper = new ArtistDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(MediaItem artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, artist, true);
        dbWritable.close();
    }

    @Override
    public void update(MediaItem artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insert(dbWritable, artist, true);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, MediaItem artist, boolean newArtist) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, artist);
        ContentValues dbRow = new ContentValues();

        dbRow.put(ArtistDatabaseDefinition.TA_ID, artist.getId());
        dbRow.put(ArtistDatabaseDefinition.TA_TITLE, cleanTitle(artist.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.TA_OVERVIEW, artist.getDescription());
        dbRow.put(ArtistDatabaseDefinition.TA_WATCHED, currentWatchedStatus);
        Log.d("[DB INSERT ARTIST]", dbRow.toString());
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_ARTISTS, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, MediaItem artist) {
        String[] args = new String[]{artist.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_ARTIST_WATCHED_STATUS, args);
        String watchedStatus = ArtistDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.TA_WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem artist) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{artist.getId()};
        Cursor cursor = dbReadable.rawQuery(GET_ARTIST_WATCHED_STATUS, args);
        String watchedStatus = ArtistDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.TA_WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return ArtistDatabaseDefinition.WATCHED_TRUE.equals(watchedStatus);
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + ";");
        dbWritable.close();
    }

    @Override
    public void delete(String artistId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=" + artistId + ";");
        dbWritable.close();
    }


    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        return new Artist(cursor);
    }

    @Override
    public List<MediaItem> getAll() {
        List<MediaItem> artists = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_ARTISTS, null);
        try {
            while (cursor.moveToNext()) {
                artists.add(buildItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return artists;
    }

    @Override
    public void updateWatchedStatus(MediaItem artist, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArtistDatabaseDefinition.TA_WATCHED, watchedStatus);
        String where = ArtistDatabaseDefinition.TA_ID + "=?";
        String[] whereArgs = new String[]{artist.getId()};
        dbWriteable.update(ArtistDatabaseDefinition.TABLE_ARTISTS, values, where, whereArgs);
        dbWriteable.close();
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    @Override
    public int countUnwatchedReleased() {
        return 0;
    }

    @Override
    public List<MediaItem> getUnwatchedReleased() {
        return new ArrayList<>();
    }

    @Override
    public List<MediaItem> getUnwatchedTotal() {
        return new ArrayList<>();
    }

    @Override
    public List<MediaItem> getUnwatched(String getQuery, String logTag) {
        return new ArrayList<>();
    }


    @Override
    public List<MediaItem> getAllSubitems(String id) {
        return new ArrayList<>();
    }
}
