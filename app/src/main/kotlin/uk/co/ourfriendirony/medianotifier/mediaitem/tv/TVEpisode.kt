package uk.co.ourfriendirony.medianotifier.mediaitem.tv

import android.database.Cursor
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetEpisode
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class TVEpisode : MediaItem {
    override val id: String
    private val subid: String
    override val title: String?
    override val releaseDate: Date?
    override val isParent = false

    // TODO: fully implement played as an item
    override val played = false

    override var subtitle = ""
    override var description: String? = ""
        private set
    override var externalLink: String? = null
        private set
    override var children: MutableList<MediaItem> = ArrayList()

    constructor(episode: TVSeasonGetEpisode, tvShow: TVShow) {
        id = tvShow.id
        subid = formatEpSe(episode)
        title = formatEpSe(episode) + ": " + episode.name
        subtitle = tvShow.title!!
        description = episode.overview
        releaseDate = episode.airDate
        Log.d("[API GET]", this.toString())
    }

    constructor(cursor: Cursor?) {
        id = getColumnValue(cursor!!, TVShowDatabaseDefinition.ID)
        subid = getColumnValue(cursor, TVShowDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, TVShowDatabaseDefinition.TITLE)
        subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.SUBTITLE)
        description = getColumnValue(cursor, TVShowDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.RELEASE_DATE))
        externalLink = getColumnValue(cursor, TVShowDatabaseDefinition.EXTERNAL_URL)
        Log.d("[DB READ]", this.toString())
    }

    private fun formatEpSe(episode: TVSeasonGetEpisode): String {
        return pad(episode.seasonNumber!!, "S") + pad(episode.episodeNumber!!, "E")
    }

    override val subId: String
        get() = subid


    override val releaseDateFull: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate)
        } else MediaItem.NO_DATE

    override val releaseDateYear: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("yyyy", Locale.UK).format(releaseDate)
        } else MediaItem.NO_DATE

    override fun countChildren(): Int {
        return children.size
    }

    override fun toString(): String {
        return "TVEpisode: $subtitle > $title > $releaseDateFull"
    }

    companion object {
        private fun pad(num: Int, prefix: String, size: Int = 2): String {
            return prefix + String.format(Locale.UK, "%0" + size + "d", num)
        }
    }
}