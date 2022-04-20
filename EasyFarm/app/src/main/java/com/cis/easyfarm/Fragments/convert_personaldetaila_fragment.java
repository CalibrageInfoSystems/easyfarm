package com.cis.easyfarm.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.ImageUtil;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.Add_converted_Farmer;
import com.cis.easyfarm.ui.EmailValidator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class convert_personaldetaila_fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT2 = "dd-MM-yyyy";
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";


    private int GALLERY = 1, CAMERA = 2;
    public static String First_name;
    private EditText fname_edittxt, father_edittxt, address1_edittxt, address2_edittxt, pincode_edittxt, date_edittxt, primaryPhone_edittxt, secondaryPhone_edittxt, income_edittxt, email_edittxt, qualification_edittxt, source_edittxt;
    TextInputLayout firstname_label, mname_label, lastname_label, father_label, address1_label, address2_label, village_label, mandal_label, pincode_label, district_label, state_label, date_label, primaryPhone_label, secondaryPhone_label, income_label, email_label, qualificatin_label, source_label;

    TextView gendertxt, countrytxt, statetxt, districttxt, mandaltxt, villagetxt, categorytxt, eductiontxt;

    ImageView profileImg;
    private Spinner spin_gender, spin_category, spin_education, spin_educationType;
    private Spinner Countryspin, statespin, districtSpin, mandalSpin, villageSpin;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    String curTime ,date_time, dateofbirthconvert;
    public static String dateofBirth, dateString;

    RelativeLayout eductionDegreeTypeLayout;
    private String mCurrentPhotoPath;

    Button submit;
    public static String value1;
    public static String stateids,districtids,mandalids,villgeids;

    public static Object genderids,categortyids,educationids, educationDegreeTypeids, valueeee;

    private static final String LOG_TAG = convert_personaldetaila_fragment.class.getName();

    public static ArrayAdapter<String> genderadapter, educationDegreeTypeAdapter;

    public static String middleName;


    private LinkedHashMap<String, Pair> CountryMap, stateDataMap = null;
    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap = null, villagesDataMap = null;

    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    String[] gender = {"Select Gender ", "Male", "Female"};

    private LinkedHashMap CategoryMap, EducationDataMap, genderdatamap, EducationDegreeType;
    DataAccessHandler dataAccessHandler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static String Farmer_code;
    private OnStepOneListener mListener;
    List<Farmer> farmerDetails;
    public convert_personaldetaila_fragment() {
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
    public static convert_personaldetaila_fragment newInstance(String param1, String param2) {
        convert_personaldetaila_fragment fragment = new convert_personaldetaila_fragment();
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
        view = inflater.inflate(R.layout.fragment_adduser_fragment, container, false);

        Add_converted_Farmer activity = (Add_converted_Farmer) getActivity();

        Bundle results = activity.getMyData();

        value1 = results.getString("val1");

        Log.d("Codeee", value1);

        intviews();
        setviews();

        //  final DataAccessHandler dataAccessHandler = new DataAccessHandler(getContext());


        dateString = farmerDetails.get(0).getDOB();
        Log.d("url@===", farmerDetails.get(0).getPPFileLocation()+ "/" +farmerDetails.get(0).getPPFileName()
                +farmerDetails.get(0).getPPFileExtension());
        try {
            dateofbirthconvert = dateString;
            date_edittxt.setText(formatDateFromDateString(DATE_FORMAT1, DATE_FORMAT2, dateofbirthconvert));
            Log.d("DateofBirth@===", date_edittxt.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // date_edittxt.setText( farmerDetails.get(0).getDOB());

        Log.d("DateofBirth!@@@", date_edittxt.getText().toString());

        primaryPhone_edittxt.setText(farmerDetails.get(0).getPrimaryPhoneNumber());


        CommonConstants.countryID = "1";
        CommonConstants.stateId =farmerDetails.get(0).getStateid()+"";
        CommonConstants.districtId =farmerDetails.get(0).getDistictid()+"";
        CommonConstants.mandalId =farmerDetails.get(0).getMandalid()+"";
        CommonConstants.villageId =farmerDetails.get(0).getVillageid()+"";
        CommonConstants.FARMER_CODE =farmerDetails.get(0).getCode();

        CommonConstants.stateId = farmerDetails.get(0).getStateid() + "";
        CommonConstants.districtId = farmerDetails.get(0).getDistictid() + "";
        CommonConstants.mandalId = farmerDetails.get(0).getMandalid() + "";
        CommonConstants.villageId = farmerDetails.get(0).getVillageid() + "";

        CommonConstants.genderId = farmerDetails.get(0).getGendertypeid() + "";
        CommonConstants.categoryId = farmerDetails.get(0).getCategorytypeid() + "";
        CommonConstants.educationId = farmerDetails.get(0).getEducationtypeid() + "";
        CommonConstants.getEducationQualificationTypeId = farmerDetails.get(0).getEducationDegreeTypeId() + "";






        Log.v(LOG_TAG, "@@@ Statename " + CommonConstants.stateId);
        if (stateDataMap != null && stateDataMap.size() > 0) {
            Pair statePair = stateDataMap.get(CommonConstants.stateId);
            Log.v(LOG_TAG, "@@@ Statename " + statePair);
            CommonConstants.stateCode = statePair.first.toString();
            CommonConstants.stateName = statePair.second.toString();
            Log.v(LOG_TAG, "@@@ Stateid " + CommonConstants.stateId);
            Log.v(LOG_TAG, "@@@ Statename " + CommonConstants.stateName);

        }

        CommonConstants.CountryName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCountriesQuery(CommonConstants.countryID));

        Log.d("=====CountryName===",CommonConstants.CountryName);

        CountryMap = dataAccessHandler.getPairData(Queries.getInstance().getCountryMasterQuery());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(CountryMap, "Country"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Countryspin.setAdapter(spinnerArrayAdapter);
        if (CommonConstants.CountryName != null) {
            int spinnerPosition1 = spinnerArrayAdapter.getPosition(CommonConstants.CountryName);
            Countryspin.setSelection(spinnerPosition1);
        }



        CommonConstants.stateName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatesQuery(CommonConstants.stateId));
        CommonConstants.districtName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsQuery(CommonConstants.districtId));
        CommonConstants.mandalName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalQuery(CommonConstants.mandalId));
        CommonConstants.villageName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getvillageQuery(CommonConstants.villageId));

        CommonConstants.genderName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getGenderNames(CommonConstants.genderId));
        CommonConstants.categoryName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getGenderNames(CommonConstants.categoryId));
        CommonConstants.educationName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getGenderNames(CommonConstants.educationId));
        CommonConstants.educationQualificationType = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getGenderNames(CommonConstants.getEducationQualificationTypeId));



        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //categortyids = CategoryMap.keySet().toArray(new String[CategoryMap.size()])[i - 1];


                //Log.d("categortyids",categortyids.toString());


                Object categoryid;

                categoryid = getKeysByValue(CategoryMap,spin_category.getSelectedItem().toString());
                categortyids = (categoryid.toString().substring(1, categoryid.toString().length()-1));
                Log.d("==categortyids==",categortyids.toString());


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        CategoryMap = dataAccessHandler.getGenericData(Queries.getInstance().getCategory());
        String[] category = CommonUtils.fromMap(CategoryMap, "Category");

        Log.e("category===========", category[0] + "");
        ArrayAdapter<String> ownerShipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, category);
        ownerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_category.setAdapter(ownerShipAdapter);

        //categortyids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getProfileIds(spin_category.getSelectedItem().toString()));


        if (CommonConstants.categoryName != null) {
            int spinnerPosition5 = ownerShipAdapter.getPosition(CommonConstants.categoryName);
            spin_category.setSelection(spinnerPosition5);
        }


        spin_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //educationids = EducationDataMap.keySet().toArray(new String[EducationDataMap.size()])[i - 1];

                //Log.d("educationids",educationids.toString());

                Object educationid;

                educationid = getKeysByValue(EducationDataMap,spin_education.getSelectedItem().toString());
                educationids = (educationid.toString().substring(1, educationid.toString().length()-1));
                Log.d("==educationid==",educationids.toString());

                if( i == 1)
                {
                    eductionDegreeTypeLayout.setVisibility(View.VISIBLE);
                }else{
                    eductionDegreeTypeLayout.setVisibility(View.GONE);
                }
