package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.clients.TMDBClient;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class MovieUpdateAsyncTask extends AsyncTask<MediaItem, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(MediaItem... mediaItems) {
        int failed = 0;
        String result = "";

        if (mediaItems.length == 1) {
            result += "'" + mediaItems[0].getTitle() + "' Updated";
        } else {
            result += "Movies Updated";
        }

        for (MediaItem mediaItem : mediaItems) {
            try {
                mediaItem = new TMDBClient().getMovie(Integer.parseInt(mediaItem.getId()));
                new MovieDatabase(getStaticContext()).update(mediaItem);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", mediaItem.getTitle() + ": " + e.getMessage());
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
