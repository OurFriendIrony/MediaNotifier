package uk.co.ourfriendirony.medianotifier.activities.pageradapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedComingSoon
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedReleased
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY

class UnplayedPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, type: String?) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val bundle = Bundle()
    private var fragment: Fragment? = null

    companion object {
        private const val NUM_PAGES = 2
    }

    init {
        bundle.putString(INTENT_KEY, type)
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> fragment = UnplayedReleased()
            1 -> fragment = UnplayedComingSoon()
            else -> Log.e("[FRAGMENT]", "Invalid Fragment Index")
        }
        fragment!!.arguments = bundle
        return fragment!!
    }
}