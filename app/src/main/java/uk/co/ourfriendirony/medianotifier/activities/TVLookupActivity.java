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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.general.MyTVShowAdapter;

public class TVLookupActivity extends AppCompatActivity {
    ListView simpleList;
    List<MDTVShowSummary> tvShows = new ArrayList<>();
    MovieDatabaseClient client = new MovieDatabaseClient();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        simpleList = (ListView) findViewById(R.id.lookup_list);
        progressBar = (ProgressBar) findViewById(R.id.lookup_progress);
        TextView textView = (TextView) findViewById(R.id.lookup_title);
        EditText editText = (EditText) findViewById(R.id.lookup_input);

        textView.setText(R.string.lookup_title_tvshow);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    new TVShowLookupAsyncTask().execute(textView.getText().toString());
                    handled = true;
                }
                return handled;
            }

        });

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lookup_secondarybar);
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                params.height = (params.height > 0) ? 0 : pdToPx(40);
                layout.setLayoutParams(params);
            }
        });
    }

    public int pdToPx(int dimensionDp) {

        float density = getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }

    class TVShowLookupAsyncTask extends AsyncTask<String, Void, List<MDTVShowSummary>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MDTVShowSummary> doInBackground(String... strings) {
            String string = strings[0];
            try {
                Log.v(String.valueOf(this.getClass()), string);
                tvShows = client.queryTVShow(string.toString());
            } catch (IOException e) {
                tvShows = new ArrayList<>();
            }
            return tvShows;
        }

        protected void onPostExecute(List<MDTVShowSummary> result) {
            progressBar.setVisibility(View.GONE);

            if (tvShows.size() > 0) {
                MyTVShowAdapter adapter = new MyTVShowAdapter(getBaseContext(), R.layout.list_item, tvShows);
                simpleList = (ListView) findViewById(R.id.lookup_list);
                simpleList.setAdapter(adapter);
            } else {
                Toast.makeText(getBaseContext(), R.string.lookup_no_results, Toast.LENGTH_LONG).show();
            }
        }
    }
}
