package com.cis.easyfarm.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.cis.easyfarm.Fragments.Adduser_fifthpage;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonActivity;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.localData.SharedPrefsData;
import com.cis.easyfarm.model.Complaints;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;



import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import dmax.dialog.SpotsDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cis.easyfarm.common.CommonUtils.bitMaptoBase64;
import static com.cis.easyfarm.ui.buyerpersonaldetails.getKeysByValue;


public class Complaints_Activity extends CommonActivity implements View.OnClickListener {
    private static final String LOG_TAG = Complaints_Activity.class.getName();
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    ImageView backImg, home_btn;
    ImageButton btn_addIMG;
    private Button btn;
    private ImageView imageview, imageview2, imageview3;
    private ImageView img_delete1, img_delete2, img_delete3;
    private RelativeLayout lyt_img, lyt_img2, lyt_img3;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;
    String currentDate,selected_issue;
    EditText comments;
    boolean flag = false;
    int pos;
    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;
    private Chronometer chronometer;
    private ImageView imageViewRecord, imageViewPlay, imageViewStop;
    private SeekBar seekBar;
    private LinearLayout linearLayoutRecorder, linearLayoutPlay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String fileName = null;
    private int lastProgress = 0;
    DataAccessHandler dataAccessHandler;
    private Handler mHandler = new Handler();
    private int RECORD_AUDIO_REQUEST_CODE = 123;
    private boolean isPlaying = false;
    Random random;
    Button submit;
    Spinner getcomplaints;
    List<String> Issue_type = new ArrayList<String>();
    private static final String TAG = Complaints_Activity.class.getSimpleName();
    List<Integer> Issue_Id = new ArrayList<Integer>();
    private List<Bitmap> images = new ArrayList<>();
    LoginResponse loginressponse;
    int User_id;
    String plot_id,totalarea,status,ownership;
    TextView plotcode, plotstatus,plotarea,plotowner;
    private String mCurrentPhotoPath;
    private String extension;
    private LinkedHashMap Compalintsmap;
    public static Object Complaintsids;
    List<Plot> plotDetails;

