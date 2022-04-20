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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cis.easyfarm.Objects.BankDetails;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_converted_Farmer;
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

import static com.cis.easyfarm.Fragments.Adduser_fragment.getKeysByValue;


public class Bank_details_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String LOG_TAG = Bank_details_Fragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private LinkedHashMap bankNames, branchNames, ifsccodes;
    public static Object bankids, branchids, ifsc;
    public static String branchNameeee;

    View view;
    private EditText accountholdername_edittxt, accountnumber_edittxt, branchname_edittxt, ifsc_edittxt;
    Spinner spin_bankName, spin_branchName;
    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    TextInputLayout accountholdername_label, accountnumber_label, bankname_label, branchname_label, ifsc_label;
    ImageView bank_profile;
    private String mCurrentPhotoPath;
    List<Farmer> farmerDetails;
    String farmerCode;
    List<BankDetails> bankDetails;
    private int GALLERY = 1, CAMERA = 2;
    private OnStepThreeListener mListener;
    Object bankid;
    Object branchid;

    DataAccessHandler dataAccessHandler;
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";


    public Bank_details_Fragment() {
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
    public static Bank_details_Fragment newInstance(String param1, String param2) {
        Bank_details_Fragment fragment = new Bank_details_Fragment();
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

        view = inflater.inflate(R.layout.fragment_adduser_thirdpage, container, false);

        intviews();
        setviews();

        spin_bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String  select_bankname = spin_bankName.getItemAtPosition(spin_bankName.getSelectedItemPosition()).toString();

                //categortyids = CategoryMap.keySet().toArray(new String[CategoryMap.size()])[i - 1];


                //Log.d("categortyids",categortyids.toString());


                bankid = getKeysByValue(bankNames, spin_bankName.getSelectedItem().toString());
                bankids = (bankid.toString().substring(1, bankid.toString().length() - 1));
                Log.d("==bankids==", bankids.toString());

                String bankiddd = bankids.toString();

                Log.d("Bankkkid", bankiddd);

                branchNames = dataAccessHandler.getGenericData(Queries.getInstance().getBranchNames(bankiddd));

                Log.d("branchQuery", branchNames + "");
                String[] branch = CommonUtils.fromMap(branchNames, "Branch");


                ArrayAdapter<String> ownerShipAdapterr = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, branch);
                ownerShipAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_branchName.setAdapter(ownerShipAdapterr);
               if(bankDetails.size()!=0) {
                   CommonConstants.bankTypeId = bankDetails.get(0).getBankId() + "";
                   Log.d("bankid", CommonConstants.bankTypeId + "");
               }
                int position;
                String branchname = dataAccessHandler.getCountValue(Queries.getInstance().getbanchname(CommonConstants.bankTypeId));
                Log.d("branchname========",  branchname + "");
                for(int ii =0; ii<branch.length; ii++ )
                {
                    Log.e("branch===========", branch[ii] + "");
                    if(branch[ii].equalsIgnoreCase(branchname))
                    {
                        position =ii;
                        Log.d("position",  position+"");
                        spin_branchName.setSelection(position);
                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bankNames = dataAccessHandler.getGenericData(Queries.getInstance().getbankNames());
        String[] banks = CommonUtils.fromMap(bankNames, "Bank");


        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, banks);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_bankName.setAdapter(bankAdapter);
        if(bankDetails.size()!=0) {
             CommonConstants.bankTypeId = bankDetails.get(0).getBankId() + "";
        }
        Log.d("bankid",  CommonConstants.bankTypeId + "");
        int position;
        String Bankname = dataAccessHandler.getCountValue(Queries.getInstance().getBankname(CommonConstants.bankTypeId));
        Log.d("Bankname========",  Bankname + "");
        for(int i =0; i<banks.length; i++ )
        {
            Log.e("banks===========", banks[i] + "");
            if(banks[i].equalsIgnoreCase(Bankname ))
        {
            position =i;
            Log.d("position",  position+"");
            spin_bankName.setSelection(position);
        }
        }




        spin_branchName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object branchid, ifsccode;

                branchid = getKeysByValue(branchNames, spin_branchName.getSelectedItem().toString());

                Log.e("branchid======", branchid + "");
                branchids = (branchid.toString().substring(1, branchid.toString().length() - 1));
                ifsccodes = dataAccessHandler.getGenericData(Queries.getInstance().getIfscCodes(branchids + ""));

                ifsccode = getKeysByValue(ifsccodes, spin_branchName.getSelectedItem().toString());

                ifsc = (ifsccode.toString().substring(1, ifsccode.toString().length() - 1));
                Log.e("ifsccodess======1", ifsc + "");
                Log.e("ifsccodess======2", ifsccode + "");

                ifsc_edittxt.setText(ifsc + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;

    }

    private void intviews() {

        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());
        accountholdername_edittxt = view.findViewById(R.id.Accountname_edittxt);
        accountnumber_edittxt = view.findViewById(R.id.Act_number_edittxt);
        spin_bankName = view.findViewById(R.id.spin_bankName);
        spin_branchName = view.findViewById(R.id.spin_branchName);
        ifsc_edittxt = view.findViewById(R.id.ifsc_edittxt);
        accountholdername_label = view.findViewById(R.id.Account_name_label);
        accountnumber_label = view.findViewById(R.id.Account_Number_label);
        ifsc_label = view.findViewById(R.id.IFSC_Code_label);

        bank_profile = view.findViewById(R.id.img_profile);

    }

    private void setviews() {

        Add_converted_Farmer activity = (Add_converted_Farmer) getActivity();

        Bundle results = activity.getMyData();

        farmerCode = results.getString("val1");


        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());

        bankDetails = (List<BankDetails>) dataAccessHandler.getFarmerBankData(Queries.getInstance().getFarmerbankDetails(farmerCode), 1);
        Log.e("bankDetails====",bankDetails.size()+"");
        if( bankDetails.size()==0) {

            accountholdername_edittxt.setText("");
            accountnumber_edittxt.setText("");
            Glide.with(getActivity()).load(R.drawable.add_image).into(bank_profile);


            // CommonConstants.bankTypeId =bankDetails.get(0).getban()+"";
        }
        else {
            accountholdername_edittxt.setText(bankDetails.get(0).getAccountHolderName());
            accountnumber_edittxt.setText(bankDetails.get(0).getAccountNumber());
            Glide.with(getActivity()).load(bankDetails.get(0).getFileLocation() + "/" + bankDetails.get(0).getFileName()
                    + bankDetails.get(0).getFileExtension()).into(bank_profile);


        }


    farmerDetails =(List<Farmer>)dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(farmerCode), 1);
        accountholdername_edittxt.addTextChangedListener(new Bank_details_Fragment.ValidationTextWatcher(accountholdername_edittxt));
        accountnumber_edittxt.addTextChangedListener(new Bank_details_Fragment.ValidationTextWatcher(accountnumber_edittxt));
        ifsc_edittxt.addTextChangedListener(new Bank_details_Fragment.ValidationTextWatcher(ifsc_edittxt));


        bank_profile.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

        showPictureDialog();
    }
    });

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

    private void choosePhotoFromGallary2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 3);
    }
    private void takePhotoFromCamera2() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 4);
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
                    bank_profile.setImageBitmap(bitmap);
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
            bank_profile.setImageBitmap(thumbnail);
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
            File f = new File(wallpaperDirectory, "BP"+farmerDetails.get(0).getCode() + ".jpg");
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

    private FloatingActionButton backBT;
    private FloatingActionButton nextBT;
    Button geo_boundaries;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
        //  geo_boundaries = view.findViewById(R.id.geo_boundaries);
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
//        geo_boundaries.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;

            case R.id.nextBT:

