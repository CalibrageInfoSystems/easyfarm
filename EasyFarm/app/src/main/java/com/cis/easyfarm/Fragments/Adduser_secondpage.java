package com.cis.easyfarm.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.FileRepository;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.Objects.Images;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.DataManager;
import com.cis.easyfarm.common.FileChooser;
import com.cis.easyfarm.common.ProgressDialogFragment;
import com.cis.easyfarm.database.DataAccessHandler;

import com.cis.easyfarm.common.ImageUtility;
import com.cis.easyfarm.common.ProgressBar;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DataSavingHelper;
import com.cis.easyfarm.database.DatabaseKeys;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.sync.DataSynchelper;


import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.cis.easyfarm.common.CommonUtils.REQUEST_CAM_PERMISSIONS;
import static com.cis.easyfarm.database.DataSavingHelper.getLandLordIdProofsData;

public class Adduser_secondpage extends Fragment implements View.OnClickListener, IdProofListAdapter.idProofsClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String LOG_TAG = Adduser_secondpage.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  List<IdProfs> idProfslist =new ArrayList<>();
    View view;
    int  pos;
    private int GALLERY = 1, CAMERA = 2;
    private ImageView addIDProof;
    private Images images;
    String usercode;
    Spinner idproof,spinn_idproof;
    private  String aadharStr;
    ArrayList<String> extensions;
    private String extension;
    private AlertDialog alertDialog;
    IdentityProof identityProof = new IdentityProof();;
    public static String idProofId,idnumber;

    Boolean isselectedCamera = false;
    Boolean isFarmerHaveImg = false;


    private AlertDialog alert;

    String selectedProof;
    private Button save;

    TextView docText, docText2, docText3, docText4, docText5, docText6;
    TextInputLayout idproofnumber_lbl;

    DataAccessHandler dataAccessHandler;
    String code;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinkedHashMap<String, String> idProofsData, mainData;
    private LinearLayout headerLL;
    private OnStepTwoListener mListener;
    private List<IdentityProof> identityProofsList;
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";

    RecyclerView idProofsRecyclerView;
    private RelativeLayout addIdProof, addIdProofBottomView;
    private IdProofListAdapter idProofsListAdapter;
    private View headerView;
    private Button saveBtn;
    ImageView idproofsImageV,idproofsImageView;
    final ApplicationThread.OnComplete<String> onComplete = null;
    private String mCurrentPhotoPath;
    private static final int CAMERA_REQUEST = 1888;
    private boolean isImage = false;
    private byte[] bytes = null;
    private  EditText idproofsEdttext;
    private IdentityProof savedPictureData = null;
    private String blockCharacterSet = "~#^|$%&*!";
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    List<Farmer> farmerDetails;
    List<IdentityProof> identityProofs;
    private static final String IMAGE_DIRECTORY = "/EasyFarm_IDS";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }

    };
    public  void  addidprof(int typId)
    {
        // check list has already that id
        boolean isalreadyexist = false;
        for (IdentityProof id: identityProofs) {
            if(typId == id.getIdProofTypeId())
            {
                isalreadyexist = true;
            }
        }
        if(isalreadyexist)
        {
            //Toast Already exist
        }else
        {
//            Add new Documnet
        }

    }
    public Adduser_secondpage() {
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
    public static Adduser_secondpage newInstance(String param1, String param2) {
        Adduser_secondpage fragment = new Adduser_secondpage();
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

        view = inflater.inflate(R.layout.fragment_adduser_secondpage, container, false);

        intviews();
        setviews();



        return view;
    }

    private void intviews() {
        addIDProof = (ImageView) view.findViewById(R.id.addRowlandLordImg);
        idProofsRecyclerView = (RecyclerView) view.findViewById(R.id.idProofsRecyclerView);
        spinn_idproof =view.findViewById(R.id.idProofsSpinner);
        save=view.findViewById(R.id.SaveButton);
        dataAccessHandler = new DataAccessHandler(getContext().getApplicationContext());
        idproofsEdttext =view.findViewById(R.id.idproofsEdttext);
        idproofsImageView=view.findViewById(R.id.idproofsImageView);
        idproofnumber_lbl = view.findViewById(R.id.idproofnumber_lbl);

    }

    private void setviews() {
        dataAccessHandler = new DataAccessHandler(getContext());

        Add_Farmer activity = (Add_Farmer) getActivity();

        Bundle results = activity.getMyData();

        usercode = results.getString("val1");

        Log.d("usercode", usercode);



        //Log.d("IDentituyProofSize",identityProofsList.size() + "");

        //identityProofs = (List<IdentityProof>) dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getFarmerPersonalDetails(Adduser_fragment.value1), 1);

        farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(Adduser_fragment.value1), 1);
        Log.e(LOG_TAG, farmerDetails.get(0).getCode() + "");
        idProofsData = mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData());

        savedPictureData = new IdentityProof();
        savedPictureData = (IdentityProof) DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA);
        Log.e("savedPictur=======", savedPictureData + "");
        if (savedPictureData == null) {
            Log.e("savedPicture=========", "nulll");
            //  savedPictureData = dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getSelectedFileRepositoryQuery(farmerDetails.get(0).getCode(), 193));
        }
        if (savedPictureData != null && savedPictureData.getFileLocation() != null) {
            mCurrentPhotoPath = savedPictureData.getFileLocation();

            idproofsImageV.invalidate();
        }

