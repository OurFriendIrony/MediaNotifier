package uk.co.ourfriendirony.medianotifier.clients

import uk.co.ourfriendirony.medianotifier.clients.rawg.RAWGClient
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException

class GameClient : Client {
    @Throws(IOException::class)
    override fun searchMediaItem(name: String?): List<MediaItem?> {
        return RAWGClient().queryGame(name)
    }

    @Throws(IOException::class)
    override fun getMediaItem(id: String?): MediaItem {
        return RAWGClient().getGame(id)
    }
}