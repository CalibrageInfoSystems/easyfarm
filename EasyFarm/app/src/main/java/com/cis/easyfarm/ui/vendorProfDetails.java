package com.cis.easyfarm.ui;

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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.cis.easyfarm.Objects.VendorDetails;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class vendorProfDetails extends CommonFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    public static String LOG_TAG = vendorProfDetails.class.getSimpleName();

    private EditText name, gstnumber, cinNumber, address1, address2;
    TextInputLayout namelyt, gstnumberlyt, cinNumberlyt, address1lyt, address2lyt;
    Spinner statespin, districtSpin, mandalSpin, villageSpin, spinbuyer,spinVendorCategory;

    LinearLayout buyerImagelyt, buyer1Imagelyt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String value1;
    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;
    Boolean isFarmerHaveImg2 = false;
    Boolean isselectedCamera2 = false;

    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";

    LinearLayout buyerdetails;

    View view;
    DataAccessHandler dataAccessHandler;
    private String mCurrentPhotoPath;
    private String mCurrentPhotoPath1;
    private String extension,extension1;

    ImageView buyerImage, buyerImage1;

    List<Farmer> farmerDetails;

    Boolean isImageCliked = false;


    private int GALLERY = 1, CAMERA = 2,GALLERY1 = 3, CAMERA1 = 4;


    public static String stateids, districtids, mandalids, villgeids, buyerTypeId, vendorCategoryId,vendorcategoryIdd;

    private LinkedHashMap buyerTypeDataMap, vendorCategoryDataMap;
    List<VendorDetails> vendor_Details;


    public  String BuyerTypeId, VendorCateoryId;
    public  String StateId, DistrictId, MandalId, VillgeId;
    public  String StateName, DistrictName, MandalName, VillgeName;
    private LinkedHashMap vendorCategoryDatamap;

    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap = null, villagesDataMap = null, stateDataMap = null;

    private vendorProfDetails.OnStepTwoListener mListener;


    public vendorProfDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendorProfDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static vendorProfDetails newInstance(String param1, String param2) {
        vendorProfDetails fragment = new vendorProfDetails();
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
        view = inflater.inflate(R.layout.fragment_vendor_prof_details, container, false);

        intviews();
        setviews();

        EditVendor activity = (EditVendor) getActivity();

        Bundle results = activity.getMyData();

        value1 = results.getString("bcode");

        intviews();
        setviews();



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

        name = view.findViewById(R.id.name_edittxt);
        namelyt = view.findViewById(R.id.namelyt);

        gstnumber = view.findViewById(R.id.GST_number_edittxt);
        gstnumberlyt = view.findViewById(R.id.GST_Number_label);

        cinNumber = view.findViewById(R.id.CIN_edittxt);
        cinNumberlyt = view.findViewById(R.id.CIN_label);

        address1 = view.findViewById(R.id.et_address1);
        address1lyt = view.findViewById(R.id.lbl_address1);

        address2 = view.findViewById(R.id.et_address2);
        address2lyt = view.findViewById(R.id.lbl_address2);

        buyerImage = view.findViewById(R.id.buyerimage);
        buyerImagelyt = view.findViewById(R.id.buyerimage_lyt);

        buyerImage1 = view.findViewById(R.id.buyer1image);
        buyer1Imagelyt = view.findViewById(R.id.buyer1image_lyt);
        spinVendorCategory = (Spinner) view.findViewById(R.id.vendorCategoryspin);
        statespin = (Spinner) view.findViewById(R.id.statespin);
        //vendorCategoryspin = (Spinner)   view.findViewById(R.id.vendorCategoryspin);
        villageSpin = (Spinner) view.findViewById(R.id.villageSpin);
        districtSpin = (Spinner) view.findViewById(R.id.districtSpin);
        mandalSpin = (Spinner) view.findViewById(R.id.mandalSpin);
        spinbuyer = (Spinner) view.findViewById(R.id.spin_buyerType);
        buyerdetails =(LinearLayout)view.findViewById(R.id.buyerdetails);

    }

    private void setviews() {


        farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getVendorPersonalDetails(value1), 1);

        vendor_Details = (List<VendorDetails>) dataAccessHandler.getVendorDetails(Queries.getInstance().getvendorDetails(value1), 1);
        if( vendor_Details != null && vendor_Details.size()>0) {
            Log.e(LOG_TAG, "Analysis====168" + vendor_Details.get(0).getVendorTypeId());
            BuyerTypeId = vendor_Details.get(0).getVendorTypeId() + "";
            VendorCateoryId = vendor_Details.get(0).getVendorCategoryId() + "";
            StateId = vendor_Details.get(0).getStateId() + "";
            DistrictId = vendor_Details.get(0).getDistrictId() + "";
            MandalId = vendor_Details.get(0).getMandalId() + "";
            VillgeId = vendor_Details.get(0).getVillageId() + "";
            StateName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatesQuery(StateId));
            DistrictName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsQuery(DistrictId));
            MandalName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalQuery(MandalId));
            VillgeName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getvillageQuery(VillgeId));
            if (vendor_Details.get(0).getROCFileBytes() != null) {
                Bitmap bitmap = ImageUtil.convert(vendor_Details.get(0).getROCFileBytes());
                buyerImage1.setImageBitmap(bitmap);
                isFarmerHaveImg = true;

            } else {

                Glide.with(getActivity()).load(R.drawable.add_image).into(buyerImage1);
            }

            if (vendor_Details.get(0).getINCFileBytes() != null) {
                Bitmap bitmap = ImageUtil.convert(vendor_Details.get(0).getINCFileBytes());
                buyerImage.setImageBitmap(bitmap);
                isFarmerHaveImg = true;
                isFarmerHaveImg2 =true;
            } else {

                Glide.with(getActivity()).load(R.drawable.add_image).into(buyerImage);
            }
            name.setText(vendor_Details.get(0).getCompanyName());
            gstnumber.setText(vendor_Details.get(0).getGSTNumber());
            cinNumber.setText(vendor_Details.get(0).getCINNumber());
            address1.setText(vendor_Details.get(0).getAddress1());
            address2.setText(vendor_Details.get(0).getAddress2());
        }
        // StateId, DistrictId, MandalId, VillgeId
        buyerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getbuyerType());
        String[] Buyer_Type = CommonUtils.fromMap(buyerTypeDataMap, "Vendor Type");

        Log.e(LOG_TAG, "Analysis====223" + Buyer_Type[2]);
        ArrayAdapter<String> BuyerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Buyer_Type);
        BuyerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinbuyer.setAdapter(BuyerAdapter);
        int position;
        String Buyertype = dataAccessHandler.getCountValue(Queries.getInstance().getbuyertype(BuyerTypeId));
        Log.d("Buyertype========", Buyertype + "");
        for (int i = 0; i < Buyer_Type.length; i++) {
            Log.e("Buyertype===========", Buyer_Type[i] + "");
            if (Buyer_Type[i].equalsIgnoreCase(Buyertype)) {
                position = i;
                Log.d("position", position + "");
                spinbuyer.setSelection(position);
            }

        }


        spinbuyer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object buyerTypeIdddd;

                buyerTypeIdddd = getKeysByValue(buyerTypeDataMap, spinbuyer.getSelectedItem().toString());
                buyerTypeId = (buyerTypeIdddd.toString().substring(1, buyerTypeIdddd.toString().length() - 1));
                Log.d("==buyerTypeId==", buyerTypeIdddd + "");

                if (spinbuyer.getSelectedItemPosition() == 0) {
                    buyerdetails.setVisibility(View.INVISIBLE);
                }
                if (spinbuyer.getSelectedItemPosition() == 1) {
                    buyerdetails.setVisibility(View.VISIBLE);
                    gstnumberlyt.setVisibility(View.VISIBLE);
                    buyer1Imagelyt.setVisibility(View.VISIBLE);
                    namelyt.setVisibility(View.VISIBLE);
                    cinNumberlyt.setVisibility(View.VISIBLE);
                    buyerImagelyt.setVisibility(View.VISIBLE);

                }


                if (spinbuyer.getSelectedItemPosition() == 3) {
                    buyerdetails.setVisibility(View.VISIBLE);
                    gstnumberlyt.setVisibility(View.GONE);
                    buyer1Imagelyt.setVisibility(View.VISIBLE);
                    namelyt.setVisibility(View.VISIBLE);
                    cinNumberlyt.setVisibility(View.VISIBLE);
                    buyerImagelyt.setVisibility(View.GONE);

                }

                if (spinbuyer.getSelectedItemPosition() == 2) {
                    buyerdetails.setVisibility(View.VISIBLE);
                    namelyt.setVisibility(View.GONE);
                    gstnumberlyt.setVisibility(View.GONE);
                    cinNumberlyt.setVisibility(View.GONE);
                    buyerImagelyt.setVisibility(View.GONE);
                    buyer1Imagelyt.setVisibility(View.GONE);
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        vendorCategoryDatamap = dataAccessHandler.getGenericData(Queries.getInstance().getvendorCategoryType());
        String[] vndorCategory_Type = CommonUtils.fromMap(vendorCategoryDatamap, "Vendor Category");

        Log.e(LOG_TAG, "Analysis====223" + vndorCategory_Type[2]);
        ArrayAdapter<String> VendorCategoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, vndorCategory_Type);
        VendorCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinVendorCategory.setAdapter(VendorCategoryAdapter);
        int position1;
        String VendorCategorytype = dataAccessHandler.getCountValue(Queries.getInstance().getvendorCategorytype(VendorCateoryId));
        Log.d("Buyertype========", VendorCategorytype + "");
        for (int i = 0; i < vndorCategory_Type.length; i++) {
            Log.e("Buyertype===========", vndorCategory_Type[i] + "");
            if (vndorCategory_Type[i].equalsIgnoreCase(VendorCategorytype)) {
                position1 = i;
                Log.d("position1", position1 + "");
                spinVendorCategory.setSelection(position1);
            }

        }

        spinVendorCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object vendorCategoryTypeIdddd;

                vendorCategoryTypeIdddd = getKeysByValue(vendorCategoryDatamap, spinVendorCategory.getSelectedItem().toString());
                vendorCategoryId= (vendorCategoryTypeIdddd.toString().substring(1, vendorCategoryTypeIdddd.toString().length() - 1));
                Log.d("==vendorCategoryId==", vendorCategoryId.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        buyerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getbuyerType());
