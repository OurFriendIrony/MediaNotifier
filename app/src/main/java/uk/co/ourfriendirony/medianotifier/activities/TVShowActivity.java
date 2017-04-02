package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowFindListAdapter;

public class TVShowActivity extends AppCompatActivity {

    private TVShowDatabase database;
    private ListView findList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvlist);

        TVShowDatabaseDefinition databaseHelper = new TVShowDatabaseDefinition(getApplicationContext());
        database = new TVShowDatabase(databaseHelper);

        findList = (ListView) findViewById(R.id.find_list);

        List<TVShow> tvShows = database.getTVShows();
        if (tvShows.size() > 0) {
            TVShowFindListAdapter adapter = new TVShowFindListAdapter(getBaseContext(), R.layout.find_item, tvShows);
            findList.setAdapter(adapter);
        }

        // ----- DELETE ME -----
        TextView textView = (TextView) findViewById(R.id.list_text);
        textView.setText(database.selectTVShow());
        // ----- DELETE ME -----
    }

}
