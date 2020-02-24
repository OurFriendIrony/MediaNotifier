package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
//TODO: Make Abstract

public class AbstractClient {
    private static final DefaultHttpClient client = new DefaultHttpClient();
    private int SLEEP = 1;

    protected String httpGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "MediaNotifier/1.0.0 ( ourfriendirony@gmail.com )");
        while (true) {
            try {
                HttpResponse httpResponse = client.execute(request);
                String payload = getPayload(httpResponse);
                int statusCode = getStatusCode(httpResponse);
                String headers = getHeaders(httpResponse);

                logResponse(url, payload, headers, statusCode);
                if (statusCode == 200) {
                    return payload;
                } else {
                    sleep(SLEEP);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private void logResponse(String url, String payload, String headers, int statusCode) {
        Log.d("[CALL URL    ]", url);
        Log.d("[CALL PAYLOAD]", payload);
        Log.d("[CALL HEADERS]", headers);
        Log.d("[CALL STATUSC]", String.valueOf(statusCode));
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPayload(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private String getHeaders(HttpResponse response) {
        StringBuilder x = new StringBuilder();
        for (Header header : response.getAllHeaders()) {
            x.append(header.getName() + ":" + header.getValue() + " | ");
        }
        return x.toString();
    }

    private int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }
}
