package uk.co.ourfriendirony.medianotifier.mediaitem.artist

import android.database.Cursor
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGet
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchArtist
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class Artist : MediaItem {
    override val id: String
    override val title: String?
    override val subtitle = ""
    override val isParent = true

    // TODO: fully implement played as an item
    override val played = false
    private var subid = ""
    override var description: String? = ""
        private set
    override var releaseDate: Date? = null
        private set
    override val externalLink: String? = null
    override var children: MutableList<MediaItem> = ArrayList()

    constructor(artist: ArtistGet) {
        id = artist.id!!
        title = artist.name
        if (artist.disambiguation != null) {
            description = artist.disambiguation
        } else if (artist.area!!.name != null && artist.type != null) {
            description = artist.type + " from " + artist.area!!.name
        }
        if (artist.lifeSpan != null && artist.lifeSpan!!.begin != null) {
            releaseDate = artist.lifeSpan!!.begin
        }
    }

    constructor(artist: ArtistSearchArtist) {
        id = artist.id!!
        title = artist.name
        if (artist.disambiguation != null) {
            description = artist.disambiguation
        } else if (artist.area != null && artist.area!!.name != null && artist.type != null) {
            description = artist.type + " from " + artist.area!!.name
        }
        if (artist.lifeSpan != null && artist.lifeSpan!!.begin != null) {
            releaseDate = artist.lifeSpan!!.begin
        }
        Log.d("[API SEARCH]", this.toString())
    }

    @JvmOverloads
    constructor(cursor: Cursor?, releases: List<MediaItem> = ArrayList()) {
        // Build Artist from DB with children
        id = getColumnValue(cursor!!, ArtistDatabaseDefinition.ID)
        subid = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE)
        description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE))
        children = releases as MutableList<MediaItem>
        Log.d("[DB READ]", this.toString())
    }

    override val subId: String
        get() = subid
    override val releaseDateFull: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate!!)
        } else MediaItem.NO_DATE
    override val releaseDateYear: String
        get() = if (releaseDate != null) {
            SimpleDateFormat("yyyy", Locale.UK).format(releaseDate!!)
        } else MediaItem.NO_DATE

    override fun countChildren(): Int {
        return children.size
    }

    override fun toString(): String {
        return "Artist: " + title + " > " + releaseDateFull + " > Releases " + countChildren()
    }
}