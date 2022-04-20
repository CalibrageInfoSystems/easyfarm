package com.cis.easyfarm.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DataSavingHelper;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.cis.easyfarm.Fragments.Adduser_fragment.DATE_FORMAT1;
import static com.cis.easyfarm.Fragments.Adduser_fragment.getKeysByValue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Adduser_fifthpage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Adduser_fifthpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Adduser_fifthpage extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    private static final String LOG_TAG = Adduser_fifthpage.class.getName();



    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";


    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;

    Spinner spin_Soil, spin_Irrigation, spin_powerAvailability,spin_soil_culture_Type;
    CheckBox powerCheck;
    DataAccessHandler dataAccessHandler;
    View view;
    Boolean isAvailable;
    EditText serviceNumber;
    EditText  Nitrogen_edittxt, Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;
    TextInputLayout serviceNumberLyt,Nitrogen_lyt, Prosperous_lyt, Potassium_lyt, Carbon_lyt,
            Hydrogen_lyt, Oxygen_lyt, Sulphur_lyt, Calcium_lyt, Magnesium_lyt;
    Object soilIds, irrigationIds, soilCultureTypeIds;
    Button save;
    ImageView soilReportImg;
    List<Plot> plotDetails;
    public static String farmerCode;
    List<Farmer> farmerDetails;

    private String mCurrentPhotoPath, extension;
    private int GALLERY = 1, CAMERA = 2;



    String[] powerAvaliabilty = new String[] {"Select Power Avaliabilty", "Yes", "No"};

    private LinkedHashMap soilDataMap, irrigationDataMap, soilCultureTypeDataMap;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Adduser_fifthpage() {
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

    public static Adduser_fifthpage newInstance(String param1, String param2) {
        Adduser_fifthpage fragment = new Adduser_fifthpage();
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
//       Nitrogen_edittxt= view.findViewById(R.id.Nitrogen_edittxt);
//        Prosperous_edittxt= view.findViewById(R.id.Prosperous_edittxt);
//        Potassium_edittxt= view.findViewById(R.id.Potassium_edittxt);
//        Carbon_edittxt= view.findViewById(R.id.Carbon_edittxt);
//        Hydrogen_edittxt= view.findViewById(R.id.Hydrogen_edittxt);
//        Oxygen_edittxt= view.findViewById(R.id.Oxygen_edittxt);
//        Sulphur_edittxt= view.findViewById(R.id.Sulphur_edittxt);
//        Calcium_edittxt= view.findViewById(R.id.Calcium_edittxt);
//        Magnesium_edittxt= view.findViewById(R.id.Magnesium_edittxt);
//
//        spin_soil_culture_Type= view.findViewById(R.id.spin_soil_culture_Type);
//        Nitrogen_lyt= view.findViewById(R.id.Nitrogen_lyt);
//        Prosperous_lyt= view.findViewById(R.id.Prosperous_lyt);
//        Potassium_lyt= view.findViewById(R.id.Potassium_lyt);
//        Carbon_lyt= view.findViewById(R.id.Carbon_lyt);
//        Hydrogen_lyt= view.findViewById(R.id.Hydrogen_lyt);
//        Oxygen_lyt= view.findViewById(R.id.Oxygen_lyt);
//        Sulphur_lyt= view.findViewById(R.id.Sulphur_lyt);
//        Calcium_lyt= view.findViewById(R.id.Calcium_lyt);
//        Magnesium_lyt= view.findViewById(R.id.Magnesium_lyt);
//
//        soilReportImg = view.findViewById(R.id.img_soilReport);

//        Nitrogen_lyt, Prosperous_lyt, Potassium_lyt, Carbon_lyt,
//                Hydrogen_lyt, Oxygen_lyt, Sulphur_lyt, Calcium_lyt, Magnesium_lyt


        spin_powerAvailability= view.findViewById(R.id.spin_powerAvailability);
        serviceNumber= view.findViewById(R.id.serviceNumber_edittxt);
        serviceNumberLyt= view.findViewById(R.id.serviceNumber_lyt);
        save = view.findViewById(R.id.saveButton);
     //   Nitrogen_edittxt, Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;
    }

    private void setviews() {

        serviceNumber.addTextChangedListener(new ValidationTextWatcher(serviceNumber));

//        Nitrogen_edittxt.addTextChangedListener(new ValidationTextWatcher(Nitrogen_edittxt));
//        Prosperous_edittxt.addTextChangedListener(new ValidationTextWatcher(Prosperous_edittxt));
//        Potassium_edittxt.addTextChangedListener(new ValidationTextWatcher(Potassium_edittxt));
//        Carbon_edittxt.addTextChangedListener(new ValidationTextWatcher(Carbon_edittxt));
//        Hydrogen_edittxt.addTextChangedListener(new ValidationTextWatcher(Hydrogen_edittxt));
//        Oxygen_edittxt.addTextChangedListener(new ValidationTextWatcher(Oxygen_edittxt));
//        Sulphur_edittxt.addTextChangedListener(new ValidationTextWatcher(Sulphur_edittxt));
//        Calcium_edittxt.addTextChangedListener(new ValidationTextWatcher(Calcium_edittxt));
//
//        Magnesium_edittxt.addTextChangedListener(new ValidationTextWatcher(Magnesium_edittxt));

        soilDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getSoilTypes());
        String[] soilTypes = CommonUtils.fromMap(soilDataMap, "Soil Type");

        Log.e("soilTypes===========", soilTypes[0] + "");
        ArrayAdapter<String> ownerShipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, soilTypes);
        ownerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Soil.setAdapter(ownerShipAdapter);

        irrigationDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getIrrigationType());
        String[] irrigationTypes = CommonUtils.fromMap(irrigationDataMap, "Irrigation Type");

        Log.e("irrigationTypes========", irrigationTypes[0] + "");
        ArrayAdapter<String> ownerShipAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, irrigationTypes);
        ownerShipAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Irrigation.setAdapter(ownerShipAdapter1);

