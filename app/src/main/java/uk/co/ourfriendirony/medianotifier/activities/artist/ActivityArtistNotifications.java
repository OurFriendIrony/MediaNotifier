package uk.co.ourfriendirony.medianotifier.activities.artist;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.artist.Artist;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryArtist;

public class ActivityArtistNotifications extends AppCompatActivity {

    private ArtistDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        super.getSupportActionBar().setTitle(R.string.title_notifications);
        super.setContentView(R.layout.activity_tv_notifications);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        ListView artistList = (ListView) findViewById(R.id.tv_notification_list);

        database = new ArtistDatabase(getApplicationContext());
        List<Artist> artists = database.getAllArtists();
        if (artists.size() > 0) {
            ListAdapterSummaryArtist listAdapterArtist = new ListAdapterSummaryArtist(getBaseContext(), R.layout.list_item_artist, artists);
            artistList.setAdapter(listAdapterArtist);
        }
    }
}
