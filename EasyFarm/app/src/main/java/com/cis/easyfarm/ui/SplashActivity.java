package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;


import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.Constants;
import com.cis.easyfarm.common.LogSaver;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.helper.PrefUtil;
import com.cis.easyfarm.localData.SharedPrefsData;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.sync.SyncActivity;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.LoginActivity;



import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import java.io.File;


public class SplashActivity extends AppCompatActivity {

    public static final String LOG_TAG = SplashActivity.class.getName();
String DB_PATH;
    private EasyFarmDatabse easyFarmDatabse;
    private String[] PERMISSIONS_REQUIRED = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
    };
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("appprefs", MODE_PRIVATE);

        if (!CommonUtils.isNetworkAvailable(this)) {
            UiUtils.showCustomToastMessage("Please check your network connection", SplashActivity.this, 1);
        }
        final boolean is_login = SharedPrefsData.getBool(SplashActivity.this, Constants.IS_LOGIN);
        final boolean is_masterDatasync = SharedPrefsData.getBool(SplashActivity.this, CommonConstants.IS_MASTER_SYNC_SUCCESS);
        Log.i("Splash Activity", "=====> Analysis  intial check===> Login"+is_login +" & master sync "+is_masterDatasync);

        final boolean welcome = SharedPrefsData.getBool(SplashActivity.this, Constants.WELCOME);

        final int langID = SharedPrefsData.getInstance(SplashActivity.this).getIntFromSharedPrefs("lang");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CommonUtils.areAllPermissionsAllowed(this, PERMISSIONS_REQUIRED)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, CommonUtils.PERMISSION_CODE);
        } else {
            try {


//                palm3FoilDatabase = EasyFarmDatabse.geteasyfarmDatabase(this);
//              palm3FoilDatabase.createDataBase();
                try{
                    File dbDirectory = new File(CommonUtils.get3FFileRootPath() );

                    if (dbDirectory.isDirectory())
                    {
                        Log.d("SplashActivity","===> Analysis ==> Delete Databse Folders info Start Deleting Directory");
                        String[] children = dbDirectory.list();
                        for (int i = 0; i < children.length; i++)
                        {
                            new File(dbDirectory, children[i]).delete();
                            Log.d("SplashActivity","===> Analysis ==> Delete Databse Folders info :"+children[i]);
                        }
                    }
                }catch (Exception e) {
                    Log.e("SplashActivity","===> Analysis ==> Delete Databse Folders Errror :"+e.getMessage());
                    e.getMessage();
                }




                easyFarmDatabse = EasyFarmDatabse.getEasyFarmDatabase(this);
                easyFarmDatabse.createDataBase();
                dbUpgradeCall();

            } catch (Exception e) {
                Log.d("SplashActivity","===> Analysis ==> Create Databse Folders Errror :"+e.getMessage());
                e.getMessage();
            }

            if(!is_login & is_masterDatasync != true){
                Log.i("Splash Activity", "=====> Analysis ===> Login false & master sync False");
                File dbDirectory = new File(CommonUtils.get3FFileRootPath() + "easyfarm_Database");


                try {
                    FileUtils.deleteDirectory(dbDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startMasterSync();

            }else if(!is_login  && is_masterDatasync == true){
                // user was not login but master sync completed
                Log.i("Splash Activity", "=====> Analysis ===> Login false & master sync true");
                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);
                finish();
            }else if(is_login == true && is_masterDatasync == true){
                Log.i("Splash Activity", "=====> Analysis ===> Login true & master sync true");
                Intent mySuperIntent = new Intent(SplashActivity.this, SyncHomeActivity.class);
                startActivity(mySuperIntent);
                finish();
            }else
            {
                Log.i("Splash Activity", "=====> Analysis ===> Login  & master  sync are not satisfied ");
//                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(mySuperIntent);
//                finish();
                startMasterSync();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CommonUtils.PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(LOG_TAG, "permission granted");
                    try {

//                        String root = Environment.getExternalStorageDirectory().toString();
//                        File del = new File(root + "/EasyFarm_Files");
//                     Log.e("del==========",del+"");
//                        boolean success = del.delete();
//                      Log.e("success=======",success+"");;
//                        if (success) {
//                            System.out.println("Deletion of directory failed!");
//                        }

                        File dbDirectory = new File(CommonUtils.get3FFileRootPath() + "easyfarm_Database");
                        Log.e("del==========", dbDirectory + "");

                        try {
                            FileUtils.deleteDirectory(dbDirectory);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                            try {
//                                File dbDirectory = new File(CommonUtils.get3FFileRootPath());
//
//                                if (dbDirectory.isDirectory()) {
//                                    Log.d("SplashActivity", "===> Analysis ==> Delete Databse Folders info Start Deleting Directory");
//                                    String[] children = dbDirectory.list();
//                                    for (int i = 0; i < children.length; i++) {
//                                        new File(dbDirectory, children[i]).delete();
//                                        Log.d("SplashActivity", "===> Analysis ==> Delete Databse Folders info :" + children[i]);
//                                    }
//                                }
//                            } catch (Exception ee) {
//                                Log.e("SplashActivity", "===> Analysis ==> Delete Databse Folders Errror :" + e.getMessage());
//                                e.getMessage();
//
//                            }


                            easyFarmDatabse = EasyFarmDatabse.getEasyFarmDatabase(this);
                            easyFarmDatabse.createDataBase();
                            dbUpgradeCall();

                        } catch (Exception e) {
                            Log.e(LOG_TAG, "@@@ Error while getting master data " + e.getMessage());
                        }

                        startMasterSync();
                        //  startTranscationSync();

                    }
                break;
        }
    }

    public void startMasterSync() {
        final boolean is_login = SharedPrefsData.getBool(SplashActivity.this, Constants.IS_LOGIN);

        if (CommonUtils.isNetworkAvailable(this) ) {
            if(!sharedPreferences.getBoolean(CommonConstants.IS_MASTER_SYNC_SUCCESS,false)){
            DataSynchelper.performMasterSync(this, PrefUtil.getBool(this, CommonConstants.IS_MASTER_SYNC_SUCCESS), new ApplicationThread.OnComplete() {
                @Override
                public void execute(boolean success, Object result, String msg) {
                   // ProgressBar.hideProgressBar();
                    if (success) {
                        Log.v(LOG_TAG, "@@@ Master sync success " + success);
                        sharedPreferences.edit().putBoolean(CommonConstants.IS_MASTER_SYNC_SUCCESS, true).apply();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Log.v(LOG_TAG, "@@@ Master sync failed " + msg);
                        ApplicationThread.uiPost(LOG_TAG, "master sync message", new Runnable() {
                            @Override
                            public void run() {
                                UiUtils.showCustomToastMessage("Data syncing failed", SplashActivity.this, 1);
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    }
                }
            });
        }else if(is_login)
            {
                startActivity(new Intent(SplashActivity.this, SyncHomeActivity.class));
                finish();
            }else
            {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }
            else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

//    public void startTranscationSync() {
//        if (CommonUtils.isNetworkAvailable(this) && !sharedPreferences.getBoolean(CommonConstants.IS_MASTER_SYNC_SUCCESS,false)) {
//            DataSynchelper.performTranscationSync(this, PrefUtil.getBool(this, CommonConstants.IS_MASTER_SYNC_SUCCESS), new ApplicationThread.OnComplete() {
//                @Override
//                public void execute(boolean success, Object result, String msg) {
//                    // ProgressBar.hideProgressBar();
//                    if (success) {
//                        sharedPreferences.edit().putBoolean(CommonConstants.IS_MASTER_SYNC_SUCCESS, true).apply();
//                        Toast.makeText(SplashActivity.this, "Transaction Sync Successfull", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        android.util.Log.d(LOG_TAG, "@@@ Transaction sync failed " + msg);
//                        ApplicationThread.uiPost(LOG_TAG, "Transaction sync message", new Runnable() {
//                            @Override
//                            public void run() {
//                                UiUtils.showCustomToastMessage("Data syncing failed", SplashActivity.this, 1);
//                                Toast.makeText(SplashActivity.this, "Transaction Sync Failed", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    }
//                }
//            });
//        } else {
//            Toast.makeText(SplashActivity.this, "Transaction Sync Successfull", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//    }

    public void dbUpgradeCall() {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(SplashActivity.this, false);
        String count = dataAccessHandler.getCountValue(Queries.getInstance().UpgradeCount());
        if (TextUtils.isEmpty(count) || Integer.parseInt(count) == 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("appprefs", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(CommonConstants.IS_FRESH_INSTALL, true).apply();
        }
    }
}
