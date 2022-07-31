package uk.co.ourfriendirony.medianotifier.db

import android.content.Context
import android.util.Log
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase
import uk.co.ourfriendirony.medianotifier.general.Constants

class DatabaseFactory {
    fun getDatabase(c: Context, type: String): Database? {
        return when (type) {
            Constants.TVSHOW -> TVShowDatabase(c)
            Constants.MOVIE -> MovieDatabase(c)
            Constants.ARTIST -> ArtistDatabase(c)
            Constants.GAME -> GameDatabase(c)
            else -> {
                Log.e("[FACTORY DB]", "Unknown Type: $type")
                null
            }
        }
    }
}