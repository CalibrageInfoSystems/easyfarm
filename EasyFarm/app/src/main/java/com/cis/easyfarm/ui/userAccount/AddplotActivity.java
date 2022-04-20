package com.cis.easyfarm.ui.userAccount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.CommonActivity;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetOwnershipStatus;
import com.cis.easyfarm.model.GetPlotStatus;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.RegistrationRequest;
import com.cis.easyfarm.model.RegistrationResponse;
import com.cis.easyfarm.model.plotRegistrationRequest;
import com.cis.easyfarm.model.plotregistationResponse;
import com.cis.easyfarm.ui.PreViewAreaCalScreen;
import com.cis.easyfarm.ui.addnewfarmer_basicdetails;
import com.cis.easyfarm.ui.addnewfarmer_plotdetails;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cis.easyfarm.Fragments.Adduser_fragment.DATE_FORMAT1;

public class AddplotActivity extends CommonActivity {

    public static String LOG_TAG = addnewfarmer_plotdetails.class.getSimpleName();

    public static String user_nam, first_name,middlename,lastname, mobile_number,Email, fatherguardianname, adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;

    View view;

    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private  Toolbar toolbar;

    private EditText totalarea_edittxt,adoptedarea_edittxt, gpsarea_edittxt,surverynumber_edittext,mandal_edittxt,state_edittxt,palmarea_edittxt,Adangal_edittxt,
            passbook_edittxt, ownerame_editext,ownerContactnumber_edittext, address_edittxt, address2_edittxt,village_edittxt,pin_edittxt,district_edittxt,Farmer_Code;

    Spinner spin_state,spin_dist,spin_mandal,spin_village,spin_ownership, spin_plotstatus;
    int state_id,dist_id,mandal_id,village_id,ownership_id,plotstatus_id;
    public static Button geo_boundaries,submit;

    List<String> get_state = new ArrayList<String>();
    List<Integer> get_state_Id = new ArrayList<Integer>();

    List<String>get_district = new ArrayList<String>();
    List<Integer> get_district_Id = new ArrayList<Integer>();

    List<String>get_mandal = new ArrayList<String>();
    List<Integer> get_mandal_Id = new ArrayList<Integer>();

    List<String>get_villages = new ArrayList<String>();
    List<Integer> get_villages_Id = new ArrayList<Integer>();

    List<String>get_plotownershipdetails = new ArrayList<String>();
    List<Integer> get_plotownershipdetails_id = new ArrayList<Integer>();

