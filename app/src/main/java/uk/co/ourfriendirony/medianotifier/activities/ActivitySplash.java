package uk.co.ourfriendirony.medianotifier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.co.ourfriendirony.medianotifier.R;

public class ActivitySplash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
        finish();
    }
}