package uk.co.ourfriendirony.medianotifier.activities.tv;

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
import uk.co.ourfriendirony.medianotifier.autogen.tv.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryTV;

public class ActivityTVFind extends AppCompatActivity {
    private TVShowDatabase database;

    private EditText findInput;
    private ProgressBar findProgressBar;
    private ListView findList;
    private List<TVShow> tvShows = new ArrayList<>();
    private MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setTitle(R.string.title_find_tvshow);

        database = new TVShowDatabase(getApplicationContext());

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
                            new TVShowFindAsyncTask().execute(input);
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
                new TVShowAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
            }
        });
    }

    private class TVShowFindAsyncTask extends AsyncTask<String, Void, List<TVShow>> {
        /* Looks up and returns tvshow using API
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<TVShow> doInBackground(String... params) {
            String tvShowTitle = params[0];
            try {
                tvShows = client.queryTVShow(tvShowTitle);
            } catch (IOException e) {
                tvShows = new ArrayList<>();
                Log.e(String.valueOf(this.getClass()), "Failed to query: " + e.getMessage());
            }
            return tvShows;
        }

        @Override
        protected void onPostExecute(List<TVShow> result) {
            findProgressBar.setVisibility(View.GONE);

            if (tvShows.size() > 0) {
                ListAdapterSummaryTV adapter = new ListAdapterSummaryTV(getBaseContext(), R.layout.list_item_find, tvShows);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class TVShowAddAsyncTask extends AsyncTask<String, Void, String> {
        /* Adds new item to database
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String tvShowId = params[0];
            String tvShowTitle = params[1];

            TVShow tvShow;
            try {
                tvShow = client.getTVShow(Integer.parseInt(tvShowId));
                database.addTVShow(tvShow);
            } catch (IOException e) {
                Log.e(String.valueOf(this.getClass()), "Failed to add: " + e.getMessage());
            }
            return tvShowTitle;
        }

        @Override
        protected void onPostExecute(String tvShowTitle) {
            findProgressBar.setVisibility(View.GONE);
            String toastMsg = "'" + tvShowTitle + "' " + getResources().getString(R.string.toast_db_added);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
