package uk.co.ourfriendirony.medianotifier.activities.pageradapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedComingSoon;
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedReleased;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class UnplayedPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;
    private Bundle b = new Bundle();
    private Fragment fragment = null;

    public UnplayedPagerAdapter(FragmentManager fm, String type) {
        super(fm);
        this.b.putString(INTENT_KEY, type);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new UnplayedReleased();
                break;
            case 1:
                fragment = new UnplayedComingSoon();
                break;
            default:
                Log.e("[FRAGMENT]", "Invalid Fragment Index");
                break;
        }
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Released";
            case 1:
                return "Coming Soon...";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
