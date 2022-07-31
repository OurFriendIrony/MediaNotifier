package uk.co.ourfriendirony.medianotifier.clients.tmdb

import com.fasterxml.jackson.databind.ObjectMapper
import uk.co.ourfriendirony.medianotifier.clients.AbstractClient
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearch
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearch
import uk.co.ourfriendirony.medianotifier.general.Helper
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import uk.co.ourfriendirony.medianotifier.mediaitem.movie.Movie
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVEpisode
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVShow
import java.io.IOException

class TMDBClient : AbstractClient() {
    private var payload: String? = null

    @Throws(IOException::class)
    fun queryMovie(name: String?): List<MediaItem> {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_MOVIE_QUERY, "@NAME@", Helper.cleanUrl(name!!))
        )
        val mediaItems: MutableList<MediaItem> = ArrayList()
        val ms = OBJECT_MAPPER.readValue(payload, MovieSearch::class.java)
        for (msr in ms.results!!) {
            mediaItems.add(Movie(msr))
        }
        return mediaItems
    }

    @Throws(IOException::class)
    fun queryTVShow(name: String?): List<MediaItem> {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_TVSHOW_QUERY, "@NAME@", Helper.cleanUrl(name!!))
        )
        val ts = OBJECT_MAPPER.readValue(payload, TVShowSearch::class.java)
        val mediaItems: MutableList<MediaItem> = ArrayList()
        for (tsr in ts.results!!) {
            mediaItems.add(TVShow(tsr))
        }
        return mediaItems
    }

    @Throws(IOException::class)
    fun getMovie(movieID: String?): MediaItem {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_MOVIE_ID, "@ID@", movieID!!)
        )
        val mg = OBJECT_MAPPER.readValue(payload, MovieGet::class.java)
        return Movie(mg)
    }

    @Throws(IOException::class)
    fun getTVShow(tvShowID: String?): MediaItem {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_TVSHOW_ID, "@ID@", tvShowID!!)
        )
        val tg = OBJECT_MAPPER.readValue(payload, TVShowGet::class.java)
        val tvShow = TVShow(tg)
        val mediaItems: MutableList<MediaItem> = ArrayList()
        for (seasonID in 1..tg.numberOfSeasons!!) {
            mediaItems.addAll(getTVShowEpisodes(tvShow, seasonID))
        }
        tvShow.children = mediaItems
        return tvShow
    }

    @Throws(IOException::class)
    private fun getTVShowEpisodes(tvShow: TVShow, seasonNo: Int): List<MediaItem> {
        payload = httpGetRequest(
                Helper.replaceTokens(URL_TVSHOW_ID_SEASON, arrayOf("@ID@", "@SEASON@"), arrayOf(tvShow.id, Integer.toString(seasonNo)))
        )
        val tsg = OBJECT_MAPPER.readValue(payload, TVSeasonGet::class.java)
        val mediaItems: MutableList<MediaItem> = ArrayList()
        for (e in tsg.tvSeasonGetEpisodes!!) {
            mediaItems.add(TVEpisode(e, tvShow))
        }
        return mediaItems
    }

    companion object {
        private const val API_KEY = "17e93178aefe463b7d42c6198ba78f30"
        private const val HOST = "https://api.themoviedb.org/3/"
        private const val URL_API = "?api_key=" + API_KEY
        private const val URL_MOVIE_QUERY = HOST + "search/movie" + URL_API + "&query=@NAME@"
        private const val URL_MOVIE_ID = HOST + "movie/@ID@" + URL_API
        private const val URL_TVSHOW_QUERY = HOST + "search/tv" + URL_API + "&query=@NAME@"
        private const val URL_TVSHOW_ID = HOST + "tv/@ID@" + URL_API + "&append_to_response=external_ids"
        private const val URL_TVSHOW_ID_SEASON = HOST + "tv/@ID@/season/@SEASON@" + URL_API
        private val OBJECT_MAPPER = ObjectMapper()
    }
}