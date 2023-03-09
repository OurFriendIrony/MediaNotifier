package uk.co.ourfriendirony.medianotifier.db.game

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
import uk.co.ourfriendirony.medianotifier.mediaitem.game.Game
import java.util.*

class GameDatabase(context: Context) : Database {
    private val context: Context
    private val dbWritable: SQLiteDatabase
    override val isParent = false

    override fun add(item: MediaItem) {
        insert(item, true)
    }

    override fun update(item: MediaItem) {
        insert(item, false)
    }

    private fun insert(mediaItem: MediaItem, isNewItem: Boolean) {
        val currentWatchedStatus = getWatchedStatus(mediaItem)
        val dbRow = ContentValues()
        dbRow.put(GameDatabaseDefinition.ID, mediaItem.id)
        dbRow.put(GameDatabaseDefinition.SUBID, mediaItem.subId)
        dbRow.put(GameDatabaseDefinition.TITLE, Helper.cleanTitle(mediaItem.title!!))
        dbRow.put(GameDatabaseDefinition.EXTERNAL_URL, mediaItem.externalLink)
        dbRow.put(GameDatabaseDefinition.RELEASE_DATE, Helper.dateToString(mediaItem.releaseDate))
        dbRow.put(GameDatabaseDefinition.DESCRIPTION, mediaItem.description)
        dbRow.put(GameDatabaseDefinition.SUBTITLE, mediaItem.subtitle)
        if (markPlayedIfReleased(isNewItem, mediaItem)) {
            dbRow.put(GameDatabaseDefinition.PLAYED, Constants.DB_TRUE)
        } else {
            dbRow.put(GameDatabaseDefinition.PLAYED, currentWatchedStatus)
        }
        Log.d("[DB INSERT]", "Game: $dbRow")
        dbWritable.replace(GameDatabaseDefinition.TABLE_GAMES, null, dbRow)
    }

    override fun getWatchedStatus(mediaItem: MediaItem): String {
        val args = arrayOf(mediaItem.id)
        val cursor = dbWritable.rawQuery(GET_GAME_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, GameDatabaseDefinition.PLAYED)
            }
        }
        return playedStatus
    }

    override fun getWatchedStatusAsBoolean(mediaItem: MediaItem): Boolean {
        val args = arrayOf(mediaItem.id)
        val cursor = dbWritable.rawQuery(GET_GAME_WATCHED_STATUS, args)
        var playedStatus = Constants.DB_FALSE
        cursor.use { c ->
            while (c.moveToNext()) {
                playedStatus = getColumnValue(c, GameDatabaseDefinition.PLAYED)
            }
        }
        return Constants.DB_TRUE == playedStatus
    }

    override fun deleteAll() {
        dbWritable.execSQL("DELETE FROM " + GameDatabaseDefinition.TABLE_GAMES + ";")
    }

    override fun delete(id: String) {
        dbWritable.execSQL("DELETE FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=" + id + ";")
    }

    override fun countUnplayedReleased(): Int {
        return countUnplayed(COUNT_UNWATCHED_GAMES_RELEASED)
    }

    override val unplayedReleased: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_GAMES_RELEASED, "UNWATCHED RELEASED")
    override val unplayedTotal: List<MediaItem>
        get() = getUnplayed(GET_UNWATCHED_GAMES_TOTAL, "UNWATCHED TOTAL")

    private fun countUnplayed(countQuery: String): Int {
        val offset = "date('now','-" + PropertyHelper.getNotificationDayOffsetGame(context) + " days')"
        val query = Helper.replaceTokens(countQuery, "@OFFSET@", offset)
        val cursor = dbWritable.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    override fun getUnplayed(getQuery: String?, logTag: String?): List<MediaItem> {
        val offset = "date('now','-" + PropertyHelper.getNotificationDayOffsetGame(context) + " days')"
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
        return Game(cursor)
    }

    override fun readAllItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_GAMES, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun readAllParentItems(): List<MediaItem> {
        val mediaItems: MutableList<MediaItem> = ArrayList()
        dbWritable.rawQuery(SELECT_GAMES, null).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(Game(cursor))
            }
        }
        return mediaItems
    }

    override fun readChildItems(id: String): List<MediaItem> {
        // TODO: Games don't have child items. Currently just return the main item as if it was the child until the display is updated
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val args = arrayOf(id)
        dbWritable.rawQuery(SELECT_GAMES_BY_ID, args).use { cursor ->
            while (cursor.moveToNext()) {
                mediaItems.add(buildItemFromDB(cursor))
            }
        }
        return mediaItems
    }

    override fun updatePlayedStatus(mediaItem: MediaItem, playedStatus: String?) {
        val values = ContentValues()
        values.put(GameDatabaseDefinition.PLAYED, playedStatus)
        val where: String = GameDatabaseDefinition.ID + "=?"
        val whereArgs = arrayOf(mediaItem.id)
        dbWritable.update(GameDatabaseDefinition.TABLE_GAMES, values, where, whereArgs)
    }

    override fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean {
        return isNew && alreadyReleased(mediaItem) && PropertyHelper.getMarkWatchedIfAlreadyReleased(context)
    }

    // TODO: This is a lazy and unneeded implementation. It should be removed
    override val coreType: String
        get() =// TODO: This is a lazy and unneeded implementation. It should be removed
            "Game"

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
        private const val SELECT_GAMES =
            "SELECT * FROM " + GameDatabaseDefinition.TABLE_GAMES + " ORDER BY " + GameDatabaseDefinition.TITLE + " ASC;"
        private const val SELECT_GAMES_BY_ID =
            "SELECT * FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=? ORDER BY " + GameDatabaseDefinition.ID + " ASC;"
        private const val GET_GAME_WATCHED_STATUS =
            "SELECT " + GameDatabaseDefinition.PLAYED + " FROM " + GameDatabaseDefinition.TABLE_GAMES + " WHERE " + GameDatabaseDefinition.ID + "=?;"
        private const val COUNT_UNWATCHED_GAMES_RELEASED = "SELECT COUNT(*) FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
                "WHERE " + GameDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@;"
        private const val GET_UNWATCHED_GAMES_RELEASED = "SELECT * " +
                "FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
                "WHERE " + GameDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " <= @OFFSET@ ORDER BY " + GameDatabaseDefinition.RELEASE_DATE + " ASC;"
        private const val GET_UNWATCHED_GAMES_TOTAL = "SELECT * " +
                "FROM " + GameDatabaseDefinition.TABLE_GAMES + " " +
                "WHERE " + GameDatabaseDefinition.PLAYED + "=" + Constants.DB_FALSE + " AND " + GameDatabaseDefinition.RELEASE_DATE + " != '' ORDER BY " + GameDatabaseDefinition.RELEASE_DATE + " ASC;"
    }

    init {
        dbWritable = GameDatabaseDefinition(context).writableDatabase
        this.context = context
    }
}