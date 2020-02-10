package uk.co.ourfriendirony.medianotifier.activities.tv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;

public class ActivityTVUnwatchedComingSoon extends Fragment {

    private TVShowDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_tv_notifications, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.tv_notification_list);

        database = new TVShowDatabase(getContext());
        Log.d("BEFORE ITEMS", "");
        List<Item> items = database.getUnwatchedTotal();
        Log.d("AFTER ITEMS", String.valueOf(items.size()));
        if (items.size() > 0) {
            ListAdapterSummary listAdapterTVEpisode = new ListAdapterSummary(getContext(), R.layout.list_item_generic_toggle, items, database);
            listView.setAdapter(listAdapterTVEpisode);
        }
        return rootView;
    }
}
