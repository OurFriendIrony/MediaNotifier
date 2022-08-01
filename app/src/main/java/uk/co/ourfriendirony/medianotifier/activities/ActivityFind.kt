package uk.co.ourfriendirony.medianotifier.activities

import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.async.AddMediaItem
import uk.co.ourfriendirony.medianotifier.activities.async.FindMediaItem
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY

class ActivityFind : AppCompatActivity() {
    private var input: EditText? = null
    private var progressBar: ProgressBar? = null
    private var listView: ListView? = null
    private var client: Client? = null
    private var db: Database? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)
        val intentKey = intent.extras!!.getString(INTENT_KEY)
        supportActionBar!!.title = "Find $intentKey"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = DatabaseFactory().getDatabase(applicationContext, intentKey!!)
        client = ClientFactory().getClient(intentKey)
        input = findViewById(R.id.find_input)
        progressBar = findViewById(R.id.find_progress)
        listView = findViewById(R.id.find_list)

        input?.setOnEditorActionListener(
            OnEditorActionListener { textView: TextView, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val input = textView.text.toString()
                if ("" != input) {
                    FindMediaItem(baseContext, progressBar, listView, db, client).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, input)
                }
                return@OnEditorActionListener true
            } else {
                return@OnEditorActionListener false
            }
        })
        listView?.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long ->
                val textViewID = view.findViewById<TextView>(R.id.list_item_generic_id)
                val textViewTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
                AddMediaItem(applicationContext, progressBar, db, client).execute(
                    textViewID.text.toString(),
                    textViewTitle.text.toString()
                )
            }
    }
}