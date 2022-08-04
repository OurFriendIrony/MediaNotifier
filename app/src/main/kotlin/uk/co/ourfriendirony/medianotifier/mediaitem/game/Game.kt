package uk.co.ourfriendirony.medianotifier.mediaitem.game

import android.database.Cursor
import android.text.TextUtils
import android.util.Log
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGet
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchResult
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabaseDefinition
import uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue
import uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.text.SimpleDateFormat
import java.util.*

class Game : MediaItem {
    override var id: String
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

    constructor(game: GameGet) {
        id = game.id.toString()
        title = game.name
        subtitle = getPlatformsCompressed(game)
        description = game.descriptionRaw
        releaseDate = game.released
        if (game.website != "" && game.website != null) {
            externalLink = game.website
        }
        Log.d("[API GET]", this.toString())
    }

    constructor(game: GameSearchResult) {
        id = game.id.toString()
        title = game.name
        subtitle = getPlatformsCompressed(game)
        releaseDate = game.released
        Log.d("[API SEARCH]", this.toString())
    }

    constructor(cursor: Cursor?) {
        id = getColumnValue(cursor!!, GameDatabaseDefinition.ID)
        subid = getColumnValue(cursor, GameDatabaseDefinition.SUBID)
        title = getColumnValue(cursor, GameDatabaseDefinition.TITLE)
        subtitle = getColumnValue(cursor, GameDatabaseDefinition.SUBTITLE)
        description = getColumnValue(cursor, GameDatabaseDefinition.DESCRIPTION)
        releaseDate = stringToDate(getColumnValue(cursor, GameDatabaseDefinition.RELEASE_DATE))
        externalLink = getColumnValue(cursor, GameDatabaseDefinition.EXTERNAL_URL)
        children = ArrayList()
        Log.d("[DB READ]", this.toString())
    }

    private fun getPlatformsCompressed(game: GameGet): String {
        val platforms: MutableList<String?> = ArrayList()
        if (game.parentPlatforms != null) {
            for (group in game.parentPlatforms!!) {
                platforms.add(group.platform!!.name)
            }
        }
        return TextUtils.join(", ", platforms)
    }

    private fun getPlatformsCompressed(game: GameSearchResult): String {
        val platforms: MutableList<String?> = ArrayList()
        if (game.parentPlatforms != null) {
            for (group in game.parentPlatforms!!) {
                platforms.add(group.platform!!.name)
            }
        }
        return TextUtils.join(", ", platforms)
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
        return "Game: $title > $releaseDateFull"
    }
}