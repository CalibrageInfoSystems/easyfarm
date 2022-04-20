package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.Fragments.Signup_fragment;
import com.cis.easyfarm.Fragments.Signup_secondpage;
import com.cis.easyfarm.Fragments.Signup_thirdpage;
import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActicity extends AppCompatActivity implements Signup_fragment.OnStepOneListener, Signup_secondpage.OnStepTwoListener, Signup_thirdpage.OnStepThreeListener {

    public  static  final  String LOG_TAG = SignUpActicity.class.getSimpleName();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    String username,password,email,mobile;
    //Declaration SqliteHelper
    EasyFarmDatabse sqliteHelper;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_acticity);


        sqliteHelper = new EasyFarmDatabse(this);
//        setToolbarBackVisibility(true);
//        setTitle("Become Partner");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

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
        return R.layout.activity_sign_up_acticity;
    }

    @Override
    public void onNextPressed(Fragment fragment, User data) {
        Log.d(LOG_TAG,"===***"+fragment.getTag());
        username = Signup_fragment.username;
        Log.e("@@@@@@@@@@@@@@@@",username);
        username = data.getUserName();
        password = data.getpassword();
        email = data.getemail();
        mobile = data.getphone();

        if (fragment instanceof Signup_fragment) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof Signup_secondpage) {


            mViewPager.setCurrentItem(2, true);
        } else if (fragment instanceof Signup_thirdpage) {


            Toast.makeText(this, "Thanks For Registering", Toast.LENGTH_SHORT).show();
            CommonUtils.appendLog(LOG_TAG,"onNextPressed", "Thanks For Registering" );
           // finish();
        }
//       else if (!sqliteHelper.isEmailExists(email)) {
//            Log.e(LOG_TAG, "====****" +username+"==="+password+"---"+email+"----"+mobile);
//            //Email does not exist now add new user to database
//            sqliteHelper.addUser(new User(null, username, email, password, mobile));
//            Toast.makeText(this, "Thanks For Registering", Toast.LENGTH_SHORT).show();
//            //  Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            }, Snackbar.LENGTH_LONG);
//        } else {
//
//            //Email exists with email input provided so show error user already exist
//            Toast.makeText(this, "already exist", Toast.LENGTH_SHORT).show();
//        }

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_become_partner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
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
                    return Signup_fragment.newInstance("", "");

                case 1:
                    return Signup_secondpage.newInstance("", "");
                case 2:
                    return Signup_thirdpage.newInstance("", "");
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Level";
                case 1:
                    return "Second Level";
                case 2:
                    return "Finish";
            }
            return null;
        }
    }


    @SuppressLint("ResourceType")

    @Override
    public void onBackPressed(Fragment fragment) {
        if (fragment instanceof Signup_secondpage) {
            mViewPager.setCurrentItem(0, true);
        } else if (fragment instanceof Signup_thirdpage) {
            mViewPager.setCurrentItem(1, true);
        }
    }


}
