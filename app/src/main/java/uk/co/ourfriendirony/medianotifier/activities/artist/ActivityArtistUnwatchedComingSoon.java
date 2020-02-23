package uk.co.ourfriendirony.medianotifier.activities.artist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityArtistUnwatchedComingSoon extends Fragment {

    private Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_tv_notifications, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.tv_notification_list);

        db = new ArtistDatabase(getContext());
        List<MediaItem> mediaItems = db.getUnwatchedTotal();
        if (mediaItems.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getContext(), R.layout.list_item_generic_toggle, mediaItems, db);
            listView.setAdapter(listAdapterSummary);
        }
        return rootView;
    }
}
