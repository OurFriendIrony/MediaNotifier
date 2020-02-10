package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Context;
import android.content.Intent;
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
import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier._objects.tv.TVShow;
import uk.co.ourfriendirony.medianotifier.activities.artist.ActivityArtist;
import uk.co.ourfriendirony.medianotifier.activities.artist.ActivityArtistFind;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovie;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieFind;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieUnwatched;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTV;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVFind;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVUnwatched;
import uk.co.ourfriendirony.medianotifier.async.MovieUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.async.TVShowUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.switchTheme;
import static uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getContactEmailIntent;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.getNotificationNumber;

public class ActivityMain extends AppCompatActivity {

    private TVShowDatabase tvShowDatabase;
    private Database movieDatabase;
    private ArtistDatabase artistDatabase;

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

        Button main_button_tv = (Button) findViewById(R.id.main_button_tv);
        Button main_button_movie = (Button) findViewById(R.id.main_button_movie);
        Button main_button_artist = (Button) findViewById(R.id.main_button_artist);

        ImageView tmdbImage = (ImageView) findViewById(R.id.badge_tmdb);
        ImageView discogsImage = (ImageView) findViewById(R.id.badge_discogs);

        main_button_tv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTVFind.class));
            }
        });
        main_button_movie_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityMovieFind.class));
            }
        });
        main_button_artist_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityArtistFind.class));
            }
        });

        main_button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTV.class));
            }
        });
        main_button_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityMovie.class));
            }
        });
        main_button_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityArtist.class));
            }
        });

        main_button_tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTVUnwatched.class));
            }
        });
        main_button_movie_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityMovieUnwatched.class));
            }
        });
        main_button_artist_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        tmdbImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(IntentGenerator.getWebPageIntent("https://www.themoviedb.org/"));
            }
        });
        discogsImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(IntentGenerator.getWebPageIntent("https://www.discogs.com/"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        int numEpisodes = tvShowDatabase.countUnwatchedEpisodesReleased();
        int numMovies = movieDatabase.countUnwatchedReleased();
        int numArtists = 0;

        main_button_tv_notification.setText(getNotificationNumber(numEpisodes));
        main_button_movie_notification.setText(getNotificationNumber(numMovies));
        main_button_artist_notification.setText(getNotificationNumber(numArtists));

        if (numEpisodes > 0)
            main_button_tv_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_on));
        else
            main_button_tv_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_off));

        if (numMovies > 0)
            main_button_movie_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_on));
        else
            main_button_movie_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_off));

        if (numArtists > 0)
            main_button_artist_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_on));
        else
            main_button_artist_notification.setBackground(getResources().getDrawable(R.drawable.button_notification_off));
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
                List<TVShow> tvShows = tvShowDatabase.getAllTVShows();
                TVShow[] tvShowsArray = new TVShow[tvShows.size()];
                tvShows.toArray(tvShowsArray); // fill the array
                new TVShowUpdateAsyncTask().execute(tvShowsArray);

                List<Item> items = movieDatabase.getAll();
                Item[] itemsArray = new Item[items.size()];
                items.toArray(itemsArray);
                new MovieUpdateAsyncTask().execute(itemsArray);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
