package com.cis.easyfarm.service;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.LocationTracker;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import static com.cis.easyfarm.common.CommonConstants.ServerUpdatedStatus;

@SuppressLint("NewApi")
public class MyLocationServices extends JobService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<Status> {

    /**
     * Update interval of location request
     */
    private final int UPDATE_INTERVAL = 10*60*1000;
    private static final int MIN_UPDATE_TIME = 0;
    private static final int MIN_UPDATE_DISTANCE = 250;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private DataAccessHandler dataAccessHandler;;
    private static final String LOG_TAG = MyLocationServices.class.getName();
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    String address,UpdatedDate;
    private EasyFarmDatabse EasefarmDatabase;
    List<LocationTracker> farmerDetails;
    /**
     * fastest possible interval of location request
     */
    private final int FASTEST_INTERVAL = 10*60*900;

    /**
     * The Job scheduler.
     */
    JobScheduler jobScheduler;

    /**
     * The Tag.
     */
    String TAG = "MyService";

    /**
     * LocationRequest instance
     */
    private LocationRequest locationRequest;

    /**
     * GoogleApiClient instance
     */
    private GoogleApiClient googleApiClient;

    /**
     * Location instance
     */
    private Location lastLocation;

    /**
     * Method is called when location is changed
     * @param location - location from fused location provider
     */
    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "onLocationChanged [" + location + "]");
        CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "onLocationChanged [" + location + "]");

        lastLocation = location;
        writeActualLocation(location);
        // todo insert data

        if (location != null) {
//
        //    CreatedDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS_SSS);
            UpdatedDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS_SSS);
            ServerUpdatedStatus = CommonConstants.ServerUpdatedStatus;
//            CreatedByUserId = USER_ID_TRACKING;
//            UpdatedByUserId = USER_ID_TRACKING;
//
//            IsActive = "1";

            String selectedLatLong = dataAccessHandler.getFalogLatLongs(Queries.getInstance().queryVerifyFalogDistance());
            Log.e("selectedLatLong===",selectedLatLong);
            CommonUtils.appendLog(LOG_TAG, "onLocationChanged", selectedLatLong);

            if (!TextUtils.isEmpty(selectedLatLong)) {
                Log.v(LOG_TAG, "@@@@ data " + selectedLatLong);
                CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "@@@@ data " + selectedLatLong);

                double actualDistance = 0;
                String[] yieldDataArr = selectedLatLong.split("-");

                if (yieldDataArr.length > 0 && !TextUtils.isEmpty(yieldDataArr[0]) && !TextUtils.isEmpty(yieldDataArr[1])) {

                    actualDistance = CommonUtils.distance( location.getLongitude(), location.getLatitude(), Double.parseDouble(yieldDataArr[0]), Double.parseDouble(yieldDataArr[1]), 'm');

                }

                Log.v(LOG_TAG, "@@@@ actual distance " + actualDistance);
                CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "@@@@ data " + actualDistance);

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                    address  =addresses.get(0).getSubLocality();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v(LOG_TAG, "@@@@ actual distance " + actualDistance);
                CommonUtils.appendLog(LOG_TAG, "onLocationChanged", "@@@@ data " + actualDistance);


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
                }
            }

            else{
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
            }
        }
    }



    /**
     * extract last location if location is not available
     */
    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        //Log.d(TAG, "getLastKnownLocation()");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            Log.i(TAG, "LasKnown location. " +
                    "Long: " + lastLocation.getLongitude() +
                    " | Lat: " + lastLocation.getLatitude());

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                address  =addresses.get(0).getSubLocality();
            }catch (IOException e) {
                e.printStackTrace();
            }
            writeLastLocation();
            startLocationUpdates();

        } else {
            Log.w(TAG, "No location retrieved yet");
            CommonUtils.appendLog(LOG_TAG, "getLastKnownLocation", "No location retrieved yet");

            startLocationUpdates();

            //here we can show Alert to start location
        }
    }

    /**
     * this method writes location to text view or shared preferences
     * @param location - location from fused location provider
     */
    @SuppressLint("SetTextI18n")
    private void writeActualLocation(Location location) {
        Log.d(TAG, location.getLatitude() + ", " + location.getLongitude());
        //here in this method you can use web service or any other thing
    }

    /**
     * this method only provokes writeActualLocation().
     */
    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }


    /**
     * this method fetches location from fused location provider and passes to writeLastLocation
     */
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        //Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    //  LocationServices.FusedLocationApi.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_DISTANCE, this);
    }

    /**
     * Default method of service
     * @param params - JobParameters params
     * @return boolean
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        startJobAgain();

        createGoogleApi();

        return false;
    }

    /**
     * Create google api instance
     */
    private void createGoogleApi() {
        //Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //connect google api
        googleApiClient.connect();

    }

    /**
     * disconnect google api
     * @param params - JobParameters params
     * @return result
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        googleApiClient.disconnect();
        return false;
    }

    /**
     * starting job again
     */
    private void startJobAgain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "Job Started");
            CommonUtils.appendLog(LOG_TAG, "startJobAgain", "Job Started");

            ComponentName componentName = new ComponentName(getApplicationContext(),
                    MyLocationServices.class);
            jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                    .setMinimumLatency(10000) //10 sec interval
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setRequiresCharging(false).build();
            jobScheduler.schedule(jobInfo);
            dataAccessHandler = new DataAccessHandler(this);
            try {
                EasefarmDatabase = EasyFarmDatabse.getEasyFarmDatabase(this);
                EasefarmDatabase.createDataBase();
                dataAccessHandler = new DataAccessHandler(this);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**
     * this method tells whether google api client connected.
     * @param bundle - to get api instance
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Log.i(TAG, "onConnected()");
        getLastKnownLocation();
    }

    /**
     * this method returns whether connection is suspended
     * @param i - 0/1
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"connection suspended");
    }

    /**
     * this method checks connection status
     * @param connectionResult - connected or failed
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"connection failed");
        CommonUtils.appendLog(LOG_TAG, "onConnectionFailed", "connection failed");

    }

    /**
     * this method tells the result of status of google api client
     * @param status - success or failure
     */
    @Override
    public void onResult(@NonNull Status status) {
        Log.d(TAG,"result of google api client : " + status);
        CommonUtils.appendLog(LOG_TAG, "onResult", "result of google api client : " + status);

    }
}
