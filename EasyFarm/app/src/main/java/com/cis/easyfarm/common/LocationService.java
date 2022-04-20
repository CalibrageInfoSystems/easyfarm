package com.cis.easyfarm.common;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cis.easyfarm.cloudHelper.ApplicationThread;

public class LocationService extends Service implements LocationListener {
    private static final String LOG_TAG = LocationService.class.getName();

    private static final int MIN_UPDATE_TIME = 1;
    private static final int MIN_UPDATE_DISTANCE = 1;

    public static final String ACTION_START = "";
    public static final String ACTION_STOP_SRV = "";
    public static final String ACTION_STOP_LBS = "";
    public static final String ACTION_LOCATION_UPDATED = "";
    public static final String KEY_LONGITUDE = "geo_longitude";
    public static final String KEY_LATITUDE = "geo_latitude";

    private Location location;
    public LocationManager locationManager;

    public void startLocationService(ApplicationThread.OnComplete onComplete) {
        Log.d(LOG_TAG, "start location service");
       // Toast.makeText(this, "Location Service Started", Toast.LENGTH_SHORT).show();

        String providerType = null;

        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


            boolean gpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gpsProviderEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    providerType = "gps";
                    Log.d(LOG_TAG, "gps lbs provider:" + (location == null ? "null" : String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude())));
                    updateLocation(location);
                }
            }
            if (networkProviderEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    providerType = "network";
                    Log.d(LOG_TAG, "network lbs provider:" + (location == null ? "null" : String.valueOf(location.getLatitude()) +"," + String.valueOf(location.getLongitude())));
                    updateLocation(location);
                }
            }




        } catch (Exception e) {
            Log.e(LOG_TAG, "Cannot get location", e);
        }

        if (onComplete != null) {
            onComplete.execute(location != null, location, providerType);
        }
    }

    public void stopLocationService(){
        if (locationManager != null) {
            Log.d(LOG_TAG, "stop location listener");
            locationManager.removeUpdates(this);
            locationManager = null;
        }
    }

    public void updateLocation(Location location) {
        if (location != null) {
            Log.d(LOG_TAG, "update location:" + String.valueOf(location.getLatitude()) + "/" + String.valueOf(location.getLongitude()));
           // Toast.makeText(this, String.valueOf(location.getLatitude()) + "/" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ACTION_LOCATION_UPDATED);
            intent.putExtra(KEY_LATITUDE, location.getLatitude());
            intent.putExtra(KEY_LONGITUDE, location.getLongitude());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "stop location service");
        stopLocationService();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent != null && intent.getAction() != null ? intent.getAction() : ACTION_STOP_SRV;

        if (action.equalsIgnoreCase(ACTION_START)) {
            Log.d(LOG_TAG, "start location service & location listener");
            ApplicationThread.nuiPost(LOG_TAG, "start lococation service", new Runnable() {
                @Override
                public void run() {
                    startLocationService(null);
                }
            });
        } else if (action.equalsIgnoreCase(ACTION_STOP_LBS)) {
            stopLocationService();
        } else {
            stopLocationService();
            stopSelf();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
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
}
