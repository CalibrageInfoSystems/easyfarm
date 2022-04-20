package com.cis.easyfarm.ui;

import androidx.appcompat.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.Fragments.Adduser_fifthpage;
import com.cis.easyfarm.Fragments.Adduser_fourthpage;
import com.cis.easyfarm.Fragments.Adduser_fragment;
import com.cis.easyfarm.Fragments.Adduser_secondpage;
import com.cis.easyfarm.Fragments.Adduser_thirdpage;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.NonSwipeableViewPager;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

public class Add_Farmer extends AppCompatActivity implements Adduser_fragment.OnStepOneListener, Adduser_secondpage.OnStepTwoListener, Adduser_thirdpage.OnStepThreeListener ,Adduser_fourthpage.OnFragmentInteractionListener,Adduser_fifthpage.OnFragmentInteractionListener{

public static String LOG_TAG = Add_Farmer.class.getSimpleName();



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
        setContentView(R.layout.activity_add__farmer);

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
        firname = Adduser_fragment.First_name;

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
        return R.layout.activity_add__farmer;
    }




@Override
public void onBackPressed(Fragment fragment) {
    if (fragment instanceof Adduser_secondpage) {
        mViewPager.setCurrentItem(0, true);
    } else if (fragment instanceof Adduser_thirdpage) {
        mViewPager.setCurrentItem(1, true);
    }
    else if (fragment instanceof Adduser_fourthpage) {
        mViewPager.setCurrentItem(2, true);
    } else if (fragment instanceof Adduser_fifthpage) {
        mViewPager.setCurrentItem(3, true);
    }

        }


@Override
public void onNextPressed(Fragment fragment) {
    if (fragment instanceof Adduser_fragment) {
        mViewPager.setCurrentItem(1, true);

    } else if (fragment instanceof Adduser_secondpage) {
        mViewPager.setCurrentItem(2, true);
    } else if (fragment instanceof Adduser_thirdpage) {
        mViewPager.setCurrentItem(3, true);
    }
    else if (fragment instanceof Adduser_fourthpage) {
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
                return Adduser_fragment.newInstance("", "");

            case 1:
                return Adduser_secondpage.newInstance("", "");
            case 2:
                return Adduser_thirdpage.newInstance("", "");
            case 3:
                return Adduser_fourthpage.newInstance("", "");
            case 4:
                return Adduser_fifthpage.newInstance("", "");
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 5;
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
