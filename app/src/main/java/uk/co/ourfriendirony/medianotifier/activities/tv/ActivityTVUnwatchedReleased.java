package uk.co.ourfriendirony.medianotifier.activities.tv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ActivityTVUnwatchedReleased extends Fragment {

    private TVShowDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notifications, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.tv_notification_list);

        database = new TVShowDatabase(getContext());
        List<MediaItem> tvEpisodes = database.getUnwatchedReleased();
        if (tvEpisodes.size() > 0) {
            ListAdapterSummary listAdapterTVEpisode = new ListAdapterSummary(getContext(), R.layout.list_item_generic_toggle, tvEpisodes, database);
            listView.setAdapter(listAdapterTVEpisode);
        }
        return rootView;
    }
}
