package com.cis.easyfarm.ui.userAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CPlots extends AppCompatActivity {

    TextView firstName;
    private List<Farmer> users;
    String CPcode;
    private List<Plot> plotList = new ArrayList<>();
    private List<Plot> mFinalFarmersList = new ArrayList<>();
    private ArrayList<Farmer> mList_sorted;
    // List<Farmer> farmerDetails;
    DataAccessHandler dataAccessHandler;
    Context context;
    private ImageView mIVClear;
    private RecyclerView recyclerView;
    CplotAdapter CplotAdapter;
    Toolbar toolbar;
    EditText et_search;
    View view;
    private TextView tvNorecords;
//    private TextWatcher mTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            doSearch(s.toString());
//            if (s.toString().length() > 0)
//                mIVClear.setVisibility(View.VISIBLE);
//            else
//                mIVClear.setVisibility(View.GONE);
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//        }
//    };
//
//    public void doSearch(String searchQuery) {
//        int etSearchLength = searchQuery.length();
//        if (!TextUtils.isEmpty(searchQuery)) {
//            mList_sorted = new ArrayList<>();
//            mFinalFarmersList = new ArrayList<>();
//            String farmerName = "";
//            for (int i = 0; i < mFarmersList.size(); i++) {
//                String lastName = "", fatherName = "";
//                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname())) {
//                    lastName = mFarmersList.get(i).getLastname();
//                    Log.d("Farmerlastname===", lastName + "");
//                }
//                if (!TextUtils.isEmpty(mFarmersList.get(i).getFatherName_GuardianName())) {
//                    fatherName = mFarmersList.get(i).getFatherName_GuardianName();
//                }
//
//                farmerName = mFarmersList.get(i).getFirstname();
//
//                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname()) && searchQuery.contains(" ")) {
//                    farmerName = farmerName + mFarmersList.get(i).getLastname();
//                }
//                if (farmerName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
//                        || mFarmersList.get(i).getPrimaryPhoneNumber().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
//                        || lastName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
//                        || mFarmersList.get(i).getCode().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
//                        || (!TextUtils.isEmpty(fatherName) && fatherName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault())))) {
//                    mList_sorted.add(mFarmersList.get(i));
//                }
//            }
//
//            mFinalFarmersList.addAll(mList_sorted);
//
//            if (convertedplotadapter == null) {
//                convertedplotadapter = new  convertedplotadapter(context, mList_sorted);
//            } else {
//                convertedplotadapter.updateAdapter(mList_sorted);
//            }
//            if (mList_sorted != null && mList_sorted.size() == 0) {
//                tvNorecords.setVisibility(View.VISIBLE);
//            } else {
//                tvNorecords.setVisibility(View.GONE);
////
//            }
//        }
//
//
//
//        if (TextUtils.isEmpty(searchQuery)) {
//            tvNorecords.setVisibility(View.GONE);
//            convertedplotadapter = new  convertedplotadapter(context,mFarmersList);
//            recyclerView.setAdapter(convertedplotadapter);
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_plots);
        context = getApplicationContext();

        intviews();
        setviews();


}

    private void setviews() {
        Intent i = getIntent();
        CPcode = i.getStringExtra("CPcode");
        Log.d("CPPPcode", CPcode);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        //  mFarmersList = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerDetails(), 1);
        plotList = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getCPlots(CPcode), 1);

        android.util.Log.d("PlottttSize", plotList.size() + "");


        CplotAdapter = new CplotAdapter(context,plotList);
        recyclerView.setAdapter(CplotAdapter);
    }

    private void intviews() {
        //tvNorecords = (TextView) view.findViewById(R.id.no_records);
        recyclerView = findViewById(R.id.recyclerView);
    }
}