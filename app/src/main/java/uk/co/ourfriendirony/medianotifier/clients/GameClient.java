package uk.co.ourfriendirony.medianotifier.clients;

import java.io.IOException;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.clients.rawg.RAWGClient;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class GameClient implements Client {

    @Override
    public List<MediaItem> searchMediaItem(String name) throws IOException {
        return new RAWGClient().queryGame(name);
    }

    @Override
    public MediaItem getMediaItem(String id) throws IOException {
        return new RAWGClient().getGame(id);
    }
}