//        soilCultureTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getsoilCultureType());
//        String[] cultureTypes = CommonUtils.fromMap(soilCultureTypeDataMap, "Culture Type");
//        ArrayAdapter<String> ownerShipAdapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cultureTypes);
//        ownerShipAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin_soil_culture_Type.setAdapter(ownerShipAdapter3);


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
//
//        spin_soil_culture_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Object cultureTypeId;
//
//                cultureTypeId = getKeysByValue(soilCultureTypeDataMap,spin_soil_culture_Type.getSelectedItem().toString());
//                soilCultureTypeIds = (cultureTypeId.toString().substring(1, cultureTypeId.toString().length()-1));
//                Log.d("==soilCultureTypeIds==",soilCultureTypeIds.toString());
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

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

//
//        soilReportImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showPictureDialog();
//            }
//        });


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

                Add_Farmer activity = (Add_Farmer) getActivity();

                Bundle results = activity.getMyData();

                farmerCode = results.getString("val1");

                Log.d("==FarmerCode==", farmerCode);
                farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(Adduser_fragment.value1), 1);


                String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
                plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getFarmerplotDetails(farmerCode), 1);

                CommonConstants.PLOT_CODE = plotDetails.get(0).getCode();
                Log.d("TotalArea==",   CommonConstants.PLOT_CODE+ "");

                if (validation()){

                    LinkedHashMap soilmap = new LinkedHashMap();


                    soilmap.put("Id", 0);
                    soilmap.put("PlotCode", plotDetails.get(0).getCode());
                    soilmap.put("SoilTypeId", soilIds.toString());
                    soilmap.put("IsActive", 1);
                    soilmap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    soilmap.put("CreatedDate", timeStamp);
                    soilmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
                    soilmap.put("UpdatedDate", timeStamp);
                    soilmap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> soillist = new ArrayList<>();

                    soillist.add(soilmap);


//                    LinkedHashMap soilTestmap = new LinkedHashMap();
//
//
//                    soilTestmap.put("Id", 0);
//                    soilTestmap.put("PlotCode", plotDetails.get(0).getCode());
//                    soilTestmap.put("CultureTypeId", soilCultureTypeIds.toString());
//                    soilTestmap.put("Nitrogen", Nitrogen_edittxt.getText().toString());
//                    soilTestmap.put("Prosperous", Prosperous_edittxt.getText().toString());
//                    soilTestmap.put("Potassium", Potassium_edittxt.getText().toString());
//                    soilTestmap.put("Carbon", Carbon_edittxt.getText().toString());
//                    soilTestmap.put("Hydrogen", Hydrogen_edittxt.getText().toString());
//                    soilTestmap.put("Oxygen", Oxygen_edittxt.getText().toString());
//                    soilTestmap.put("Sulphur", Sulphur_edittxt.getText().toString());
//                    soilTestmap.put("Calcium", Calcium_edittxt.getText().toString());
//                    soilTestmap.put("Magnesium", Magnesium_edittxt.getText().toString());
//                    soilTestmap.put("FileName", "");
//                    soilTestmap.put("FileLocation", "");
//                    soilTestmap.put("FileExtension", extension);
//                    soilTestmap.put("IsActive", 1);
//                    soilTestmap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
//                    soilTestmap.put("CreatedDate", timeStamp);
//                    soilTestmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
//                    soilTestmap.put("UpdatedDate", timeStamp);
//                    soilTestmap.put("FileBytes", doFileUpload(new File(mCurrentPhotoPath)));
//                    soilTestmap.put("ServerUpdatedStatus", 0);
//
//                    final List<LinkedHashMap> soilTestlist = new ArrayList<>();
//
//                    soilTestlist.add(soilTestmap);

                    LinkedHashMap irrigationmap = new LinkedHashMap();


                    irrigationmap.put("Id", 0);
                    irrigationmap.put("PlotCode", plotDetails.get(0).getCode());
                    irrigationmap.put("IrrigationTypeId", irrigationIds.toString());
                    irrigationmap.put("IsActive", 1);
                    irrigationmap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    irrigationmap.put("CreatedDate", timeStamp);
                    irrigationmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
                    irrigationmap.put("UpdatedDate", timeStamp);
                    irrigationmap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> irrigationlist = new ArrayList<>();

                    irrigationlist.add(irrigationmap);

                    LinkedHashMap powermap = new LinkedHashMap();


                    powermap.put("Id", 0);
                    powermap.put("PlotCode", plotDetails.get(0).getCode());
                    powermap.put("IsAvailable", isAvailable);

                    if (isAvailable = true){
                        powermap.put("ServiceNumber", serviceNumber.getText().toString());
                    }else{
                        powermap.put("ServiceNumber", "null");
                    }

                    dataAccessHandler.insertMyData("SoilDetails", soillist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {

                                Log.d("SoilDetailsTable", "Inserted Successfully");
                                farmerDetails.get(0).setServerupdatedstatus(0);
                                CommonUtils.appendLog(LOG_TAG, "SoilDetailsTable", "Inserted Successfully");

                            }
                        }
                    });

                    powermap.put("IsActive", 1);
                    powermap.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
                    powermap.put("CreatedDate", timeStamp);
                    powermap.put("UpdatedByUserId", SyncHomeActivity.User_id);
                    powermap.put("UpdatedDate", timeStamp);
                    powermap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> powerlist = new ArrayList<>();

                    powerlist.add(powermap);
                    DataSavingHelper.saveRecordIntoActivityLog(getContext(), CommonConstants.SoildetailsupdatedforPlot);


                    DataSavingHelper.saveRecordIntoActivityLog(getContext(), CommonConstants.PowerdetailsupdatedforPlot);
                    dataAccessHandler.insertMyData("PowerDetails", powerlist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {

                                Log.d("PowerDetailsTable", "Inserted Successfully");
                                CommonUtils.appendLog(LOG_TAG, "PowerDetailsTable", "Inserted Successfully");

                                farmerDetails.get(0).setServerupdatedstatus(0);
                            }
                        }
                    });

