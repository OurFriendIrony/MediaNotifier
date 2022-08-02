package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException
import java.lang.ref.WeakReference

class FindMediaItem(context: Context, progressBar: ProgressBar?, listView: ListView?, db: Database?, client: Client?) : AsyncTask<String?, Void?, List<MediaItem?>>() {
    private val context: WeakReference<Context>
    private val progressBar: WeakReference<ProgressBar?>
    private val listView: WeakReference<ListView?>
    private val db: Database?
    private val client: Client?
    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.get()!!.isIndeterminate = true
    }

    override fun onPostExecute(mediaItems: List<MediaItem?>) {
        progressBar.get()!!.isIndeterminate = false
        if (mediaItems.isNotEmpty()) {
            val adapter: ArrayAdapter<*> = ListAdapterSummary(context.get(), R.layout.list_item_generic, mediaItems, db)
            listView.get()!!.adapter = adapter
        } else {
            Toast.makeText(context.get(), R.string.toast_no_results, Toast.LENGTH_LONG).show()
        }
    }

    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.listView = WeakReference(listView)
        this.db = db
        this.client = client
    }

    override fun doInBackground(vararg params: String?): List<MediaItem?> {
        val query = params[0]
        return try {
            client!!.searchMediaItem(query)
        } catch (e: IOException) {
            Log.e("Exception", e.localizedMessage!!)
            ArrayList()
        }
    }
}