package uk.co.ourfriendirony.medianotifier.db

import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

interface Database {
    val unplayedReleased: List<MediaItem>
    val unplayedTotal: List<MediaItem>
    val coreType: String
    val isParent: Boolean

    fun add(item: MediaItem)
    fun update(item: MediaItem)
    fun getWatchedStatus(mediaItem: MediaItem): String
    fun getWatchedStatusAsBoolean(mediaItem: MediaItem): Boolean
    fun deleteAll()
    fun delete(id: String)
    fun countUnplayedReleased(): Int
    fun getUnplayed(getQuery: String?, logTag: String?): List<MediaItem>
    fun readAllItems(): List<MediaItem>
    fun readAllParentItems(): List<MediaItem>
    fun readChildItems(id: String): List<MediaItem>
    fun updatePlayedStatus(mediaItem: MediaItem, playedStatus: String?)
    fun markPlayedIfReleased(isNew: Boolean, mediaItem: MediaItem): Boolean
}