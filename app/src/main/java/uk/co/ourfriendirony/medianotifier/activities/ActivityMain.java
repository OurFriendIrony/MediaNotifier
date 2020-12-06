package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem;
import uk.co.ourfriendirony.medianotifier.clients.ArtistClient;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.GameClient;
import uk.co.ourfriendirony.medianotifier.clients.MovieClient;
import uk.co.ourfriendirony.medianotifier.clients.TVClient;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST;
import static uk.co.ourfriendirony.medianotifier.general.Constants.GAME;
import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;
import static uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW;
import static uk.co.ourfriendirony.medianotifier.general.Helper.getNotificationNumber;
import static uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getContactEmailIntent;

public class ActivityMain extends AppCompatActivity {
    private Database tvShowDatabase;
    private Database movieDatabase;
    private Database artistDatabase;
    private Database gameDatabase;

    private final Client tvShowClient = new TVClient();
    private final Client movieClient = new MovieClient();
    private final Client artistClient = new ArtistClient();
    private final Client gameClient = new GameClient();

    private TextView main_button_tvshow_notification;
    private TextView main_button_movie_notification;
    private TextView main_button_artist_notification;
    private TextView main_button_game_notification;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.main_progress);

        tvShowDatabase = new TVShowDatabase(getApplicationContext());
        movieDatabase = new MovieDatabase(getApplicationContext());
        artistDatabase = new ArtistDatabase(getApplicationContext());
        gameDatabase = new GameDatabase(getApplicationContext());

        FloatingActionButton main_button_tvshow_find = findViewById(R.id.main_button_tv_find);
        FloatingActionButton main_button_movie_find = findViewById(R.id.main_button_movie_find);
        FloatingActionButton main_button_artist_find = findViewById(R.id.main_button_artist_find);
        FloatingActionButton main_button_game_find = findViewById(R.id.main_button_game_find);

        main_button_tvshow_notification = findViewById(R.id.main_button_tv_notification);
        main_button_movie_notification = findViewById(R.id.main_button_movie_notification);
        main_button_artist_notification = findViewById(R.id.main_button_artist_notification);
        main_button_game_notification = findViewById(R.id.main_button_game_notification);

        Button main_button_tvshow_library = findViewById(R.id.main_button_tv);
        Button main_button_movie_library = findViewById(R.id.main_button_movie);
        Button main_button_artist_library = findViewById(R.id.main_button_artist);
        Button main_button_game_library = findViewById(R.id.main_button_game);

        ImageView tmdbImage = findViewById(R.id.badge_tmdb);
        ImageView musicbrainzImage = findViewById(R.id.badge_musicbrainz);
        ImageView rawgImage = findViewById(R.id.badge_rawg);

        prepButton(main_button_tvshow_find, ActivityFind.class, TVSHOW);
        prepButton(main_button_movie_find, ActivityFind.class, MOVIE);
        prepButton(main_button_artist_find, ActivityFind.class, ARTIST);
        prepButton(main_button_game_find, ActivityFind.class, GAME);

        prepButton(main_button_tvshow_library, ActivityLibrary.class, TVSHOW);
        prepButton(main_button_movie_library, ActivityLibrary.class, MOVIE);
        prepButton(main_button_artist_library, ActivityLibrary.class, ARTIST);
        prepButton(main_button_game_library, ActivityLibrary.class, GAME);

        prepButton(main_button_tvshow_notification, ActivityUnplayed.class, TVSHOW);
        prepButton(main_button_movie_notification, ActivityUnplayed.class, MOVIE);
        prepButton(main_button_artist_notification, ActivityUnplayed.class, ARTIST);
        prepButton(main_button_game_notification, ActivityUnplayed.class, GAME);

        tmdbImage.setOnClickListener(view -> {
            startActivity(IntentGenerator.getWebPageIntent("https://www.themoviedb.org/"));
        });
        musicbrainzImage.setOnClickListener(view -> {
            startActivity(IntentGenerator.getWebPageIntent("https://musicbrainz.org/"));
        });
        rawgImage.setOnClickListener(view -> {
            startActivity(IntentGenerator.getWebPageIntent("https://rawg.io/"));
        });
    }

    private void prepButton(View view, final Class c, final String type) {
        view.setOnClickListener(subView -> {
            Intent intent = new Intent(subView.getContext(), c).putExtra(INTENT_KEY, type);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Drawable notificationOn = getResources().getDrawable(R.drawable.button_notification_on);
        final Drawable notificationOff = getResources().getDrawable(R.drawable.button_notification_off);

        int numEpisodes = tvShowDatabase.countUnplayedReleased();
        int numMovies = movieDatabase.countUnplayedReleased();
        int numAlbums = artistDatabase.countUnplayedReleased();
        int numGames = gameDatabase.countUnplayedReleased();

        Drawable tvNotifyBG = (numEpisodes > 0) ? notificationOn : notificationOff;
        Drawable movieNotifyBG = (numMovies > 0) ? notificationOn : notificationOff;
        Drawable albumNotifyBG = (numAlbums > 0) ? notificationOn : notificationOff;
        Drawable gameNotifyBG = (numGames > 0) ? notificationOn : notificationOff;

        main_button_tvshow_notification.setText(getNotificationNumber(numEpisodes));
        main_button_tvshow_notification.setBackground(tvNotifyBG);

        main_button_movie_notification.setBackground(movieNotifyBG);
        main_button_movie_notification.setText(getNotificationNumber(numMovies));

        main_button_artist_notification.setBackground(albumNotifyBG);
        main_button_artist_notification.setText(getNotificationNumber(numAlbums));

        main_button_game_notification.setBackground(gameNotifyBG);
        main_button_game_notification.setText(getNotificationNumber(numGames));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_about).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, ActivitySettings.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_contact) {
            startActivity(getContactEmailIntent());
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            new UpdateMediaItem(getBaseContext(), progressBar, tvShowDatabase, tvShowClient).execute(asArray(tvShowDatabase.readAllItems()));
            new UpdateMediaItem(getBaseContext(), progressBar, movieDatabase, movieClient).execute(asArray(movieDatabase.readAllItems()));
            new UpdateMediaItem(getBaseContext(), progressBar, artistDatabase, artistClient).execute(asArray(artistDatabase.readAllItems()));
            new UpdateMediaItem(getBaseContext(), progressBar, gameDatabase, gameClient).execute(asArray(gameDatabase.readAllItems()));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    private MediaItem[] asArray(List<MediaItem> items) {
        MediaItem[] itemsArray = new MediaItem[items.size()];
        return items.toArray(itemsArray);
    }
}
