package uk.co.ourfriendirony.medianotifier.general;

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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;

public class MyTVShowAdapter extends ArrayAdapter {
    private final List<MDTVShowSummary> tvShows;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public MyTVShowAdapter(Context context, int textViewResourceId, List<MDTVShowSummary> objects) {
        super(context, textViewResourceId, objects);
        tvShows = objects;
    }

    public List<MDTVShowSummary> getTvShows() {
        return tvShows;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MDTVShowSummary tvShow = tvShows.get(position);

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);
        TextView textView1 = (TextView) v.findViewById(R.id.lookup_item_title);
        TextView textView2 = (TextView) v.findViewById(R.id.lookup_item_date);
        TextView textView3 = (TextView) v.findViewById(R.id.lookup_item_overview);
        TextView textView4 = (TextView) v.findViewById(R.id.lookup_item_id);
        ImageView imageView = (ImageView) v.findViewById(R.id.lookup_item_img);

        String year = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            year = dateFormat.format(date);

        textView1.setText(tvShow.getName());
        textView2.setText(year);
        textView3.setText(tvShow.getOverview());
        textView4.setText(String.valueOf(tvShow.getId()));
        imageView.setImageResource(R.drawable.img_concentric);
        return v;
    }
}
