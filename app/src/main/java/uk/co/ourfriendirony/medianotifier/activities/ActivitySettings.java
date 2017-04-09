package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMinute;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setMinute;

public class ActivitySettings extends AppCompatActivity {
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonDeleteTV = (Button) findViewById(R.id.settings_button_delete_tv_all);
        Button buttonDeleteMovie = (Button) findViewById(R.id.settings_button_delete_movie_all);
        Button buttonDeleteMusic = (Button) findViewById(R.id.settings_button_delete_music_all);

        Button buttonNotifyTimer = (Button) findViewById(R.id.settings_time_picker_button);

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
//                Toast.makeText(ActivitySettings.this, R.string.settings_delete_response, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySettings.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
//                Toast.makeText(ActivitySettings.this, R.string.settings_delete_response, Toast.LENGTH_SHORT).show();
            }
        });

        buttonNotifyTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(String.valueOf(R.string.app_name), 0);

                LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_time_selector, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                TimePicker timePicker = (TimePicker) popupWindow.getContentView().findViewById(R.id.popup_time_picker);
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(getHour(getApplicationContext()));
                timePicker.setCurrentMinute(getMinute(getApplicationContext()));

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setHour(getApplicationContext(), timePicker.getCurrentHour());
                        setMinute(getApplicationContext(), timePicker.getCurrentMinute());
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
}
