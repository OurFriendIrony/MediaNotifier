package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class MovieUpdateAsyncTask extends AsyncTask<Item, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Item... items) {
        int failed = 0;
        String result = "";

        if (items.length == 1) {
            result += "'" + items[0].getTitle() + "' Updated";
        } else {
            result += "Movies Updated";
        }

        for (Item item : items) {
            try {
                item = new MovieDatabaseClient().getMovie(Integer.parseInt(item.getId()));
                new MovieDatabase(getStaticContext()).update(item);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", item.getTitle() + ": " + e.getMessage());
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
