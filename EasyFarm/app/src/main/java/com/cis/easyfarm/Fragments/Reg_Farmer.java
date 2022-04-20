package com.cis.easyfarm.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.badoualy.stepperindicator.StepperIndicator;
import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.BaseFragment;
import com.cis.easyfarm.common.NonSwipeableViewPager;


public class Reg_Farmer extends Fragment implements Adduser_fragment.OnStepOneListener, Adduser_secondpage.OnStepTwoListener, Adduser_thirdpage.OnStepThreeListener {

    public static String LOG_TAG = Reg_Farmer.class.getSimpleName();
  //  private OnFragmentInteractionListener mListener;
    View v;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public NonSwipeableViewPager mViewPager;
    private StepperIndicator stepperIndicator;
    public Reg_Farmer() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reg__farmer, container, false);

        intviews();
        setviews();


        return v;
    }

    private void intviews() {
    }
    private void setviews() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager =v .findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        stepperIndicator = v.findViewById(R.id.stepperIndicator);


        stepperIndicator.showLabels(false);
        stepperIndicator.setViewPager(mViewPager);
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter().getCount() - 1); //
    }


    // TODO: Rename method, update argument and hook method into UI event



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_reg__farmer;
    }

    @Override
    public void onBackPressed(Fragment fragment) {

    }

    @Override
    public void onNextPressed(Fragment fragment) {
        if (fragment instanceof Adduser_fragment) {
            mViewPager.setCurrentItem(1, true);

        } else if (fragment instanceof Adduser_secondpage) {


            mViewPager.setCurrentItem(2, true);
        } else if (fragment instanceof Adduser_thirdpage) {


            Toast.makeText(getContext(), "Thanks For Registering", Toast.LENGTH_SHORT).show();
            // finish();
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

}
