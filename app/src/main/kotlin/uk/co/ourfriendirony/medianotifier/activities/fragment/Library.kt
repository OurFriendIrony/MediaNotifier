package uk.co.ourfriendirony.medianotifier.activities.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem
import uk.co.ourfriendirony.medianotifier.activities.viewadapter.MyExpandableListAdapter
import uk.co.ourfriendirony.medianotifier.clients.Client
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.util.concurrent.Executors

abstract class Library : Fragment() {
    abstract val bottom: ConstraintLayout
    abstract val progressBar: ProgressBar
    abstract val type: String
    abstract fun getItems(db: Database?): List<MediaItem?>
    private val myHandler = Handler(Looper.getMainLooper())
    private lateinit var mediaItems: List<MediaItem?>
    private lateinit var db: Database
    private lateinit var client: Client
    private lateinit var listView: ExpandableListView
    private var allowRefresh = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.activity_notifications, container, false) as ViewGroup
        val intentKey = this.requireArguments().getString(INTENT_KEY)

        val d = DatabaseFactory().getDatabase(requireContext(), intentKey!!)
        val c = ClientFactory().getClient(intentKey)!!
        val m: List<MediaItem?> = getItems(d!!)
        val l: ExpandableListView = rootView.findViewById(R.id.notification_list)

        mediaItems = m
        db = d
        client = c
        listView = l
        rebuild(mediaItems, db, client, listView)

        return rootView
    }

    private fun rebuild(
        mediaItems: List<MediaItem?>,
        db: Database?,
        client: Client,
        listView: ExpandableListView
    ) {
        var lastExpandedPosition = -1
        val adapter = MyExpandableListAdapter(context, mediaItems, db, bottom, progressBar)
        listView.setAdapter(adapter)
        listView.setOnGroupExpandListener { parentPosition ->
            if (parentPosition != lastExpandedPosition) listView.collapseGroup(lastExpandedPosition)
            lastExpandedPosition = parentPosition
            Log.d("PARENT_EXPANDED", "$type: $lastExpandedPosition")
            val mediaItem = mediaItems[lastExpandedPosition]!!
            bottom.findViewById<TextView>(R.id.bottomSheetTitle).text = mediaItem.title
            bottom.findViewById<TextView>(R.id.bottomSheetSubtitle).text = mediaItem.description
            bottom.findViewById<ImageButton>(R.id.action_lookup).setOnClickListener {
                val intent = IntentGenerator.getWebPageIntent(mediaItem.externalLink)
                startActivity(intent)
            }
            bottom.findViewById<ImageButton>(R.id.action_refresh).setOnClickListener {
                Executors.newSingleThreadExecutor().execute(
                    UpdateMediaItem(requireContext(), progressBar, db, client, myHandler, mediaItem)
                )
                this.mediaItems = getItems(db)
                rebuild(this.mediaItems, db, client, listView)
            }
            bottom.findViewById<ImageButton>(R.id.action_remove).setOnClickListener {
                BottomSheetBehavior.from(bottom).state = BottomSheetBehavior.STATE_COLLAPSED
                db!!.delete(mediaItem.id)
                this.mediaItems = getItems(db)
                rebuild(this.mediaItems, db, client, listView)
            }
            BottomSheetBehavior.from(bottom).state = BottomSheetBehavior.STATE_EXPANDED
        }
        listView.setOnGroupCollapseListener {
            Log.d("PARENT_COLLAPSED", "$type: $lastExpandedPosition")
            BottomSheetBehavior.from(bottom).state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.pager_strip)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.pager)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> LibraryUnplayedReleased.t
                1 -> LibraryUnplayedComingSoon.t
                2 -> LibraryFull.t
                else -> null
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (allowRefresh) {
            allowRefresh = false
            rebuild(mediaItems, db, client, listView)
        }
    }

    override fun onPause() {
        super.onPause()
        if (!allowRefresh) allowRefresh = true
    }
}