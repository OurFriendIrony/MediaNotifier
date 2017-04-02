package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MovieFind;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.general.MyMovieAdapter;

public class MovieLookupActivity extends AppCompatActivity {
    private TextView lookupTitle;
    private EditText lookupInput;
    private ProgressBar lookupProgressBar;
    private ListView lookupList;

    private List<MovieFind> movies = new ArrayList<>();
    private MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        lookupTitle = (TextView) findViewById(R.id.lookup_title);
        lookupInput = (EditText) findViewById(R.id.lookup_input);
        lookupProgressBar = (ProgressBar) findViewById(R.id.lookup_progress);
        lookupList = (ListView) findViewById(R.id.lookup_list);

        lookupTitle.setText(R.string.lookup_title_movie);

        lookupInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    new MovieLookupAsyncTask().execute(textView.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        lookupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(String.valueOf(this.getClass()), "IM HERE");
            }
        });


        lookupList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lookup_secondarybar);
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

    class MovieLookupAsyncTask extends AsyncTask<String, Void, List<MovieFind>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lookupProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieFind> doInBackground(String... strings) {
            String string = strings[0];
            try {
                Log.v(String.valueOf(this.getClass()), string);
                movies = client.queryMovie(string.toString());
            } catch (IOException e) {
                movies = new ArrayList<>();
            }
            return movies;
        }

        protected void onPostExecute(List<MovieFind> result) {
            lookupProgressBar.setVisibility(View.GONE);

            if (movies.size() > 0) {
                MyMovieAdapter adapter = new MyMovieAdapter(getBaseContext(), R.layout.list_item, movies);
                lookupList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.lookup_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }
}
