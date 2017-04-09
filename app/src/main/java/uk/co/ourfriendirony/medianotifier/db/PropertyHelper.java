package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.content.SharedPreferences;

import uk.co.ourfriendirony.medianotifier.R;

import static android.content.Context.MODE_PRIVATE;

public class PropertyHelper {
    private static String PROP_NAME = String.valueOf(R.string.app_name);
    private static String NOTIFY_HOUR = "notifcation_hour";
    private static String NOTIFY_MIN = "notifcation_minute";
    private static int NOTIFY_HOUR_DEFAULT = 21;
    private static int NOTIFY_MIN_DEFAULT = 0;

    public static int getHour(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        return settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT);
    }

    public static int getMinute(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        return settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT);
    }

    public static void setHour(Context context, int newHour) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(NOTIFY_HOUR, newHour);
        editor.commit();
    }

    public static void setMinute(Context context, int newMinute) {
        SharedPreferences settings = context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(NOTIFY_MIN, newMinute);
        editor.commit();
    }
}
