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
import uk.co.ourfriendirony.medianotifier.autogen.movie.MovieFind;

public class MovieListViewAdapter extends ArrayAdapter {
    private final List<MovieFind> movies;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public MovieListViewAdapter(Context context, int textViewResourceId, List<MovieFind> movies) {
        super(context, textViewResourceId, movies);
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        MovieFind movie = movies.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.find_item, null);
        TextView textView1 = (TextView) view.findViewById(R.id.find_item_title);
        TextView textView2 = (TextView) view.findViewById(R.id.find_item_date);
        TextView textView3 = (TextView) view.findViewById(R.id.find_item_overview);
        TextView textView4 = (TextView) view.findViewById(R.id.find_item_id);
        ImageView imageView = (ImageView) view.findViewById(R.id.find_item_img);

        String year = "";
        Date date = movie.getReleaseDate();
        if (date != null)
            year = dateFormat.format(date);

        textView1.setText(movie.getTitle());
        textView2.setText(year);
        textView3.setText(movie.getOverview());
        textView4.setText(String.valueOf(movie.getId()));
        imageView.setImageResource(R.drawable.circle_off);
        return view;
    }
}
