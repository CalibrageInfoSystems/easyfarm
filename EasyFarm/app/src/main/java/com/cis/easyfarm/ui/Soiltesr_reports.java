package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cis.easyfarm.Fragments.Adduser_fragment;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.FileChooser;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cis.easyfarm.ui.buyerpersonaldetails.getKeysByValue;

public class Soiltesr_reports extends AppCompatActivity {


    EditText Nitrogen_edittxt, Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;
    TextInputLayout serviceNumberLyt,Nitrogen_lyt, Prosperous_lyt, Potassium_lyt, Carbon_lyt,
            Hydrogen_lyt, Oxygen_lyt, Sulphur_lyt, Calcium_lyt, Magnesium_lyt;

    Spinner spin_soil_culture_Type;
    private LinkedHashMap  soilCultureTypeDataMap;
    Object  soilCultureTypeIds;
    Button save;
    DataAccessHandler dataAccessHandler;
    String code,Farmercode;
    List<Farmer> farmerDetails;
    List<Plot> plotDetails;
    private String mCurrentPhotoPath, extension;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/EasyFarm_profiles";
    public  static  final  String LOG_TAG = Soiltesr_reports.class.getSimpleName();

    private ImageView  imageView, deleteIcon;
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";

Toolbar toolbar;
    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;

Button uploaddocuments;
    LinearLayout maindoc;
    private Context mContext;
    private TextView dialogMessage, docText;
    ArrayList<String> extensions;
    // String[] gender = { "Male", "Female" };
    private AlertDialog alertDialog;
    Bitmap bitmap;
    String date_birth;
    int  pos;
    String docString, filePath, authorizationToken, yearTxt, monthTxt, dateandtime, jobTitle;
    private static long MAX_FILE_SIZE = 5;
    Button cancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soiltesr_reports);

        intviews();
        setviews();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void intviews() {


        dataAccessHandler = new DataAccessHandler(getApplicationContext());

        Nitrogen_edittxt= findViewById(R.id.Nitrogen_edittxt);
        Prosperous_edittxt= findViewById(R.id.Prosperous_edittxt);
        Potassium_edittxt= findViewById(R.id.Potassium_edittxt);
        Carbon_edittxt= findViewById(R.id.Carbon_edittxt);
        Hydrogen_edittxt= findViewById(R.id.Hydrogen_edittxt);
        Oxygen_edittxt= findViewById(R.id.Oxygen_edittxt);
        Sulphur_edittxt= findViewById(R.id.Sulphur_edittxt);
        Calcium_edittxt= findViewById(R.id.Calcium_edittxt);
        Magnesium_edittxt= findViewById(R.id.Magnesium_edittxt);
        uploaddocuments=findViewById(R.id.uploaddocuments);
        spin_soil_culture_Type= findViewById(R.id.spin_soil_culture_Type);
        Nitrogen_lyt= findViewById(R.id.Nitrogen_lyt);
        Prosperous_lyt= findViewById(R.id.Prosperous_lyt);
        Potassium_lyt= findViewById(R.id.Potassium_lyt);
        Carbon_lyt= findViewById(R.id.Carbon_lyt);
        Hydrogen_lyt= findViewById(R.id.Hydrogen_lyt);
        Oxygen_lyt= findViewById(R.id.Oxygen_lyt);
        Sulphur_lyt= findViewById(R.id.Sulphur_lyt);
        Calcium_lyt= findViewById(R.id.Calcium_lyt);
        Magnesium_lyt=findViewById(R.id.Magnesium_lyt);


        save =findViewById(R.id.saveButton);

        imageView = findViewById(R.id.imageView);
        maindoc = findViewById(R.id.maindoc);
        deleteIcon = findViewById(R.id.deleteIcon);
        docText = findViewById(R.id.docText);

    }

    private void setviews() {
        Intent i = getIntent();
        code = i.getStringExtra("code");
        Farmercode = i.getStringExtra("Farmercode");
        Log.d("SFarmeCode", Farmercode);
        Log.d("SCode", code + "");


        farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(Farmercode), 1);
        plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getFarmerplotDetails(Farmercode), 1);
        Log.d("PlotCodes", plotDetails.get(0).getCode());

        Nitrogen_edittxt.addTextChangedListener(new ValidationTextWatcher(Nitrogen_edittxt));
        Prosperous_edittxt.addTextChangedListener(new ValidationTextWatcher(Prosperous_edittxt));
        Potassium_edittxt.addTextChangedListener(new ValidationTextWatcher(Potassium_edittxt));
        Carbon_edittxt.addTextChangedListener(new ValidationTextWatcher(Carbon_edittxt));
        Hydrogen_edittxt.addTextChangedListener(new ValidationTextWatcher(Hydrogen_edittxt));
        Oxygen_edittxt.addTextChangedListener(new ValidationTextWatcher(Oxygen_edittxt));
        Sulphur_edittxt.addTextChangedListener(new ValidationTextWatcher(Sulphur_edittxt));
        Calcium_edittxt.addTextChangedListener(new ValidationTextWatcher(Calcium_edittxt));

        Magnesium_edittxt.addTextChangedListener(new ValidationTextWatcher(Magnesium_edittxt));
        soilCultureTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getsoilCultureType());
        String[] cultureTypes = CommonUtils.fromMap(soilCultureTypeDataMap, "Culture Type");
        ArrayAdapter<String> ownerShipAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cultureTypes);
        ownerShipAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_soil_culture_Type.setAdapter(ownerShipAdapter3);

        spin_soil_culture_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object cultureTypeId;

                cultureTypeId = getKeysByValue(soilCultureTypeDataMap,spin_soil_culture_Type.getSelectedItem().toString());
                soilCultureTypeIds = (cultureTypeId.toString().substring(1, cultureTypeId.toString().length()-1));
                Log.d("==soilCultureTypeIds==",soilCultureTypeIds.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // selected file to be remove
                showConformationDialog(pos);
            }
        });
        uploaddocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                    showPictureDialog();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    LinkedHashMap soilTestmap = new LinkedHashMap();
                    String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

                    soilTestmap.put("Id", 0);
                    soilTestmap.put("PlotCode", code);
                    soilTestmap.put("CultureTypeId", soilCultureTypeIds.toString());
                    soilTestmap.put("Nitrogen", Nitrogen_edittxt.getText().toString());
                    soilTestmap.put("Prosperous", Prosperous_edittxt.getText().toString());
                    soilTestmap.put("Potassium", Potassium_edittxt.getText().toString());
                    soilTestmap.put("Carbon", Carbon_edittxt.getText().toString());
                    soilTestmap.put("Hydrogen", Hydrogen_edittxt.getText().toString());
                    soilTestmap.put("Oxygen", Oxygen_edittxt.getText().toString());
                    soilTestmap.put("Sulphur", Sulphur_edittxt.getText().toString());
                    soilTestmap.put("Calcium", Calcium_edittxt.getText().toString());
                    soilTestmap.put("Magnesium", Magnesium_edittxt.getText().toString());
                    soilTestmap.put("FileName", "");
                    soilTestmap.put("FileLocation", "");
                    soilTestmap.put("FileExtension", extension);
                    soilTestmap.put("IsActive", 1);
                    soilTestmap.put("CreatedByUserId", SyncHomeActivity.User_id);
                    soilTestmap.put("CreatedDate", timeStamp);
                    soilTestmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
                    soilTestmap.put("UpdatedDate", timeStamp);
                    soilTestmap.put("FileBytes", docString);
                    soilTestmap.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> soilTestlist = new ArrayList<>();

                    soilTestlist.add(soilTestmap);

                    dataAccessHandler.insertMyData("SoilTestDetails", soilTestlist, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                            if (success) {
                                if (success) {
                                    Toast.makeText(Soiltesr_reports.this, "Soil Test Report Submitted", Toast.LENGTH_SHORT).show();
                                    CommonUtils.appendLog(LOG_TAG,"onNextPressed", "Soil Test Report Submitted" );

                                    Log.d("SoilTestDetails", "Inserted Successfully");
                                    CommonUtils.appendLog(LOG_TAG,"onNextPressed", "SoilTestDetails Inserted Successfully" );

                                    Intent intent = new Intent(Soiltesr_reports.this, SyncHomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                 finish();

                                    farmerDetails.get(0).setServerupdatedstatus(0);
//                                    plotDetails.get(0).setPlotStatusId(149);
                                }


                            }
                        }
                    });

                    dataAccessHandler.updatePlotStatus(149,code, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            plotDetails.get(0).setServerUpdatedStatus(0);
                            android.util.Log.d("mahesh", "@@@@@ UPdated Status :"+success);
                        }
                    });

                }
            }});
    }

    // selected file to be remove
    private void showConformationDialog(final int pos) {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(this);
        }


        builder.setTitle("delete_entry")
                .setMessage("are_you_sure_you_want_to_delete_this_entry")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        imageView.setImageBitmap(null);
                        docText.setText("");
                        filePath = null;
                        deleteIcon.setVisibility(View.GONE);
                        maindoc.setVisibility(View.GONE);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showPictureDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogRootView = layoutInflater.inflate(R.layout.dialog_adddocuments, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        cancel_btn =dialogRootView.findViewById(R.id.cancel_btn);
        dialogMessage = dialogRootView.findViewById(R.id.dialogMessage);

        /**
         * @param OnClickListner
         */
        dialogMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FileChooser.class);
                extensions = new ArrayList<String>();
                extensions.add(".pdf");
