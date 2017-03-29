package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.general.MyArrayAdapter;

public class TVLookupActivity extends AppCompatActivity {
    ListView simpleList;
    List<MDTVShowSummary> tvShows = new ArrayList<>();
    MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvlookup);

        EditText editText = (EditText) findViewById(R.id.input_tvshowlookup);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    try {
                        tvShows = client.queryTVShow(textView.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(textView.getContext(), "No Matches", Toast.LENGTH_LONG);
                    }

                    simpleList = (ListView) findViewById(R.id.list_tvshowlookup);

                    MyArrayAdapter adapter = new MyArrayAdapter(textView.getContext(), R.layout.list_item, tvShows);
                    simpleList.setAdapter(adapter);
                    handled = true;
                }
                return handled;
            }
        });
    }
}