    List<String>get_plotstatusdetails = new ArrayList<String>();
    List<Integer> get_plotstatusdetails_id = new ArrayList<Integer>();

    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    TextInputLayout father_label,address_label,address2_label, village_label, mandal_label, pin_label, dist_label, state_label,ownerame_label,
            totalarea_label, adoptedarea_label, palmarea_label,gpsarea_label,Adangal_label,passbook_label,surverynumber_label,ownerContactnumber_label;
    private int GALLERY = 1, CAMERA = 2;
    private addnewfarmer_plotdetails.OnStepTwoListener mListener;
    DataAccessHandler dataAccessHandler;
    String currentDate;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplot);
        settoolbar();
        intviews();
        setviews();

    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setTitle("Select Godown");
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


    private void intviews() {

        dataAccessHandler = new DataAccessHandler(this.getApplicationContext());
        address_edittxt = findViewById(R.id.address_edittxt);
        address2_edittxt = findViewById(R.id.addres_edittxt);
         Farmer_Code = findViewById(R.id.Farmer_code_edittxt);
        totalarea_edittxt= findViewById(R.id.Total_Area_edittxt);
        adoptedarea_edittxt= findViewById(R.id.Adopted_area_edittxt);
        palmarea_edittxt= findViewById(R.id.Palm_Area_edittxt);
        Adangal_edittxt = findViewById(R.id.surveynumber_edittxt);
        gpsarea_edittxt= findViewById(R.id.gps_are_edittxt);
        surverynumber_edittext = findViewById(R.id.surveynumber_edittxt);
        passbook_edittxt = findViewById(R.id.passbook_number_edittxt);
        ownerContactnumber_edittext = findViewById(R.id.owner_contactNumber_edittxt);
        ownerame_editext = findViewById(R.id.owner_name_edittxt);
        address_edittxt = findViewById(R.id.address_edittxt);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();


        totalarea_label = findViewById(R.id.Total_Area_label);
        adoptedarea_label = findViewById(R.id.Adopted_Area_label);
        gpsarea_label = findViewById(R.id.Gps_Area_label);
        surverynumber_label = findViewById(R.id.surveynumber_label);
        passbook_label = findViewById(R.id.passbook_number_label);
        ownerContactnumber_label = findViewById(R.id.owner_contactNumber);
        ownerame_label = findViewById(R.id.owner_name);
        address_label= findViewById(R.id.address_label);
        address2_label = findViewById(R.id.address2_label);

        spin_state = findViewById(R.id.spin_state);
        spin_dist = findViewById(R.id.spin_district);
        spin_mandal = findViewById(R.id.spin_mandal);
        spin_village = findViewById(R.id.spin_village);
        spin_ownership = findViewById(R.id.spin_Ownership);
        spin_plotstatus = findViewById(R.id.spin_plotStatus);

    }

    private void setviews() {
        Intent i = getIntent();
        code = i.getStringExtra("code");

      Log.d("Code", code + "");

        totalarea_edittxt.addTextChangedListener(new ValidationTextWatcher(totalarea_edittxt));
        adoptedarea_edittxt.addTextChangedListener(new ValidationTextWatcher(adoptedarea_edittxt));
        gpsarea_edittxt.addTextChangedListener(new ValidationTextWatcher(gpsarea_edittxt));
        address_edittxt.addTextChangedListener(new ValidationTextWatcher(address_edittxt));
        surverynumber_edittext.addTextChangedListener(new ValidationTextWatcher(surverynumber_edittext));
        passbook_edittxt.addTextChangedListener(new ValidationTextWatcher(passbook_edittxt));
        ownerame_editext.addTextChangedListener(new ValidationTextWatcher(ownerame_editext));
        ownerContactnumber_edittext.addTextChangedListener(new ValidationTextWatcher(ownerContactnumber_edittext));
        Farmer_Code.setText(code);
        if (isOnline())  {
            getplotstatus();
            getplotownership();
            GetStates();
        }
        else {
            showDialog(this, getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
        submit = findViewById(R.id.submit);
        geo_boundaries = findViewById(R.id.geo_boundaries);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()) {
                    plotRegistrationAPI();
                }
            }
        });

        geo_boundaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddplotActivity.this, PreViewAreaCalScreen.class));
            }
        });


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

        spin_plotstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_state = spin_plotstatus.getItemAtPosition(spin_plotstatus.getSelectedItemPosition()).toString();


                if (position != 0) {
                    plotstatus_id = get_plotstatusdetails_id.get(spin_plotstatus.getSelectedItemPosition() - 1);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        spin_ownership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected_ownership= spin_ownership.getItemAtPosition(spin_ownership.getSelectedItemPosition()).toString();


                if (position != 0) {

                    ownership_id = get_plotownershipdetails_id.get(spin_ownership.getSelectedItemPosition() - 1);

                }
                if(ownership_id==108){
                    ownerame_label.setVisibility(View.VISIBLE);
                    ownerContactnumber_label.setVisibility(View.VISIBLE);

                }
                else {
                    ownerame_label.setVisibility(View.GONE);
                    ownerContactnumber_label.setVisibility(View.GONE);                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void GetVillages(int mandalid) {
        mdilogue.show();
        get_villages.clear();
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.getvillages(Config.Getvillages + mandalid +"/"+null)
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


                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_villages);
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
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.getmandals(Config.Getmandals + distid +"/"+null)
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


                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_mandal);
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
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.getdistricts(Config.Getdistricts + stateid+"/"+ null)
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
                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_district);
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
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
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
                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_state);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_state.setAdapter(aa);



                        } else {
                            Log.e("nodada====", "nodata===custom2");
                            get_state.add("No State Available");

                        }

                    }

                });


    }


    private void getplotstatus() {
        get_plotstatusdetails.clear();
        get_plotstatusdetails_id.clear();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.getstatus(Config.GetPlotstatus )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetPlotStatus>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetPlotStatus getPlotStatus) {


                        if (getPlotStatus.getListResult() != null) {
                            get_plotstatusdetails.add("Select PlotStatus");
                            for (GetPlotStatus.ListResult data : getPlotStatus.getListResult()
                            ) {
                                get_plotstatusdetails.add(data.getDesc());
                                get_plotstatusdetails_id.add(data.getTypeCdId());
                            }

                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_plotstatusdetails);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_plotstatus.setAdapter(aa);


                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }
    private void getplotownership() {
        get_plotownershipdetails.clear();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.getownership(Config.GetPlotownership )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetOwnershipStatus>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetOwnershipStatus getOwnershipStatus) {


                        if (getOwnershipStatus.getListResult() != null) {
                            get_plotownershipdetails.add("Select Plot OwnerShip");
                            for (GetOwnershipStatus.ListResult data : getOwnershipStatus.getListResult()
                            ) {
                                get_plotownershipdetails.add(data.getDesc());
                                get_plotownershipdetails_id.add(data.getTypeCdId());
                            }


                            ArrayAdapter aa = new ArrayAdapter(AddplotActivity.this,android.R.layout.simple_spinner_item,get_plotownershipdetails);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_ownership.setAdapter(aa);


                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });


    }


    private void plotRegistrationAPI() {
        JsonObject object = plotRegistrationObject();
        ApiService service = ServiceFactory.createRetrofitService(AddplotActivity.this, ApiService.class);
        mSubscription = service.plotRegistrationPage(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<plotregistationResponse>() {
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
                        // showDialog(SignUpActicity.this, getString(R.string.server_error));
                    }


                    @Override
                    public void onNext(plotregistationResponse plotRegistationResponse) {

                        if(plotRegistationResponse.getIsSuccess() == true){

                            Toast.makeText(AddplotActivity.this,"Plot Registration Successfull", Toast.LENGTH_SHORT).show();
                           // {"Result":{"Id":83,"Code":"PAPKHAVJWVJW00003","FarmerCode":"APGNTRALTML00006","TotalPlotArea":48.56,"AdaptedArea":48.56,"GPSPlotArea":95.51,"SurveyNumber":"767687","PassbookNumber":"hcavjVjs","PlotOwnerShipTypeId":108,"OwnerName":"hzjJ","OwnerContactNumber":"hJaj","PlotStatusId":104,"Address1":"gzivj","Address2":"hcZvj","StateId":1,"DistrictId":3,"MandalId":30,"VillageId":11,"IsActive":true,"CreatedByUserId":1,"CreatedDate":"2020-06-24T15:20:15.863","UpdatedByUserId":1,"UpdatedDate":"2020-06-24T15:20:15.863","ServerUpdatedStatus":false},"IsSuccess":true,"AffectedRecords":1,"EndUserMessage":"Plot Registered Successfully","ValidationErrors":[],"Exception":null}
                            Log.d("Registration",  "Plot Registration Successfull");
                            String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

                            LinkedHashMap map= new LinkedHashMap();


                            map.put("Id", plotRegistationResponse.getResult().getId());
                            map.put("Code",  plotRegistationResponse.getResult().getCode());
                            map.put("FarmerCode", plotRegistationResponse.getResult().getFarmerCode());
                            map.put("TotalPlotArea" ,plotRegistationResponse.getResult().getTotalPlotArea());
                            map.put("AdaptedArea",  plotRegistationResponse.getResult().getAdaptedArea());
                            map.put("GPSPlotArea",  plotRegistationResponse.getResult().getGPSPlotArea());
                            map.put("SurveyNumber",  plotRegistationResponse.getResult().getSurveyNumber());
                            map.put("PassbookNumber",  plotRegistationResponse.getResult().getPassbookNumber());
                            map.put("PlotOwnerShipTypeId",  plotRegistationResponse.getResult().getPlotOwnerShipTypeId());

                            if(spin_ownership.getSelectedItemPosition() == 1){

                                map.put("OwnerName",  plotRegistationResponse.getResult().getOwnerName());
                                map.put("OwnerContactNumber",plotRegistationResponse.getResult().getOwnerContactNumber());

                            }else {

                                map.put("OwnerName", "");
                                map.put("OwnerContactNumber", "");
                            }

                            map.put("PlotStatusId", plotRegistationResponse.getResult().getPlotStatusId());
                            map.put("Address1", plotRegistationResponse.getResult().getAddress1());
                            map.put("Address2", plotRegistationResponse.getResult().getAddress2());
                            map.put("StateId",  plotRegistationResponse.getResult().getStateId());
                            map.put("DistrictId",  plotRegistationResponse.getResult().getDistrictId());
                            map.put("MandalId",  plotRegistationResponse.getResult().getMandalId());
                            map.put("VillageId",  plotRegistationResponse.getResult().getVillageId());
                            map.put("IsActive", true);
                            map.put("CreatedByUserId", plotRegistationResponse.getResult().getCreatedByUserId());
                            map.put("CreatedDate",  plotRegistationResponse.getResult().getCreatedDate());
                            map.put("UpdatedByUserId",  plotRegistationResponse.getResult().getUpdatedByUserId());
                            map.put("UpdatedDate", timeStamp);
                            map.put("ServerUpdatedStatus", 0);
//

                            List<LinkedHashMap> list = new ArrayList<>();

                            list.add(map);



//                            dataAccessHandler.updateData("Plot", list, true, " where FarmerCode = " + "'"+farmerDetails.get(0).getCode()+"'", new ApplicationThread.OnComplete<String>() {
//                                @Override
//                                public void execute(boolean success, String result, String msg) {
//                                    super.execute(success, result, msg);
//                                    if (success) {
//                                        Log.v(LOG_TAG, "Plot Details inserted Successfully");
//                                    }
//
//                                }
//                            });

                            dataAccessHandler.insertData("Plot", list, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    super.execute(success, result, msg);

                                    if (success) {
                                        Log.v(LOG_TAG, "Plot Details inserted Successfully");
                                        CommonUtils.appendLog(LOG_TAG, "plotRegistrationAPI", "Plot Details inserted Successfully");

                                        Toast.makeText(AddplotActivity.this,"Plot Details inserted Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        }
                        }


                    });
                }



    private JsonObject plotRegistrationObject() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);
        plotRegistrationRequest requestModel = new plotRegistrationRequest();

        requestModel.setId(85);
        requestModel.setCode(null);
        requestModel.setFarmerCode(code);
        requestModel.setTotalPlotArea(Double.parseDouble(String.valueOf(totalarea_edittxt.getText())));
        requestModel.setAdaptedArea(Double.parseDouble(String.valueOf(adoptedarea_edittxt.getText())));
        requestModel.setGPSPlotArea(Double.parseDouble(String.valueOf(gpsarea_edittxt.getText())));
        requestModel.setSurveyNumber(surverynumber_edittext.getText().toString());
        requestModel.setPassbookNumber(passbook_edittxt.getText().toString());
        requestModel.setPlotOwnerShipTypeId(ownership_id);
        requestModel.setOwnerName(ownerame_editext.getText().toString());
        requestModel.setOwnerContactNumber(ownerContactnumber_edittext.getText().toString());
        requestModel.setPlotStatusId(plotstatus_id);
        requestModel.setAddress1(address_edittxt.getText().toString());
        requestModel.setAddress2(address2_edittxt.getText().toString());
        requestModel.setStateId(state_id);
        requestModel.setDistrictId(dist_id);
        requestModel.setMandalId(mandal_id);
        requestModel.setVillageId(village_id);
        requestModel.setIsActive(true);
        requestModel.setCreatedByUserId(SyncHomeActivity.User_id);
        requestModel.setCreatedDate(currentDate);
        requestModel.setUpdatedByUserId(SyncHomeActivity.User_id);
        requestModel.setUpdatedDate(currentDate);
        requestModel.setServerUpdatedStatus(true);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();

    }



    private boolean validation() {


        if (TextUtils.isEmpty(totalarea_edittxt.getText().toString().trim())) {
            totalarea_label.setError("Please Enter Total Area");
            return false;
        }

        else if (TextUtils.isEmpty(adoptedarea_edittxt.getText().toString().trim())) {

            adoptedarea_label.setError("Please Enter Adopted Area");

            return false;
        }

        else if (TextUtils.isEmpty(gpsarea_edittxt.getText().toString().trim())) {

            gpsarea_label.setError("Please Enter GPS Area");

            return false;
        }
        else if (TextUtils.isEmpty(surverynumber_edittext.getText().toString().trim())) {
            surverynumber_label.setError("Please Enter Survey Number");

            return false;
        }

        else if (TextUtils.isEmpty(passbook_edittxt.getText().toString().trim())) {
            passbook_label.setError("Please Enter Passbook Number");

            return false;
        }



        else if (spin_ownership.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this, "Please Select Plot OwnerShip");
            return false;
        }

        else if(spin_ownership.getSelectedItemPosition() == 1){
            if (TextUtils.isEmpty(ownerame_editext.getText().toString().trim())) {
                ownerame_label.setError("Please Enter Owner Name");
                return false;
            } else if (TextUtils.isEmpty(ownerContactnumber_edittext.getText().toString().trim())) {
                ownerContactnumber_label.setError("Please Enter Owner Contact Number");
                return false;
            }
        }


         if (spin_plotstatus.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this, "Please Select Plot Status");
            return false;
        }

         if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            return false;
        }

         if (spin_state.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this, "Please Select State");
            return false;
        }

         if (spin_dist.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this, "Please Select District");
            return false;
        }

         if (spin_mandal.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this, "Please Select Mandal");
            return false;
        }

         if (spin_village.getSelectedItemPosition() == 0) {

            showDialog(AddplotActivity.this,"Please Select Village");
            return false;
        }



        return true;

