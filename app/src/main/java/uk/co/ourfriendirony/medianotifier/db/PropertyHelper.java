package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.content.SharedPreferences;

import uk.co.ourfriendirony.medianotifier.R;

import static android.content.Context.MODE_PRIVATE;

public class PropertyHelper {
    private static String PROP_NAME = String.valueOf(R.string.app_name);

    private static String NOTIFY_HOUR = "notification_hour";
    private static String NOTIFY_MIN = "notification_minute";
    private static String NOTIFY_DAY_OFFSET = "notification_day_offset";

    private static int NOTIFY_HOUR_DEFAULT = 21;
    private static int NOTIFY_MIN_DEFAULT = 0;
    private static int NOTIFY_DAY_OFFSET_DEFAULT = 0;
    private static int NOTIFY_DAY_OFFSET_MAX_DEFAULT = +3;
    private static int NOTIFY_DAY_OFFSET_MIN_DEFAULT = 0;

    public static int getNotificationHour(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        return settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT);
    }

    public static int getNotificationMinute(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        return settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT);
    }

    public static int getNotificationDayOffset(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        return settings.getInt(NOTIFY_DAY_OFFSET, NOTIFY_DAY_OFFSET_DEFAULT);
    }

    public static int getNotificationDayOffsetMax() {
        return NOTIFY_DAY_OFFSET_MAX_DEFAULT;
    }

    public static int getNotificationDayOffsetMin() {
        return NOTIFY_DAY_OFFSET_MIN_DEFAULT;
    }

    public static void setNotificationHour(Context context, int newHour) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(NOTIFY_HOUR, newHour);
        editor.apply();
    }

    public static void setNotificationMinute(Context context, int newMinute) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(NOTIFY_MIN, newMinute);
        editor.apply();
    }

    public static void setNotificationDayOffset(Context context, int newDayOffset) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(NOTIFY_DAY_OFFSET, newDayOffset);
        editor.apply();
    }
}
