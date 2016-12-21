package qa.edu.qu.cmps312.maps;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
Google Play Services
Permissions
API Key
Check for play services
Map Fragment
Initialize the map

 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    //Google Map object that we can use to manipulate the map
    GoogleMap gmap;
    private LocationManager locationManager;
    private LocationListener listener;
    private Marker marker = null;
    private Marker marker2 = null;
    LatLng latlng = null;
    private ArrayList<Marker> markers = new ArrayList();
    Circle circle;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //We should check this because we don't want the app trying to display the MAP
        // without google services being available
        // [ if this is not checked then the app will crush when Google Services are not available]
        if (googleServicesAvailable()) {
            checkPermissionAndinitMap();
        }


    }

    private void checkPermissionAndinitMap() {

        /*
        Ask for the permissions for GPS Location
        */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 0);
            return;
        }

        //TODO 1.0 Initialize the Map Fragment Here and use MapFragments .getMapAsync() Method
        //TODO 1.0 to initialize the GoogleMap Object and NOT the getMap
        //TODO 1.0 You will need to handle the call back by implementing the call back method .

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);


        // gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);


    }


    //Checks if google map is available
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();

        int apiAvailable = api.isGooglePlayServicesAvailable(this);

        /* This returns three cases
            1. The API is installed and available
            2. The error can be fixed by the phone
            3. The API is not available and can not be fixed
        */

        if (apiAvailable == ConnectionResult.SUCCESS) {
            Toast.makeText(this, "It is Available", Toast.LENGTH_LONG).show();
            return true;
        } else if (api.isUserResolvableError(apiAvailable)) {
            //This is resolvable so we launch the google dialog to resolve the issue
            Dialog dialog = api.getErrorDialog(this, apiAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "It is not possible to connect", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //TODO 2.0 : Handle the following events and implement the TODO's accordingly
    public void upDateMapLocation(View view) throws IOException {

        switch (view.getId()) {

            //Map Types
            case R.id.normalViewBtn:
                //TODO : Change the Map type to  Normal Map
                gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satelliteViewBtn:
                //TODO : Change the Map type toSATELLITE Map;
                gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrainViewBtn:
                //TODO : Change the Map type to TERRAIN Map;
                gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybridViewBtn:
                //TODO : Change the Map type to Hybrid Map;
                gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            //Geo Location

            case R.id.geoLocate:
                EditText edt = (EditText) findViewById(R.id.locationEditBox);
                String placeName = edt.getText().toString();

                // TODO: Using the user entered text try to covert it into Latitude and Longitude  Use the (Geocoder) class
                // TODO: Then add a Marker to that Location by calling the addMarker(latLng, placeName) method
                // TODO: Then Call the zoomToLocation to Zoom into that location
                // TODO: both addMarker and ZoomToLocation need to be implemented

                Geocoder geocoder = new Geocoder(this);
                List<Address> addresses = geocoder.getFromLocationName(placeName, 1);

                latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                addMarker(latlng, placeName);
                zoomToLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), 15);

                break;

            case R.id.draw_circle:
                if (latlng != null)
                    drawCircleAround(latlng);
                break;
            case R.id.clear_map:
                clearMarkers(markers);
                break;
        }

    }

    private void addMarker(LatLng latLng, String placeName) {

        //TODO : using the latlng place a marker on the map and add the Marker to the markers List Array
        //TODO : If the number of Markers you place on the Map reached 5 then draw a polygon by calling drawPolygon(markers)
        //TODO : If the number of Markers is greater than 5 then clear the Map and draw a single one [RESTART]
        //TODO : You will need to implement the drawPolygon and clearMarkers methods

        marker = gmap.addMarker(new MarkerOptions().position(latLng).title(placeName));
        markers.add(marker);

        if (markers.size() > 5) {
            clearMarkers(markers);
        }
        else if (markers.size() == 5) {
            drawPolygon(markers);
        }

    }

    private void clearMarkers(ArrayList<Marker> markers) {
        //TODO: remove all the markers from the Map

        for (Marker marker : markers)
            marker.remove();

        circle.remove();

    }

    private void drawPolygon(ArrayList<Marker> markers) {

        //TODO: Draw polygon on the Map

        for (int i = 0; i < markers.size()-1; i++) {
            drawLineBetween(markers.get(i), markers.get(i + 1));
        }
    }


    private void drawLineBetween(Marker marker, Marker marker2) {

        PolylineOptions options = new PolylineOptions()
                .add(marker.getPosition())
                .add(marker2.getPosition())
                .color(Color.RED)
                .width(3);
        gmap.addPolyline(options);

    }

    public void drawCircleAround(LatLng latlng) {
        //TODO:  draw a circle on the Map
        CircleOptions circleOptions = new CircleOptions();
        Log.i("TAG", latlng.toString());
        circleOptions.center(latlng).radius(100).fillColor(Color.BLUE);
        circle = gmap.addCircle(circleOptions);

    }

    public void zoomToLocation(double lat, double lng, int zoom) {
        //Zoom the Camera to Location
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                lat, lng), zoom));

    }

    /*
       Permission call back method
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
    }
}
