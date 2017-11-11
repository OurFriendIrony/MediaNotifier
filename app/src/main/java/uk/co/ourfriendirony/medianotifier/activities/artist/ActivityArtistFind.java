package uk.co.ourfriendirony.medianotifier.activities.artist;

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
import uk.co.ourfriendirony.medianotifier.autogen.artist.Artist;
import uk.co.ourfriendirony.medianotifier.clients.DiscogsDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryArtist;

public class ActivityArtistFind extends AppCompatActivity {
    private ArtistDatabase database;

    private EditText findInput;
    private ProgressBar findProgressBar;
    private ListView findList;
    private List<Artist> artists = new ArrayList<>();
    private DiscogsDatabaseClient client = new DiscogsDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setTitle(R.string.title_find_artist);

        database = new ArtistDatabase(getApplicationContext());

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
                            new ArtistFindAsyncTask().execute(input);
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
                new ArtistAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
            }
        });
    }

    private class ArtistFindAsyncTask extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Artist> doInBackground(String... params) {
            String string = params[0];
            try {
                artists = client.queryArtist(string);
                for (int i = 0; i < artists.size(); i++) {
                    artists.set(i, client.getArtist(artists.get(i).getId()));
                }
            } catch (IOException e) {
                artists = new ArrayList<>();
                Log.e(String.valueOf(this.getClass()), "Failed to query: " + e.getMessage());
            }
            return artists;
        }

        @Override
        protected void onPostExecute(List<Artist> result) {
            findProgressBar.setVisibility(View.GONE);

            if (artists.size() > 0) {
                ListAdapterSummaryArtist adapter = new ListAdapterSummaryArtist(getBaseContext(), R.layout.list_item_find, artists);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ArtistAddAsyncTask extends AsyncTask<String, Void, String> {
        /* Adds new item to database
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String artistId = params[0];
            String artistTitle = params[1];

            Artist artist;
            try {
                artist = client.getArtist(Integer.parseInt(artistId));
                database.addArtist(artist);
            } catch (IOException e) {
                Log.e(String.valueOf(this.getClass()), "Failed to add: " + e.getMessage());
            }
            return artistTitle;
        }

        @Override
        protected void onPostExecute(String artistTitle) {
            findProgressBar.setVisibility(View.GONE);
            String toastMsg = "'" + artistTitle + "' " + getResources().getString(R.string.toast_db_added);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