//                if(educationids.toString() == valueeee.toString()){
//                    eductionDegreeTypeLayout.setVisibility(View.VISIBLE); }
//                else { eductionDegreeTypeLayout.setVisibility(View.GONE); }
//
                EducationDegreeType = dataAccessHandler.getGenericData(Queries.getInstance().geteducationDegreeType());
                String[] educationDegreeType = CommonUtils.fromMap(EducationDegreeType, "EducationDegreeType");
                educationDegreeTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, educationDegreeType);
                educationDegreeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_educationType.setAdapter(educationDegreeTypeAdapter);

                if (CommonConstants.educationQualificationType != null) {
                    int spinnerPosition7 = educationDegreeTypeAdapter.getPosition(CommonConstants.educationQualificationType);
                    spin_educationType.setSelection(spinnerPosition7);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        EducationDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getEducationType());
        String[] Education_type = CommonUtils.fromMap(EducationDataMap, "Education");

        ArrayAdapter<String> EducationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Education_type);
        EducationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_education.setAdapter(EducationAdapter);

        //educationids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getProfileIds(spin_education.getSelectedItem().toString()));


        if (CommonConstants.educationName != null) {
            int spinnerPosition6 = EducationAdapter.getPosition(CommonConstants.educationName);
            spin_education.setSelection(spinnerPosition6);
        }



        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // genderids = genderdatamap.keySet().toArray(new String[genderdatamap.size()])[i - 1];

                // Log.d("GenderId",genderids.toString());

                Object genderid;

                genderid = getKeysByValue(genderdatamap,spin_gender.getSelectedItem().toString());
                genderids = (genderid.toString().substring(1, genderid.toString().length()-1));
                Log.d("==GenderId==",genderids.toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderdatamap = dataAccessHandler.getGenericData(Queries.getInstance().getgender());
        String[] gender = CommonUtils.fromMap(genderdatamap, "Gender");


        genderadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gender);
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_gender.setAdapter(genderadapter);

        //genderids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getProfileIds(spin_gender.getSelectedItem().toString()));


        if (CommonConstants.genderName != null) {
            int spinnerPosition7 = genderadapter.getPosition(CommonConstants.genderName);
            spin_gender.setSelection(spinnerPosition7);
        }

        spin_educationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Object educationDeegreeTypeid;

                educationDeegreeTypeid = getKeysByValue(EducationDegreeType,spin_educationType.getSelectedItem().toString());
                educationDegreeTypeids = (educationDeegreeTypeid.toString().substring(1, educationDeegreeTypeid.toString().length()-1));
                Log.d("educationTypeids",educationDegreeTypeids.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        //  Log.d("====GenderName====", CommonConstants.genderName);
//        countrytxt.setText(CommonConstants.CountryName + "");
//        statetxt.setText(CommonConstants.stateName + "");
//        districttxt.setText(CommonConstants.districtName + "");
//        mandaltxt.setText(CommonConstants.mandalName + "");
//        villagetxt.setText(CommonConstants.villageName + "");
//
//        gendertxt.setText(CommonConstants.genderName + "");
//        categorytxt.setText(CommonConstants.categoryName + "");
//        eductiontxt.setText(CommonConstants.educationName + "");

        return view;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }


    private void intviews() {
        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());
