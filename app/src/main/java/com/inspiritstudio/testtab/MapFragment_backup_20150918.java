package com.inspiritstudio.testtab;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapFragment_backup_20150918 extends Fragment implements GoogleMap.OnMarkerDragListener {
//public class MapFragment extends Fragment {
    private MyLocationListener locationListener;
    private LocationManager locationManager;
    MapView mMapView;
    private GoogleMap googleMap;
    private String provider;
    private double latitude;
    private double longitude;
    private Location myLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // Enable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);



        // Get LocationManager object from System Service LOCATION_SERVICE
        //LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

           // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        //String provider = locationManager.getBestProvider(criteria, true);
        provider = locationManager.getBestProvider(criteria, true);
        // Get Current Location

        locationManager.requestLocationUpdates(provider, 0, 0,
                new LocationListener() {
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                    @Override
                    public void onLocationChanged(final Location location) {
                    }
                });
         myLocation  = locationManager
                .getLastKnownLocation(provider);


        //Location myLocation = locationManager.getLastKnownLocation(provider);

        // +


        //-

        // set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        latitude =  myLocation.getLatitude();
        longitude = myLocation.getLongitude();

        // Get latitude of the current location
        //LatLng place = myLocation.getLatitude();
        LatLng place = new LatLng(latitude,longitude);
        //LatLng place = new LatLng(22.3199, 114.1714);
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(place)
                        .zoom(15)
                        .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


//map marker start +

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition arg0) {
/*
                googleMap.clear();
                int maxResults = 1;

                try {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
                    LatLng markerPosition = new MarkerOptions().position(arg0.target).getPosition();

                    //List<Address> addresses = gc.getFromLocation(latitude,longitude, maxResults);
                    List<Address> addresses = gc.getFromLocation(markerPosition.latitude,markerPosition.longitude, maxResults);
                    //googleMap.addMarker(new MarkerOptions().position(arg0.target).title("You are here").snippet(addresses.get(0).toString())).showInfoWindow();

                    Toast.makeText(getActivity().getApplicationContext(),addresses.get(0).toString() , Toast.LENGTH_LONG).show();
                    addresses.clear();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    //Log.e("Canont get Address!");
                }
                */
            }

        });

//map marker end-
/*
        int maxResults = 1;
        try {
            Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = gc.getFromLocation(myLocation.getLatitude(),myLocation.getLongitude(), maxResults);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet(addresses.get(0).toString()));
            /*longitude
            if (addresses.size() == 1) {
                return addresses.get(0);
            } else {
                return null;
            }

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Log.e("Canont get Address!");
        }
*/

        /*
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        //googleMap.addMarker(marker);
        // Enable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

       // googleMap.setOnMarkerDragListener(this);

        //test+
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        latitude = myLocation.getLatitude();

        // Get longitude of the current location
        longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //get map stree name
        int maxResults = 1;
        try {
            Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = gc.getFromLocation(latitude, longitude, maxResults);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet(addresses.get(0).toString()));
            /*
            if (addresses.size() == 1) {
                return addresses.get(0);
            } else {
                return null;
            }

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Log.e("Canont get Address!");
        }
        */
        // Zoom in the Google Map
       // googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
       // googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet(addresses.get(0)));


        //test-
        //CameraPosition cameraPosition = new CameraPosition.Builder()
        //        .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
        //googleMap.animateCamera(CameraUpdateFactory
        //        .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }


    class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            //if (location != null) {
            //    loc.setText(LocationSample.showLocation(location));
            //} else {
            //    loc.setText("Cannot get location!");
            //}
        }
        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
/*
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
        } else {
            locationManager.requestLocationUpdates
                    (LocationManager.GPS_PROVIDER, 0, 1, locationListener);
        }
*/

        mMapView.onResume();



    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationManager.removeUpdates(locationListener);
        locationManager.setTestProviderEnabled(provider, false);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker arg0) {
        LatLng dragPosition = arg0.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getActivity().getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_LONG).show();
    }

}