//        String[] Buyer_Type = CommonUtils.fromMap(buyerTypeDataMap, "Vendor Type");
//        ArrayAdapter<String> BuyerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Buyer_Type);
//        BuyerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinbuyer.setAdapter(BuyerAdapter);
//
//
//        vendorCategoryDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getvendorCategoryType());
//        String[] VendorCategory_Type = CommonUtils.fromMap(vendorCategoryDataMap, "Vendor Category");
//        ArrayAdapter<String> VendorCategoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, VendorCategory_Type);
//        VendorCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        vendorCategoryspin.setAdapter(VendorCategoryAdapter);
//
//
//        spinbuyer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Object buyerTypeIdddd;
//
//                buyerTypeIdddd = getKeysByValue(buyerTypeDataMap, spinbuyer.getSelectedItem().toString());
//                buyerTypeId = (buyerTypeIdddd.toString().substring(1, buyerTypeIdddd.toString().length() - 1));
//                Log.d("==buyerTypeId==", buyerTypeId.toString());
//
//
//                if (spinbuyer.getSelectedItemPosition() == 0) {
//                    buyerdetails.setVisibility(View.INVISIBLE);
//                }
//                if (spinbuyer.getSelectedItemPosition() == 1) {
//                    buyerdetails.setVisibility(View.VISIBLE);
//                    gstnumberlyt.setVisibility(View.VISIBLE);
//                    buyer1Imagelyt.setVisibility(View.VISIBLE);
//                    namelyt.setVisibility(View.VISIBLE);
//                    cinNumberlyt.setVisibility(View.VISIBLE);
//                    buyerImagelyt.setVisibility(View.VISIBLE);
//
//                }
//
//
//                if (spinbuyer.getSelectedItemPosition() == 3) {
//                    buyerdetails.setVisibility(View.VISIBLE);
//                    gstnumberlyt.setVisibility(View.GONE);
//                    buyer1Imagelyt.setVisibility(View.VISIBLE);
//                    namelyt.setVisibility(View.VISIBLE);
//                    cinNumberlyt.setVisibility(View.VISIBLE);
//                    buyerImagelyt.setVisibility(View.GONE);
//
//                }
//
//                if (spinbuyer.getSelectedItemPosition() == 2) {
//                    buyerdetails.setVisibility(View.VISIBLE);
//                    namelyt.setVisibility(View.GONE);
//                    gstnumberlyt.setVisibility(View.GONE);
//                    cinNumberlyt.setVisibility(View.GONE);
//                    buyerImagelyt.setVisibility(View.GONE);
//                    buyer1Imagelyt.setVisibility(View.GONE);
//                }
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
//        vendorCategoryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Object vendorCategoryTypeIdddd;
//
//                vendorCategoryTypeIdddd = getKeysByValue(vendorCategoryDataMap, vendorCategoryspin.getSelectedItem().toString());
//                vendorCategoryId = (vendorCategoryTypeIdddd.toString().substring(1, vendorCategoryTypeIdddd.toString().length() - 1));
//                Log.d("==vendorCategoryId==", vendorCategoryId.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        buyerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isImageCliked = true;
                showPictureDialog();
            }
        });


        buyerImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isImageCliked = false;
                showPictureDialog1();
            }
        });

        name.addTextChangedListener(new ValidationTextWatcher(name));
        gstnumber.addTextChangedListener(new ValidationTextWatcher(gstnumber));
        cinNumber.addTextChangedListener(new ValidationTextWatcher(cinNumber));
        address1.addTextChangedListener(new ValidationTextWatcher(address1));
        address2.addTextChangedListener(new ValidationTextWatcher(address2));


        stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getStatesMasterQuery("1"));
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespin.setAdapter(spinnerArrayAdapter1);

        if (StateName != null) {
            int spinnerPosition = spinnerArrayAdapter1.getPosition(StateName);
            statespin.setSelection(spinnerPosition);
        }

        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (stateDataMap != null && stateDataMap.size() > 0 && statespin.getSelectedItemPosition() != 0) {

                    districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getDistrictQuery(stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
                    spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpin.setAdapter(spinnerDistrictArrayAdapter);

                    stateids = stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1];
                    Log.d("stateids", stateids + "");


                    if (DistrictName != null) {
                        int spinnerPosition2 = spinnerDistrictArrayAdapter.getPosition(DistrictName);
                        districtSpin.setSelection(spinnerPosition2);
                    }
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
                    Log.d("districtids", districtids + "");


                    if (MandalName != null) {
                        int spinnerPosition3 = spinnerMandalArrayAdapter.getPosition(MandalName);
                        mandalSpin.setSelection(spinnerPosition3);
                    }
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
                    Log.d("mandalids", mandalids + "");
//
                    if (VillgeName != null) {
                        int spinnerPosition4 = spinnerArrayAdapter.getPosition(VillgeName);
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

                    villgeids = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                    Log.d("villgeids", villgeids + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof vendorProfDetails.OnStepTwoListener) {
            mListener = (vendorProfDetails.OnStepTwoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private FloatingActionButton backBT;
    private FloatingActionButton nextBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBT = view.findViewById(R.id.backBT);
        nextBT = view.findViewById(R.id.nextBT);
    }

    @Override
    public void onResume() {
        super.onResume();
        backBT.setOnClickListener(this);
        nextBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
        nextBT.setOnClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());


        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;


            case R.id.nextBT:
                if (validation()) {

                    LinkedHashMap map = new LinkedHashMap();

                    map.put("Id", 0);
                    map.put("UserCode", farmerDetails.get(0).getCode());
                    map.put("VendorTypeId", buyerTypeId);
                    map.put("VendorCategoryId", vendorCategoryId);



                    if (spinbuyer.getSelectedItemPosition() == 1) {

                        map.put("GSTNumber", gstnumber.getText().toString());
                        map.put("CompanyName", name.getText().toString());
                        map.put("ROCFileName", "");
                        map.put("ROCFileLocation", "");
                        map.put("ROCFileExtension", extension1);
                        map.put("CINNumber", cinNumber.getText().toString());
                        map.put("INCFileName", "");
                        map.put("INCFileLocation", "");
                        map.put("INCFileExtension", extension);
                    }

                    if (spinbuyer.getSelectedItemPosition() == 3) {

                        map.put("CompanyName", name.getText().toString());
                        map.put("GSTNumber", "");
                        map.put("ROCFileName", "");
                        map.put("ROCFileLocation", "");
                        map.put("ROCFileExtension", extension1);
                        map.put("CINNumber", cinNumber.getText().toString());
                    }


                    map.put("Address1", address1.getText().toString());
                    map.put("Address2", address2.getText().toString());
                    map.put("StateId", stateids);
                    map.put("DistrictId", districtids);
                    map.put("MandalId", mandalids);
                    map.put("VillageId", villgeids);

                    map.put("IsActive", true);
                    map.put("CreatedByUserId", farmerDetails.get(0).getCreatedbyuserid());
                    map.put("CreatedDate", farmerDetails.get(0).getCreateddate());
                    map.put("UpdatedByUserId", 1);
                    map.put("UpdatedDate", timeStamp);
                    map.put("ServerUpdatedStatus", 0);
                    if (!TextUtils.isEmpty(mCurrentPhotoPath))
                        map.put("INCFileBytes", doFileUpload(new File(mCurrentPhotoPath)));

                    if (!TextUtils.isEmpty(mCurrentPhotoPath1))
                        map.put("ROCFileBytes", doFileUpload(new File(mCurrentPhotoPath1)));


                    final List<LinkedHashMap> list = new ArrayList<>();

                    list.add(map);

                    dataAccessHandler.insertMyData("VendorDetails", list, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {
                                Log.v(LOG_TAG, "VendorDetails inserted Successfully");
                                farmerDetails.get(0).setServerupdatedstatus(0);
                                CommonUtils.appendLog(LOG_TAG, "next Button Clicked", "VendorDetails inserted Successfully" );
                            }

                        }
                    });


                    dataAccessHandler.updateFarmerStatus(0, farmerDetails.get(0).getCode(), new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            Log.d("mahesh", "@@@@@ UPdated Status :" + success);
                            CommonUtils.appendLog(LOG_TAG, "next Button Clicked", "Updated Status :" + success);
                        }
                    });


                    if (mListener != null)
                        mListener.onNextPressed(this);
                }
                break;

