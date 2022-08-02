package uk.co.ourfriendirony.medianotifier.notifier

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.ActivityUnplayed
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase
import uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST
import uk.co.ourfriendirony.medianotifier.general.Constants.GAME
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE
import uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW
import java.text.MessageFormat

class NotifierReceiver : BroadcastReceiver() {
    var unplayedEpisodes = 0
    var unplayedMovies = 0
    var unplayedAlbums = 0
    var unplayedGames = 0
    var unplayedTotal = 0
    override fun onReceive(context: Context, intent: Intent) {
        unplayedEpisodes = TVShowDatabase(context).countUnplayedReleased()
        unplayedMovies = MovieDatabase(context).countUnplayedReleased()
        unplayedAlbums = ArtistDatabase(context).countUnplayedReleased()
        unplayedGames = GameDatabase(context).countUnplayedReleased()
        unplayedTotal = unplayedEpisodes + unplayedMovies + unplayedAlbums + unplayedGames
        if (unplayedTotal > 0) {
            val notification = getBuilder(context)
            val notificationIntent = PendingIntent.getActivity(
                    context, 0,
                    getNotificationIntent(context),
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            notification.setContentIntent(notificationIntent)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = 1
            notificationManager.notify(notificationId, notification.build())
        }
    }

    private fun getNotificationIntent(context: Context): Intent {
        if (unplayedEpisodes > 0) {
            return Intent(context, ActivityUnplayed::class.java).putExtra(INTENT_KEY, TVSHOW)
        } else if (unplayedMovies > 0) {
            return Intent(context, ActivityUnplayed::class.java).putExtra(INTENT_KEY, MOVIE)
        } else if (unplayedAlbums > 0) {
            return Intent(context, ActivityUnplayed::class.java).putExtra(INTENT_KEY, ARTIST)
        } else if (unplayedGames > 0) {
            return Intent(context, ActivityUnplayed::class.java).putExtra(INTENT_KEY, GAME)
        }
        return Intent(context, ActivityUnplayed::class.java).putExtra(INTENT_KEY, TVSHOW)
    }

    private fun getBuilder(context: Context): NotificationCompat.Builder {
        val text = MessageFormat.format("Episodes: {1}  /  Movies: {2}  /  Albums: {3}  /  Games: {4}",
                context.getString(R.string.notification_text),
                unplayedEpisodes, unplayedMovies, unplayedAlbums, unplayedGames
        )
        return NotificationCompat.Builder(context, "MediaNotifier")
                .setSmallIcon(R.drawable.img_app_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL)
    }
}