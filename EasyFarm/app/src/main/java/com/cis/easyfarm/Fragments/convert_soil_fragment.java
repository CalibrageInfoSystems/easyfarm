package com.cis.easyfarm.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.SoilDetails;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.Constants;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.Add_converted_Farmer;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cis.easyfarm.Fragments.Adduser_fragment.getKeysByValue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link convert_soil_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link convert_soil_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class convert_soil_fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String LOG_TAG = convert_soil_fragment.class.getName();



    Spinner spin_Soil, spin_Irrigation, spin_powerAvailability;
    CheckBox powerCheck;
    DataAccessHandler dataAccessHandler;
    View view;
    Boolean isAvailable;
    EditText serviceNumber;
    TextInputLayout serviceNumberLyt;
    Object soilIds, irrigationIds;
    Button save;
    List<Plot> plotDetails;
    List<SoilDetails>soilDetails;
    public static String farmerCode;
int Soiltype;

    String[] powerAvaliabilty = new String[] {"Select Power Avaliabilty", "Yes", "No"};

    private LinkedHashMap soilDataMap, irrigationDataMap;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public convert_soil_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Adduser_fifthpage.
     */
    // TODO: Rename and change types and number of parameters

    public static convert_soil_fragment newInstance(String param1, String param2) {
        convert_soil_fragment fragment = new convert_soil_fragment();
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
        view =  inflater.inflate(R.layout.fragment_adduser_fifthpage, container, false);

        intviews();
        setviews();

        return view;
    }


    private void intviews() {

        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());

        spin_Soil= view.findViewById(R.id.spin_soilType);
        spin_Irrigation= view.findViewById(R.id.spin_irrigationType);
        // powerCheck= view.findViewById(R.id.powerCheckBox);
        spin_powerAvailability= view.findViewById(R.id.spin_powerAvailability);
        serviceNumber= view.findViewById(R.id.serviceNumber_edittxt);
        serviceNumberLyt= view.findViewById(R.id.serviceNumber_lyt);
        save = view.findViewById(R.id.saveButton);

    }

    private void setviews() {

        Add_converted_Farmer activity = (Add_converted_Farmer) getActivity();

        Bundle results = activity.getMyData();

        farmerCode = results.getString("val1");

        Log.d("==FarmerCode==", farmerCode);
        soilDetails = (List<SoilDetails>) dataAccessHandler.getSoilDetails(Queries.getInstance().getFarmerplotDetails(farmerCode), 1);

        Log.e("soil===========1111",soilDetails.get(0).getSoilTypeId()+"") ;
       Soiltype = soilDetails.get(0).getSoilTypeId();
        serviceNumber.addTextChangedListener(new convert_soil_fragment.ValidationTextWatcher(serviceNumber));


        soilDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getSoilTypes());
        String[] soilTypes = CommonUtils.fromMap(soilDataMap, "Soil Type");

        Log.e("soilTypes===========", soilTypes[0] + "");
        ArrayAdapter<String> ownerShipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, soilTypes);
        ownerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Soil.setAdapter(ownerShipAdapter);
        if (Soiltype != 0) {
            int spinnerPosition5 = ownerShipAdapter.getPosition(Soiltype+"");
            Log.e("spinnerPosition5===",spinnerPosition5+1+"");
            spin_Soil.setSelection(spinnerPosition5+2);
        }
        irrigationDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getIrrigationType());
        String[] irrigationTypes = CommonUtils.fromMap(irrigationDataMap, "Irrigation Type");

        Log.e("irrigationTypes========", irrigationTypes[0] + "");
        ArrayAdapter<String> ownerShipAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, irrigationTypes);
        ownerShipAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Irrigation.setAdapter(ownerShipAdapter1);

        ArrayAdapter<String> ownerShipAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, powerAvaliabilty);
        ownerShipAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_powerAvailability.setAdapter(ownerShipAdapter2);


        spin_Soil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object soilId;

                soilId = getKeysByValue(soilDataMap,spin_Soil.getSelectedItem().toString());
                soilIds = (soilId.toString().substring(1, soilId.toString().length()-1));
                Log.d("==soilIds==",soilIds.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_Irrigation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object irrigationId;

                irrigationId = getKeysByValue(irrigationDataMap,spin_Irrigation.getSelectedItem().toString());
                irrigationIds = (irrigationId.toString().substring(1, irrigationId.toString().length()-1));
                Log.d("==irrigationIds==",irrigationIds.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_powerAvailability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spin_powerAvailability.getSelectedItemPosition() == 1) {
                    isAvailable = true;
                    serviceNumberLyt.setVisibility(View.VISIBLE);
                } else {
                    isAvailable = false;
                    serviceNumberLyt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        powerCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (powerCheck.isChecked()) {
//                    isAvailable = true;
//                    serviceNumberLyt.setVisibility(View.VISIBLE);
//                } else if(!powerCheck.isChecked()) {
//                    isAvailable = false;
//                    serviceNumberLyt.setVisibility(View.GONE);
//                }
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
                plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getFarmerplotDetails(farmerCode), 1);


                if (validation()){

                    LinkedHashMap soilmap = new LinkedHashMap();


                    soilmap.put("Id", 0);
                    soilmap.put("PlotCode", plotDetails.get(0).getCode());
                    soilmap.put("SoilTypeId", soilIds.toString());
                    soilmap.put("IsActive", true);
                    soilmap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    soilmap.put("CreatedDate", timeStamp);
                    soilmap.put("UpdatedByUserId", plotDetails.get(0).getUpdatedByUserId());
                    soilmap.put("UpdatedDate", timeStamp);
                    soilmap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> soillist = new ArrayList<>();

                    soillist.add(soilmap);

                    LinkedHashMap irrigationmap = new LinkedHashMap();


                    irrigationmap.put("Id", 0);
                    irrigationmap.put("PlotCode", plotDetails.get(0).getCode());
                    irrigationmap.put("IrrigationTypeId", irrigationIds.toString());
                    irrigationmap.put("IsActive", true);
                    irrigationmap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    irrigationmap.put("CreatedDate", timeStamp);
                    irrigationmap.put("UpdatedByUserId", plotDetails.get(0).getUpdatedByUserId());
                    irrigationmap.put("UpdatedDate", timeStamp);
                    irrigationmap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> irrigationlist = new ArrayList<>();

                    irrigationlist.add(irrigationmap);

                    LinkedHashMap powermap = new LinkedHashMap();


                    powermap.put("Id", 0);
                    powermap.put("PlotCode", plotDetails.get(0).getCode());
                    powermap.put("IsAvailable", isAvailable);

                    if (isAvailable == true){
                        powermap.put("ServiceNumber", serviceNumber.getText().toString());
                    }else{
                        powermap.put("ServiceNumber", null);
                    }

                    powermap.put("IsActive", true);
                    powermap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    powermap.put("CreatedDate", timeStamp);
                    powermap.put("UpdatedByUserId", plotDetails.get(0).getUpdatedByUserId());
                    powermap.put("UpdatedDate", timeStamp);
                    powermap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> powerlist = new ArrayList<>();

                    powerlist.add(powermap);


                    dataAccessHandler.insertData("SoilDetails", soillist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {

                                Log.d("SoilDetailsTable", "Inserted Successfully");
                                CommonUtils.appendLog(LOG_TAG, "SoilDetailsTable", "Updated Successfully");

                            }
                        }
                    });

                    dataAccessHandler.insertData("PowerDetails", powerlist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {

                                Log.d("PowerDetailsTable", "Inserted Successfully");
                                CommonUtils.appendLog(LOG_TAG, "PowerDetailsTable", "Updated Successfully");

                            }
                        }
                    });

                    dataAccessHandler.insertData("IrrigationDetails", irrigationlist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {

                                Log.d("IrrigationDetailsTable", "Inserted Successfully");
                                CommonUtils.appendLog(LOG_TAG, "IrrigationDetailsTable", "Updated Successfully");

                            }
                        }
                    });

                    Intent intent = new Intent(getContext(), SyncHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);

                }
            }
        });
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
                case R.id.serviceNumber_edittxt:
                    validate_serviceNumber();
                    break;

            }
        }
    }


    private boolean validate_serviceNumber() {
        if (TextUtils.isEmpty(serviceNumber.getText().toString())) {
            serviceNumberLyt.setError("Please Enter Service Number");
            requestFocus(serviceNumber);
        } else {
            serviceNumberLyt.setErrorEnabled(false);

        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private boolean validation() {

        if (CommonUtils.isEmptySpinner(spin_Soil)) {
            Toast.makeText(getContext(), "Please select Soil Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (CommonUtils.isEmptySpinner(spin_Irrigation)) {
            Toast.makeText(getContext(), "Please select Irrigation Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (CommonUtils.isEmptySpinner(spin_powerAvailability)) {
            Toast.makeText(getContext(), "Please select PowerAvailability Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if(spin_powerAvailability.getSelectedItemPosition() == 1) {

            if (TextUtils.isEmpty(serviceNumber.getText().toString().trim())) {
                serviceNumberLyt.setError("Please Enter Service Number");
                return false;
            } else if (TextUtils.isEmpty(serviceNumber.getText().toString().trim())) {
                serviceNumberLyt.setError("Please Enter Service Number");
                return false;
            }

        }

        return true;
    }

    private FloatingActionButton backBT;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backBT=view.findViewById(R.id.backBT);
        //  geo_boundaries = view.findViewById(R.id.geo_boundaries);
    }


    @Override
    public void onResume() {
        super.onResume();
        backBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
//        geo_boundaries.setOnClickListener(null);
    }


    // TODO: Rename method, update argument and hook method into UI event

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;
        }
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
        void onBackPressed(Fragment fragment);
    }
}
