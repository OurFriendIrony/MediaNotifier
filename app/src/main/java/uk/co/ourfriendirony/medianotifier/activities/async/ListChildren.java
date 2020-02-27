package uk.co.ourfriendirony.medianotifier.activities.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.Database;
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class ListChildren extends AsyncTask<String, Void, List<MediaItem>> {
    private final WeakReference<Context> context;
    private final WeakReference<ProgressBar> progressBar;
    private final WeakReference<ListView> listView;
    private final Database db;

    public ListChildren(Context context, ProgressBar progressBar, ListView listView, Database db) {
        this.context = new WeakReference<>(context);
        this.progressBar = new WeakReference<>(progressBar);
        this.listView = new WeakReference<>(listView);
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected List<MediaItem> doInBackground(String... params) {
        String id = params[0];
        return db.readChildItems(id);
    }

    @Override
    protected void onPostExecute(final List<MediaItem> children) {
        progressBar.get().setVisibility(View.GONE);
        ListAdapterSummary adapter = new ListAdapterSummary(context.get(), R.layout.list_item_generic_toggle, children, db);
        listView.get().setAdapter(adapter);
        listView.get().setSelection(children.size());
    }
}
