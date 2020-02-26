package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class AddAsyncTask extends AsyncTask<String, Void, String> {
    private final WeakReference<Context> context;
    private final WeakReference<ProgressBar> progressBar;
    private final Database db;
    private final Client client;

    public AddAsyncTask(Context context, ProgressBar progressBar, Database db, Client client) {
        // TODO: I'm pretty sure I can get the progressBar from the context, instead of this "pass-in" mess
        this.context = new WeakReference<>(context);
        this.progressBar = new WeakReference<>(progressBar);
        this.db = db;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.get().setVisibility(View.VISIBLE);
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
        progressBar.get().setVisibility(View.GONE);
        Toast.makeText(context.get(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
