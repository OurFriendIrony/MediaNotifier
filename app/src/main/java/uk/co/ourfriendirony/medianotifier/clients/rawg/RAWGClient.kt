package uk.co.ourfriendirony.medianotifier.clients.rawg

import com.fasterxml.jackson.databind.ObjectMapper
import uk.co.ourfriendirony.medianotifier.clients.AbstractClient
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGet
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearch
import uk.co.ourfriendirony.medianotifier.general.Helper
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import uk.co.ourfriendirony.medianotifier.mediaitem.game.Game
import java.io.IOException

class RAWGClient : AbstractClient() {
    private var payload: String? = null

    @Throws(IOException::class)
    fun queryGame(name: String?): List<MediaItem> {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_GAME_QUERY, "@NAME@", Helper.cleanUrl(name!!))
        )
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val ms = OBJECT_MAPPER.readValue(payload, GameSearch::class.java)
        for (gsr in ms.results!!) {
            mediaItems.add(Game(gsr))
        }
        return mediaItems
    }

    @Throws(IOException::class)
    fun getGame(id: String?): MediaItem {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_GAME_ID, "@ID@", id!!)
        )
        val gg = OBJECT_MAPPER.readValue(payload, GameGet::class.java)
        return Game(gg)
    }

    companion object {
        private const val API_KEY = "89ef6832aaab49bc808264be6ea2c591"
        private const val HOST = "https://api.rawg.io/api/"
        private const val URL_PAGE = "&page=1&page_size=20"
        private const val URL_API = "key=" + API_KEY
        private const val URL_GAME_QUERY = HOST + "games?search=@NAME@" + URL_PAGE + "&exclude_additions" + "&" + URL_API
        private const val URL_GAME_ID = HOST + "games/@ID@" + "?" + URL_API
        private val OBJECT_MAPPER = ObjectMapper()
    }
}