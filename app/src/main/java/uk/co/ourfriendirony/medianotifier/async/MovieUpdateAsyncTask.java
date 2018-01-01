package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier._objects.movie.Movie;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class MovieUpdateAsyncTask extends AsyncTask<Movie, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Movie... movies) {
        int failed = 0;
        String result = "";

        if (movies.length == 1) {
            result += "'" + movies[0].getTitle() + "' Updated";
        } else {
            result += "Movies Updated";
        }

        for (Movie movie : movies) {
            try {
                movie = new MovieDatabaseClient().getMovie(movie.getId());
                new MovieDatabase(getStaticContext()).updateMovie(movie);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", movie.getTitle() + ": " + e.getMessage());
                failed += 1;
            }
        }

        if (failed > 0)
            result += " [Failed=" + failed + "]";

        return result;
    }

    @Override
    protected void onPostExecute(String toastMsg) {
        Toast.makeText(getStaticContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
