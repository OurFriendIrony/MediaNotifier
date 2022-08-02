package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.io.IOException
import java.lang.ref.WeakReference

class AddMediaItem(
    context: Context,
    progressBar: ProgressBar?,
    db: Database?,
    client: Client?,
    ui: Handler,
    id: String,
    title: String
) : Runnable {
    private val context: WeakReference<Context>
    private val progressBar: WeakReference<ProgressBar?>
    private val db: Database?
    private val client: Client?
    private val ui: Handler
    private val id: String
    private val title: String

    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.db = db
        this.client = client
        this.ui = ui
        this.id = id
        this.title = title
    }

    override fun run() {
        val mediaItem: MediaItem

        ui.post {
            progressBar.get()!!.isIndeterminate = true
        }

        try {
            mediaItem = client!!.getMediaItem(id)
            db!!.add(mediaItem)
        } catch (e: IOException) {
            Log.e("[FAILED_ADD]", e.message!! + e.stackTrace)
        }

        ui.post {
            progressBar.get()!!.isIndeterminate = false
            Toast.makeText(context.get(), db!!.coreType + " '" + title + "' Added", Toast.LENGTH_SHORT).show()
        }
    }
}