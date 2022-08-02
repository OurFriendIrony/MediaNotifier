package uk.co.ourfriendirony.medianotifier.clients

import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException

interface Client {
    @Throws(IOException::class)
    fun searchMediaItem(name: String?): List<MediaItem?>

    @Throws(IOException::class)
    fun getMediaItem(id: String?): MediaItem
}