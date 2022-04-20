package com.cis.easyfarm.ui;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.CommonActivity;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetRoles;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.GetVillagesbypincode;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.model.Loginobject;
import com.cis.easyfarm.model.RegistrationRequest;
import com.cis.easyfarm.model.RegistrationResponse;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class Exsting_Farmer_list extends CommonActivity {
    public static String LOG_TAG = Exsting_Farmer_list.class.getSimpleName();
  String username,password;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
    String datetimevaluereq,Exit_username,Exit_mobilenumber,Exit_Email,Exit_firstname,Exit_middlename,Exit_lastname,Exit_gardianname
    ,dateogbirth;
    Toolbar toolbar;
    Spinner spin_roll;
    List<String> get_role = new ArrayList<String>();
    List<Integer> get_role_Id = new ArrayList<Integer>();
    List<String> get_state = new ArrayList<String>();
    List<Integer> get_state_Id = new ArrayList<Integer>();

    List<String>get_district = new ArrayList<String>();
    List<Integer> get_district_Id = new ArrayList<Integer>();

    List<String>get_mandal = new ArrayList<String>();
    List<Integer> get_mandal_Id = new ArrayList<Integer>();

    List<String>get_villages = new ArrayList<String>();
    List<Integer> get_villages_Id = new ArrayList<Integer>();
    public  Integer role_id;
    String Pincode;
   LinearLayout plot_details;
   Button submit;
   String currentDate;
    int PERMISSION_ID = 44;
    private EditText plot_address,plot_address2,totalarea_edittxt;
    Spinner spin_plot_state,spin_plot_dist,spin_plot_mandal,spin_plot_village;
    Double lattitude, Longitude;
    FusedLocationProviderClient mFusedLocationClient;
    public  String user_name,first_name,middlename,lastname, Existing_villagename,Existing_role,Exsting_mandal,Existing_dist,Existing_state,
            mobile_number,Email, fatherguardianname,Existing_address,Existing_address2, adress1,adress2,dateof_birth, pasword;
    String roleid;
    public  int stateid,distid,mandalid,villageid,genderid,plot_state_id,plot_dist_id,plot_mandal_id,plot_village_id;
    int stateid_from_server,Pin_villcode,pin_distid,pin_madalid;
    LinearLayout plot_info;
    TextInputLayout totalarea_label;
    String Farmer_code;
    private TextView address, address2,farmer_name, father_name, gender, ext_role, village, mandal, dist, state, category, mobile, alt_mobile, email,farmer_code,dateofbirth,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exsting__farmer_list);
        settoolbar();
        initviews();

        setviews();
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
    private void initviews() {
        farmer_name = (TextView) findViewById(R.id.farmerName);
        father_name = (TextView) findViewById(R.id.fatherName);
        gender = (TextView) findViewById(R.id.gender);
        village = (TextView) findViewById(R.id.village);
        mandal = (TextView) findViewById(R.id.mandal);
        dist = (TextView) findViewById(R.id.district);
        state = (TextView) findViewById(R.id.state);
        dateofbirth = (TextView)findViewById(R.id.dateofbirth);
        alt_mobile = (TextView)findViewById(R.id.alternatemobilenumber);
        email = (TextView)findViewById(R.id.emailid);
        ext_role =(TextView)findViewById(R.id.ext_role);
        spin_roll = findViewById(R.id.spin_roll);
        plot_details =findViewById(R.id.plot_details);
       address =findViewById(R.id.address);
        address2 =findViewById(R.id.address2);
        submit =findViewById(R.id.submit);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        plot_address=findViewById(R.id.addresss_plot_edittxt);
        plot_address2=findViewById(R.id.addres_plot_edittxt);
        spin_plot_state = findViewById(R.id.spin_plot_state);
        spin_plot_dist =findViewById(R.id.spin_plot_dist);
        spin_plot_mandal =findViewById(R.id.spin_plot_mandal);
        spin_plot_village = findViewById(R.id.spin_plot_village);
        totalarea_edittxt= findViewById(R.id.Total_Area_edittxt);
//
      totalarea_label = findViewById(R.id.Total_Area_label);

    }
    private void setviews() {
        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        password= intent.getStringExtra("password");
        Log.e("=============",password+"========"+username);

        if (isOnline()) {

            user_login();
            getLastLocation();
            GettAddressByPinCode();
            GetAllRole();
        }
// }
        else {
            showDialog(this, getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }


        spin_roll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_roll.getItemAtPosition(spin_roll.getSelectedItemPosition()).toString();
                if (position != 0) {

                    role_id = get_role_Id.get(spin_roll.getSelectedItemPosition() - 1);
                    Log.d("RoleId11", role_id + "");
                    Log.e("RoleId21",roleid+"");
                    if(roleid.contains(role_id+"")|| roleid.equalsIgnoreCase(role_id+"")){
                        Toast.makeText(Exsting_Farmer_list.this, "Role already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else if (role_id == 2) {

                        plot_details.setVisibility(View.VISIBLE);
                    } else {

                        plot_details.setVisibility(View.GONE);
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_plot_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_state = spin_plot_state.getItemAtPosition(spin_plot_state.getSelectedItemPosition()).toString();


                if (position != 0) {
                    plot_state_id = get_state_Id.get(spin_plot_state.getSelectedItemPosition() - 1);
                    Getplotdistricts(plot_state_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_plot_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_state = spin_plot_dist.getItemAtPosition(spin_plot_dist.getSelectedItemPosition()).toString();
                if (position != 0) {
                    //spin_plot_dist.setSelection(2);
                    plot_dist_id = get_district_Id.get(spin_plot_dist.getSelectedItemPosition() - 1);
                    Log.d(LOG_TAG, "dist_id======" + plot_dist_id);
                    Getplotmandal(plot_dist_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_plot_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_plot_mandal.getItemAtPosition(spin_plot_mandal.getSelectedItemPosition()).toString();
                if (position != 0) {
                    plot_mandal_id = get_mandal_Id.get(spin_plot_mandal.getSelectedItemPosition() - 1);
                    Log.d(LOG_TAG, "mandal_id======" + plot_mandal_id);
                    GetplotVillages(plot_mandal_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_plot_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_plot_village.getItemAtPosition(spin_plot_village.getSelectedItemPosition()).toString();
                if (position != 0) {

                    plot_village_id = get_villages_Id.get(spin_plot_village.getSelectedItemPosition() - 1);
                }
                spin_plot_village.setPrompt(selected_mandal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validation()) {
                    if (isOnline()) {
                        externalRegistrationAPI();
                    }
                    else {
                        showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.Internet));
                        //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private void GettAddressByPinCode() {

        mdilogue.show();
        get_villages.clear();
        get_villages_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getvillagesbypincode(Config.GetAddressByPinCode + Pincode )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetVillagesbypincode>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "GettAddressByPinCode", e.getMessage());

                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "GettAddressByPinCode", e1.getMessage());
                            }
                            CommonUtils.appendLog(LOG_TAG, "GettAddressByPinCode", e.getMessage());
                            e.printStackTrace();
                        }
                        mdilogue.cancel();
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetVillagesbypincode getVillagesbypincode) {
                        if (getVillagesbypincode.getListResult() != null) {
                            for (GetVillagesbypincode.ListResult data : getVillagesbypincode.getListResult()
                            ) {

                                Pin_villcode =data.getVillageId();
                                pin_distid  = data.getDistrictId();
                                stateid_from_server =data.getStateId();
                                pin_madalid =data.getMandalId();

                            }



                        }
                        if (isOnline()) {

                            GetStates();
                            // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
                        }

                        else {
                            //showDialog(this, getResources().getString(R.string.Internet));
                           Toast.makeText(Exsting_Farmer_list.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }

    private void GetplotVillages(int plot_mandal_id) {
        mdilogue.show();
        get_villages.clear();
        get_villages_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getvillages(Config.Getvillages + plot_mandal_id +"/"+null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetVillages>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "GetplotVillages", e.getMessage());
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "GetplotVillages", e1.getMessage());
                            }
                            e.printStackTrace();
                            CommonUtils.appendLog(LOG_TAG, "GetplotVillages", e.getMessage());
                        }
                        mdilogue.cancel();
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetVillages getVillages) {

                        if (getVillages.getListResult() != null) {
                            get_villages.add("Select Village");
                            for (GetVillages.ListResult data : getVillages.getListResult()
                            ) {
                                get_villages.add(data.getName());
                                get_villages_Id.add(data.getId());
                            }
                            Log.d(LOG_TAG, "RESPONSE======" + get_state);



                            ArrayAdapter village = new ArrayAdapter(Exsting_Farmer_list.this,android.R.layout.simple_spinner_item,get_villages);
                            village.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_plot_village.setAdapter(village);

                        } else {
                            get_villages.add("No Villages Available");
                            Log.e("nodada====", "nodata===custom2");

                        }
                        for(int i=0; i< getVillages.getListResult().size();i++)
                        {
                            if( getVillages.getListResult().get(i).getId() == Pin_villcode)
                            {
                                spin_plot_village.setSelection(i+1);


                            }
                        }
                    }

                });
    }

    private void Getplotmandal(int plot_dist_id) {
        mdilogue.show();
        get_mandal.clear();
        get_mandal_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getmandals(Config.Getmandals + plot_dist_id +"/"+null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetMandals>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "Getplotmandal", e.getMessage());

                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "Getplotmandal", e1.getMessage());

                            }
                            e.printStackTrace();
                            CommonUtils.appendLog(LOG_TAG, "Getplotmandal", e.getMessage());

                        }
                        mdilogue.cancel();
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetMandals getMandals) {


                        if (getMandals.getListResult() != null) {
                            get_mandal.add("Select Mandal");
                            for (GetMandals.ListResult data : getMandals.getListResult()
                            ) {
                                get_mandal.add(data.getName());
                                get_mandal_Id.add(data.getId());
                            }
                            Log.d(LOG_TAG, "RESPONSE======" + get_state);

                            ArrayAdapter mandal = new ArrayAdapter(Exsting_Farmer_list.this,android.R.layout.simple_spinner_item,get_mandal);
                            mandal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_plot_mandal.setAdapter(mandal);


                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_mandal.add("No Mandal Available");
                        }
                        for(int i=0; i< getMandals.getListResult().size();i++)
                        {
                            if( getMandals.getListResult().get(i).getId() == pin_madalid)
                            {
                                spin_plot_mandal.setSelection(i+1);
                            }
                        }
                    }

                });
    }

    private void Getplotdistricts(int plot_state_id) {
        mdilogue.show();
        get_district.clear();
        get_district_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getdistricts(Config.Getdistricts + plot_state_id+"/"+ null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetDistricts>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "Getplotdistricts", e.getMessage());

                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "Getplotdistricts", e1.getMessage());

                            }
                            e.printStackTrace();
                            CommonUtils.appendLog(LOG_TAG, "Getplotdistricts", e.getMessage());

                        }
                        mdilogue.cancel();
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetDistricts getDistricts) {

                        if (getDistricts.getListResult() != null) {
                            get_district.add("Select District");
                            for (GetDistricts.ListResult data : getDistricts.getListResult()
                            ) {
                                get_district.add(data.getName());
                                get_district_Id.add(data.getId());
                            }
                            Log.d(LOG_TAG, "RESPONSE======" + get_state);

//

                            ArrayAdapter dist= new ArrayAdapter(Exsting_Farmer_list.this,android.R.layout.simple_spinner_item,get_district);
                            dist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_plot_dist.setAdapter(dist);

                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_district.add("No District Available");
                        }
                        for(int i=0; i< getDistricts.getListResult().size();i++)
                        {
                            if( getDistricts.getListResult().get(i).getId() == pin_distid)
                            {
                                spin_plot_dist.setSelection(i+1);
                            }
                        }
                    }

                });
    }




    private void GetStates() {
        mdilogue.show();
        get_state.clear();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getstates(Config.Getstate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetStates>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "GetStates", e.getMessage());

                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "GetStates", e1.getMessage());

                            }
                            e.printStackTrace();
                            CommonUtils.appendLog(LOG_TAG, "GetStates", e.getMessage());

                        }
                        mdilogue.cancel();
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetStates getStates) {

                        if (getStates.getListResult() != null) {
                            get_state.add("Select State");
                            for (GetStates.ListResult data : getStates.getListResult()
                            ) {
                                get_state.add(data.getName());
                                get_state_Id.add(data.getId());
                            }


                            ArrayAdapter state = new ArrayAdapter(Exsting_Farmer_list.this, android.R.layout.simple_spinner_item, get_state);
                            state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_plot_state.setAdapter(state);
                            for (int i = 0; i < getStates.getListResult().size(); i++) {
                                if (getStates.getListResult().get(i).getId() == stateid_from_server) {
                                    Log.e(LOG_TAG, "i===========1" + stateid_from_server);
                                    Log.e(LOG_TAG, "i===========2" + i);
                                    spin_plot_state.setSelection(i + 1);
                                }
                            }

                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_state.add("No State Available");

                        }

                    }

                });


    }


    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Geocoder geocoder = new Geocoder(Exsting_Farmer_list.this, Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        plot_address.setText(addresses.get(0).getSubThoroughfare() + ","+ addresses.get(0).getThoroughfare());
                                        plot_address2.setText(addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality());
                                        Pincode = addresses.get(0).getPostalCode();
                                        Log.e("Pincode=========",Pincode);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    lattitude =location.getLatitude();
                                    Longitude=location.getLongitude();
                                    Log.e("lat====================",location.getLatitude()+"");
                                    Log.e("long==================",location.getLongitude()+"");
                                    CommonUtils.appendLog(LOG_TAG, "getLastLocation", location.getLatitude()+"");
                                    CommonUtils.appendLog(LOG_TAG, "getLastLocation", location.getLongitude()+"");



                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Longitude =mLastLocation.getLongitude();
            lattitude = mLastLocation.getLatitude();
            // plot_address.setText();
            Log.e("lat====================",mLastLocation.getLatitude()+"");
            Log.e("long==================",mLastLocation.getLongitude()+"");
            CommonUtils.appendLog(LOG_TAG, "LocationCallback", mLastLocation.getLatitude()+"");
            CommonUtils.appendLog(LOG_TAG, "LocationCallback", mLastLocation.getLatitude()+"");

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    private void externalRegistrationAPI() {

        mdilogue.show();

        JsonObject object = externalRegistrationObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.externalRegistrationPage(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegistrationResponse>() {
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

                        mdilogue.cancel();
                        //showDialog(this, getString(R.string.server_error));
                    }



                    @Override
                    public void onNext(RegistrationResponse registrationResponse) {
                        Log.e("============",registrationResponse+"");
                        mdilogue.dismiss();
                        if (registrationResponse.getIsSuccess()) {


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */

                                    startActivity(new Intent(Exsting_Farmer_list.this, SyncHomeActivity.class));
                                   finish();
                                }
                            }, 300);

                        } else {
                            Toast.makeText(Exsting_Farmer_list.this,registrationResponse.getEndUserMessage(), Toast.LENGTH_SHORT).show();

                          //  showDialog(,registrationResponse.getEndUserMessage() );
                        }


                    }
                });

    }

    private JsonObject externalRegistrationObject() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);


        RegistrationRequest requestModel = new RegistrationRequest();
        requestModel.setFirstName(Exit_firstname);
        requestModel.setMiddleName(Exit_middlename);
        requestModel.setLastName(Exit_lastname);
        requestModel.setPrimaryPhoneNumber(Exit_mobilenumber);
        requestModel.setFatherOrGuardianName(Exit_gardianname);
        requestModel.setAddress1(Existing_address);
        requestModel.setAddress2(Existing_address2);
        requestModel.setStateId(stateid);
        requestModel.setDistrictId(distid);
        requestModel.setMandalId(mandalid);
        requestModel.setVillageId(villageid);
        requestModel.setGenderTypeId(genderid);
        requestModel.setDOB(dateogbirth);
        requestModel.setUserName(Exit_username);
        requestModel.setPassword(password);
        requestModel.setRoleIds(roleid +","+role_id+"");
        requestModel.setEmail(Exit_Email);
        requestModel.setServerUpdateStatus(true);
        requestModel.setLatitude(lattitude);
        requestModel.setLongitude(Longitude);
        requestModel.setUserCode(Farmer_code);
        requestModel.setCreatedByUserId(1);
        requestModel.setUpdatedByUserId(1);

        requestModel.setIsWillingtoConvert(true);
        requestModel.setIsNRI(false);
        requestModel.setCountryName(null);
        requestModel.setPostalCode(null);

        //stateid,distid,mandalid,villageid,genderid,roleid
        if(role_id==2 ) {
            requestModel.setTotalPlotArea(Double.valueOf(totalarea_edittxt.getText().toString()));
            requestModel.setPAddress1(plot_address.getText().toString());
            requestModel.setPAddress2(plot_address2.getText().toString());
            requestModel.setPStateId(plot_state_id);
            requestModel.setPDistrictId(plot_dist_id);
            requestModel.setPMandalId(plot_mandal_id);
            requestModel.setPVillageId(plot_village_id);

        }
        else{
            requestModel.setTotalPlotArea(null);
            requestModel.setPAddress1(null);
            requestModel.setPAddress2(null);
            requestModel.setPStateId(null);
            requestModel.setPDistrictId(null);
            requestModel.setPMandalId(null);
            requestModel.setPVillageId(-1);
        }




