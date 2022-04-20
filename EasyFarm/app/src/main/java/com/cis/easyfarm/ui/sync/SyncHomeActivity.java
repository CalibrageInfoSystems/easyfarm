package com.cis.easyfarm.ui.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cis.easyfarm.Fragments.ConvertedFarmerFragment;
import com.cis.easyfarm.Fragments.HomeFragment;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.cloudHelper.CloudDataHandler;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.HttpClient;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.ProgressBar;
import com.cis.easyfarm.common.ProgressDialogFragment;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;

import com.cis.easyfarm.database.DatabaseKeys;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.helper.PrefUtil;
import com.cis.easyfarm.localData.SharedPrefsData;
import com.cis.easyfarm.model.DevisetokenResopnse;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.model.TokenObject;
import com.cis.easyfarm.service.FalogService;
import com.cis.easyfarm.service.MyLocationServices;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.Adapters.NotificationDisplayAdapter;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.Add_new_Farmer;

import com.cis.easyfarm.ui.Farmer_list;
import com.cis.easyfarm.ui.Farmerregistation;
import com.cis.easyfarm.ui.GoogleSignUpDetails;
import com.cis.easyfarm.ui.SplashActivity;

import com.cis.easyfarm.ui.SyncFragment;
import com.cis.easyfarm.ui.socialmedia.SocialMediaActivity;
import com.cis.easyfarm.ui.userAccount.AddplotActivity;

import com.cis.easyfarm.ui.userAccount.ConvertedPlots;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;
import com.cis.easyfarm.ui.userAccount.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.androidquery.util.AQUtility.getContext;

public class SyncHomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String LOG_TAG = SyncHomeActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 420;
    private GoogleApiClient mGoogleApiClient;
    TextView lastSyncTime;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> allRefreshDataMap;
    private DataAccessHandler dataAccessHandler;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private BottomNavigationView bottom_navigation;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageView notification;
    FloatingActionButton myFab;
    Integer mSelectedItem ;
    private TextView dialogMessage;
    private PopupWindow window;
    private Button ok_btn, cancel_btn;
    TextView txt_count;
    LoginResponse loginressponse;
    String TOken;
public static int User_id;
    private JobScheduler jobScheduler;
    private ComponentName componentName;
    private JobInfo jobInfo;
    private String noticount;
    private String isNew;
private  Toolbar toolbar;
    private String unreadNotificationsCount;

    CallbackManager callbackManager;
    final int SIGN_IN = 1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_home);

        getHashkey();
        initviews();
        setViews();
//        try {
//            Runtime.getRuntime().exec("logcat -f" + " /sdcard/Logcat.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cis.easyfarm",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }

    }
    public void getHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("Facebook Base64", Base64.encodeToString(md.digest(),Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);

        }
    }
    private void initviews() {
        
        dataAccessHandler = new DataAccessHandler(this);

        initToolBar();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.activity_main);

        t = new ActionBarDrawerToggle(this, dl, R.string.app_name, R.string.app_name);
        myFab = (FloatingActionButton) findViewById(R.id.call_fb);

        dl.addDrawerListener(t);
        t.syncState();
        toolbar.setNavigationIcon(R.drawable.toggle_f);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment, HomeFragment.TAG)
                .commit();

        notification = (ImageView)findViewById(R.id.notification) ;
        txt_count= (TextView)findViewById(R.id.txt_count);


//        SharedPreferences sharedPreferences = SyncHomeActivity.this.getSharedPreferences("appprefs", MODE_PRIVATE);
//
//        noticount = sharedPreferences.getString("nCount", null);
//
//        Log.d("noticount", noticount + "");

        Intent n = getIntent();

        Bundle extras = n.getExtras();
        if (extras != null) {
            if (extras.containsKey("nCount")) {
               noticount = extras.getString("nCount", "0");
                Log.d("noticount", noticount + "");
                sharedPreferences = SyncHomeActivity.this.getSharedPreferences("appprefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SaveCount", noticount);
                editor.commit();

                if (noticount != null && !noticount.isEmpty() && !noticount.equals("null")) {
                    txt_count.setText(noticount);
                }else{
                    txt_count.setText("0");
                }

            }
        }
       sharedPreferences = getSharedPreferences("appprefs", MODE_PRIVATE);
