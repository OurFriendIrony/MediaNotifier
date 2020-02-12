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
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.clients.DiscogsClient;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;

public class ActivityArtistFind extends AppCompatActivity {
    private EditText input;
    private ProgressBar progressBar;
    private ListView listView;
    private List<MediaItem> mediaItems = new ArrayList<>();
    private DiscogsClient client = new DiscogsClient();
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_find_artist);
        super.setContentView(R.layout.activity_find);

        database = new ArtistDatabase(getApplicationContext());

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
                            new ArtistFindAsyncTask().execute(input);
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
                new ArtistAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
            }
        });
    }

    private class ArtistFindAsyncTask extends AsyncTask<String, Void, List<MediaItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MediaItem> doInBackground(String... params) {
            String query = params[0];
            try {
                mediaItems = client.queryArtist(query);
            } catch (IOException e) {
                mediaItems = new ArrayList<>();
            }
            return mediaItems;
        }

        @Override
        protected void onPostExecute(List<MediaItem> result) {
            progressBar.setVisibility(View.GONE);

            if (mediaItems.size() > 0) {
                ListAdapterSummary adapter = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic, mediaItems, database);
                listView.setAdapter(adapter);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String artistId = params[0];
            String artistTitle = params[1];

            MediaItem artist;
            try {
                artist = client.getArtist(Integer.parseInt(artistId));
                database.add(artist);
            } catch (IOException e) {
                Log.e(String.valueOf(this.getClass()), "Failed to add: " + e.getMessage());
            }
            return artistTitle;
        }

        @Override
        protected void onPostExecute(String artistTitle) {
            progressBar.setVisibility(View.GONE);
            String toastMsg = "'" + artistTitle + "' " + getResources().getString(R.string.toast_db_added);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
