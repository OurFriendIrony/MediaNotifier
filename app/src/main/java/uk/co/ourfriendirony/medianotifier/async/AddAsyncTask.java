package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class AddAsyncTask extends AsyncTask<String, Void, String> {
    private final Context context;
    private final Database db;
    private final Client client;
    private final ProgressBar progressBar;

    public AddAsyncTask(Context context, Database db, Client client, ProgressBar progressBar) {
        this.context = context;
        this.db = db;
        this.client = client;
        // TODO: I'm pretty sure there is a better way of feeding the progress bar object in...
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String title = params[1];

        MediaItem mediaItem;
        try {
            mediaItem = client.getMediaItem(id);
            db.add(mediaItem);
        } catch (IOException e) {
            Log.e("[FAILED_ADD]", e.getMessage());
        }
        return db.getCoreType() + " '" + title + "' Added";
    }

    @Override
    protected void onPostExecute(String toastMsg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
