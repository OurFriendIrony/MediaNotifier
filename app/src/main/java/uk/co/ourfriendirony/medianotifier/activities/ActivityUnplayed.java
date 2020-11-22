package uk.co.ourfriendirony.medianotifier.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.pageradapter.UnplayedPagerAdapter;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class ActivityUnplayed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);

        setTheme(PropertyHelper.getTheme(getBaseContext()));
        setContentView(R.layout.activity_core);
        getSupportActionBar().setTitle("Released " + intentKey + "s");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancelNotifications();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new UnplayedPagerAdapter(getSupportFragmentManager(), intentKey);
        mPager.setAdapter(mPagerAdapter);
    }

    private void cancelNotifications() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
