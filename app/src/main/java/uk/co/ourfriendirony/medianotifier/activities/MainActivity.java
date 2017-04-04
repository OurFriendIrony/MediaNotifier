package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

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

        FloatingActionButton fab_find_tv = (FloatingActionButton) findViewById(R.id.fab_find_tv);
        FloatingActionButton fab_find_movie = (FloatingActionButton) findViewById(R.id.fab_find_movie);
        FloatingActionButton fab_find_music = (FloatingActionButton) findViewById(R.id.fab_find_music);

        FloatingActionButton fab_notifaction_tv = (FloatingActionButton) findViewById(R.id.button_notification_tv);
        FloatingActionButton fab_notifaction_movie = (FloatingActionButton) findViewById(R.id.button_notification_movie);
        FloatingActionButton fab_notifaction_music = (FloatingActionButton) findViewById(R.id.button_notification_music);

        Button button_show_tv = (Button) findViewById(R.id.button_show_tv);
        Button button_show_movie = (Button) findViewById(R.id.button_show_movie);
        Button button_show_music = (Button) findViewById(R.id.button_show_music);

        ImageView tmdbImage = (ImageView) findViewById(R.id.badge_tmdb);

        fab_find_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TVShowFindActivity.class));
            }
        });
        fab_find_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MovieFindActivity.class));
            }
        });
        fab_find_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(view.getContext(), SettingsActivity.class));
            }
        });

        TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(getApplicationContext()));
        fab_notifaction_tv.setImageResource(getNumberImage(database.countUnwatchedEpisodes()));

        // DEBUG
        database.getUnwatchedEpisodes();
        database.getUnwatchedUnairedEpisodes();
        // DEBUG

        button_show_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TVShowActivity.class));
            }
        });
        button_show_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(view.getContext(), TVShowActivity.class));
            }
        });
        button_show_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(view.getContext(), TVShowActivity.class));
            }
        });

        tmdbImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(IntentGenerator.getWebPageIntent("https://www.themoviedb.org/"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
