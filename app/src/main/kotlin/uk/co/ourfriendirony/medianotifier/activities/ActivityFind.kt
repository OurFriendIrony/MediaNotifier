package uk.co.ourfriendirony.medianotifier.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.async.AddMediaItem
import uk.co.ourfriendirony.medianotifier.activities.async.FindMediaItem
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import java.util.concurrent.Executors

class ActivityFind : AppCompatActivity() {
    private var input: EditText? = null
    private var progressBar: ProgressBar? = null
    private var listView: ListView? = null
    private var client: Client? = null
    private var db: Database? = null

    private val myHandler = Handler(Looper.getMainLooper())

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

        input?.setOnEditorActionListener { textView: TextView, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val input = textView.text.toString()
                if ("" != input) {
                    Executors.newSingleThreadExecutor().execute(
                        FindMediaItem(baseContext, progressBar, listView, db, client, myHandler, input)
                    )
                }
                true
            } else {
                false
            }
        }

        listView?.onItemClickListener = OnItemClickListener { _: AdapterView<*>?, view: View, _: Int, _: Long ->
            val id = view.findViewById<TextView>(R.id.list_item_generic_id).text.toString()
            val title = view.findViewById<TextView>(R.id.list_item_generic_title).text.toString()

            Executors.newSingleThreadExecutor().execute(
                AddMediaItem(applicationContext, progressBar, db, client, myHandler, id, title)
            )
        }
    }
}