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
import com.cis.easyfarm.R;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConvertedVendors extends AppCompatActivity {

    TextView firstName;
    private List<Farmer> users;

    private List<Farmer> mFarmersList = new ArrayList<>();
    private List<Farmer> mFinalFarmersList = new ArrayList<>();
    private ArrayList<Farmer> mList_sorted;
    // List<Farmer> farmerDetails;
    DataAccessHandler dataAccessHandler;
    Context context;
    private ImageView mIVClear;
    private RecyclerView recyclerView;
    ConvertedVendorsAdapter recyclerAdapter;
    Toolbar toolbar;
    EditText et_search;
    private TextView tvNorecords;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            doSearch(s.toString());
            if (s.toString().length() > 0)
                mIVClear.setVisibility(View.VISIBLE);
            else
                mIVClear.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converted_vendors);
        context = getApplicationContext();
        users = new ArrayList<>();

        settoolbar();
        intview();

        setViews();

    }

    private void setViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        mFarmersList = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getConvertedVendors(), 1);

        android.util.Log.d("FarmerDetails====", mFarmersList.size() + "");

        for (int i=0;i<mFarmersList.size();i++) {

            Log.d("FarmerUserNames===", mFarmersList.get(i).getFirstname() + "");

        }

        recyclerAdapter = new ConvertedVendorsAdapter(context,mFarmersList);
        recyclerView.setAdapter(recyclerAdapter);

        et_search.addTextChangedListener(mTextWatcher);
        mIVClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
    }

    private void intview() {
        tvNorecords = (TextView) findViewById(R.id.no_records);
        recyclerView = findViewById(R.id.recyclerView);
        mIVClear = (ImageView) findViewById(R.id.iv_clear);
        et_search = findViewById( R.id.et_search);
    }

    public void doSearch(String searchQuery) {
        int etSearchLength = searchQuery.length();
        if (!TextUtils.isEmpty(searchQuery)) {
            mList_sorted = new ArrayList<>();
            mFinalFarmersList = new ArrayList<>();
            String farmerName = "";
            for (int i = 0; i < mFarmersList.size(); i++) {
                String lastName = "", fatherName = "";
                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname())) {
                    lastName = mFarmersList.get(i).getLastname();
                    Log.d("Farmerlastname===", lastName + "");
                }
                if (!TextUtils.isEmpty(mFarmersList.get(i).getFatherName_GuardianName())) {
                    fatherName = mFarmersList.get(i).getFatherName_GuardianName();
                }

                farmerName = mFarmersList.get(i).getFirstname();

                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname()) && searchQuery.contains(" ")) {
                    farmerName = farmerName + mFarmersList.get(i).getLastname();
                }
                if (farmerName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || mFarmersList.get(i).getPrimaryPhoneNumber().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || lastName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || mFarmersList.get(i).getCode().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || (!TextUtils.isEmpty(fatherName) && fatherName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault())))) {
                    mList_sorted.add(mFarmersList.get(i));
                }
            }

            mFinalFarmersList.addAll(mList_sorted);

            if (recyclerAdapter == null) {
                recyclerAdapter = new ConvertedVendorsAdapter(this, mList_sorted);
            } else {
                recyclerAdapter.updateAdapter(mList_sorted);
            }
            if (mList_sorted != null && mList_sorted.size() == 0) {
                tvNorecords.setVisibility(View.VISIBLE);
            } else {
                tvNorecords.setVisibility(View.GONE);
                toolbar.setTitle(getResources().getString(R.string.farmer_list) + " (" + mList_sorted.size() + ")");
                toolbar.invalidate();
            }
        }



        if (TextUtils.isEmpty(searchQuery)) {
            tvNorecords.setVisibility(View.GONE);
            recyclerAdapter = new ConvertedVendorsAdapter(context,mFarmersList);
            recyclerView.setAdapter(recyclerAdapter);
        }
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

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SyncHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
