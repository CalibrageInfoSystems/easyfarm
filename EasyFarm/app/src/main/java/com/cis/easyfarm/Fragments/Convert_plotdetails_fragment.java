package com.cis.easyfarm.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.Add_converted_Farmer;
import com.cis.easyfarm.ui.PreViewAreaCalScreen;

import com.cis.easyfarm.ui.userAccount.AddplotActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import rx.Subscription;

import static com.cis.easyfarm.Fragments.Adduser_fragment.DATE_FORMAT1;
import static com.cis.easyfarm.Fragments.Adduser_fragment.getKeysByValue;
import static com.cis.easyfarm.common.CommonConstants.gpsArea;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Convert_plotdetails_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Convert_plotdetails_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Convert_plotdetails_fragment extends Fragment implements View.OnClickListener,PlotDetailsAdapter.PlotClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String LOG_TAG = Convert_plotdetails_fragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static String user_nam, first_name,middlename,lastname, mobile_number,Email, fatherguardianname, adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;

    Button geo_boundaries;
    List<Farmer> farmerDetails;
    List<Plot> plotDetails;



    public static String farmerCode;

    View view;

    private Subscription mSubscription;
    private SpotsDialog mdilogue;

    private EditText totalarea_edittxt,adoptedarea_edittxt, gpsarea_edittxt,surverynumber_edittext,mandal_edittxt,state_edittxt,palmarea_edittxt,survey_edittxt,
            passbook_edittxt, ownerame_editext,ownerContactnumber_edittext, address_edittxt, address2_edittxt,village_edittxt,pin_edittxt,district_edittxt,adangal_edittext;

    Spinner spin_state,spin_dist,spin_mandal,spin_village,spin_ownership, spin_plotstatus,Countryspin;
    int state_id,dist_id,mandal_id,village_id,ownership_id,plotstatus_id;


    List<String> get_state = new ArrayList<String>();
    List<Integer> get_state_Id = new ArrayList<Integer>();

    List<String>get_district = new ArrayList<String>();
    List<Integer> get_district_Id = new ArrayList<Integer>();

    List<String>get_mandal = new ArrayList<String>();
    List<Integer> get_mandal_Id = new ArrayList<Integer>();

    List<String>get_villages = new ArrayList<String>();
    List<Integer> get_villages_Id = new ArrayList<Integer>();

    List<String>get_plotownershipdetails = new ArrayList<String>();
    List<Integer> get_plotownershipdetails_id = new ArrayList<Integer>();

    List<String>get_plotstatusdetails = new ArrayList<String>();
    List<Integer> get_plotstatusdetails_id = new ArrayList<Integer>();
    List<Plot> plotdetailsList;
 //   List<IdentityProof>plotdetailsList;
    private LinkedHashMap plotownership, plotstatus;
    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    private LinkedHashMap<String, Pair> CountryMap, stateDataMap = null;
    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap = null, villagesDataMap = null;
    TextInputLayout father_label,address_label,address2_label, village_label, mandal_label, pin_label, dist_label, state_label,ownerame_label,
            totalarea_label, adoptedarea_label, palmarea_label,gpsarea_label,Adangal_label,passbook_label,surverynumber_label,ownerContactnumber_label,adangal_label;
    public static Object ownershipids, plotStatusID;
    private int GALLERY = 1, CAMERA = 2;
    DataAccessHandler dataAccessHandler;
    private OnFragmentInteractionListener mListener;
    String currentDate;
    public static String stateids,districtids,mandalids,villgeids;
    RecyclerView plotdetailsRecycleview;

    PlotDetailsAdapter plotdetailsAdapter;
    public Convert_plotdetails_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BePartnerStepOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Convert_plotdetails_fragment newInstance(String param1, String param2) {
        Convert_plotdetails_fragment fragment = new Convert_plotdetails_fragment();
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

            String strtext = getArguments().getString("edttext");


            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_plot_details, container, false);

        Add_converted_Farmer activity = (Add_converted_Farmer) getActivity();

        Bundle results = activity.getMyData();

        farmerCode = results.getString("val1");

        Log.d("==FarmerCode==", farmerCode);

        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());




        //Log.d("TotalArea==", plotDetails.get(0).getTotalPlotArea() + "");




        intviews();
        setviewss();

