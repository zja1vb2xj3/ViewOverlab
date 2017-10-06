package com.androidg.beaconyx.testproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private MarkerOptions mMarkerOptions;
    public static final LatLng SAMPLE_LATLNG = new LatLng(37.338359, 126.744272);
    private GpsService mGpsService;
    private ImageView mListExitImg;

    private boolean mListImageClickSign = false;

    private LinearLayout mListLayout;
    private int mListLayoutHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListLayout = (LinearLayout) findViewById(R.id.listLayout);

        ListView listView = (ListView) findViewById(R.id.listView);

        ListViewAdapter listViewAdapter = new ListViewAdapter();

        listViewAdapter.addItem("A");
        listViewAdapter.addItem("B");
        listViewAdapter.addItem("C");
        listViewAdapter.addItem("D");
        listViewAdapter.addItem("E");

        listView.setAdapter(listViewAdapter);

        mGpsService = new GpsService(this);

        mListExitImg = (ImageView) findViewById(R.id.listExitImg);

        mMapView = (MapView) findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mapReadyCallback);


        mListExitImg.setOnClickListener(clickListener);

        mListLayoutHeight = mListLayout.getHeight();


    }//end onCreate

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            mGoogleMap = map;
            mGpsService.startLocationService();

            LatLng latLng = mGpsService.getLatLng();
            showCurrentLocation(latLng);
        }
    };

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.listExitImg :
                    mListLayout.setVisibility(View.INVISIBLE );
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private void showCurrentLocation(LatLng currentLatLng) {
        if (currentLatLng != null) {
            zoomMap(currentLatLng);
            mMarkerOptions = new MarkerOptions();
            mMarkerOptions.position(currentLatLng);
            mMarkerOptions.title("현재위치");
            mGoogleMap.clear();
            mGoogleMap.addMarker(mMarkerOptions);

            mGoogleMap.setOnMarkerClickListener(onMarkerClickListener);
        } else {
            Toast.makeText(getApplicationContext(), "현재위치 정보가 없습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private void zoomMap(LatLng latLng) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            mListLayout.setVisibility(View.VISIBLE);
            mListLayout.animate().translationY(mListLayoutHeight);

            return false;
        }
    };
}
