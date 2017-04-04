package uk.co.ourfriendirony.medianotifier.general;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringHandler {
    private static final String[] PREFIXES = new String[]{"A ", "The "};

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    public static String cleanDate(Date date) {
        if (date == null)
            return "";
        return dateFormat.format(date);
    }
}
