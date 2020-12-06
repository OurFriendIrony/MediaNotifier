package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.text.MessageFormat;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.ActivityUnplayed;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

import static uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST;
import static uk.co.ourfriendirony.medianotifier.general.Constants.GAME;
import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;
import static uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE;
import static uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW;

public class NotifierReceiver extends BroadcastReceiver {
    int unplayedEpisodes = 0;
    int unplayedMovies = 0;
    int unplayedAlbums = 0;
    int unplayedGames = 0;
    int unplayedTotal = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        unplayedEpisodes = new TVShowDatabase(context).countUnplayedReleased();
        unplayedMovies = new MovieDatabase(context).countUnplayedReleased();
        unplayedAlbums = new ArtistDatabase(context).countUnplayedReleased();
        unplayedGames = new GameDatabase(context).countUnplayedReleased();
        unplayedTotal = unplayedEpisodes + unplayedMovies + unplayedAlbums + unplayedGames;

        if (unplayedTotal > 0) {
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
        if (unplayedEpisodes > 0) {
            return new Intent(context, ActivityUnplayed.class).putExtra(INTENT_KEY, TVSHOW);
        } else if (unplayedMovies > 0) {
            return new Intent(context, ActivityUnplayed.class).putExtra(INTENT_KEY, MOVIE);
        } else if (unplayedAlbums > 0) {
            return new Intent(context, ActivityUnplayed.class).putExtra(INTENT_KEY, ARTIST);
        } else if (unplayedGames > 0) {
            return new Intent(context, ActivityUnplayed.class).putExtra(INTENT_KEY, GAME);
        }
        return new Intent(context, ActivityUnplayed.class).putExtra(INTENT_KEY, TVSHOW);
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        String text = MessageFormat.format("Episodes: {1}  /  Movies: {2}  /  Albums: {3}  /  Games: {4}",
                context.getString(R.string.notification_text),
                unplayedEpisodes, unplayedMovies, unplayedAlbums, unplayedGames
        );
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.img_app_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL);
    }
}
