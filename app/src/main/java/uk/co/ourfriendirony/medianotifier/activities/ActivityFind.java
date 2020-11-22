package uk.co.ourfriendirony.medianotifier.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.async.AddMediaItem;
import uk.co.ourfriendirony.medianotifier.activities.async.FindMediaItem;
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

        input = findViewById(R.id.find_input);
        progressBar = findViewById(R.id.find_progress);
        listView = findViewById(R.id.find_list);

        input.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String input = textView.getText().toString();
                if (!"".equals(input)) {
                    new FindMediaItem(getBaseContext(), progressBar, listView, db, client).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, input);
                }
                return true;
            } else {
                return false;
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView textViewID = view.findViewById(R.id.list_item_generic_id);
            TextView textViewTitle = view.findViewById(R.id.list_item_generic_title);
            new AddMediaItem(getApplicationContext(), progressBar, db, client).execute(
                    textViewID.getText().toString(),
                    textViewTitle.getText().toString()
            );
        });
    }
}