//        totalarea_edittxt.setText(plotDetails.get(0).getTotalPlotArea() + "");
//        address_edittxt.setText(plotDetails.get(0).getAddress1() + "");
//        address2_edittxt.setText(plotDetails.get(0).getAddress2() + "");
//        adoptedarea_edittxt.setText(plotDetails.get(0).getAdaptedArea() + "");
//        gpsarea_edittxt.setText(plotDetails.get(0).getGPSPlotArea() + "");
//        surverynumber_edittext.setText(plotDetails.get(0).getSurveyNumber() + "");
//        ownerame_editext.setText(plotDetails.get(0).getOwnerName() + "");
//        ownerContactnumber_edittext.setText(plotDetails.get(0).getOwnerContactNumber() + "");
//        passbook_edittxt.setText(plotDetails.get(0).getOwnerContactNumber() + "");
//        adangal_edittext.setText( "");
//        passbook_edittxt.setText(plotDetails.get(0).getPassbookNumber() + "");
//        CommonConstants.plotownership =plotDetails.get(0).getPlotOwnerShipTypeId()+"";
//
//        Log.e("ownership", CommonConstants.plotownership);
        return view;

    }

    private void setviewss() {
        plotdeatils();
    }

    private void plotdeatils() {
        plotdetailsList = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getFarmerplotDetails(farmerCode), 1);
         //   identityProofsList = (List<IdentityProof>) dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getFarmerIDProofDetails(usercode), 1);
        plotdetailsRecycleview.setHasFixedSize(true);
        plotdetailsAdapter = new PlotDetailsAdapter(plotdetailsList,getActivity());
        plotdetailsAdapter.setPlotClickListener(Convert_plotdetails_fragment.this);
        plotdetailsRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        plotdetailsRecycleview.setAdapter(plotdetailsAdapter);

    }

    private void intviews() {

        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());
        plotdetailsRecycleview = (RecyclerView) view.findViewById(R.id.plotrecycler_view);

//        farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(farmerCode), 1);
//
//
//        totalarea_edittxt= view.findViewById(R.id.Total_Area_edittxt);
//        adoptedarea_edittxt= view.findViewById(R.id.Adopted_area_edittxt);
//        palmarea_edittxt= view.findViewById(R.id.Palm_Area_edittxt);
//        survey_edittxt = view.findViewById(R.id.surveynumber_edittxt);
//        gpsarea_edittxt= view.findViewById(R.id.gps_are_edittxt);
//        surverynumber_edittext = view.findViewById(R.id.surveynumber_edittxt);
//        passbook_edittxt = view.findViewById(R.id.passbook_number_edittxt);
//        ownerContactnumber_edittext = view.findViewById(R.id.owner_contactNumber_edittxt);
//        ownerame_editext = view.findViewById(R.id.owner_name_edittxt);
//        address_edittxt = view.findViewById(R.id.address_edittxt);
//        address2_edittxt = view.findViewById(R.id.address2_edittxt);
//        adangal_edittext = view.findViewById(R.id.adangal_edittxt);
//
//
//        totalarea_label = view.findViewById(R.id.Total_Area_label);
//        adoptedarea_label = view.findViewById(R.id.Adopted_Area_label);
//        gpsarea_label = view.findViewById(R.id.Gps_Area_label);
//        surverynumber_label = view.findViewById(R.id.surveynumber_label);
//        passbook_label = view.findViewById(R.id.passbook_number_label);
//        ownerContactnumber_label = view.findViewById(R.id.owner_contactNumber);
//        ownerame_label = view.findViewById(R.id.owner_name);
//        address_label= view.findViewById(R.id.address_label);
//        address2_label = view.findViewById(R.id.address_label2);
//        adangal_label = view.findViewById(R.id.adangal_label);
//
//
//        spin_state = view.findViewById(R.id.spin_state);
//        spin_dist = view.findViewById(R.id.spin_district);
//        spin_mandal = view.findViewById(R.id.spin_mandal);
//        spin_village = view.findViewById(R.id.spin_village);
//        spin_ownership = view.findViewById(R.id.spin_Ownership);
//        //spin_plotstatus = view.findViewById(R.id.spin_plotStatus);
//        Countryspin = view.findViewById(R.id.spin_Country);
//        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
//                .setContext(getContext())
//                .setTheme(R.style.Custom)
//                .build();
    }

