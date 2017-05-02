package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MovieFinds;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShowFinds;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.replaceTokens;

public class MovieDatabaseClient {
    private static final String API_KEY = "17e93178aefe463b7d42c6198ba78f30";
    private static final String HOST = "https://api.themoviedb.org/3/";

    private static final String URL_API = "?api_key=" + API_KEY;
    private static final String URL_MOVIE_QUERY = HOST + "search/movie" + URL_API + "&query=@NAME@";
    private static final String URL_MOVIE_ID = HOST + "movie/@ID@" + URL_API;
    private static final String URL_TVSHOW_QUERY = HOST + "search/tv" + URL_API + "&query=@NAME@";
    private static final String URL_TVSHOW_ID = HOST + "tv/@ID@" + URL_API + "&append_to_response=external_ids";
    private static final String URL_TVSHOW_ID_SEASON = HOST + "tv/@ID@/season/@SEASON@" + URL_API;

    private static final DefaultHttpClient client = new DefaultHttpClient();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;
    private int statusCode;
    private String headers;

    public List<Movie> queryMovie(String movie) throws IOException {
        httpGetRequest(
                replaceTokens(URL_MOVIE_QUERY, "@NAME@", cleanUrl(movie))
        );
        MovieFinds query = OBJECT_MAPPER.readValue(payload, MovieFinds.class);
        return query.getMovies();
    }

    public List<TVShow> queryTVShow(String tvShow) throws IOException {
        httpGetRequest(
                replaceTokens(URL_TVSHOW_QUERY, "@NAME@", cleanUrl(tvShow))
        );
        TVShowFinds query = OBJECT_MAPPER.readValue(payload, TVShowFinds.class);
        return query.getTVShowsWithDates();
    }

    public Movie getMovie(int movieID) throws IOException {
        httpGetRequest(
                replaceTokens(URL_MOVIE_ID, "@ID@", Integer.toString(movieID))
        );
        Movie movie = OBJECT_MAPPER.readValue(payload, Movie.class);
        return movie;
    }

    public TVShow getTVShow(int tvShowID) throws IOException {
        httpGetRequest(
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
        httpGetRequest(
                replaceTokens(URL_TVSHOW_ID_SEASON,
                        new String[]{"@ID@", "@SEASON@"},
                        new String[]{Integer.toString(tvShowID), Integer.toString(seasonNo)})
        );
        TVSeason tvShow = OBJECT_MAPPER.readValue(payload, TVSeason.class);
        return tvShow;
    }

    private void httpGetRequest(String url) throws IOException {

        while (true) {
            HttpResponse httpResponse = client.execute(new HttpGet(url));
            payload = getPayload(httpResponse);
            statusCode = getStatusCode(httpResponse);
            headers = getHeaders(httpResponse);

            logResponse(url);
            if (statusCode == 200) {
                break;
            } else {
                sleep(1);
            }
        }
    }

    private void logResponse(String url) {
        Log.v(String.valueOf(this.getClass()), "URL     = " + url);
        Log.v(String.valueOf(this.getClass()), "PAYLOAD = " + payload);
        Log.v(String.valueOf(this.getClass()), "HEADERS = " + headers);
        Log.v(String.valueOf(this.getClass()), "STATUSC = " + statusCode);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPayload(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private String getHeaders(HttpResponse response) {
        StringBuilder x = new StringBuilder();
        for (Header header : response.getAllHeaders()) {
            x.append(header.getName() + ":" + header.getValue() + " | ");
        }
        return x.toString();
    }

    private int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }
}
