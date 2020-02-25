package uk.co.ourfriendirony.medianotifier.clients;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public interface Client {
    List<MediaItem> searchMediaItem(String name) throws IOException;

    MediaItem getMediaItem(String id) throws IOException;
}
