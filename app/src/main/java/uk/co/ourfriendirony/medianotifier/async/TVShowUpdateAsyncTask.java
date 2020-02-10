package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.TMDBClient;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class TVShowUpdateAsyncTask extends AsyncTask<Item, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Item... tvShows) {
        int failed = 0;
        String result = "";

        if (tvShows.length == 1) {
            result += "'" + tvShows[0].getTitle() + "' Updated";
        } else {
            result += "TV Shows Updated";
        }
        for (Item tvShow : tvShows) {
            try {
                tvShow = new TMDBClient().getTVShow(Integer.parseInt(tvShow.getId()));
                new TVShowDatabase(getStaticContext()).update(tvShow);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", tvShow.getTitle() + ": " + e.getMessage());
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
