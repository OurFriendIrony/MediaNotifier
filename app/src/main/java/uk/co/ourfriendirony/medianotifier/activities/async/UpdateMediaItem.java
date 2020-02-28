package uk.co.ourfriendirony.medianotifier.activities.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class UpdateMediaItem extends AsyncTask<MediaItem, Void, String> {
    private final WeakReference<Context> context;
    private final WeakReference<ProgressBar> progressBar;
    private final Database db;
    private final Client client;

    public UpdateMediaItem(Context context, ProgressBar progressBar, Database db, Client client) {
        this.context = new WeakReference<>(context);
        this.progressBar = new WeakReference<>(progressBar);
        this.db = db;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.get().setIndeterminate(true);
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
        progressBar.get().setIndeterminate(false);
        Toast.makeText(context.get(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
