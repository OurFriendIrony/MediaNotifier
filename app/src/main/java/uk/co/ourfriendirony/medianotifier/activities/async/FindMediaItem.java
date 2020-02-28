package uk.co.ourfriendirony.medianotifier.activities.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class FindMediaItem extends AsyncTask<String, Void, List<MediaItem>> {
    private final WeakReference<Context> context;
    private final WeakReference<ProgressBar> progressBar;
    private final WeakReference<ListView> listView;
    private final Database db;
    private final Client client;

    public FindMediaItem(Context context, ProgressBar progressBar, ListView listView, Database db, Client client) {
        this.context = new WeakReference<>(context);
        this.progressBar = new WeakReference<>(progressBar);
        this.listView = new WeakReference<>(listView);
        this.db = db;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.get().setIndeterminate(true);
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
        progressBar.get().setIndeterminate(false);
        if (mediaItems.size() > 0) {
            ArrayAdapter adapter = new ListAdapterSummary(context.get(), R.layout.list_item_generic, mediaItems, db);
            listView.get().setAdapter(adapter);
        } else {
            Toast.makeText(context.get(), R.string.toast_no_results, Toast.LENGTH_LONG).show();
        }
    }
}