//        gendertxt = view.findViewById(R.id.gender_textView);
//        countrytxt = view.findViewById(R.id.country_textView);
//        statetxt = view.findViewById(R.id.state_textView);
//        districttxt = view.findViewById(R.id.district_textView);
//        mandaltxt = view.findViewById(R.id.mandal_textView);
//        villagetxt = view.findViewById(R.id.village_textView);
//        categorytxt = view.findViewById(R.id.category_textView);
//        eductiontxt = view.findViewById(R.id.education_textView);
//        countrytxt = view.findViewById(R.id.country_textView);

        //submit = view.findViewById(R.id.submit);

        fname_edittxt = view.findViewById(R.id.fname_edittxt);
        father_edittxt = view.findViewById(R.id.father_edittxt);
        address1_edittxt = view.findViewById(R.id.address1_edittxt);
        address2_edittxt = view.findViewById(R.id.address2_edittxt);
//        pincode_edittxt = view.findViewById(R.id.pincode_edittxt);
//        district_edittxt = view.findViewById(R.id.district_edittxt);
        date_edittxt = view.findViewById(R.id.date_edittxt);
        primaryPhone_edittxt = view.findViewById(R.id.primaryPhone_edittxt);
        secondaryPhone_edittxt = view.findViewById(R.id.secondaryPhone_edittxt);
        // income_edittxt = view.findViewById(R.id.income_edittxt);
        email_edittxt = view.findViewById(R.id.email_edittxt);


        eductionDegreeTypeLayout = view.findViewById(R.id.eductionDegreeTypeLayout);
        eductionDegreeTypeLayout.setVisibility(View.GONE);

        firstname_label = view.findViewById(R.id.firstname_label);
        father_label = view.findViewById(R.id.father_label);
        address1_label = view.findViewById(R.id.address1_label);
        address2_label = view.findViewById(R.id.address2_label);
        village_label = view.findViewById(R.id.village_label);
        mandal_label = view.findViewById(R.id.mandal_label);
