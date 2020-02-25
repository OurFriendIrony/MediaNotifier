package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class UpdateAsyncTask extends AsyncTask<MediaItem, Void, String> {
    private final Context context;
    private final Database db;
    private final Client client;

    public UpdateAsyncTask(Context context, Database db, Client client) {
        this.context = context;
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
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
