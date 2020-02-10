package uk.co.ourfriendirony.medianotifier.activities.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;

public class ActivityMovieUnwatchedComingSoon extends Fragment {

    private Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_tv_notifications, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.tv_notification_list);

        db = new MovieDatabase(getContext());
        List<Item> items = db.getUnwatchedTotal();
        if (items.size() > 0) {
            ListAdapterSummary listAdapterMovie = new ListAdapterSummary(getContext(), R.layout.list_item_generic_toggle, items, db);
            listView.setAdapter(listAdapterMovie);
        }
        return rootView;
    }
}