//        identityProofsList = (List<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_IDPROOFS_DATA);
//        if (null == identityProofsList) {
//            identityProofsList = new ArrayList<>();
//        } else {
//            idProofsRecyclerView.setVisibility(View.VISIBLE);
//        }

        IdProofViews();


        addIDProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayIdProofsDialog();
            }
        });
      //  filterIdProofs();
        spinn_idproof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // idproofsEdttext.setText("");

                selectedProof = ""+spinn_idproof.getSelectedItem().toString();
                idproofsEdttext.setInputType(selectedProof.contains("Aadhar") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT) ;


                if(selectedProof.contains("Aadhar"))
                {
                    idproofsEdttext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12),filter});

                }else{

                    idproofsEdttext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20),filter});

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinn_idproof.setAdapter(UiUtils.createAdapter(getActivity(), idProofsData, "Id proof"));


//        public void filterIdProofs() {
//            for (IdentityProof identityProof : identityProofsList) {
//                idProofsData.remove(String.valueOf(identityProof.getIdProofTypeId()));
//                UiUtils.createAdapter.notifyDataSetChanged();
//            }
//        }
        idproofsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadharStr = idproofsEdttext.getText().toString();

                if (savevalidation()){

                    updateIdProofsAdapter(spinn_idproof.getSelectedItem().toString(), aadharStr);
                    newaddIDProof();

                }

