package uk.co.ourfriendirony.medianotifier.general;

import android.database.Cursor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {
    private static final String[] PREFIXES = new String[]{"A ", "The "};

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

    public static String cleanUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static String cleanTitle(String string) {
        for (String prefix : PREFIXES) {
            if (string.startsWith(prefix)) {
                return string.substring(prefix.length()) + ", " + prefix.substring(0, prefix.length() - 1);
            }
        }
        return string;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date) {
        if (date == null) {
            return null;
        }
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String replaceTokens(String original, String token, String value) {
        return replaceTokens(original, new String[]{token}, new String[]{value});
    }

    public static String replaceTokens(String string, String[] tokens, String[] values) {
        if (tokens.length == values.length) {
            for (int i = 0; i < tokens.length; i++) {
                string = string.replace(tokens[i], values[i]);
            }
        }
        return string;
    }

    public static String getNotificationNumber(int num) {
        if (num > 9) {
            return "9+";
        }
        return String.valueOf(num);
    }

    public static String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }
}
