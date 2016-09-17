package hackthenorth.neighborcater;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import hackthenorth.neighborcater.models.Kitchen;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    GoogleMap mMap;
    private LocationManager locationManager;
    private DatabaseReference mDatabase;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private JSONArray kitchenArray;

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            HashMap<String, Object> kitchenHashmap= (HashMap) dataSnapshot.child("kitchens").getValue();
            Log.d("asdf", kitchenHashmap.toString());
            ArrayList<Kitchen> kitchenArrayList = new ArrayList<>();
            Iterator<DataSnapshot> iterator = dataSnapshot.child("kitchens").getChildren().iterator();
                while(iterator.hasNext()){
                    Kitchen newKitchen = iterator.next().getValue(Kitchen.class);
                    kitchenArrayList.add(newKitchen);
                }
            placeMarkers(kitchenArrayList);
            Log.d("asdf", kitchenArrayList.toString());

            // ...
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            // ...
        }
    };

    private void placeMarkers(ArrayList<Kitchen> kitchenArrayList) {
        if(mMap != null){
            for (Kitchen kitchen : kitchenArrayList){
                LatLng position = new LatLng(kitchen.getLatitude(), kitchen.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(kitchen.getKitchenName()));
                marker.setTag(kitchen.getKitchenName());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(postListener);

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng myHome = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myHome).title("Home"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myHome, 10);
        mMap.animateCamera(cameraUpdate);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}
