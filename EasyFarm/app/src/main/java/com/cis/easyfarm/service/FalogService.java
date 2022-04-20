package com.cis.easyfarm.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.cis.easyfarm.Interface.LatLongListener;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.LocationProvider;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static com.cis.easyfarm.common.CommonConstants.ServerUpdatedStatus;

public class FalogService extends Service implements LocationListener {

    private static final String LOG_TAG = "MyService";

    private static LocationProvider mLocationProvider;
    private static String latLong="";
//    private final int INTERVAL = 5000;
//    private Timer timer = new Timer();

    PowerManager.WakeLock wakeLock;
    public Context context;
    double latitude, longitude;

    private static final int MIN_UPDATE_TIME = 0;
    private static final int MIN_UPDATE_DISTANCE = 250;
    private Location location;
    public LocationManager locationManager;
    public String CreatedDate, UpdatedDate, ServerUpdatedStatus, CreatedByUserId, UpdatedByUserId, IsActive, IMEINumber;
    public String USER_ID_TRACKING;
String address;
    private EasyFarmDatabse EasefarmDatabase;
    private DataAccessHandler dataAccessHandler ;

  //  DataAccessHandler dataAccessHandler = new DataAccessHandler(this);
    @Override
    public void onCreate() {
        super.onCreate();
//        palm3FoilDatabase = new Palm3FoilDatabase(this);
        Log.v(LOG_TAG, "Congrats! MyService Created");
        CommonUtils.appendLog(LOG_TAG, "onCreate", "Congrats! MyService Created");
//        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "onCreate");


    }

    public static LocationProvider getLocationProvider(Context context, boolean showDialog){
        if(mLocationProvider == null){
            mLocationProvider = new LocationProvider(context, new LatLongListener() {

                public void getLatLong(String mLatLong) {
                    latLong = mLatLong;
                }
            });

        }
        if(mLocationProvider.getLocation(showDialog)){
            return mLocationProvider;
        } else{
            return null;
        }

    }

    public  String getLatLong(Context context,boolean showDialog) {

        mLocationProvider = getLocationProvider(context,showDialog);

        if(mLocationProvider != null){
            latLong = mLocationProvider.getLatitudeLongitude();


        }
        return latLong;
    }




    public void startLocationService(ApplicationThread.OnComplete onComplete) {
        Log.d(LOG_TAG, "start location service");
        CommonUtils.appendLog(LOG_TAG, "startLocationService", "startLocationService");

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
                    //updateLocation(location);
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
                    // updateLocation(location);
                }
            }




        } catch (Exception e) {
            Log.e(LOG_TAG, "Cannot get location", e);
            CommonUtils.appendLog(LOG_TAG, "startLocationService", "Cannot get location");

        }

        if (onComplete != null) {
            onComplete.execute(location != null, location, providerType);
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

      Log.d(LOG_TAG, "start location service & location listener");
        CommonUtils.appendLog(LOG_TAG, "onStartCommand", "start location service & location listener");

        ApplicationThread.nuiPost(LOG_TAG, "start lococation service", new Runnable() {
            @Override
            public void run() {
                startLocationService(null);

            }
        });

        try {
            EasefarmDatabase = EasyFarmDatabse.getEasyFarmDatabase(this);
            EasefarmDatabase.createDataBase();
            dataAccessHandler = new DataAccessHandler(context);
        } catch (Exception e) {
            e.getMessage();
        }
 dataAccessHandler = new DataAccessHandler(this);
//       String query = Queries.getInstance().getUserDetailsNewQuery(CommonUtils.getIMEInumber(this));
//
//        DataAccessHandler dataAccessHandler = new DataAccessHandler(this);
//        final UserDetails userDetails = (UserDetails) dataAccessHandler.getUserDetails(query, 0);
//
//        if (null != userDetails) {
//            USER_ID_TRACKING = userDetails.getId();
//            Log.v(LOG_TAG, "Start Service userId" + USER_ID_TRACKING);
//        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("appprefs", MODE_PRIVATE);
        boolean isFreshInstall = sharedPreferences.getBoolean(CommonConstants.IS_FRESH_INSTALL, true);

        if (location != null) {
//
//            }
            String latlong[]= getLatLong(FalogService.this,false).split("@");

            Log.d(LOG_TAG, "updateTracking location:" + String.valueOf(location.getLatitude()) + "/" + String.valueOf(location.getLongitude()));
            latitude = Double.parseDouble(latlong[0]);
            longitude = Double.parseDouble(latlong[1]);

            CreatedDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS_SSS);
            UpdatedDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS_SSS);
            ServerUpdatedStatus = CommonConstants.ServerUpdatedStatus;
            CreatedByUserId = USER_ID_TRACKING;
            UpdatedByUserId = USER_ID_TRACKING;

            IsActive = "1";

            String selectedLatLong = dataAccessHandler.getFalogLatLongs(Queries.getInstance().queryVerifyFalogDistance());
            if (!TextUtils.isEmpty(selectedLatLong)) {
                Log.v(LOG_TAG, "@@@@ data " + selectedLatLong);
                double actualDistance = 0;
                String[] yieldDataArr = selectedLatLong.split("-");

                if (yieldDataArr.length > 0 && !TextUtils.isEmpty(yieldDataArr[0]) && !TextUtils.isEmpty(yieldDataArr[1])) {

                    actualDistance = CommonUtils.distance( location.getLongitude(), location.getLatitude(), Double.parseDouble(yieldDataArr[0]), Double.parseDouble(yieldDataArr[1]), 'm');

                }

                Log.v(LOG_TAG, "@@@@ actual distance " + actualDistance);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

             address  =addresses.get(0).getSubLocality();

                    address  =addresses.get(0).getSubLocality();

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v(LOG_TAG, "@@@@ actual distance " + actualDistance);

                if (actualDistance >= 50) {


                    EasefarmDatabase.insertLatLong(location.getLatitude(),  location.getLongitude(), address, UpdatedDate,  ServerUpdatedStatus);
                    DataSynchelper.sendTrackingData(this, new ApplicationThread.OnComplete() {
                        @Override
                        public void execute(boolean success, Object result, String msg) {
                            if (success) {
                                Log.v(LOG_TAG, "sent success");
                                CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "Insert Success");

                            } else {
                                Log.e(LOG_TAG, "sent failed");
                                CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "Insert Failed");

                            }
                        }
                    });
                }else {

                }
            }
            else{
                EasefarmDatabase.insertLatLong(location.getLatitude(),  location.getLongitude(), address, UpdatedDate,  ServerUpdatedStatus);
                DataSynchelper.sendTrackingData(this, new ApplicationThread.OnComplete() {
                    @Override
                    public void execute(boolean success, Object result, String msg) {
                        if (success) {
                            CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "Insert Success");
                            Log.v(LOG_TAG, "sent success");
                        } else {
                            Log.e(LOG_TAG, "sent failed");
                            CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "Insert Failed");

                        }
                    }
                });
            }
            }

           /* palm3FoilDatabase.insertLatLong(latitude, longitude, IsActive, CreatedByUserId, CreatedDate, UpdatedByUserId, UpdatedDate, IMEINumber, ServerUpdatedStatus);

            DataSyncHelper.sendTrackingData(context, new ApplicationThread.OnComplete() {
                @Override
                public void execute(boolean success, Object result, String msg) {
                    if (success) {
                        com.oilpalm3f.mainapp.cloudhelper.Log.v(LOG_TAG, "sent success");
                    } else {
                        com.oilpalm3f.mainapp.cloudhelper.Log.e(LOG_TAG, "sent failed");
                    }
                }
            });*/
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
