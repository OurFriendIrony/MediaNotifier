package uk.co.ourfriendirony.medianotifier.activities.pageradapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedComingSoon
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedReleased
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY

class UnplayedPagerAdapter(fm: FragmentManager?, type: String?) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val bundle = Bundle()
    private var fragment: Fragment? = null
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> fragment = UnplayedReleased()
            1 -> fragment = UnplayedComingSoon()
            else -> Log.e("[FRAGMENT]", "Invalid Fragment Index")
        }
        fragment!!.arguments = bundle
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Released"
            1 -> "Coming Soon..."
            else -> null
        }
    }

    override fun getCount(): Int {
        return NUM_PAGES
    }

    companion object {
        private const val NUM_PAGES = 2
    }

    init {
        bundle.putString(INTENT_KEY, type)
    }
}