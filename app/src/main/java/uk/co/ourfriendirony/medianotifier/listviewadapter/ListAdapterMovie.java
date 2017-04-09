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
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;

public class ListAdapterMovie extends ArrayAdapter {
    private final List<Movie> movies;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public ListAdapterMovie(Context context, int textViewResourceId, List<Movie> objects) {
        super(context, textViewResourceId, objects);
        this.movies = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Movie movie = movies.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_find, null);

        TextView textId = (TextView) view.findViewById(R.id.find_item_id);
        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        TextView textDate = (TextView) view.findViewById(R.id.find_item_date);
        TextView textOverview = (TextView) view.findViewById(R.id.find_item_overview);

        String year = "";
        Date date = movie.getReleaseDate();
        if (date != null)
            year = dateFormat.format(date);

        textId.setText(String.valueOf(movie.getId()));
        textTitle.setText(movie.getTitle());
        textDate.setText(year);
        textOverview.setText(movie.getOverview());

        return view;
    }
}
