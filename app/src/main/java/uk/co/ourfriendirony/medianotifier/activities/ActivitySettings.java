package uk.co.ourfriendirony.medianotifier.activities;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetArtist;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetGame;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMax;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMin;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationMinute;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationTimeFull;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setMarkWatchedIfAlreadyReleased;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetArtist;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetGame;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationDayOffsetTV;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationHour;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.setNotificationMinute;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.notifier.AlarmScheduler;

public class ActivitySettings extends AppCompatActivity {
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle(R.string.title_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load Page Objects

        final SwitchCompat toggleMarkWatched = findViewById(R.id.settings_played_toggle);

        final Button buttonNotifyTimer = findViewById(R.id.settings_notification_time_button);

        final Button buttonNotifyOffsetTV = findViewById(R.id.settings_notification_day_offset_tv_button);
        final Button buttonNotifyOffsetMovie = findViewById(R.id.settings_notification_day_offset_movie_button);
        final Button buttonNotifyOffsetArtist = findViewById(R.id.settings_notification_day_offset_artist_button);
        final Button buttonNotifyOffsetGame = findViewById(R.id.settings_notification_day_offset_game_button);

        final Button buttonDeleteTV = findViewById(R.id.settings_button_delete_tv_all);
        final Button buttonDeleteMovie = findViewById(R.id.settings_button_delete_movie_all);
        final Button buttonDeleteArtist = findViewById(R.id.settings_button_delete_artist_all);
        final Button buttonDeleteGame = findViewById(R.id.settings_button_delete_game_all);

        // Set Object Current Values
        toggleMarkWatched.setChecked(getMarkWatchedIfAlreadyReleased(getBaseContext()));

        buttonNotifyTimer.setText(getNotificationTimeFull(getBaseContext()));

        buttonNotifyOffsetTV.setText(String.valueOf(getNotificationDayOffsetTV(getBaseContext())));
        buttonNotifyOffsetMovie.setText(String.valueOf(getNotificationDayOffsetMovie(getBaseContext())));
        buttonNotifyOffsetArtist.setText(String.valueOf(getNotificationDayOffsetArtist(getBaseContext())));
        buttonNotifyOffsetGame.setText(String.valueOf(getNotificationDayOffsetGame(getBaseContext())));

        buttonDeleteTV.setText(getResources().getString(R.string.button_delete_tv_all));
        buttonDeleteMovie.setText(getResources().getString(R.string.button_delete_movie_all));
        buttonDeleteArtist.setText(getResources().getString(R.string.button_delete_artist_all));
        buttonDeleteGame.setText(getResources().getString(R.string.button_delete_game_all));

        // Define Object Actions
        toggleMarkWatched.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setMarkWatchedIfAlreadyReleased(getBaseContext(), isChecked);
        });

        buttonDeleteTV.setOnClickListener(view -> {
            new TVShowDatabase(getApplicationContext()).deleteAll();
            Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
        });

        buttonDeleteMovie.setOnClickListener(view -> {
            new MovieDatabase(getApplicationContext()).deleteAll();
            Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
        });

        buttonDeleteArtist.setOnClickListener(view -> {
            new ArtistDatabase(getApplicationContext()).deleteAll();
            Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
        });

        buttonDeleteGame.setOnClickListener(view -> {
            new GameDatabase(getApplicationContext()).deleteAll();
            Toast.makeText(ActivitySettings.this, R.string.toast_db_table_cleared, Toast.LENGTH_SHORT).show();
        });

        buttonNotifyTimer.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_time_selector, findViewById(R.id.popup));

            popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final TimePicker timePicker = popupWindow.getContentView().findViewById(R.id.popup_time_picker);
            timePicker.setIs24HourView(true);
            timePicker.setHour(getNotificationHour(getApplicationContext()));
            timePicker.setMinute(getNotificationMinute(getApplicationContext()));

            Button buttonOk = popupWindow.getContentView().findViewById(R.id.popup_ok);
            buttonOk.setOnClickListener(v1 -> {
                setNotificationHour(getApplicationContext(), timePicker.getCurrentHour());
                setNotificationMinute(getApplicationContext(), timePicker.getCurrentMinute());

                AlarmScheduler.reschedule(getApplicationContext());
                popupWindow.dismiss();

                buttonNotifyTimer.setText(getNotificationTimeFull(getBaseContext()));
            });
        });

        buttonNotifyOffsetTV.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup));

            popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final NumberPicker picker = popupWindow.getContentView().findViewById(R.id.popup_date_picker);
            picker.setMaxValue(getNotificationDayOffsetMax());
            picker.setMinValue(getNotificationDayOffsetMin());
            picker.setValue(getNotificationDayOffsetTV(getApplicationContext()));
            picker.setWrapSelectorWheel(false);

            Button buttonOk = popupWindow.getContentView().findViewById(R.id.popup_ok);
            buttonOk.setOnClickListener(subView -> {
                setNotificationDayOffsetTV(getApplicationContext(), picker.getValue());
                popupWindow.dismiss();
                buttonNotifyOffsetTV.setText(String.valueOf(getNotificationDayOffsetTV(getBaseContext())));
            });
        });

        buttonNotifyOffsetMovie.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup));

            popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final NumberPicker picker = popupWindow.getContentView().findViewById(R.id.popup_date_picker);
            picker.setMaxValue(getNotificationDayOffsetMax());
            picker.setMinValue(getNotificationDayOffsetMin());
            picker.setValue(getNotificationDayOffsetMovie(getApplicationContext()));
            picker.setWrapSelectorWheel(false);

            Button buttonOk = popupWindow.getContentView().findViewById(R.id.popup_ok);
            buttonOk.setOnClickListener(subView -> {
                setNotificationDayOffsetMovie(getApplicationContext(), picker.getValue());
                popupWindow.dismiss();
                buttonNotifyOffsetMovie.setText(String.valueOf(getNotificationDayOffsetMovie(getBaseContext())));
            });
        });

        buttonNotifyOffsetArtist.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup));

            popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final NumberPicker picker = popupWindow.getContentView().findViewById(R.id.popup_date_picker);
            picker.setMaxValue(getNotificationDayOffsetMax());
            picker.setMinValue(getNotificationDayOffsetMin());
            picker.setValue(getNotificationDayOffsetArtist(getApplicationContext()));
            picker.setWrapSelectorWheel(false);

            Button buttonOk = popupWindow.getContentView().findViewById(R.id.popup_ok);
            buttonOk.setOnClickListener(subView -> {
                setNotificationDayOffsetArtist(getApplicationContext(), picker.getValue());
                popupWindow.dismiss();
                buttonNotifyOffsetArtist.setText(String.valueOf(getNotificationDayOffsetArtist(getBaseContext())));
            });
        });


        buttonNotifyOffsetGame.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) ActivitySettings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_offset_selector, findViewById(R.id.popup));

            popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final NumberPicker picker = popupWindow.getContentView().findViewById(R.id.popup_date_picker);
            picker.setMaxValue(getNotificationDayOffsetMax());
            picker.setMinValue(getNotificationDayOffsetMin());
            picker.setValue(getNotificationDayOffsetGame(getApplicationContext()));
            picker.setWrapSelectorWheel(false);

            Button buttonOk = popupWindow.getContentView().findViewById(R.id.popup_ok);
            buttonOk.setOnClickListener(subView -> {
                setNotificationDayOffsetGame(getApplicationContext(), picker.getValue());
                popupWindow.dismiss();
                buttonNotifyOffsetGame.setText(String.valueOf(getNotificationDayOffsetGame(getBaseContext())));
            });
        });
    }
}