//        }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnStepTwoListener {
        void onBackPressed(Fragment fragment);

        void onNextPressed(Fragment fragment);
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
    private void showPictureDialog1() {
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
                                choosePhotoFromGallary1();
                                break;
                            case 1:
                                takePhotoFromCamera1();
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
    public void choosePhotoFromGallary1() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY1);
    }

    private void takePhotoFromCamera1() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA1);
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
                    buyerImage.setImageBitmap(bitmap);
                    isFarmerHaveImg = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    CommonUtils.appendLog(LOG_TAG, "VPPImageOnactivityResult", e.getLocalizedMessage() +"" );
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            buyerImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            isselectedCamera = true;
        }

        if (requestCode == GALLERY1) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage2(bitmap);
                    Log.e("path===", path);
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    buyerImage1.setImageBitmap(bitmap);

                    isFarmerHaveImg2 = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    CommonUtils.appendLog(LOG_TAG, "VPPImageOnactivityResult1", e.getLocalizedMessage() +"" );

                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA1) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            buyerImage1.setImageBitmap(thumbnail);
            saveImage2(thumbnail);
            isselectedCamera2 = true;
        }


//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    String path1 = saveImage(bitmap);
//
//                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//
//                    if (isImageCliked == true) {
//                        isFarmerHaveImg = true;
//                        buyerImage.setImageBitmap(bitmap);
//                    } else if (isImageCliked == false) {
//                        isFarmerHaveImg2 =true;
//                        buyerImage1.setImageBitmap(bitmap);
//                    }
//                    // profileImgBtn.add(bitmap);
//                    // displayImages();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            saveImage(thumbnail);
//
//            Bitmap thumbnail1 = (Bitmap) data.getExtras().get("data");
//            saveImage1(thumbnail1);
//
//            if (isImageCliked == true) {
//                buyerImage.setImageBitmap(thumbnail);
//                isselectedCamera = true;
//            } else if (isImageCliked == false) {
//
//                buyerImage1.setImageBitmap(thumbnail1);
//                isselectedCamera2 = true;
//            }


        // profileImgBtn.add(thumbnail);
        //  saveImage(thumbnail);
        //displayImages();
        //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            mCurrentPhotoPath = f.getAbsolutePath();
            extension = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("."));
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "VPPsaveImage", e1.getLocalizedMessage() +"" );
        }
        return "";
    }

    public String saveImage2(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            mCurrentPhotoPath1 = f.getAbsolutePath();
            extension1 = mCurrentPhotoPath1.substring(mCurrentPhotoPath1.lastIndexOf("."));
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "VPPsaveImage2", e1.getLocalizedMessage() +"" );

        }
        return "";
    }
    private String doFileUpload(File f) {


        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "VPPDoploadFile", ex.getLocalizedMessage() +"" );
        }

        //String encoded = Base64.encodeToString(bytes, 0);
        String finalString = android.util.Base64.encodeToString(bytes, 0);


        return finalString;

