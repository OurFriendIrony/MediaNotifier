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

import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;

public class ArtistDatabase implements Database {
    private static final String SELECT_ARTISTS = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.TITLE + " ASC;";

    private static final String GET_ARTIST_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.WATCHED + " FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.ID + "=?;";

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

        dbRow.put(ArtistDatabaseDefinition.ID, artist.getId());
        dbRow.put(ArtistDatabaseDefinition.TITLE, cleanTitle(artist.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, artist.getDescription());
        dbRow.put(ArtistDatabaseDefinition.WATCHED, currentWatchedStatus);
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
                watchedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.WATCHED);
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
                watchedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.WATCHED);
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
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.ID + "=" + artistId + ";");
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
        values.put(ArtistDatabaseDefinition.WATCHED, watchedStatus);
        String where = ArtistDatabaseDefinition.ID + "=?";
        String[] whereArgs = new String[]{artist.getId()};
        dbWriteable.update(ArtistDatabaseDefinition.TABLE_ARTISTS, values, where, whereArgs);
        dbWriteable.close();
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
