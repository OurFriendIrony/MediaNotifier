package uk.co.ourfriendirony.medianotifier.mediaitem.tv

import android.database.Cursor
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearchResult
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class TVShow : MediaItem {
    override val id: String
    override val title: String?
    override val releaseDate: Date?

    // TODO: fully implement played as an item
    override val played = false
    private var subid = ""
    override var subtitle = ""
    override var description: String? = ""
        private set
    override var externalLink: String? = null
        private set
    override var children: List<MediaItem> = ArrayList()

    constructor(tvShow: TVShowGet) {
        id = tvShow.id.toString()
        title = tvShow.name
        description = tvShow.overview
        releaseDate = tvShow.firstAirDate
        if (tvShow.externalIds != null && tvShow.externalIds!!.imdbId != null) {
            externalLink = IMDB_URL + tvShow.externalIds!!.imdbId
        }
        Log.d("[API GET]", this.toString())
    }

    constructor(item: TVShowSearchResult) {
        id = item.id.toString()
        title = item.name
        description = item.overview
        releaseDate = item.firstAirDate
        Log.d("[API SEARCH]", this.toString())
    }

    @JvmOverloads
    constructor(cursor: Cursor?, episodes: List<MediaItem> = ArrayList()) {
        id = getColumnValue(cursor!!, TVShowDatabaseDefinition.ID)
        subid = getColumnValue(cursor, TVShowDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, TVShowDatabaseDefinition.TITLE)
        subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.SUBTITLE)
        description = getColumnValue(cursor, TVShowDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.RELEASE_DATE))
        externalLink = getColumnValue(cursor, TVShowDatabaseDefinition.EXTERNAL_URL)
        children = episodes
        Log.d("[DB READ]", this.toString())
    }


    override val subId: String?
        get() = subid

    override val releaseDateFull: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate)
        } else MediaItem.Companion.NO_DATE

    override val releaseDateYear: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("yyyy", Locale.UK).format(releaseDate)
        } else MediaItem.Companion.NO_DATE

    override fun countChildren(): Int {
        return children.size
    }

    override fun toString(): String {
        return "TVShow: " + title + " > " + releaseDateFull + " > Episodes " + countChildren()
    }

    companion object {
        private const val IMDB_URL = "http://www.imdb.com/title/"
    }
}