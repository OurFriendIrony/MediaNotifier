package uk.co.ourfriendirony.medianotifier.mediaitem

import java.util.*

interface MediaItem {
    val id: String
    val subId: String?
    val title: String?
    val subtitle: String?
    val description: String?
    val releaseDate: Date?
    val releaseDateFull: String
    val releaseDateYear: String
    var children: MutableList<MediaItem>
    val externalLink: String?
    val played: Boolean

    fun countChildren(): Int

    companion object {
        const val NO_DATE = "Date TBD"
    }
}