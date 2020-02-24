package uk.co.ourfriendirony.medianotifier.db.artist;

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
import uk.co.ourfriendirony.medianotifier.general.Constants;
import uk.co.ourfriendirony.medianotifier.general.Helper;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Release;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetArtist;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_FALSE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.Helper.dateToString;

public class ArtistDatabase implements Database {
    private static final String SELECT_ARTISTS = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.TITLE + " ASC;";
    private static final String SELECT_RELEASES = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_RELEASE_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.WATCHED + " FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? AND " + ArtistDatabaseDefinition.SUBID + "=?;";

    private static final String COUNT_UNWATCHED_RELEASES_RELEASED = "SELECT COUNT(*) FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.WATCHED + "=" + DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";

    private static final String GET_UNWATCHED_RELEASES_RELEASED = "SELECT * " +
            "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.WATCHED + "=" + DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_RELEASES_TOTAL = "SELECT * " +
            "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.WATCHED + "=" + DB_FALSE + " ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final ArtistDatabaseDefinition databaseHelper;
    private final Context context;

    public ArtistDatabase(Context context) {
        this.databaseHelper = new ArtistDatabaseDefinition(context);
        this.context = context;
    }

    @Override
    public void add(MediaItem artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (MediaItem release : artist.getChildren()) {
            insertRelease(dbWritable, release, true);
        }
        insert(dbWritable, artist);
        dbWritable.close();
    }

    @Override
    public void update(MediaItem artist) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        for (MediaItem release : artist.getChildren()) {
            insertRelease(dbWritable, release, false);
        }
        insert(dbWritable, artist);
        dbWritable.close();
    }

    private void insert(SQLiteDatabase dbWritable, MediaItem artist) {
        ContentValues dbRow = new ContentValues();
        dbRow.put(ArtistDatabaseDefinition.ID, artist.getId());
        dbRow.put(ArtistDatabaseDefinition.SUBID, artist.getSubId());
        dbRow.put(ArtistDatabaseDefinition.TITLE, cleanTitle(artist.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, artist.getSubtitle());
        dbRow.put(ArtistDatabaseDefinition.EXTERNAL_URL, artist.getExternalLink());
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, dateToString(artist.getReleaseDate()));
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, artist.getDescription());
        Log.d("[DB INSERT ARTIST]", dbRow.toString());
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_ARTISTS, null, dbRow);

    }

    private void insertRelease(SQLiteDatabase dbWritable, MediaItem release, boolean isNew) {
        String currentWatchedStatus = getWatchedStatus(dbWritable, release);
        ContentValues dbRow = new ContentValues();
        dbRow.put(ArtistDatabaseDefinition.ID, release.getId());
        dbRow.put(ArtistDatabaseDefinition.SUBID, release.getSubId());
        dbRow.put(ArtistDatabaseDefinition.TITLE, cleanTitle(release.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, release.getSubtitle());
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, dateToString(release.getReleaseDate()));
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, release.getDescription());
        if (markWatchedIfReleased(isNew, release)) {
            dbRow.put(ArtistDatabaseDefinition.WATCHED, DB_TRUE);
        } else {
            dbRow.put(ArtistDatabaseDefinition.WATCHED, currentWatchedStatus);
        }
        Log.d("[DB INSERT RELEASE]", dbRow.toString());
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_RELEASES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(SQLiteDatabase dbReadable, MediaItem release) {
        String[] args = new String[]{release.getId(), release.getSubId()};
        Cursor cursor = dbReadable.rawQuery(GET_RELEASE_WATCHED_STATUS, args);
        String watchedStatus = DB_FALSE;

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
    public boolean getWatchedStatusAsBoolean(MediaItem release) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{release.getId(), release.getSubId()};
        Cursor cursor = dbReadable.rawQuery(GET_RELEASE_WATCHED_STATUS, args);
        String watchedStatus = DB_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return DB_TRUE.equals(watchedStatus);
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + ";");
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + ";");
        dbWritable.close();
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";");
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";");
        dbWritable.close();
    }

    @NonNull
    private MediaItem buildSubItemFromDB(Cursor cursor) {
        return new Release(cursor);
    }

    @NonNull
    private MediaItem buildItemFromDB(Cursor cursor) {
        // TODO: When building an mediaItem, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.

        String id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        List<MediaItem> releases = new ArrayList<>();

        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor subCursor = dbReadable.rawQuery(SELECT_RELEASES, new String[]{id});
        try {
            while (subCursor.moveToNext()) {
                releases.add(buildSubItemFromDB(subCursor));
            }
        } finally {
            subCursor.close();
        }
        dbReadable.close();
        return new Artist(cursor, releases);
    }

    @Override
    public int countUnwatchedReleased() {
        return countUnwatched(COUNT_UNWATCHED_RELEASES_RELEASED);
    }

    private int countUnwatched(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
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
        return getUnwatched(GET_UNWATCHED_RELEASES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnwatchedTotal() {
        return getUnwatched(GET_UNWATCHED_RELEASES_TOTAL, "UNWATCHED TOTAL");
    }

    @Override
    public List<MediaItem> getUnwatched(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
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
    public List<MediaItem> getAllSubitems(String id) {
        List<MediaItem> mediaItems = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = {id};
        Cursor tvShowCursor = dbReadable.rawQuery(SELECT_RELEASES, args);
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
        values.put(ArtistDatabaseDefinition.WATCHED, watchedStatus);
        String where = ArtistDatabaseDefinition.ID + "=? and " + ArtistDatabaseDefinition.SUBID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId(), mediaItem.getSubId()};
        dbWriteable.update(ArtistDatabaseDefinition.TABLE_RELEASES, values, where, whereArgs);
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
