package uk.co.ourfriendirony.medianotifier.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVUnwatchedComingSoon;
import uk.co.ourfriendirony.medianotifier.activities.tv.ActivityTVUnwatchedReleased;

public class ActivityTVPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;

    public ActivityTVPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActivityTVUnwatchedReleased();
            case 1:
                return new ActivityTVUnwatchedComingSoon();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Released";
            case 1:
                return "Coming Soon...";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
