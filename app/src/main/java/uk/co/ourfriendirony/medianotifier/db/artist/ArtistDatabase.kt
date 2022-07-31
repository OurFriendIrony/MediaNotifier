package uk.co.ourfriendirony.medianotifier.db.artist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper
import uk.co.ourfriendirony.medianotifier.general.Constants
import uk.co.ourfriendirony.medianotifier.general.Helper
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Release
import java.util.*

class ArtistDatabase(context: Context) : Database {
    private val context: Context
    private val dbWritable: SQLiteDatabase
    override fun add(artist: MediaItem) {
        for (release in artist.children) {
            insertRelease(release, true)
        }
        insert(artist)
    }

    override fun update(artist: MediaItem) {
        for (release in artist.children) {
            insertRelease(release, false)
        }
        insert(artist)
    }

    private fun insert(artist: MediaItem) {
        val dbRow = ContentValues()
        dbRow.put(ArtistDatabaseDefinition.Companion.ID, artist.id)
        dbRow.put(ArtistDatabaseDefinition.Companion.SUBID, artist.subId)
        dbRow.put(ArtistDatabaseDefinition.Companion.TITLE, Helper.cleanTitle(artist.title!!))
        dbRow.put(ArtistDatabaseDefinition.Companion.SUBTITLE, artist.subtitle)
        dbRow.put(ArtistDatabaseDefinition.Companion.EXTERNAL_URL, artist.externalLink)
        dbRow.put(ArtistDatabaseDefinition.Companion.RELEASE_DATE, Helper.dateToString(artist.releaseDate))
        dbRow.put(ArtistDatabaseDefinition.Companion.DESCRIPTION, artist.description)
        Log.d("[DB INSERT]", "Artist: $dbRow")
        dbWritable.replace(ArtistDatabaseDefinition.Companion.TABLE_ARTISTS, null, dbRow)
    }

    private fun insertRelease(release: MediaItem, isNew: Boolean) {
        val currentWatchedStatus = getWatchedStatus(release)
        val dbRow = ContentValues()
        dbRow.put(ArtistDatabaseDefinition.Companion.ID, release.id)
        dbRow.put(ArtistDatabaseDefinition.Companion.SUBID, release.subId)
        dbRow.put(ArtistDatabaseDefinition.Companion.TITLE, Helper.cleanTitle(release.title!!))
        dbRow.put(ArtistDatabaseDefinition.Companion.SUBTITLE, release.subtitle)
        dbRow.put(ArtistDatabaseDefinition.Companion.RELEASE_DATE, Helper.dateToString(release.releaseDate))
        dbRow.put(ArtistDatabaseDefinition.Companion.DESCRIPTION, release.description)
        if (markPlayedIfReleased(isNew, release)) {
            dbRow.put(ArtistDatabaseDefinition.Companion.PLAYED, Constants.DB_TRUE)
        } else {
            dbRow.put(ArtistDatabaseDefinition.Companion.PLAYED, currentWatchedStatus)
        }
        Log.d("[DB INSERT]", "Release: $dbRow")
        dbWritable.replace(ArtistDatabaseDefinition.Companion.TABLE_RELEASES, null, dbRow)
    }

