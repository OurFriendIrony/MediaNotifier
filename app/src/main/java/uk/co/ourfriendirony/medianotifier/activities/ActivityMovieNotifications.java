package uk.co.ourfriendirony.medianotifier.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryMovie;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTVEpisode;

public class ActivityMovieNotifications extends AppCompatActivity {

    private MovieDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_notifications);
        getSupportActionBar().setTitle(R.string.title_notifications);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        ListView movieList = (ListView) findViewById(R.id.tv_notification_list);

        database = new MovieDatabase(getApplicationContext());
        List<Movie> movies = database.getUnwatchedReleasedMovies();
        if (movies.size() > 0) {
            ListAdapterSummaryMovie listAdapterMovie = new ListAdapterSummaryMovie(getBaseContext(), R.layout.list_item_movie, movies);
            movieList.setAdapter(listAdapterMovie);
        }
    }
}