//
        return new Gson().toJsonTree(requestModel).getAsJsonObject();



//                "CreatedByUserId": 35,
//                "CreatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "UpdatedByUserId": 37,
//                "UpdatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "ServerUpdatedStatus":

    }


    private boolean validation() {
        if (spin_roll.getSelectedItemPosition() == 0) {

            showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.select_role));
            return false;
        }
        if(roleid.contains(role_id+"")|| roleid.equalsIgnoreCase(role_id+"")){
            showDialog(Exsting_Farmer_list.this, "Already Exist this Role");
            return false;
            //Toast.makeText(Exsting_Farmer_list.this, " Already Exist this Role  ", Toast.LENGTH_SHORT).show();
        }
        if(role_id==2) {
            if (TextUtils.isEmpty(totalarea_edittxt.getText().toString().trim())) {
                totalarea_label.setError(getResources().getString(R.string.err_please_Totalarea));
                return false;
            } else if (spin_plot_state.getSelectedItemPosition() == 0) {

                showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.select_state));
                return false;
            } else if (spin_plot_dist.getSelectedItemPosition() == 0) {

                showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.select_district));
                return false;
            } else if (spin_plot_mandal.getSelectedItemPosition() == 0) {

                showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.select_mandal));
                return false;
            } else if (spin_plot_village.getSelectedItemPosition() == 0) {

                showDialog(Exsting_Farmer_list.this, getResources().getString(R.string.select_village));
                return false;
            }
        }

