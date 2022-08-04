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
    override fun add(item: MediaItem) {
        for (release in item.children) {
            insertRelease(release, true)
        }
        insert(item)
    }

    override fun update(item: MediaItem) {
        for (release in item.children) {
            insertRelease(release, false)
        }
        insert(item)
    }

    private fun insert(artist: MediaItem) {
        val dbRow = ContentValues()
        dbRow.put(ArtistDatabaseDefinition.ID, artist.id)
        dbRow.put(ArtistDatabaseDefinition.SUBID, artist.subId)
        dbRow.put(ArtistDatabaseDefinition.TITLE, Helper.cleanTitle(artist.title!!))
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, artist.subtitle)
        dbRow.put(ArtistDatabaseDefinition.EXTERNAL_URL, artist.externalLink)
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, Helper.dateToString(artist.releaseDate))
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, artist.description)
        Log.d("[DB INSERT]", "Artist: $dbRow")
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_ARTISTS, null, dbRow)
    }

    private fun insertRelease(release: MediaItem, isNew: Boolean) {
        val currentWatchedStatus = getWatchedStatus(release)
        val dbRow = ContentValues()
        dbRow.put(ArtistDatabaseDefinition.ID, release.id)
        dbRow.put(ArtistDatabaseDefinition.SUBID, release.subId)
        dbRow.put(ArtistDatabaseDefinition.TITLE, Helper.cleanTitle(release.title!!))
        dbRow.put(ArtistDatabaseDefinition.SUBTITLE, release.subtitle)
        dbRow.put(ArtistDatabaseDefinition.RELEASE_DATE, Helper.dateToString(release.releaseDate))
        dbRow.put(ArtistDatabaseDefinition.DESCRIPTION, release.description)
        if (markPlayedIfReleased(isNew, release)) {
            dbRow.put(ArtistDatabaseDefinition.PLAYED, Constants.DB_TRUE)
        } else {
            dbRow.put(ArtistDatabaseDefinition.PLAYED, currentWatchedStatus)
        }
        Log.d("[DB INSERT]", "Release: $dbRow")
        dbWritable.replace(ArtistDatabaseDefinition.TABLE_RELEASES, null, dbRow)
    }

    override fun getWatchedStatus(mediaItem: MediaItem): String {
        val args = arrayOf(mediaItem.id, mediaItem.subId)
        val cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE

        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, ArtistDatabaseDefinition.PLAYED)
            }
        }
        return playedStatus
    }

    override fun getWatchedStatusAsBoolean(mediaItem: MediaItem): Boolean {
        val args = arrayOf(mediaItem.id, mediaItem.subId)
        val cursor = dbWritable.rawQuery(GET_RELEASE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, ArtistDatabaseDefinition.PLAYED)
            }
        }
        return Constants.DB_TRUE == playedStatus
    }

    override fun deleteAll() {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + ";")
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + ";")
    }

    override fun delete(id: String) {
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";")
        dbWritable.execSQL("DELETE FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=\"" + id + "\";")
    }

    private fun buildSubItemFromDB(cursor: Cursor): MediaItem {
        return Release(cursor)
    }

    private fun buildItemFromDB(cursor: Cursor): MediaItem {
        // TODO: When building an mediaItem, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.
        val id = getColumnValue(cursor, ArtistDatabaseDefinition.ID)
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
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetArtist(context) + " days')"
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
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetArtist(context) + " days')"
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
        values.put(ArtistDatabaseDefinition.PLAYED, playedStatus)
        val where: String =
            ArtistDatabaseDefinition.ID + "=? and " + ArtistDatabaseDefinition.SUBID + "=?"
        val whereArgs = arrayOf(mediaItem.id, mediaItem.subId)
        dbWritable.update(ArtistDatabaseDefinition.TABLE_RELEASES, values, where, whereArgs)
    }

    override fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean {
        return isNew && alreadyReleased(mediaItem) && PropertyHelper.getMarkWatchedIfAlreadyReleased(
            context
        )
    }

    // TODO: This is a lazy and unneeded implementation. It should be removed
    override val coreType: String
        get() = "Artist"

    private fun alreadyReleased(mediaItem: MediaItem): Boolean {
        return if (mediaItem.releaseDate == null) {
            true
        } else mediaItem.releaseDate!! < Date()
    }

    private fun getColumnValue(cursor: Cursor, field: String): String {
        val idx = cursor.getColumnIndex(field)
        return cursor.getString(idx)
    }

    companion object {
        private const val SELECT_ARTISTS =
            "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_ARTISTS + " ORDER BY " + ArtistDatabaseDefinition.TITLE + " ASC;"
        private const val SELECT_RELEASES_BY_ID =
            "SELECT * FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;"
        private const val GET_RELEASE_WATCHED_STATUS =
            "SELECT " + ArtistDatabaseDefinition.PLAYED + " FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " WHERE " + ArtistDatabaseDefinition.ID + "=? AND " + ArtistDatabaseDefinition.SUBID + "=?;"
        private const val COUNT_UNWATCHED_RELEASES_RELEASED =
            "SELECT COUNT(*) FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
                    "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;"
        private const val GET_UNWATCHED_RELEASES_RELEASED = "SELECT * " +
                "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
                "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;"
        private const val GET_UNWATCHED_RELEASES_TOTAL = "SELECT * " +
                "FROM " + ArtistDatabaseDefinition.TABLE_RELEASES + " " +
                "WHERE " + ArtistDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + ArtistDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + ArtistDatabaseDefinition.RELEASE_DATE + " ASC;"
    }

    init {
        dbWritable = ArtistDatabaseDefinition(context).writableDatabase
        this.context = context
    }
}