//                if (!CommonUtils.isEmptySpinner(spinn_idproof)) {
//                    if (!TextUtils.isEmpty(aadharStr))
//                    {
//
////                        String selectedProof = spinn_idproof.getSelectedItem().toString();
////
////                        if (TextUtils.isEmpty(selectedProof)) {
////                            UiUtils.showCustomToastMessage("Please select id proof", getActivity(), 1);
////                            return;
////                        }
////
////                        if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.adhar_number))) {
////                            if (!TextUtils.isDigitsOnly(aadharStr)) {
////                                UiUtils.showCustomToastMessage("Enter only numbers", getActivity(), 1);
////                                return;
////                            }
////                            if (aadharStr.length() < 12) {
////                                UiUtils.showCustomToastMessage("Enter proper Aadhar card number", getActivity(), 1);
////                                return;
////                            } else {
////                                if (identityProofsList == null || identityProofsList.isEmpty()) {
////                                    startAnimation();
////                                }
////                                updateIdProofsAdapter(spinn_idproof.getSelectedItem().toString(), aadharStr);
////                                newaddIDProof();
////                                //  alert.cancel();
////                            }
////                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.pancard_number))) {
////                            if (aadharStr.length() < 10) {
////                                UiUtils.showCustomToastMessage("Enter proper PAN card number", getActivity(), 1);
////                                return;
////                            } else {
////                                if (identityProofsList == null || identityProofsList.isEmpty()) {
////                                    startAnimation();
////                                }
////                                updateIdProofsAdapter(spinn_idproof.getSelectedItem().toString(), aadharStr);
////                                newaddIDProof();
////                                // alert.cancel();
////                            }
////                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.drive_number))) {
////                            if (aadharStr.length() < 10) {
////                                UiUtils.showCustomToastMessage("Enter proper Driving License number", getActivity(), 1);
////                                return;
////                            } else {
////                                if (identityProofsList == null || identityProofsList.isEmpty()) {
////                                    startAnimation();
////                                }
////                                updateIdProofsAdapter(spinn_idproof.getSelectedItem().toString(), aadharStr);
////                                newaddIDProof();
////                                // alert.cancel();
////                            }
////                        }
////                        else if ( mCurrentPhotoPath == null) {
////
////                            UiUtils.showCustomToastMessage("Please Select Id Proof Image", getActivity(), 1);
////                            return ;
////                        }
////                        else {
////                            if (identityProofsList == null || identityProofsList.isEmpty()) {
////                                startAnimation();
////                            }
////                            updateIdProofsAdapter(spinn_idproof.getSelectedItem().toString(), aadharStr);
////                            newaddIDProof();
////
////                            // alert.cancel();
////                        }
//                    } else {
//                        UiUtils.showCustomToastMessage("Please enter the Idproof detail", getActivity(), 1);
//                    }
//                    // SaveImages(getActivity(), ApplicationThread.OnComplete);
//                    // SaveImages();
//                    //savePlantation(context, oncomplete);
//                }


                //   savePlantation(context, oncomplete);
//                else {
//                    UiUtils.showCustomToastMessage("Please select IdProof", getActivity(), 1);
//                }

                //idProofsData.remove(String.valueOf(identityProof.getIdProofTypeId()));
            }
        });
//        for (IdentityProof id:
//                identityProofsList) {
//            Log.d("IDPROF","=====> analysis =====> ID Prof "+id.getIdprooftypeid() +"name :");
//        }
    }


    private boolean savevalidation() {

        if (CommonUtils.isEmptySpinner(spinn_idproof)) {
            Toast.makeText(getContext(), "Please select Id Proof Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(idproofsEdttext.getText().toString().trim())) {
            idproofnumber_lbl.setError("Please Enter Id Proof Number");
            return false;
        }

        if( idproofsImageView.getDrawable() == null || idproofsImageView.getDrawable().getConstantState().equals(idproofsImageView.getContext().getDrawable(R.mipmap.imgdefault).getConstantState()) ){
            UiUtils.showCustomToastMessage("Please Select Id Proof Image", getActivity(), 1);
            return false ;
        }


        return true;
    }


    private void IdProofViews() {
        identityProofsList = (List<IdentityProof>) dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getFarmerIDProofDetails(usercode), 1);
        Log.d("AddUserIDprofs", "identityProofsList size:"+identityProofsList.size());
        idProofsRecyclerView.setHasFixedSize(true);
        idProofsListAdapter = new IdProofListAdapter(getActivity(), identityProofsList, mainData);
        idProofsListAdapter.setIdProofsClickListener(Adduser_secondpage.this);
        idProofsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        idProofsRecyclerView.setAdapter(idProofsListAdapter);
    }


    public static void SaveImages(final Context context, final ApplicationThread.OnComplete<String> oncomplete) {

    }