//                String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
//
//
//                LinkedHashMap map = new LinkedHashMap();
//
//                map.put("BankPassbookFileBytes", "");
//                map.put("Id", 0);
//                map.put("BankId", bankids);
//                map.put("BranchName", spin_bankName.getSelectedItem());
//                map.put("AccountHolderName", accountholdername_edittxt.getText().toString());
//                map.put("AccountNumber", accountnumber_edittxt.getText().toString());
//                map.put("FileName", "");
//                map.put("FileLocation", "");
//                map.put("FileExtension", "");
//                map.put("IsActive", true);
//                map.put("CreatedByUserId", farmerDetails.get(0).getCreatedbyuserid());
//                map.put("CreatedDate", timeStamp);
//                map.put("UpdatedByUserId", farmerDetails.get(0).getUpdatedbyuserid());
//                map.put("UpdatedDate", timeStamp);
//                map.put("UserCode", farmerDetails.get(0).getCode());
//                map.put("BPFileLocation", "");
//                map.put("BPFileExtension", ".jpg");
//                map.put("BPFileName", "BP" + farmerDetails.get(0).getCode());
//                map.put("FileBytes", doFileUpload(new File(mCurrentPhotoPath)));
//                map.put("ServerUpdatedStatus", 0);
//
//                final List<LinkedHashMap> list = new ArrayList<>();
//
//                list.add(map);
//
//                dataAccessHandler.insertData("bankDetails", list, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//
//                        if (success) {
//                            Log.v(LOG_TAG, "Bank Details inserted Successfully");
//                        }
//
//                    }
//                });
                if (mListener != null)
                    mListener.onNextPressed(this);




        break;
    }
    }

    private boolean validation() {
//        if ( mCurrentPhotoPath == null) {
//
//            UiUtils.showCustomToastMessage("Please Select Bank Passbook Image", getActivity(), 1);
//            return false ;
//        }
//        else
//
        if (TextUtils.isEmpty(accountholdername_edittxt.getText().toString().trim())) {
            accountholdername_label.setError("Please Enter Account Holder Name");
            return false;
        } else if (TextUtils.isEmpty(accountnumber_edittxt.getText().toString().trim())) {
            accountnumber_label.setError("Please Enter Account Number");

            return false;
        }

        if (CommonUtils.isEmptySpinner(spin_bankName)) {
            Toast.makeText(getContext(), "Please Select Bank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtils.isEmptySpinner(spin_branchName)) {
            Toast.makeText(getContext(), "Please Select Branch", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepThreeListener) {
            mListener = (OnStepThreeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        backBT=null;
        nextBT=null;
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
    public interface OnStepThreeListener {
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
                case R.id.Accountname_edittxt:
                    validate_Accountname();
                    break;
                case R.id.Act_number_edittxt:
                    validate_accountnumber();
                    break;

            }
        }

        private boolean validate_accountnumber() {
            if (TextUtils.isEmpty(accountnumber_edittxt.getText().toString())) {
                accountnumber_label.setError("Please Enter Account Number");
                requestFocus(accountnumber_edittxt);
            } else {
                accountnumber_label.setErrorEnabled(false);

            }
            return true;
        }


        private boolean validate_Accountname() {
            if (TextUtils.isEmpty(accountholdername_edittxt.getText().toString())) {
                accountholdername_label.setError("Please Enter Account Holder Name");
                requestFocus(accountholdername_edittxt);
            } else {
                accountholdername_label.setErrorEnabled(false);

            }
            return true;
        } private boolean validate_branchname() {
            if (TextUtils.isEmpty(branchname_edittxt.getText().toString())) {
                branchname_label.setError("Please Enter Branch Name");

                requestFocus(branchname_edittxt);
            } else {
                branchname_label.setErrorEnabled(false);

            }
            return true;
        }
        private boolean validate_ifsc() {
            if (TextUtils.isEmpty(ifsc_edittxt.getText().toString())) {
                ifsc_label.setError("Please Enter IFSC Code");
                requestFocus(ifsc_edittxt);
            } else {
                ifsc_label.setErrorEnabled(false);

            }
            return true;
        }

        private void requestFocus(View view) {
            if (view.requestFocus()) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    }
}