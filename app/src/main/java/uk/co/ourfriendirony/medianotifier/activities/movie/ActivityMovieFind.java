package uk.co.ourfriendirony.medianotifier.activities.movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.clients.TMDBClient;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityMovieFind extends AppCompatActivity {
    private EditText input;
    private ProgressBar progressBar;
    private ListView listView;
    private List<MediaItem> mediaItems = new ArrayList<>();
    private TMDBClient client = new TMDBClient();
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_find_movie);
        super.setContentView(R.layout.activity_find);

        db = new MovieDatabase(getApplicationContext());

        input = (EditText) findViewById(R.id.find_input);
        progressBar = (ProgressBar) findViewById(R.id.find_progress);
        listView = (ListView) findViewById(R.id.find_list);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewID = (TextView) view.findViewById(R.id.list_item_generic_id);
                TextView textViewTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
                new MovieAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
            }
        });
    }

    private class MovieFindAsyncTask extends AsyncTask<String, Void, List<MediaItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MediaItem> doInBackground(String... params) {
            String query = params[0];
            try {
                mediaItems = client.queryMovie(query);
            } catch (IOException e) {
                mediaItems = new ArrayList<>();
            }
            return mediaItems;
        }

        @Override
        protected void onPostExecute(List<MediaItem> result) {
            progressBar.setVisibility(View.GONE);

            if (mediaItems.size() > 0) {
                ListAdapterSummary adapter = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic, mediaItems, db);
                listView.setAdapter(adapter);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String movieId = params[0];
            String movieTitle = params[1];

            MediaItem mediaItem;
            try {
                mediaItem = client.getMovie(Integer.parseInt(movieId));
                db.add(mediaItem);
            } catch (IOException e) {
                Log.e(String.valueOf(this.getClass()), "Failed to add: " + e.getMessage());
            }
            return movieTitle;
        }

        @Override
        protected void onPostExecute(String movieTitle) {
            progressBar.setVisibility(View.GONE);
            String toastMsg = "'" + movieTitle + "' " + getResources().getString(R.string.toast_db_added);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
