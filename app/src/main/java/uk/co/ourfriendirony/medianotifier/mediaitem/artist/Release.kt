package uk.co.ourfriendirony.medianotifier.mediaitem.artist

import android.database.Cursor
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class Release : MediaItem {
    override val id: String
    override val title: String?
    override val releaseDate: Date?

    // TODO: fully implement played as an item
    override val played = false
    override var subId: String? = ""
        private set
    override var subtitle: String? = ""
        private set
    override var description: String? = ""
        private set
    override var externalLink: String? = null
        private set
    override var children: List<MediaItem> = ArrayList()

    constructor(release: ArtistGetReleaseGroup, artist: Artist) {
        id = artist.id!!
        subId = release.id
        if (release.disambiguation!!.isNotEmpty()) {
            title = release.title + " (" + release.disambiguation + ")"
        } else {
            title = release.title
        }
        subtitle = artist.title
        releaseDate = release.firstReleaseDate
        Log.d("[API GET]", this.toString())
    }

    constructor(cursor: Cursor?) {
        id = getColumnValue(cursor!!, ArtistDatabaseDefinition.ID)
        subId = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE)
        subtitle = getColumnValue(cursor, ArtistDatabaseDefinition.SUBTITLE)
        description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE))
        externalLink = getColumnValue(cursor, ArtistDatabaseDefinition.EXTERNAL_URL)
        Log.d("[DB READ]", this.toString())
    }

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
        return "Release: $title > $releaseDateFull"
    }
}