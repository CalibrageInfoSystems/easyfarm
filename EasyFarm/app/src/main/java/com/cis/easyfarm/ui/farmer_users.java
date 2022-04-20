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
import com.cis.easyfarm.ui.userAccount.ConvertedFarmersActivity;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;

public class farmer_users extends AppCompatActivity implements View.OnClickListener {
Toolbar toolbar;
    LinearLayout linear,linear2,linear3,linear4;
    CardView pro_farmers,vendors,buyers,converted_farmers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_users);

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
        pro_farmers = (CardView) findViewById(R.id.pro_farmers);
        linear = (LinearLayout) findViewById(R.id.linear);
        converted_farmers = (CardView) findViewById(R.id.converted_farmers);

    }

    private void setviews() {
        pro_farmers.setOnClickListener(this);
        converted_farmers.setOnClickListener(this);
        linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//
            case R.id.pro_farmers:
                Intent intent = new Intent(this, FarmerDetails.class);
                startActivity(intent);



//
//               //finish();
                break;

            case R.id.converted_farmers:
                Intent intent2 = new Intent(this, ConvertedFarmersActivity.class);
                startActivity(intent2);

//
//               //finish();
                break;




            case R.id.linear:
                Intent intent1 = new Intent(this, FarmerDetails.class);
                startActivity(intent1);

//
//               //finish();
                break;


        }
    }
}
