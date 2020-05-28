package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import static uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST;
import static uk.co.ourfriendirony.medianotifier.general.Constants.GAME;
import static uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW;

public class ClientFactory {
    public Client getClient(String type) {
        switch (type) {
            case TVSHOW:
                return new TVClient();
            case MOVIE:
                return new MovieClient();
            case ARTIST:
                return new ArtistClient();
            case GAME:
                return new GameClient();
            default:
                Log.e("[FACTORY CLIENT]", "Unknown Type: " + type);
                return null;
        }
    }
}
