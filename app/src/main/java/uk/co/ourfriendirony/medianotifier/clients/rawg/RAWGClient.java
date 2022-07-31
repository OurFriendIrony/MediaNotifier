package uk.co.ourfriendirony.medianotifier.clients.rawg;

import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.Helper.replaceTokens;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.AbstractClient;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGet;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearch;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchResult;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.game.Game;

public class RAWGClient extends AbstractClient {
    private static final String API_KEY = "89ef6832aaab49bc808264be6ea2c591";

    private static final String HOST = "https://api.rawg.io/api/";
    private static final String URL_PAGE = "&page=1&page_size=20";
    private static final String URL_API = "key=" + API_KEY;
    private static final String URL_GAME_QUERY = HOST + "games?search=@NAME@" + URL_PAGE + "&exclude_additions" + "&" + URL_API;
    private static final String URL_GAME_ID = HOST + "games/@ID@" + "?" + URL_API;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;

    public List<MediaItem> queryGame(String name) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_GAME_QUERY, "@NAME@", cleanUrl(name))
        );
        List<MediaItem> mediaItems = new ArrayList<>();
        GameSearch ms = OBJECT_MAPPER.readValue(payload, GameSearch.class);
        for (GameSearchResult gsr : ms.getResults()) {
            mediaItems.add(new Game(gsr));
        }
        return mediaItems;
    }

    public MediaItem getGame(String id) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_GAME_ID, "@ID@", id)
        );
        GameGet gg = OBJECT_MAPPER.readValue(payload, GameGet.class);
        return new Game(gg);
    }
}
