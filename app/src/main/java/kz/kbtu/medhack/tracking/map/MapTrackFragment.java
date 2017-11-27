package kz.kbtu.medhack.tracking.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.medhack.App;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.main.MainActivity;
import kz.kbtu.medhack.utils.base.view.BaseFragment;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class MapTrackFragment extends BaseFragment<MapTrackView, MapTrackPresenter> implements MapTrackView, OnMapReadyCallback {


    public static MapTrackFragment newInstance() {

        Bundle args = new Bundle();

        MapTrackFragment fragment = new MapTrackFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.mapview)
    MapView mMapView;

    GoogleMap map;
    NetComponent mNetComponent;

    Bitmap marker;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        int height = 200;
        int width = 200;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker);
        Bitmap b = bitmapdraw.getBitmap();
        marker = Bitmap.createScaledBitmap(b, width, height, false);
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        mMapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMapView.getMapAsync(this);
//        map.getUiSettings().setMyLocationButtonEnabled(false);

    }

    @Override
    protected void injectDependencies() {
        mNetComponent = ((App) getActivity().getApplication()).getNetComponent();
        mNetComponent.inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map_track;
    }

    @NonNull
    @Override
    public MapTrackPresenter createPresenter() {
        return mNetComponent.mapTrackPresenter();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(51.1692483, 71.4410553), 10);
        map.animateCamera(cameraUpdate);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showPermissionDialog();
        }
        map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
//                map.animateCamera(cameraUpdate);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);

        try {
            presenter.startMoveCar(new LatLng(51.1646283, 71.4397143));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        if (mMapView != null)
            mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }


    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void showPermissionDialog() {
        if (!checkPermission(getActivity())) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    99);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                Log.e("Result", "OK");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Result", "YES");
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        map.setMyLocationEnabled(true);
                    }

                } else {
                    Log.e("Result", "NO");
                    showPermissionDialog();
                }
            }

        }
    }

    @Override
    public void moveCar(ArrayList<LatLng> points) {

        map.clear();

        Marker m = map.addMarker(new MarkerOptions()
                .position(points.get(0))
                .title("Marker"));

        m.setIcon(BitmapDescriptorFactory.fromBitmap(marker));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(points.get(0), 15);
        map.animateCamera(cameraUpdate);

        Handler handler1 = new Handler();
        for (int a = 1; a < points.size(); a++) {
            LatLng point = points.get(a);
            int finalA = a;
            handler1.postDelayed(() -> {
                animateMarker(map, m, points.get(finalA - 1), point, false);
            }, 1500 * a);
        }

        handler1.postDelayed(this::showToast, 1500 * points.size() + 1);


        handler1.postDelayed(this::finishTracking, 1500 * (points.size() + 3));

    }


    public void showToast() {
        if (getActivity() != null)
            Toast.makeText(getActivity(), "Скорая помощь прибыла", Toast.LENGTH_SHORT).show();

    }

    public void finishTracking() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();

    }

    private void animateMarker(final GoogleMap myMap, final Marker myMarker, final LatLng startPosition, final LatLng finalPosition,
                               final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 1500;
//        final boolean hideMarker = false;

        handler.post(new Runnable() {
                         long elapsed;
                         float t;
                         float v;

                         @Override
                         public void run() {
                             // Calculate progress using interpolator
                             elapsed = SystemClock.uptimeMillis() - start;
                             t = elapsed / durationInMs;
                             v = interpolator.getInterpolation(t);

                             LatLng currentPosition = new LatLng(
                                     startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                                     startPosition.longitude * (1 - t) + finalPosition.longitude * t);

                             myMarker.setPosition(currentPosition);

                             // Repeat till progress is complete.
                             if (t < 1) {
                                 // Post again 16ms later.
                                 handler.postDelayed(this, 16);

                             }
                         }
                     }

        );


    }
}
