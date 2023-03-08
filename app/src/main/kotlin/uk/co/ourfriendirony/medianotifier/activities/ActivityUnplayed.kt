package uk.co.ourfriendirony.medianotifier.activities

import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.pageradapter.UnplayedPagerAdapter
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY

class ActivityUnplayed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val bottomSheetView = findViewById<ConstraintLayout>(R.id.bottomSheet)
        BottomSheetBehavior.from(bottomSheetView).state = BottomSheetBehavior.STATE_COLLAPSED

        val intentKey = intent.extras!!.getString(INTENT_KEY)
        supportActionBar!!.title = "Released " + intentKey + "s"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        cancelNotifications()

        val mPager = findViewById<ViewPager2>(R.id.pager)
        val mPagerAdapter = UnplayedPagerAdapter(supportFragmentManager, lifecycle, intentKey, bottomSheetView)
        mPager.adapter = mPagerAdapter
        mPager.currentItem = 0
    }

    private fun cancelNotifications() {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}