//        if (TextUtils.isEmpty(totalarea_edittxt.getText().toString().trim())) {
//            totalarea_label.setError("Please Enter Total Area");
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(adoptedarea_edittxt.getText().toString().trim())) {
//
//            adoptedarea_label.setError("Please Enter Adopted Area");
//
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(gpsarea_edittxt.getText().toString().trim())) {
//
//            gpsarea_label.setError("Please Enter GPS Area");
//
//            return false;
//        } else if (TextUtils.isEmpty(surverynumber_edittext.getText().toString().trim())) {
//
//            gpsarea_label.setError("Please Enter Survey Number");
//
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(passbook_edittxt.getText().toString().trim())) {
//            passbook_label.setError("Please Enter Passbook Number");
//
//            return false;
//        }
//
//        else if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
//            address_label.setError(getResources().getString(R.string.err_please_address));
//            return false;
//        }
//
//
//        else if (spin_ownership.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select Plot OwnerShip");
//            return false;
//        }
//        else if (spin_plotstatus.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select Plot Status");
//            return false;
//        }
//
//        else if (spin_state.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select State");
//            return false;
//        }
//
//        else if (spin_dist.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select District");
//            return false;
//        }
//
//        else if (spin_mandal.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select Mandal");
//            return false;
//        }
//
//        else if (spin_village.getSelectedItemPosition() == 0) {
//
//            showDialog(AddplotActivity.this, "Please Select Mandal");
//            return false;
//        }
//
//
//         if(ownership_id == 108){
//            if (TextUtils.isEmpty(ownerame_editext.getText().toString().trim())) {
//                ownerame_label.setError("Please Enter Owner Name");
//                return false;
//            } else if (TextUtils.isEmpty(ownerContactnumber_edittext.getText().toString().trim())) {
//                ownerContactnumber_label.setError("Please Enter Owner Contact Number");
//                return false;
//            }
//        }
//
//
//        return true;
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
                case R.id.Total_Area_edittxt:
                    validate_totalarea();
                    break;
                case R.id.Adopted_area_edittxt:
                    validate_adoptedarea();
                    break;
                case R.id.Palm_Area_edittxt:
                    validate_palmarea();
                    break;

                case R.id.gps_are_edittxt:
                    validate_gpsarea();
                    break;

                case R.id.surveynumber_edittxt:
                    validate_surveynumber();
                    break;

                case R.id.passbook_number_edittxt:
                    validate_passbooknumber();
                    break;

                case R.id.owner_contactNumber_edittxt:
                    validate_ownerContactnumber();
                    break;

                case R.id.owner_name_edittxt:
                    validate_ownername();
                    break;
            }}
        private boolean validate_surveynumber() {
            if (TextUtils.isEmpty(surverynumber_edittext.getText().toString())) {
                surverynumber_label.setError("Please Enter Adangal Number/Survey Number");
                requestFocus(surverynumber_edittext);
            } else {
                surverynumber_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_passbooknumber() {
            if (TextUtils.isEmpty(passbook_edittxt.getText().toString())) {
                passbook_label.setError("Please Enter Passbook Number");
                requestFocus(passbook_edittxt);
            } else {
                passbook_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_totalarea() {
            if (TextUtils.isEmpty(totalarea_edittxt.getText().toString())) {
                totalarea_label.setError("Please Enter Total Area");
                requestFocus(totalarea_edittxt);
            } else {
                totalarea_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_adoptedarea() {


            if (TextUtils.isEmpty(adoptedarea_edittxt.getText().toString())) {
                adoptedarea_label.setError("Please Enter Adopted Area");
                requestFocus(adoptedarea_edittxt);
            }

            else if( Double.parseDouble(adoptedarea_edittxt.getText().toString()) > Double.parseDouble(totalarea_edittxt.getText().toString())){
                adoptedarea_label.setError(getResources().getString(R.string.Adpoterror));
                requestFocus(adoptedarea_edittxt);
                return false;
            }
            else {
                adoptedarea_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_ownername() {
            if (TextUtils.isEmpty(ownerame_editext.getText().toString())) {
                ownerame_label.setError("Please Enter Owner Name");
                requestFocus(ownerame_editext);
            } else {
                ownerame_label.setErrorEnabled(false);

            }
            return true;
        }


    }


    private boolean validate_palmarea() {
        if (TextUtils.isEmpty(palmarea_edittxt.getText().toString())) {
            palmarea_label.setError("Please Enter Palm Area");

        } else {
            palmarea_label.setErrorEnabled(false);

        }
        return true;
    }
    private boolean validate_gpsarea() {
        if (TextUtils.isEmpty(gpsarea_edittxt.getText().toString())) {
            gpsarea_label.setError("Please Enter GPS Area");

        } else {
            gpsarea_label.setErrorEnabled(false);

        }
        return true;
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
    private boolean validate_state() {
        if(TextUtils.isEmpty(state_edittxt.getText().toString().trim())){
            state_label.setError(getResources().getString(R.string.err_please_state));
            requestFocus(state_edittxt);

        }
        else {
            state_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_dist() {
        if(TextUtils.isEmpty(district_edittxt.getText().toString().trim())){
            dist_label.setError(getResources().getString(R.string.err_please_district));
            requestFocus(district_edittxt);

        }
        else {
            dist_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_pin() {
        if(TextUtils.isEmpty(pin_edittxt.getText().toString().trim())){
            pin_label.setError(getResources().getString(R.string.err_please_pin));
            requestFocus(pin_edittxt);

        }
        else {
            pin_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_mandal() {
        if(TextUtils.isEmpty(mandal_edittxt.getText().toString().trim())){
            mandal_label.setError(getResources().getString(R.string.err_please_mandal));
            requestFocus(mandal_edittxt);

        }
        else {
            mandal_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_ownerContactnumber() {
        if (TextUtils.isEmpty(ownerContactnumber_edittext.getText().toString())) {
            ownerContactnumber_label.setError("Please Enter Owner Contact Number");
            requestFocus(ownerContactnumber_edittext);
        } else {
            ownerContactnumber_label.setErrorEnabled(false);
        } return true;}

    private boolean validate_village() {
        if(TextUtils.isEmpty(village_edittxt.getText().toString().trim())){
            village_label.setError(getResources().getString(R.string.err_please_village));
            requestFocus(village_edittxt);
        }
        else {
            village_label.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_address() {
        if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address_edittxt);

        }
        else {
            address_label.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //endregion
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SyncHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
