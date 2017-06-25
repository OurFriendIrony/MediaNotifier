package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class TVShowUpdateAsyncTask extends AsyncTask<TVShow, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(TVShow... tvShows) {
        int failed = 0;
        String result = "";

        if (tvShows.length == 1) {
            result += "'" + tvShows[0].getName() + "' Updated";
        } else {
            result += "TV Shows Updated";
        }

        for (TVShow tvShow : tvShows) {
            try {
                tvShow = new MovieDatabaseClient().getTVShow(tvShow.getId());
                new TVShowDatabase(getStaticContext()).updateTVShow(tvShow);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", tvShow.getName() + ": " + e.getMessage());
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
