package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.text.MessageFormat;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.artist.ActivityArtistUnwatched;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieUnwatched;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVUnwatched;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

public class NotifierReceiver extends BroadcastReceiver {
    int unwatchedEpisodes = 0;
    int unwatchedMovies = 0;
    int unwatchedAlbums = 0;
    int unwatchedTotal = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        unwatchedEpisodes = new TVShowDatabase(context).countUnwatchedReleased();
        unwatchedMovies = new MovieDatabase(context).countUnwatchedReleased();
        unwatchedAlbums = new ArtistDatabase(context).countUnwatchedReleased();
        unwatchedTotal = unwatchedEpisodes + unwatchedMovies + unwatchedAlbums;

        if (unwatchedTotal > 0) {
            NotificationCompat.Builder notification = getBuilder(context);

            PendingIntent notificationIntent = PendingIntent.getActivity(
                    context, 0,
                    getNotificationIntent(context),
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            notification.setContentIntent(notificationIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 1;
            notificationManager.notify(notificationId, notification.build());
        }
    }

    @NonNull
    private Intent getNotificationIntent(Context context) {
        if (unwatchedEpisodes > 0) {
            return new Intent(context, ActivityTVUnwatched.class);
        } else if (unwatchedMovies > 0) {
            return new Intent(context, ActivityMovieUnwatched.class);
        } else if (unwatchedAlbums > 0) {
            return new Intent(context, ActivityArtistUnwatched.class);
        }
        return new Intent(context, ActivityTVUnwatched.class);
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        String text = MessageFormat.format("Episodes: {1}  /  Movies: {2}  /  Albums: {3}",
                context.getString(R.string.notification_text),
                unwatchedEpisodes, unwatchedMovies, unwatchedAlbums
        );
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.img_app_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL);
    }
}
