package uk.co.ourfriendirony.medianotifier.notifier

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationHour
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationMinute
import java.util.*

object AlarmScheduler {
    @JvmStatic
    fun reschedule(context: Context) {
        val dialogIntent = Intent(context, NotifierReceiver::class.java)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, 0, dialogIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val triggerTime = Calendar.getInstance()
        triggerTime[Calendar.HOUR_OF_DAY] = getNotificationHour(context)
        triggerTime[Calendar.MINUTE] = getNotificationMinute(context)
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}