package uk.co.ourfriendirony.medianotifier.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTVNotification;

public class ActivityTVNotifications extends AppCompatActivity {

    private TVShowDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_notifications);
        getSupportActionBar().setTitle(R.string.title_notifications);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        ListView seasonList = (ListView) findViewById(R.id.tvlist_notification_list);

        database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));
        List<TVEpisode> tvEpisodes = database.getUnwatchedEpisodes();
        if (tvEpisodes.size() > 0) {
            ListAdapterTVNotification listAdapterTVNotification = new ListAdapterTVNotification(getBaseContext(), R.layout.list_item_tv_notifications, tvEpisodes, true);
            seasonList.setAdapter(listAdapterTVNotification);
        }
    }
}
