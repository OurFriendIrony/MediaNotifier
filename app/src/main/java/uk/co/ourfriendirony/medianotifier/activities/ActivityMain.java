package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.artist.ActivityArtist;
import uk.co.ourfriendirony.medianotifier.activities.artist.ActivityArtistFind;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovie;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieFind;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieNotifications;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTV;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVFind;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVNotifications;
import uk.co.ourfriendirony.medianotifier.async.MovieUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.async.TVShowUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.autogen.tv.TVShow;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;

import static uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getContactEmailIntent;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.getNotificationNumber;

public class ActivityMain extends AppCompatActivity {

    private TVShowDatabase tvShowDatabase;
    private MovieDatabase movieDatabase;
    private ArtistDatabase artistDatabase;

    private TextView main_button_tv_notification;
    private TextView main_button_movie_notification;
    private TextView main_button_artist_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        main_button_tv_notification.setText(getNotificationNumber(tvShowDatabase.countUnwatchedReleasedEpisodes()));
        main_button_movie_notification.setText(getNotificationNumber(movieDatabase.countUnwatchedReleasedMovies()));
        main_button_artist_notification.setText(getNotificationNumber(0));

        main_button_tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTVNotifications.class));
            }
        });
        main_button_movie_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityMovieNotifications.class));
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
        main_button_tv_notification.setText(getNotificationNumber(tvShowDatabase.countUnwatchedReleasedEpisodes()));
        main_button_movie_notification.setText(getNotificationNumber(movieDatabase.countUnwatchedReleasedMovies()));
        main_button_artist_notification.setText(getNotificationNumber(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                return true;

            case R.id.action_contact:
                startActivity(getContactEmailIntent());
                return true;

            case R.id.action_debug:
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_refresh:
                List<TVShow> tvShows = tvShowDatabase.getAllTVShows();
                TVShow[] tvShowsArray = new TVShow[tvShows.size()];
                tvShows.toArray(tvShowsArray); // fill the array
                new TVShowUpdateAsyncTask().execute(tvShowsArray);

                List<Movie> movies = movieDatabase.getAllMovies();
                Movie[] moviesArray = new Movie[movies.size()];
                movies.toArray(moviesArray);
                new MovieUpdateAsyncTask().execute(moviesArray);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
