package com.cis.easyfarm.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.cis.easyfarm.ui.EmailValidator;
import com.cis.easyfarm.ui.userAccount.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Signup_fragment extends Fragment implements View.OnClickListener {
    public static String LOG_TAG = Signup_fragment.class.getSimpleName();
    public static String username ;
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnStepOneListener mListener;
    private FloatingActionButton nextBT;
    TextView signin;
    private EditText userNameEdt,First_name,middle_name,last_name,date_birth,email,password,confirm_passwors;
    TextInputLayout name_layout,firstname_label,lastname_label,email_label,pass_label,confirm_pass_label;
    String pass,cpass;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    User user;
    public Signup_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Signup_fragment newInstance(String param1, String param2) {
        Signup_fragment fragment = new Signup_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d(LOG_TAG,"======69"+args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_fragment, container, false);

        intviews();
        setviews();

        return view;
    }

    private void intviews() {
        nextBT = view.findViewById(R.id.nextBT);
        signin = view.findViewById(R.id.signin);
        firstname_label =view.findViewById(R.id.firstname_label);
        name_layout =view.findViewById(R.id.name_layout);
        lastname_label = view.findViewById(R.id.lastname_label);
        email_label = view.findViewById(R.id.email_label);
        pass_label  =  view.findViewById(R.id.pass_label);
      //  confirm_pass_label =view.findViewById(R.id.confirm_pass_label);
        userNameEdt =  view.findViewById(R.id.name_edittxt);
        First_name = view.findViewById(R.id.fname_edittxt);
        middle_name =  view.findViewById(R.id.m_edittxt);
        last_name = view.findViewById(R.id.L_edittxt);
        date_birth =  view.findViewById(R.id.date_edittxt);
        email = view.findViewById(R.id.Email_edittxt);
        password =  view.findViewById(R.id.pass_edittxt);
     //   confirm_passwors = view.findViewById(R.id.confirm_pass_edittxt);
    }
    private void setviews() {

        userNameEdt.addTextChangedListener(new ValidationTextWatcher(userNameEdt));
        First_name.addTextChangedListener(new ValidationTextWatcher(First_name));
        last_name.addTextChangedListener(new ValidationTextWatcher(last_name));
        email.addTextChangedListener(new ValidationTextWatcher(email));
        password.addTextChangedListener(new ValidationTextWatcher(password));
        //confirm_passwors.addTextChangedListener(new ValidationTextWatcher(confirm_passwors));

        date_birth.setOnClickListener(new View.OnClickListener() {
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
                                date_birth.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {
        username= userNameEdt.getText().toString();
      User user = new  User("","","");
        user.setUserName(userNameEdt.getText().toString());
        user.setemail(email.getText().toString());
        user.setpassword(password.getText().toString());
        switch (view.getId()) {
            case R.id.nextBT:
                if(validation()) {
                    if (mListener != null)
                        mListener.onNextPressed(this,user);
                    Log.e(LOG_TAG,"===="+user);
                }
                break;
            case R.id.signin:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                break;
        }

    }

    private boolean validation() {

        pass=password.getText().toString();
      //  cpass=confirm_passwors.getText().toString();
        if (TextUtils.isEmpty(userNameEdt.getText().toString())) {
            // userNameEdt.setError(getString(R.string.err_please_enter_username));
            name_layout.setError(getResources().getString(R.string.err_please_enter_username));
            //  showDialog(getActivity(), getResources().getString(R.string.err_message));
            return false;
        }

        else  if (TextUtils.isEmpty(First_name.getText().toString().trim())) {
            firstname_label.setError(getResources().getString(R.string.err_please_F_name));
            return false;
        }
        else if (TextUtils.isEmpty(last_name.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            lastname_label.setError(getResources().getString(R.string.err_please_L_name));

            return false;
        }
        else if (TextUtils.isEmpty(date_birth.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            // showDialog(SignupActivity.this, getResources().getString(R.string.err_please_date));
            return false;
        }
        else if (TextUtils.isEmpty(email.getText().toString().trim())) {
            //  passwordEdt.setError(getString(R.string.err_please_enter_password));
            email_label.setError(getResources().getString(R.string.err_please_email));
            return false;
        }
        else if(!EmailValidator.getInstance().validate(email.getText().toString().trim())){
            email_label.setError(getResources().getString(R.string.err_please_valid_email));
        }
        else if (TextUtils.isEmpty(password.getText().toString().trim())) {

            pass_label.setError(getResources().getString(R.string.err_please_password));

            return false;
        }
//        else if (TextUtils.isEmpty(confirm_passwors.getText().toString().trim())) {
//            confirm_pass_label.setError(getResources().getString(R.string.err_please_confirm_password));
//            return false;
//        }
//
//
//        else if(!pass.equals(cpass)){
//            confirm_pass_label.setError(getResources().getString(R.string.err_please_match_password));
//
//            return false;
//        }
//
//        if(TextUtils.isEmpty(userNameEdt.getText().toString()) || TextUtils.isEmpty(First_name.getText().toString().trim()) ||
//                TextUtils.isEmpty(last_name.getText().toString().trim()) || TextUtils.isEmpty(date_birth.getText().toString().trim() ) || TextUtils.isEmpty(email.getText().toString().trim())
//                || TextUtils.isEmpty(password.getText().toString().trim()) || TextUtils.isEmpty(confirm_passwors.getText().toString().trim())){
//            showDialog(getActivity(), getResources().getString(R.string.err_message));
//            return false;
//        }

        return true;
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
        void onNextPressed(Fragment fragment, User data);

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
                case R.id.fname_edittxt:
                    validate_firstname();
                    break;
                case R.id.L_edittxt:
                    validate_lastname();
                    break;
                case R.id.Email_edittxt:
                    validate_Email();
                    break;
                case R.id.pass_edittxt:
                    validate_password();
                    break;
                case R.id.confirm_pass_edittxt:
                    validate_confirm_password();
                    break;



            }
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validate_confirm_password() {
        pass=password.getText().toString();
        cpass=confirm_passwors.getText().toString();
        if (TextUtils.isEmpty(confirm_passwors.getText().toString().trim())) {
            confirm_pass_label.setError(getResources().getString(R.string.err_please_confirm_password));
            requestFocus(confirm_passwors);
        }


        else if(!pass.equals(cpass)){
            confirm_pass_label.setError( getResources().getString(R.string.err_please_match_password));
            requestFocus(confirm_passwors);

        }
        else {
            confirm_pass_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_password() {
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            pass_label.setError(getResources().getString(R.string.err_please_password));
            requestFocus(password);
        }
        else {
            pass_label.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_Email() {
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email_label.setError(getResources().getString(R.string.err_please_email));
            requestFocus(email);
        }
        else  if(!EmailValidator.getInstance().validate(email.getText().toString().trim())){
            email_label.setError(getResources().getString(R.string.err_please_valid_email));
            requestFocus(email);

        }
        else {
            email_label.setErrorEnabled(false);
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


    private boolean validate_firstname() {
        if (TextUtils.isEmpty(First_name.getText().toString())) {
        firstname_label.setError(getResources().getString(R.string.err_please_F_name));
        requestFocus(First_name);
    } else {
        firstname_label.setErrorEnabled(false);

    }
        return true;
}



    private boolean validateusename() {
        if (TextUtils.isEmpty(userNameEdt.getText().toString())) {
            name_layout.setError(getResources().getString(R.string.err_please_enter_username));
            requestFocus(userNameEdt);
            return false;

        }
        else {
            name_layout.setErrorEnabled(false);
        }
        return true;
    }

}