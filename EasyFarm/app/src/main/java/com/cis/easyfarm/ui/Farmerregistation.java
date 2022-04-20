package com.cis.easyfarm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.BaseActivity;
import com.cis.easyfarm.common.CommonActivity;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetGender;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetOwnershipStatus;
import com.cis.easyfarm.model.GetPlotStatus;
import com.cis.easyfarm.model.GetRoles;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.GetVillagesbypincode;
import com.cis.easyfarm.model.RegistrationRequest;
import com.cis.easyfarm.model.RegistrationResponse;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;
import com.cis.easyfarm.ui.userAccount.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Farmerregistation extends AppCompatActivity implements addnewfarmer_basicdetails.OnStepOneListener, addnewfarmer_plotdetails.OnStepTwoListener,addnew_plotdetails.OnStepthreeListener {

    public static String LOG_TAG = FarmerDetails.class.getSimpleName();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    Toolbar toolbar;

    public static String user_nam, first_name, middlename, lastname, mobile_number, Email, fatherguardianname, adress1, adress2, dateof_birth, pasword, roleid;
    public static int stateid, distid, mandalid, villageid, genderid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farmerregistation);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = findViewById(R.id.stepperIndicator);

        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //
        settoolbar();





        /*// or manual change
        indicator.setStepCount(3);
        indicator.setCurrentStep(2);
*/

    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Select Godown");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }


    @Override
    public int getLayoutResource() {
        return R.layout.activity_farmerregistation;
    }


    @Override
    public void onNextPressed(Fragment fragment) {

        if (fragment instanceof addnewfarmer_basicdetails) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof addnewfarmer_plotdetails) {
            mViewPager.setCurrentItem(2, true);
        } else if (fragment instanceof addnew_plotdetails) {

            mViewPager.setCurrentItem(3, true);
            //  Toast.makeText(this, "Thanks For Registering", Toast.LENGTH_SHORT).show();
            // finish();
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return addnewfarmer_basicdetails.newInstance("", "");

                case 1:
                    return addnewfarmer_plotdetails.newInstance("", "");
                case 2:
                    return addnew_plotdetails.newInstance("", "");
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Level";
                case 1:
                    return "Second Level";
                case 2:
                    return "Third Level";
            }
            return null;
        }
    }


    @SuppressLint("ResourceType")

    @Override
    public void onBackPressed(Fragment fragment) {
        if (fragment instanceof addnewfarmer_plotdetails) {
            mViewPager.setCurrentItem(0, true);
        } else if (fragment instanceof addnew_plotdetails) {
            mViewPager.setCurrentItem(1, true);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SyncHomeActivity.class);
        startActivity(intent);
        finish();
    }
}