//

        final ApplicationThread.OnComplete<String> onComplete = null;
        ImageView notificationsRel = (ImageView) findViewById(R.id.notification);
        allRefreshDataMap = new ArrayList<>();

        allRefreshDataMap.add(DatabaseKeys.TABLE_FORMER);
        allRefreshDataMap.add(DatabaseKeys.TABLE_PLOT);
        allRefreshDataMap.add(DatabaseKeys.TABLE_FILEREPOSITORY);
        allRefreshDataMap.add(DatabaseKeys.TABLE_BANKDetailsHistory);
        allRefreshDataMap.add(DatabaseKeys.TABLE_FARMERBANK);
        allRefreshDataMap.add(DatabaseKeys.TABLE_IDENTITYPROOFS);



        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiverLocation,
                new IntentFilter("data_action"));

 //  StartBackgroundTask();
        if (CommonUtils.isLocationPermissionGranted(SyncHomeActivity.this) ) {
            startService(new Intent(this, FalogService.class));
        }
    }


    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.toggle_f);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(View v) {
                        if (!dl.isDrawerOpen(Gravity.START)) dl.openDrawer(Gravity.START);
                        else dl.closeDrawer(Gravity.END);
                    }
                }
        );
    }

    public BroadcastReceiver mReceiverLocation = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String Items_count = intent.getStringExtra("category");

            txt_count.setText(noticount);
            Log.e("ItemName======",noticount + "");
            CommonUtils.appendLog(LOG_TAG, "BroadcastReceiver", Items_count + "");


        }
    };
    @SuppressLint("NewApi")
    public void StartBackgroundTask() {
        jobScheduler = (JobScheduler)getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        componentName = new ComponentName(getApplicationContext(), MyLocationServices.class);
        jobInfo = new JobInfo.Builder(1, componentName)
                .setMinimumLatency(60*10000) //10 sec interval
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setRequiresCharging(false).build();
        jobScheduler.schedule(jobInfo);
    }

    private void setViews() {
        nv.setNavigationItemSelectedListener(this);

        if (CommonUtils.isNetworkAvailable(this)) {
            loginressponse = SharedPrefsData.getCatagories(SyncHomeActivity.this);
            Log.d("TOken ", "" + FirebaseInstanceId.getInstance().getToken());
            CommonUtils.appendLog(LOG_TAG, "setViews", FirebaseInstanceId.getInstance().getToken() + "");

            TOken = FirebaseInstanceId.getInstance().getToken();
            FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
//            if(getIntent()!=null) {
            User_id = loginressponse.getResult().getUserInfos().getId();
//                Log.e("User_id==",User_id);
//            }

            UpdateDeviseTokenByUserId();
        } else {
            UiUtils.showCustomToastMessage("Internet Not Available", SyncHomeActivity.this, 1);
        }
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        bottomNavigationViewHelper.disableShiftMode(bottom_navigation);


        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new HomeFragment(), HomeFragment.TAG);
                        break;
                    }
                    case R.id.action_registration: {
                        mSelectedItem = item.getItemId();
                        registerpoup();
                        break;
                    }
                    case R.id.action_sync: {
                        mSelectedItem = item.getItemId();

                        viewFragment(new SyncFragment(), SyncFragment.TAG);
                        break;
                    }
                    case R.id.action_complaits: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new ConvertedFarmerFragment(), ConvertedFarmerFragment.TAG);
                        break;

                    }
                    case R.id.action_insurance: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new ConvertedPlots(), ConvertedPlots.TAG);
                        break;

                    }

                }
                return true;
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SyncHomeActivity.this, NotificationsScreen.class));
            }
        });


    }

    private void UpdateDeviseTokenByUserId() {
        JsonObject object = tokenObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.gettokenresponse(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DevisetokenResopnse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }

                        UiUtils.showCustomToastMessage("An Error has Occurred. ", SyncHomeActivity.this, 1);
                    }

                    @Override
                    public void onNext(DevisetokenResopnse devisetokenResopnse) {



                        if (devisetokenResopnse.getIsSuccess()) {
                      //    Toast.makeText(getApplicationContext(),devisetokenResopnse.getEndUserMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                           // Toast.makeText(getApplicationContext(),devisetokenResopnse.getEndUserMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }});}





    private JsonObject tokenObject() {
        TokenObject requestModel = new TokenObject();
        requestModel.setDeviseToken(TOken);
        requestModel.setUserId(User_id);


        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }


    private void registerpoup() {
        final Dialog dialog = new Dialog(SyncHomeActivity.this, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");

//        final GoogleApiClient googleApiClient;
//
//        callbackManager = CallbackManager.Factory.create();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        // set the custom forgotPasswordDialog components - text, image and button
        final LinearLayout Farmer = dialog.findViewById(R.id.farmer);
        final LinearLayout plot = dialog.findViewById(R.id.plot);
        final Button farmer_list =dialog.findViewById(R.id.farmer_list);
        final Button plotlist =dialog.findViewById(R.id.btnplot);

        //Facebook & Google Sign Up Code



        TextView cancel = dialog.findViewById(R.id.CancelBtn);


/**
 * @param OnClickListner
 *
 *
 */
        Farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "en" is the localization code for our default English language.
                 */


                Intent refresh = new Intent(getApplicationContext(), Farmerregistation.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });
        farmer_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "en" is the localization code for our default English language.
                 */


                Intent refresh = new Intent(getApplicationContext(), Farmerregistation.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });

/**
 * @param OnClickListner
 */
        plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "te" is the localization code for our default Telugu language.
                 */

                Intent refresh = new Intent(getApplicationContext(), Farmer_list.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });

        plotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "te" is the localization code for our default Telugu language.
                 */

                Intent refresh = new Intent(getApplicationContext(), Farmer_list.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
//    p

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        callbackManager.onActivityResult(requestCode,resultCode,data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.d("Login with google",result.getStatus().toString());
//            handleGPlusSignInResult(result);
//        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
       // int id = menuItem.getItemId();
        int id = item.getItemId();
        Log.e("id===", String.valueOf(id));
        if (id == R.id.action_home) {

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, homeFragment, null)
                    .commit();
            // Select home item
            bottom_navigation.setSelectedItemId(id);
            // finish();
            bottom_navigation.setSelectedItemId(R.id.action_home);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);

        } else if (id == R.id.action_sync) {


            mSelectedItem = item.getItemId();
            viewFragment(new SyncFragment(), SyncFragment.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_sync);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);


        } else if (id == R.id.action_complaits) {
            mSelectedItem = item.getItemId();
            viewFragment(new ConvertedFarmerFragment(), ConvertedFarmerFragment.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_complaits);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.action_insurance) {
            mSelectedItem = item.getItemId();
            viewFragment(new ConvertedPlots(), ConvertedPlots.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_insurance);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.action_registration) {

                registerpoup();

        }
        return true;
    }

