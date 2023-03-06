package uk.co.ourfriendirony.medianotifier.activities.fragment

import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem

class LibraryUnplayedComingSoon : Library() {
    override val type: String
        get() = t

    override fun getItems(db: Database?): List<MediaItem?> {
        return db!!.unplayedTotal
    }

    companion object {
        var t = "ComingSoon"
    }
}
