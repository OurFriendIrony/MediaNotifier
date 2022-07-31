package uk.co.ourfriendirony.medianotifier.clients

import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.MusicBrainzClient
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException

class ArtistClient : Client {
    @Throws(IOException::class)
    override fun searchMediaItem(name: String?): List<MediaItem?> {
        return MusicBrainzClient().queryArtist(name)
    }

    @Throws(IOException::class)
    override fun getMediaItem(id: String?): MediaItem {
        return MusicBrainzClient().getArtist(id)
    }
}