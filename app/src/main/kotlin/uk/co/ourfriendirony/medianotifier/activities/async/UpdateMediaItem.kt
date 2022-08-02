package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.lang.ref.WeakReference

class UpdateMediaItem(
    context: Context,
    progressBar: ProgressBar?,
    db: Database?,
    client: Client?,
    private val ui: Handler,
    private vararg val mediaItems: MediaItem
) : Runnable {
    private var context: WeakReference<Context>
    private var progressBar: WeakReference<ProgressBar?>
    private var db: Database?
    private var client: Client?
//    private val ui: Handler


    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.db = db
        this.client = client
//        this.ui = ui
//        this.mediaItems = mediaItems
    }


    override fun run() {
        ui.post {
            progressBar.get()!!.isIndeterminate = true
        }

        var failed = 0
        var toastMsg = ""
        toastMsg += if (mediaItems.size == 1) {
            db!!.coreType + " '" + mediaItems[0].title + "' Updated"
        } else {
            "All " + db!!.coreType + " Media Updated"
        }
        for (mediaItem in mediaItems) {
            try {
                db!!.update(client!!.getMediaItem(mediaItem.id))
            } catch (e: Exception) {
                Log.e("[FAILED_UPDATE]", mediaItem.toString() + ": " + e.message)
                failed += 1
            }
        }
        if (failed > 0) {
            toastMsg += " [Failed=$failed]"
        }

        ui.post {
            progressBar.get()!!.isIndeterminate = false
            Toast.makeText(context.get(), toastMsg, Toast.LENGTH_SHORT).show()
        }
    }
}