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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;

public class TVShowListAdapter extends ArrayAdapter {
    private final List<TVShow> tvShows;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public TVShowListAdapter(Context context, int textViewResourceId, List<TVShow> objects) {
        super(context, textViewResourceId, objects);
        tvShows = objects;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_tvshow, null);

        TextView textTitle = (TextView) v.findViewById(R.id.find_item_title);
        TextView textDate = (TextView) v.findViewById(R.id.find_item_date);
        TextView textOverview = (TextView) v.findViewById(R.id.find_item_overview);
        TextView textId = (TextView) v.findViewById(R.id.find_item_id);

        TVShow tvShow = tvShows.get(position);

        String year = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            year = dateFormat.format(date);

        textTitle.setText(tvShow.getName());
        textDate.setText(year);
        textOverview.setText(tvShow.getOverview());
        textId.setText(String.valueOf(tvShow.getId()));

        return v;
    }
}
