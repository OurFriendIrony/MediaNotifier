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

import uk.co.ourfriendirony.medianotifier.autogen.artist.Artist;
import uk.co.ourfriendirony.medianotifier.autogen.artist.ArtistFinds;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.replaceTokens;

public class DiscogsDatabaseClient {
    private static final String HOST = "https://api.discogs.com/";
    private static final String API_KEY = "DbgZtMTTICHMGzrmeSZZ";
    private static final String API_SECRET = "ZbUHjTuBkPFLaJFaiJIDnFESiqEOequW";
    private static final String URL_AUTH = "?key=" + API_KEY + "&secret=" + API_SECRET;

    private static final String URL_ARTIST_QUERY = HOST + "database/search" + URL_AUTH + "&type=artist&title=@NAME@&per_page=10";
    private static final String URL_ARTIST_ID = HOST + "artists/@ID@" + URL_AUTH;
    private static final String URL_ARTIST_RELEASE_ID = HOST + "artists/@ID@/releases" + URL_AUTH;

    private static final DefaultHttpClient client = new DefaultHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String payload;
    private int statusCode;
    private String headers;

    public List<Artist> queryArtist(String artist) throws IOException {
        httpGetRequest(
                replaceTokens(URL_ARTIST_QUERY, "@NAME@", cleanUrl(artist))
        );
        ArtistFinds query = OBJECT_MAPPER.readValue(payload, ArtistFinds.class);
        return query.getArtists();
    }

    public Artist getArtist(int artistID) throws IOException {
        httpGetRequest(
                replaceTokens(URL_ARTIST_ID, "@ID@", Integer.toString(artistID))
        );
        Artist artist = OBJECT_MAPPER.readValue(payload, Artist.class);
        return artist;
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
