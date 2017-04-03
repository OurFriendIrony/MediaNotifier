package uk.co.ourfriendirony.medianotifier.general;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringHandler {
    public static final String[] PREFIXES = new String[]{"A ", "The "};

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
                return string.substring(prefix.length() - 1) + ", " + prefix.substring(0, prefix.length() - 1);
            }
        }
        return string;
    }
}