//                    dataAccessHandler.insertMyData("SoilTestDetails", soilTestlist, new ApplicationThread.OnComplete<String>() {
//                        @Override
//                        public void execute(boolean success, String result, String msg) {
//
//                            if (success) {
//
//                                Log.d("SoilTestDetails", "Inserted Successfully");
//                                farmerDetails.get(0).setServerupdatedstatus(0);
//                            }
//                        }
//                    });


                    DataSavingHelper.saveRecordIntoActivityLog(getContext(), CommonConstants.IrrigationdetailsupdatedforPlot);
                    dataAccessHandler.insertMyData("IrrigationDetails", irrigationlist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {
                                Intent intent = new Intent(getContext(), SyncHomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                getActivity().finish();
                                Log.d("IrrigationDetailsTable", "Inserted Successfully");
                                CommonUtils.appendLog(LOG_TAG, "IrrigationDetailsTable", "Inserted Successfully");

                                farmerDetails.get(0).setServerupdatedstatus(0);
                            }
                        }
                    });

                    dataAccessHandler.updateUserInfoData(farmerDetails.get(0).getId().toString(), new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            super.execute(success, result, msg);

                            Log.d("mahesh", "@@@@@ updateUserRoleXref Status :" + success);
                        }
                    });

                    dataAccessHandler.updateUserRoleXrefDate(timeStamp, farmerDetails.get(0).getId().toString(), new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            super.execute(success, result, msg);

                            Log.d("mahesh", "@@@@@ updateUserRoleXref Status :" + success);
                        }
                    });


