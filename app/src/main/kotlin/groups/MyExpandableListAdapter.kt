package groups

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class MyExpandableListAdapter(
    private val context: Context?,
    private val defaultLayoutId: Int,
    private val mediaItems: List<MediaItem?>,
    private val db: Database?

) : BaseExpandableListAdapter() {
    override fun getChild(parentPosition: Int, childPosition: Int): Any {
        return mediaItems[parentPosition]!!.children[childPosition]
    }

    override fun getChildId(parentPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(parentPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val mediaItem = getChild(parentPosition, childPosition) as MediaItem
        return buildChild(convertView!!, mediaItem)
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

    override fun onGroupCollapsed(groupPosition: Int) {
        super.onGroupCollapsed(groupPosition)
        Log.w("GROUP", "Collapsed")
    }

    override fun onGroupExpanded(groupPosition: Int) {
        super.onGroupExpanded(groupPosition)
        Log.w("GROUP", "Expanded")
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val mediaItem = getGroup(groupPosition) as MediaItem
        return if (getChildrenCount(groupPosition) > 0) {
            buildParent(convertView!!, mediaItem)
        } else {
            buildSoloParent(convertView!!, mediaItem)
        }
    }

    private fun getView(convertView: View?, layoutId: Int): View {
        val view: View = if (convertView == null) {
            val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(layoutId, null);
        } else {
            convertView
        }
        return view
    }

    private fun buildChild(convertView: View, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_child)
        view.findViewById<TextView>(R.id.list_item_generic_title).height = 0
        view.findViewById<TextView>(R.id.list_item_generic_subtitle).text = mediaItem.title
        view.findViewById<TextView>(R.id.list_item_generic_date).text = mediaItem.releaseDateFull
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        view.setPadding(10, view.paddingTop, view.paddingRight, view.paddingBottom)

        textOverview.text = ""

        view.setOnLongClickListener {
            textOverview.text = if (textOverview.text === "") mediaItem.description else ""
            true
        }
        return view
    }

    private fun buildParent(convertView: View, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_parent)
        view.findViewById<TextView>(R.id.list_item_generic_subtitle).height = 0
        view.findViewById<TextView>(R.id.list_item_generic_title).text = mediaItem.title
        view.findViewById<TextView>(R.id.list_item_generic_date).text = ""
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        view.setPadding(100, view.paddingTop, view.paddingRight, view.paddingBottom)
        textOverview.text = ""

        return view
    }

    private fun buildSoloParent(convertView: View, mediaItem: MediaItem): View {
        val view: View = getView(convertView, R.layout.list_row_solo)
        view.findViewById<TextView>(R.id.list_item_generic_title).text = mediaItem.title
        view.findViewById<TextView>(R.id.list_item_generic_subtitle).text = mediaItem.subtitle
        view.findViewById<TextView>(R.id.list_item_generic_date).text = mediaItem.releaseDateFull
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        textOverview.text = ""

        view.setOnClickListener {
            textOverview.text = if (textOverview.text === "") mediaItem.description else ""
        }
        return view
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

}