//    private void setviews() {
//
//        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
//
//        Log.d("==UpdatedDate====", gpsArea);
//        survey_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(survey_edittxt));
//        totalarea_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(totalarea_edittxt));
//        adoptedarea_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(adoptedarea_edittxt));
//        gpsarea_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(gpsarea_edittxt));
//        address_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(address_edittxt));
//        address2_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(address2_edittxt));
////
//        passbook_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(passbook_edittxt));
//
//        //  Submit.setOnClickListener(this);
//
//
//        passbook_edittxt.addTextChangedListener(new Convert_plotdetails_fragment.ValidationTextWatcher(passbook_edittxt));
//
//
//        spin_ownership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Object ownershipid;
//
//                ownershipid = getKeysByValue(plotownership,spin_ownership.getSelectedItem().toString());
//                ownershipids = (ownershipid.toString().substring(1, ownershipid.toString().length()-1));
//                Log.d("==ownershipids==",ownershipids.toString());
//
//                if(spin_ownership.getSelectedItemPosition() == 1){
//                    ownerame_label.setVisibility(View.VISIBLE);
//                    ownerContactnumber_label.setVisibility(View.VISIBLE);
//
//                }
//                else {
//                    ownerame_label.setVisibility(View.GONE);
//                    ownerContactnumber_label.setVisibility(View.GONE);                }
//
//
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
////        spin_plotstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////
////                Object plotStatusId;
////
////                plotStatusId = getKeysByValue(plotownership,spin_ownership.getSelectedItem().toString());
////                plotStatusID = (plotStatusId.toString().substring(1, plotStatusId.toString().length()-1));
////                Log.d("==ownershipids==",plotStatusID.toString());
////
////            }
////
////
////            @Override
////            public void onNothingSelected(AdapterView<?> adapterView) {
////
////            }
////        });
//
//
//        plotownership = dataAccessHandler.getGenericData(Queries.getInstance().getownership());
//        String[] ownership = CommonUtils.fromMap(plotownership, "Plot Ownership");
//
//        Log.e("category===========", ownership[0] + "");
//        ArrayAdapter<String> ownerShipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ownership);
//        ownerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin_ownership.setAdapter(ownerShipAdapter);
//
//        //categortyids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getProfileIds(spin_category.getSelectedItem().toString()));
//
//
//        if (CommonConstants.plotownership != null) {
//            int spinnerPosition5 = ownerShipAdapter.getPosition(CommonConstants.plotownership);
//            Log.e("spinnerPosition5===",spinnerPosition5+1+"");
//            spin_ownership.setSelection(spinnerPosition5+2);
//        }
//
////        plotstatus = dataAccessHandler.getGenericData(Queries.getInstance().getplotStatus());
////        String[] plotStatus = CommonUtils.fromMap(plotstatus, "Plot Status");
////
////        Log.e("plotStatus===========", plotStatus[0] + "");
////        ArrayAdapter<String> plotStatusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, plotStatus);
////        plotStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spin_plotstatus.setAdapter(plotStatusAdapter);
//
//
//
//
//
//
//
//        CountryMap = dataAccessHandler.getPairData(Queries.getInstance().getCountryMasterQuery());
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(CountryMap, "Country"));
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Countryspin.setAdapter(spinnerArrayAdapter);
//        if (CommonConstants.CountryName != null) {
//            int spinnerPosition1 = spinnerArrayAdapter.getPosition(CommonConstants.CountryName);
//            Countryspin.setSelection(spinnerPosition1);
//        }
//
//        Countryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (CountryMap != null && CountryMap.size() > 0 && Countryspin.getSelectedItemPosition() != 0) {
//                    stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getStatesMasterQuery(CountryMap.keySet().toArray(new String[CountryMap.size()])[i - 1]));
//                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
//                    spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spin_state.setAdapter(spinnerArrayAdapter1);
//
//
//                    if (CommonConstants.stateName != null) {
//                        int spinnerPosition = spinnerArrayAdapter1.getPosition(CommonConstants.stateName);
//                        spin_state.setSelection(spinnerPosition);
//                    }
//                    //statespin.setSelection(1);
//
//                    // statespin.setPrompt(CommonConstants.stateName);
//                }
////
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (stateDataMap != null && stateDataMap.size() > 0 && spin_state.getSelectedItemPosition() != 0) {
//
//                    districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getDistrictQuery(stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1]));
//                    ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
//                    spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spin_dist.setAdapter(spinnerDistrictArrayAdapter);
//
//                    stateids = stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1];
//
//
//                    if (CommonConstants.districtName != null) {
//                        int spinnerPosition2 = spinnerDistrictArrayAdapter.getPosition(CommonConstants.districtName);
//                        spin_dist.setSelection(spinnerPosition2);
//                    }
//                    // districtSpin.setSelection(1);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        spin_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (districtDataMap != null && districtDataMap.size() > 0 && spin_dist.getSelectedItemPosition() != 0) {
//                    mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getMandalsQuery(districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1]));
//                    ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
//                    spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spin_mandal.setAdapter(spinnerMandalArrayAdapter);
//
//                    districtids = districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1];
//
//
//                    //districtids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsIdsQuery(districtSpin.getSelectedItem().toString()));
//
//
//
//
//                    if (CommonConstants.mandalName != null) {
//                        int spinnerPosition3 = spinnerMandalArrayAdapter.getPosition(CommonConstants.mandalName);
//                        spin_mandal.setSelection(spinnerPosition3);
//                    }
//                    //mandalSpin.setSelection(1);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        spin_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (mandalDataMap != null && mandalDataMap.size() > 0 && spin_mandal.getSelectedItemPosition() != 0) {
//                    villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getVillagesQuery(mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1]));
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
//                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spin_village.setAdapter(spinnerArrayAdapter);
//
//                    mandalids = mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1];
//
//
//                    //mandalids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalIdsQuery(mandalSpin.getSelectedItem().toString()));
//
//
//
//
//                    if (CommonConstants.villageName != null) {
//                        int spinnerPosition4 = spinnerArrayAdapter.getPosition(CommonConstants.villageName);
//                        spin_village.setSelection(spinnerPosition4);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        spin_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if (villagesDataMap != null && villagesDataMap.size() > 0 && spin_village.getSelectedItemPosition() != 0) {
//                    String villageCode = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
//                    //villgeids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalIdsQuery(villageSpin.getSelectedItem().toString()));
//
//                    villgeids = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
////
//    }





    private FloatingActionButton backBT;
    private FloatingActionButton nextBT,add;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
//        add=view.findViewById(R.id.add);
//

    //    geo_boundaries = view.findViewById(R.id.geo_boundaries);
    }


    @Override
    public void onResume() {
        super.onResume();

        backBT.setOnClickListener(this);
        nextBT.setOnClickListener(this);
//        geo_boundaries.setOnClickListener(this);
//        add.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
        nextBT.setOnClickListener(null);
//        geo_boundaries.setOnClickListener(null);
//        add.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;
//
//            case R.id.nextBT:
//                if (validation()) {
//
//                    submitplotdetails();
//
//                    if (mListener != null)
//                        mListener.onNextPressed(this);
//                }
//                break;
//
//            case R.id.geo_boundaries:
//                startActivity(new Intent(getContext(), PreViewAreaCalScreen.class));
//                break;
//
//            case R.id.add:
//
//                Intent intent = new Intent(getContext(), AddplotActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("code",farmerDetails.get(0).getCode());
//                startActivity(intent);
//                break;
//        }
        }
    }
    private void submitplotdetails() {
        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

        LinkedHashMap map= new LinkedHashMap();


        map.put("Id", farmerDetails.get(0).getId());
        map.put("Code", plotDetails.get(0).getCode());
        map.put("FarmerCode", plotDetails.get(0).getFarmerCode());
        map.put("TotalPlotArea",totalarea_edittxt.getText());
        map.put("AdaptedArea", adoptedarea_edittxt.getText().toString());
        map.put("GPSPlotArea", gpsarea_edittxt.getText());
        map.put("SurveyNumber", surverynumber_edittext.getText().toString());
        map.put("PassbookNumber", passbook_edittxt.getText().toString());
        map.put("PlotOwnerShipTypeId", ownershipids.toString());

        if(spin_ownership.getSelectedItemPosition() == 1){

            map.put("OwnerName", ownerame_editext.getText().toString());
            map.put("OwnerContactNumber", ownerContactnumber_edittext.getText().toString());

        }else {

            map.put("OwnerName", "");
            map.put("OwnerContactNumber", "");
        }

        map.put("PlotStatusId", 105);
        map.put("Address1", address_edittxt.getText().toString());
        map.put("Address2", address2_edittxt.getText().toString());
        map.put("StateId", stateids);
        map.put("DistrictId", districtids);
        map.put("MandalId", mandalids);
        map.put("VillageId", villgeids);
        map.put("IsActive", true);
        map.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
        map.put("CreatedDate", plotDetails.get(0).getCreatedDate());
        map.put("UpdatedByUserId", plotDetails.get(0).getUpdatedByUserId());
        map.put("UpdatedDate", timeStamp);
        map.put("ServerUpdatedStatus", 0);
//

        List<LinkedHashMap> list = new ArrayList<>();

        list.add(map);

     //   Log.d("Query=======","where Code = " + farmerDetails.get(0).getCode());

//        dataAccessHandler.updateData("Plot", list, true, " where FarmerCode = " + "'"+farmerDetails.get(0).getCode()+"'", new ApplicationThread.OnComplete<String>() {
//            @Override
//            public void execute(boolean success, String result, String msg) {
//                super.execute(success, result, msg);
//                if (success) {
//                    Log.v(LOG_TAG, "Plot Details inserted Successfully");
//                }
//
//            }add
//        });

//        dataAccessHandler.insertData("Plot", list, new ApplicationThread.OnComplete<String>() {
//            @Override
//            public void execute(boolean success, String result, String msg) {
//                super.execute(success, result, msg);
//
//                if (success) {
//                    Log.v(LOG_TAG, "Plot Details inserted Successfully");
//                }
//            }
//        });

    }


    private boolean validation() {
        if (TextUtils.isEmpty(totalarea_edittxt.getText().toString().trim())) {
            totalarea_label.setError("Please Enter Total Area");
            return false;
        }

        else if (TextUtils.isEmpty(adoptedarea_edittxt.getText().toString().trim())) {

            adoptedarea_label.setError("Please Enter Adopted Area");

            return false;
        }

        else if (TextUtils.isEmpty(gpsarea_edittxt.getText().toString().trim())) {

            gpsarea_label.setError("Please Enter Gps Area");

            return false;
        }
        else if (TextUtils.isEmpty(survey_edittxt.getText().toString().trim())) {
            surverynumber_label.setError("Please Enter Survey Number");

            return false;
        }
        else if (TextUtils.isEmpty(passbook_edittxt.getText().toString().trim())) {
            passbook_label.setError("Please Enter Passbook Number");

            return false;
        }

        else if (spin_ownership.getSelectedItemPosition() == 0) {

            showDialog(getContext(), "Please Select Plot OwnerShip");
            return false;
        }
        else if(ownership_id==108){
            if (TextUtils.isEmpty(ownerame_editext.getText().toString().trim())) {
                ownerame_label.setError("Please Enter Owner Name");
                return false;
            } else if (TextUtils.isEmpty(ownerContactnumber_edittext.getText().toString().trim())) {
                ownerContactnumber_label.setError("Please Enter Owner Contact Number");
                return false;
            }

            else if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
                address_label.setError(getResources().getString(R.string.err_please_address));
                return false;
            }




            else if (spin_state.getSelectedItemPosition() == 0) {

                showDialog(getContext(), "Please Select State");
                return false;
            }

            else if (spin_dist.getSelectedItemPosition() == 0) {

                showDialog(getContext(), "Please Select District");
                return false;
            }

            else if (spin_mandal.getSelectedItemPosition() == 0) {

                showDialog(getContext(), "Please Select Mandal");
                return false;
            }

            else if (spin_village.getSelectedItemPosition() == 0) {

                showDialog(getContext(), "Please Select Mandal");
                return false;
            }

//        else if (spin_plotstatus.getSelectedItemPosition() == 0) {
//
//            showDialog(getContext(), "Please Select Plot Status");
//            return false;
//        }


        }