//                    dataAccessHandler.updateFarmerStatus(0, farmerDetails.get(0).getCode(), new ApplicationThread.OnComplete<String>() {
//                        @Override
//                        public void execute(boolean success, String result, String msg) {
//                            Log.d("mahesh", "@@@@@ UPdated Status :" + success);
//                        }
//                    });


//                    dataAccessHandler.updateFarmerStatus(0, farmerDetails.get(0).getCode(), new ApplicationThread.OnComplete<String>() {
//                        @Override
//                        public void execute(boolean success, String result, String msg) {
//                            Log.d("mahesh", "@@@@@ UPdated Status :"+success);
//                        }
//                    });



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
      //  Nitrogen_edittxt, Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.serviceNumber_edittxt:
                    validate_serviceNumber();
                    break;
                case R.id.Nitrogen_edittxt:
                    validate_Nitrogen();
                    break;
                case R.id.Prosperous_edittxt:
                    validate_Prosperous();
                    break;
                case R.id.Potassium_edittxt:
                    validate_Potassium();
                    break;
                case R.id.Carbon_edittxt:
                    validate_Carbon();
                    break;
                case R.id.Hydrogen_edittxt:
                    validate_Hydrogen();
                    break;
                case R.id.Oxygen_edittxt:
                    validate_Oxygen();
                    break;
                case R.id.Sulphur_edittxt:
                    validate_sulphur();
                    break;
                case R.id.Calcium_edittxt:
                    validate_Calcium();
                    break;
                case R.id.Magnesium_edittxt:
                    validate_Magnesium();
                    break;

            }
        }
    }

    private boolean validate_Magnesium() {
        if (TextUtils.isEmpty(Magnesium_edittxt.getText().toString())) {
            Magnesium_lyt.setError("Please Enter Magnesium Value");
            requestFocus(Magnesium_edittxt);
        } else {
            Magnesium_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_Calcium() {
        if (TextUtils.isEmpty(Calcium_edittxt.getText().toString())) {
            Calcium_lyt.setError("Please Enter Calcium Value");
            requestFocus(Calcium_edittxt);
        } else {
            Calcium_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_sulphur() {
        if (TextUtils.isEmpty(Oxygen_edittxt.getText().toString())) {
            Sulphur_lyt.setError("Please Enter Sulphur Value");
            requestFocus(Oxygen_edittxt);
        } else {
            Sulphur_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_Oxygen() {
        if (TextUtils.isEmpty(Oxygen_edittxt.getText().toString())) {
            Oxygen_lyt.setError("Please Enter Oxygen Value");
            requestFocus(Oxygen_edittxt);
        } else {
            Oxygen_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_Hydrogen() {
        if (TextUtils.isEmpty(Hydrogen_edittxt.getText().toString())) {
            Hydrogen_lyt.setError("Please Enter Carbon Value");
            requestFocus(Hydrogen_edittxt);
        } else {
            Hydrogen_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_Carbon() {
        if (TextUtils.isEmpty(Carbon_edittxt.getText().toString())) {
            Carbon_lyt.setError("Please Enter Carbon Value");
            requestFocus(Carbon_edittxt);
        } else {
            Carbon_lyt.setErrorEnabled(false);

        }
        return true;

    }

    private boolean validate_Potassium() {
        if (TextUtils.isEmpty(Potassium_edittxt.getText().toString())) {
            Potassium_lyt.setError("Please Enter Potassium Value");
            requestFocus(Potassium_edittxt);
        } else {
            Potassium_lyt.setErrorEnabled(false);

        }
        return true;

    }

    private boolean validate_Prosperous() {
        if (TextUtils.isEmpty(Prosperous_edittxt.getText().toString())) {
            Prosperous_lyt.setError("Please Enter Prosperous Value");
            requestFocus(Prosperous_edittxt);
        } else {
            Prosperous_lyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_Nitrogen() {
        if (TextUtils.isEmpty(Nitrogen_edittxt.getText().toString())) {
            Nitrogen_lyt.setError("Please Enter Nitrogen Value");
            requestFocus(Nitrogen_edittxt);
        } else {
            Nitrogen_lyt.setErrorEnabled(false);

        }
        return true;
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
            Toast.makeText(getContext(), "Please Select Soil Type", Toast.LENGTH_SHORT).show();
            return false;
        }
//        else if(CommonUtils.isEmptySpinner(spin_soil_culture_Type)){
//            Toast.makeText(getContext(), "Please Select Soil Culture Type  ", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (TextUtils.isEmpty(Nitrogen_edittxt.getText().toString().trim())) {
//            Nitrogen_lyt.setError("Please Enter Nitrogen Value");
//            return false;
//        }
//     //   Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;
//        else if (TextUtils.isEmpty(Prosperous_edittxt.getText().toString().trim())) {
//            Prosperous_lyt.setError("Please Enter Prosperous Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Potassium_edittxt.getText().toString().trim())) {
//            Potassium_lyt.setError("Please Enter Potassium Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Carbon_edittxt.getText().toString().trim())) {
//           Carbon_lyt.setError("Please Enter Carbon Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Hydrogen_edittxt.getText().toString().trim())) {
//            Hydrogen_lyt.setError("Please Enter Hydrogen Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Oxygen_edittxt.getText().toString().trim())) {
//            Oxygen_lyt.setError("Please Enter Oxygen Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Sulphur_edittxt.getText().toString().trim())) {
//            Sulphur_lyt.setError("Please Enter Sulphur Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Calcium_edittxt.getText().toString().trim())) {
//            Calcium_lyt.setError("Please Enter  Calcium Value");
//            return false;
//        }
//        else if (TextUtils.isEmpty(Magnesium_edittxt.getText().toString().trim())) {
//            Magnesium_lyt.setError("Please Enter Magnesium Value");
//            return false;
//        }
        else if (CommonUtils.isEmptySpinner(spin_Irrigation)) {
            Toast.makeText(getContext(), "Please Select Irrigation Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (CommonUtils.isEmptySpinner(spin_powerAvailability)) {
            Toast.makeText(getContext(), "Please Select PowerAvailability Type", Toast.LENGTH_SHORT).show();
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
        if (context instanceof Adduser_fifthpage.OnFragmentInteractionListener) {
            mListener = (Adduser_fifthpage.OnFragmentInteractionListener) context;
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

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());

        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path===", path);
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    soilReportImg.setImageBitmap(bitmap);
                    isselectedCamera = true;
                    // profileImgBtn.add(bitmap);
                    // displayImages();
                } catch (IOException e) {
                    e.printStackTrace();
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            saveImage(thumbnail);
            soilReportImg.setImageBitmap(thumbnail);
            isselectedCamera = true;
            // profileImgBtn.add(thumbnail);
            //  s
            //displayImages();
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == 3){
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path===", path);
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    // profileImgBtn.add(bitmap);
                    // displayImages();
                } catch (IOException e) {
                    e.printStackTrace();
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == 4) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            // profileImgBtn.add(thumbnail);
            //  saveImage(thumbnail);
            //displayImages();
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {

            farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(Adduser_fragment.value1), 1);

            File f = new File(wallpaperDirectory, "STR"+farmerDetails.get(0).getCode() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            mCurrentPhotoPath =f.getAbsolutePath();
            Log.e("PhotoPath===========",mCurrentPhotoPath);
            Log.e("PhotoPath===========", doFileUpload(new File(mCurrentPhotoPath)));
            extension = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("."));
            Log.e("Extension===========",extension);
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private String doFileUpload(File f){


        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //String encoded = Base64.encodeToString(bytes, 0);
        String finalString =android.util.Base64.encodeToString(bytes,0);



        return finalString;

// Receiving side

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
