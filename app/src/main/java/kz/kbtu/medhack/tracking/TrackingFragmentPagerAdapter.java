package kz.kbtu.medhack.tracking;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kz.kbtu.medhack.tracking.advice.AdviceFragment;
import kz.kbtu.medhack.tracking.map.MapTrackFragment;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class TrackingFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public TrackingFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return MapTrackFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return AdviceFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