return true;
    }

    private void user_login() {

        JsonObject object = loginObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getuserLogin(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            CommonUtils.appendLog(LOG_TAG, "user_login", e.getMessage());

                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                CommonUtils.appendLog(LOG_TAG, "user_login", e1.getMessage());

                            }
                            e.printStackTrace();
                            CommonUtils.appendLog(LOG_TAG, "user_login", e.getMessage());

                        }

                       // showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(final LoginResponse loginResponse) {

                        if (loginResponse.getIsSuccess()) {

                            Log.d("loginResponse======",loginResponse.getResult().getUserInfos().getFirstName() );
                            String name = loginResponse.getResult().getUserInfos().getFirstName() + " " + loginResponse.getResult().getUserInfos().getMiddleName() + " " + loginResponse.getResult().getUserInfos().getLastName();
                            farmer_name.setText("" + name.replace("null", ""));
                            village.setText("" + loginResponse.getResult().getUserInfos().getVillageName());
                            gender.setText("" + loginResponse.getResult().getUserInfos().getGender());
                            mandal.setText("" + loginResponse.getResult().getUserInfos().getMandalName());
                            dist.setText("" + loginResponse.getResult().getUserInfos().getDistrictName());
                            state.setText("" + loginResponse.getResult().getUserInfos().getStateName());
                            alt_mobile.setText("" + loginResponse.getResult().getUserInfos().getPrimaryPhoneNumber());
                            father_name.setText("" + loginResponse.getResult().getUserInfos().getFatherNameGuardianName());
                            email.setText("" + loginResponse.getResult().getUserInfos().getEmail());
                            ext_role.setText("" + loginResponse.getResult().getUserInfos().getRoles());
                            try {
                                Date oneWayTripDate = input.parse(loginResponse.getResult().getUserInfos().getDOB());

                                datetimevaluereq = output.format(oneWayTripDate);


                                Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateofbirth.setText("" + datetimevaluereq);
                           // first_name,middlename,lastname

                             Exit_username =loginResponse.getResult().getUserInfos().getUserName();

                             Exit_mobilenumber =loginResponse.getResult().getUserInfos().getPrimaryPhoneNumber();
                             Exit_Email =loginResponse.getResult().getUserInfos().getEmail();
                             Exit_firstname =loginResponse.getResult().getUserInfos().getFirstName();
                             Exit_middlename =loginResponse.getResult().getUserInfos().getMiddleName();
                             Exit_lastname =loginResponse.getResult().getUserInfos().getLastName();
                             Exit_gardianname =loginResponse.getResult().getUserInfos().getFatherNameGuardianName();
                             dateogbirth =loginResponse.getResult().getUserInfos().getDOB();

                            roleid = loginResponse.getResult().getUserInfos().getRoleIds();
                            Log.e("======roleid",roleid);
                            genderid=loginResponse.getResult().getUserInfos().getGenderTypeId();
                            stateid =loginResponse.getResult().getUserInfos().getStateId();
                            distid =loginResponse.getResult().getUserInfos().getDistrictId();
                            mandalid = loginResponse.getResult().getUserInfos().getMandalId();
                            villageid =loginResponse.getResult().getUserInfos().getVillageId();
                             Farmer_code =loginResponse.getResult().getUserInfos().getCode();
                            Existing_address =loginResponse.getResult().getUserInfos().getAddress1();
                            address.setText(Existing_address);
                            Existing_address2=loginResponse.getResult().getUserInfos().getAddress2();
                            address2.setText(Existing_address2);

                        } else {

                            //showDialog(getActivity(),loginResponse.getEndUserMessage() );
                        }
                    }


                });}



    private JsonObject loginObject() {
        Loginobject requestModel = new Loginobject();
        requestModel.setUserName(username);
        requestModel.setPassword(password);


        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }
    private void GetAllRole() {
        get_role.clear();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getrole(Config.Getroles )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetRoles>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.appendLog(LOG_TAG, "GetAllRole", e.getMessage());

                    }

                    @Override
                    public void onNext(GetRoles getRoles) {

                        if (getRoles.getListResult() != null) {
                            get_role.add("Select Role");
                            for (GetRoles.ListResult data : getRoles.getListResult()
                            ) {
                                get_role.add(data.getName());
                                get_role_Id.add(data.getId());
                            }
                            Log.d(LOG_TAG, "RESPONSE======" + get_role);


                            ArrayAdapter aa = new ArrayAdapter(Exsting_Farmer_list.this,android.R.layout.simple_spinner_item,get_role);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_roll.setAdapter(aa);


                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }

}
