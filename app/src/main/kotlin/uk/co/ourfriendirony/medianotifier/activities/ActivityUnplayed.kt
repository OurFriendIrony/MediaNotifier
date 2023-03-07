package uk.co.ourfriendirony.medianotifier.activities

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.pageradapter.UnplayedPagerAdapter
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY

class ActivityUnplayed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val intentKey = intent.extras!!.getString(INTENT_KEY)
        supportActionBar!!.title = "Released " + intentKey + "s"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        cancelNotifications()

        val mPager = findViewById<ViewPager2>(R.id.pager)
        val mPagerAdapter = UnplayedPagerAdapter(supportFragmentManager, lifecycle, intentKey)
        mPager.adapter = mPagerAdapter
        mPager.currentItem = 0

        val bottomSheetDialog = BottomSheetDialog(applicationContext)
        val view = View.inflate(applicationContext, R.layout.layout_persistent_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        //        val bottomSheetBehavior = bottomSheetDialog.behavior
//
//        val bottomSheetBehavior = BottomSheetBehavior.from(view)
////
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                // handle onSlide
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(this@ActivityMain, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(this@ActivityMain, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(this@ActivityMain, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(this@ActivityMain, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(this@ActivityMain, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
//                    else -> Toast.makeText(this@ActivityMain, "OTHER_STATE", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//        val btnBottomSheetPersistent = findViewById<Button>(R.id.btnBottomSheetPersistent)
////btnBottomSheetPersistent
//        btnBottomSheetPersistent.setOnClickListener {
//            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            else
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }
    }

    private fun cancelNotifications() {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}