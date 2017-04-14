package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterMovie;

public class ActivityMovieFind extends AppCompatActivity {
    private TextView findTitle;
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


        findInput = (EditText) findViewById(R.id.find_input);
        findProgressBar = (ProgressBar) findViewById(R.id.find_progress);
        findList = (ListView) findViewById(R.id.find_list_tv);


        findInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    new MovieFindAsyncTask().execute(textView.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        findList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ActivityMovieFind.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int pdToPx(int dimensionDp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }

    private class MovieFindAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String string = strings[0];
            try {
                movies = client.queryMovie(string);
            } catch (IOException e) {
                movies = new ArrayList<>();
            }
            return movies;
        }

        protected void onPostExecute(List<Movie> result) {
            findProgressBar.setVisibility(View.GONE);

            if (movies.size() > 0) {
                ListAdapterMovie adapter = new ListAdapterMovie(getBaseContext(), R.layout.list_item_find, movies);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }
}
