package uk.co.ourfriendirony.medianotifier.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.music.*;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanUrl;

public class MusicBrainzClient {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String HOST = "http://musicbrainz.org/ws/2/";
    private static final String URL_ARTIST_LOOKUP = HOST + "artist/?query=artist:@ARTIST@&fmt=json";
    private static final String URL_GET_ARTIST = HOST + "artist/@MBID@?inc=release-groups&fmt=json&limit=100";
    private static final String URL_GET_RELEASE_GROUP = HOST + "release-group/@MBID@?inc=releases&fmt=json";
    private static final String URL_GET_ALBUM_WITH_TRACKS = HOST + "release/@MBID@?inc=recordings&fmt=json";

    private static final DefaultHttpClient client = new DefaultHttpClient();
    private String payload;
    private int statusCode;

    public List<MBArtist> queryArtist(String artist) throws IOException {
        String url = URL_ARTIST_LOOKUP.replace("@ARTIST@", cleanUrl(artist));
        httpGetRequest(url);
        QueryArtist query = OBJECT_MAPPER.readValue(payload, QueryArtist.class);
        return query.getArtists();
    }

    public void getArtist(String artistMBID) throws IOException {
        String url = URL_GET_ARTIST.replace("@MBID@", cleanUrl(artistMBID));
        httpGetRequest(url);
        LookupArtist artist = OBJECT_MAPPER.readValue(payload, LookupArtist.class);

        for (MBReleaseGroup group : artist.getReleaseGroups()) {
            if (isStudioAlbum(group)) {
                MBRelease album = getAlbumGB(group.getId());

                if (album != null) {
                    System.out.println("Album  >> " + group.getTitle() + " | " + group.getFirstReleaseDate() + " | " + group.getId());
                    getTracks(album.getId());
                }
            }
        }
    }

    public MBRelease getAlbumGB(String id) throws IOException {
        String url = URL_GET_RELEASE_GROUP.replace("@MBID@", cleanUrl(id));
        httpGetRequest(url);
        LookupReleaseGroup releaseGroup = OBJECT_MAPPER.readValue(payload, LookupReleaseGroup.class);

        System.out.println("Group  > " + releaseGroup.getTitle() + " " + releaseGroup.getId() + " " + payload);
        for (MBRelease albumRelease : releaseGroup.getReleases()) {
            if ("GB".equals(albumRelease.getCountry())) {
                return albumRelease;
            }
        }
        return null;
    }

    private void getTracks(String id) throws IOException {
        String url = URL_GET_ALBUM_WITH_TRACKS.replace("@MBID@", cleanUrl(id));
        httpGetRequest(url);
        LookupAlbum album = OBJECT_MAPPER.readValue(payload, LookupAlbum.class);

        for (MBMedium medium : album.getMedia()) {
            for (MBTrack track : medium.getTracks()) {
                System.out.println("Track  >>> " + (medium.getPosition()) + "." + track.getNumber() + " | " + track.getTitle());
            }
        }
        System.out.println();
    }

    private boolean isStudioAlbum(MBReleaseGroup group) {
        if (!"Album".equals(group.getPrimaryType()))
            return false;
        if (group.getSecondaryTypes().size() > 0)
            return false;
        return true;
    }

    private void httpGetRequest(String url) throws IOException {
        sleep();
        HttpResponse httpResponse = client.execute(new HttpGet(url));
        payload = getPayload(httpResponse);
        statusCode = getStatusCode(httpResponse);
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
