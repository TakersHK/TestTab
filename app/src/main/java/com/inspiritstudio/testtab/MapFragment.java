package com.inspiritstudio.testtab;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements GoogleMap.OnMarkerDragListener {
//public class MapFragment extends Fragment {
    private MyLocationListener locationListener;
    private LocationManager locationManager;
    MapView mMapView;
    private GoogleMap googleMap;
    private String provider;
    private double latitude;
    private double longitude;
    private Location myLocation;
    private Button btsubmit;
    private EditText etAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        //mMapView = (MapView) v.findViewById(R.id.mapView);
        //mMapView.onCreate(savedInstanceState);

        //mMapView.onResume();// needed to get the map to display immediately

        //try {
        //    MapsInitializer.initialize(getActivity().getApplicationContext());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //
        // }
        etAddress = (EditText) v.findViewById(R.id.etLocationName);
        //set on click listener ｏｆ　button +
        btsubmit = (Button) v.findViewById(R.id.btSubmit);
        btsubmit.setOnClickListener (new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!isMapReady()) {
                    return;
                }

                EditText etLocationName = (EditText) getActivity().findViewById(R.id.etLocationName);
                String locationName = etLocationName.getText().toString().trim();
                if (locationName.length() > 0) {
                    locationNameToMarker(locationName);
                } else {
                    //showToast(R.string.msg_LocationNameIsEmpty);
                }

            }
        });
        //set on click listener of button -

        if (googleMap == null) {
            //googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager()

            googleMap = ((SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map)).getMap();
            if (googleMap != null) {
             //   setUpMap();
            }
        }

        //googleMap = mMapView.getMap();
        // Enable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

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
                       // etAddress.setText(location.toString());
                    }
                });
         myLocation  = locationManager
                .getLastKnownLocation(provider);

        // set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.getUiSettings().setMyLocationButtonEnabled(true);
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
                //etAddress.setText(arg0.toString());
                GetAddress getaddress = new GetAddress();

                getaddress.execute(arg0);
            }

        });

        return v;
    }

    private class GetAddress extends AsyncTask<CameraPosition,Void, String> {
        @Override
        protected String doInBackground(CameraPosition... params) {
            List<Address> addresses;
            String add;
            add=null;
            addresses=null;
            try {
                Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
                LatLng markerPosition = new MarkerOptions().position(params[0].target).getPosition();

                //List<Address> addresses = gc.getFromLocation(latitude,longitude, maxResults);
                addresses = gc.getFromLocation(markerPosition.latitude, markerPosition.longitude, 1);
                //googleMap.addMarker(new MarkerOptions().position(arg0.target).title("You are here").snippet(addresses.get(0).toString())).showInfoWindow();
                Log.d("STATE", addresses.get(0).toString());
                //Toast.makeText(getActivity().getApplicationContext(), addresses.get(0).toString(), Toast.LENGTH_LONG).show();

                add =  addresses.get(0).toString();
                addresses.clear();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Log.e("Canont get Address!");
            }

            return add;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            etAddress.setText(result);
        }
    }


    private boolean isMapReady() {
        if (googleMap == null) {
            Toast.makeText(getActivity(), "map is not ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void onLocationNameClick(View view) {
        if (!isMapReady()) {
            return;
        }

        EditText etLocationName = (EditText) getActivity().findViewById(R.id.etLocationName);
        String locationName = etLocationName.getText().toString().trim();
        if (locationName.length() > 0) {
            locationNameToMarker(locationName);
        } else {
            //showToast(R.string.msg_LocationNameIsEmpty);
        }
    }

    private void locationNameToMarker(String locationName) {
        googleMap.clear();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> addressList = null;
        int maxResults = 1;
        try {
            addressList = geocoder
                    .getFromLocationName(locationName, maxResults);
        }
        catch (IOException e) {
            // Log.e(TAG, e.toString());
        }

        if (addressList == null || addressList.isEmpty()) {
            //showToast(R.string.msg_LocationNameNotFound);
        } else {
            Address address = addressList.get(0);

            LatLng position = new LatLng(address.getLatitude(),
                    address.getLongitude());

            String snippet = address.getAddressLine(0);

            googleMap.addMarker(new MarkerOptions().position(position)
                    .title(locationName).snippet(snippet));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(position).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }

    class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            //EditText etLocationName =(EditText) v.find
            if (location != null) {
            //    etLocationName.setText(LocationSample.showLocation(location));
            } else {
             //   etLocationName.setText("Cannot get location!");
            }
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
        setUpMapIfNeeded();
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

//        mMapView.onResume();



    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            //if (googleMap != null) {
            //    setUpMap();
            //}
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //mMapView.onPause();
        //this.onPause();

        //setUpMapIfNeeded();
        //locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //mMapView.onDestroy();
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
