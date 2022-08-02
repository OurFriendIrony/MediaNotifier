package uk.co.ourfriendirony.medianotifier.clients

import android.util.Log
import uk.co.ourfriendirony.medianotifier.general.Constants

class ClientFactory {
    fun getClient(type: String): Client? {
        return when (type) {
            Constants.TVSHOW -> TVClient()
            Constants.MOVIE -> MovieClient()
            Constants.ARTIST -> ArtistClient()
            Constants.GAME -> GameClient()
            else -> {
                Log.e("[FACTORY CLIENT]", "Unknown Type: $type")
                null
            }
        }
    }
}