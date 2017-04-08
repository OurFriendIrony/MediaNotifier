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

public class NotifierReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        android.support.v4.app.NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.img_icon)
                        .setContentTitle(context.getString(R.string.notification_title))
                        .setContentText(context.getString(R.string.notification_text))
                        .setDefaults(Notification.DEFAULT_ALL);

        Intent activityTVNotification = new Intent(context, ActivityTVNotifications.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, activityTVNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(notificationIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        notificationManager.notify(notificationId, notification.build());
    }
}
