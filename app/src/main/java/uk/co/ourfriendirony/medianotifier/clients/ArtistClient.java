package uk.co.ourfriendirony.medianotifier.clients;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.MusicBrainzClient;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ArtistClient implements Client {

    @Override
    public List<MediaItem> searchMediaItem(String name) throws IOException {
        return new MusicBrainzClient().queryArtist(name);
    }

    @Override
    public MediaItem getMediaItem(String id) throws IOException {
        return new MusicBrainzClient().getArtist(id);
    }
}
