package uk.co.ourfriendirony.medianotifier.mediaitem.movie

import android.database.Cursor
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearchResult
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class Movie : MediaItem {
    override var id: String
    override val title: String?
    override val releaseDate: Date?

    // TODO: fully implement played as an item
    override val played = false
    private var subid = ""
    override var subtitle: String? = ""
        private set
    override var description: String? = ""
        private set
    override var externalLink: String? = null
        private set
    override var children: MutableList<MediaItem> = ArrayList()

    constructor(movie: MovieGet) {
        id = movie.id.toString()
        title = movie.title
        if (movie.belongsToCollection != null) {
            subtitle = movie.belongsToCollection!!.name
        }
        description = movie.overview
        releaseDate = movie.releaseDate
        if (movie.imdbId != null) {
            externalLink = IMDB_URL + movie.imdbId
        }
        Log.d("[API GET]", this.toString())
    }

    constructor(movie: MovieSearchResult) {
        id = movie.id.toString()
        title = movie.title
        description = movie.overview
        releaseDate = movie.releaseDate
        Log.d("[API SEARCH]", this.toString())
    }

    constructor(cursor: Cursor?) {
        id = getColumnValue(cursor!!, MovieDatabaseDefinition.ID)
        subid = getColumnValue(cursor, MovieDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, MovieDatabaseDefinition.TITLE)
        subtitle = getColumnValue(cursor, MovieDatabaseDefinition.SUBTITLE)
        description = getColumnValue(cursor, MovieDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, MovieDatabaseDefinition.RELEASE_DATE))
        externalLink = getColumnValue(cursor, MovieDatabaseDefinition.EXTERNAL_URL)
        children = ArrayList()
        Log.d("[DB READ]", this.toString())
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
        return "Movie: $title > $releaseDateFull"
    }

    companion object {
        private const val IMDB_URL = "http://www.imdb.com/title/"
    }
}