package com.cis.easyfarm.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.BaseFragment;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.GetVillagesbypincode;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class addnew_plotdetails  extends CommonFragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String LOG_TAG = addnewfarmer_plotdetails.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Double lattitude, Longitude;
    public static String user_nam, first_name,middlename,lastname, mobile_number,Email, fatherguardianname, birthdate,adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;
    FusedLocationProviderClient mFusedLocationClient;
    View view;
    String Pincode;
    Spinner spin_plot_state,spin_plot_dist,spin_plot_mandal,spin_plot_village;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;

    private EditText plot_address,plot_address2,totalarea_edittxt,adoptedarea_edittxt, gpsarea_edittxt,surverynumber_edittext,mandal_edittxt,state_edittxt,palmarea_edittxt,Adangal_edittxt,
            address, address2, passbook_edittxt, ownerame_editext,ownerContactnumber_edittext, address_edittxt, address2_edittxt,village_edittxt,pin_edittxt,district_edittxt;

    Spinner spin_state,spin_dist,spin_mandal,spin_village,spin_ownership, spin_plotstatus;
    int state_id,dist_id,mandal_id,village_id,ownership_id,plotstatus_id,plot_state_id,plot_dist_id,plot_mandal_id,plot_village_id;;

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

    int PERMISSION_ID = 44;
    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    TextInputLayout father_label,address_label,address2_label, village_label, mandal_label, pin_label, dist_label, state_label,ownerame_label,
            totalarea_label, adoptedarea_label, palmarea_label,gpsarea_label,Adangal_label,passbook_label,surverynumber_label,ownerContactnumber_label;
    private int GALLERY = 1, CAMERA = 2;
    private OnStepthreeListener mListener;
    Spinner spin_roll;
    String currentDate;
    Integer role_id;
    LinearLayout plot_details;
    int stateid_from_server,Pin_villcode,pin_distid,pin_madalid;
    public addnew_plotdetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BePartnerStepOneFragment.
     */
// TODO: Rename and change types and number of parameters
    public static addnew_plotdetails newInstance(String param1, String param2) {
        addnew_plotdetails fragment = new addnew_plotdetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_addnew_plotdetails, container, false);

        intviews();
        setviews();

        return view;

    }

    private void intviews() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
//

        Submit = view.findViewById(R.id.nextBT);



        spin_state = view.findViewById(R.id.spin_state);
        spin_dist = view.findViewById(R.id.spin_dist);
        spin_mandal =view.findViewById(R.id.spin_mandal);
        spin_village = view.findViewById(R.id.spin_village);
        spin_roll =view.findViewById(R.id.spin_roll);
        address_label =view.findViewById(R.id.address_label);
        address = view.findViewById(R.id.address_edittxt);
        address2 = view.findViewById(R.id.address2_edittxt);
        address2_label = view.findViewById(R.id.address2_label);
        totalarea_edittxt= view.findViewById(R.id.Total_Area_edittxt);
//
        totalarea_label = view.findViewById(R.id.Total_Area_label);
//

        ;


        plot_details = view.findViewById(R.id.plot_details);

        plot_address=view.findViewById(R.id.addresss_plot_edittxt);
        plot_address2=view.findViewById(R.id.addres_plot_edittxt);
        spin_plot_state = view.findViewById(R.id.spin_plot_state);
        spin_plot_dist =view. findViewById(R.id.spin_plot_dist);
        spin_plot_mandal =view.findViewById(R.id.spin_plot_mandal);
        spin_plot_village = view.findViewById(R.id.spin_plot_village);


        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
    }

    private void setviews() {
        if (isOnline(getContext())) {

            getLastLocation();
            GettAddressByPinCode();
            // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
        }

        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }

        totalarea_edittxt.addTextChangedListener(new ValidationTextWatcher(totalarea_edittxt));
        // adoptedarea_edittxt.addTextChangedListener(new addnewfarmer_plotdetails.ValidationTextWatcher(adoptedarea_edittxt));
        // gpsarea_edittxt.addTextChangedListener(new addnewfarmer_plotdetails.ValidationTextWatcher(gpsarea_edittxt));
