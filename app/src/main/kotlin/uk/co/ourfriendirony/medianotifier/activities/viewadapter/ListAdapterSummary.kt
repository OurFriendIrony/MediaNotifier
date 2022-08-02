package uk.co.ourfriendirony.medianotifier.activities.viewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
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
        return when (defaultLayoutId) {
            R.layout.list_item_generic -> {
                getFindView(position, originalView, parent)
            }
            R.layout.list_item_generic_toggle -> {
                getChecklistView(position, originalView, parent)
            }
            else -> {
                getTitleView(position, originalView, parent)
            }
        }
    }

    override fun getDropDownView(position: Int, originalView: View?, parent: ViewGroup): View {
        return getFindView(position, originalView, parent)
    }

    private fun getFindView(position: Int, originalView: View?, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view = originalView
        view = inflater.inflate(R.layout.list_item_generic, null)

        val textId = view.findViewById<TextView>(R.id.list_item_generic_id)
        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
        val textDate = view.findViewById<TextView>(R.id.list_item_generic_date)
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        val mediaItem = mediaItems[position]
        textId.text = mediaItem!!.id
        textTitle.text = mediaItem.title
        textDate.text = mediaItem.releaseDateYear
        textOverview.text = mediaItem.description
        return view
    }

    private fun getChecklistView(position: Int, originalView: View?, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view = originalView
        view = inflater.inflate(R.layout.list_item_generic_toggle, null)

        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
        val textSubTitle = view.findViewById<TextView>(R.id.list_item_generic_subtitle)
        val textDate = view.findViewById<TextView>(R.id.list_item_generic_date)
        val textOverview = view.findViewById<TextView>(R.id.list_item_generic_overview)
        val mediaItem = mediaItems[position]
        textTitle.text = mediaItem!!.title
        textSubTitle.text = mediaItem.subtitle
        textDate.text = mediaItem.releaseDateFull
        textOverview.text = ""

        view.setOnClickListener(View.OnClickListener { subView: View ->
            val overview = subView.findViewById<TextView>(R.id.list_item_generic_overview)
            val t = if (overview.text === "") mediaItem.description else ""
            overview.text = t
        })
        view.setOnLongClickListener(OnLongClickListener { subView: View ->
            val overview = subView.findViewById<TextView>(R.id.list_item_generic_overview)
            val t = if (overview.text === "") mediaItem.description else ""
            overview.text = t
            true
        })

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

    private fun getTitleView(position: Int, originalView: View?, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view = originalView
        view = inflater.inflate(defaultLayoutId, null)

        val textTitle = view.findViewById<TextView>(R.id.list_item_generic_title)
        val mediaItem = mediaItems[position]
        textTitle.text = mediaItem!!.title
        return view
    }
}