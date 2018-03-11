package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.*;
import android.content.*;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.text.MessageFormat;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.movie.ActivityMovieUnwatched;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVUnwatched;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

public class NotifierReceiver extends BroadcastReceiver {
    int unwatchedEpisodes = 0;
    int unwatchedMovies = 0;
    int unwatchedTotal = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        unwatchedEpisodes = new TVShowDatabase(context).countUnwatchedEpisodesReleased();
        unwatchedMovies = new MovieDatabase(context).countUnwatchedMoviesReleased();
        unwatchedTotal = unwatchedEpisodes + unwatchedMovies;

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
        if (unwatchedEpisodes > 0)
            return new Intent(context, ActivityTVUnwatched.class);
        else if (unwatchedMovies > 0)
            return new Intent(context, ActivityMovieUnwatched.class);
        return new Intent(context, ActivityTVUnwatched.class);
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        String text = MessageFormat.format("Episodes: {1}  /  Movies: {2}  /  Albums: {3}",
                context.getString(R.string.notification_text),
                unwatchedEpisodes, unwatchedMovies, 0
        );
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.img_app_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL);
    }
}
