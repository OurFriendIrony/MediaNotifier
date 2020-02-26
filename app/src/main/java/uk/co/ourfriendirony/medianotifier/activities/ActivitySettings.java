package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.notifier.AlarmScheduler;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetArtist;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMax;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMin;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationMinute;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationTimeFull;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetArtist;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationMinute;

public class ActivitySettings extends AppCompatActivity {
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(PropertyHelper.getTheme(getBaseContext()));
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.title_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load Page Objects

        final SwitchCompat toggleMarkWatched = (SwitchCompat) findViewById(R.id.settings_watched_toggle);

        final Button buttonNotifyTimer = (Button) findViewById(R.id.settings_notification_time_button);

        final Button buttonNotifyOffsetTV = (Button) findViewById(R.id.settings_notification_day_offset_tv_button);
        final Button buttonNotifyOffsetMovie = (Button) findViewById(R.id.settings_notification_day_offset_movie_button);
        final Button buttonNotifyOffsetArtist = (Button) findViewById(R.id.settings_notification_day_offset_artist_button);

        final Button buttonDeleteTV = (Button) findViewById(R.id.settings_button_delete_tv_all);
        final Button buttonDeleteMovie = (Button) findViewById(R.id.settings_button_delete_movie_all);
        final Button buttonDeleteArtist = (Button) findViewById(R.id.settings_button_delete_artist_all);

        // Set Object Current Values
        toggleMarkWatched.setChecked(getMarkWatchedIfAlreadyReleased(getBaseContext()));

        buttonNotifyTimer.setText(getNotificationTimeFull(getBaseContext()));

        buttonNotifyOffsetTV.setText(String.valueOf(getNotificationDayOffsetTV(getBaseContext())));
        buttonNotifyOffsetMovie.setText(String.valueOf(getNotificationDayOffsetMovie(getBaseContext())));
        buttonNotifyOffsetArtist.setText(String.valueOf(getNotificationDayOffsetArtist(getBaseContext())));

        buttonDeleteTV.setText(getResources().getString(R.string.button_delete_tv_all));
        buttonDeleteMovie.setText(getResources().getString(R.string.button_delete_movie_all));
        buttonDeleteArtist.setText(getResources().getString(R.string.button_delete_artist_all));

        // Define Object Actions
        toggleMarkWatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMarkWatchedIfAlreadyReleased(getBaseContext(), isChecked);
            }
        });

        buttonDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TVShowDatabase(getApplicationContext()).deleteAll();
                Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MovieDatabase(getApplicationContext()).deleteAll();
                Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ArtistDatabase(getApplicationContext()).deleteAll();
                Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
            }
        });

        buttonNotifyTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_time_selector, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final TimePicker timePicker = (TimePicker) popupWindow.getContentView().findViewById(R.id.popup_time_picker);
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(getNotificationHour(getApplicationContext()));
                timePicker.setCurrentMinute(getNotificationMinute(getApplicationContext()));

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setNotificationHour(getApplicationContext(), timePicker.getCurrentHour());
                        setNotificationMinute(getApplicationContext(), timePicker.getCurrentMinute());

                        AlarmScheduler.reschedule(getApplicationContext());
                        popupWindow.dismiss();

                        buttonNotifyTimer.setText(getNotificationTimeFull(getBaseContext()));
                    }
                });
            }
        });

        buttonNotifyOffsetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_offset_selector, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final NumberPicker picker = (NumberPicker) popupWindow.getContentView().findViewById(R.id.popup_date_picker);
                picker.setMaxValue(getNotificationDayOffsetMax());
                picker.setMinValue(getNotificationDayOffsetMin());
                picker.setValue(getNotificationDayOffsetTV(getApplicationContext()));
                picker.setWrapSelectorWheel(false);

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setNotificationDayOffsetTV(getApplicationContext(), picker.getValue());
                        popupWindow.dismiss();
                        buttonNotifyOffsetTV.setText(String.valueOf(getNotificationDayOffsetTV(getBaseContext())));
                    }
                });
            }
        });

        buttonNotifyOffsetMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_offset_selector, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final NumberPicker picker = (NumberPicker) popupWindow.getContentView().findViewById(R.id.popup_date_picker);
                picker.setMaxValue(getNotificationDayOffsetMax());
                picker.setMinValue(getNotificationDayOffsetMin());
                picker.setValue(getNotificationDayOffsetMovie(getApplicationContext()));
                picker.setWrapSelectorWheel(false);

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setNotificationDayOffsetMovie(getApplicationContext(), picker.getValue());
                        popupWindow.dismiss();
                        buttonNotifyOffsetMovie.setText(String.valueOf(getNotificationDayOffsetMovie(getBaseContext())));
                    }
                });
            }
        });

        buttonNotifyOffsetArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_offset_selector, (ViewGroup) findViewById(R.id.popup));

                popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final NumberPicker picker = (NumberPicker) popupWindow.getContentView().findViewById(R.id.popup_date_picker);
                picker.setMaxValue(getNotificationDayOffsetMax());
                picker.setMinValue(getNotificationDayOffsetMin());
                picker.setValue(getNotificationDayOffsetArtist(getApplicationContext()));
                picker.setWrapSelectorWheel(false);

                Button buttonOk = (Button) popupWindow.getContentView().findViewById(R.id.popup_ok);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setNotificationDayOffsetArtist(getApplicationContext(), picker.getValue());
                        popupWindow.dismiss();
                        buttonNotifyOffsetArtist.setText(String.valueOf(getNotificationDayOffsetArtist(getBaseContext())));
                    }
                });
            }
        });
    }
}
