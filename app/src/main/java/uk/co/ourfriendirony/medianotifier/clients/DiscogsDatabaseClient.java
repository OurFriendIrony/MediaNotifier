package uk.co.ourfriendirony.medianotifier.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier._objects.artist.ItemArtist;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearch;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearchResult;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.replaceTokens;

public class DiscogsDatabaseClient extends AbstractClient {
    private static final String API_KEY = "DbgZtMTTICHMGzrmeSZZ";
    private static final String API_SECRET = "ZbUHjTuBkPFLaJFaiJIDnFESiqEOequW";

    private static final String HOST = "https://api.discogs.com/";
    private static final String URL_AUTH = "?key=" + API_KEY + "&secret=" + API_SECRET;

    private static final String URL_ARTIST_QUERY = HOST + "database/search" + URL_AUTH + "&type=artist&title=@NAME@&per_page=10";
    private static final String URL_ARTIST_ID = HOST + "artists/@ID@" + URL_AUTH;
    private static final String URL_ARTIST_RELEASE_ID = HOST + "artists/@ID@/releases" + URL_AUTH;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;

    public List<Item> queryArtist(String artist) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_QUERY, "@NAME@", cleanUrl(artist))
        );
        List<Item> items = new ArrayList<>();
        ArtistSearch rawSearch = OBJECT_MAPPER.readValue(payload, ArtistSearch.class);
        for (ArtistSearchResult rawResult : rawSearch.getResults()) {
            items.add(new ItemArtist(rawResult));
        }
        return items;
    }

    public Item getArtist(int artistID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_ARTIST_ID, "@ID@", Integer.toString(artistID))
        );
        ArtistGet artist = OBJECT_MAPPER.readValue(payload, ArtistGet.class);
        return new ItemArtist(artist);
    }
}
