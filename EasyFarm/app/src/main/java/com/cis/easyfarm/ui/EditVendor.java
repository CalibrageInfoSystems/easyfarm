package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;


public class EditVendor extends AppCompatActivity implements vendorPersonalDetails.OnStepOneListener, vendorProfDetails.OnStepTwoListener, vendorBankDetails.OnStepThreeListener, vendorIdProofs.OnStepFourListener {

    public static String LOG_TAG = EditVendor.class.getSimpleName();
    private EditVendor.SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    Toolbar toolbar;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_vendor);
        mSectionsPagerAdapter = new EditVendor.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = findViewById(R.id.stepperIndicator);

        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //

        settoolbar();

        Intent i = getIntent();
        code = i.getStringExtra("buyercode");
        Log.d("Buyercode", code);
    }

    public Bundle getMyData() {
        Bundle hm = new Bundle();
        hm.putString("bcode",code);
        return hm;
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


    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_vendor;
    }




    @Override
    public void onBackPressed(Fragment fragment) {

        if (fragment instanceof vendorProfDetails) {
            mViewPager.setCurrentItem(0, true);
        }else if (fragment instanceof vendorIdProofs) {
            mViewPager.setCurrentItem(1, true);
        }
        else if (fragment instanceof vendorBankDetails) {
            mViewPager.setCurrentItem(2, true);
        }

    }


    @Override
    public void onNextPressed(Fragment fragment) {

        if (fragment instanceof vendorPersonalDetails) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof vendorProfDetails) {
            mViewPager.setCurrentItem(2, true);
        }
        else if (fragment instanceof vendorIdProofs) {
            mViewPager.setCurrentItem(3, true);
        }
        else if (fragment instanceof vendorBankDetails) {
            mViewPager.setCurrentItem(4, true);
        }
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return vendorPersonalDetails.newInstance("", "");

                case 1:
                    return vendorProfDetails.newInstance("", "");

                case 2:
                    return vendorIdProofs.newInstance("", "");
                case 3:
                    return vendorBankDetails.newInstance("", "");
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Level";
                case 1:
                    return "Second Level";
                case 2:
                    return "Third Level";
                case 3:
                    return "Fourth Level";
            }
            return null;
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