//        address.addTextChangedListener(new ValidationTextWatcher(address));
//        address2.addTextChangedListener(new ValidationTextWatcher(address2));
//



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
                    Log.d(LOG_TAG, "dist_id======" + dist_id);
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
                    Log.d(LOG_TAG, "mandal_id======" + mandal_id);
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


    }


    private void GettAddressByPinCode() {

        mdilogue.show();
        get_villages.clear();
        get_villages_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
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
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
                        if (isOnline(getContext())) {

                            GetStates();
                            // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
                        }

                        else {
                            showDialog(getActivity(), getResources().getString(R.string.Internet));
                            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
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
                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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


                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                plot_address.setText(addresses.get(0).getSubThoroughfare() + ","+ addresses.get(0).getThoroughfare());
                plot_address2.setText(addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality());
                // plot_address.setText();
                Log.e("lat====================",mLastLocation.getLatitude()+"");
                Log.e("long==================",mLastLocation.getLongitude()+"");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void GetplotVillages(int plot_mandal_id) {
        mdilogue.show();
        get_villages.clear();
        get_villages_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
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
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
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



                            ArrayAdapter village = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,get_villages);
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
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
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
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
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

                            ArrayAdapter mandal = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,get_mandal);
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
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
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
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
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

                            ArrayAdapter dist= new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,get_district);
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
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
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
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
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


                            ArrayAdapter state = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, get_state);
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

    private void showPictureDialog2() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());


    }

    private void choosePhotoFromGallary2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 3);
    }
    private void takePhotoFromCamera2() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 4);
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    Button geo_boundaries,Submit;


    private FloatingActionButton backBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBT=view.findViewById(R.id.backBT);


        //   geo_boundaries = view.findViewById(R.id.geo_boundaries);
        Submit = view.findViewById(R.id.signup);
    }


    @Override
    public void onResume() {
        super.onResume();

        backBT.setOnClickListener(this);
        // geo_boundaries.setOnClickListener(this);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
//        geo_boundaries.setOnClickListener(null);
        Submit.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;

            case R.id.signup:
                user_nam = addnewfarmer_basicdetails.user_name;
                first_name = addnewfarmer_basicdetails.first_name;
                middlename = addnewfarmer_basicdetails.middlename;
                lastname = addnewfarmer_basicdetails.lastname;
                mobile_number = addnewfarmer_basicdetails.mobile_number;
                Email = addnewfarmer_basicdetails.Email;
                fatherguardianname = addnewfarmer_basicdetails.fatherguardianname;

                dateof_birth = addnewfarmer_basicdetails.dateof_birth;
                pasword = addnewfarmer_basicdetails.pasword;
                role_id = addnewfarmer_plotdetails.role_id;
                stateid = addnewfarmer_plotdetails.state_id;
                distid = addnewfarmer_plotdetails.dist_id;
                mandalid = addnewfarmer_plotdetails.mandal_id;
                villageid = addnewfarmer_plotdetails.village_id;
                genderid = addnewfarmer_basicdetails.genderid;
                adress1 = addnewfarmer_plotdetails.adress1;
                adress2 = addnewfarmer_plotdetails.adress2;

//

                if(validation()) {
                    if (mListener != null)
                        if (isOnline(getContext())) {

                            externalRegistrationAPI();
                            // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
                        }

                        else {
                            showDialog(getActivity(), getResources().getString(R.string.Internet));
                            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                        }
                    //externalRegistrationAPI();
                    mListener.onNextPressed(this);
                }
                break;
//            case R.id.geo_boundaries:
//                startActivity(new Intent(getContext(), PreViewAreaCalScreen.class));
//                break;
        }
    }

    private void externalRegistrationAPI() {

        mdilogue.show();

        JsonObject object = externalRegistrationObject();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
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
                        showDialog(getActivity(), getString(R.string.server_error));
                    }



                    @Override
                    public void onNext(RegistrationResponse registrationResponse) {
                        mdilogue.dismiss();
                        Log.e("============",registrationResponse+"");


                        if (registrationResponse.getIsSuccess()) {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        /* Create an Intent that will start the Menu-Activity. */

                                        startActivity(new Intent(getActivity(), SyncHomeActivity.class));
                                        //finish();
                                    }
                                }, 300);


                        } else {
                            showDialog(getActivity(),registrationResponse.getEndUserMessage() );
                        }

                        Toast.makeText(getContext(),registrationResponse.getEndUserMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private JsonObject externalRegistrationObject() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);
        // dob = date_birth.getText().toString().trim();

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            birthdate = myFormat.format(fromUser.parse(dateof_birth));
            Log.d("collection", "RESPONSE reformattedStr======" + birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RegistrationRequest requestModel = new RegistrationRequest();
        requestModel.setFirstName(first_name);
        requestModel.setMiddleName(middlename);
        requestModel.setLastName(lastname);
        requestModel.setPrimaryPhoneNumber(mobile_number);
        requestModel.setFatherOrGuardianName(fatherguardianname);
        requestModel.setAddress1(adress1);
        requestModel.setAddress2(adress2);
        requestModel.setStateId(stateid);
        requestModel.setDistrictId(distid);
        requestModel.setMandalId(mandalid);
        requestModel.setVillageId(villageid);
        requestModel.setGenderTypeId(genderid);
        requestModel.setDOB(birthdate);
        requestModel.setUserName(user_nam);
        requestModel.setPassword(pasword);
        requestModel.setRoleIds(role_id+"");
        requestModel.setEmail(Email);
        requestModel.setServerUpdateStatus(true);
        requestModel.setLatitude(lattitude);
        requestModel.setLongitude(Longitude);
        requestModel.setUserCode(null);

        requestModel.setPostalCode(null);
        requestModel.setCountryName(null);
        requestModel.setTotalPlotArea(Double.valueOf(totalarea_edittxt.getText().toString()));
        requestModel.setPAddress1(plot_address.getText().toString());
        requestModel.setPAddress2(plot_address2.getText().toString());
        requestModel.setPStateId(plot_state_id);
        requestModel.setPDistrictId(plot_dist_id);
        requestModel.setPMandalId(plot_mandal_id);
        requestModel.setPVillageId(plot_village_id);
        requestModel.setCreatedByUserId(1);
        requestModel.setUpdatedByUserId(1);
        requestModel.setIsWillingtoConvert(true);



//
        return new Gson().toJsonTree(requestModel).getAsJsonObject();



//                "CreatedByUserId": 35,
//                "CreatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "UpdatedByUserId": 37,
//                "UpdatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "ServerUpdatedStatus":

    }



    private boolean validation() {

        // total_area,adopted_area,palm_area,gps_area,plot_address,plot_address2,pincode;
        // total_area_layout,adopted_area_label,adopted_are_label,gps_area_label,plot_address_label,plot_address2_label,pincode_label;

        if (TextUtils.isEmpty(totalarea_edittxt.getText().toString().trim())) {
            totalarea_label.setError(getResources().getString(R.string.err_please_Totalarea));
            return false;
        } else if (spin_plot_state.getSelectedItemPosition() == 0) {

            showDialog(getActivity(), getResources().getString(R.string.select_state));
            return false;
        } else if (spin_plot_dist.getSelectedItemPosition() == 0) {

            showDialog(getActivity(), getResources().getString(R.string.select_district));
            return false;
        } else if (spin_plot_mandal.getSelectedItemPosition() == 0) {

            showDialog(getActivity(), getResources().getString(R.string.select_mandal));
            return false;
        } else if (spin_plot_village.getSelectedItemPosition() == 0) {

            showDialog(getActivity(), getResources().getString(R.string.select_village));
            return false;
        }


        return true;
    }


    public void showDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof addnew_plotdetails.OnStepthreeListener) {
            mListener = (addnew_plotdetails.OnStepthreeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepthreeListener {
        void onBackPressed(Fragment fragment);
        void onNextPressed(Fragment fragment);
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }



        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.Total_Area_edittxt:
                    validate_totalarea();
                    break;



            }}




        private boolean validate_totalarea() {
            if (TextUtils.isEmpty(totalarea_edittxt.getText().toString())) {
                totalarea_label.setError("Please Enter Total Area");
                requestFocus(totalarea_edittxt);
            } else {
                totalarea_label.setErrorEnabled(false);

            }
            return true;
        }



    }


    private boolean validate_surveynumber() {
        if (TextUtils.isEmpty(surverynumber_edittext.getText().toString())) {
            surverynumber_label.setError("Please Enter Passbook Number");
            requestFocus(surverynumber_edittext);
        } else {
            surverynumber_label.setErrorEnabled(false);
        }
        return true;
    }




    private boolean validate_address() {
        if(TextUtils.isEmpty(address.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address);

        }
        else {
            address_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_address2() {
        if(TextUtils.isEmpty(address2.getText().toString().trim())){
            address2_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address2);

        }
        else {
            address2_label.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

