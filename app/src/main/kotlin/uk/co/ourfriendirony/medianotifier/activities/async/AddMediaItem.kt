package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException
import java.lang.ref.WeakReference

class AddMediaItem(context: Context, progressBar: ProgressBar?, db: Database?, client: Client?) : AsyncTask<String?, Void?, String>() {
    private val context: WeakReference<Context>
    private val progressBar: WeakReference<ProgressBar?>
    private val db: Database?
    private val client: Client?
    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.get()!!.isIndeterminate = true
    }


    override fun onPostExecute(toastMsg: String) {
        progressBar.get()!!.isIndeterminate = false
        Toast.makeText(context.get(), toastMsg, Toast.LENGTH_SHORT).show()
    }

    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.db = db
        this.client = client
    }

    override fun doInBackground(vararg params: String?): String {
        val id = params[0]
        val title = params[1]
        val mediaItem: MediaItem
        try {
            mediaItem = client!!.getMediaItem(id)
            db!!.add(mediaItem)
        } catch (e: IOException) {
            Log.e("[FAILED_ADD]", e.message!! + e.stackTrace!!)
        }
        return db!!.coreType + " '" + title + "' Added"
    }
}