    override fun getWatchedStatus(release: MediaItem): String {
        val args = arrayOf(release.id, release.subId)
        val cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.Companion.PLAYED)
            }
        } finally {
            cursor.close()
        }
        return playedStatus
    }

    override fun getWatchedStatusAsBoolean(release: MediaItem): Boolean {
        val args = arrayOf(release.id, release.subId)
        val cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        try {
            while (cursor.moveToNext()) {
                playedStatus = getColumnValue(cursor, ArtistDatabaseDefinition.Companion.PLAYED)
            }
        } finally {
            cursor.close()
        }
        return Constants.DB_TRUE == playedStatus
    }

    override fun deleteAll() {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.Companion.TABLE_ARTISTS + ";")
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + ";")
    }

    override fun delete(id: String) {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.Companion.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.Companion.ID + "=\"" + id + "\";")
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.Companion.ID + "=\"" + id + "\";")
    }

    private fun buildSubItemFromDB(cursor: Cursor): MediaItem {
        return Release(cursor)
    }

    private fun buildItemFromDB(cursor: Cursor): MediaItem {
        // TODO: When building an mediaItem, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.
        val id = getColumnValue(cursor, ArtistDatabaseDefinition.Companion.ID)
        val releases: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_RELEASES_BY_ID, arrayOf(id)).use { subCursor ->
            while (subCursor.moveToNext()) {
                releases.add(buildSubItemFromDB(subCursor))
            }
        }
        return Artist(cursor, releases)
    }

    override fun countUnplayedReleased(): Int {
        return countUnplayed(COUNT_UNWATCHED_RELEASES_RELEASED)
    }

    private fun countUnplayed(countQuery: String): Int {
        val offset = "date('now','-" + PropertyHelper.getNotificationDayOffsetArtist(context) + " days')"
        val query = Helper.replaceTokens(countQuery, "@OFFSET@", offset)
        val cursor = dbWritable.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    override val unplayedReleased: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_RELEASES_RELEASED, "UNWATCHED RELEASED")
    override val unplayedTotal: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_RELEASES_TOTAL, "UNWATCHED TOTAL")

    override fun getUnplayed(getQuery: String?, logTag: String?): List<MediaItem> {
        val offset = "date('now','-" + PropertyHelper.getNotificationDayOffsetArtist(context) + " days')"
        val query = Helper.replaceTokens(getQuery!!, "@OFFSET@", offset)
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(query, null).use { cursor ->
            while (cursor.moveToNext()) {
                val mediaItem = buildSubItemFromDB(cursor)
                mediaItems.add(mediaItem)
            }
        }
        return mediaItems
    }

    override fun readAllItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_ARTISTS, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun readAllParentItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_ARTISTS, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(Artist(cursor))
            }
        }
        return mediaItems
    }

    override fun readChildItems(id: String): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val args = arrayOf(id)
        dbWritable.rawQuery(SELECT_RELEASES_BY_ID, args).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildSubItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun updatePlayedStatus(mediaItem: MediaItem, playedStatus: String?) {
        val values = ContentValues()
        values.put(ArtistDatabaseDefinition.Companion.PLAYED, playedStatus)
        val where: String = ArtistDatabaseDefinition.Companion.ID + "=? and " + ArtistDatabaseDefinition.Companion.SUBID + "=?"
        val whereArgs = arrayOf(mediaItem.id, mediaItem.subId)
        dbWritable.update(ArtistDatabaseDefinition.Companion.TABLE_RELEASES, values, where, whereArgs)
    }

    override fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean {
        return isNew && alreadyReleased(mediaItem) && PropertyHelper.getMarkWatchedIfAlreadyReleased(context)
    }

    // TODO: This is a lazy and unneeded implementation. It should be removed
    override val coreType: String
        get() = "Artist"

    private fun alreadyReleased(mediaItem: MediaItem): Boolean {
        return if (mediaItem.releaseDate == null) {
            true
        } else mediaItem.releaseDate!!.compareTo(Date()) < 0
    }

    private fun getColumnValue(cursor: Cursor, field: String): String {
        return cursor.getString(cursor.getColumnIndex(field))
    }

    companion object {
        private val SELECT_ARTISTS = "SELECT * FROM " + ArtistDatabaseDefinition.Companion.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.Companion.TITLE + " ASC;"
        private val SELECT_RELEASES_BY_ID = "SELECT * FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.Companion.ID + "=? ORDER BY " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " ASC;"
        private val GET_RELEASE_WATCHED_STATUS = "SELECT " + ArtistDatabaseDefinition.Companion.PLAYED + " FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.Companion.ID + "=? AND " + ArtistDatabaseDefinition.Companion.SUBID + "=?;"
        private val COUNT_UNWATCHED_RELEASES_RELEASED = "SELECT COUNT(*) FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " " +
                "WHERE " + ArtistDatabaseDefinition.Companion.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " <= @OFFSET@;"
        private val GET_UNWATCHED_RELEASES_RELEASED = "SELECT * " +
                "FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " " +
                "WHERE " + ArtistDatabaseDefinition.Companion.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " ASC;"
        private val GET_UNWATCHED_RELEASES_TOTAL = "SELECT * " +
                "FROM " + ArtistDatabaseDefinition.Companion.TABLE_RELEASES + " " +
                "WHERE " + ArtistDatabaseDefinition.Companion.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " != '' ORDER BY " + ArtistDatabaseDefinition.Companion.RELEASE_DATE + " ASC;"
    }

    init {
        dbWritable = ArtistDatabaseDefinition(context).writableDatabase
        this.context = context
    }
}