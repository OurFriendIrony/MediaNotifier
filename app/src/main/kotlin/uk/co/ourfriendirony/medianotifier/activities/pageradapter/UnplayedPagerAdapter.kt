package uk.co.ourfriendirony.medianotifier.activities.pageradapter

import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uk.co.ourfriendirony.medianotifier.activities.fragment.LibraryFull
import uk.co.ourfriendirony.medianotifier.activities.fragment.LibraryUnplayedComingSoon
import uk.co.ourfriendirony.medianotifier.activities.fragment.LibraryUnplayedReleased
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY


class UnplayedPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, type: String?, bottomSheetBehavior: ConstraintLayout) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val bundle = Bundle()
    private var fragment: Fragment? = null
    private var bottom = bottomSheetBehavior

    companion object {
        private const val NUM_PAGES = 3
    }

    init {
        bundle.putString(INTENT_KEY, type)
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> fragment = LibraryUnplayedReleased(bottom)
            1 -> fragment = LibraryUnplayedComingSoon(bottom)
            2 -> fragment = LibraryFull(bottom)
            else -> Log.e("[FRAGMENT]", "Invalid Fragment Index")
        }
        fragment!!.arguments = bundle
        return fragment!!
    }

    override fun getItemId(position: Int): Long {
        Log.d("PAGER_ADAPTER", "fragment: $position")
        return super.getItemId(position)
    }

}