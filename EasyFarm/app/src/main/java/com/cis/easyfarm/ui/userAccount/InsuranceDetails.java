package com.cis.easyfarm.ui.userAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
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

import com.cis.easyfarm.Fragments.Adduser_fragment;
import com.cis.easyfarm.Objects.InsuranceDetailsObject;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.PlotCropCycleObject;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DataSavingHelper;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.EmailValidator;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InsuranceDetails extends AppCompatActivity {

    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;

    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT2 = "dd-MM-yyyy";
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";

    final Calendar myCalendar = Calendar.getInstance();

        private int GALLERY = 1, CAMERA = 2;
    public static String First_name;
    private EditText policyName, policyNumber, startdate, enddate, sumIssued, premiumamount, fpercentage, vfpercentagem, comments;
    private TextInputLayout lblpolicyName, lblpolicyNumber, lblstartdate, lblenddate, lblsumIssued, lblpremiumamount, lblfpercentage, lblvfpercentagem, lblcomments;;


    ImageView insuranceImg;
    private Spinner spin_insuranceprovider;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    private Context context;
    public String startdateStr, startDateformatted;
    public String enddateStr, endDateformatted;

    private List<Plot> plotList = new ArrayList<>();
    private String cropcyclecode;
    private int insuranceStatusID;
    private List<PlotCropCycleObject> plotCropCycleObject = new ArrayList<>();
    private List<InsuranceDetailsObject> insuranceDetails = new ArrayList<>();



    public static ArrayAdapter<String> insuranceadapter;


    String curTime ,date_time, dateofbirthconvert;

    private String mCurrentPhotoPath;
    private String extension;

    Button save;
    public static String insuranceproviderId;

    public static Object insuranceproviderIds;

    private static final String LOG_TAG = InsuranceDetails.class.getName();

    public static ArrayAdapter<String> insuranceProviderAdapter;

    private static final String IMAGE_DIRECTORY = "/EasyFarm_Insurance";

    private LinkedHashMap InsuranceMap;
    private DataAccessHandler dataAccessHandler;

    Boolean isStartDate = false;
    Boolean isEndDate = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        context = getApplicationContext();
        setviews();

        String plotCode;

        Intent i = getIntent();
        plotCode = i.getStringExtra("Plotcode");
        Log.d("Plotcode", plotCode);

       dataAccessHandler = new DataAccessHandler(context);

        plotList = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getPPlotdetails(plotCode), 1);
        plotCropCycleObject = (List<PlotCropCycleObject>) dataAccessHandler.getPlotCropCycle(Queries.getInstance().getCropCycleCode(plotCode), 1);

        Log.d("PlotStatusIddd", plotList.get(0).getPlotStatusId() + "");
        Log.d("CropCycleCode", plotCropCycleObject.get(0).getCycleCode());

        cropcyclecode = plotCropCycleObject.get(0).getCycleCode();

//        insuranceDetails = (List<InsuranceDetailsObject>) dataAccessHandler.getInsuranceDetails(Queries.getInstance().getInsuranceStatus(cropcyclecode), 1);
//        Log.d("InsuranceStatus", insuranceDetails.get(0).getStatusTypeId() + "");
//
//        insuranceStatusID = insuranceDetails.get(0).getStatusTypeId();

       spin_insuranceprovider =  findViewById(R.id.insuranceproviderSpin);

        policyName =  findViewById(R.id.policyName_edittxt);
        policyNumber =  findViewById(R.id.policyNumber_edittxt);
        startdate =  findViewById(R.id.startdate_edittxt);
        enddate =  findViewById(R.id.enddate_edittxt);
        sumIssued =  findViewById(R.id.sumissued_edittxt);
        premiumamount =  findViewById(R.id.premium_edittxt);
        fpercentage =  findViewById(R.id.farmerperc_edittxt);
        vfpercentagem =  findViewById(R.id.vfperc_edittxt);
        comments =  findViewById(R.id.comments_edittxt);
        save = findViewById(R.id.saveButton);
        insuranceImg = findViewById(R.id.insuranceimg);

        lblpolicyName =  findViewById(R.id.policyName_label);
        lblpolicyNumber =  findViewById(R.id.policyNumber_label);
        lblstartdate =  findViewById(R.id.startdate_label);
        lblenddate =  findViewById(R.id.enddate_label);
        lblsumIssued =  findViewById(R.id.sumissued_label);
        lblpremiumamount =  findViewById(R.id.premium_label);
        lblfpercentage =  findViewById(R.id.farmerperc_label);
        lblvfpercentagem =  findViewById(R.id.vfperc_label);
        lblcomments =  findViewById(R.id.comments_label);

        if (plotList.get(0).getPlotStatusId() == 180){

            fpercentage.setText("100");
            fpercentage.setEnabled(false);
        }

        if (plotList.get(0).getPlotStatusId() == 181){

            lblvfpercentagem.setVisibility(View.VISIBLE);
        }


        insuranceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });


