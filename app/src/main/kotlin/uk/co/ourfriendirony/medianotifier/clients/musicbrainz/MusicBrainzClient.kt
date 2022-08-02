package uk.co.ourfriendirony.medianotifier.clients.musicbrainz

import com.fasterxml.jackson.databind.ObjectMapper
import uk.co.ourfriendirony.medianotifier.clients.AbstractClient
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGet
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearch
import uk.co.ourfriendirony.medianotifier.general.Helper
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Release
import java.io.IOException

class MusicBrainzClient : AbstractClient() {
    private var payload: String? = null

    @Throws(IOException::class)
    fun queryArtist(artist: String?): List<MediaItem> {
        payload = httpGetRequest(
            Helper.replaceTokens(URL_ARTIST_QUERY, "@NAME@", Helper.cleanUrl(artist!!))
        )
        val mediaItems: MutableList<MediaItem> = ArrayList()
        try {
            val rawSearch = OBJECT_MAPPER.readValue(payload, ArtistSearch::class.java)
            for (a in rawSearch.artists!!) {
                mediaItems.add(Artist(a))
            }
        } catch (e: Exception) {
            System.err.println(e.message)
        }
        return mediaItems
    }

    @Throws(IOException::class)
    fun getArtist(artistID: String?): MediaItem {
        payload = httpGetRequest(
            Helper.replaceTokens(URL_ARTIST_ID, "@ID@", artistID!!)
        )
        val ag = OBJECT_MAPPER.readValue(payload, ArtistGet::class.java)
        val artist = Artist(ag)
        val releases: MutableList<MediaItem> = ArrayList()
        for (rawRelease in ag.releaseGroups!!) {
            if (isWanted(rawRelease) && hasADate(rawRelease)) {
                releases.add(Release(rawRelease, artist))
            }
        }
        artist.children = releases
        return artist
    }

    private fun hasADate(rawRelease: ArtistGetReleaseGroup): Boolean {
        return rawRelease.firstReleaseDate != null
    }

    private fun isWanted(agrg: ArtistGetReleaseGroup): Boolean {
        val releaseTypes = mutableListOf(agrg.primaryType)
        releaseTypes.addAll(agrg.secondaryTypes!!)
        for (releaseType in releaseTypes) {
            if (RELEASE_TYPES_UNWANTED.contains(releaseType)) {
                return false
            }
        }
        return true
    }

    companion object {
        private const val HOST = "https://musicbrainz.org/ws/2/"
        private const val URL_ARTIST_QUERY = HOST + "artist/?query=artist:@NAME@&fmt=json"
        private const val URL_ARTIST_ID = HOST + "artist/@ID@/?inc=release-groups&fmt=json"
        private const val URL_ARTIST_RELEASE_ID = HOST + "release/@ID@?inc=recordings&fmt=json"
        private val OBJECT_MAPPER = ObjectMapper()
        private val RELEASE_TYPES_UNWANTED: ArrayList<String> = object : ArrayList<String>() {
            init {
                add("Compilation")
                add("EP")
                add("Live")
                add("Single")
                add("Demo")
            }
        }
    }
}