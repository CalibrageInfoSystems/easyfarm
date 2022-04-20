package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.Fragments.Bank_details_Fragment;
import com.cis.easyfarm.Fragments.Convert_plotdetails_fragment;
import com.cis.easyfarm.Fragments.convert_idproofs_fragment;
import com.cis.easyfarm.Fragments.convert_personaldetaila_fragment;
import com.cis.easyfarm.Fragments.convert_soil_fragment;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

public class Add_converted_Farmer extends AppCompatActivity implements convert_personaldetaila_fragment.OnStepOneListener, convert_idproofs_fragment.OnStepTwoListener, Bank_details_Fragment.OnStepThreeListener ,
        Convert_plotdetails_fragment.OnFragmentInteractionListener,convert_soil_fragment.OnFragmentInteractionListener{

    public static String LOG_TAG = Add_converted_Farmer.class.getSimpleName();



    public static String code;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    Toolbar toolbar;
    String firname;
    ImageView backImg, home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_converted__farmer);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = findViewById(R.id.stepperIndicator);

        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //
        settoolbar();
       // firname = Adduser_fragment.First_name;

        Intent i = getIntent();
        code = i.getStringExtra("code");

        Log.d("Code", code + "");

//        Bundle bundle = new Bundle();
//        bundle.putString("Codee", code);
//        Adduser_fragment fragobj = new Adduser_fragment();
//        fragobj.setArguments(bundle);


        /*// or manual change
        indicator.setStepCount(3);
        indicator.setCurrentStep(2);
*/

    }

    public Bundle getMyData() {
        Bundle hm = new Bundle();
        hm.putString("val1",code);
        return hm;
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


    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_converted__farmer;
    }




    @Override
    public void onBackPressed(Fragment fragment) {
        if (fragment instanceof convert_idproofs_fragment) {
            mViewPager.setCurrentItem(0, true);
        } else if (fragment instanceof Bank_details_Fragment) {
            mViewPager.setCurrentItem(1, true);
        }
        else if (fragment instanceof Convert_plotdetails_fragment) {
            mViewPager.setCurrentItem(2, true);
        } else if (fragment instanceof convert_soil_fragment) {
            mViewPager.setCurrentItem(3, true);
        }

    }


    @Override
    public void onNextPressed(Fragment fragment) {
        if (fragment instanceof convert_personaldetaila_fragment) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof convert_idproofs_fragment) {
            mViewPager.setCurrentItem(2, true);
        } else if (fragment instanceof Bank_details_Fragment) {
            mViewPager.setCurrentItem(3, true);
        }
        else if (fragment instanceof Convert_plotdetails_fragment) {
            mViewPager.setCurrentItem(4, true);
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
                    return convert_personaldetaila_fragment.newInstance("", "");

                case 1:
                    return convert_idproofs_fragment.newInstance("", "");
                case 2:
                    return Bank_details_Fragment.newInstance("", "");
                case 3:
                    return Convert_plotdetails_fragment.newInstance("", "");
//                case 4:
//                    return convert_soil_fragment.newInstance("", "");
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
                case 4:
                    return "Fifth Level";
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
