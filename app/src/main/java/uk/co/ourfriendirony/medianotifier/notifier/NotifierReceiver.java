package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.ActivityTVNotifications;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;

public class NotifierReceiver extends BroadcastReceiver {
    int unwatchedEpisodes = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(context));
        unwatchedEpisodes = database.countUnwatchedEpisodes();

        if (unwatchedEpisodes > 0) {
            NotificationCompat.Builder notification = getBuilder(context);

            Intent activityTVNotification = new Intent(context, ActivityTVNotifications.class);
            PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, activityTVNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(notificationIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 1;
            notificationManager.notify(notificationId, notification.build());
        }
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.img_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text) + unwatchedEpisodes)
                .setDefaults(Notification.DEFAULT_ALL);
    }
}
