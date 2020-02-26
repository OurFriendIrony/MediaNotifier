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

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.ListChildrenAsyncTask;
import uk.co.ourfriendirony.medianotifier.async.UpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.MovieClient;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityMovie extends AppCompatActivity {
    private Spinner spinnerView;
    private ListView listView;
    private List<MediaItem> movies;
    private ProgressBar progressBar;
    private int currentItemPos;
    private Database db;
    private Client client = new MovieClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_movie);
        super.setContentView(R.layout.activity_list);

        db = new MovieDatabase(getBaseContext());

        spinnerView = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        new MovieListAsyncTask().execute();

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                currentItemPos = itemPos;
                new ListChildrenAsyncTask(getBaseContext(), db, progressBar, listView).execute(movies.get(itemPos).getId());
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
        MediaItem mediaItem = movies.get(currentItemPos);
        switch (menuItem.getItemId()) {
            case R.id.action_refresh:
                new UpdateAsyncTask(getApplicationContext(), db, client).execute(mediaItem);
                new ListChildrenAsyncTask(getBaseContext(), db, progressBar, listView).execute(mediaItem.getId());
                return true;

            case R.id.action_remove:
                db.delete(mediaItem.getId());
                this.recreate();
                return true;

            case R.id.action_lookup:
                Intent intent = IntentGenerator.getWebPageIntent(mediaItem.getExternalLink());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class MovieListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            movies = db.readAllParentItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            progressBar.setVisibility(View.GONE);
            if (movies.size() > 0) {
                ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, movies, db);
                spinnerView.setAdapter(listAdapterSummary);
            }
        }
    }
}
