package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.Fragments.Adduser_fifthpage;
import com.cis.easyfarm.Fragments.Adduser_fourthpage;
import com.cis.easyfarm.Fragments.Adduser_fragment;
import com.cis.easyfarm.Fragments.Adduser_secondpage;
import com.cis.easyfarm.Fragments.Adduser_thirdpage;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

public class Add_new_Farmer extends AppCompatActivity implements addnewfarmer_basicdetails.OnStepOneListener, addnewfarmer_plotdetails.OnStepTwoListener {

    public static String LOG_TAG = Add_Farmer.class.getSimpleName();
    private Add_new_Farmer.SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    Toolbar toolbar;

    public static String user_nam, first_name,middlename,lastname, mobile_number,Email, fatherguardianname, adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add__farmer);

        mSectionsPagerAdapter = new Add_new_Farmer.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = findViewById(R.id.stepperIndicator);

        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //
        settoolbar();





        /*// or manual change
        indicator.setStepCount(3);
        indicator.setCurrentStep(2);
*/

    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Select Godown");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
            }
        });
    }


    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_new__farmer;
    }




    @Override
    public void onBackPressed(Fragment fragment) {

        if (fragment instanceof addnewfarmer_plotdetails) {
            mViewPager.setCurrentItem(0, true);
        }

    }


    @Override
    public void onNextPressed(Fragment fragment) {

        if (fragment instanceof addnewfarmer_basicdetails) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof addnewfarmer_plotdetails) {
            mViewPager.setCurrentItem(2, true);
            Toast.makeText(this, "Thanks For Registering", Toast.LENGTH_SHORT).show();
        }
    }
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
                    return addnewfarmer_basicdetails.newInstance("", "");

                case 1:
                    return addnewfarmer_plotdetails.newInstance("", "");
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Level";
                case 1:
                    return "Second Level";
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


