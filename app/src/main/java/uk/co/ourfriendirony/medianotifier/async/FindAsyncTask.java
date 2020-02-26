package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class FindAsyncTask extends AsyncTask<String, Void, List<MediaItem>> {
    private final WeakReference<Context> context;
    private final WeakReference<ProgressBar> progressBar;
    private final WeakReference<ListView> listView;
    private final Database db;
    private final Client client;

    public FindAsyncTask(Context context, ProgressBar progressBar, ListView listView, Database db, Client client) {
        // TODO: I'm pretty sure I can get the listView and the progressBar from the context, instead of this "pass-in" mess
        this.context = new WeakReference<>(context);
        this.progressBar = new WeakReference<>(progressBar);
        this.listView = new WeakReference<>(listView);
        this.db = db;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected List<MediaItem> doInBackground(String... params) {
        String query = params[0];
        try {
            return client.searchMediaItem(query);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<MediaItem> mediaItems) {
        progressBar.get().setVisibility(View.GONE);
        if (mediaItems.size() > 0) {
            ListAdapterSummary adapter = new ListAdapterSummary(context.get(), R.layout.list_item_generic, mediaItems, db);
            listView.get().setAdapter(adapter);
        } else {
            Toast.makeText(context.get(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
        }
    }
}
