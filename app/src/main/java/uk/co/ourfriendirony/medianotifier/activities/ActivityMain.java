package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.UpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.clients.ArtistClient;
import uk.co.ourfriendirony.medianotifier.clients.MovieClient;
import uk.co.ourfriendirony.medianotifier.clients.TVClient;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.switchTheme;
import static uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST;
import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;
import static uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW;
import static uk.co.ourfriendirony.medianotifier.general.Helper.getNotificationNumber;
import static uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getContactEmailIntent;

public class ActivityMain extends AppCompatActivity {
    private TVShowDatabase tvShowDatabase;
    private Database movieDatabase;
    private ArtistDatabase artistDatabase;

    private TVClient tvShowClient = new TVClient();
    private MovieClient movieClient = new MovieClient();
    private ArtistClient artistClient = new ArtistClient();

    private TextView main_button_tv_notification;
    private TextView main_button_movie_notification;
    private TextView main_button_artist_notification;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.setContentView(R.layout.activity_main);

        tvShowDatabase = new TVShowDatabase(getApplicationContext());
        movieDatabase = new MovieDatabase(getApplicationContext());
        artistDatabase = new ArtistDatabase(getApplicationContext());

        FloatingActionButton main_button_tv_find = (FloatingActionButton) findViewById(R.id.main_button_tv_find);
        FloatingActionButton main_button_movie_find = (FloatingActionButton) findViewById(R.id.main_button_movie_find);
        FloatingActionButton main_button_artist_find = (FloatingActionButton) findViewById(R.id.main_button_artist_find);

        main_button_tv_notification = (TextView) findViewById(R.id.main_button_tv_notification);
        main_button_movie_notification = (TextView) findViewById(R.id.main_button_movie_notification);
        main_button_artist_notification = (TextView) findViewById(R.id.main_button_artist_notification);

        Button main_button_tv_library = (Button) findViewById(R.id.main_button_tv);
        Button main_button_movie_library = (Button) findViewById(R.id.main_button_movie);
        Button main_button_artist_library = (Button) findViewById(R.id.main_button_artist);

        ImageView tmdbImage = (ImageView) findViewById(R.id.badge_tmdb);
        ImageView musicbrainzImage = (ImageView) findViewById(R.id.badge_musicbrainz);

        setupFindButtons(main_button_tv_find, main_button_movie_find, main_button_artist_find);
        setupLibraryButtons(main_button_tv_library, main_button_movie_library, main_button_artist_library);
        setupNotificationButtons();

        tmdbImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(IntentGenerator.getWebPageIntent("https://www.themoviedb.org/"));
            }
        });
        musicbrainzImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(IntentGenerator.getWebPageIntent("https://musicbrainz.org/"));
            }
        });
    }

    private void setupLibraryButtons(Button main_button_tv, Button main_button_movie, Button main_button_artist) {
        main_button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityLibrary.class).putExtra(INTENT_KEY, TVSHOW);
                startActivity(intent);
            }
        });
        main_button_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityLibrary.class).putExtra(INTENT_KEY, MOVIE);
                startActivity(intent);
            }
        });
        main_button_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityLibrary.class).putExtra(INTENT_KEY, ARTIST);
                startActivity(intent);
            }
        });
    }

    private void setupFindButtons(FloatingActionButton main_button_tv_find, FloatingActionButton main_button_movie_find, FloatingActionButton main_button_artist_find) {
        main_button_tv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityFind.class).putExtra(INTENT_KEY, TVSHOW);
                startActivity(intent);
            }
        });
        main_button_movie_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityFind.class).putExtra(INTENT_KEY, MOVIE);
                startActivity(intent);
            }
        });
        main_button_artist_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityFind.class).putExtra(INTENT_KEY, ARTIST);
                startActivity(intent);
            }
        });
    }


    private void setupNotificationButtons() {
        main_button_tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityUnwatched.class).putExtra(INTENT_KEY, TVSHOW);
                startActivity(intent);
            }
        });
        main_button_movie_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityUnwatched.class).putExtra(INTENT_KEY, MOVIE);
                startActivity(intent);
            }
        });
        main_button_artist_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityUnwatched.class).putExtra(INTENT_KEY, ARTIST);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Drawable notificationOn = getResources().getDrawable(R.drawable.button_notification_on);
        final Drawable notificationOff = getResources().getDrawable(R.drawable.button_notification_off);

        int numEpisodes = tvShowDatabase.countUnwatchedReleased();
        int numMovies = movieDatabase.countUnwatchedReleased();
        int numAlbums = artistDatabase.countUnwatchedReleased();

        Drawable tvNotifyBG = (numEpisodes > 0) ? notificationOn : notificationOff;
        Drawable movieNotifyBG = (numMovies > 0) ? notificationOn : notificationOff;
        Drawable albumNotifyBG = (numAlbums > 0) ? notificationOn : notificationOff;

        main_button_tv_notification.setText(getNotificationNumber(numEpisodes));
        main_button_tv_notification.setBackground(tvNotifyBG);

        main_button_movie_notification.setBackground(movieNotifyBG);
        main_button_movie_notification.setText(getNotificationNumber(numMovies));

        main_button_artist_notification.setBackground(albumNotifyBG);
        main_button_artist_notification.setText(getNotificationNumber(numAlbums));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_debug).setEnabled(false);
        menu.findItem(R.id.action_logview).setEnabled(false);
        menu.findItem(R.id.action_about).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                return true;

            case R.id.action_theme:
                setTheme(switchTheme(getBaseContext()));
                this.recreate();
                return true;

            case R.id.action_contact:
                startActivity(getContactEmailIntent());
                return true;

            case R.id.action_logview:
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_logviewer, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final TextView logViewer = (TextView) popupWindow.getContentView().findViewById(R.id.popup_text);
                logViewer.setText(getLogcatLog());

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                return true;

            case R.id.action_debug:
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_refresh:
                new UpdateAsyncTask(getApplicationContext(), tvShowDatabase, tvShowClient).execute(asArray(tvShowDatabase.readAllItems()));
                new UpdateAsyncTask(getApplicationContext(), movieDatabase, movieClient).execute(asArray(movieDatabase.readAllItems()));
                new UpdateAsyncTask(getApplicationContext(), artistDatabase, artistClient).execute(asArray(artistDatabase.readAllItems()));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    private MediaItem[] asArray(List<MediaItem> items) {
        MediaItem[] itemsArray = new MediaItem[items.size()];
        return items.toArray(itemsArray);
    }

    @NonNull
    private String getLogcatLog() {
        StringBuilder log = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");
            }
        } catch (Exception e) {
            // do nothing
        }
        return log.toString();
    }
}
