package uk.co.ourfriendirony.medianotifier.activities.tv;

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
import uk.co.ourfriendirony.medianotifier.async.TVShowUpdateAsyncTask;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityTV extends AppCompatActivity {
    private Spinner spinner;
    private ListView listView;
    private List<MediaItem> tvShows;
    private ProgressBar progressBar;
    private int currentItemPos;
    private TVShowDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_library_tvshow);
        super.setContentView(R.layout.activity_tv);

        db = new TVShowDatabase(getApplicationContext());

        spinner = (Spinner) findViewById(R.id.tv_spinner);
        listView = (ListView) findViewById(R.id.tv_list);
        progressBar = (ProgressBar) findViewById(R.id.tv_progress);
        new TVShowListAsyncTask().execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int showPosition, long id) {
                displayEpisodes(showPosition);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        MediaItem show = tvShows.get(currentItemPos);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new TVShowUpdateAsyncTask().execute(show);
                this.recreate();
                return true;

            case R.id.action_remove:
                db.delete(show.getId());
                this.recreate();
                return true;

            case R.id.action_lookup:
                Intent intent = IntentGenerator.getWebPageIntent(show.getExternalLink());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayShows() {
        if (tvShows.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_title, tvShows, db);
            spinner.setAdapter(listAdapterSummary);
            displayEpisodes(0);
        }
    }

    private void displayEpisodes(int itemPos) {
        currentItemPos = itemPos;
        List<MediaItem> tvEpisodes = tvShows.get(itemPos).getChildren();
        if (tvEpisodes.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getBaseContext(), R.layout.list_item_generic_toggle, tvEpisodes, db);
            listView.setAdapter(listAdapterSummary);
            listView.setSelection(tvEpisodes.size());
        } else {
            listView.setAdapter(null);
        }
    }

    private class TVShowListAsyncTask extends AsyncTask<String, Void, Void> {
        /* Responsible for retrieving all tv shows
         * and displaying them
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            tvShows = db.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void x) {
            progressBar.setVisibility(View.GONE);
            displayShows();
        }
    }
}
