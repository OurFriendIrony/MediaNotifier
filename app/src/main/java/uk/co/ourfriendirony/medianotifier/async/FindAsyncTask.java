package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.clients.Client;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class FindAsyncTask extends AsyncTask<String, Void, List<MediaItem>> {
    private final Context context;
    private final Database db;
    private final Client client;
    private final ProgressBar progressBar;
    private final ListView listView;

    public FindAsyncTask(Context context, Database db, Client client, ProgressBar progressBar, ListView listView) {
        this.context = context;
        this.db = db;
        this.client = client;
        this.listView = listView;
        // TODO: I'm pretty sure there is a better way of feeding the progress bar object in...
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
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
        progressBar.setVisibility(View.GONE);

        if (mediaItems.size() > 0) {
            ListAdapterSummary adapter = new ListAdapterSummary(context, R.layout.list_item_generic, mediaItems, db);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(context, R.string.toast_no_results, Toast.LENGTH_LONG).show();
        }
    }
}
