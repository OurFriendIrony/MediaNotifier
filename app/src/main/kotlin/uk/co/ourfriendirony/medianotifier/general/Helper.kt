package uk.co.ourfriendirony.medianotifier.general

import android.database.Cursor
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    private val PREFIXES = arrayOf("A ", "The ")
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

    @JvmStatic
    fun cleanUrl(url: String): String {
        return try {
            URLEncoder.encode(url, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            url
        }
    }

    @JvmStatic
    fun cleanTitle(string: String): String {
        for (prefix in PREFIXES) {
            if (string.startsWith(prefix)) {
                return string.substring(prefix.length) + ", " + prefix.substring(
                    0,
                    prefix.length - 1
                )
            }
        }
        return string
    }

    @JvmStatic
    fun dateToString(date: Date?): String? {
        return if (date != null) dateFormat.format(date) else null
    }

    @JvmStatic
    fun stringToDate(date: String?): Date? {
        return if (date == null) {
            null
        } else try {
            dateFormat.parse(date)
        } catch (e: Exception) {
            null
        }
    }

    @JvmStatic
    fun replaceTokens(original: String, token: String, value: String): String {
        return replaceTokens(original, arrayOf(token), arrayOf(value))
    }

    @JvmStatic
    fun replaceTokens(s: String, tokens: Array<String>, values: Array<String>): String {
        var string = s
        if (tokens.size == values.size) {
            for (i in tokens.indices) {
                string = string.replace(tokens[i], values[i])
            }
        }
        return string
    }

    @JvmStatic
    fun getNotificationNumber(num: Int): String {
        return if (num <= 9) num.toString() else "9+"
    }

    @JvmStatic
    fun getColumnValue(cursor: Cursor, field: String?): String {
//        Log.d("[FIELD]", "$field")
        val colIndex = cursor.getColumnIndex(field)
        val value = cursor.getString(colIndex)
        return value ?: ""
    }
}