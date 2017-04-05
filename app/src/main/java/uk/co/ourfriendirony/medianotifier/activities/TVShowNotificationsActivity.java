package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.TVShowNotificationAdapter;

public class TVShowNotificationsActivity extends AppCompatActivity {

    private TVShowDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_notifications);

        ListView seasonList = (ListView) findViewById(R.id.tvlist_notification_list);

        database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));
        List<TVEpisode> tvEpisodes = database.getUnwatchedUnairedEpisodes();
        if (tvEpisodes.size() > 0) {
            TVShowNotificationAdapter tvShowNotificationAdapter = new TVShowNotificationAdapter(getBaseContext(), R.layout.list_item_notification, tvEpisodes);
            seasonList.setAdapter(tvShowNotificationAdapter);
        }
    }
}
