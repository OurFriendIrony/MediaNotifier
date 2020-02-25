package uk.co.ourfriendirony.medianotifier.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.StaticContext.getStaticContext;

public class UpdateAsyncTask extends AsyncTask<MediaItem, Void, String> {
    private final Client client;
    private final Database db;

    public UpdateAsyncTask(Database db, Client client) {
        this.db = db;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(MediaItem... mediaItems) {
        int failed = 0;
        String result = "";

        if (mediaItems.length == 1) {
            result += db.getCoreType() + " '" + mediaItems[0].getTitle() + "' Updated";
        } else {
            result += "All " + db.getCoreType() + " Media Updated";
        }

        for (MediaItem mediaItem : mediaItems) {
            try {
                mediaItem = client.getMediaItem(mediaItem.getId());
                db.update(mediaItem);
            } catch (Exception e) {
                Log.e("[FAILED_UPDATE]", mediaItem.toString() + ": " + e.getMessage());
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
