package uk.co.ourfriendirony.medianotifier.activities.viewadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.general.Constants
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class MyExpandableListAdapter(
    private val context: Context?,
    private val mediaItems: List<MediaItem?>,
    private val db: Database?,
    private val bottom: ConstraintLayout,
    private val progressBar: ProgressBar

) : BaseExpandableListAdapter() {
    override fun getChild(parentPosition: Int, childPosition: Int): Any {
        return mediaItems[parentPosition]!!.children[childPosition]
    }

    override fun getChildId(parentPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return mediaItems[groupPosition]!!.children.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return mediaItems[groupPosition]!!
    }

    override fun getGroupCount(): Int {
        return mediaItems.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val mediaItem = getGroup(groupPosition) as MediaItem
        return if (mediaItem.isParent) {
            buildParent(convertView, mediaItem)
        } else {
            buildSoloParent(convertView, mediaItem)
        }
    }

    override fun getChildView(parentPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val mediaItem = getChild(parentPosition, childPosition) as MediaItem

        return buildChild(convertView, mediaItem)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    private fun buildChild(convertView: View?, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_child)
        view.findViewById<TextView>(R.id.list_item_generic_subtitle).text = mediaItem.title
        view.findViewById<TextView>(R.id.list_item_generic_date).text = mediaItem.releaseDateFull

        view.setOnClickListener {
            Log.d("CHILD_CLICK", "")
            val childDescription = mediaItem.title + "\n\n" + mediaItem.description
            bottom.findViewById<TextView>(R.id.bottomSheetSubtitle).text = childDescription
            BottomSheetBehavior.from(bottom).state = BottomSheetBehavior.STATE_EXPANDED
        }
        val toggle = view.findViewById<SwitchCompat>(R.id.list_item_toggle)
        toggle.isChecked = !db!!.getWatchedStatusAsBoolean(mediaItem)

        // Can't use 'setOnCheckedChangeListener' as child moving out of focus triggers the function
        toggle.setOnClickListener {
            val isPlayed = if (!toggle.isChecked) Constants.DB_TRUE else Constants.DB_FALSE
            Log.d("TOGGLE isPlayed", mediaItem.subId + ": $isPlayed")
            db.updatePlayedStatus(mediaItem, isPlayed)
        }
        return view
    }

    private fun buildParent(convertView: View?, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_parent)
        view.findViewById<TextView>(R.id.list_item_generic_title).text = mediaItem.title
        return view
    }

    private fun buildSoloParent(convertView: View?, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_solo)
        view.findViewById<TextView>(R.id.list_item_generic_title).text = mediaItem.title
        view.findViewById<TextView>(R.id.list_item_generic_subtitle).text = mediaItem.subtitle
        view.findViewById<TextView>(R.id.list_item_generic_date).text = mediaItem.releaseDateFull
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        textOverview.text = ""

        val toggle = view.findViewById<SwitchCompat>(R.id.list_item_toggle)
        toggle.isChecked = !db!!.getWatchedStatusAsBoolean(mediaItem)

        // Can't use 'setOnCheckedChangeListener' as child moving out of focus triggers the function
        toggle.setOnClickListener {
            val isPlayed = if (!toggle.isChecked) Constants.DB_TRUE else Constants.DB_FALSE
            Log.d("TOGGLE isPlayed", mediaItem.subId + ": $isPlayed")
            db.updatePlayedStatus(mediaItem, isPlayed)
        }
        return view
    }

    private fun getView(convertView: View?, layoutId: Int): View {
        val view: View = if (convertView == null) {
            val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(layoutId, null)
        } else {
            convertView
        }
        return view
    }
}
