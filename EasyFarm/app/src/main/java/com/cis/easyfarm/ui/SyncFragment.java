package com.cis.easyfarm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.service.autofill.Transformation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cis.easyfarm.Fragments.HomeFragment;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.ProgressBar;
import com.cis.easyfarm.common.ProgressDialogFragment;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DatabaseKeys;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.FarmerDetails;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SyncFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SyncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyncFragment extends Fragment implements View.OnClickListener {
    public static String TAG = SyncFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
View v;
Context mContext;
    private ArrayList<String> allRefreshDataMap;
    private DataAccessHandler dataAccessHandler;
CardView syncbtn, Reversesynbtn, deleteButton;
CircleImageView syncImgBtn,reversesync;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SyncFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SyncFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SyncFragment newInstance(String param1, String param2) {
        SyncFragment fragment = new SyncFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sync, container, false);

        intviews();
        setviews();


        return v;
    }

    private void intviews() {

        allRefreshDataMap = new ArrayList<>();

        allRefreshDataMap.add(DatabaseKeys.TABLE_FORMER);
        allRefreshDataMap.add(DatabaseKeys.TABLE_PLOT);
        allRefreshDataMap.add(DatabaseKeys.TABLE_FILEREPOSITORY);
        allRefreshDataMap.add(DatabaseKeys.TABLE_BANKDetailsHistory);
        allRefreshDataMap.add(DatabaseKeys.TABLE_FARMERBANK);
        allRefreshDataMap.add(DatabaseKeys.TABLE_IDENTITYPROOFS);


        dataAccessHandler = new DataAccessHandler(getActivity());

        syncbtn = v.findViewById(R.id.syncbtn);
        Reversesynbtn =v.findViewById(R.id.ReverseSyncBtn);
        //deleteButton = v.findViewById(R.id.deleteButton);
    }
    private void setviews() {
        syncbtn.setOnClickListener(this);
      Reversesynbtn.setOnClickListener(this);
        //deleteButton.setOnClickListener(this);


        //   syncImgBtn.setImageDrawable(R.drawable.spinner_drawable);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.syncbtn:
                showTransactionsAlertDialog(true);
                break;
            case R.id.ReverseSyncBtn:

                showReverseSyncAlertDialog();
//
//               //finish();
                break;

//            case R.id.deleteButton:
//
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS ActivityLog");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Bank");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS BankDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS BankDetailsHistory");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS BuyerDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS ClassType");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Country");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS District");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS FileRepository");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS GeoBoundaries");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS IdentityProofs");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS IrrigationDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS LocationTracker");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS LookUp");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Mandal");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Notification");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Plot");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS PlotStatusHistory");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS PowerDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Role");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS SoilDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS SoilTestDetails");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS State");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS TypeCdDmt");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS UserInfo");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS UserRoleXref");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS Village");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS android_metadata");
//                dataAccessHandler.executeRawQuery("DROP TABLE if EXISTS test");
//
//
//                UiUtils.showCustomToastMessage("Tables Deleted Successfully", getActivity(), 0);
//
//                break;

        }
    }

    public void showReverseSyncAlertDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirm)
                .setMessage("Do you want to Perform Reverse Sync?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (CommonUtils.isNetworkAvailable(getActivity())) {

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
                            progressDialogFragment.show(fm, "progress dialog fragment");
                            DataSynchelper.performReverseSync(getActivity(),progressDialogFragment, new ApplicationThread.OnComplete() {
                                @Override
                                public void execute(boolean success, Object result, String msg) {
                                    if (success) {
                                        ApplicationThread.uiPost(TAG, "transactions sync message", new Runnable() {
                                            @Override
                                            public void run() {

                                            }
                                        });
                                    } else {
                                        ApplicationThread.uiPost(TAG, "transactions sync failed message", new Runnable() {
                                            @Override
                                            public void run() {
                                                ProgressBar.hideProgressBar();
                                                Toast.makeText(getActivity(), "Data sending failed", Toast.LENGTH_SHORT).show();
                                                //startTranscationSync();
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            UiUtils.showCustomToastMessage("Please check network connection", getActivity(), 1);
                        }

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public void showTransactionsAlertDialog(final boolean fromReset) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirm)
                .setMessage(R.string.you_want_to_perform_transactions_sync)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (fromReset) {
                        //  DataSynchelper.updateSyncDate(getActivity(), "");
                            for (String s : allRefreshDataMap) {
                               // dataAccessHandler.executeRawQuery("DELETE FROM " + s);
                                Log.v(TAG, "delete table" + s);
                            }
                        }
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
                        progressDialogFragment.show(fm, "progress dialog fragment");
                        DataSynchelper.startTransactionSync(getActivity(), progressDialogFragment);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
