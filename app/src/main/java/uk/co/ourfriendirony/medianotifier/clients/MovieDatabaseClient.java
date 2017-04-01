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
    private String headers;

    public List<MDMovieSummary> queryMovie(String movie) throws IOException {
        httpGetRequest(URL_MOVIE_QUERY
                .replace("@NAME@", urlCleaner(movie))
        );
        MDQueryMovie query = OBJECT_MAPPER.readValue(payload, MDQueryMovie.class);
        return query.getResults();
    }

    public List<MDTVShowSummary> queryTVShow(String tvShow) throws IOException {
        httpGetRequest(URL_TVSHOW_QUERY
                .replace("@NAME@", urlCleaner(tvShow))
        );
        MDQueryTVShow query = OBJECT_MAPPER.readValue(payload, MDQueryTVShow.class);
        return query.getResults();
    }

    public MDLookupMovie getMovie(int movieID) throws IOException {
        httpGetRequest(URL_MOVIE_ID
                .replace("@ID@", Integer.toString(movieID))
        );
        MDLookupMovie movie = OBJECT_MAPPER.readValue(payload, MDLookupMovie.class);
        return movie;
    }

    public MDLookupTVShow getTVShow(int tvShowID) throws IOException {
        httpGetRequest(URL_TVSHOW_ID
                .replace("@ID@", Integer.toString(tvShowID))
        );
        MDLookupTVShow tvShow = OBJECT_MAPPER.readValue(payload, MDLookupTVShow.class);
        for (int i = tvShow.getSeasons().size()-1; i >= 0; i--) {
            if (tvShow.getSeasons().get(i).getSeasonNumber() > 0) {
                Log.v(String.valueOf(this.getClass()), "no > 0 ");
                MDLookupTVShowSeason lookupSeason = getTVShowSeason(tvShowID, tvShow.getSeasons().get(i).getSeasonNumber());
                tvShow.getSeasons().get(i).setEpisodes(lookupSeason.getEpisodes());
                tvShow.getSeasons().get(i).setEpisodeCount(lookupSeason.getEpisodes().size());
            } else {
                tvShow.getSeasons().remove(i);
            }
        }
        return tvShow;
    }

    public MDLookupTVShowSeason getTVShowSeason(int tvShowID, int seasonNo) throws IOException {
        httpGetRequest(URL_TVSHOW_ID_SEASON
                .replace("@ID@", Integer.toString(tvShowID))
                .replace("@SEASON@", Integer.toString(seasonNo))
        );
        MDLookupTVShowSeason tvShow = OBJECT_MAPPER.readValue(payload, MDLookupTVShowSeason.class);
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
