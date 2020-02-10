package uk.co.ourfriendirony.medianotifier.activities.tv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.tv.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterTVEpisode;

public class ActivityTVUnwatchedReleased extends Fragment {

    private TVShowDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_tv_notifications, container, false);
        ListView seasonList = (ListView) rootView.findViewById(R.id.tv_notification_list);

        database = new TVShowDatabase(getContext());
        List<TVEpisode> tvEpisodes = database.getUnwatchedEpisodesReleased();
        if (tvEpisodes.size() > 0) {
            ListAdapterTVEpisode listAdapterTVEpisode = new ListAdapterTVEpisode(getContext(), R.layout.list_item_generic_toggle, tvEpisodes, true);
            seasonList.setAdapter(listAdapterTVEpisode);
        }
        return rootView;
    }
}