    private List<Complaints> complaintList = new ArrayList<>();


    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_);
        requestMultiplePermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio();
           
        }
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            plot_id = extras.getString("code");
            totalarea = extras.getString("totalarea");
            status = extras.getString("status");
            ownership = extras.getString("ownership");



        }
        intview();
        setViews();


    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.userPermission), Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }


    private void intview() {
        //   plotcode, plotstatus,plotarea,plotowner;;
        dataAccessHandler = new DataAccessHandler(this);
        plotcode = findViewById(R.id.plotcode);
        plotstatus = findViewById(R.id.plotstatus);
        plotarea = findViewById(R.id.plotarea);
        plotowner = findViewById(R.id.plotowner);
        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        btn_addIMG = findViewById(R.id.btn_addIMG);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();


        getcomplaints = findViewById(R.id.issue_type);
        comments = findViewById(R.id.comments);
        imageview = (ImageView) findViewById(R.id.iv);
        imageview2 = (ImageView) findViewById(R.id.iv2);
        imageview3 = (ImageView) findViewById(R.id.iv3);

        imageview.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        btn_addIMG.setVisibility(View.VISIBLE);

        submit = (Button) findViewById(R.id.req_loan);

        lyt_img = findViewById(R.id.lyt_img1);
        lyt_img2 = findViewById(R.id.lyt_img2);
        lyt_img3 = findViewById(R.id.lyt_img3);

        img_delete1 = findViewById(R.id.img_delete1);
        img_delete2 = findViewById(R.id.img_delete2);
        img_delete3 = findViewById(R.id.img_delete3);

        /*Initially Disbale images */
        lyt_img.setVisibility(View.GONE);
        lyt_img2.setVisibility(View.GONE);
        lyt_img3.setVisibility(View.GONE);

        linearLayoutRecorder = (LinearLayout) findViewById(R.id.linearLayoutRecorder);
        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        imageViewRecord = (ImageView) findViewById(R.id.imageViewRecord);
        imageViewStop = (ImageView) findViewById(R.id.imageViewStop);
        imageViewPlay = (ImageView) findViewById(R.id.imageViewPlay);
        linearLayoutPlay = (LinearLayout) findViewById(R.id.linearLayoutPlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        imageViewRecord.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        random = new Random();

    }

    private void setViews() {

        plotcode.setText(plot_id);
        plotstatus.setText(status);
        plotarea.setText(totalarea);
        plotowner.setText(ownership);
        loginressponse = SharedPrefsData.getCatagories(Complaints_Activity.this);
        plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getcompleteplotdetails(plot_id), 1);
        img_delete1.setOnClickListener(this);
        img_delete2.setOnClickListener(this);
        img_delete3.setOnClickListener(this);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(Complaints_Activity.this, SyncHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        Compalintsmap = dataAccessHandler.getGenericData(Queries.getInstance().getComplainttype());
        String[] ComplaintType = CommonUtils.fromMap(Compalintsmap, "ComplaintType");
        ArrayAdapter<String> plotadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ComplaintType);
        plotadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getcomplaints.setAdapter(plotadapter);

        getcomplaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_issue = getcomplaints.getItemAtPosition(getcomplaints.getSelectedItemPosition()).toString();
                Object complaint_id;

                complaint_id = getKeysByValue(Compalintsmap,getcomplaints.getSelectedItem().toString());
                Complaintsids = (complaint_id.toString().substring(1, complaint_id.toString().length()-1));
                Log.d("==complaint_id==",Complaintsids.toString());
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        btn_addIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() > 2) {
                    Toast.makeText(Complaints_Activity.this, "max Images 3", Toast.LENGTH_SHORT).show();
                } else {
                    showPictureDialog();
                }


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
//                    stoppRecording();
//                    try {
//                        mPlayer.release();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    mPlayer = null;
             complaintsdatainsert();
                    }


            }
        });
    }

    private void complaintsdatainsert() {

        complaintList = (List<Complaints>) dataAccessHandler.getComplaintsDetails(Queries.getInstance().getComplaintsbyplotcode(plot_id), 1);

        Log.d("ComplaintListSize", complaintList.size() + "");

        int ccsize = complaintList.size();

        Log.d("ccsize", ccsize + "");

        int cccsize = ccsize + 1;

        Log.d("cccsize", cccsize + "");

        String CCode = "COM" + SyncHomeActivity.User_id + plot_id + -+cccsize;

        Log.d("ComplaintCode", CCode);


        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

        //ComplaintsTable

        LinkedHashMap complaintsmap = new LinkedHashMap();

        complaintsmap.put("Id", 0);
        complaintsmap.put("Code", CCode);
        complaintsmap.put("PlotCode", plot_id);
        complaintsmap.put("IsActive", 1);
        complaintsmap.put("CreatedByUserId", SyncHomeActivity.User_id);
        complaintsmap.put("CreatedDate", timeStamp);
        complaintsmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
        complaintsmap.put("UpdatedDate", timeStamp);
        complaintsmap.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> Complaint_list = new ArrayList<>();

        Complaint_list.add(complaintsmap);


        //ComplaintsRepositoryTable


        LinkedHashMap complaintRepoAdiomap = new LinkedHashMap();

        final List<LinkedHashMap> ComplaintRepo_list = new ArrayList<>();
       //  images = new ArrayList<>();

        for (int ii = 0; ii < images.size(); ii++) {
Log.e("=========i=value",images.get(ii)+"");
            LinkedHashMap complaintRepomap = new LinkedHashMap();
            complaintRepomap.put("FileBytes", bitMaptoBase64(images.get(ii)));
            complaintRepomap.put("Id", 0);
            complaintRepomap.put("ComplaintCode", CCode);
            complaintRepomap.put("FileName", "");
            complaintRepomap.put("FileExtension", extension);
            complaintRepomap.put("FileLocation", "");
            complaintRepomap.put("IsAudioRecording", false);
            complaintRepomap.put("IsActive", 1);
            complaintRepomap.put("CreatedByUserId", SyncHomeActivity.User_id);
            complaintRepomap.put("CreatedDate", timeStamp);
            complaintRepomap.put("UpdatedByUserId", SyncHomeActivity.User_id);
            complaintRepomap.put("UpdatedDate", timeStamp);
            complaintRepomap.put("ServerUpdatedStatus", 0);
            ComplaintRepo_list.add(complaintRepomap);

        }



        if (null != fileName || !TextUtils.isEmpty(fileName)) {

            complaintRepoAdiomap.put("FileBytes", doFileUpload(new File(fileName)));

            complaintRepoAdiomap.put("Id", 0);
            complaintRepoAdiomap.put("ComplaintCode", CCode);
            complaintRepoAdiomap.put("FileName", "");
            complaintRepoAdiomap.put("FileExtension", ".mp3");
            complaintRepoAdiomap.put("FileLocation", "");
            complaintRepoAdiomap.put("IsAudioRecording", true);
            complaintRepoAdiomap.put("IsActive", 1);
            complaintRepoAdiomap.put("CreatedByUserId", SyncHomeActivity.User_id);
            complaintRepoAdiomap.put("CreatedDate", timeStamp);
            complaintRepoAdiomap.put("UpdatedByUserId", SyncHomeActivity.User_id);
            complaintRepoAdiomap.put("UpdatedDate", timeStamp);
            complaintRepoAdiomap.put("ServerUpdatedStatus", 0);

            ComplaintRepo_list.add(complaintRepoAdiomap);
        }

        //ComplaintsXrefTable


        LinkedHashMap complaintxrefmap = new LinkedHashMap();

        complaintxrefmap.put("Id", 0);
        complaintxrefmap.put("ComplaintCode", CCode);
        complaintxrefmap.put("ComplaintTypeId", Complaintsids);
        complaintxrefmap.put("CreatedByUserId", SyncHomeActivity.User_id);
        complaintxrefmap.put("CreatedDate", timeStamp);
        complaintxrefmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
        complaintxrefmap.put("UpdatedDate", timeStamp);
        complaintxrefmap.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> Complaintxref_list = new ArrayList<>();

        Complaintxref_list.add(complaintxrefmap);

        //ComplaintsStatusTable


        LinkedHashMap complaintstatusmap = new LinkedHashMap();

        complaintstatusmap.put("Id", 0);
        complaintstatusmap.put("ComplaintCode", CCode);
        complaintstatusmap.put("StatusTypeId", 159);
        complaintstatusmap.put("AssigntoUserId", -1);
        complaintstatusmap.put("Comments", comments.getText().toString());
        complaintstatusmap.put("IsActive", 1);
        complaintstatusmap.put("CreatedByUserId",SyncHomeActivity.User_id);
        complaintstatusmap.put("CreatedDate", timeStamp);
        complaintstatusmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
        complaintstatusmap.put("UpdatedDate", timeStamp);
        complaintstatusmap.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> Complaintstatus_list = new ArrayList<>();

        Complaintstatus_list.add(complaintstatusmap);


        dataAccessHandler.insertMyData("Complaints", Complaint_list, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {

                if (success) {

                    Log.d("complaintsTable", "Inserted Successfully");
                 //   farmerDetails.get(0).setServerupdatedstatus(0);
                    CommonUtils.appendLog(LOG_TAG, "ComplaintsTable", "Inserted Successfully");

                }
            }
        });
        dataAccessHandler.insertMyData("ComplaintTypeXref", Complaintxref_list, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {

                if (success) {

                    Log.d("ComplaintTypeXrefTable", "Inserted Successfully");
                    //   farmerDetails.get(0).setServerupdatedstatus(0);
                    CommonUtils.appendLog(LOG_TAG, "ComplaintTypeXrefTable", "Inserted Successfully");

                }
            }
        });
