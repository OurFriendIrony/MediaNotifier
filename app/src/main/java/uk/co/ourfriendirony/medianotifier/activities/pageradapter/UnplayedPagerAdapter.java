package uk.co.ourfriendirony.medianotifier.activities.pageradapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedComingSoon;
import uk.co.ourfriendirony.medianotifier.activities.fragment.UnplayedReleased;

import static uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY;

public class UnplayedPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;
    private final Bundle bundle = new Bundle();
    private Fragment fragment = null;

    public UnplayedPagerAdapter(FragmentManager fm, String type) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.bundle.putString(INTENT_KEY, type);
    }

    @NonNull
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
        fragment.setArguments(bundle);
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
