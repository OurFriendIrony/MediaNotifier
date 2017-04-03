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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowListAdapter;

public class TVShowFindActivity extends AppCompatActivity {
    private TextView findTitle;
    private EditText findInput;
    private ProgressBar findProgressBar;
    private ListView findList;

    private List<TVShow> tvShows = new ArrayList<>();
    private MovieDatabaseClient client = new MovieDatabaseClient();
    private TVShowDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        TVShowDatabaseDefinition databaseHelper = new TVShowDatabaseDefinition(getApplicationContext());
        database = new TVShowDatabase(databaseHelper);

        findTitle = (TextView) findViewById(R.id.find_title);
        findInput = (EditText) findViewById(R.id.find_input);
        findProgressBar = (ProgressBar) findViewById(R.id.find_progress);
        findList = (ListView) findViewById(R.id.find_list_tv);

        findTitle.setText(R.string.find_title_tvshow);

        findInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    new TVShowFindAsyncTask().execute(textView.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        findList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewID = (TextView) view.findViewById(R.id.find_item_id);
                TextView textViewTitle = (TextView) view.findViewById(R.id.find_item_title);
                new TVShowAddAsyncTask().execute(textViewID.getText().toString(), textViewTitle.getText().toString());
                ImageView img = (ImageView) view.findViewById(R.id.find_item_img);
                img.setImageDrawable(getResources().getDrawable(R.drawable.img_tick));
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

    class TVShowFindAsyncTask extends AsyncTask<String, Void, List<TVShow>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<TVShow> doInBackground(String... strings) {
            String string = strings[0];
            try {
                tvShows = client.queryTVShow(string);
            } catch (IOException e) {
                tvShows = new ArrayList<>();
            }
            return tvShows;
        }

        protected void onPostExecute(List<TVShow> result) {
            findProgressBar.setVisibility(View.GONE);

            if (tvShows.size() > 0) {
                TVShowListAdapter adapter = new TVShowListAdapter(getBaseContext(), R.layout.find_item, tvShows);
                findList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.find_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }

    class TVShowAddAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String string = params[0];
            database.saveTVShow(Integer.parseInt(string));
            return params[1];
        }

        protected void onPostExecute(String title) {
            findProgressBar.setVisibility(View.GONE);
            String toastMsg = "'" + title + "' " + getResources().getString(R.string.find_add_to_db_done);
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
