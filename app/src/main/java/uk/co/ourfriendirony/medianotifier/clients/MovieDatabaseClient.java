package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.movie.MDLookupMovie;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MDMovieSummary;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MDQueryMovie;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDLookupTVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDLookupTVShowSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDQueryTVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;

import static uk.co.ourfriendirony.medianotifier.general.UrlHandler.urlCleaner;

public class MovieDatabaseClient {
    private static final String API_KEY = "17e93178aefe463b7d42c6198ba78f30";
    private static final String HOST = "https://api.themoviedb.org/3/";

    private static final String URL_API = "?api_key=" + API_KEY;
    private static final String URL_MOVIE_QUERY = HOST + "search/movie" + URL_API + "&query=@NAME@";
    private static final String URL_MOVIE_ID = HOST + "movie/@ID@" + URL_API;
    private static final String URL_TVSHOW_QUERY = HOST + "search/tv" + URL_API + "&query=@NAME@";
    private static final String URL_TVSHOW_ID = HOST + "tv/@ID@" + URL_API;
    private static final String URL_TVSHOW_ID_SEASON = HOST + "tv/@ID@/season/@SEASON@" + URL_API;

    private static final DefaultHttpClient client = new DefaultHttpClient();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;
    private int statusCode;
    private String url;

    public List<MDMovieSummary> queryMovie(String movie) throws IOException {
        url = URL_MOVIE_QUERY.replace("@NAME@", urlCleaner(movie));
        httpGetRequest(url);
        MDQueryMovie query = OBJECT_MAPPER.readValue(payload, MDQueryMovie.class);
        return query.getResults();
    }

    public MDLookupMovie getMovie(String movieID) throws IOException {
        url = URL_MOVIE_ID.replace("@ID@", urlCleaner(movieID));
        httpGetRequest(url);
        MDLookupMovie movie = OBJECT_MAPPER.readValue(payload, MDLookupMovie.class);
        return movie;
    }

    public List<MDTVShowSummary> queryTVShow(String tvShow) throws IOException {
        url = URL_TVSHOW_QUERY.replace("@NAME@", urlCleaner(tvShow));
        httpGetRequest(url);
        MDQueryTVShow query = OBJECT_MAPPER.readValue(payload, MDQueryTVShow.class);
        return query.getResults();
    }

    public MDLookupTVShow getTVShow(String tvShowID) throws IOException {
        url = URL_TVSHOW_ID.replace("@ID@", urlCleaner(tvShowID));
        httpGetRequest(url);
        MDLookupTVShow tvShow = OBJECT_MAPPER.readValue(payload, MDLookupTVShow.class);
        return tvShow;
    }

    public MDLookupTVShowSeason getTVShowSeason(int tvShowID, int seasonNo) throws IOException {
        url = URL_TVSHOW_ID_SEASON.replace("@ID@", Integer.toString(tvShowID)).replace("@SEASON@", Integer.toString(seasonNo));
        httpGetRequest(url);
        MDLookupTVShowSeason tvShow = OBJECT_MAPPER.readValue(payload, MDLookupTVShowSeason.class);
        return tvShow;
    }

    private void httpGetRequest(String url) throws IOException {
        sleep();
        HttpResponse httpResponse = client.execute(new HttpGet(url));
        payload = getPayload(httpResponse);
        statusCode = getStatusCode(httpResponse);
        Log.v(String.valueOf(this.getClass()), "URL     = " + url);
        Log.v(String.valueOf(this.getClass()), "PAYLOAD = " + payload);
        Log.v(String.valueOf(this.getClass()), "STATUSC = " + statusCode);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPayload(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }
}
