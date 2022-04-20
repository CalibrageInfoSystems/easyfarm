package com.cis.easyfarm.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cis.easyfarm.BuildConfig;
import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.FileChooser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class Signup_thirdpage extends Fragment implements View.OnClickListener{
    public static String LOG_TAG = Signup_thirdpage.class.getSimpleName();
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton backBT;
    private Button nextBT, Adhar_doc,experience_doc,local_doc_upload;
    private OnStepThreeListener mListener;
    ArrayList<String> extensions;
    String filename,USER_NAME;
    private static long MAX_FILE_SIZE = 5;
    // String[] gender = { "Male", "Female" };
    private AlertDialog alertDialog;
    Bitmap bitmap;
    String docString, extension, filePath;
    ImageView imageView,imageView2,deleteIcon,deleteIcon2;
    LinearLayout maindoc,maindoc2;
    TextView docText,docText2;
    int  pos;
    EditText  facebook_edittxt;
    public Signup_thirdpage() {
        // Required empty public constructor
    }


    public static Signup_thirdpage newInstance(String param1, String param2) {
        Signup_thirdpage fragment = new Signup_thirdpage();
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
        view = inflater.inflate(R.layout.fragment_signup_thirdpage, container, false);

        intviews();
        setviews();

        return view;


    }

    private void intviews() {
        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
        Adhar_doc =view.findViewById(R.id.adhar_upload);
        experience_doc= view.findViewById(R.id.experiance_doc_upload);
        local_doc_upload = view.findViewById(R.id.local_doc_upload);
        imageView = view.findViewById(R.id.imageView);
        imageView2=view.findViewById(R.id.imageView2);
        maindoc = view.findViewById(R.id.maindoc);
        maindoc2 = view.findViewById(R.id.maindoc2);
        deleteIcon = view.findViewById(R.id.deleteIcon);
        deleteIcon2 =view.findViewById(R.id.deleteIcon2);
        docText = view .findViewById(R.id.docText);
        docText2= view .findViewById(R.id.docText2);
        facebook_edittxt=view.findViewById(R.id.facebook_edittxt);


    }
    private void setviews() {

        USER_NAME=Signup_fragment.username;
        Log.d(LOG_TAG,"===***"+USER_NAME);
        Adhar_doc.setOnClickListener(this);
        local_doc_upload.setOnClickListener(this);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // selected file to be remove
                showConformationDialog(pos);
            }
        });
        deleteIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // selected file to be remove
                showConformationDialog2(pos);
            }
        });

    }

    private void showConformationDialog2(int pos) {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(getContext());
        }


        builder.setTitle(" Delete Entry ")
                .setMessage(" Ary you sure you want delete this entry ")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        imageView2.setImageBitmap(null);
                        docText2.setText("");
                        filePath = null;
                        deleteIcon2.setVisibility(View.GONE);
                        maindoc2.setVisibility(View.GONE);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        User user = new  User("","","");
        user.setphone(facebook_edittxt.getText().toString());
        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;
            case R.id.adhar_upload:
                Adhar_doc.setBackgroundResource(R.drawable.button_bg);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                    // PopUpDailog to Select Resume
                    startDialog();

//
                }


                break;
            case R.id.local_doc_upload:
                local_doc_upload.setBackgroundResource(R.drawable.button_bg);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                    // PopUpDailog to Select Resume
                    startDialog2();