//
//    private void SaveImages() {
//
//        LinkedHashMap map= new LinkedHashMap();
//        map.put("UserName", farmerDetails.get(0).getCode());
//        map.put("FatherName_GuardianName",doFileUpload(new File(mCurrentPhotoPath)));
//Log.e("map==========",map+"");
//      //  dataAccessHandler.insertData("Image", (List<LinkedHashMap>)map, new ApplicationThread.OnComplete<String>() {
//  dataAccessHandler.insertData("Image", "Image", new ApplicationThread.OnComplete<String>() {
//                @Override
//                public void execute(boolean success, String result, String msg) {
//                    if (success) {
//
//                    } else {
//                        Log.v(LOG_TAG, "@@@ check 2 " + masterData.size() + "...pos " + countCheck);
//                        Log.v(LOG_TAG, "@@@ sync failed for " + tableName + " message " + msg);
//                    }
//
//                }
//            });
//
//    }
//

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
                    Log.e("path===", bitmap+"");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                    long sizeOfImage = imageInByte.length / 1024; //Image size
                    Log.d("ImageLength", imageInByte.length + "");
                    Log.e("sizeOfImage===", sizeOfImage+"");
                    if(sizeOfImage > 1024) {
                        Log.e("imagesize===", "Image Size more than 1MB");
                        showDialog(getActivity(), "Image Size should be less than 1MB");
                    }else {
                        idproofsImageView.setImageBitmap(bitmap);
                        isselectedCamera = true;
                    }
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    // profileImgBtn.add(bitmap);
                    // displayImages();
                } catch (IOException e) {
                    e.printStackTrace();
                    CommonUtils.appendLog(LOG_TAG, "FIImageOnactivityResult", e.getLocalizedMessage() +"" );
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            saveImage(thumbnail);
            idproofsImageView.setImageBitmap(thumbnail);
            // profileImgBtn.add(thumbnail);
            //saveImage(thumbnail);
            //displayImages();
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
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

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageInByte = bytes.toByteArray();
        long sizeOfImage = imageInByte.length / 1024; //Image size
        Log.d("UImageLength", imageInByte.length + "");
        Log.e("UsizeOfImage===", sizeOfImage+"");
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory,  "id"+ farmerDetails.get(0).getCode()+ ".jpg");
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

            //  doFileUpload(new File(mCurrentPhotoPath));
            File imagefile = new File(mCurrentPhotoPath);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                CommonUtils.appendLog(LOG_TAG, "FIsaveImage", e.getLocalizedMessage() +"" );
                bytes = null;
            }
            try {
                Bitmap bm = BitmapFactory.decodeStream(fis);
                Log.e("============1",bm+"");
                getBytesFromBitmap(bm);
                //  Log.e("============2", CommonUtils.bitMaptoBase64(idproofsImageV.get()));
            } catch (Exception e) {
                bytes = null;
                CommonUtils.appendLog(LOG_TAG, "FIsaveImage", e.getLocalizedMessage() +"" );
            }
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "FIsaveImage", e1.getLocalizedMessage() +"" );
        }
        return "";
    }
    private String doFileUpload(File f){


        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "FIDoploadFile", ex.getLocalizedMessage() +"" );
        }

        //String encoded = Base64.encodeToString(bytes, 0);
        String finalString =android.util.Base64.encodeToString(bytes,0);



        return finalString;

// Receiving side

    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



    Animation.AnimationListener animationInListener
            = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            addIdProofBottomView.setVisibility(View.VISIBLE);
            addIdProof.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }
    };
    public void startAnimation() {
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_anim);
        logoMoveAnimation.setFillAfter(true);
        logoMoveAnimation.setFillEnabled(true);
        //  addIdProof.startAnimation(logoMoveAnimation);
        logoMoveAnimation.setAnimationListener(animationInListener);
        // headerLL.setVisibility(View.VISIBLE);
        // headerView.setVisibility(View.VISIBLE);
    }

    private void updateIdProofsAdapter(String idProofName, String enteredNumber) {


        idProofId = CommonUtils.getKeyFromValue(mainData, idProofName);
        Log.d("mahesh","ID Prof Key idProofId:"+idProofId);
         identityProof = new IdentityProof();
        identityProof.setIdProofTypeId(Integer.parseInt(idProofId));
        identityProof.setIdProofNumber(enteredNumber);
        idnumber = enteredNumber;

//        identityProofsList.add(identityProof);
//        idProofsRecyclerView.setVisibility(View.VISIBLE);
//        mainData = dataAccessHandler.getGenericDataa(Queries.getInstance().getTypeCdDmtData());
//        idProofsListAdapter.updateData(identityProofsList, mainData);

        // loadImageFromStorage("/storage/emulated/0/EasyFarm_IDS/TEGNTRALKON00007.jpg");

    }






    private FloatingActionButton backBT;
    private FloatingActionButton nextBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;


            case R.id.nextBT:
                if(validation()) {
                    //newaddIDProof();
                    if (mListener != null)
                        Log.d("Codeee", Adduser_fragment.value1 + "==========" + CommonConstants.FARMER_CODE);
                    code = Adduser_fragment.Farmer_code;
                    Log.e("code======", code);
                    mListener.onNextPressed(this);

                }
                break;

