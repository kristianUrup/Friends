package dk.easv.friendsv2.Model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class LocationTracker implements LocationListener {

    private boolean isGPSEnabled = false;
    private boolean isGPSPermissionGiven = true;
    private boolean canGetLocation = false;

    private Location location = null;

    private static final long MIN_DISTANCE_CHANGE_FOR_GPS = 0; //0 meter
    private static final long MIN_TIME_BETWEEN_CHANGE_FOR_GPS = 1000 * 60; //1 Minute

    private Context context;
    private LocationManager locationManager;

    public LocationTracker(Context context) {
        this.context = context;
    }

    public void startLocationUpdate() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isGPSPermissionGiven = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED;
        }

        if (isGPSEnabled && isGPSPermissionGiven) {
            canGetLocation = true;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BETWEEN_CHANGE_FOR_GPS,
                    MIN_DISTANCE_CHANGE_FOR_GPS,
                    this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    public void stopLocationUpdate() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    public Location getLocation() {
        return location;
    }

    public boolean canGetLocation() {
        return canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
