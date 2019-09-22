package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.tv.TVShow;

public class ListAdapterSummaryTV extends ArrayAdapter {
    private final List<TVShow> tvShows;
    private final int defaultLayoutId;

    private DateFormat yearFormatter = new SimpleDateFormat("yyyy");

    public ListAdapterSummaryTV(Context context, int defaultLayoutId, List<TVShow> objects) {
        super(context, defaultLayoutId, objects);
        this.defaultLayoutId = defaultLayoutId;
        this.tvShows = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (defaultLayoutId == R.layout.list_item_generic) {
            view = getFindView(position, view);
        } else {
            view = getTitleView(position, view);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = getFindView(position, view);
        return view;
    }

    @NonNull
    private View getFindView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_generic, null);
        final TVShow tvShow = tvShows.get(position);

//        TextView textId = (TextView) view.findViewById(R.id.find_item_id);
        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_generic_date);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_generic_overview);

        String dateString = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            dateString = yearFormatter.format(date);

//        textId.setText(String.valueOf(tvShow.getId()));
        textTitle.setText(tvShow.getName());
        textDate.setText(dateString);
        textOverview.setText(tvShow.getOverview());
        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);
        TVShow tvShow = tvShows.get(position);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);

        textTitle.setText(tvShow.getName());
        return view;
    }
}
