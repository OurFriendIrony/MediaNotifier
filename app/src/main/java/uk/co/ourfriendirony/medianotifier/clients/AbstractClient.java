package uk.co.ourfriendirony.medianotifier.clients;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AbstractClient {
    private static final DefaultHttpClient client = new DefaultHttpClient();

    String httpGetRequest(String url) throws IOException {

        while (true) {
            HttpResponse httpResponse = client.execute(new HttpGet(url));
            String payload = getPayload(httpResponse);
            int statusCode = getStatusCode(httpResponse);
            String headers = getHeaders(httpResponse);

            logResponse(url, payload, headers, statusCode);
            if (statusCode == 200) {
                return payload;
            } else {
                sleep(1);
            }
        }

    }

    private void logResponse(String url, String payload, String headers, int statusCode) {
        Log.v(String.valueOf(this.getClass()), "URL     = " + url);
        Log.v(String.valueOf(this.getClass()), "PAYLOAD = " + payload);
        Log.v(String.valueOf(this.getClass()), "HEADERS = " + headers);
        Log.v(String.valueOf(this.getClass()), "STATUSC = " + statusCode);
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