//  case R.id.action_sync: {
//        mSelectedItem = item.getItemId();
//
//        viewFragment(new SyncFragment(), SyncFragment.TAG);
//        break;
//    }
//                    case R.id.action_complaits: {
//        mSelectedItem = item.getItemId();
//        viewFragment(new ConvertedFarmerFragment(), ConvertedFarmerFragment.TAG);
//        break;
//
//    }
//                    case R.id.action_insurance: {
//        mSelectedItem = item.getItemId();
//        viewFragment(new ConvertedPlots(), ConvertedPlots.TAG);
//        break;
//
//    }

    public class BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        public void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                  item.setShifting(true);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }

    private void viewFragment(Fragment fragment, String name) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        // 1. Know how many fragments there are in the stack
        final int count = fragmentManager.getBackStackEntryCount();
        // 2. If the fragment is **not** "home type", save it to the stack
        if (name.equals(HomeFragment.TAG)) {
            fragmentTransaction.addToBackStack(name);
        }
        // Commit !
        fragmentTransaction.commit();
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(HomeFragment.TAG, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    bottom_navigation.getMenu().findItem(R.id.action_home).setChecked(true);
                   // bottom_navigation.getMenu().getItem(1).setChecked(true);
                }
            }
        });
    }

    @Override
    public void onResume() {
   registerReceiver(mReceiverLocation, new IntentFilter("data_action"));
        super.onResume();

        Log.d("OnResume:", "is called");

        String var1 = sharedPreferences.getString("SaveCount","");

        if (var1 != null && !var1.isEmpty() && !var1.equals("null")) {
            txt_count.setText(var1);
        }
        else{
            txt_count.setText("0");
        }
    }

    @Override
    public void onPause() {
     unregisterReceiver(mReceiverLocation);
        super.onPause();

        Log.d("onPause:", "is called");

        Intent n = getIntent();

        Bundle extras = n.getExtras();
        if (extras != null) {
            if (extras.containsKey("nCount")) {
                noticount = extras.getString("nCount", "0");
                Log.d("noticount", noticount + "");
                if (noticount != null && !noticount.isEmpty() && !noticount.equals("null")) {
                    txt_count.setText(noticount);
                }else{
                    txt_count.setText("0");
                }

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        Log.d("onStop:", "is called");


        Intent n = getIntent();

        Bundle extras = n.getExtras();
        if (extras != null) {
            if (extras.containsKey("nCount")) {
                noticount = extras.getString("nCount", "0");
                Log.d("noticount", noticount + "");
                if (noticount != null && !noticount.isEmpty() && !noticount.equals("null")) {
                    txt_count.setText(noticount);
                }else{
                    txt_count.setText("0");
                }

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy:", "is called");

        Intent n = getIntent();

        Bundle extras = n.getExtras();
        if (extras != null) {
            if (extras.containsKey("nCount")) {
                noticount = extras.getString("nCount", "0");
                Log.d("noticount", noticount + "");
                if (noticount != null && !noticount.isEmpty() && !noticount.equals("null")) {
                    txt_count.setText(noticount);
                }else{
                    txt_count.setText("0");
                }

            }
        }
    }




    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottom_navigation.getMenu().getItem(0);
Log.e("homeItem=====",homeItem+"");
        if (mSelectedItem !=null && mSelectedItem != homeItem.getItemId()) {

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, homeFragment, null)
                    .commit();
            // Select home item
            bottom_navigation.setSelectedItemId(homeItem.getItemId());
        } else {
            super.onBackPressed();
        }
    }
}

//    private void bankdetailsSync() {
//            DataSynchelper.performbankSync(this, PrefUtil.getBool(this, CommonConstants.IS_MASTER_SYNC_SUCCESS), new ApplicationThread.OnComplete() {
//                @Override
//                public void execute(boolean success, Object result, String msg) {
//                    // ProgressBar.hideProgressBar();
//                    if (success) {
//                        sharedPreferences.edit().putBoolean(CommonConstants.IS_MASTER_SYNC_SUCCESS, true).apply();
//                        Log.v(LOG_TAG, "@@@ bank sync success " + msg);
////                        startActivity(new Intent(SyncHomeActivity.this, LoginActivity.class));
////                        finish();
//                    } else {
//                 Log.v(LOG_TAG, "@@@ Master sync failed " + msg);
//                        ApplicationThread.uiPost(LOG_TAG, "master sync message", new Runnable() {
//                            @Override
//                            public void run() {
//                                UiUtils.showCustomToastMessage("bank detailsData syncing failed", SyncHomeActivity.this, 1);
////                                startActivity(new Intent(SyncHomeActivity.this, LoginActivity.class));
////                                finish();
//                            }
//                        });
//                    }
//                }
//            });
//        }



