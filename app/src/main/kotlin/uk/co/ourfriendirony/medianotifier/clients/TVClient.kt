package uk.co.ourfriendirony.medianotifier.clients

import uk.co.ourfriendirony.medianotifier.clients.tmdb.TMDBClient
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException

class TVClient : Client {
    @Throws(IOException::class)
    override fun searchMediaItem(name: String?): List<MediaItem?> {
        return TMDBClient().queryTVShow(name)
    }

    @Throws(IOException::class)
    override fun getMediaItem(id: String?): MediaItem {
        return TMDBClient().getTVShow(id)
    }
}