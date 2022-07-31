package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.lang.ref.WeakReference

class UpdateMediaItem(context: Context, progressBar: ProgressBar?, db: Database?, client: Client?) : AsyncTask<MediaItem?, Void?, String>() {
    private var context: WeakReference<Context>
    private var progressBar: WeakReference<ProgressBar?>
    private var db: Database?
    private var client: Client?
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

    override fun doInBackground(vararg mediaItems: MediaItem?): String {
        var failed = 0
        var result = ""
        result += if (mediaItems.size == 1) {
            db!!.coreType + " '" + mediaItems[0]!!.title + "' Updated"
        } else {
            "All " + db!!.coreType + " Media Updated"
        }
        for (mediaItem in mediaItems) {
            try {
                db!!.update(client!!.getMediaItem(mediaItem!!.id))
            } catch (e: Exception) {
                Log.e("[FAILED_UPDATE]", mediaItem.toString() + ": " + e.message)
                failed += 1
            }
        }
        if (failed > 0) {
            result += " [Failed=$failed]"
        }
        return result
    }
}