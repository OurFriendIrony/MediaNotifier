package uk.co.ourfriendirony.medianotifier.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.listviewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ListChildrenAsyncTask extends AsyncTask<String, Void, List<MediaItem>> {
    // TODO: What the fuck is a 'static field leak' of the context object
    private final Context context;
    private final Database db;
    private final ProgressBar progressBar;
    private final ListView listView;

    public ListChildrenAsyncTask(Context context, Database db, ProgressBar progressBar, ListView listView) {
        this.context = context;
        this.db = db;
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
        String id = params[0];
        return db.readChildItems(id);
    }

    @Override
    protected void onPostExecute(final List<MediaItem> children) {
        progressBar.setVisibility(View.GONE);
        ListAdapterSummary adapter = new ListAdapterSummary(context, R.layout.list_item_generic_toggle, children, db);
        listView.setAdapter(adapter);
        listView.setSelection(children.size());
    }
}
