package uk.co.ourfriendirony.medianotifier.activities.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.movie.Movie;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummaryMovie;

public class ActivityMovieUnwatchedComingSoon extends Fragment {

    private MovieDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_tv_notifications, container, false);
        ListView movieList = (ListView) rootView.findViewById(R.id.tv_notification_list);

        database = new MovieDatabase(getContext());
        List<Movie> movies = database.getUnwatchedMoviesTotal();
        if (movies.size() > 0) {
            ListAdapterSummaryMovie listAdapterMovie = new ListAdapterSummaryMovie(getContext(), R.layout.list_item_movie, movies);
            movieList.setAdapter(listAdapterMovie);
        }
        return rootView;
    }
}
