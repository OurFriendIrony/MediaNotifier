package uk.co.ourfriendirony.medianotifier.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator;

import static uk.co.ourfriendirony.medianotifier.general.ImageNumber.getNumberImage;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doSomeNotificationShit();


//        if (!isMyServiceRunning()){
//            Intent serviceIntent = new Intent("uk.co.ourfriendirony.notifier.NotifierService");
//            getApplicationContext().startService(serviceIntent);
//        }

        TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));

        FloatingActionButton main_button_tv_find = (FloatingActionButton) findViewById(R.id.main_button_tv_find);
        FloatingActionButton main_button_movie_find = (FloatingActionButton) findViewById(R.id.main_button_movie_find);
        FloatingActionButton main_button_music_find = (FloatingActionButton) findViewById(R.id.main_button_music_find);

        FloatingActionButton main_button_tv_notification = (FloatingActionButton) findViewById(R.id.main_button_tv_notification);
        FloatingActionButton main_button_movie_notification = (FloatingActionButton) findViewById(R.id.main_button_movie_notification);
        FloatingActionButton main_button_music_notification = (FloatingActionButton) findViewById(R.id.main_button_music_notification);

        Button main_button_tv = (Button) findViewById(R.id.main_button_tv);
        Button main_button_movie = (Button) findViewById(R.id.main_button_movie);
        Button main_button_music = (Button) findViewById(R.id.main_button_music);

        ImageView tmdbImage = (ImageView) findViewById(R.id.badge_tmdb);

        main_button_tv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTVFind.class));
            }
        });
        main_button_movie_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityMovieFind.class));
            }
        });
        main_button_music_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        main_button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTV.class));
            }
        });
        main_button_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });
        main_button_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        main_button_tv_notification.setImageResource(getNumberImage(database.countUnwatchedEpisodes()));
        main_button_movie_notification.setImageResource(getNumberImage(0));
        main_button_music_notification.setImageResource(getNumberImage(0));

        main_button_tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityTVNotifications.class));
            }
        });
        main_button_movie_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });
        main_button_music_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });

        tmdbImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(IntentGenerator.getWebPageIntent("https://www.themoviedb.org/"));
            }
        });

        // DEBUG
        database.getUnwatchedEpisodes();
        database.getUnwatchedUnairedEpisodes();
        // DEBUG
    }

    private void doSomeNotificationShit() {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.img_icon)
                        .setContentTitle(getResources().getString(R.string.notification_title))
                        .setContentText(getResources().getString(R.string.notification_text))
                        .setDefaults(Notification.DEFAULT_ALL);

        Intent resultIntent = new Intent(this, ActivityTVNotifications.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int notificationId = 001;
        notificationManager.notify(notificationId, mBuilder.build());

        // Cancel with "notificationManager.cancel(notificationId)"
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ActivitySettings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//
//    private boolean isMyServiceRunning() {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (NotifierService.class.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
}
