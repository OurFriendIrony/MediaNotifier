package uk.co.ourfriendirony.medianotifier.activities.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import java.util.Collections;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.movie.Movie;
import uk.co.ourfriendirony.medianotifier.async.MovieUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryMovie;

public class ActivityMovie extends AppCompatActivity {

    private Spinner movieSpinner;
    private ListView movieList;
    private List<Movie> movies;
    private ProgressBar loadPageProgressBar;
    private int currentMoviePosition;
    private MovieDatabase database;
    private MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_movie);
        super.setContentView(R.layout.activity_movie);

        database = new MovieDatabase(getApplicationContext());

        movieSpinner = (Spinner) findViewById(R.id.movie_spinner);
        movieList = (ListView) findViewById(R.id.movie_list);
        loadPageProgressBar = (ProgressBar) findViewById(R.id.movie_progress);
        new MovieListAsyncTask().execute();

        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int moviePosition, long id) {
                displayMovies(moviePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Movie currentMovie = movies.get(currentMoviePosition);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new MovieUpdateAsyncTask().execute(currentMovie);
                this.recreate();
                return true;

            case R.id.action_remove:
                database.deleteMovie(currentMovie.getId());
                this.recreate();
                return true;

            case R.id.action_imdb:
                Intent intent = IntentGenerator.getWebPageIntent("http://www.imdb.com/title/" + currentMovie.getImdbId() + "/");
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayMovies() {
        if (movies.size() > 0) {
            ListAdapterSummaryMovie listAdapterSummaryMovie = new ListAdapterSummaryMovie(getBaseContext(), R.layout.list_item_movie_title, movies);
            movieSpinner.setAdapter(listAdapterSummaryMovie);
            displayMovies(0);
        }
    }

    private void displayMovies(int moviePosition) {
        currentMoviePosition = moviePosition;
        ListAdapterSummaryMovie movieListAdapter = new ListAdapterSummaryMovie(getBaseContext(), R.layout.list_item_movie, Collections.singletonList(movies.get(moviePosition)));
        movieList.setAdapter(movieListAdapter);
    }

    private class MovieListAsyncTask extends AsyncTask<String, Void, Void> {
        /* Responsible for retrieving all tv shows
         * and displaying them
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadPageProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            movies = database.getAllMovies();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            loadPageProgressBar.setVisibility(View.GONE);
            displayMovies();
        }
    }
}