//        }
        }
    }

    private void newaddIDProof() {

        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());


        LinkedHashMap mapp = new LinkedHashMap();

        mapp.put("Id", 0);
        mapp.put("IdProofTypeId", idProofId);
        mapp.put("IdProofNumber", idnumber);
        mapp.put("FileName", "");
        mapp.put("FileLocation", "");
        mapp.put("FileExtension", extension);
        mapp.put("IsActive",true );
        mapp.put("IsVerified",true );
        mapp.put("CreatedByUserId", farmerDetails.get(0).getCreatedbyuserid());
        mapp.put("CreatedDate", timeStamp);
        mapp.put("UpdatedByUserId", SyncHomeActivity.User_id );
        mapp.put("UpdatedDate",timeStamp);
        mapp.put("UserCode",farmerDetails.get(0).getCode());
        mapp.put("ServerUpdatedStatus", 0);
        mapp.put("fileBytes", doFileUpload(new File(mCurrentPhotoPath)));

        List<LinkedHashMap> list = new ArrayList<>();

        list.add(mapp);
      for(int i=0; i<list.size(); i++)
      {
          Log.d("mahesh", "IDPROF DATA :: "+list.get(i).keySet().toString() +":"+list.get(i).values().toString());

      }

        boolean isalreadyexist = false;
        for (IdentityProof id: identityProofsList) {
            if(Integer.parseInt(idProofId )== id.getIdProofTypeId())
            {
                isalreadyexist = true;
            }
        }
        if (!isalreadyexist) {
            DataSavingHelper.saveRecordIntoActivityLog(getContext(), CommonConstants.Identity_Proof_Added);
            dataAccessHandler.insertMyData("IdentityProofs", list, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    super.execute(success, result, msg);

                    if (success) {
                        Log.v(LOG_TAG, "Identity Proof Details inserted Successfully");
                        CommonUtils.appendLog(LOG_TAG, "Identity Proof Details", "Inserted Successfully");


                        IdProofViews();
                        // clear data
                        spinn_idproof.setSelection(0);
                        idproofsEdttext.setText("");
                        idproofsImageView.setImageResource(R.mipmap.imgdefault);
                    }
                    else{
                        Log.d("Error Message", "Insertion Failed due to" + result);
                        CommonUtils.appendLog(LOG_TAG, "Identity Proof Details", "Inserted Failed due to" + result);

                    }

                }
            });
