package kz.kbtu.medhack.tracking.map;

import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public interface MapTrackView extends MvpView {

    void moveCar( ArrayList<LatLng> points);
}
