package uk.co.ourfriendirony.medianotifier.activities.viewadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.general.Constants.DB_FALSE
import uk.co.ourfriendirony.medianotifier.general.Constants.DB_TRUE
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class ListAdapterSummary(
    context: Context?,
    private val defaultLayoutId: Int,
    private val mediaItems: List<MediaItem?>,
    private val db: Database?
) : ArrayAdapter<Any?>(context!!, defaultLayoutId, mediaItems) {
    override fun getView(position: Int, originalView: View?, parent: ViewGroup): View {
        val mediaItem = mediaItems[position]!!

        return when (defaultLayoutId) {
            R.layout.list_item_generic -> {
                getFindView(mediaItem)
            }
            R.layout.list_item_generic_toggle -> {
                getChecklistView(mediaItem)
            }
            else -> {
                getTitleView(mediaItem)
            }
        }
    }

    override fun getDropDownView(position: Int, originalView: View?, parent: ViewGroup): View {
        val mediaItem = mediaItems[position]
        return getFindView(mediaItem!!)
    }

    private fun getFindView(mediaItem: MediaItem): View {
        val view = View.inflate(context, R.layout.list_item_generic, null)

        val textId = view.findViewById<TextView>(R.id.list_item_generic_id)
        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
        val textDate = view.findViewById<TextView>(R.id.list_item_generic_date)
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)

        textId.text = mediaItem.id
        textTitle.text = mediaItem.title
        textDate.text = mediaItem.releaseDateYear
        textOverview.text = mediaItem.description
        return view
    }

    private fun getChecklistView(mediaItem: MediaItem): View {
        val view = View.inflate(context, R.layout.list_item_generic_toggle, null)

        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
        val textSubTitle = view.findViewById<TextView>(R.id.list_item_generic_subtitle)
        val textDate = view.findViewById<TextView>(R.id.list_item_generic_date)
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)

        textTitle.text = mediaItem.title
        textSubTitle.text = mediaItem.subtitle
        textDate.text = mediaItem.releaseDateFull
        textOverview.text = ""

        view.setOnClickListener { subView: View ->
            val overview = subView.findViewById<TextView>(R.id.list_item_generic_overview)
            val t = if (overview.text === "") mediaItem.description else ""
            overview.text = t
        }
        view.setOnLongClickListener { subView: View ->
            val overview = subView.findViewById<TextView>(R.id.list_item_generic_overview)
            val t = if (overview.text === "") mediaItem.description else ""
            overview.text = t
            true
        }

        val toggle = view.findViewById<SwitchCompat>(R.id.list_item_toggle)
        toggle.isChecked = !db!!.getWatchedStatusAsBoolean(mediaItem)
        toggle.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            db.updatePlayedStatus(
                mediaItem,
                if (!isChecked) DB_TRUE else DB_FALSE
            )
        }
        return view
    }

    private fun getTitleView(mediaItem: MediaItem): View {
        val view = View.inflate(context, defaultLayoutId, null)
        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)

        textTitle.text = mediaItem.title
        return view
    }
}