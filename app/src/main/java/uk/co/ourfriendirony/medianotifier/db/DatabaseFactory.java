package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.util.Log;

import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

import static uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST;
import static uk.co.ourfriendirony.medianotifier.general.Constants.GAME;
import static uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW;

public class DatabaseFactory {
    public Database getDatabase(Context c, String type) {
        switch (type) {
            case TVSHOW:
                return new TVShowDatabase(c);
            case MOVIE:
                return new MovieDatabase(c);
            case ARTIST:
                return new ArtistDatabase(c);
            case GAME:
                return new GameDatabase(c);
            default:
                Log.e("[FACTORY DB]", "Unknown Type: " + type);
                return null;
        }
    }
}
