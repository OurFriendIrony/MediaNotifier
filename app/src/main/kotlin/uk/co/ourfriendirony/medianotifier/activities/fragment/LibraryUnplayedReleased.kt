package uk.co.ourfriendirony.medianotifier.activities.fragment

import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class LibraryUnplayedReleased(override val bottom: ConstraintLayout) : Library() {
    override val type: String
        get() = t

    override fun getItems(db: Database?): List<MediaItem?> {
        return db!!.unplayedReleased
    }

    companion object {
        var t = "Released"
    }
}