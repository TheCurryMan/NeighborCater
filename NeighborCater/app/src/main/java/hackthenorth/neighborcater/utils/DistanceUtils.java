package hackthenorth.neighborcater.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by rowandempster on 9/17/16.
 */

public class DistanceUtils {

    public static String getDistanceInKm(LatLng currentLocation, double latitude, double longitude) {
        float[] results = new float[1];
        Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                latitude, longitude, results);
        double km = results[0]/1000;
        DecimalFormat df = new DecimalFormat("#.##");
        return String.valueOf(df.format(km));
    }
}
