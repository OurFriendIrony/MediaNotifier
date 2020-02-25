package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.clients.TVClient;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class TVShowUpdateAsyncTask extends AsyncTask<MediaItem, Void, String> {
    /* Background Task to Update an existing item */
    private Client client = new TVClient();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(MediaItem... tvShows) {
        int failed = 0;
        String result = "";

        if (tvShows.length == 1) {
            result += "'" + tvShows[0].getTitle() + "' Updated";
        } else {
            result += "TV Shows Updated";
        }
        for (MediaItem tvShow : tvShows) {
            try {
                tvShow = client.getMediaItem(tvShow.getId());
                new TVShowDatabase(getStaticContext()).update(tvShow);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", tvShow.getTitle() + ": " + e.getMessage());
                failed += 1;
            }
        }

        if (failed > 0) {
            result += " [Failed=" + failed + "]";
        }

        return result;
    }

    @Override
    protected void onPostExecute(String toastMsg) {
        Toast.makeText(getStaticContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
