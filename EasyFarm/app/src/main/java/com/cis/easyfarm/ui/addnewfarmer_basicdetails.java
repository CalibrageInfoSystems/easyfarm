package com.cis.easyfarm.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.BaseFragment;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.model.GetGender;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.model.Loginobject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class addnewfarmer_basicdetails extends CommonFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText Fathername_edittxt,fullname, contact_number,address, address2;
    private EditText userNameEdt,First_name,middle_name,last_name,date_birth,email,password,confirmpass_edittxt;
    TextInputLayout name_layout,firstname_label,lastname_label,email_label,pass_label,father_name_layout, middleName_layout,confirm_pass;
    TextInputLayout  mobile_label,address_label, address2_label,date_label;
    String[] gender = { "Select Gender ","Male", "Female" };
    Spinner spin_gender,spin_state,spin_dist,spin_mandal,spin_village,spin_roll;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    Button login;
    EditText Existingusername, userpassword;
    TextView signup;
    TextInputLayout username_layout,userpass_label;
    String dob,birthdate;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    List<String>get_gender = new ArrayList<String>();
    List<Integer> get_gender_Id = new ArrayList<Integer>();

    List<String>get_role = new ArrayList<String>();
    List<Integer> get_role_Id = new ArrayList<Integer>();

    int state_id,dist_id,mandal_id,village_id,gender_id,Exsting_gender_id;
    String role_id,Exsting_gender;
    public static String user_name,first_name,middlename,lastname, Existing_villagename,Existing_role,Exsting_mandal,Existing_dist,Existing_state,
            mobile_number,Email, fatherguardianname,Existing_address,Existing_address2, adress1,adress2,dateof_birth, pasword,roleid;
    public static int stateid,distid,mandalid,villageid,genderid;
    public static String Google_fname,Google_lname,Google_email;
    public static String FB_fname,FB_lname,FB_Username;
    TextView signin;
    String pass,Confirm_pass;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private RadioGroup radiouserGroup;
    private RadioButton radionewuser,radioexistinguser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog dialog;


    public  static String datetimevaluereq;
    private addnewfarmer_basicdetails.OnStepOneListener mListener;

    public addnewfarmer_basicdetails() {
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
    public static addnewfarmer_basicdetails newInstance(String param1, String param2) {
        addnewfarmer_basicdetails fragment = new addnewfarmer_basicdetails();
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
        view = inflater.inflate(R.layout.fragment_addnewfarmer_basicdetails, container, false);



        init();
        setViews();

        return view;
    }


    private void init() {
        signin = view.findViewById(R.id.signin);
        name_layout =view.findViewById(R.id.name_layout);
        middleName_layout = view.findViewById(R.id.middleName_layout);
        confirm_pass =view.findViewById(R.id.confirm_pass_label);
        mobile_label = view.findViewById(R.id.mobile_label);
        email_label = view.findViewById(R.id.email_label);
        father_name_layout = view.findViewById(R.id.father_name_layout);
        fullname = view.findViewById(R.id.username_edittxt);
        spin_gender = view.findViewById(R.id.spin_gender);
        contact_number = view.findViewById(R.id.phone_edittxt);
        email = view.findViewById(R.id.Email_edittxt);
        confirmpass_edittxt=view.findViewById(R.id.confirmpass_edittxt);
        firstname_label = view.findViewById(R.id.firstname_label);
        name_layout =view.findViewById(R.id.name_layout);
        lastname_label = view.findViewById(R.id.lastname_label);
        email_label = view.findViewById(R.id.email_label);
        pass_label  = view.findViewById(R.id.pass_label);
        Fathername_edittxt = view.findViewById(R.id.Fathername_edittxt);
        userNameEdt = view.findViewById(R.id.name_edittxt);
        First_name = view.findViewById(R.id.fname_edittxt);
        middle_name =  view.findViewById(R.id.m_edittxt);
        last_name = view.findViewById(R.id.L_edittxt);
        date_birth =  view.findViewById(R.id.date_edittxt);
        email = view.findViewById(R.id.Email_edittxt);
        password = view.findViewById(R.id.pass_edittxt);
        date_label = view.findViewById(R.id.date_label);
        radiouserGroup = (RadioGroup)view.findViewById(R.id.radiouser);
        radionewuser =(RadioButton) view.findViewById(R.id.radionewuser);
        radioexistinguser =(RadioButton)view.findViewById(R.id.radioexistinguser);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();


    }
    private void setViews() {
        password.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));
        pass_label.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));
        confirmpass_edittxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));
        confirm_pass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));

        radioexistinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    loginpoup();
                }
                else{
                    // Do your coding
                }
            }
        });
        int selectedId = radiouserGroup.getCheckedRadioButtonId();
        date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_birth.setText(day + "/" + (month + 1) + "/" + year);
                                Log.d("DateofBirth", "Date of Birth is " + date_birth.getText().toString());
                                // date_birth.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        if (isOnline(getContext())) {

            getgender();

        }
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
        spin_gender.setOnItemSelectedListener(this);