//        dataAccessHandler.insertMyData("ComplaintStatusHistory", Complaintstatus_list, new ApplicationThread.OnComplete<String>() {
//            @Override
//            public void execute(boolean success, String result, String msg) {
//
//                if (success) {
//
//                    Log.d("complaintStatusHistorytTable", "Inserted Successfully");
//                    //   farmerDetails.get(0).setServerupdatedstatus(0);
//                    CommonUtils.appendLog(LOG_TAG, "complaintStatusHistoryTable", "Inserted Successfully");
//
//                }
//            }
//        });
        dataAccessHandler.insertMyData("ComplaintRepository", ComplaintRepo_list, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {

                if (success) {
                    if (success) {
                        Toast.makeText(Complaints_Activity.this, "Complaint Raised Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Complaints_Activity.this, SyncHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                      finish();
                        Log.d("ComplaintRepository", "Inserted Successfully");
                        CommonUtils.appendLog(LOG_TAG, "ComplaintRepository", "Inserted Successfully");

                       // farmerDetails.get(0).setServerupdatedstatus(0);
                    }

                    Log.d("ComplaiRepositoryTable", "Inserted Successfully");
                    //   farmerDetails.get(0).setServerupdatedstatus(0);
                    CommonUtils.appendLog(LOG_TAG, "CComplaintRepositoryTable", "Inserted Successfully");

                }
            }
        });
    }

    private void stoppRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }


    private boolean validations() {
        if (getcomplaints.getSelectedItemPosition() == 0) {

            showDialog(Complaints_Activity.this, getResources().getString(R.string.valid_issue_type));
            return false;
        }

        if (images.size() == 0 &&  fileName == null) {

            Log.d(TAG, "---- analysis ---->> base64 :" + images.size() + fileName);
            showDialog(Complaints_Activity.this, getResources().getString(R.string.select_image));

        }


        return true;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_delete1:
                images.remove(0);
                displayImages();
                break;
            case R.id.img_delete2:
                images.remove(1);
                displayImages();
                break;
            case R.id.img_delete3:
                images.remove(2);
                displayImages();
                break;

            case R.id.imageViewRecord:
                prepareforRecording();
                startRecording();

                break;
            case R.id.imageViewStop:
                prepareforStop();
                stopRecording();

                break;
            case R.id.imageViewPlay:
                if (!isPlaying && fileName != null) {
                    isPlaying = true;
                    if (mPlayer != null) {
                        lastProgress = 0;
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        startPlaying();
                    } else {
                        startPlaying();
                    }

                } else {
                    isPlaying = false;
                    stopPlaying();
                }
        }

    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path===", path);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                    long sizeOfImage = imageInByte.length / 1024; //Image size
                    Log.e("sizeOfImage===", sizeOfImage+"");
                    if(sizeOfImage > 1024) {
                        Log.e("imagesize===", "Image Size more than 1MB");
                        showDialog(Complaints_Activity.this, "Image Size more than 1MB");
                    }else {
                        images.add(bitmap);
                    isselectedCamera = true;
                        displayImages();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] imageInByte = stream.toByteArray();
