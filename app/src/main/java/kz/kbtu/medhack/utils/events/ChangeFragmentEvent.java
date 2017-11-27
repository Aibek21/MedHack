package kz.kbtu.medhack.utils.events;

import android.support.v4.app.Fragment;

/**
 * Created by aibekkuralbaev on 25.11.16.
 */

public class ChangeFragmentEvent {

    Fragment mFragment;


    public ChangeFragmentEvent(Fragment fragment){
        mFragment = fragment;

    }

    public Fragment getFragment() {
        return mFragment;
    }
}
