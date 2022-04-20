package com.cis.easyfarm.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.BaseFragment;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetRoles;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.RegistrationRequest;
import com.cis.easyfarm.model.RegistrationResponse;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import static com.cis.easyfarm.cloudHelper.Config.*;


public class addnewfarmer_plotdetails extends CommonFragment implements View.OnClickListener {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String LOG_TAG = addnewfarmer_plotdetails.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static String user_nam, first_name,middlename,lastname, mobile_number,Email, fatherguardianname, birthdate,adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;
    FusedLocationProviderClient mFusedLocationClient;
    View view;
    String Pincode;
    Spinner spin_plot_state,spin_plot_dist,spin_plot_mandal,spin_plot_village;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    LinearLayout linea_btn;
    private EditText plot_address,plot_address2,totalarea_edittxt,adoptedarea_edittxt, gpsarea_edittxt,surverynumber_edittext,mandal_edittxt,state_edittxt,palmarea_edittxt,Adangal_edittxt,
            address, address2,zip_edittxt, passbook_edittxt,Country_edittxt, ownerame_editext,ownerContactnumber_edittext, address_edittxt,zipcode_edittxt, address2_edittxt,village_edittxt,pin_edittxt,district_edittxt;
    CheckBox checkbox,check_box;
    Spinner spin_state,spin_dist,spin_mandal,spin_village,spin_ownership, spin_plotstatus;
    public static int state_id,dist_id,mandal_id,village_id,ownership_id,plotstatus_id,plot_state_id,plot_dist_id,plot_mandal_id,plot_village_id;;

    List<String>get_role = new ArrayList<String>();
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
    TextInputLayout father_label,address_label,address2_label, village_label, mandal_label, pin_label, dist_label, state_label,ownerame_label,Country_label,zipcode_label,
            totalarea_label, adoptedarea_label, palmarea_label,gpsarea_label,Adangal_label,passbook_label,surverynumber_label,ownerContactnumber_label;
    private int GALLERY = 1, CAMERA = 2;
    private addnewfarmer_plotdetails.OnStepTwoListener mListener;
    Spinner spin_roll;
    String currentDate;

