package com.cis.easyfarm.ui.userAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cis.easyfarm.R;
import com.cis.easyfarm.ui.VendorDetails;

public class Vendor_users extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout linear;
    CardView pro_vendors,conv_vendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_users);

        intviews();
        setviews();
        settoolbar();
    }

    private void settoolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

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
        pro_vendors = (CardView) findViewById(R.id.pro_vendors);
        linear = (LinearLayout) findViewById(R.id.linear);
        conv_vendors = (CardView) findViewById(R.id.converted_vendors);

    }

    private void setviews() {
        pro_vendors.setOnClickListener(this);
        conv_vendors.setOnClickListener(this);
        linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//
            case R.id.pro_vendors:
                Intent intent = new Intent(this, VendorDetails.class);
                startActivity(intent);



//
//               //finish();
                break;

            case R.id.converted_vendors:
                Intent intent2 = new Intent(this, ConvertedVendors.class);
                startActivity(intent2);

//
//               //finish();
                break;




            case R.id.linear:
                Intent intent1 = new Intent(this, BuyerDetails.class);
                startActivity(intent1);

//
//               //finish();
                break;


        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