//dataAccessHandler.updateFarmerStatus(0, farmerDetails.get(0).getCode(), new ApplicationThread.OnComplete<String>() {
//    @Override
//    public void execute(boolean success, String result, String msg) {
//        Log.d("mahesh", "@@@@@ UPdated Status :"+success);
//    }
//});
//            dataAccessHandler.updateData("IdentityProofs", list, true, " where UserCode = " + "'"+farmerDetails.get(0).getCode()+"'", new ApplicationThread.OnComplete<String>() {
//                @Override
//                public void execute(boolean success, String result, String msg) {
//                    super.execute(success, result, msg);
//
//                    farmerDetails.get(0).setServerupdatedstatus(0);
//                }
//            });
            spinn_idproof.setSelection(0);
            idproofsEdttext.setText("");
            idproofsImageView.setImageResource(R.mipmap.imgdefault);
        }else{
            Toast.makeText(getContext(), "This Id Proof Already Exist", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validation() {



        if (identityProofsList.size() == 0) {
            UiUtils.showCustomToastMessage("Please Add Id Proof", getActivity(), 1);

            return false;
        }

        if (identityProofsList.get(0).getIdProofTypeId()!=6) {
            UiUtils.showCustomToastMessage("Please Add Aadhar Details", getActivity(), 1);

            return false;
        }



        return true;
    }
//
//

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepTwoListener) {
            mListener = (OnStepTwoListener) context;
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

    @Override
    public void onEditClicked(int position) {
        Log.v(LOG_TAG, "@@@ edit clicked " + position);
        showEditDialog(position);
    }

    private void showEditDialog(final int position){
        mainData = dataAccessHandler.getGenericDataa(Queries.getInstance().getTypeCdDmtData());
        final EditText idEdit = new EditText(getActivity());
        final String title = mainData.get(String.valueOf(identityProofsList.get(position).getIdProofTypeId()));
        idEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        idEdit.setText(identityProofsList.get(position).getIdProofNumber());
        @SuppressLint("RestrictedApi") final AlertDialog.Builder idProofsBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("Edit")
                .setMessage(title)
                .setView(idEdit)
                //  .setView(idEdit, 20, 0, 20, 0)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog idProofsDialog = idProofsBuilder.create();
        idProofsDialog.setCancelable(false);
        idProofsDialog.setCanceledOnTouchOutside(false);
        idProofsDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button muteBtn = idProofsDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                muteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(idEdit.getText().toString())) {
                            if (!TextUtils.isEmpty(title) && title.equalsIgnoreCase(getResources().getString(R.string.adhar_number))
                                    && !TextUtils.isDigitsOnly(idEdit.getText().toString())) {
                                Toast.makeText(getActivity(), "Aadhar card accepts only numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            IdentityProof identityProof = new IdentityProof();
                            identityProof.setIdProofTypeId(identityProofsList.get(position).getIdProofTypeId());
                            identityProof.setIdProofNumber(idEdit.getText().toString());
                            identityProofsList.set(position, identityProof);
                            idProofsListAdapter.updateData(identityProofsList, mainData);
                            idProofsDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Please enter id proof value", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        idProofsDialog.show();

    }



    @Override
    public void onDeleteClicked(int position) {
        Log.v(LOG_TAG, "@@@ delete clicked " + position);
//        identityProofsList.remove(position);

        showIDproofDeleteDialogue(position);
//        idProofsListAdapter.notifyDataSetChanged();

    }

    public void showIDproofDeleteDialogue(final int po) {
        new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirm)
                .setMessage("Do you want to delete the Id Proof")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dataAccessHandler.deleteIDProfRow(identityProofsList.get(po).getId(),identityProofsList.get(po).getUserCode(),identityProofsList.get(po).getIdProofTypeId().toString(), new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);
                            }
                        });
                        IdProofViews();
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
    public interface OnStepTwoListener {
        void onBackPressed(Fragment fragment);

        void onNextPressed(Fragment fragment);

    }

    class IdProfs{
        int TypeID;
        String TyIdName;
        String imgBase64;

        public IdProfs(int typeID, String tyIdName, String imgBase64) {
            TypeID = typeID;
            TyIdName = tyIdName;
            this.imgBase64 = imgBase64;
        }

        public int getTypeID() {
            return TypeID;
        }

        public void setTypeID(int typeID) {
            TypeID = typeID;
        }

        public String getTyIdName() {
            return TyIdName;
        }

        public void setTyIdName(String tyIdName) {
            TyIdName = tyIdName;
        }

        public String getImgBase64() {
            return imgBase64;
        }

        public void setImgBase64(String imgBase64) {
            this.imgBase64 = imgBase64;
        }
    }

}
