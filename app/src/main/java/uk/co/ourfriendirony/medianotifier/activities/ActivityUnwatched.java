package uk.co.ourfriendirony.medianotifier.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.pageradapter.UnwatchedPagerAdapter;
import uk.co.ourfriendirony.medianotifier.db.PropertyHelper;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class ActivityUnwatched extends AppCompatActivity {

    private ViewPager mPager;
    private android.support.v4.view.PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);

        setTheme(PropertyHelper.getTheme(getBaseContext()));
        setContentView(R.layout.activity_core);
        getSupportActionBar().setTitle("Released " + intentKey + "s");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancelNotifications();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new UnwatchedPagerAdapter(getSupportFragmentManager(), intentKey);
        mPager.setAdapter(mPagerAdapter);
    }

    private void cancelNotifications() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
