package uk.co.ourfriendirony.medianotifier.db.movie

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
import uk.co.ourfriendirony.medianotifier.mediaitem.movie.Movie
import java.util.*

class MovieDatabase(context: Context) : Database {
    private val context: Context
    private val dbWritable: SQLiteDatabase
    override fun add(item: MediaItem) {
        insert(item, true)
    }

    override fun update(item: MediaItem) {
        insert(item, false)
    }

    private fun insert(mediaItem: MediaItem, isNewItem: Boolean) {
        val currentWatchedStatus = getWatchedStatus(mediaItem)
        val dbRow = ContentValues()
        dbRow.put(MovieDatabaseDefinition.ID, mediaItem.id)
        dbRow.put(MovieDatabaseDefinition.SUBID, mediaItem.subId)
        dbRow.put(MovieDatabaseDefinition.TITLE, Helper.cleanTitle(mediaItem.title!!))
        dbRow.put(MovieDatabaseDefinition.EXTERNAL_URL, mediaItem.externalLink)
        dbRow.put(MovieDatabaseDefinition.RELEASE_DATE, Helper.dateToString(mediaItem.releaseDate))
        dbRow.put(MovieDatabaseDefinition.DESCRIPTION, mediaItem.description)
        dbRow.put(MovieDatabaseDefinition.SUBTITLE, mediaItem.subtitle)
        if (markPlayedIfReleased(isNewItem, mediaItem)) {
            dbRow.put(MovieDatabaseDefinition.PLAYED, Constants.DB_TRUE)
        } else {
            dbRow.put(MovieDatabaseDefinition.PLAYED, currentWatchedStatus)
        }
        Log.d("[DB INSERT]", "Movie: $dbRow")
        dbWritable.replace(MovieDatabaseDefinition.TABLE_MOVIES, null, dbRow)
    }

    override fun getWatchedStatus(mediaItem: MediaItem): String {
        val args = arrayOf(mediaItem.id)
        val cursor = dbWritable.rawQuery(GET_MOVIE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, MovieDatabaseDefinition.PLAYED)
            }
        }
        return playedStatus
    }

    override fun getWatchedStatusAsBoolean(mediaItem: MediaItem): Boolean {
        val args = arrayOf(mediaItem.id)
        val cursor = dbWritable.rawQuery(GET_MOVIE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, MovieDatabaseDefinition.PLAYED)
            }
        }
        return Constants.DB_TRUE == playedStatus
    }

    override fun deleteAll() {
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + ";")
    }

    override fun delete(id: String) {
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=" + id + ";")
    }

    override fun countUnplayedReleased(): Int {
        return countUnplayed(COUNT_UNWATCHED_MOVIES_RELEASED)
    }

    override val unplayedReleased: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_MOVIES_RELEASED, "UNWATCHED RELEASED")
    override val unplayedTotal: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_MOVIES_TOTAL, "UNWATCHED TOTAL")

    private fun countUnplayed(countQuery: String): Int {
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetMovie(context) + " days')"
        val query = Helper.replaceTokens(countQuery, "@OFFSET@", offset)
        val cursor = dbWritable.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    override fun getUnplayed(getQuery: String?, logTag: String?): List<MediaItem> {
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetMovie(context) + " days')"
        val query = Helper.replaceTokens(getQuery!!, "@OFFSET@", offset)
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(query, null).use { cursor ->
            while (cursor.moveToNext()) {
                val mediaItem = buildItemFromDB(cursor)
                mediaItems.add(mediaItem)
            }
        }
        return mediaItems
    }

    private fun buildItemFromDB(cursor: Cursor): MediaItem {
        return Movie(cursor)
    }

    override fun readAllItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_MOVIES, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun readAllParentItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_MOVIES, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(Movie(cursor))
            }
        }
        return mediaItems
    }

    override fun readChildItems(id: String): List<MediaItem> {
        // TODO: Movies don't have child items. Currently just return the main item as if it was the child until the display is updated
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val args = arrayOf(id)
        dbWritable.rawQuery(SELECT_MOVIES_BY_ID, args).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun updatePlayedStatus(mediaItem: MediaItem, playedStatus: String?) {
        val values = ContentValues()
        values.put(MovieDatabaseDefinition.PLAYED, playedStatus)
        val where: String = MovieDatabaseDefinition.ID + "=?"
        val whereArgs = arrayOf(mediaItem.id)
        dbWritable.update(MovieDatabaseDefinition.TABLE_MOVIES, values, where, whereArgs)
    }

    override fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean {
        return isNew && alreadyReleased(mediaItem) && PropertyHelper.getMarkWatchedIfAlreadyReleased(
            context
        )
    }

    // TODO: This is a lazy and unneeded implementation. It should be removed
    override val coreType: String
        get() =// TODO: This is a lazy and unneeded implementation. It should be removed
            "Movie"

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
        private const val SELECT_MOVIES =
            "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " ORDER BY " + MovieDatabaseDefinition.TITLE + " ASC;"
        private const val SELECT_MOVIES_BY_ID =
            "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=? ORDER BY " + MovieDatabaseDefinition.ID + " ASC;"
        private const val GET_MOVIE_WATCHED_STATUS =
            "SELECT " + MovieDatabaseDefinition.PLAYED + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.ID + "=?;"
        private const val COUNT_UNWATCHED_MOVIES_RELEASED =
            "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
                    "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;"
        private const val GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
                "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
                "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;"
        private const val GET_UNWATCHED_MOVIES_TOTAL = "SELECT * " +
                "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
                "WHERE " + MovieDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + MovieDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + MovieDatabaseDefinition.RELEASE_DATE + " ASC;"
    }

    init {
        dbWritable = MovieDatabaseDefinition(context).writableDatabase
        this.context = context
    }
}