// Receiving side

    }

    public String saveImage1(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, "P" + farmerDetails.get(0).getCode() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            mCurrentPhotoPath1 = f.getAbsolutePath();
            Log.e("PhotoPath===========", mCurrentPhotoPath1);

            extension = mCurrentPhotoPath1.substring(mCurrentPhotoPath1.lastIndexOf("."));
            Log.e("Extension===========", extension);


            Log.e("PhotoPath===========", doFileUpload1(new File(mCurrentPhotoPath1)));
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String doFileUpload1(File f) {


        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "VPPDoploadFile", ex.getLocalizedMessage() +"" );
        }

        //String encoded = Base64.encodeToString(bytes, 0);
        String finalString = android.util.Base64.encodeToString(bytes, 0);


        return finalString;

// Receiving side

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
                case R.id.name_edittxt:
                    validate_name();
                    break;
                case R.id.GST_number_edittxt:
                    validate_gst();
                    break;
                case R.id.et_address1:
                    validate_address1();
                    break;
                case R.id.CIN_edittxt:
                    validate_CIN();
                    break;
            }
        }
    }


    private boolean validate_name() {
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            namelyt.setError("Please Enter Company/Association Name");
            requestFocus(name);

        } else {
            namelyt.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_address1() {
        if (TextUtils.isEmpty(address1.getText().toString().trim())) {
            address1lyt.setError("Please Enter Address1");
            requestFocus(address1);

        } else {
            address1lyt.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_address2() {
        if (TextUtils.isEmpty(address2.getText().toString())) {
            address2lyt.setError("Please Enter Address2");
            requestFocus(address2);
        } else {
            address2lyt.setErrorEnabled(false);

        }
        return true;
    }


    private boolean validate_gst() {
        if (TextUtils.isEmpty(gstnumber.getText().toString())) {
            gstnumberlyt.setError("Please Enter GST Number");
            requestFocus(gstnumber);
        } else {
            gstnumberlyt.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_CIN() {
        if (TextUtils.isEmpty(cinNumber.getText().toString())) {
            cinNumberlyt.setError("Please Enter CIN/ROC Number");
            requestFocus(cinNumber);
        } else {
            cinNumberlyt.setErrorEnabled(false);

        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validation() {
        if (CommonUtils.isEmptySpinner(spinbuyer)) {
            Toast.makeText(getContext(), "Please select Vendor Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (CommonUtils.isEmptySpinner(spinVendorCategory)) {
            Toast.makeText(getContext(), "Please select Vendor Category", Toast.LENGTH_SHORT).show();
            return false;
        }



        if (spinbuyer.getSelectedItemPosition() == 1) {

            if (TextUtils.isEmpty(name.getText().toString().trim())) {
                namelyt.setError("Please Enter Company Name");
                return false;
            } else if (TextUtils.isEmpty(gstnumber.getText().toString().trim())) {
                gstnumberlyt.setError("Please Enter GST Number");
                return false;
            } else if (TextUtils.isEmpty(cinNumber.getText().toString().trim())) {
                cinNumberlyt.setError("Please Enter CIN Number");
                return false;
            }

        }

        if (spinbuyer.getSelectedItemPosition() == 3) {

            if (TextUtils.isEmpty(name.getText().toString().trim())) {
                namelyt.setError("Please Enter Association Name");
                return false;
            } else if (TextUtils.isEmpty(cinNumber.getText().toString().trim())) {
                cinNumberlyt.setError("Please Enter ROC Number");
                return false;
            } else if (TextUtils.isEmpty(address1.getText().toString().trim())) {
                //  passwordEdt.setError(getString(R.string.err_please_enter_password));
                address1lyt.setError("Please Enter Address1");

                return false;
            }

        } else if (TextUtils.isEmpty(address1.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            address1lyt.setError("Please Enter Address1");

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

        if (CommonUtils.isEmptySpinner(spinbuyer)) {
            Toast.makeText(getContext(), "Please select Buyer Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinbuyer.getSelectedItemPosition() == 1) {
            if (isFarmerHaveImg == false && isselectedCamera == false) {
                UiUtils.showCustomToastMessage("Please Select Incorporation Certificate", getActivity(), 1);
                return false;
            }
            if (isFarmerHaveImg2 == false && isselectedCamera2 == false) {
                UiUtils.showCustomToastMessage("Please SelectROC Certificate", getActivity(), 1);
                return false;
            }
        }
        if (spinbuyer.getSelectedItemPosition() == 3) {
            if (isFarmerHaveImg2 == false && isselectedCamera2 == false) {
                UiUtils.showCustomToastMessage("Please SelectROC Certificate", getActivity(), 1);
                return false;
            }

        }

        return true;
    }
}
