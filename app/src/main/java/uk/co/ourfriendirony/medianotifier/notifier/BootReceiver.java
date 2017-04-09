package uk.co.ourfriendirony.medianotifier.notifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMinute;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmScheduler.reschedule(context);
        }
    }
}
