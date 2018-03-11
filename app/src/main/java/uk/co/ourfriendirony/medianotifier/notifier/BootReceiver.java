package uk.co.ourfriendirony.medianotifier.notifier;

import android.content.*;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmScheduler.reschedule(context);
        }
    }
}
