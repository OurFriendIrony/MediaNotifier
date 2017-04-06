package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonDeleteTV = (Button) findViewById(R.id.settings_button_delete_tv_all);
        Button buttonDeleteMovie = (Button) findViewById(R.id.settings_button_delete_movie_all);
        Button buttonDeleteMusic = (Button) findViewById(R.id.settings_button_delete_music_all);


        buttonDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVShowDatabaseDefinition databaseHelper = new TVShowDatabaseDefinition(getApplicationContext());
                new TVShowDatabase(databaseHelper).deleteAllTVShows();
                Toast.makeText(ActivitySettings.this, R.string.settings_delete_response, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySettings.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
                Toast.makeText(ActivitySettings.this, R.string.settings_delete_response, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySettings.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
                Toast.makeText(ActivitySettings.this, R.string.settings_delete_response, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