//
//
//        else if (TextUtils.isEmpty(primaryPhone_edittxt.getText().toString().trim())) {
//            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
//            primaryPhone_label.setError("Please Enter Primary Phone Number");
//            return false;
//        }
//
//
//        else if (TextUtils.isEmpty(primaryPhone_edittxt.getText().toString().trim())) {
//            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
//            primaryPhone_label.setError("Please Enter Primary Phone Number");
//            return false;
//        }
        return true;
    }


    public void showDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onEditClicked(int position) {

    }

    @Override
    public void onDeleteClicked(int position) {

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
        void onBackPressed(Fragment fragment);
        void onNextPressed(Fragment fragment);
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }



        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.address_edittxt:
                    validate_address();
                    break;
//                case R.id.surveynumber_edittxt:
//                    validate_village();
//                    break;
//                case R.id.mandal_edittxt:
//                    validate_mandal();
//                    break;
//                case R.id.pin_edittxt:
//                    validate_pin();
//                    break;
//                case R.id.dist_edittxt:
//                    validate_dist();
//                    break;
//                case R.id.state_edittxt:
//                    validate_state();
//                    break;
                case R.id.Total_Area_edittxt:
                    validate_totalarea();
                    break;
                case R.id.Adopted_area_edittxt:
                    validate_adoptedarea();
                    break;

                case R.id.gps_are_edittxt:
                    validate_gpsarea();
                    break;

                case R.id.surveynumber_edittxt:
                    validate_surveynumber();
                    break;

                case R.id.passbook_number_edittxt:
                    validate_passbooknumber();
                    break;

                case R.id.owner_contactNumber_edittxt:
                    validate_ownerContactnumber();
                    break;

                case R.id.owner_name_edittxt:
                    validate_ownername();
                    break;

            }}

