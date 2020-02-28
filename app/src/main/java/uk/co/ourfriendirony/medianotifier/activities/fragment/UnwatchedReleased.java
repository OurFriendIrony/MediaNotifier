package uk.co.ourfriendirony.medianotifier.activities.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class UnwatchedReleased extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notifications, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.tv_notification_list);

        String intentKey = this.getArguments().getString(INTENT_KEY);
        Database db = new DatabaseFactory().getDatabase(getContext(), intentKey);

        List<MediaItem> mediaItems = db.getUnwatchedReleased();
        if (mediaItems.size() > 0) {
            ListAdapterSummary listAdapterSummary = new ListAdapterSummary(getContext(), R.layout.list_item_generic_toggle, mediaItems, db);
            listView.setAdapter(listAdapterSummary);
        }
        return rootView;
    }
}
