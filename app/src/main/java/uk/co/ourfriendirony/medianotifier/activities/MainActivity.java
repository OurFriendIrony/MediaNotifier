package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.net.Uri;
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
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;

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

        TVShowDatabaseDefinition def = new TVShowDatabaseDefinition(getApplicationContext());
        def.getReadableDatabase();
        def.getWritableDatabase();


        FloatingActionButton fab_find_tv = (FloatingActionButton) findViewById(R.id.fab_find_tv);
        FloatingActionButton fab_find_movie = (FloatingActionButton) findViewById(R.id.fab_find_movie);
        FloatingActionButton fab_find_music = (FloatingActionButton) findViewById(R.id.fab_find_music);

        Button button_show_tv = (Button) findViewById(R.id.button_show_tv);
        Button button_show_movie = (Button) findViewById(R.id.button_show_movie);
        Button button_show_music = (Button) findViewById(R.id.button_show_music);

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
                startActivity(new Intent(view.getContext(), SettingsActivity.class));
            }
        });

        button_show_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TVListActivity.class));
            }
        });
        button_show_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(view.getContext(), TVListActivity.class));
            }
        });
        button_show_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(view.getContext(), TVListActivity.class));
            }
        });
        ImageView img = (ImageView)findViewById(R.id.badge_tmdb);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.themoviedb.org/"));
                startActivity(intent);
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
