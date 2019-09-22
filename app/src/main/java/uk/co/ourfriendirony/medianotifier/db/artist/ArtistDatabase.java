package uk.co.ourfriendirony.medianotifier.db.artist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.artist.Artist;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;

public class ArtistDatabase {
    private static final String SELECT_ARTISTS = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.TA_TITLE + " ASC;";

    private static final String GET_ARTIST_TITLE_BY_ID = "SELECT " + ArtistDatabaseDefinition.TA_TITLE + " FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=?;";

    private static final String GET_ARTIST_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.TA_WATCHED + " FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=?;";

    private final ArtistDatabaseDefinition databaseHelper;
    private final Context context;

    public ArtistDatabase(Context context) {
        this.databaseHelper = new ArtistDatabaseDefinition(context);
        this.context = context;
    }

    public void addArtist(Artist artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insertArtist(dbWritable, artist, true);
        dbWritable.close();
    }

    public void updateArtist(Artist artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insertArtist(dbWritable, artist, false);
        dbWritable.close();
    }

    private void insertArtist(SQLiteDatabase dbWritable, Artist artist, boolean newArtist) {
        String currentWatchedStatus = getArtistWatchedStatus(dbWritable, artist);
        ContentValues artistRow = new ContentValues();

        artistRow.put(ArtistDatabaseDefinition.TA_ID, artist.getId());
        artistRow.put(ArtistDatabaseDefinition.TA_TITLE, cleanTitle(artist.getTitle()));
        artistRow.put(ArtistDatabaseDefinition.TA_OVERVIEW, artist.getOverview());
        artistRow.put(ArtistDatabaseDefinition.TA_WATCHED, currentWatchedStatus);
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_ARTISTS, null, artistRow);
    }

    public String getArtistWatchedStatus(SQLiteDatabase dbReadable, Artist artist) {
        String[] args = new String[]{artist.getIdAsString()};
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

    public boolean getArtistWatchedStatusAsBoolean(Artist artist) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{artist.getIdAsString()};
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

    public void deleteAllArtists() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + ";");
        dbWritable.close();
    }

    public void deleteArtist(Integer artistId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.TA_ID + "=" + artistId + ";");
        dbWritable.close();
    }

    public String getTitleById(int artistId) {
        String title = "";
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(GET_ARTIST_TITLE_BY_ID, new String[]{String.valueOf(artistId)});
        try {
            while (cursor.moveToNext()) {
                title = getColumnValue(cursor, ArtistDatabaseDefinition.TA_TITLE);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return title;
    }

    @NonNull
    private Artist buildArtist(Cursor cursor) {
        Artist artist = new Artist();

        artist.setId(Integer.parseInt(getColumnValue(cursor, ArtistDatabaseDefinition.TA_ID)));
        artist.setTitle(getColumnValue(cursor, ArtistDatabaseDefinition.TA_TITLE));
        artist.setOverview(getColumnValue(cursor, ArtistDatabaseDefinition.TA_OVERVIEW));

        Log.d("BUILD_ARTIST", artist.getId() + " " + artist.getTitle());

        return artist;
    }

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_ARTISTS, null);
        try {
            while (cursor.moveToNext()) {
                artists.add(buildArtist(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return artists;
    }

    public void updateArtistWatchedStatus(Artist artist, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArtistDatabaseDefinition.TA_WATCHED, watchedStatus);
        String where = ArtistDatabaseDefinition.TA_ID + "=?";
        String[] whereArgs = new String[]{artist.getIdAsString()};
        dbWriteable.update(ArtistDatabaseDefinition.TABLE_ARTISTS, values, where, whereArgs);
        dbWriteable.close();
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }
}
