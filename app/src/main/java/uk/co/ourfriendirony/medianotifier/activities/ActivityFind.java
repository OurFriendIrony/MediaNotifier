package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.async.AddAsyncTask;
import uk.co.ourfriendirony.medianotifier.async.FindAsyncTask;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class ActivityFind extends AppCompatActivity {
    private EditText input;
    private ProgressBar progressBar;
    private ListView listView;
    private Client client;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);

        setTheme(PropertyHelper.getTheme(getBaseContext()));
        setContentView(R.layout.activity_find);
        getSupportActionBar().setTitle("Find " + intentKey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseFactory().getDatabase(getApplicationContext(), intentKey);
        client = new ClientFactory().getClient(intentKey);

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
                            new FindAsyncTask(getBaseContext(), progressBar, listView, db, client).execute(input);
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
                new AddAsyncTask(getApplicationContext(), progressBar, db, client).execute(
                        textViewID.getText().toString(),
                        textViewTitle.getText().toString()
                );
            }
        });
    }
}
