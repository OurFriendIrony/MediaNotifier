package uk.co.ourfriendirony.medianotifier.clients.musicbrainz;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.AbstractClient;
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup;
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearch;
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchArtist;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Artist;
import uk.co.ourfriendirony.medianotifier.mediaitem.artist.Release;

import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.Helper.replaceTokens;

public class MusicBrainzClient extends AbstractClient {
    private static final String HOST = "https://musicbrainz.org/ws/2/";

    private static final String URL_ARTIST_QUERY = HOST + "artist/?query=artist:@NAME@&fmt=json";
    private static final String URL_ARTIST_ID = HOST + "artist/@ID@/?inc=release-groups&fmt=json";
    private static final String URL_ARTIST_RELEASE_ID = HOST + "release/@ID@?inc=recordings&fmt=json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ArrayList<String> RELEASE_TYPES_UNWANTED = new ArrayList<String>() {{
        add("Compilation");
        add("EP");
        add("Live");
        add("Single");
        add("Demo");
    }};
    private String payload;

    public List<MediaItem> queryArtist(String artist) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_QUERY, "@NAME@", cleanUrl(artist))
        );
        List<MediaItem> mediaItems = new ArrayList<>();
        try {
            ArtistSearch rawSearch = OBJECT_MAPPER.readValue(payload, ArtistSearch.class);
            for (ArtistSearchArtist a : rawSearch.getArtists()) {
                mediaItems.add(new Artist(a));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return mediaItems;
    }

    public MediaItem getArtist(String artistID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_ID, "@ID@", artistID)
        );
        ArtistGet ag = OBJECT_MAPPER.readValue(payload, ArtistGet.class);
        Artist artist = new Artist(ag);

        List<MediaItem> releases = new ArrayList<>();
        for (ArtistGetReleaseGroup rawRelease : ag.getReleaseGroups()) {
            if (isWanted(rawRelease) && hasADate(rawRelease)) {
                releases.add(new Release(rawRelease, artist));
            }
        }

        artist.setChildren(releases);
        return artist;
    }

    private boolean hasADate(ArtistGetReleaseGroup rawRelease) {
        return (rawRelease.getFirstReleaseDate() != null);
    }

    private boolean isWanted(ArtistGetReleaseGroup agrg) {
        List<String> releaseTypes = agrg.getSecondaryTypes();
        releaseTypes.add(agrg.getPrimaryType());
        for (String releaseType : releaseTypes) {
            if (RELEASE_TYPES_UNWANTED.contains(releaseType)) {
                return false;
            }
        }
        return true;
    }
}
