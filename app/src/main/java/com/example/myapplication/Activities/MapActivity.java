package com.example.myapplication.Activities;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    MapView mapView;
    GoogleMap googleMap;
    TextView dTv;
    double distance;

    double latitude, longitude;

    //Location currentLocation;
    //FusedLocationProviderClient fusedLocationProviderClient;
// bla bla

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent gi=getIntent();
        latitude=gi.getDoubleExtra("latitude",-1);
        longitude=gi.getDoubleExtra("longitude", -1);

        dTv = findViewById(R.id.dTv);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initGoogleMap(savedInstanceState);
    }

    private void showMap() {
        if (googleMap != null) {
            // Clear previous markers (optional)
            googleMap.clear();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Check if GoogleMap is ready
                if (googleMap != null) {
                    // Add a marker at a specific location (e.g., Be'er Sheva)
                    LatLng location = new LatLng(latitude, longitude);
                    MarkerOptions markerEnd = new MarkerOptions().position(location).title("Marker in employee location");
                    googleMap.addMarker(markerEnd);


//                    LatLng location1 = new LatLng(31.265116, 34.809213);
//                    MarkerOptions markerStart = new MarkerOptions().position(location1).title("Marker in pinhas lavon 4 beer sheva");
//                    googleMap.addMarker(markerStart);


                    // Move camera to the marker and set an appropriate zoom level
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
//                    distance = calculateDistance(markerStart.getPosition(), markerEnd.getPosition());
//                    dTv.setText("Distance: " + distance);
                }
            }
        });
    }

//



    public void Show(View view) {
        mapView.setVisibility(View.VISIBLE);
        showMap();
    }

     /* public void getLastLocatiom(){
        if (ActivityCompat.checkSelfPermission(mapViewActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mapViewActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        currentLocation = location;

                        LatLng location1 = new LatLng(31.265116, 34.809213);
                        googleMap.addMarker(new MarkerOptions().position(location1).title("Marker in pinhas lavon 4 beer sheva"));
                    }
                }
            });
        }
    }*/

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }





    // Other necessary lifecycle methods for MapView
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}