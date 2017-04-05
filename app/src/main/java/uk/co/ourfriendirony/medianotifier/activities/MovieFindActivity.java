package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.listviewadapter.MovieListViewAdapter;

public class MovieFindActivity extends AppCompatActivity {
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

        findTitle = (TextView) findViewById(R.id.find_title);
        findInput = (EditText) findViewById(R.id.find_input);
        findProgressBar = (ProgressBar) findViewById(R.id.find_progress);
        findList = (ListView) findViewById(R.id.find_list_tv);

        findTitle.setText(R.string.find_title_movie);

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
                Toast.makeText(MovieFindActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });


        findList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.find_item_secondarybar);
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                params.height = (params.height > 0) ? 0 : pdToPx(40);
                layout.setLayoutParams(params);
                return true;
            }
        });
    }

    public int pdToPx(int dimensionDp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }

    class MovieFindAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String string = strings[0];
            try {
                movies = client.queryMovie(string.toString());
            } catch (IOException e) {
                movies = new ArrayList<>();
            }
            return movies;
        }

        protected void onPostExecute(List<Movie> result) {
            findProgressBar.setVisibility(View.GONE);

            if (movies.size() > 0) {
                MovieListViewAdapter adapter = new MovieListViewAdapter(getBaseContext(), R.layout.list_item_tvshow, movies);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.find_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }
}
