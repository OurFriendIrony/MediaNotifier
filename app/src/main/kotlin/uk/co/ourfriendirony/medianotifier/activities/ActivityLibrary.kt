package uk.co.ourfriendirony.medianotifier.activities

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.async.ListChildren
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getWebPageIntent
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class ActivityLibrary : AppCompatActivity() {
    private var spinnerView: Spinner? = null
    private var listView: ListView? = null
    private var mediaItems: List<MediaItem?>? = null
    private var progressBar: ProgressBar? = null
    private var currentItemPos = 0
    private var db: Database? = null
    private var client: Client? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val intentKey = intent.extras!!.getString(INTENT_KEY)
        supportActionBar!!.title = "$intentKey Library"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = DatabaseFactory().getDatabase(baseContext, intentKey!!)
        client = ClientFactory().getClient(intentKey)
        spinnerView = findViewById(R.id.spinner)
        listView = findViewById(R.id.list)
        progressBar = findViewById(R.id.progress)
        spinnerView!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, itemPos: Int, id: Long) {
                currentItemPos = itemPos
                ListChildren(parent.context, progressBar, listView, db)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItems!![itemPos]!!.id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        mediaItems = db!!.readAllParentItems()
        if (mediaItems!!.isNotEmpty()) {
            progressBar!!.isIndeterminate = true
            val listAdapterSummary: ArrayAdapter<*> = ListAdapterSummary(baseContext, R.layout.list_item_generic_title, mediaItems!!, db)
            spinnerView!!.adapter = listAdapterSummary
        } else {
            findViewById<View>(R.id.spinner_progress).layoutParams = RelativeLayout.LayoutParams(0, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_library, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (mediaItems!!.isNotEmpty()) {
            val mediaItem = mediaItems!![currentItemPos]
            return when (menuItem.itemId) {
                R.id.action_refresh -> {
                    UpdateMediaItem(this@ActivityLibrary, progressBar, db, client)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItem)
                    ListChildren(this@ActivityLibrary, progressBar, listView, db)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaItem!!.id)
                    true
                }
                R.id.action_remove -> {
                    db!!.delete(mediaItem!!.id)
                    recreate()
                    true
                }
                R.id.action_lookup -> {
                    if (mediaItem!!.externalLink != null) {
                        val intent = getWebPageIntent(mediaItem.externalLink)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "No External Link", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> {
                    super.onOptionsItemSelected(menuItem)
                }
            }
        }
        return false
    }
}