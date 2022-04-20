package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cis.easyfarm.R;
import com.cis.easyfarm.ui.userAccount.BuyerDetails;
import com.cis.easyfarm.ui.userAccount.ConvertedBuyers;
import com.cis.easyfarm.ui.userAccount.ConvertedFarmersActivity;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;

public class Buyer_users extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    LinearLayout linear,linear2,linear3,linear4;
    CardView pro_buyers,vendors,buyers,converted_farmers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_users);
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
        pro_buyers = (CardView) findViewById(R.id.pro_buyers);
        linear = (LinearLayout) findViewById(R.id.linear);
        converted_farmers = (CardView) findViewById(R.id.converted_farmers);

    }

    private void setviews() {
        pro_buyers.setOnClickListener(this);
        converted_farmers.setOnClickListener(this);
        linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//
            case R.id.pro_buyers:
                Intent intent = new Intent(this, BuyerDetails.class);
                startActivity(intent);



//
//               //finish();
                break;

            case R.id.converted_farmers:
                Intent intent2 = new Intent(this, ConvertedBuyers.class);
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
