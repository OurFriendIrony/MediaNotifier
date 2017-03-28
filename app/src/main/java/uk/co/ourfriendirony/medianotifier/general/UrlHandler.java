package uk.co.ourfriendirony.medianotifier.general;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlHandler {
    public static String urlCleaner(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }
}
