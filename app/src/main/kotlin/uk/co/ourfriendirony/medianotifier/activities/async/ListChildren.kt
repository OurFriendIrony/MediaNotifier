package uk.co.ourfriendirony.medianotifier.activities.async

import android.content.Context
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.db.Database
import java.lang.ref.WeakReference

class ListChildren(context: Context, progressBar: ProgressBar?, listView: ListView?, db: Database?, ui: Handler, id: String) : Runnable {
    private val context: WeakReference<Context>
    private val progressBar: WeakReference<ProgressBar?>
    private val listView: WeakReference<ListView?>
    private val db: Database?
    private val ui: Handler
    private val id: String

    init {
        this.context = WeakReference(context)
        this.progressBar = WeakReference(progressBar)
        this.listView = WeakReference(listView)
        this.db = db
        this.ui = ui
        this.id = id
    }

    override fun run() {
        ui.post {
            progressBar.get()!!.isIndeterminate = true
        }

        val children = db!!.readChildItems(id)

        ui.post {
            progressBar.get()!!.isIndeterminate = false
            val adapter: ArrayAdapter<*> = ListAdapterSummary(context.get(), R.layout.list_item_generic_toggle, children, db)
            listView.get()!!.adapter = adapter
            listView.get()!!.setSelection(children.size)
        }
    }
}