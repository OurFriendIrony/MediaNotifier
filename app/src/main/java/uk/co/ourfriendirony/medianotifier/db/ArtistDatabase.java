package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.artist.Artist;

import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.TABLE_ARTISTS;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.TA_ID;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.TA_TITLE;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.TA_WATCHED;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.WATCHED_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;

public class ArtistDatabase {
    private static final String SELECT_ARTISTS = "SELECT * FROM " + TABLE_ARTISTS + " ORDER BY " + TA_TITLE + " ASC;";

    private static final String GET_ARTIST_TITLE_BY_ID = "SELECT " + TA_TITLE + " FROM " + TABLE_ARTISTS + " WHERE " + TA_ID + "=?;";

    private static final String GET_ARTIST_WATCHED_STATUS = "SELECT " + TA_WATCHED + " FROM " + TABLE_ARTISTS + " WHERE " + TA_ID + "=?;";

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

        artistRow.put(TA_ID, artist.getId());
        artistRow.put(TA_TITLE, cleanTitle(artist.getTitle()));
        artistRow.put(TA_WATCHED, currentWatchedStatus);

        dbWritable.replace(TABLE_ARTISTS, null, artistRow);
    }

    public String getArtistWatchedStatus(SQLiteDatabase dbReadable, Artist artist) {
        String[] args = new String[]{artist.getIdAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_ARTIST_WATCHED_STATUS, args);
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TA_WATCHED);
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
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TA_WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return WATCHED_TRUE.equals(watchedStatus);
    }

    public void deleteAllArtists() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_ARTISTS + ";");
        dbWritable.close();
    }

    public void deleteArtist(Integer artistId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_ARTISTS + " WHERE " + TA_ID + "=" + artistId + ";");
        dbWritable.close();
    }

    public String getTitleById(int artistId) {
        String title = "";
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(GET_ARTIST_TITLE_BY_ID, new String[]{String.valueOf(artistId)});
        try {
            while (cursor.moveToNext()) {
                title = getColumnValue(cursor, TA_TITLE);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return title;
    }
//
//
//    public List<Artist> getUnwatchedReleasedArtists() {
//        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
//        String query = StringHandler.replaceTokens(GET_UNWATCHED_ARTISTS_RELEASED, "@OFFSET@", offset);
//        List<Artist> artists = new ArrayList<>();
//        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
//
//        Cursor cursor = dbReadable.rawQuery(query, null);
//        try {
//            while (cursor.moveToNext()) {
//                Artist artist = buildArtist(cursor);
//                artists.add(artist);
//                Log.v("*****IMHERE*****", "UNWATCHED AIRED ARTISTS: Id=" + artist.getId() + " | Title=" + artist.getTitle() + " | Date=" + artist.getReleaseDate());
//            }
//        } finally {
//            cursor.close();
//        }
//        dbReadable.close();
//        return artists;
//    }
//
//    public List<Artist> getUnwatchedUnreleasedArtists() {
//        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
//        String query = StringHandler.replaceTokens(GET_UNWATCHED_ARTISTS_UNRELEASED, "@OFFSET@", offset);
//        List<Artist> artists = new ArrayList<>();
//        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
//
//        Cursor cursor = dbReadable.rawQuery(query, null);
//        try {
//            while (cursor.moveToNext()) {
//                Artist artist = buildArtist(cursor);
//                artists.add(artist);
//                Log.v("*****IMHERE*****", "UNWATCHED UNAIRED ARTISTS: Id=" + artist.getId() + " | Title=" + artist.getTitle() + " | Date=" + artist.getReleaseDate());
//            }
//        } finally {
//            cursor.close();
//        }
//        dbReadable.close();
//        return artists;
//    }

    @NonNull
    private Artist buildArtist(Cursor cursor) {
        Artist artist = new Artist();

        artist.setId(Integer.parseInt(getColumnValue(cursor, TA_ID)));
        artist.setTitle(getColumnValue(cursor, TA_TITLE));

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
        values.put(TA_WATCHED, watchedStatus);
        String where = TA_ID + "=?";
        String[] whereArgs = new String[]{artist.getIdAsString()};
        dbWriteable.update(TABLE_ARTISTS, values, where, whereArgs);
        dbWriteable.close();
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }
}
