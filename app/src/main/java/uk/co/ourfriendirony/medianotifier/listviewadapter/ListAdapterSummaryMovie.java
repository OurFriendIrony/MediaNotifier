package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static uk.co.ourfriendirony.medianotifier.db.MovieDatabaseDefinition.*;
import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.db.MovieDatabase;

public class ListAdapterSummaryMovie extends ArrayAdapter {
    private final List<Movie> movies;
    private final int defaultLayoutId;

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
        switch (defaultLayoutId) {
            case R.layout.list_item_find:
                view = getFindView(position, view);
                break;
            case R.layout.list_item_movie:
                view = getChecklistView(position, view);
                break;
            default:
                view = getTitleView(position, view);
                break;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy");

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
    private View getChecklistView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_movie, null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MovieDatabase database = new MovieDatabase(getContext());

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_title);
        TextView textSubTitle = (TextView) view.findViewById(R.id.list_item_sub_title);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_date);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_overview);

        Movie movie = movies.get(position);

        String year = "";
        Date date = movie.getReleaseDate();
        if (date != null)
            year = dateFormat.format(date);

        String collection = movie.getBelongsToCollection().getCollectionName();
        if ("".equals(collection)) {
            textSubTitle.setHeight(0);
        } else {
            collection = "[" + collection + "]";
            textSubTitle.setText(collection);
        }
        textTitle.setText(movie.getTitle());
        textDate.setText(year);
        textOverview.setText(movie.getOverview());

        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.button_toggle);
        toggle.setChecked(database.getMovieWatchedStatusAsBoolean(movie));

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    database.updateMovieWatchedStatus(movie, WATCHED_TRUE);
                } else {
                    database.updateMovieWatchedStatus(movie, WATCHED_FALSE);
                }
            }
        });

        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        Movie movie = movies.get(position);
        textTitle.setText(movie.getTitle());

        return view;
    }
}