//                extensions.add(".doc");
//                extensions.add(".docx");
//                extensions.add(".txt");
//                extensions.add(".rtf");

                intent.putStringArrayListExtra("filterFileExtension", extensions);
                startActivityForResult(intent, 1);
                alertDialog.dismiss();
            }
        });

        alertDialogBuilder.setView(dialogRootView);


        /**
         * @param OnClickListner
         */
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                filePath = data.getStringExtra("fileSelected");
                File file = new File(filePath);

                long fileSizeInBytes = file.length();
                long fileSizeInKB = fileSizeInBytes / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;

                // selected file is converting into base64 conversion
                convertFileToByteArray(filePath);

                if (fileSizeInMB < MAX_FILE_SIZE) {

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }

                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                docText.setText(filename);
            }

            Glide.with(this).load("").error(R.drawable.ic_pdf).into(imageView);
          //  Glide.with(this).load("").into(imageView);
            maindoc.setVisibility(View.VISIBLE);
            deleteIcon.setVisibility(View.VISIBLE);


        }
//
    }

    private String convertFileToByteArray(String filePath) {
        String val = null;

        byte[] byteArray = null;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                extension = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("."));

                long fileSizeInBytes = f.length();
                long fileSizeInKB = fileSizeInBytes / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;

                if (fileSizeInMB < 10) {
                    InputStream inputStream = new FileInputStream(f);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b = new byte[1024 * 10000];
                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }

                    byteArray = bos.toByteArray();
                    val = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    docString = val.toString();


