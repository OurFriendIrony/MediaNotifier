package uk.co.ourfriendirony.medianotifier.general;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.MDTVShowSummary;

public class MyArrayAdapter extends ArrayAdapter {
    private final List<MDTVShowSummary> tvShows;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("#.0");

    public MyArrayAdapter(Context context, int textViewResourceId, List<MDTVShowSummary> objects) {
        super(context, textViewResourceId, objects);
        tvShows = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MDTVShowSummary tvShow = tvShows.get(i);

        View v = view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView3);
        TextView textView3 = (TextView) v.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        String year = "";
        Date date = tvShow.getFirstAirDate();
        if (date != null)
            year = dateFormat.format(date);

        textView1.setText(tvShow.getName());
        textView2.setText(year);
        textView3.setText(tvShow.getOverview());
        imageView.setImageResource(R.drawable.img_concentric);
        return v;
    }
}
