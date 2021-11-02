package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class AbstractClient {
    private static final int SLEEP = 1;

    protected String httpGetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "MediaNotifier/1.0.0 ( ourfriendirony@gmail.com )")
                .build();
        while (true) {
            try (Response res = client.newCall(req).execute()) {
                String payload = res.body().string();
                int statusCode = res.code();
                String headers = parseHeaders(res);

                logResponse(url, payload, headers, statusCode);
                if (statusCode == 200) {
                    return payload;
                } else {
                    // TODO: This is 100% unacceptable implementation for a production release product
                    sleep(SLEEP);
                }
            }
        }
    }

    private void logResponse(String url, String payload, String headers, int statusCode) {
        Log.d("[REQUEST URL]", url);
        Log.d("[RESPONSE PAYLOAD]", payload);
        Log.d("[RESPONSE HEADERS]", headers);
        Log.d("[RESPONSE STATUSC]", String.valueOf(statusCode));
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String parseHeaders(Response res) {
        StringBuilder builder = new StringBuilder();
        for (String name : res.headers().names()) {
            builder.append(name).append(":").append(res.headers().get(name)).append("|");
        }
        return builder.toString();
    }
}