Log.e(LOG_TAG,docString);
                } else {
                    Toast.makeText(mContext, "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }


            }

            return val;

        } catch (IOException e) {
            e.printStackTrace();
            return val;
        }


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


    private boolean validation() {
         if(CommonUtils.isEmptySpinner(spin_soil_culture_Type)){
            Toast.makeText(this, "Please Select Soil Culture Type  ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(Nitrogen_edittxt.getText().toString().trim())) {
            Nitrogen_lyt.setError("Please Enter Nitrogen Value");
            return false;
        }
     //   Prosperous_edittxt, Potassium_edittxt, Carbon_edittxt,  Hydrogen_edittxt, Oxygen_edittxt, Sulphur_edittxt, Calcium_edittxt, Magnesium_edittxt;
        else if (TextUtils.isEmpty(Prosperous_edittxt.getText().toString().trim())) {
            Prosperous_lyt.setError("Please Enter Prosperous Value");
            return false;
        }
        else if (TextUtils.isEmpty(Potassium_edittxt.getText().toString().trim())) {
            Potassium_lyt.setError("Please Enter Potassium Value");
            return false;
        }
        else if (TextUtils.isEmpty(Carbon_edittxt.getText().toString().trim())) {
           Carbon_lyt.setError("Please Enter Carbon Value");
            return false;
        }
        else if (TextUtils.isEmpty(Hydrogen_edittxt.getText().toString().trim())) {
            Hydrogen_lyt.setError("Please Enter Hydrogen Value");
            return false;
        }
        else if (TextUtils.isEmpty(Oxygen_edittxt.getText().toString().trim())) {
            Oxygen_lyt.setError("Please Enter Oxygen Value");
            return false;
        }
        else if (TextUtils.isEmpty(Sulphur_edittxt.getText().toString().trim())) {
            Sulphur_lyt.setError("Please Enter Sulphur Value");
            return false;
        }
        else if (TextUtils.isEmpty(Calcium_edittxt.getText().toString().trim())) {
            Calcium_lyt.setError("Please Enter  Calcium Value");
            return false;
        }
        else if (TextUtils.isEmpty(Magnesium_edittxt.getText().toString().trim())) {
            Magnesium_lyt.setError("Please Enter Magnesium Value");
            return false;
        }
         else if (TextUtils.isEmpty(docString) && docString == null   ) {
             UiUtils.showCustomToastMessage("Please Add SoilReport Document", Soiltesr_reports.this, 1);
         //    Toast.makeText(Soiltesr_reports.this, "Please Sdd SoilReport Document", Toast.LENGTH_SHORT).show();
          //   .setError("Please Sdd SoilReport Document");
             return false;
         }


        return true;
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