//        pincode_label = view.findViewById(R.id.pincode_label);
//        district_label = view.findViewById(R.id.district_label);
        state_label = view.findViewById(R.id.state_label);
        date_label = view.findViewById(R.id.date_label);
        primaryPhone_label = view.findViewById(R.id.primaryPhone_label);
        secondaryPhone_label = view.findViewById(R.id.secondaryPhone_label);
        // income_label = view.findViewById(R.id.income_label);
        email_label = view.findViewById(R.id.email_label);

        spin_gender = (Spinner) view.findViewById(R.id.spin_gender);
        spin_category = (Spinner) view.findViewById(R.id.spin_category);
        spin_education = (Spinner) view.findViewById(R.id.spin_education);
        spin_educationType = (Spinner) view.findViewById(R.id.spin_educationtype);



        profileImg = view.findViewById(R.id.profileImgBtn);
        Countryspin = (Spinner) view.findViewById(R.id.spin_Country);
        statespin = (Spinner) view.findViewById(R.id.statespin);
        villageSpin = (Spinner) view.findViewById(R.id.villageSpin);




        // if(CountryMap.)
        //Countryspin.setSelection(1);
        Log.e("CountryMap=======", CountryMap + "");

        districtSpin = (Spinner) view.findViewById(R.id.districtSpin);
        mandalSpin = (Spinner) view.findViewById(R.id.mandalSpin);



    }

    private void setviews() {

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
            }
        });
        farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(value1), 1);

        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        int seconds = dt.getSeconds();

        curTime = hours + ":" + minutes + ":" + seconds;

        List<Farmer> farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(value1), 1);

        //  final DataAccessHandler dataAccessHandler = new DataAccessHandler(getContext());


        String fullname = farmerDetails.get(0).getFirstname() + " " + farmerDetails.get(0).getMiddlename() + " " + farmerDetails.get(0).getLastname();

        fname_edittxt.setText("" + fullname.replace("null", ""));

        father_edittxt.setText(farmerDetails.get(0).getFatherName_GuardianName());
        email_edittxt.setText(farmerDetails.get(0).getEmail());
        address1_edittxt.setText(farmerDetails.get(0).getAddress1());
        address2_edittxt.setText(farmerDetails.get(0).getAddress2());
        if(farmerDetails.get(0).getPPFileLocation()!=null){
            Glide.with(getActivity()).load(farmerDetails.get(0).getPPFileLocation()+ "/" +farmerDetails.get(0).getPPFileName()
                    +farmerDetails.get(0).getPPFileExtension()).into(profileImg);

        }
        else {
            Bitmap bitmap = ImageUtil.convert(farmerDetails.get(0).getProfilePicFileBytes());
            profileImg.setImageBitmap(bitmap);
          //  Glide.with(getActivity()).load(R.drawable.add_image).into(profileImg);
        }

        fname_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(fname_edittxt));
        father_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(father_edittxt));
        address1_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(address1_edittxt));
        address2_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(address2_edittxt));


        date_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(date_edittxt));

        primaryPhone_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(primaryPhone_edittxt));
        secondaryPhone_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(secondaryPhone_edittxt));

        email_edittxt.addTextChangedListener(new convert_personaldetaila_fragment.ValidationTextWatcher(email_edittxt));

        date_edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_edittxt.setText(day + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });






        Countryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (CountryMap != null && CountryMap.size() > 0 && Countryspin.getSelectedItemPosition() != 0) {
                    stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getStatesMasterQuery(CountryMap.keySet().toArray(new String[CountryMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
                    spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    statespin.setAdapter(spinnerArrayAdapter1);


                    if (CommonConstants.stateName != null) {
                        int spinnerPosition = spinnerArrayAdapter1.getPosition(CommonConstants.stateName);
                        statespin.setSelection(spinnerPosition);
                    }
                    //statespin.setSelection(1);

                    // statespin.setPrompt(CommonConstants.stateName);
                }
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (stateDataMap != null && stateDataMap.size() > 0 && statespin.getSelectedItemPosition() != 0) {

                    districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getDistrictQuery(stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
                    spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpin.setAdapter(spinnerDistrictArrayAdapter);

                    stateids = stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1];


                    if (CommonConstants.districtName != null) {
                        int spinnerPosition2 = spinnerDistrictArrayAdapter.getPosition(CommonConstants.districtName);
                        districtSpin.setSelection(spinnerPosition2);
                    }
                    // districtSpin.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        districtSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (districtDataMap != null && districtDataMap.size() > 0 && districtSpin.getSelectedItemPosition() != 0) {
                    mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getMandalsQuery(districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
                    spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mandalSpin.setAdapter(spinnerMandalArrayAdapter);

                    districtids = districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1];


                    //districtids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsIdsQuery(districtSpin.getSelectedItem().toString()));




                    if (CommonConstants.mandalName != null) {
                        int spinnerPosition3 = spinnerMandalArrayAdapter.getPosition(CommonConstants.mandalName);
                        mandalSpin.setSelection(spinnerPosition3);
                    }
                    //mandalSpin.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mandalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mandalDataMap != null && mandalDataMap.size() > 0 && mandalSpin.getSelectedItemPosition() != 0) {
                    villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getVillagesQuery(mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    villageSpin.setAdapter(spinnerArrayAdapter);

                    mandalids = mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1];


                    //mandalids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalIdsQuery(mandalSpin.getSelectedItem().toString()));




                    if (CommonConstants.villageName != null) {
                        int spinnerPosition4 = spinnerArrayAdapter.getPosition(CommonConstants.villageName);
                        villageSpin.setSelection(spinnerPosition4);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        villageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (villagesDataMap != null && villagesDataMap.size() > 0 && villageSpin.getSelectedItemPosition() != 0) {
                    String villageCode = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                    //villgeids = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalIdsQuery(villageSpin.getSelectedItem().toString()));

                    villgeids = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;

    }

    private FloatingActionButton nextBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nextBT = view.findViewById(R.id.nextBT);

    }


    @Override
    public void onResume() {
        super.onResume();
        nextBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {
        Farmer_code = value1;

        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

        Log.d("==UpdatedDate====", timeStamp);

        try {
            date_time = date_edittxt.getText().toString();
            dateofBirth = formatDateFromDateString(DATE_FORMAT2, DATE_FORMAT1, date_time);
            Log.d("DateofBirth", dateofBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        switch (view.getId()) {
            case R.id.nextBT:
                if (validation()) {

//                    LinkedHashMap map= new LinkedHashMap();
//
//
//                    map.put("MutualAgreementFileBytes",null);
//                    map.put("Id", farmerDetails.get(0).getId());
//                    map.put("Code", farmerDetails.get(0).getCode());
//                    map.put("UserId", farmerDetails.get(0).getUserId());
//                    map.put("FirstName", farmerDetails.get(0).getFirstname());
//                    map.put("MiddleName", farmerDetails.get(0).getMiddlename());
//                    map.put("LastName", farmerDetails.get(0).getLastname());
//                    map.put("FatherName_GuardianName", father_edittxt.getText().toString());
//                    map.put("Address1", address1_edittxt.getText().toString());
//                    map.put("Address2", address2_edittxt.getText().toString());
//                    map.put("StateId", stateids);
//                    map.put("DistrictId", districtids);
//                    map.put("MandalId", mandalids);
//                    map.put("VillageId", villgeids);
//                    map.put("GenderTypeId", genderids.toString());
//                    map.put("DOB", dateofBirth);
//                    map.put("PrimaryPhoneNumber", primaryPhone_edittxt.getText().toString());
//                    map.put("SecondaryPhoneNumber", secondaryPhone_edittxt.getText().toString());
//                    map.put("AnnualIncomeTypeId", farmerDetails.get(0).getAnnualincometypeid());
//                    map.put("CategoryTypeId", categortyids.toString());
//                    map.put("Email", email_edittxt.getText().toString());
//                    map.put("EducationTypeId", educationids.toString());
//
//                    if(spin_education.getSelectedItemPosition() == 1){
//
//                        map.put("EducationDegreeTypeId", educationDegreeTypeids.toString());
//
//                    }else {
//                        map.put("EducationDegreeTypeId", -1);
//                    }
//
//
//                    map.put("SourceTypeId", farmerDetails.get(0).getSourceTypeId());
//                    map.put("PPFileName", "P" +farmerDetails.get(0).getCode());
//                    map.put("PPFileLocation", farmerDetails.get(0).getPPFileLocation());
//                    map.put("PPFileExtension", ".jpg");
//                    map.put("MAFileName", farmerDetails.get(0).getMAFileName());
//                    map.put("MAFileLocation", farmerDetails.get(0).getMAFileLocation());
//                    map.put("MAFileExtension", farmerDetails.get(0).getMAFileExtension());
//                    map.put("IsActive", true);
//                    map.put("CreatedByUserId", farmerDetails.get(0).getCreatedbyuserid());
//                    map.put("CreatedDate", farmerDetails.get(0).getCreateddate());
//                    map.put("UpdatedByUserId", farmerDetails.get(0).getUpdatedbyuserid());
//                    map.put("UpdatedDate", timeStamp);
//                    map.put("UserName", fname_edittxt.getText().toString());
//                    map.put("Password", farmerDetails.get(0).getPassword());
//                    map.put("EmployeeTypeId", farmerDetails.get(0).getEmployeeTypeId());
//                    map.put("ReportingManagerId", farmerDetails.get(0).getReportingManagerId());
//                    map.put("ExpInYears", farmerDetails.get(0).getExpInYears());
//                    map.put("ExpInMonths", farmerDetails.get(0).getExpInMonths());
//                    map.put("AccessToken", farmerDetails.get(0).getAccessToken());
//                    map.put("ServerUpdatedStatus", 0);
//                    map.put("IsWillingtoConvert", true);
//                    map.put("ProfilePicFileBytes", "");
//
//                    List<LinkedHashMap> list = new ArrayList<>();
//
//                    list.add(map);
//
//                    Log.d("Query=======","where Code = " + farmerDetails.get(0).getCode());
//
//                    dataAccessHandler.updateData("UserInfo", list, true, " where Code = " + "'"+farmerDetails.get(0).getCode()+"'", new ApplicationThread.OnComplete<String>() {
//                        @Override
//                        public void execute(boolean success, String result, String msg) {
//                            super.execute(success, result, msg);
//                        }
//                    });

                    if (mListener != null)
                        mListener.onNextPressed(this);
                }
                break;
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepOneListener) {
            mListener = (OnStepOneListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        nextBT = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public interface OnStepOneListener {
        int getLayoutResource();

        //void onFragmentInteraction(Uri uri);
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
                case R.id.fname_edittxt:
                    validate_firstname();
                    break;
                case R.id.father_edittxt:
                    validate_fatherName();
                    break;
                case R.id.address1_edittxt:
                    validate_address1();
                    break;
                case R.id.primaryPhone_edittxt:
                    validate_primarymobile();
                    break;
                case R.id.email_edittxt:
                    validate_Email();
                    break;
                case R.id.date_edittxt:
                    validate_dateOfBirth();
                    break;
            }
        }
    }




    private boolean validate_primarymobile() {
        if (TextUtils.isEmpty(primaryPhone_edittxt.getText().toString().trim())) {
            primaryPhone_label.setError(getResources().getString(R.string.err_please_phone));
            requestFocus(primaryPhone_edittxt);

        } else {
            primaryPhone_label.setErrorEnabled(false);
        }
        return true;
    }



    private boolean validate_Email() {
        if (TextUtils.isEmpty(email_edittxt.getText().toString().trim())) {
            email_label.setError(getResources().getString(R.string.err_please_email));
            requestFocus(email_edittxt);
        } else if (!EmailValidator.getInstance().validate(email_edittxt.getText().toString().trim())) {
            email_label.setError(getResources().getString(R.string.err_please_valid_email));
            requestFocus(email_edittxt);

        } else {
            email_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_address1() {
        if (TextUtils.isEmpty(address1_edittxt.getText().toString().trim())) {
            address1_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address1_edittxt);

        } else {
            address1_label.setErrorEnabled(false);
        }
        return true;
    }



    private boolean validate_fatherName() {
        if (TextUtils.isEmpty(father_edittxt.getText().toString())) {
            father_label.setError(getResources().getString(R.string.err_please_L_name));
            requestFocus(father_edittxt);
        } else {
            father_label.setErrorEnabled(false);

        }
        return true;
    }


    private boolean validate_firstname() {
        if (TextUtils.isEmpty(fname_edittxt.getText().toString())) {
            firstname_label.setError(getResources().getString(R.string.err_please_F_name));
            requestFocus(fname_edittxt);
        } else {
            firstname_label.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_dateOfBirth() {
        if (TextUtils.isEmpty(date_edittxt.getText().toString())) {
            date_label.setError(getResources().getString(R.string.err_please_F_name));
            requestFocus(date_edittxt);
        } else {
            date_label.setErrorEnabled(false);

        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validation() {
//        if ( mCurrentPhotoPath == null) {
//
//            UiUtils.showCustomToastMessage("Please Select Profile Image", getActivity(), 1);
//            return false ;
//        }
         if (TextUtils.isEmpty(fname_edittxt.getText().toString().trim())) {
            firstname_label.setError(getResources().getString(R.string.err_please_F_name));
            return false;
        } else if (TextUtils.isEmpty(father_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            father_label.setError("Please Enter Father Name");

            return false;
        } else if (TextUtils.isEmpty(address1_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            address1_label.setError("Please Enter Address1");

            return false;
        }


        else if (TextUtils.isEmpty(date_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            date_label.setError("Please Enter Date of Birth");
            return false;
        }

        else if (TextUtils.isEmpty(primaryPhone_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            primaryPhone_label.setError("Please Enter Primary Phone Number");
            return false;
        }

        else if (TextUtils.isEmpty(email_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            email_label.setError("Please Enter Email");
            return false;
        }

        if (CommonUtils.isEmptySpinner(statespin)) {
            Toast.makeText(getContext(), "Please select state", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtils.isEmptySpinner(districtSpin)) {
            Toast.makeText(getContext(), "Please select district", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtils.isEmptySpinner(mandalSpin)) {
            Toast.makeText(getContext(), "Please select mandal", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtils.isEmptySpinner(villageSpin)) {
            Toast.makeText(getContext(), "Please select village", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonUtils.isEmptySpinner(spin_category)) {
            Toast.makeText(getContext(), "Please select Category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonUtils.isEmptySpinner(spin_education)) {
            Toast.makeText(getContext(), "Please select Education Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(spin_education.getSelectedItemPosition() == 1) {

            if (CommonUtils.isEmptySpinner(spin_educationType)) {
                Toast.makeText(getContext(), "Please select Educational Qualification", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        if (CommonUtils.isEmptySpinner(spin_gender)) {
            Toast.makeText(getContext(), "Please select Gender Type", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
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
                    profileImg.setImageBitmap(bitmap);
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
            profileImg.setImageBitmap(thumbnail);
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
            File f = new File(wallpaperDirectory, "P" +farmerDetails.get(0).getCode() + ".jpg");
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
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String doFileUpload(File f) {


        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //String encoded = Base64.encodeToString(bytes, 0);
        String finalString = android.util.Base64.encodeToString(bytes, 0);


        return finalString;

// Receiving side
    }

    }