//        private boolean validate_surveynumber() {
//            if (TextUtils.isEmpty(survey_edittxt.getText().toString())) {
//                surverynumber_label.setError("Please Enter Adangal Number/Survey Number");
//                requestFocus(survey_edittxt);
//            } else {
//                surverynumber_label.setErrorEnabled(false);
//
//            }
//            return true;
//        }
//
//        private boolean validate_passbooknumber() {
//            if (TextUtils.isEmpty(passbook_edittxt.getText().toString())) {
//                passbook_label.setError("Please Enter Passbook Number");
//                requestFocus(passbook_edittxt);
//            } else {
//                passbook_label.setErrorEnabled(false);
//
//            }
//            return true;
//        }


        private boolean validate_totalarea() {
            if (TextUtils.isEmpty(totalarea_edittxt.getText().toString())) {
                totalarea_label.setError("Please Enter Total Area");
                requestFocus(totalarea_edittxt);
            } else {
                totalarea_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_adoptedarea() {
            if (TextUtils.isEmpty(adoptedarea_edittxt.getText().toString())) {
                adoptedarea_label.setError("Please Enter Adopted Area");
                requestFocus(adoptedarea_edittxt);
            } else {
                adoptedarea_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_gpsarea() {
            if (TextUtils.isEmpty(gpsarea_edittxt.getText().toString())) {
                gpsarea_label.setError("Please Enter Gps Area");

            } else {
                gpsarea_label.setErrorEnabled(false);

            }
            return true;
        }


        private boolean validate_surveynumber() {
            if (TextUtils.isEmpty(surverynumber_edittext.getText().toString())) {
                surverynumber_label.setError("Please Enter Adangal Number/Survey Number");
                requestFocus(surverynumber_edittext);
            } else {
                surverynumber_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_adangal() {
            if (TextUtils.isEmpty(adangal_edittext.getText().toString())) {
                adangal_label.setError("Please Enter Adangal Number/Survey Number");
                requestFocus(adangal_edittext);
            } else {
                adangal_label.setErrorEnabled(false);

            }
            return true;
        }

        private boolean validate_passbooknumber() {
            if (TextUtils.isEmpty(passbook_edittxt.getText().toString())) {
                passbook_label.setError("Please Enter Passbook Number");
                requestFocus(passbook_edittxt);
            } else {
                passbook_label.setErrorEnabled(false);

            }
            return true;
        }

    }

    private boolean validate_ownername() {
        if (TextUtils.isEmpty(ownerame_editext.getText().toString())) {
            ownerame_label.setError("Please Enter Passbook Number");
            requestFocus(ownerame_editext);
        } else {
            ownerame_label.setErrorEnabled(false);
        } return true;}


    private boolean validate_ownerContactnumber() {
        if (TextUtils.isEmpty(ownerContactnumber_edittext.getText().toString())) {
            ownerContactnumber_label.setError("Please Enter Passbook Number");
            requestFocus(ownerContactnumber_edittext);
        } else {
            ownerContactnumber_label.setErrorEnabled(false);
        } return true;}



    private boolean validate_address() {
        if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address_edittxt);

        }
        else {
            address_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_address2() {
        if(TextUtils.isEmpty(address2_edittxt.getText().toString().trim())){
            address2_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address2_edittxt);

        }
        else {
            address2_label.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

