package uk.co.ourfriendirony.medianotifier.clients;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.tmdb.TMDBClient;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class TVClient implements Client {

    @Override
    public List<MediaItem> searchMediaItem(String name) throws IOException {
        return new TMDBClient().queryTVShow(name);
    }

    @Override
    public MediaItem getMediaItem(String id) throws IOException {
        return new TMDBClient().getTVShow(id);
    }
}
