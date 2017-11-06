package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import uk.co.ourfriendirony.medianotifier.autogen.artist.Artist;
import uk.co.ourfriendirony.medianotifier.clients.DiscogsDatabaseClient;
import uk.co.ourfriendirony.medianotifier.db.ArtistDatabase;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class ArtistUpdateAsyncTask extends AsyncTask<Artist, Void, String> {
        /* Background Task to Update an existing item */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Artist... artists) {
        int failed = 0;
        String result = "";

        if (artists.length == 1) {
            result += "'" + artists[0].getTitle() + "' Updated";
        } else {
            result += "Artists Updated";
        }

        for (Artist artist : artists) {
            try {
                artist = new DiscogsDatabaseClient().getArtist(artist.getId());
                new ArtistDatabase(getStaticContext()).updateArtist(artist);
            } catch (Exception e) {
                Log.e("FAILED_UPDATE", artist.getTitle() + ": " + e.getMessage());
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
