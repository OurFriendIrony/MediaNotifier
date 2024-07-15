package uk.co.ourfriendirony.medianotifier.general

object Constants {
    const val DB_TRUE = "1"
    const val DB_FALSE = "0"
    const val INTENT_KEY = "ITEM"
    const val MOVIE = "Movie"
    const val TVSHOW = "TV Show"
    const val ARTIST = "Artist"
    const val GAME = "Game"
    const val QUEUE_FILENAME = "media.queue"


    private const val LOGP = "[OFI]"
    const val LOGLABEL_DBREAD = "$LOGP DB READ"

    const val LOGLABEL_APISEARCH = "$LOGP API SEARCH"
    const val LOGLABEL_APIGET = "$LOGP API GET"
    const val LOGLABEL_HEY = "$LOGP HEYHEY"
    const val LOGLABEL_SERVICE = "$LOGP QueueService"
    const val LOGLABEL_DEBUG = "$LOGP DEBUG"
}