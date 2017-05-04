package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;

public class ListAdapterSummaryMovie extends ArrayAdapter {
    private final List<Movie> movies;
    private final int defaultLayoutId;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public ListAdapterSummaryMovie(Context context, int defaultLayoutId, List<Movie> objects) {
        super(context, defaultLayoutId, objects);
        this.defaultLayoutId = defaultLayoutId;
        this.movies = objects;
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

        Movie movie = movies.get(position);

        String year = "";
        Date date = movie.getReleaseDate();
        if (date != null)
            year = dateFormat.format(date);

        textId.setText(movie.getIdAsString());
        textTitle.setText(movie.getTitle());
        textDate.setText(year);
        textOverview.setText(movie.getOverview());
        return view;
    }

    @NonNull
    private View getSimpleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        Movie movie = movies.get(position);
        textTitle.setText(movie.getTitle());

        return view;
    }
}
