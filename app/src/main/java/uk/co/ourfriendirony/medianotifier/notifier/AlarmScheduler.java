package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationMinute;

public class AlarmScheduler {
    public static void reschedule(Context context) {
        Intent dialogIntent = new Intent(context, NotifierReceiver.class);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, dialogIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar triggerTime = Calendar.getInstance();
        triggerTime.set(Calendar.HOUR_OF_DAY, getNotificationHour(context));
        triggerTime.set(Calendar.MINUTE, getNotificationMinute(context));

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
