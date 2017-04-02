package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;

public class TVShowFindListAdapter extends ArrayAdapter {
    private final List<TVShow> tvShows;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public TVShowFindListAdapter(Context context, int textViewResourceId, List<TVShow> objects) {
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
        TVShow tvShow = tvShows.get(position);
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.find_item, null);
        TextView textView1 = (TextView) v.findViewById(R.id.find_item_title);
        TextView textView2 = (TextView) v.findViewById(R.id.find_item_date);
        TextView textView3 = (TextView) v.findViewById(R.id.find_item_overview);
        TextView textView4 = (TextView) v.findViewById(R.id.find_item_id);
        ImageView imageView = (ImageView) v.findViewById(R.id.find_item_img);

        String year = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            year = dateFormat.format(date);

        textView1.setText(tvShow.getName());
        textView2.setText(year);
        textView3.setText(tvShow.getOverview());
        textView4.setText(String.valueOf(tvShow.getId()));
        imageView.setImageResource(R.drawable.circle_off);

        return v;
    }
}
