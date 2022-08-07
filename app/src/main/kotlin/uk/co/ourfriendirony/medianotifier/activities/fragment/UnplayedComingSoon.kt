package uk.co.ourfriendirony.medianotifier.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import groups.MyExpandableListAdapter
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class UnplayedComingSoon : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.activity_notifications, container, false) as ViewGroup

        val listView = rootView.findViewById(R.id.notification_list) as ExpandableListView

        val intentKey = this.requireArguments().getString(INTENT_KEY)
        val db = DatabaseFactory().getDatabase(requireContext(), intentKey!!)
        val mediaItems: List<MediaItem?> = db!!.unplayedTotal
        if (mediaItems.isNotEmpty()) {
            listView.setAdapter(MyExpandableListAdapter(context, R.layout.list_item_generic_toggle, mediaItems, db))
        }
        return rootView
    }
}