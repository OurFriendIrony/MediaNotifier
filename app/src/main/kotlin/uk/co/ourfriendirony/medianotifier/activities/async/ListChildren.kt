package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.AsyncTask
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.lang.ref.WeakReference

class ListChildren(context: Context, progressBar: ProgressBar?, listView: ListView?, db: Database?) : AsyncTask<String?, Void?, List<MediaItem?>>() {
    private val context: WeakReference<Context>
    private val progressBar: WeakReference<ProgressBar?>
    private val listView: WeakReference<ListView?>
    private val db: Database?
    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.get()!!.isIndeterminate = true
    }


    override fun onPostExecute(children: List<MediaItem?>) {
        progressBar.get()!!.isIndeterminate = false
        val adapter: ArrayAdapter<*> = ListAdapterSummary(context.get(), R.layout.list_item_generic_toggle, children, db)
        listView.get()!!.adapter = adapter
        listView.get()!!.setSelection(children.size)
    }

    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.listView = WeakReference(listView)
        this.db = db
    }

    override fun doInBackground(vararg params: String?): List<MediaItem?> {
        val id = params[0]
        return db!!.readChildItems(id!!)
    }
}