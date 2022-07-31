package uk.co.ourfriendirony.medianotifier.db

import android.content.Context
import android.content.SharedPreferences
import uk.co.ourfriendirony.medianotifier.R
import java.util.*

object PropertyHelper {
    private const val PROP_NAME = R.string.app_name.toString()
    private const val MARK_PLAYED = "mark_watched"
    private const val NOTIFY_HOUR = "notification_hour"
    private const val NOTIFY_MIN = "notification_minute"
    private const val NOTIFY_DAY_OFFSET_TV = "notification_day_offset_tv"
    private const val NOTIFY_DAY_OFFSET_MOVIE = "notification_day_offset_movie"
    private const val NOTIFY_DAY_OFFSET_ARTIST = "notification_day_offset_artist"
    private const val NOTIFY_DAY_OFFSET_GAME = "notification_day_offset_game"
    private const val MARK_WATCHED_DEFAULT = true
    private const val NOTIFY_HOUR_DEFAULT = 21
    private const val NOTIFY_MIN_DEFAULT = 0
    private const val NOTIFY_DAY_OFFSET_DEFAULT = 0
    const val notificationDayOffsetMax = +90
    const val notificationDayOffsetMin = 0

    @JvmStatic
    fun getMarkWatchedIfAlreadyReleased(context: Context): Boolean {
        val settings = getSharedPreferences(context)
        return settings.getBoolean(MARK_PLAYED, MARK_WATCHED_DEFAULT)
    }

    @JvmStatic
    fun getNotificationTimeFull(context: Context): String {
        val settings = getSharedPreferences(context)
        return String.format(Locale.UK, "%02d", settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT)) + ":" + String.format(Locale.UK, "%02d", settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT))
    }

    @JvmStatic
    fun getNotificationHour(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_HOUR, NOTIFY_HOUR_DEFAULT)
    }

    @JvmStatic
    fun getNotificationMinute(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_MIN, NOTIFY_MIN_DEFAULT)
    }

    @JvmStatic
    fun getNotificationDayOffsetTV(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_DAY_OFFSET_TV, NOTIFY_DAY_OFFSET_DEFAULT)
    }

    @JvmStatic
    fun getNotificationDayOffsetMovie(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_DAY_OFFSET_MOVIE, NOTIFY_DAY_OFFSET_DEFAULT)
    }

    @JvmStatic
    fun getNotificationDayOffsetArtist(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_DAY_OFFSET_ARTIST, NOTIFY_DAY_OFFSET_DEFAULT)
    }

    @JvmStatic
    fun getNotificationDayOffsetGame(context: Context): Int {
        val settings = getSharedPreferences(context)
        return settings.getInt(NOTIFY_DAY_OFFSET_GAME, NOTIFY_DAY_OFFSET_DEFAULT)
    }

    @JvmStatic
    fun setMarkWatchedIfAlreadyReleased(context: Context, markWatched: Boolean) {
        val editor = getSharedPreferencesEditor(context)
        editor.putBoolean(MARK_PLAYED, markWatched)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationHour(context: Context, newHour: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_HOUR, newHour)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationMinute(context: Context, newMinute: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_MIN, newMinute)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationDayOffsetTV(context: Context, newDayOffset: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_DAY_OFFSET_TV, newDayOffset)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationDayOffsetMovie(context: Context, newDayOffset: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_DAY_OFFSET_MOVIE, newDayOffset)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationDayOffsetArtist(context: Context, newDayOffset: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_DAY_OFFSET_ARTIST, newDayOffset)
        editor.apply()
    }

    @JvmStatic
    fun setNotificationDayOffsetGame(context: Context, newDayOffset: Int) {
        val editor = getSharedPreferencesEditor(context)
        editor.putInt(NOTIFY_DAY_OFFSET_GAME, newDayOffset)
        editor.apply()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PROP_NAME, Context.MODE_PRIVATE)
    }

    private fun getSharedPreferencesEditor(context: Context): SharedPreferences.Editor {
        return getSharedPreferences(context).edit()
    }
}