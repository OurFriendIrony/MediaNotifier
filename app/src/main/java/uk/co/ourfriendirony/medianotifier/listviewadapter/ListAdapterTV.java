package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;

public class ListAdapterTV extends ArrayAdapter {
    private final List<TVShow> tvShows;
    private final Context context;
    private final int defaultLayoutId;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public ListAdapterTV(Context context, int defaultLayoutId, List<TVShow> objects) {
        super(context, defaultLayoutId, objects);
        this.defaultLayoutId = defaultLayoutId;
        this.context = context;
        this.tvShows = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (defaultLayoutId == R.layout.list_item_find) {
            view = getFindView(position, view);
        } else {
            view = getSimpleView(position, view);
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
        view = inflater.inflate(R.layout.list_item_find, null);

        TextView textId = (TextView) view.findViewById(R.id.find_item_id);
        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        TextView textDate = (TextView) view.findViewById(R.id.find_item_date);
        TextView textOverview = (TextView) view.findViewById(R.id.find_item_overview);

        TVShow tvShow = tvShows.get(position);

        String year = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            year = dateFormat.format(date);

        textId.setText(String.valueOf(tvShow.getId()));
        textTitle.setText(tvShow.getName());
        textDate.setText(year);
        textOverview.setText(tvShow.getOverview());
        return view;
    }

    @NonNull
    private View getSimpleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        TVShow tvShow = tvShows.get(position);
        textTitle.setText(tvShow.getName());

        return view;
    }
}
