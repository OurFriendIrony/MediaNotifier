package uk.co.ourfriendirony.medianotifier.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistReleases;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistReleasesRelease;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearch;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearchResult;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Release;

import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.Helper.replaceTokens;

public class DiscogsClient extends AbstractClient {
    private static final String API_KEY = "DbgZtMTTICHMGzrmeSZZ";
    private static final String API_SECRET = "ZbUHjTuBkPFLaJFaiJIDnFESiqEOequW";

    private static final String HOST = "https://api.discogs.com/";
    private static final String URL_AUTH = "?key=" + API_KEY + "&secret=" + API_SECRET;

    private static final String URL_ARTIST_QUERY = HOST + "database/search" + URL_AUTH + "&q=@NAME@&type=artist&per_page=10";
    private static final String URL_ARTIST_ID = HOST + "artists/@ID@" + URL_AUTH;
    private static final String URL_ARTIST_RELEASE_ID = HOST + "artists/@ID@/releases" + URL_AUTH;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;

    public List<MediaItem> queryArtist(String artist) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_QUERY, "@NAME@", cleanUrl(artist))
        );
        List<MediaItem> mediaItems = new ArrayList<>();
        ArtistSearch rawSearch = OBJECT_MAPPER.readValue(payload, ArtistSearch.class);
        for (ArtistSearchResult rawResult : rawSearch.getResults()) {
            // Search mechanism doesn't give you any description so additional call made
            Artist temp = new Artist(rawResult);
            mediaItems.add(getArtist(Integer.parseInt(temp.getId())));
        }
        return mediaItems;
    }

    public MediaItem getArtist(int artistID) throws IOException {
        return getArtist(artistID, false);
    }

    public MediaItem getArtist(int artistID, boolean getReleases) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_ID, "@ID@", Integer.toString(artistID))
        );
        ArtistGet ag = OBJECT_MAPPER.readValue(payload, ArtistGet.class);
        List<MediaItem> releases = new ArrayList<>();
        if (getReleases) {
            releases = getReleases(ag.getId());
        }
        return new Artist(ag, releases);
    }

    public List<MediaItem> getReleases(int artistID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_RELEASE_ID, "@ID@", Integer.toString(artistID))
        );
        List<MediaItem> mediaItems = new ArrayList<>();
        ArtistReleases releases = OBJECT_MAPPER.readValue(payload, ArtistReleases.class);
        for (ArtistReleasesRelease release : releases.getReleases()) {
            mediaItems.add(new Release(release, Integer.toString(artistID)));
        }
        return mediaItems;
    }
}
