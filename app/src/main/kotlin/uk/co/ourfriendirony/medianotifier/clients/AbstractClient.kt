package uk.co.ourfriendirony.medianotifier.clients

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

abstract class AbstractClient {
    @Throws(IOException::class)
    protected fun httpGetRequest(url: String): String {
        val client = OkHttpClient()
        val req: Request = Request.Builder()
                .url(url)
                .addHeader("User-Agent", "MediaNotifier/1.0.0 ( ourfriendirony@gmail.com )")
                .build()
        while (true) {
            client.newCall(req).execute().use { res ->
                val payload = res.body!!.string()
                val statusCode = res.code
                val headers = parseHeaders(res)
                logResponse(url, payload, headers, statusCode)
                if (statusCode == 200) {
                    return payload
                } else {
                    // TODO: This is 100% unacceptable implementation for a production release product
                    sleep(SLEEP)
                }
            }
        }
    }

    private fun logResponse(url: String, payload: String, headers: String, statusCode: Int) {
        Log.d("[REQUEST URL]", url)
        Log.d("[RESPONSE PAYLOAD]", payload)
        Log.d("[RESPONSE HEADERS]", headers)
        Log.d("[RESPONSE STATUSC]", statusCode.toString())
    }

    private fun sleep(time: Int) {
        try {
            Thread.sleep((time * 1000).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun parseHeaders(res: Response): String {
        val builder = StringBuilder()
        for (name in res.headers.names()) {
            builder.append(name).append(":").append(res.headers[name]).append("|")
        }
        return builder.toString()
    }

    companion object {
        private const val SLEEP = 1
    }
}