//
                }


                break;

            case  R.id.deleteIcon:
                showConformationDialog(pos);
            case R.id.nextBT:

                if (mListener != null)
                    mListener.onNextPressed(this,user);
                break;
        }
    }

    private void startDialog2() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View dialogRootView = layoutInflater.inflate(R.layout.dialog_adddocuments, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        Button  cancel_btn =dialogRootView.findViewById(R.id.cancel_btn);
        TextView dialogMessage = dialogRootView.findViewById(R.id.dialogMessage);

        /**
         * @param OnClickListner
         */
        dialogMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FileChooser.class);
                extensions = new ArrayList<String>();
                extensions.add(".pdf");
                extensions.add(".doc");
                extensions.add(".docx");
                extensions.add(".txt");
                extensions.add(".rtf");

                intent.putStringArrayListExtra("filterFileExtension", extensions);
                startActivityForResult(intent, 2);
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

    private void showConformationDialog(final int pos) {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(getContext());
        }


        builder.setTitle(" Delete Entry ")
                .setMessage(" Ary you sure you want delete this entry ")
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


        private void startDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View dialogRootView = layoutInflater.inflate(R.layout.dialog_adddocuments, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        Button  cancel_btn =dialogRootView.findViewById(R.id.cancel_btn);
        TextView dialogMessage = dialogRootView.findViewById(R.id.dialogMessage);

        /**
         * @param OnClickListner
         */
        dialogMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FileChooser.class);
                extensions = new ArrayList<String>();
                extensions.add(".pdf");
                extensions.add(".doc");
                extensions.add(".docx");
                extensions.add(".txt");
                extensions.add(".rtf");

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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                       imageView.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }

                filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                    docText.setText(filename);
                    maindoc.setVisibility(View.VISIBLE);
                    deleteIcon.setVisibility(View.VISIBLE);
                Glide.with(getContext())
                        .load(R.drawable.notifications_bell_button)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);

                }

            if (requestCode == 2) {
                filePath = data.getStringExtra("fileSelected");
                File file = new File(filePath);

                long fileSizeInBytes = file.length();
                long fileSizeInKB = fileSizeInBytes / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;

                // selected file is converting into base64 conversion
                convertFileToByteArray2(filePath);

                if (fileSizeInMB < MAX_FILE_SIZE) {

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                        imageView2.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }

                filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                docText2.setText(filename);
                maindoc2.setVisibility(View.VISIBLE);
                deleteIcon2.setVisibility(View.VISIBLE);

            }

            }


//            Picasso.with(getContext())
//                    .load(filename)
//                    .placeholder( R.drawable.document_icon)
//                    .into(imageView);


        }

    private void convertFileToByteArray2(String filePath) {
        String val = null;

        byte[] byteArray = null;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                extension = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("."));
                Log.d("extension===",extension);

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

                    Log.e("=====",docString);
                    if (docString != null) {
                        Log.e("base64String===", docString);

                        byte[] inData = Base64.decode(docString, 0);
//
                        File root = android.os.Environment.getExternalStorageDirectory();
                        File filee = new File(root.getAbsolutePath() + "/Easy Farm/Users/"+USER_NAME);
                        if (!filee.exists()) {
                            filee.mkdirs();
                        }

                        try {

                            File file = new File(filee,"Voter id" + extension);
                            Log.e("file ==", file.getAbsolutePath());
                            FileOutputStream outData = new FileOutputStream(file);
                            outData.write(inData);
                            outData.close();
                            String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString()));
                            if (mime == null) {
                                mime = "application/octet-stream";
                            }



                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    Toast.makeText(getContext(), "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }


            }

          //  return val;

        } catch (IOException e) {
            e.printStackTrace();
           // return val;
        }


    }


    // selected file is converting into base64 conversion
    private String convertFileToByteArray(String filePath) {
        String val = null;

        byte[] byteArray = null;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                extension = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("."));
                Log.d("extension===",extension);

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

                    Log.e("=====",docString);
                    if (docString != null) {
                        Log.e("base64String===", docString);

                        byte[] inData = Base64.decode(docString, 0);
//
                        File root = android.os.Environment.getExternalStorageDirectory();
                        File filee = new File(root.getAbsolutePath() + "/Easy Farm/Users/"+USER_NAME);
                        if (!filee.exists()) {
                            filee.mkdirs();
                        }

                        try {

                            File file = new File(filee,"Adhar" + extension);
                            Log.e("file ==", file.getAbsolutePath());
                            FileOutputStream outData = new FileOutputStream(file);
                            outData.write(inData);
                            outData.close();
                            String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString()));
                            if (mime == null) {
                                mime = "application/octet-stream";
                            }



                        } catch (IOException e) {
                         e.printStackTrace();
                        }

                    }

                } else {
                    Toast.makeText(getContext(), "file_size_is_must_be_less_than_5MB", Toast.LENGTH_SHORT).show();
                }


            }

            return val;

        } catch (IOException e) {
            e.printStackTrace();
            return val;
        }


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

    public interface OnStepThreeListener {
        void onBackPressed(Fragment fragment);
        void onNextPressed(Fragment fragment,User data);
    }
}