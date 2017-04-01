package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.general.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.general.TVShowDatabaseDefinition;

public class TVListActivity extends AppCompatActivity {

    private TVShowDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvlist);

        TVShowDatabaseDefinition databaseHelper = new TVShowDatabaseDefinition(getApplicationContext());
        database = new TVShowDatabase(databaseHelper);

        TextView textView = (TextView) findViewById(R.id.list_text);
        textView.setText(database.selectTVShow());
    }
}
