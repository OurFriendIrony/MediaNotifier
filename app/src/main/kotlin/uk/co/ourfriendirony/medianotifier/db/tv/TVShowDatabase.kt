package uk.co.ourfriendirony.medianotifier.db.tv

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
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVEpisode
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVShow
import java.util.*

class TVShowDatabase(context: Context) : Database {
    private val context: Context
    private val dbWritable: SQLiteDatabase
    override val isParent = true

    override fun add(item: MediaItem) {
        for (episode in item.children) {
            insertEpisode(episode, true)
        }
        insert(item)
    }

    override fun update(item: MediaItem) {
        for (episode in item.children) {
            insertEpisode(episode, false)
        }
        insert(item)
    }

    private fun insert(mediaItem: MediaItem) {
        val dbRow = ContentValues()
        dbRow.put(TVShowDatabaseDefinition.ID, mediaItem.id)
        dbRow.put(TVShowDatabaseDefinition.SUBID, mediaItem.subId)
        dbRow.put(TVShowDatabaseDefinition.TITLE, Helper.cleanTitle(mediaItem.title!!))
        dbRow.put(TVShowDatabaseDefinition.SUBTITLE, mediaItem.subtitle)
        dbRow.put(TVShowDatabaseDefinition.EXTERNAL_URL, mediaItem.externalLink)
        dbRow.put(TVShowDatabaseDefinition.RELEASE_DATE, Helper.dateToString(mediaItem.releaseDate))
        dbRow.put(TVShowDatabaseDefinition.DESCRIPTION, mediaItem.description)
        Log.d("[DB INSERT]", "TVShow: $dbRow")
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_TVSHOWS, null, dbRow)
    }

    private fun insertEpisode(episode: MediaItem, isNewTVShow: Boolean) {
        val currentWatchedStatus = getWatchedStatus(episode)
        val dbRow = ContentValues()
        dbRow.put(TVShowDatabaseDefinition.ID, episode.id)
        dbRow.put(TVShowDatabaseDefinition.SUBID, episode.subId)
        dbRow.put(TVShowDatabaseDefinition.TITLE, Helper.cleanTitle(episode.title!!))
        dbRow.put(TVShowDatabaseDefinition.SUBTITLE, episode.subtitle)
        dbRow.put(TVShowDatabaseDefinition.RELEASE_DATE, Helper.dateToString(episode.releaseDate))
        dbRow.put(TVShowDatabaseDefinition.DESCRIPTION, episode.description)
        if (markPlayedIfReleased(isNewTVShow, episode)) {
            dbRow.put(TVShowDatabaseDefinition.PLAYED, Constants.DB_TRUE)
        } else {
            dbRow.put(TVShowDatabaseDefinition.PLAYED, currentWatchedStatus)
        }
        Log.d("[DB INSERT]", "TVEpisode: $dbRow")
        dbWritable.replace(TVShowDatabaseDefinition.TABLE_EPISODES, null, dbRow)
    }

    override fun getWatchedStatus(mediaItem: MediaItem): String {
        val args = arrayOf(mediaItem.id, mediaItem.subId)
        val cursor = dbWritable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, TVShowDatabaseDefinition.PLAYED)
            }
        }
        return playedStatus
    }

    override fun getWatchedStatusAsBoolean(mediaItem: MediaItem): Boolean {
        val args = arrayOf(mediaItem.id, mediaItem.subId)
        val cursor = dbWritable.rawQuery(GET_TVEPISODE_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, TVShowDatabaseDefinition.PLAYED)
            }
        }
        return Constants.DB_TRUE == playedStatus
    }

    override fun deleteAll() {
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + ";")
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + ";")
    }

    override fun delete(id: String) {
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " WHERE " + TVShowDatabaseDefinition.ID + "=" + id + ";")
        dbWritable.execSQL("DELETE FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=" + id + ";")
    }

    private fun buildSubItemFromDB(cursor: Cursor): MediaItem {
        return TVEpisode(cursor)
    }

    private fun buildItemFromDB(cursor: Cursor): MediaItem {
        // TODO: When building an mediaItem, we're currently pulling all children back from the DB to display to the user.
        // TODO: Sometimes we just want to display all the tvshows, and pulling all children for all shows is pretty excessive.
        val id = getColumnValue(cursor, TVShowDatabaseDefinition.ID)
        val episodes: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_TVEPISODES_BY_ID, arrayOf(id)).use { subCursor ->
            while (subCursor.moveToNext()) {
                episodes.add(buildSubItemFromDB(subCursor))
            }
        }
        return TVShow(cursor, episodes)
    }

    override fun countUnplayedReleased(): Int {
        return countPlayed(COUNT_UNWATCHED_EPISODES_RELEASED)
    }

    private fun countPlayed(countQuery: String): Int {
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetTV(context) + " days')"
        val query = Helper.replaceTokens(countQuery, "@OFFSET@", offset)
        val cursor = dbWritable.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    override val unplayedReleased: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_EPISODES_RELEASED, "UNWATCHED RELEASED")
    override val unplayedTotal: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_EPISODES_TOTAL, "UNWATCHED TOTAL")

    override fun getUnplayed(getQuery: String?, logTag: String?): List<MediaItem> {
        val offset =
            "date('now','-" + PropertyHelper.getNotificationDayOffsetTV(context) + " days')"
        val query = Helper.replaceTokens(getQuery!!, "@OFFSET@", offset)

        val parentMediaItems: MutableList<MediaItem> = readAllParentItems() as MutableList<MediaItem>
        dbWritable.rawQuery(query, null).use { cursor ->
            while (cursor.moveToNext()) {
                val mediaItem = buildSubItemFromDB(cursor)
                val parentItem = parentMediaItems.filter { p -> mediaItem.id == p.id }[0]
                parentItem.children.add(mediaItem)
            }
        }
        return parentMediaItems.filter { p -> p.children.isNotEmpty() }
    }

    override fun readAllItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_TVSHOWS, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun readAllParentItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_TVSHOWS, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(TVShow(cursor))
            }
        }
        return mediaItems
    }

    override fun readChildItems(id: String): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val args = arrayOf(id)
        dbWritable.rawQuery(SELECT_TVEPISODES_BY_ID, args).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildSubItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun updatePlayedStatus(mediaItem: MediaItem, playedStatus: String?) {
        val values = ContentValues()
        values.put(TVShowDatabaseDefinition.PLAYED, playedStatus)
        val where: String =
            TVShowDatabaseDefinition.ID + "=? and " + TVShowDatabaseDefinition.SUBID + "=?"
        val whereArgs = arrayOf(mediaItem.id, mediaItem.subId)
        dbWritable.update(TVShowDatabaseDefinition.TABLE_EPISODES, values, where, whereArgs)
    }

    override fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean {
        return isNew && alreadyReleased(mediaItem) && PropertyHelper.getMarkWatchedIfAlreadyReleased(
            context
        )
    }

    // TODO: This is a lazy and unneeded implementation. It should be removed
    override val coreType: String
        get() =// TODO: This is a lazy and unneeded implementation. It should be removed
            "TVShow"

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
        private const val SELECT_TVSHOWS =
            "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_TVSHOWS + " ORDER BY " + TVShowDatabaseDefinition.TITLE + " ASC;"
        private const val SELECT_TVEPISODES_BY_ID =
            "SELECT * FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=? ORDER BY " + TVShowDatabaseDefinition.SUBID + " ASC;"
        private const val GET_TVEPISODE_WATCHED_STATUS =
            "SELECT " + TVShowDatabaseDefinition.PLAYED + " FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " WHERE " + TVShowDatabaseDefinition.ID + "=? AND " + TVShowDatabaseDefinition.SUBID + "=?;"
        private const val COUNT_UNWATCHED_EPISODES_RELEASED =
            "SELECT COUNT(*) FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
                    "WHERE " + TVShowDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + TVShowDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;"
        private const val GET_UNWATCHED_EPISODES_RELEASED = "SELECT * " +
                "FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
                "WHERE " + TVShowDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + TVShowDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + TVShowDatabaseDefinition.RELEASE_DATE + " ASC;"
        private const val GET_UNWATCHED_EPISODES_TOTAL = "SELECT * " +
                "FROM " + TVShowDatabaseDefinition.TABLE_EPISODES + " " +
                "WHERE " + TVShowDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + TVShowDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + TVShowDatabaseDefinition.RELEASE_DATE + " ASC;"
    }

    init {
        dbWritable = TVShowDatabaseDefinition(context).writableDatabase
        this.context = context
    }
}