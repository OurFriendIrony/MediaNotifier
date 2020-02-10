package uk.co.ourfriendirony.medianotifier.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier._objects.movie.ItemMovie;
import uk.co.ourfriendirony.medianotifier._objects.tv.ItemTV;
import uk.co.ourfriendirony.medianotifier._objects.tv.ItemTVEpisode;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.search.MovieSearch;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.search.MovieSearchResult;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVSeasonGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVSeasonGetEpisode;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVShowGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.search.TVShowSearch;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.search.TVShowSearchResult;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.replaceTokens;

public class TMDBClient extends AbstractClient {
    private static final String API_KEY = "17e93178aefe463b7d42c6198ba78f30";

    private static final String HOST = "https://api.themoviedb.org/3/";
    private static final String URL_API = "?api_key=" + API_KEY;

    private static final String URL_MOVIE_QUERY = HOST + "search/movie" + URL_API + "&query=@NAME@";
    private static final String URL_MOVIE_ID = HOST + "movie/@ID@" + URL_API;
    private static final String URL_TVSHOW_QUERY = HOST + "search/tv" + URL_API + "&query=@NAME@";
    private static final String URL_TVSHOW_ID = HOST + "tv/@ID@" + URL_API + "&append_to_response=external_ids";
    private static final String URL_TVSHOW_ID_SEASON = HOST + "tv/@ID@/season/@SEASON@" + URL_API;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;

    public List<Item> queryMovie(String name) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_QUERY, "@NAME@", cleanUrl(name))
        );
        List<Item> items = new ArrayList<>();
        MovieSearch ms = OBJECT_MAPPER.readValue(payload, MovieSearch.class);
        for (MovieSearchResult msr : ms.getResults()) {
            items.add(new ItemMovie(msr));
        }
        return items;
    }

    public List<Item> queryTVShow(String name) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_QUERY, "@NAME@", cleanUrl(name))
        );
        TVShowSearch ts = OBJECT_MAPPER.readValue(payload, TVShowSearch.class);
        List<Item> items = new ArrayList<>();
        for (TVShowSearchResult tsr : ts.getResults()) {
            items.add(new ItemTV(tsr));
        }
        return items;
    }

    public Item getMovie(int movieID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_ID, "@ID@", Integer.toString(movieID))
        );
        MovieGet mg = OBJECT_MAPPER.readValue(payload, MovieGet.class);
        return new ItemMovie(mg);
    }

    public Item getTVShow(int tvShowID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID, "@ID@", Integer.toString(tvShowID))
        );
        TVShowGet tg = OBJECT_MAPPER.readValue(payload, TVShowGet.class);
        List<Item> items = new ArrayList<>();
        for (int seasonID = 1; seasonID <= tg.getNumberOfSeasons(); seasonID++) {
            items.addAll(getTVShowEpisodes(tvShowID, seasonID));
        }
        return new ItemTV(tg, items);
    }

    private List<Item> getTVShowEpisodes(int tvShowID, int seasonNo) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID_SEASON,
                        new String[]{"@ID@", "@SEASON@"},
                        new String[]{Integer.toString(tvShowID), Integer.toString(seasonNo)})
        );
        TVSeasonGet tsg = OBJECT_MAPPER.readValue(payload, TVSeasonGet.class);
        List<Item> items = new ArrayList<>();
        for (TVSeasonGetEpisode e : tsg.getTVSeasonGetEpisodes()) {
            items.add(new ItemTVEpisode(e, Integer.toString(tvShowID)));
        }
        return items;
    }
}
