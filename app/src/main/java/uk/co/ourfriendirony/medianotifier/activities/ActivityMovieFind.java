package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryMovie;

public class ActivityMovieFind extends AppCompatActivity {
    private MovieDatabase database;

    private EditText findInput;
    private ProgressBar findProgressBar;
    private ListView findList;
    private List<Movie> movies = new ArrayList<>();
    private MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setTitle(R.string.title_find_movie);

        database = new MovieDatabase(getApplicationContext());

        findInput = (EditText) findViewById(R.id.find_input);
        findProgressBar = (ProgressBar) findViewById(R.id.find_progress);
        findList = (ListView) findViewById(R.id.find_list);

        findInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEND:
                        String input = textView.getText().toString();
                        if (!"".equals(input)) {
                            new MovieFindAsyncTask().execute(input);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        findList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewID = (TextView) view.findViewById(R.id.find_item_id);
                TextView textViewTitle = (TextView) view.findViewById(R.id.find_item_title);
                new MovieAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
            }
        });
    }

    private class MovieFindAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            String string = params[0];
            try {
                movies = client.queryMovie(string);
            } catch (IOException e) {
                movies = new ArrayList<>();
                Log.e(String.valueOf(this.getClass()), "Failed to query: " + e.getMessage());
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            findProgressBar.setVisibility(View.GONE);

            if (movies.size() > 0) {
                ListAdapterSummaryMovie adapter = new ListAdapterSummaryMovie(getBaseContext(), R.layout.list_item_find, movies);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class MovieAddAsyncTask extends AsyncTask<String, Void, String> {
        /* Adds new item to database
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String movieId = params[0];
            String movieTitle = params[1];

            Movie movie;
            try {
                movie = client.getMovie(Integer.parseInt(movieId));
                database.addMovie(movie);
            } catch (IOException e) {
                Log.e(String.valueOf(this.getClass()), "Failed to add: " + e.getMessage());
            }
            return movieTitle;
        }

        @Override
        protected void onPostExecute(String movieTitle) {
            findProgressBar.setVisibility(View.GONE);
            String toastMsg = "'" + movieTitle + "' " + getResources().getString(R.string.toast_db_added);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