// if(activity.getFBData()!=null){
//    FB_fname = resultFB.getString("valF1") == null ?"no data f1":resultFB.getString("valF1");
//    FB_lname = resultFB.getString("valF2")  == null ?"no data f2":resultFB.getString("valF2");
//    FB_Username = resultFB.getString("valF3")  == null ?"no data f3":resultFB.getString("valF3");
//            First_name.setText(FB_fname);
//            last_name.setText(FB_lname);
//            userNameEdt.setText(FB_Username);
//
//        }

        date_birth.addTextChangedListener(new ValidationTextWatcher(date_birth));
        Fathername_edittxt.addTextChangedListener(new ValidationTextWatcher(Fathername_edittxt));
        contact_number.addTextChangedListener(new ValidationTextWatcher(contact_number));
        confirmpass_edittxt.addTextChangedListener(new ValidationTextWatcher(confirmpass_edittxt));
        //  email.addTextChangedListener(new ValidationTextWatcher(email));
        // fullname.addTextChangedListener(new ValidationTextWatcher(fullname));
        userNameEdt.addTextChangedListener(new ValidationTextWatcher(userNameEdt));
        First_name.addTextChangedListener(new ValidationTextWatcher(First_name));
        last_name.addTextChangedListener(new ValidationTextWatcher(last_name));
        password.addTextChangedListener(new ValidationTextWatcher(password));



    }

    private void loginpoup() {

        dialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register_exist);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");
        username_layout=dialog.findViewById(R.id.name_layout);
        userpass_label =dialog.findViewById(R.id.pass_label);
        login = dialog.findViewById(R.id.loginBtn);

        Existingusername = dialog.findViewById(R.id.username_edittxt);
        userpassword = dialog.findViewById(R.id.pass_edittxt);
        userpassword.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));
        // pass_label.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/rubik_regular.ttf"));
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (popupvalidations()) {
                    if (isOnline(getContext())) {

                        user_login();
                    }
// }
                    else {
                        showDialog(getActivity(), getResources().getString(R.string.Internet));
                        //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }

    private boolean popupvalidations() {
        if (TextUtils.isEmpty(Existingusername.getText().toString())) {
            // userNameEdt.setError(getString(R.string.err_please_enter_username));
            username_layout.setError(getResources().getString(R.string.err_please_enter_username));
            //  showDialog(SignupActivity.this, getResources().getString(R.string.err_please_enter_username));
            return false;
        }
        else{

            username_layout.setErrorEnabled(false);
        }


        if (TextUtils.isEmpty(userpassword.getText().toString().trim())) {
            userpass_label.setError(getResources().getString(R.string.err_please_password));
            return false;
        }
        else {
            userpass_label.setErrorEnabled(false);
        }
        return true;
    }

    private void user_login() {
        mdilogue.show();
        JsonObject object = loginObject();
        ApiService service = ServiceFactory.createRetrofitService(getActivity(), ApiService.class);
        mSubscription = service.getuserLogin(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                        mdilogue.dismiss();
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(final LoginResponse loginResponse) {
                        mdilogue.dismiss();
                        if (loginResponse.getIsSuccess()) {
                            String Exit_username = loginResponse.getResult().getUserInfos().getUserName();
                            String Exit_password = userpassword.getText().toString();
                            Gson gson = new Gson();
                            final String myJson = gson.toJson(loginResponse);
                            Log.e("myJson===", myJson);

                            if (loginResponse.getResult().getUserInfos().getStatusTypeId() != 104) {
                                Intent intent = new Intent(getActivity(), Exsting_Farmer_list.class);
                                intent.putExtra("username", Exit_username);
                                intent.putExtra("password", Exit_password);


                                startActivity(intent);
                            } else {
                                showDialog(getActivity(), "New Roles can be added to Converted Users only");
                            }
                        }
                        //  dialog.cancel();
                        else {

                            showDialog(getActivity(),loginResponse.getEndUserMessage() );
                        }
                        dialog.cancel();
                    }


                });}


    private JsonObject loginObject() {
        Loginobject requestModel = new Loginobject();
        requestModel.setUserName(Existingusername.getText().toString());
        requestModel.setPassword(userpassword.getText().toString());


        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }



    private void getgender() {
        get_gender.clear();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getender(Config.Getgender )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetGender>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetGender getGender) {
                        if (getGender.getListResult() != null) {
                            get_gender.add("Select Gender");
                            for (GetGender.ListResult data : getGender.getListResult()
                            ) {
                                get_gender.add(data.getDesc());
                                get_gender_Id.add(data.getTypeCdId());
                            }


                            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,get_gender);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_gender.setAdapter(aa);
                            Log.e("Exsting_genderid===1",Exsting_gender_id+"");

                            if( Exsting_gender_id != 0)
                            {
                                spin_gender.setSelection(Exsting_gender_id);
                            }


                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }








    public void showDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
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

    private boolean validations() {


        pass = password.getText().toString();
        Confirm_pass =confirmpass_edittxt.getText().toString();

        if (TextUtils.isEmpty(userNameEdt.getText().toString())) {

            name_layout.setError(getResources().getString(R.string.err_please_enter_username));

            return false;
        }
        else if (TextUtils.isEmpty(contact_number.getText().toString().trim())) {
            mobile_label.setError(getResources().getString(R.string.err_please_phone));

            return false;
        }
        else if (TextUtils.isEmpty(password.getText().toString().trim())) {

            pass_label.setError(getResources().getString(R.string.err_please_password));

            return false;
        } else if (TextUtils.isEmpty(confirmpass_edittxt.getText().toString().trim())) {

            confirm_pass.setError(getResources().getString(R.string.err_please_confirm_password));

            return false;
        }
        else if(!pass.equals(Confirm_pass)){
            confirm_pass.setError(getResources().getString(R.string.error_confirm));
            return false;
        }
//        else  if (TextUtils.isEmpty(middle_name.getText().toString().trim())) {
//            middleName_layout.setError("Please Enter Middle Name");
//            return false;
//        }
        else if (TextUtils.isEmpty(First_name.getText().toString().trim())) {
            firstname_label.setError(getResources().getString(R.string.err_please_F_name));
            return false;
        }
        else if (TextUtils.isEmpty(last_name.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lastname_label.setError(getResources().getString(R.string.err_please_L_name));

            return false;
        }  else if (TextUtils.isEmpty(Fathername_edittxt.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            father_name_layout.setError("Please Enter Father/Guardian Name");

            return false;
        }
        else if (spin_gender.getSelectedItemPosition() == 0) {

            showDialog(getActivity(), getResources().getString(R.string.select_gender));
            return false;
        }
        else if (TextUtils.isEmpty(date_birth.getText().toString().trim())) {
            date_label.setError(getString(R.string.err_please_date));
            //   showDialog(SignUpActicity.this, getResources().getString(R.string.err_please_date));
            return false;
        }


//        else if (TextUtils.isEmpty(email.getText().toString().trim())) {
//            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
//            email_label.setError(getResources().getString(R.string.err_please_email));
//            return false;
//        }
//        else if(!EmailValidator.getInstance().validate(email.getText().toString().trim())){
//            email_label.setError(getResources().getString(R.string.err_please_valid_email));
//            return false;
//        }






        return true;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String  select_gender = spin_gender.getItemAtPosition(spin_gender.getSelectedItemPosition()).toString();
        if (position != 0) {

            gender_id = get_gender_Id.get(spin_gender.getSelectedItemPosition()-1);
            Exsting_gender_id= gender_id;

            Log.e("Exsting_genderid===2",Exsting_gender_id+"");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                    validateusename();
                    break;
                case R.id.phone_edittxt:
                    validate_mobile();
                    break;
                case R.id.address_edittxt:
                    validate_address();
                    break;
                case R.id.username_edittxt:
                    validate_name();
                    break;
                case R.id.date_edittxt:
                    validate_dob();
                    break;
//                case R.id.m_edittxt:
//                    validate_middlename();
//                    break;
                case R.id.Fathername_edittxt:
                    validate_faatherguardianname();
                    break;
                case R.id.address2_edittxt:
                    validate_address2();
                    break;

                case R.id.fname_edittxt:
                    validate_firstname();
                    break;
                case R.id.L_edittxt:
                    validate_lastname();
                    break;

                case R.id.pass_edittxt:
                    validate_password();
                    break;
                case R.id.confirmpass_edittxt:
                    validaeconfirmpassword();
                    break;


            }
        }
    }

    private boolean validaeconfirmpassword() {
        if (TextUtils.isEmpty(confirmpass_edittxt.getText().toString().trim())) {
            confirm_pass.setError(getResources().getString(R.string.err_please_confirm_password));
            requestFocus(confirmpass_edittxt);
        }
//        else if(!pass.equals(Confirm_pass)){
//            confirm_pass.setError(getResources().getString(R.string.error_confirm));
//            requestFocus(confirmpass_edittxt);
//            return false;
//        }
        else {
            confirm_pass.setErrorEnabled(false);
        }
        return true;
    }

    private void validate_dob() {
        if (TextUtils.isEmpty(date_birth.getText().toString().trim())) {
            date_label.setError(getResources().getString(R.string.err_please_date));
            requestFocus(date_birth);
        }
        else {
            date_label.setErrorEnabled(false);
        }

    }

    private boolean validate_password() {
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            pass_label.setError(getResources().getString(R.string.err_please_password));
            requestFocus(password);
        }
        else if( password.length() < 6)
        {
            pass_label.setError("Password must have 6 Characters minimum");
            return false;
        }
        else {
            pass_label.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validate_lastname() {
        if (TextUtils.isEmpty(last_name.getText().toString())) {
            lastname_label.setError(getResources().getString(R.string.err_please_L_name));
            requestFocus(last_name);
        } else {
            lastname_label.setErrorEnabled(false);

        }
        return true;
    }


//    private boolean validate_middlename() {
//        if (TextUtils.isEmpty(middle_name.getText().toString())) {
//            middleName_layout.setError(getResources().getString(R.string.err_please_L_name));
//            requestFocus(middle_name);
//        } else {
//            middleName_layout.setErrorEnabled(false);
//
//        }
//        return true;
//    }

    private boolean validate_firstname() {
        if (TextUtils.isEmpty(First_name.getText().toString())) {
            firstname_label.setError(getResources().getString(R.string.err_please_F_name));
            requestFocus(First_name);
        } else {
            firstname_label.setErrorEnabled(false);

        }
        return true;
    }

    private boolean validate_faatherguardianname() {
        if (TextUtils.isEmpty(Fathername_edittxt.getText().toString())) {
            father_name_layout.setError(getResources().getString(R.string.err_please_F_name));
            requestFocus(Fathername_edittxt);
        } else {
            father_name_layout.setErrorEnabled(false);

        }
        return true;
    }



    private boolean validateusename() {
        if (TextUtils.isEmpty(userNameEdt.getText().toString())) {
            name_layout.setError(getResources().getString(R.string.err_please_enter_username));
            requestFocus(userNameEdt);
            return false;

        }
        else if( userNameEdt.length() < 6)
        {
            name_layout.setError("User Name must have 6 Characters minimum");
            return false;
        }

        else {
            name_layout.setErrorEnabled(false);
        }
        return true;
    }
//
//    private boolean validate_email() {
//        if (TextUtils.isEmpty(email.getText().toString().trim())) {
//            email_label.setError(getResources().getString(R.string.err_please_email));
//            requestFocus(email);
//        }
//        else  if(!EmailValidator.getInstance().validate(email.getText().toString().trim())){
//            email_label.setError(getResources().getString(R.string.err_please_valid_email));
//            requestFocus(email);
//
//        }
//        else {
//            email_label.setErrorEnabled(false);
//        }
//        return true;
//    }

    private boolean validate_name() {
        if (TextUtils.isEmpty(fullname.getText().toString())) {
            name_layout.setError(getResources().getString(R.string.err_please_Full_name));
            requestFocus(fullname);
        } else {
            name_layout.setErrorEnabled(false);

        }
        return true;
    }



    private boolean validate_address() {
        if(TextUtils.isEmpty(address.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address);

        }
        else {
            address_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_address2() {
        if(TextUtils.isEmpty(address2.getText().toString().trim())){
            address2_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address2);

        }
        else {
            address2_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_mobile() {
        if(TextUtils.isEmpty(contact_number.getText().toString().trim())){
            mobile_label.setError(getResources().getString(R.string.err_please_phone));
            requestFocus(contact_number);

        }
        else if( contact_number.length() < 10)
        {
            mobile_label.setError("Mobile Number must have 10 Digits");
            return false;
        }

        else {
            mobile_label.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
        signin.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Exsting_genderid===3",Exsting_gender_id+"");
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        user_name = userNameEdt.getText().toString();


        first_name = First_name.getText().toString();
        middlename = middle_name.getText().toString();
        lastname = last_name.getText().toString();
        mobile_number = contact_number.getText().toString();
        Email = email.getText().toString();
        fatherguardianname = Fathername_edittxt.getText().toString();
        adress1  = Existing_address;
        Log.e("adress1======",adress1+"============"+Existing_address2);
        dateof_birth = date_birth.getText().toString();
        pasword = password.getText().toString();
//        roleid = role_id;
//        stateid = state_id;
//        distid = dist_id;
//        mandalid = mandal_id;
//        villageid = village_id;
        genderid = gender_id;
        Log.e("Exsting_genderid===4",Exsting_gender_id+"");


        switch (view.getId()) {
            case R.id.nextBT:
                if (validations()) {
                    if (mListener != null)
                        mListener.onNextPressed(this);
                }
                break;
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof addnewfarmer_basicdetails.OnStepOneListener) {
            mListener = (addnewfarmer_basicdetails.OnStepOneListener) context;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //  outState.putString("message", "This is a saved message");
        outState.putInt("firstSpinnerPosition", genderid);
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

}
