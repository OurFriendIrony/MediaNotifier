package uk.co.ourfriendirony.medianotifier.activities.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Collections;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.MovieUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityMovie extends AppCompatActivity {
    private Spinner spinnerView;
    private ListView listView;
    private List<MediaItem> mediaItems;
    private ProgressBar loadPageProgressBar;
    private int currentItemPosition;
    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getApplicationContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_movie);
        super.setContentView(R.layout.activity_movie);

        db = new MovieDatabase(getApplicationContext());

        spinnerView = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.list);
        loadPageProgressBar = (ProgressBar) findViewById(R.id.progress);
        new MovieListAsyncTask().execute();
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                display(itemPos);
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MediaItem mediaItem = mediaItems.get(currentItemPosition);
        switch (menuItem.getItemId()) {
            case R.id.action_refresh:
                new MovieUpdateAsyncTask().execute(mediaItem);
                this.recreate();
                return true;

            case R.id.action_remove:
                db.delete(mediaItem.getId());
                this.recreate();
                return true;

            case R.id.action_imdb:
                Intent intent = IntentGenerator.getWebPageIntent(mediaItem.getExternalLink());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void display() {
        if (mediaItems.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, mediaItems, db);
            spinnerView.setAdapter(listAdapterSummary);
            display(0);
        }
    }

    private void display(int itemPosition) {
        currentItemPosition = itemPosition;
        ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_toggle, Collections.singletonList(mediaItems.get(itemPosition)), db);
        listView.setAdapter(listAdapterSummary);
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
            mediaItems = db.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            loadPageProgressBar.setVisibility(View.GONE);
            display();
        }
    }
}
