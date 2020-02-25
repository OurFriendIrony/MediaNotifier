package uk.co.ourfriendirony.medianotifier.clients.tmdb;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.AbstractClient;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGet;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearch;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearchResult;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGet;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetEpisode;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGet;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearch;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearchResult;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.mediaitem.movie.Movie;
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVEpisode;
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVShow;

import static uk.co.ourfriendirony.medianotifier.general.Helper.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.Helper.replaceTokens;

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

    public List<MediaItem> queryMovie(String name) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_QUERY, "@NAME@", cleanUrl(name))
        );
        List<MediaItem> mediaItems = new ArrayList<>();
        MovieSearch ms = OBJECT_MAPPER.readValue(payload, MovieSearch.class);
        for (MovieSearchResult msr : ms.getResults()) {
            mediaItems.add(new Movie(msr));
        }
        return mediaItems;
    }

    public List<MediaItem> queryTVShow(String name) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_QUERY, "@NAME@", cleanUrl(name))
        );
        TVShowSearch ts = OBJECT_MAPPER.readValue(payload, TVShowSearch.class);
        List<MediaItem> mediaItems = new ArrayList<>();
        for (TVShowSearchResult tsr : ts.getResults()) {
            mediaItems.add(new TVShow(tsr));
        }
        return mediaItems;
    }

    public MediaItem getMovie(String movieID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_ID, "@ID@", movieID)
        );
        MovieGet mg = OBJECT_MAPPER.readValue(payload, MovieGet.class);
        return new Movie(mg);
    }

    public MediaItem getTVShow(String tvShowID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID, "@ID@", tvShowID)
        );
        TVShowGet tg = OBJECT_MAPPER.readValue(payload, TVShowGet.class);
        List<MediaItem> mediaItems = new ArrayList<>();
        for (int seasonID = 1; seasonID <= tg.getNumberOfSeasons(); seasonID++) {
            mediaItems.addAll(getTVShowEpisodes(tvShowID, seasonID));
        }
        return new TVShow(tg, mediaItems);
    }

    private List<MediaItem> getTVShowEpisodes(String tvShowID, int seasonNo) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID_SEASON,
                        new String[]{"@ID@", "@SEASON@"},
                        new String[]{tvShowID, Integer.toString(seasonNo)})
        );
        TVSeasonGet tsg = OBJECT_MAPPER.readValue(payload, TVSeasonGet.class);
        List<MediaItem> mediaItems = new ArrayList<>();
        for (TVSeasonGetEpisode e : tsg.getTVSeasonGetEpisodes()) {
            mediaItems.add(new TVEpisode(e, tvShowID));
        }
        return mediaItems;
    }
}
