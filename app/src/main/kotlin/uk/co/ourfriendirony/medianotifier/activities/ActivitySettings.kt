package uk.co.ourfriendirony.medianotifier.activities

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.squareup.tape2.QueueFile
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetArtist
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetGame
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetTV
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationHour
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationMinute
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationTimeFull
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setMarkWatchedIfAlreadyReleased
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetArtist
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetGame
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetMovie
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetTV
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationHour
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationMinute
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase
import uk.co.ourfriendirony.medianotifier.general.Constants
import uk.co.ourfriendirony.medianotifier.notifier.AlarmScheduler.reschedule
import java.io.File

class ActivitySettings : AppCompatActivity() {
    private var popupWindow: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val file = File(filesDir, Constants.QUEUE_FILENAME)
        val queueFile = QueueFile.Builder(file).build()

        Toast.makeText(baseContext, "${queueFile.size()} - "+queueFile.remove().toString(), Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.setTitle(R.string.title_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Load Page Objects
        val toggleMarkWatched = findViewById<SwitchCompat>(R.id.settings_played_toggle)
        val buttonNotifyTimer = findViewById<Button>(R.id.settings_notification_time_button)
        val buttonNotifyOffsetTV = findViewById<Button>(R.id.settings_notification_day_offset_tv_button)
        val buttonNotifyOffsetMovie = findViewById<Button>(R.id.settings_notification_day_offset_movie_button)
        val buttonNotifyOffsetArtist = findViewById<Button>(R.id.settings_notification_day_offset_artist_button)
        val buttonNotifyOffsetGame = findViewById<Button>(R.id.settings_notification_day_offset_game_button)
        val buttonDeleteTV = findViewById<Button>(R.id.settings_button_delete_tv_all)
        val buttonDeleteMovie = findViewById<Button>(R.id.settings_button_delete_movie_all)
        val buttonDeleteArtist = findViewById<Button>(R.id.settings_button_delete_artist_all)
        val buttonDeleteGame = findViewById<Button>(R.id.settings_button_delete_game_all)
        val customUrlParent = findViewById<EditText>(R.id.settings_custom_parent_input)
        val customUrlChild = findViewById<EditText>(R.id.settings_custom_child_input)

        // Set Object Current Values
        toggleMarkWatched.isChecked = getMarkWatchedIfAlreadyReleased(baseContext)
        buttonNotifyTimer.text = getNotificationTimeFull(baseContext)
        buttonNotifyOffsetTV.text = getNotificationDayOffsetTV(baseContext).toString()
        buttonNotifyOffsetMovie.text = getNotificationDayOffsetMovie(baseContext).toString()
        buttonNotifyOffsetArtist.text = getNotificationDayOffsetArtist(baseContext).toString()
        buttonNotifyOffsetGame.text = getNotificationDayOffsetGame(baseContext).toString()
        customUrlParent.setText(PropertyHelper.getCustomUrlParent(baseContext))
        customUrlChild.setText(PropertyHelper.getCustomUrlChild(baseContext))
        buttonDeleteTV.text = resources.getString(R.string.button_delete_tv_all)
        buttonDeleteMovie.text = resources.getString(R.string.button_delete_movie_all)
        buttonDeleteArtist.text = resources.getString(R.string.button_delete_artist_all)
        buttonDeleteGame.text = resources.getString(R.string.button_delete_game_all)

        customUrlParent.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            if (!hasFocus) {
                PropertyHelper.setCustomUrlParent(baseContext, customUrlParent.text.toString())
            }
        }
        customUrlChild.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            if (!hasFocus) {
                PropertyHelper.setCustomUrlChild(baseContext, customUrlChild.text.toString())
            }
        }
        // Define Object Actions
        toggleMarkWatched.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            setMarkWatchedIfAlreadyReleased(baseContext, isChecked)
        }
        buttonDeleteTV.setOnClickListener {
            TVShowDatabase(applicationContext).deleteAll()
            Toast.makeText(this@ActivitySettings, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show()
        }
        buttonDeleteMovie.setOnClickListener {
            MovieDatabase(applicationContext).deleteAll()
            Toast.makeText(this@ActivitySettings, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show()
        }
        buttonDeleteArtist.setOnClickListener {
            ArtistDatabase(applicationContext).deleteAll()
            Toast.makeText(this@ActivitySettings, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show()
        }
        buttonDeleteGame.setOnClickListener {
            GameDatabase(applicationContext).deleteAll()
            Toast.makeText(this@ActivitySettings, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show()
        }
        buttonNotifyTimer.setOnClickListener {
            val inflater = this@ActivitySettings.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_time_selector, findViewById(R.id.popup))
            popupWindow = PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val timePicker = popupWindow!!.contentView.findViewById<TimePicker>(R.id.popup_time_picker)
            timePicker.setIs24HourView(true)
            timePicker.hour = getNotificationHour(applicationContext)
            timePicker.minute = getNotificationMinute(applicationContext)
            val buttonOk = popupWindow!!.contentView.findViewById<Button>(R.id.popup_ok)
            buttonOk.setOnClickListener {
                setNotificationHour(applicationContext, timePicker.hour)
                setNotificationMinute(applicationContext, timePicker.minute)
                reschedule(applicationContext)
                popupWindow!!.dismiss()
                buttonNotifyTimer.text = getNotificationTimeFull(baseContext)
            }
        }
        buttonNotifyOffsetTV.setOnClickListener {
            val inflater =
                this@ActivitySettings.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup))
            popupWindow = PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val picker = popupWindow!!.contentView.findViewById<NumberPicker>(R.id.popup_date_picker)
            picker.maxValue = PropertyHelper.notificationDayOffsetMax
            picker.minValue = PropertyHelper.notificationDayOffsetMin
            picker.value = getNotificationDayOffsetTV(applicationContext)
            picker.wrapSelectorWheel = false
            val buttonOk = popupWindow!!.contentView.findViewById<Button>(R.id.popup_ok)
            buttonOk.setOnClickListener {
                setNotificationDayOffsetTV(applicationContext, picker.value)
                popupWindow!!.dismiss()
                buttonNotifyOffsetTV.text = getNotificationDayOffsetTV(baseContext).toString()
            }
        }
        buttonNotifyOffsetMovie.setOnClickListener {
            val inflater = this@ActivitySettings.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup))
            popupWindow = PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val picker = popupWindow!!.contentView.findViewById<NumberPicker>(R.id.popup_date_picker)
            picker.maxValue = PropertyHelper.notificationDayOffsetMax
            picker.minValue = PropertyHelper.notificationDayOffsetMin
            picker.value = getNotificationDayOffsetMovie(applicationContext)
            picker.wrapSelectorWheel = false
            val buttonOk = popupWindow!!.contentView.findViewById<Button>(R.id.popup_ok)
            buttonOk.setOnClickListener {
                setNotificationDayOffsetMovie(applicationContext, picker.value)
                popupWindow!!.dismiss()
                buttonNotifyOffsetMovie.text = getNotificationDayOffsetMovie(baseContext).toString()
            }
        }
        buttonNotifyOffsetArtist.setOnClickListener {
            val inflater = this@ActivitySettings.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup))
            popupWindow = PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val picker = popupWindow!!.contentView.findViewById<NumberPicker>(R.id.popup_date_picker)
            picker.maxValue = PropertyHelper.notificationDayOffsetMax
            picker.minValue = PropertyHelper.notificationDayOffsetMin
            picker.value = getNotificationDayOffsetArtist(applicationContext)
            picker.wrapSelectorWheel = false
            val buttonOk = popupWindow!!.contentView.findViewById<Button>(R.id.popup_ok)
            buttonOk.setOnClickListener {
                setNotificationDayOffsetArtist(applicationContext, picker.value)
                popupWindow!!.dismiss()
                buttonNotifyOffsetArtist.text = getNotificationDayOffsetArtist(baseContext).toString()
            }
        }
        buttonNotifyOffsetGame.setOnClickListener {
            val inflater = this@ActivitySettings.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup))
            popupWindow = PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val picker = popupWindow!!.contentView.findViewById<NumberPicker>(R.id.popup_date_picker)
            picker.maxValue = PropertyHelper.notificationDayOffsetMax
            picker.minValue = PropertyHelper.notificationDayOffsetMin
            picker.value = getNotificationDayOffsetGame(applicationContext)
            picker.wrapSelectorWheel = false
            val buttonOk = popupWindow!!.contentView.findViewById<Button>(R.id.popup_ok)
            buttonOk.setOnClickListener {
                setNotificationDayOffsetGame(applicationContext, picker.value)
                popupWindow!!.dismiss()
                buttonNotifyOffsetGame.text = getNotificationDayOffsetGame(baseContext).toString()
            }
        }
    }
}