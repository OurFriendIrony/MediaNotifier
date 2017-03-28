package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

public class TVLookupActivity extends AppCompatActivity {

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
                    List<String> list = lookupTvShow(getInputString(textView));
                    updateListView(textView, list);
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void updateListView(TextView textView, List<String> list) {
        ListView lv = (ListView) findViewById(R.id.list_tvshowlookup);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(textView.getContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(arrayAdapter);
    }

    private List<String> lookupTvShow(String inputString) {
        List<String> mylist = new ArrayList<>();
        Log.v(String.valueOf(this.getClass()), "*** lookup");
        MovieDatabaseClient client = new MovieDatabaseClient();
        try {
            for (MDTVShowSummary tvShow : client.queryTVShow(inputString)) {
                mylist.add(tvShow.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mylist;
    }

    @NonNull
    private String getInputString(TextView textView) {
        return textView.getText().toString();
    }

}
