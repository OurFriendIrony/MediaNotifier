package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.R;

import static android.content.Context.MODE_PRIVATE;

public class PropertyHelper {
    private static final String PROP_NAME = String.valueOf(R.string.app_name);

    private static final String MARK_PLAYED = "mark_watched";
    private static final String NOTIFY_HOUR = "notification_hour";
    private static final String NOTIFY_MIN = "notification_minute";
    private static final String NOTIFY_DAY_OFFSET_TV = "notification_day_offset_tv";
    private static final String NOTIFY_DAY_OFFSET_MOVIE = "notification_day_offset_movie";
    private static final String NOTIFY_DAY_OFFSET_ARTIST = "notification_day_offset_artist";
    private static final String NOTIFY_DAY_OFFSET_GAME = "notification_day_offset_game";

    private static final boolean MARK_WATCHED_DEFAULT = true;
    private static final int NOTIFY_HOUR_DEFAULT = 21;
    private static final int NOTIFY_MIN_DEFAULT = 0;
    private static final int NOTIFY_DAY_OFFSET_DEFAULT = 0;
    private static final int NOTIFY_DAY_OFFSET_MAX_DEFAULT = +90;
    private static final int NOTIFY_DAY_OFFSET_MIN_DEFAULT = 0;

    public static boolean getMarkWatchedIfAlreadyReleased(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getBoolean(MARK_PLAYED, MARK_WATCHED_DEFAULT);
    }

    public static String getNotificationTimeFull(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return String.format(Locale.UK, "%02d", settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT)) + ":" + String.format(Locale.UK, "%02d", settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT));
    }

    public static int getNotificationHour(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT);
    }

    public static int getNotificationMinute(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT);
    }

    public static int getNotificationDayOffsetTV(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_DAY_OFFSET_TV, NOTIFY_DAY_OFFSET_DEFAULT);
    }

    public static int getNotificationDayOffsetMovie(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_DAY_OFFSET_MOVIE, NOTIFY_DAY_OFFSET_DEFAULT);
    }

    public static int getNotificationDayOffsetArtist(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_DAY_OFFSET_ARTIST, NOTIFY_DAY_OFFSET_DEFAULT);
    }

    public static int getNotificationDayOffsetGame(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(NOTIFY_DAY_OFFSET_GAME, NOTIFY_DAY_OFFSET_DEFAULT);
    }

    public static int getNotificationDayOffsetMax() {
        return NOTIFY_DAY_OFFSET_MAX_DEFAULT;
    }

    public static int getNotificationDayOffsetMin() {
        return NOTIFY_DAY_OFFSET_MIN_DEFAULT;
    }

    public static void setMarkWatchedIfAlreadyReleased(Context context, boolean markWatched) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putBoolean(MARK_PLAYED, markWatched);
        editor.apply();
    }

    public static void setNotificationHour(Context context, int newHour) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_HOUR, newHour);
        editor.apply();
    }

    public static void setNotificationMinute(Context context, int newMinute) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_MIN, newMinute);
        editor.apply();
    }

    public static void setNotificationDayOffsetTV(Context context, int newDayOffset) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_DAY_OFFSET_TV, newDayOffset);
        editor.apply();
    }

    public static void setNotificationDayOffsetMovie(Context context, int newDayOffset) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_DAY_OFFSET_MOVIE, newDayOffset);
        editor.apply();
    }

    public static void setNotificationDayOffsetArtist(Context context, int newDayOffset) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_DAY_OFFSET_ARTIST, newDayOffset);
        editor.apply();
    }

    public static void setNotificationDayOffsetGame(Context context, int newDayOffset) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(NOTIFY_DAY_OFFSET_GAME, newDayOffset);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PROP_NAME, MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return getSharedPreferences(context).edit();
    }
}