//       startdate.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Log.d("StartDate", "Clicked");
//           }
//       });
//
//       enddate.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Log.d("EndDate", "Clicked");
//
//           }
//       });

        policyName.addTextChangedListener(new ValidationTextWatcher(policyName));
        policyNumber.addTextChangedListener(new ValidationTextWatcher(policyNumber));
        startdate.addTextChangedListener(new ValidationTextWatcher(startdate));
        enddate.addTextChangedListener(new ValidationTextWatcher(enddate));
        sumIssued.addTextChangedListener(new ValidationTextWatcher(sumIssued));
        premiumamount.addTextChangedListener(new ValidationTextWatcher(premiumamount));
        fpercentage.addTextChangedListener(new ValidationTextWatcher(fpercentage));
        vfpercentagem.addTextChangedListener(new ValidationTextWatcher(vfpercentagem));


        InsuranceMap = dataAccessHandler.getGenericData(Queries.getInstance().getInsuranceProviderQuery());
        String[] insuranceproviders = CommonUtils.fromMap(InsuranceMap, "InsuranceProvider");
        insuranceadapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, insuranceproviders);
        insuranceadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_insuranceprovider.setAdapter(insuranceadapter);

        spin_insuranceprovider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (InsuranceMap != null && InsuranceMap.size() > 0 && spin_insuranceprovider.getSelectedItemPosition() != 0) {

                    Object insuranceproviderIdddd;

                    insuranceproviderIdddd = getKeysByValue(InsuranceMap,spin_insuranceprovider.getSelectedItem().toString());
                    insuranceproviderId = (insuranceproviderIdddd.toString().substring(1, insuranceproviderIdddd.toString().length()-1));
                    Log.d("insuranceproviderId",insuranceproviderId.toString());
                }
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (isStartDate == true) {
                    updatestartdateLabel();
                }else {
                    updateenddateLabel();
                }
            }

        };

        startdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isStartDate = true;
                isEndDate = false;

                // TODO Auto-generated method stub
                new DatePickerDialog(InsuranceDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        enddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                isStartDate = false;
                isEndDate = true;

                new DatePickerDialog(InsuranceDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
                startdateStr = startdate.getText().toString();
                enddateStr = enddate.getText().toString();
                try {
                    startDateformatted = formatDateFromDateString(DATE_FORMAT2, DATE_FORMAT1, startdateStr);
                    endDateformatted  = formatDateFromDateString(DATE_FORMAT2, DATE_FORMAT1, enddateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (validation()){

                    LinkedHashMap map = new LinkedHashMap();

                    map.put("Id", 0);
                    map.put("CropCycleCode", cropcyclecode);
                    map.put("ProviderId", insuranceproviderId);
                    map.put("InsurancePlan", policyName.getText().toString());
                    map.put("StartDate", startDateformatted);
                    map.put("EndDate", endDateformatted);
                    map.put("SumIssued", sumIssued.getText().toString());
                    map.put("PremiumAmount", premiumamount.getText().toString());
                    map.put("FarmerPencentage",fpercentage.getText().toString());
                    map.put("VFarmerPencentage", vfpercentagem.getText().toString());
                    map.put("BondNumber", policyNumber.getText().toString());
                    map.put("FileName", "");
                    map.put("FileLocation", "");
                    map.put("FileExtention", extension);
                    map.put("StatusTypeId", 182);
                    map.put("Comments", comments.getText().toString());
                    map.put("StatusChangedBy", SyncHomeActivity.User_id);
                    map.put("StatusChangedDate", timeStamp);
                    map.put("IsActive", true);
                    map.put("CreatedByUserId", SyncHomeActivity.User_id);
                    map.put("CreatedDate", timeStamp);
                    map.put("UpdatedByUserId",  SyncHomeActivity.User_id);
                    map.put("UpdatedDate", timeStamp);

                    if(isselectedCamera == true)
                    {
                        map.put("FileBytes", doFileUpload(new File(mCurrentPhotoPath)));
                    }
                    map.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> list = new ArrayList<>();

                    list.add(map);
                    DataSavingHelper.saveRecordIntoActivityLog(context, CommonConstants.Bank_Details_Added);

                    dataAccessHandler.insertMyData("InsuranceDetails", list, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {
                                Log.v(LOG_TAG, "InsuranceDetails inserted Successfully");
                                Toast.makeText(InsuranceDetails.this, "Insurace Details Added Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InsuranceDetails.this, SyncHomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                InsuranceDetails.this.finish();
                                CommonUtils.appendLog(LOG_TAG, "InsuranceDetails", "Inserted Successfully");
                            }

                        }
                    });

                }
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

    private void updatestartdateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateenddateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        enddate.setText(sdf.format(myCalendar.getTime()));
    }

    private void setviews() {




//        InsuranceMap = dataAccessHandler.getPairData(Queries.getInstance().getInsuranceProviderQuery());
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(InsuranceMap, "Insurance Provider"));
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin_insuranceprovider.setAdapter(spinnerArrayAdapter);



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
                case R.id.policyName_edittxt:
                    validate_policyName();
                    break;
                case R.id.policyNumber_edittxt:
                    validate_policyNumber();
                    break;
                case R.id.startdate_edittxt:
                    validate_startDate();
                    break;
                case R.id.enddate_edittxt:
                    validate_enddate();
                    break;
                case R.id.sumissued_edittxt:
                    validate_sumIssued();
                    break;
                case R.id.premium_edittxt:
                    validate_premiumAmount();
                    break;
                case R.id.farmerperc_edittxt:
                    validate_famerpercentage();
                    break;
                case R.id.vfperc_edittxt:
                    validate_vfamerpercentage();
                    break;
            }
        }
    }




    private boolean validate_policyName() {
        if (TextUtils.isEmpty(policyName.getText().toString().trim())) {
            lblpolicyName.setError("Please Enter Policy Name");
            requestFocus(policyName);

        } else {
            lblpolicyName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_policyNumber() {
        if (TextUtils.isEmpty(policyNumber.getText().toString().trim())) {
            lblpolicyNumber.setError("Please Enter Policy Number");
            requestFocus(policyNumber);

        } else {
            lblpolicyNumber.setErrorEnabled(false);
        }
        return true;
    }



    private boolean validate_startDate() {
        if (TextUtils.isEmpty(startdate.getText().toString().trim())) {
            lblstartdate.setError("Please Enter Start Date");
            requestFocus(startdate);

        } else {
            lblstartdate.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_enddate() {
        if (TextUtils.isEmpty(enddate.getText().toString().trim())) {
            lblenddate.setError("Please Enter End Date");
            requestFocus(enddate);

        } else {
            lblenddate.setErrorEnabled(false);
        }
        return true;
    }



    private boolean validate_sumIssued() {
        if (TextUtils.isEmpty(sumIssued.getText().toString())) {
            lblsumIssued.setError("Please Enter Sum Insured");
            requestFocus(sumIssued);
        } else {
            lblsumIssued.setErrorEnabled(false);

        }
        return true;
    }


    private boolean validate_premiumAmount() {
        if (TextUtils.isEmpty(premiumamount.getText().toString())) {
            lblpremiumamount.setError("Please Enter Premium Amount");
            requestFocus(premiumamount);
        } else {
            lblpremiumamount.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_famerpercentage() {
        if (TextUtils.isEmpty(fpercentage.getText().toString())) {
            lblfpercentage.setError("Please Enter Farmer percentage");
            requestFocus(fpercentage);
        } else {
            lblfpercentage.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_vfamerpercentage() {
        if (TextUtils.isEmpty(vfpercentagem.getText().toString())) {
            lblvfpercentagem.setError("Please Enter Virtual Farmer Percentage");
            requestFocus(vfpercentagem);
        } else {
            lblvfpercentagem.setErrorEnabled(false);

        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validation() {


        if (isFarmerHaveImg == false && isselectedCamera == false) {
            UiUtils.showCustomToastMessage("Please Upload Insurance Bond Image", InsuranceDetails.this, 1);
            return false;
        }

        if (TextUtils.isEmpty(policyName.getText().toString().trim())) {
            lblpolicyName.setError("Please Enter Policy Name");
            return false;
        } else if (TextUtils.isEmpty(policyNumber.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lblpolicyNumber.setError("Please Enter Policy Number");

            return false;
        } else if (TextUtils.isEmpty(startdate.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lblstartdate.setError("Please Enter Start Date");

            return false;
        }


        else if (TextUtils.isEmpty(enddate.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lblenddate.setError("Please Enter End Date");
            return false;
        }

        else if (TextUtils.isEmpty(sumIssued.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lblsumIssued.setError("Please Enter Sum Insured");
            return false;
        }

        else if (TextUtils.isEmpty(premiumamount.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lblpremiumamount.setError("Please Premium Amount");
            return false;
        }

        if (plotList.get(0).getPlotStatusId() == 180) {

            if (TextUtils.isEmpty(fpercentage.getText().toString().trim())) {
                //  passwordEdt.setError(getString(R.string.err_please_enter_password));
                lblfpercentage.setError("Please Enter Farmer Percentage");
                return false;
            }
        }

        if (plotList.get(0).getPlotStatusId() == 181) {

            if (TextUtils.isEmpty(fpercentage.getText().toString().trim())) {
                //  passwordEdt.setError(getString(R.string.err_please_enter_password));
                lblfpercentage.setError("Please Enter Farmer Percentage");
                return false;
            } else if (TextUtils.isEmpty(vfpercentagem.getText().toString().trim())) {
                //  passwordEdt.setError(getString(R.string.err_please_enter_password));
                lblvfpercentagem.setError("Please Enter Vitual Farmer Percentage");
                return false;
            }
        }

        if (CommonUtils.isEmptySpinner(spin_insuranceprovider)) {
            Toast.makeText(context, "Please select Insurance Provider", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(InsuranceDetails.this);

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
        if (resultCode == InsuranceDetails.this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(InsuranceDetails.this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path===", path);
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    insuranceImg.setImageBitmap(bitmap);
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
            insuranceImg.setImageBitmap(thumbnail);
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(InsuranceDetails.this.getContentResolver(), contentURI);
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
            File f = new File(wallpaperDirectory, "Insurance Image"+ ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(InsuranceDetails.this,
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

}