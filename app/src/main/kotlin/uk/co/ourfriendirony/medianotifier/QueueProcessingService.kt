package uk.co.ourfriendirony.medianotifier

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.tape2.QueueFile
import uk.co.ourfriendirony.medianotifier.activities.ActivityMain
import uk.co.ourfriendirony.medianotifier.activities.objectMapper
import uk.co.ourfriendirony.medianotifier.clients.ClientFactory
import uk.co.ourfriendirony.medianotifier.db.DatabaseFactory
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase
import uk.co.ourfriendirony.medianotifier.general.Constants
import uk.co.ourfriendirony.medianotifier.general.Constants.LOGLABEL_DEBUG
import uk.co.ourfriendirony.medianotifier.general.Constants.LOGLABEL_HEY
import uk.co.ourfriendirony.medianotifier.general.Constants.LOGLABEL_SERVICE
import java.io.File


class QueueProcessingService : Service() {

    companion object {
        const val CHANNEL_ID = "QueueProcessingServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var gameDatabase: GameDatabase
    private lateinit var artistDatabase: ArtistDatabase
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var tvShowDatabase: TVShowDatabase
    private lateinit var queueFile: QueueFile

    override fun onCreate() {
        super.onCreate()

        tvShowDatabase = TVShowDatabase(applicationContext)
        movieDatabase = MovieDatabase(applicationContext)
        artistDatabase = ArtistDatabase(applicationContext)
        gameDatabase = GameDatabase(applicationContext)
        queueFile = QueueFile.Builder(File(filesDir, Constants.QUEUE_FILENAME)).build()
        createNotificationChannel()
    }

    private var isServiceRunning = false
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isServiceRunning) {
            isServiceRunning = true
            startForegroundService()
        }
        processQueue()
//        performLongRunningTask()
        return START_STICKY
    }

    private fun startForegroundService() {
        val notificationIntent = Intent(this, ActivityMain::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Queue Processing Service")
            .setContentText("Service is running")
//            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)

    }

    private fun performLongRunningTask() {
        // This simulates a long-running task
        queueFile.clear()
        handler.postDelayed(object : Runnable {
            override fun run() {

                while (!queueFile.isEmpty) {
                    val it = queueFile.peek()
                    Log.e(LOGLABEL_HEY, "before deserialize" + it.toString())
                    val obj: QueueObject = objectMapper.readValue(it, QueueObject::class.java)
                    Log.e(LOGLABEL_HEY, obj.toString())
                    val client = ClientFactory().getClient(obj.type)
                    val db = DatabaseFactory().getDatabase(applicationContext, obj.type)

                    db!!.update(client!!.getMediaItem(obj.id))

                    queueFile.remove()
                    updateNotification(queueFile.size())
                }

                // Perform your task here
                // For demonstration, we're just logging a message every 5 seconds
                // In a real application, you might perform network operations, etc.
                // Ensure that you keep the service running by scheduling the next execution

                handler.postDelayed(this, rerunInMS)
            }
        }, rerunInMS)
    }

    private val rerunInSeconds: Long = 20
    val rerunInMS: Long = rerunInSeconds * 1000
    private fun processQueue() {
//        queueFile.clear()
        Log.e(LOGLABEL_DEBUG, "Before Thread")
        Thread {
            while (true) {
                Log.e(LOGLABEL_DEBUG, "Thread Loop")
                while (!queueFile.isEmpty) {
                    Log.e(LOGLABEL_DEBUG, "QueueNotEmpty")
                    val it = queueFile.peek()
                    Log.e(LOGLABEL_HEY, "before deserialize" + it.toString())
                    try {
                        val obj: QueueObject = objectMapper.readValue(it, QueueObject::class.java)
                        Log.e(LOGLABEL_HEY, obj.toString())
                        val client = ClientFactory().getClient(obj.type)
                        val db = DatabaseFactory().getDatabase(applicationContext, obj.type)

                        db!!.update(client!!.getMediaItem(obj.id))
                    } catch (_: Exception) {
                        Log.e(LOGLABEL_SERVICE, "Failed to Parse item from queue...")
                    }


//                Thread.sleep(2000)

                    queueFile.remove()
                    updateNotification(queueFile.size())
                }
                Thread.sleep(2000)
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }.start()
    }

    private fun updateNotification(remainingItems: Int) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Queue Processing Service")
            .setContentText("Items remaining: $remainingItems")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)

        val broadcastIntent = Intent("QUEUE_UPDATE")
        broadcastIntent.putExtra("remainingItems", remainingItems)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Queue Processing Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        handler.removeCallbacksAndMessages(null) // Stop the handler when service is destroyed
    }
}
