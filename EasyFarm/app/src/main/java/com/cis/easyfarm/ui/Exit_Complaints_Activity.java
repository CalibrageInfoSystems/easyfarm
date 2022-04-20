package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.CommonActivity;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.localData.SharedPrefsData;
import com.cis.easyfarm.model.Complaints;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.ui.Adapters.GetcomplaintsAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Exit_Complaints_Activity extends CommonActivity {
    public static final String TAG = Exit_Complaints_Activity.class.getSimpleName();
    int User_id;
    private Context ctx;
    private LinearLayoutManager lytManager;
    private RecyclerView rcv_complaints;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    LoginResponse loginressponse;
    // MyLabour_ReqAdapter adapter;
    String Farmer_code;
    TextView no_data;
    Toolbar toolbar;
    DataAccessHandler dataAccessHandler;
    private List<Complaints> complaintList = new ArrayList<>();
    String plot_id,totalarea,status,ownership;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit__complaints);
        settoolbar();
        intviews();
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

    private void intviews() {
        dataAccessHandler = new DataAccessHandler(this);
        rcv_complaints =findViewById(R.id.rcv_complaints);
        ctx = this;
        lytManager = new LinearLayoutManager(this);

        no_data=findViewById(R.id.no_data);
        rcv_complaints.setLayoutManager(lytManager);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }

    private void setviews() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            plot_id = extras.getString("code");
        }
        complaintList = (List<Complaints>) dataAccessHandler.getComplaintsDetails(Queries.getInstance().getComplaintsbyplotCcode(plot_id), 1);
        loginressponse = SharedPrefsData.getCatagories(Exit_Complaints_Activity.this);

      Log.d("FarmerDetails====", complaintList.size() + "");

        for (int i=0;i<complaintList.size();i++) {

            Log.d("complaintList===", complaintList.get(i).getCode() + "");

        }

        if ( complaintList.size()!=0) {
                            no_data.setVisibility(View.GONE);
                            rcv_complaints.setVisibility(View.VISIBLE);

                            GetcomplaintsAdapter adapter = new GetcomplaintsAdapter(complaintList, ctx);
                            rcv_complaints.setAdapter(adapter);
                        } else {
            no_data.setVisibility(View.VISIBLE);
            rcv_complaints.setVisibility(View.GONE);
        }

//                        }
       // getComplaints();
    }

//    private void getComplaints() {
//
//
//        mdilogue.show();
//
//        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
//        mSubscription = service.GetExitingcomplaints(APIConstantURL.GetComplaintsByPlotCode+plot_id)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetComplaintsByPlotCode>() {
//                    @Override
//                    public void onCompleted() {
//                        mdilogue.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof HttpException) {
//                            ((HttpException) e).code();
//                            ((HttpException) e).message();
//                            ((HttpException) e).response().errorBody();
//                            try {
//                                ((HttpException) e).response().errorBody().string();
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                            e.printStackTrace();
//                        }
//                        mdilogue.dismiss();
//                        //showDialog(Exit_Complaints_Activity.this, getString(R.string.server_error));
//                    }
//
//                    @Override
//                    public void onNext(GetComplaintsByPlotCode getComplaintsresponse) {
//
//
//                        if ( getComplaintsresponse.getListResult()!= null) {
//                            no_data.setVisibility(View.GONE);
//                            rcv_complaints.setVisibility(View.VISIBLE);
//
//                            GetcomplaintsAdapter adapter = new GetcomplaintsAdapter(getComplaintsresponse.getListResult(), ctx);
//                            rcv_complaints.setAdapter(adapter);
//                        } else {
//                            no_data.setVisibility(View.VISIBLE);
//                            rcv_complaints.setVisibility(View.GONE);
//
//
//                        }
//                    }
//
//
////                            GetLoanAdapter adapter = new GetLoanAdapter(resLoan.getListResult(), ctx);
////                            rcv_requests.setAdapter(adapter);
//
//
//                });
//    }


}


