package com.grafixartist.parseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PolylineOptions rectOptions = new PolylineOptions();
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            public void onInfoWindowClick(Marker marker) {
                LatLng mark = marker.getPosition();
                Double lat = mark.latitude;
                Double lon = mark.longitude;
                pictures(lat,lon);
            }
        });
        ArrayList<Double> pins = null;
        try {
            pins = (ArrayList<Double>) InternalStorage.readObject(this, "array");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double lowestLat = pins.get(2);
        double highestLat = pins.get(2);
        double lowestLon = pins.get(1);
        double highestLon = pins.get(1);
        for(int i = 0; i < pins.size()/3; i++){
            MarkerOptions option = new MarkerOptions();
            option.position(new LatLng(pins.get(i * 3 + 1), pins.get(i * 3 + 2)));
            option.title("Stop " + (i+1));
                mMap.addMarker(option);
                rectOptions.add(new LatLng(pins.get(i*3+1), pins.get(i*3+2)));
        }
        rectOptions.color(Color.RED);
        Polyline polyline = mMap.addPolyline(rectOptions);
        final LatLngBounds area = new LatLngBounds(
                new LatLng(pins.get(1)-0.005, pins.get(2)-0.005), new LatLng(pins.get(1)+0.005, pins.get(2)+0.005));

// Set the camera to the greatest possible zoom level that includes the
// bounds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(area, 0));
            }
        }, 1000);
    }

    private void pictures(Double lat, Double lon) {
        Intent intent = new Intent(this, Picture.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
            }
        }
    }


}