    public static Integer role_id;
    boolean isnri = false;
    LinearLayout plot_details,vfaddress_layout,address_layout;
    RelativeLayout check_nri;
    int stateid_from_server,Pin_villcode,pin_distid,pin_madalid;
    public addnewfarmer_plotdetails() {
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
    public static addnewfarmer_plotdetails newInstance(String param1, String param2) {
        addnewfarmer_plotdetails fragment = new addnewfarmer_plotdetails();
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

        view = inflater.inflate(R.layout.fragment_addnewfarmer_plotdetails, container, false);

        intviews();
        setviews();

        return view;

    }

    private void intviews() {

        Submit = view.findViewById(R.id.signup);
        Country_label = view.findViewById(R.id.Country_label);
        zipcode_label = view.findViewById(R.id.zipcode_label);

        check_nri = view.findViewById(R.id.check_nri);
        linea_btn= view.findViewById(R.id.linea_btn);
        spin_state = view.findViewById(R.id.spin_state);
        spin_dist = view.findViewById(R.id.spin_dist);
        spin_mandal =view.findViewById(R.id.spin_mandal);
        spin_village = view.findViewById(R.id.spin_village);
        spin_roll =view.findViewById(R.id.spin_roll);
        address_label =view.findViewById(R.id.address_label);
        address = view.findViewById(R.id.address_edittxt);
        address2 = view.findViewById(R.id.address2_edittxt);
        address2_label = view.findViewById(R.id.address2_label);
        // totalarea_edittxt= view.findViewById(R.id.Total_Area_edittxt);
        vfaddress_layout = view.findViewById(R.id.vfaddress_layout);
        zip_edittxt = view.findViewById(R.id.zip_edittxt);
        check_box =(CheckBox) view.findViewById(R.id.check_Box);
//
        Country_edittxt = view.findViewById(R.id.Country_edittxt);
        address_layout = view.findViewById(R.id.address_layout);
//
        totalarea_label = view.findViewById(R.id.Total_Area_label);
//






        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
    }

    private void setviews() {


        if (isOnline(getContext())) {

            GetStates();
            // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
        }

        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
        // spin_gender.setOnItemSelectedListener(SignUpActicity.this);



        address.addTextChangedListener(new addnewfarmer_plotdetails.ValidationTextWatcher(address));
        zip_edittxt.addTextChangedListener(new addnewfarmer_plotdetails.ValidationTextWatcher(zip_edittxt));
        Country_edittxt.addTextChangedListener(new addnewfarmer_plotdetails.ValidationTextWatcher(Country_edittxt));
//
        GetAllRole();
        //GetStates();
        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_state = spin_state.getItemAtPosition(spin_state.getSelectedItemPosition()).toString();


                if (position != 0) {
                    state_id = get_state_Id.get(spin_state.getSelectedItemPosition() - 1);
                    Getdistricts(state_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_state = spin_dist.getItemAtPosition(spin_dist.getSelectedItemPosition()).toString();
                if (position != 0) {
                    dist_id = get_district_Id.get(spin_dist.getSelectedItemPosition() - 1);
                    Getmandal(dist_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_mandal.getItemAtPosition(spin_mandal.getSelectedItemPosition()).toString();
                if (position != 0) {
                    mandal_id = get_mandal_Id.get(spin_mandal.getSelectedItemPosition() - 1);
                    GetVillages(mandal_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_village.getItemAtPosition(spin_village.getSelectedItemPosition()).toString();
                if (position != 0) {

                    village_id = get_villages_Id.get(spin_village.getSelectedItemPosition() - 1);
                    Log.d("VillageId", "Village Id is" + village_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_roll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_mandal = spin_roll.getItemAtPosition(spin_roll.getSelectedItemPosition()).toString();
                if (position != 0) {

                    role_id = get_role_Id.get(spin_roll.getSelectedItemPosition() - 1);
                    if (role_id == 2) {
                        linea_btn.setVisibility(View.GONE);
                        nextBT.setVisibility(View.VISIBLE);
                    } else {
                        linea_btn.setVisibility(View.VISIBLE);
                        nextBT.setVisibility(View.GONE);
                    }
                    if(role_id==3){
                        check_nri.setVisibility(View.VISIBLE);
                    }
                    else {
                        check_box.setChecked(false);
                        check_nri.setVisibility(View.GONE);
                        //  isChecked
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked  && role_id==3) {

                    // CostConfig();
                    isnri = true;
                    vfaddress_layout.setVisibility(View.VISIBLE);
                    address_layout.setVisibility(View.GONE);
                }
                else {
                    vfaddress_layout.setVisibility(View.GONE);
                    address_layout.setVisibility(View.VISIBLE);
                    isnri = false;
                }


            }
        });

    }







    private void GetVillages(int mandalid) {
        mdilogue.show();
        get_villages.clear();
        get_villages_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getvillages(Getvillages + mandalid +"/"+null)
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


                            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,get_villages);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_village.setAdapter(aa);


                        } else {
                            get_villages.add("No Villages Available");
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });

    }

    private void Getmandal(int distid) {
        mdilogue.show();
        get_mandal.clear();
        get_mandal_Id.clear();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getmandals(Getmandals + distid +"/"+null)
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


                            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,get_mandal);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_mandal.setAdapter(aa);


                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_mandal.add("No Mandal Available");
                        }

                    }

                });
    }

    private void Getdistricts(int stateid) {
        mdilogue.show();
        get_district.clear();
        get_district_Id.clear();

        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getdistricts(Getdistricts + stateid+"/"+ null)
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

//
                            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,get_district);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_dist.setAdapter(aa);

                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_district.add("No District Available");
                        }

                    }

                });



    }



    private void GetStates() {
        mdilogue.show();
        get_state.clear();
        get_state_Id.clear();
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

//
                            ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,get_state);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_state.setAdapter(aa);




                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_state.add("No State Available");

                        }

                    }

                });


    }
    private void GetAllRole() {
        get_role.clear();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
        mSubscription = service.getrole(Config.Getroles )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetRoles>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {

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


                            ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,get_role);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_roll.setAdapter(aa);


                        } else {
                            Log.e("nodada====", "nodata===custom2");

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


    private FloatingActionButton backBT,nextBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBT=view.findViewById(R.id.backBT);
        nextBT =view.findViewById(R.id.nextBT);


        //   geo_boundaries = view.findViewById(R.id.geo_boundaries);
        Submit = view.findViewById(R.id.signup);
    }


    @Override
    public void onResume() {
        super.onResume();

        backBT.setOnClickListener(this);
        nextBT.setOnClickListener(this);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
        nextBT.setOnClickListener(null);
        Submit.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {
        adress1 = address.getText().toString();
        adress2 = address2.getText().toString();
        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;
            case R.id.nextBT:
                if(validation()) {
                    if (mListener != null)
                        mListener.onNextPressed(this);
                }
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
//                roleid = addnewfarmer_basicdetails.roleid;
//                stateid = addnewfarmer_basicdetails.stateid;
//                distid = addnewfarmer_basicdetails.distid;
//                mandalid = addnewfarmer_basicdetails.mandalid;
//                villageid = addnewfarmer_basicdetails.villageid;
                genderid = addnewfarmer_basicdetails.genderid;

//                com.cis.easyfarm.ui.Log.d(LOG_TAG,"UserNamewwwwwww" + user_nam);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,first_name);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,middlename);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,lastname);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,mobile_number);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,Email);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,fatherguardianname);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,adress1);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,adress2);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,dateof_birth);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,pasword);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,roleid);
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,stateid+ "");
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,distid + "");
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,mandalid + "");
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,villageid + "");
//                com.cis.easyfarm.ui.Log.d(LOG_TAG,genderid + "");

                if(validation()) {

                    if (isOnline(getContext())) {

                        externalRegistrationAPI();
                        // spin_gender.setOnItemSelectedListener(SignUpActicity.this);
                    }

                    else {
                        showDialog(getActivity(), getResources().getString(R.string.Internet));
                        //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                    //externalRegistrationAPI();

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
                    public void onNext(RegistrationResponse registrationResponse) {
                        Log.e("============",registrationResponse+"");
                        mdilogue.dismiss();
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

        requestModel.setGenderTypeId(genderid);
        requestModel.setDOB(birthdate);
        requestModel.setUserName(user_nam);
        requestModel.setPassword(pasword);
        requestModel.setRoleIds(role_id+"");
        requestModel.setEmail(Email);
        requestModel.setServerUpdateStatus(true);
        requestModel.setLatitude(0.00);
        requestModel.setLatitude(0.00);
        requestModel.setCreatedByUserId(1);
        requestModel.setUpdatedByUserId(1);
        requestModel.setIsWillingtoConvert(true);
        requestModel.setUserCode(null);

        requestModel.setServerUpdateStatus(true);


//        requestModel.setAdaptedArea(Double.valueOf(adopted_area.getText().toString()));
//        requestModel.setGPSPlotArea(Double.valueOf(gps_area.getText().toString()));
//        requestModel.setSurveyNumber(survey_number.getText().toString());
//        requestModel.setPlotOwnerShipTypeId(ownership_id);
        requestModel.setIsNRI(isnri);
        if( check_box.isChecked()==true) {
            requestModel.setPostalCode(zip_edittxt.getText().toString());
            requestModel.setCountryName(Country_edittxt.getText().toString());
            requestModel.setAddress1(address.getText().toString());
            requestModel.setAddress2(address2.getText().toString());
            requestModel.setStateId(null);
            requestModel.setDistrictId(null);
            requestModel.setMandalId(null);
            requestModel.setVillageId(null);
        }
        else {
            requestModel.setPostalCode(null);
            requestModel.setCountryName(null);
            requestModel.setAddress1(address.getText().toString());
            requestModel.setAddress2(address2.getText().toString());
            requestModel.setStateId(state_id);
            requestModel.setDistrictId(dist_id);
            requestModel.setMandalId(mandal_id);
            requestModel.setVillageId(village_id);
        }
        if(role_id==2) {
            requestModel.setTotalPlotArea(Double.valueOf(totalarea_edittxt.getText().toString()));
            requestModel.setPAddress1(plot_address.getText().toString());
            requestModel.setPAddress2(plot_address2.getText().toString());
            requestModel.setPStateId(plot_state_id);
            requestModel.setPDistrictId(plot_dist_id);
            requestModel.setPMandalId(plot_mandal_id);
            requestModel.setPVillageId(plot_village_id);
        }

//        requestModel.setIsActive(true);
//        requestModel.setCreatedByUserId(1);
//        requestModel.setCreatedDate(currentDate);
//        requestModel.setUpdatedByUserId(1);
//        requestModel.setUpdatedDate(currentDate);
//        requestModel.setServerUpdatedStatus(true);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();



//                "CreatedByUserId": 35,
//                "CreatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "UpdatedByUserId": 37,
//                "UpdatedDate": "2020-06-05T09:30:50.2506828+05:30",
//                "ServerUpdatedStatus":

    }



    private boolean validation() {
        if (spin_roll.getSelectedItemPosition() == 0) {

            showDialog(getContext(), getResources().getString(R.string.select_role));
            return false;
        }
        if( check_box.isChecked()==true) {
            if (TextUtils.isEmpty(Country_edittxt.getText().toString().trim())) {
                Country_label.setError(getResources().getString(R.string.err_please_address));
                return false;
            } else if(TextUtils.isEmpty(zip_edittxt.getText().toString().trim())) {
                zipcode_label.setError(getResources().getString(R.string.err_please_address));
                return false;
            }
        }
        else    {
            if (TextUtils.isEmpty(address.getText().toString().trim())) {
                address_label.setError(getResources().getString(R.string.err_please_address));
                return false;
            } else if (spin_state.getSelectedItemPosition() == 0) {

                showDialog(getContext(), getResources().getString(R.string.select_state));
                return false;
            } else if (spin_dist.getSelectedItemPosition() == 0) {

                showDialog(getContext(), getResources().getString(R.string.select_district));
                return false;
            } else if (spin_mandal.getSelectedItemPosition() == 0) {

                showDialog(getContext(), getResources().getString(R.string.select_mandal));
                return false;
            } else if (spin_village.getSelectedItemPosition() == 0) {

                showDialog(getContext(), getResources().getString(R.string.select_village));
                return false;
            }
        }
//       else{
//         if (TextUtils.isEmpty(address.getText().toString().trim())) {
//                address_label.setError(getResources().getString(R.string.err_please_address));
//                return false;
//            } else if (spin_state.getSelectedItemPosition() == 0) {
//
//                showDialog(getContext(), getResources().getString(R.string.select_state));
//                return false;
//            } else if (spin_dist.getSelectedItemPosition() == 0) {
//
//                showDialog(getContext(), getResources().getString(R.string.select_district));
//                return false;
//            } else if (spin_mandal.getSelectedItemPosition() == 0) {
//
//                showDialog(getContext(), getResources().getString(R.string.select_mandal));
//                return false;
//            } else if (spin_village.getSelectedItemPosition() == 0) {
//
//                showDialog(getContext(), getResources().getString(R.string.select_village));
//                return false;
//            }
//        }




        // total_area,adopted_area,palm_area,gps_area,plot_address,plot_address2,pincode;
        // total_area_layout,adopted_area_label,adopted_are_label,gps_area_label,plot_address_label,plot_address2_label,pincode_label;

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
        if (context instanceof addnewfarmer_plotdetails.OnStepTwoListener) {
            mListener = (addnewfarmer_plotdetails.OnStepTwoListener) context;
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
    public interface OnStepTwoListener {
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
                case R.id.address_edittxt:
                    validate_address();
                    break;


                case R.id.zip_edittxt:
                    validate_zipcode();
                    break;
                case R.id.Country_edittxt:
                    validate_country();
                    break;


            }}








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



        private void requestFocus(View view) {
            if (view.requestFocus()) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }



        private boolean validate_country() {
            if(TextUtils.isEmpty(Country_edittxt.getText().toString().trim())){
                Country_label.setError(getResources().getString(R.string.err_please_address));
                requestFocus(Country_edittxt);

            }
            else {
                Country_label.setErrorEnabled(false);
            }
            return true;

        }

        private boolean validate_zipcode() {
            if(TextUtils.isEmpty(zip_edittxt.getText().toString().trim())){
                zipcode_label.setError(getResources().getString(R.string.err_please_address));
                requestFocus(zip_edittxt);

            }
            else {
                zipcode_label.setErrorEnabled(false);
            }
            return true;

        }
    }
}