//            long sizeOfImage = imageInByte.length / 1024; //Image size
//            Log.e("sizeOfImage===", sizeOfImage+"");
//            if(sizeOfImage > 200) {
//                Log.e("imagesize===", "Image Size more than 200KB");
//                showDialog(Complaints_Activity.this, "Image Size more than 200KB");
//                Toast.makeText(this, "Image Size more than 200KB", Toast.LENGTH_LONG).show();
//            }else {
                images.add(thumbnail);
            isselectedCamera = true;
                //  saveImage(thumbnail);
                displayImages();
           // }
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImages() {

        lyt_img.setVisibility(View.GONE);
        lyt_img2.setVisibility(View.GONE);
        lyt_img3.setVisibility(View.GONE);
        btn_addIMG.setVisibility(View.VISIBLE);
        if (images.size() > 0) {
            if (images.size() > 0 && images.get(0) != null) {
                imageview.setImageBitmap(images.get(0));
                imageview.setVisibility(View.VISIBLE);
                lyt_img.setVisibility(View.VISIBLE);

            }
            if (images.size() > 1 && images.get(1) != null) {
                imageview2.setImageBitmap(images.get(1));
                imageview2.setVisibility(View.VISIBLE);
                lyt_img2.setVisibility(View.VISIBLE);
            }
            if (images.size() > 2 && images.get(2) != null) {
                imageview3.setImageBitmap(images.get(2));
                imageview3.setVisibility(View.VISIBLE);
                lyt_img3.setVisibility(View.VISIBLE);
                btn_addIMG.setVisibility(View.GONE);
            }
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
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            long length = f.length() / 1024; // Size in KB Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            Log.d("TAG", " sizFilee::--->" + length);
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            mCurrentPhotoPath =f.getAbsolutePath();
            Log.e("PhotoPath===========",mCurrentPhotoPath);

            extension = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("."));
            Log.e("Extension===========",extension);

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void prepareforStop() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.GONE);
        linearLayoutPlay.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void prepareforRecording() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.GONE);
        imageViewStop.setVisibility(View.VISIBLE);
        linearLayoutPlay.setVisibility(View.GONE);
    }

    private void stopPlaying() {
        try {
            mPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer = null;
        //showing the play button
        imageViewPlay.setImageResource(R.drawable.ic_play);
        chronometer.stop();

    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.AudioEncoder.AMR_NB);

        File root = Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/EasyFarm Complaints/Audios");

        if (!file.exists()) {
            file.mkdirs();
        }

        fileName = root.getAbsolutePath() + "/EasyFarm Complaints/Audios/" + String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename===1049", fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastProgress = 0;
        seekBar.setProgress(0);
        stopPlaying();
        // making the imageview a stop button
        //starting the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }


    private void stopRecording() {

        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        //showing the play button
        //  Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
    }


    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        imageViewPlay.setImageResource(R.drawable.ic_pause);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);
        seekBar.setMax(mPlayer.getDuration());
        seekUpdation();
        chronometer.start();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {imageViewPlay.setImageResource(R.drawable.ic_play);
                seekBar.setVisibility(View.GONE);
                isPlaying = false;
                chronometer.stop();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress);
                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    private void seekUpdation() {
        if (mPlayer != null) {
            int mCurrentPosition = mPlayer.getCurrentPosition();
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }

}
