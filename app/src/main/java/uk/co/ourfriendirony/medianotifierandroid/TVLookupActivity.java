package uk.co.ourfriendirony.medianotifierandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TVLookupActivity extends AppCompatActivity {
    private ListView lv;

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
                    lookupTvShow(textView);
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void lookupTvShow(TextView textView) {
        lv = (ListView) findViewById(R.id.list_tvshowlookup);
        List<String> mylist = new ArrayList<>();
        mylist.add("first item-" + getInputString(textView));
        mylist.add("2nd item");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(textView.getContext(), android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(arrayAdapter);
    }

    @NonNull
    private String getInputString(TextView textView) {
        return textView.getText().toString();
    }

}
