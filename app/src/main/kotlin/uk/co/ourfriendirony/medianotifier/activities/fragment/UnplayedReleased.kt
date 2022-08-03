package uk.co.ourfriendirony.medianotifier.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class UnplayedReleased : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.activity_notifications, container, false) as ViewGroup
        val listView = rootView.findViewById<View>(R.id.notification_list) as ListView
        val intentKey = this.requireArguments().getString(INTENT_KEY)
        val db = DatabaseFactory().getDatabase(requireContext(), intentKey!!)
        val mediaItems: List<MediaItem?> = db!!.unplayedReleased

        if (mediaItems.isNotEmpty()) {
            val listAdapterSummary: ArrayAdapter<*> = ListAdapterSummary(context, R.layout.list_item_generic_toggle, mediaItems, db)
            listView.adapter = listAdapterSummary
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.pager_strip)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.pager)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Released"
                1 -> "Coming Soon..."
                else -> null
            }
        }.attach()
    }
}