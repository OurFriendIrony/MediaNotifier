package uk.co.ourfriendirony.medianotifier.db.artist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import uk.co.ourfriendirony.medianotifier.db.Database;
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
    private static final String SELECT_RELEASES_BY_ID = "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_RELEASE_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.PLAYED + " FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? AND " + ArtistDatabaseDefinition.SUBID + "=?;";

    private static final String COUNT_UNWATCHED_RELEASES_RELEASED = "SELECT COUNT(*) FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;";

    private static final String GET_UNWATCHED_RELEASES_RELEASED = "SELECT * " +
            "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private static final String GET_UNWATCHED_RELEASES_TOTAL = "SELECT * " +
            "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
            "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;";

    private final Context context;
    private final SQLiteDatabase dbWritable;

    public ArtistDatabase(Context context) {
        this.dbWritable = new ArtistDatabaseDefinition(context).getWritableDatabase();
        this.context = context;
    }

    @Override
    public void add(MediaItem artist) {
        for (MediaItem release : artist.getChildren()) {
            insertRelease(release, true);
        }
        insert(artist);
    }

    @Override
    public void update(MediaItem artist) {
        for (MediaItem release : artist.getChildren()) {
            insertRelease(release, false);
        }
        insert(artist);
    }

    private void insert(MediaItem artist) {
        ContentValues dbRow = new ContentValues();
        dbRow.put(ArtistDatabaseDefinition.ID, artist.getId());
        dbRow.put(ArtistDatabaseDefinition.SUBID, artist.getSubId());
        dbRow.put(ArtistDatabaseDefinition.TITLE, cleanTitle(artist.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, artist.getSubtitle());
        dbRow.put(ArtistDatabaseDefinition.EXTERNAL_URL, artist.getExternalLink());
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, dateToString(artist.getReleaseDate()));
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, artist.getDescription());
        Log.d("[DB INSERT]", "Artist: " + dbRow.toString());
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_ARTISTS, null, dbRow);
    }

    private void insertRelease(MediaItem release, boolean isNew) {
        String currentWatchedStatus = getWatchedStatus(release);
        ContentValues dbRow = new ContentValues();
        dbRow.put(ArtistDatabaseDefinition.ID, release.getId());
        dbRow.put(ArtistDatabaseDefinition.SUBID, release.getSubId());
        dbRow.put(ArtistDatabaseDefinition.TITLE, cleanTitle(release.getTitle()));
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, release.getSubtitle());
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, dateToString(release.getReleaseDate()));
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, release.getDescription());
        if (markPlayedIfReleased(isNew, release)) {
            dbRow.put(ArtistDatabaseDefinition.PLAYED, DB_TRUE);
        } else {
            dbRow.put(ArtistDatabaseDefinition.PLAYED, currentWatchedStatus);
        }
        Log.d("[DB INSERT]", "Release: " + dbRow.toString());
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_RELEASES, null, dbRow);
    }

    @Override
    public String getWatchedStatus(MediaItem release) {
        String[] args = new String[]{release.getId(), release.getSubId()};
        Cursor cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args);
        String playedStatus = DB_FALSE;

        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.PLAYED);
            }
        } finally {
            cursor.close();
        }

        return playedStatus;
    }

    @Override
    public boolean getWatchedStatusAsBoolean(MediaItem release) {
        String[] args = new String[]{release.getId(), release.getSubId()};
        Cursor cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args);
        String playedStatus = DB_FALSE;

        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.PLAYED);
            }
        } finally {
            cursor.close();
        }
        return DB_TRUE.equals(playedStatus);
    }

    @Override
    public void deleteAll() {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + ";");
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + ";");
    }

    @Override
    public void delete(String id) {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";");
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";");
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
        Cursor subCursor = dbWritable.rawQuery(SELECT_RELEASES_BY_ID, new String[]{id});
        try {
            while (subCursor.moveToNext()) {
                releases.add(buildSubItemFromDB(subCursor));
            }
        } finally {
            subCursor.close();
        }
        return new Artist(cursor, releases);
    }

    @Override
    public int countUnplayedReleased() {
        return countUnplayed(COUNT_UNWATCHED_RELEASES_RELEASED);
    }

    private int countUnplayed(String countQuery) {
        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
        String query = Helper.replaceTokens(countQuery, "@OFFSET@", offset);
        Cursor cursor = dbWritable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    @Override
    public List<MediaItem> getUnplayedReleased() {
        return getUnplayed(GET_UNWATCHED_RELEASES_RELEASED, "UNWATCHED RELEASED");
    }

    @Override
    public List<MediaItem> getUnplayedTotal() {
        return getUnplayed(GET_UNWATCHED_RELEASES_TOTAL, "UNWATCHED TOTAL");
    }

    @Override
    public List<MediaItem> getUnplayed(String getQuery, String logTag) {
        String offset = "date('now','-" + getNotificationDayOffsetArtist(context) + " days')";
        String query = Helper.replaceTokens(getQuery, "@OFFSET@", offset);
        List<MediaItem> mediaItems = new ArrayList<>();
        Cursor cursor = dbWritable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = buildSubItemFromDB(cursor);
                mediaItems.add(mediaItem);
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readAllItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        Cursor cursor = dbWritable.rawQuery(SELECT_ARTISTS, null);
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
        Cursor cursor = dbWritable.rawQuery(SELECT_ARTISTS, null);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(new Artist(cursor));
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public List<MediaItem> readChildItems(String id) {
        List<MediaItem> mediaItems = new ArrayList<>();
        String[] args = {id};
        Cursor cursor = dbWritable.rawQuery(SELECT_RELEASES_BY_ID, args);
        try {
            while (cursor.moveToNext()) {
                mediaItems.add(buildSubItemFromDB(cursor));
            }
        } finally {
            cursor.close();
        }
        return mediaItems;
    }

    @Override
    public void updatePlayedStatus(MediaItem mediaItem, String playedStatus) {
        ContentValues values = new ContentValues();
        values.put(ArtistDatabaseDefinition.PLAYED, playedStatus);
        String where = ArtistDatabaseDefinition.ID + "=? and " + ArtistDatabaseDefinition.SUBID + "=?";
        String[] whereArgs = new String[]{mediaItem.getId(), mediaItem.getSubId()};
        dbWritable.update(ArtistDatabaseDefinition.TABLE_RELEASES, values, where, whereArgs);
    }

    @Override
    public boolean markPlayedIfReleased(boolean isNew, MediaItem mediaItem) {
        return isNew && alreadyReleased(mediaItem) && getMarkWatchedIfAlreadyReleased(context);
    }

    @Override
    public String getCoreType() {
        // TODO: This is a lazy and unneeded implementation. It should be removed
        return "Artist";
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
