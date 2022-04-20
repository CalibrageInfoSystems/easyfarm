package com.cis.easyfarm.Fragments;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cis.easyfarm.R;
import com.cis.easyfarm.common.BaseFragment;
import com.cis.easyfarm.Interface.UpdateUiListener;
import com.cis.easyfarm.common.LocationService;
import com.cis.easyfarm.service.LocationServices;
import com.cis.easyfarm.service.MyLocationServices;
import com.cis.easyfarm.sync.SyncActivity;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.Buyer_users;
import com.cis.easyfarm.ui.PreViewAreaCalScreen;
import com.cis.easyfarm.ui.farmer_users;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.BuyerDetails;
import com.cis.easyfarm.ui.userAccount.BuyersAdapter;
import com.cis.easyfarm.ui.userAccount.ConvertedBuyers;
import com.cis.easyfarm.ui.userAccount.ConvertedFarmersActivity;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;
import com.cis.easyfarm.ui.userAccount.Vendor_users;

import static android.content.Context.JOB_SCHEDULER_SERVICE;


public class HomeFragment extends BaseFragment implements View.OnClickListener , UpdateUiListener {
    public static String TAG = HomeFragment.class.getSimpleName();
    View v;

    private JobScheduler jobScheduler;
    private ComponentName componentName;
    private JobInfo jobInfo;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    Button add_farmer,syncBtn;
    LinearLayout linear,linear2,linear3,linear4;
    CardView pro_farmers,vendors,buyers,converted_farmers;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        intviews();
        setviews();


        return v;
    }

    @Override
    public void Initialize() {

    }

    private void intviews() {
      pro_farmers = (CardView) v.findViewById(R.id.pro_farmers);
        linear = (LinearLayout) v.findViewById(R.id.linear);
        converted_farmers = (CardView) v.findViewById(R.id.converted_farmers);
        buyers = (CardView) v.findViewById(R.id.buyer_button);
        vendors = (CardView) v.findViewById(R.id.vendor_button);


    }
    private void setviews() {
       pro_farmers.setOnClickListener(this);
        converted_farmers.setOnClickListener(this);
        buyers.setOnClickListener(this);
        vendors.setOnClickListener(this);

//        syncBtn.setOnClickListener(this);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
//
            case R.id.pro_farmers:
//                Intent intent = new Intent(getActivity(), FarmerDetails.class);
//                startActivity(intent);

                Intent intent = new Intent(getActivity(), farmer_users.class);
                startActivity(intent);

//
//               //finish();
               break;

            case R.id.converted_farmers:
                Intent intent2 = new Intent(getActivity(), ConvertedFarmersActivity.class);
                startActivity(intent2);

//
//               //finish();
                break;

            case R.id.buyer_button:
//                Intent intent3 = new Intent(getActivity(), BuyerDetails.class);
//                startActivity(intent3);
                Intent intent3 = new Intent(getActivity(), Buyer_users.class);
                startActivity(intent3);
//
//               //finish();
                break;

            case R.id.vendor_button:


         //  StartBackgroundTask();

//todo task

                Intent intent4 = new Intent(getActivity(), Vendor_users.class);
                startActivity(intent4);



//
//               //finish();
                break;


            case R.id.linear:
                Intent intent1 = new Intent(getActivity(), FarmerDetails.class);
                startActivity(intent1);

//
//               //finish();
                break;


        }
    }

    @SuppressLint("NewApi")
    public void StartBackgroundTask() {
        jobScheduler = (JobScheduler) getActivity().getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        componentName = new ComponentName(getActivity().getApplicationContext(), MyLocationServices.class);
        jobInfo = new JobInfo.Builder(1, componentName)
                .setMinimumLatency(10000) //10 sec interval
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setRequiresCharging(false).build();
        jobScheduler.schedule(jobInfo);
    }
//
//    public void replaceFragment(Fragment someFragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.content_frame, someFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }


    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,0,0, R.anim.exit_to_left
        );
        mFragmentTransaction.replace(android.R.id.content, fragment);
        mFragmentTransaction.addToBackStack(backStateName);
        mFragmentTransaction.commit();
    }

    @Override
    public void updateUserInterface(int refreshPosition) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
