package uk.co.ourfriendirony.medianotifier.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifierReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
//            Intent serviceIntent = new Intent("your.package.MyService");
//            context.startService(serviceIntent);
//        }
    }
}
