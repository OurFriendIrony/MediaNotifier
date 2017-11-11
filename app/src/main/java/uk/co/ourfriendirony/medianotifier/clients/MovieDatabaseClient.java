package uk.co.ourfriendirony.medianotifier.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MovieFinds;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShowFinds;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.replaceTokens;

public class MovieDatabaseClient extends AbstractClient {
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

    public List<Movie> queryMovie(String movie) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_QUERY, "@NAME@", cleanUrl(movie))
        );
        MovieFinds query = OBJECT_MAPPER.readValue(payload, MovieFinds.class);
        return query.getMovies();
    }

    public List<TVShow> queryTVShow(String tvShow) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_QUERY, "@NAME@", cleanUrl(tvShow))
        );
        TVShowFinds query = OBJECT_MAPPER.readValue(payload, TVShowFinds.class);
        return query.getTVShowsWithDates();
    }

    public Movie getMovie(int movieID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_MOVIE_ID, "@ID@", Integer.toString(movieID))
        );
        Movie movie = OBJECT_MAPPER.readValue(payload, Movie.class);
        return movie;
    }

    public TVShow getTVShow(int tvShowID) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID, "@ID@", Integer.toString(tvShowID))
        );
        TVShow tvShow = OBJECT_MAPPER.readValue(payload, TVShow.class);
        for (int i = tvShow.getSeasons().size() - 1; i >= 0; i--) {
            if (tvShow.getSeasons().get(i).getSeasonNumber() > 0) {
                TVSeason season = getTVShowSeason(tvShowID, tvShow.getSeasons().get(i).getSeasonNumber());
                tvShow.getSeasons().get(i).setEpisodes(season.getEpisodes());
            } else {
                tvShow.getSeasons().remove(i);
            }
        }
        return tvShow;
    }

    public TVSeason getTVShowSeason(int tvShowID, int seasonNo) throws IOException {
        payload = httpGetRequest(
                replaceTokens(URL_TVSHOW_ID_SEASON,
                        new String[]{"@ID@", "@SEASON@"},
                        new String[]{Integer.toString(tvShowID), Integer.toString(seasonNo)})
        );
        TVSeason tvShow = OBJECT_MAPPER.readValue(payload, TVSeason.class);
        return tvShow;
    }
}
