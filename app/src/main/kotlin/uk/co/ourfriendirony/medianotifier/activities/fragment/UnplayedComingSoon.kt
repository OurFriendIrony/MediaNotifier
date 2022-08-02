package uk.co.ourfriendirony.medianotifier.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.ListAdapterSummary
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class UnplayedComingSoon : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.activity_notifications, container, false) as ViewGroup
        val listView = rootView.findViewById<ListView>(R.id.notification_list)
        val intentKey = this.requireArguments().getString(INTENT_KEY)
        val db = DatabaseFactory().getDatabase(requireContext(), intentKey!!)
        val mediaItems: List<MediaItem?> = db!!.unplayedTotal
        if (mediaItems.isNotEmpty()) {
            val listAdapterSummary: ArrayAdapter<*> = ListAdapterSummary(context, R.layout.list_item_generic_toggle, mediaItems, db)
            listView.adapter = listAdapterSummary
        }
        return rootView
    }
}