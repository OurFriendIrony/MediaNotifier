package uk.co.ourfriendirony.medianotifier.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.activities.pageradapter.UnplayedPagerAdapter;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class ActivityUnplayed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        String intentKey = getIntent().getExtras().getString(INTENT_KEY);
        getSupportActionBar().setTitle("Released " + intentKey + "s");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancelNotifications();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new UnplayedPagerAdapter(getSupportFragmentManager(), intentKey);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);
    }

    private void cancelNotifications() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
