package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;

public class ListAdapterTV extends ArrayAdapter {
    private final List<TVShow> tvShows;
    private final Context context;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public ListAdapterTV(Context context, int textViewResourceId, List<TVShow> objects) {
        super(context, textViewResourceId, objects);
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
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
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
}
