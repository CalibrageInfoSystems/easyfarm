package com.cis.easyfarm.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.Spinner;

import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;


public class Signup_secondpage extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String LOG_TAG = Signup_secondpage.class.getSimpleName();
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText  phone_numer,Alt_phone_edittxt,address_edittxt, address_edittxt2,village_edittxt,mandal_edittxt,pin_edittxt,dist_edittxt,state_edittxt;
    TextInputLayout mobile_label,address_label, address_label2,village_label,mandal_label,pin_label,dist_label,state_label;
    String spin_val;

    String[] gender = { "Select Gender ","Male", "Female" };
    Spinner spin_gender;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnStepTwoListener mListener;

    public Signup_secondpage() {
        // Required empty public constructor
    }

    public static Signup_secondpage newInstance(String param1, String param2) {
        Signup_secondpage fragment = new Signup_secondpage();
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


        view = inflater.inflate(R.layout.fragment_signup_secondpage, container, false);

        intviews();
        setviews();

        return view;
    }

    private void intviews() {
        mobile_label =view.findViewById(R.id.mobile_label);
        address_label= view.findViewById(R.id.address_label);
        village_label =view.findViewById(R.id.village_label);
        mandal_label = view.findViewById(R.id.mandal_label);
        pin_label =view.findViewById(R.id.pin_label);
        dist_label =view.findViewById(R.id.dist_label);
        state_label = view.findViewById(R.id.state_label);
        phone_numer = view.findViewById(R.id.phone_edittxt);
        Alt_phone_edittxt = view.findViewById(R.id.Alt_phone_edittxt);
        spin_gender =view.findViewById(R.id.spin_gender);
        address_edittxt = view.findViewById(R.id.address_edittxt);
        address_edittxt2 = view.findViewById(R.id.addres_edittxt);
        village_edittxt = view.findViewById(R.id.village_edittxt);
        mandal_edittxt= view.findViewById(R.id.mandal_edittxt);
        pin_edittxt = view.findViewById(R.id.pin_edittxt);
        dist_edittxt = view.findViewById(R.id.dist_edittxt);
        state_edittxt = view.findViewById(R.id.state_edittxt);
    }

    private void setviews() {
        spin_gender.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin_gender.setAdapter(aa);


        phone_numer.addTextChangedListener(new ValidationTextWatcher(phone_numer));
        address_edittxt.addTextChangedListener(new ValidationTextWatcher(address_edittxt));
        village_edittxt.addTextChangedListener(new ValidationTextWatcher(village_edittxt));
        mandal_edittxt.addTextChangedListener(new ValidationTextWatcher(mandal_edittxt));
        pin_edittxt.addTextChangedListener(new ValidationTextWatcher(pin_edittxt));
        dist_edittxt.addTextChangedListener(new ValidationTextWatcher(dist_edittxt));
        state_edittxt.addTextChangedListener(new ValidationTextWatcher(state_edittxt));
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
        User user = new  User("","","");
        user.setphone(phone_numer.getText().toString());
        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;

            case R.id.nextBT:
                if(validate()) {
                    if (mListener != null)
                        mListener.onNextPressed(this,user);
                }
                break;
        }
    }

    private boolean validate() {
        if(TextUtils.isEmpty(phone_numer.getText().toString().trim())){
            mobile_label.setError(getResources().getString(R.string.err_please_phone));

            return false;
        }
        else if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            return false;
        }
        else if(TextUtils.isEmpty(village_edittxt.getText().toString().trim())){
            village_label.setError(getResources().getString(R.string.err_please_village));
            return false;
        }
        else if(TextUtils.isEmpty(mandal_edittxt.getText().toString().trim())){
            mandal_label.setError(getResources().getString(R.string.err_please_mandal));
            return false;
        }
        else if(TextUtils.isEmpty(pin_edittxt.getText().toString().trim())){
            pin_label.setError(getResources().getString(R.string.err_please_pin));
            return false;
        }
        else if(TextUtils.isEmpty(dist_edittxt.getText().toString().trim())){
            dist_label.setError(getResources().getString(R.string.err_please_district));

            return false;
        }
        else if(TextUtils.isEmpty(state_edittxt.getText().toString().trim())){
            state_label.setError(getResources().getString(R.string.err_please_state));
            return false;
        }

        return true;
    }

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void displayReceivedData(String username) {
String user_name = username;
Log.e(LOG_TAG,"=====****"+user_name);
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
                case R.id.phone_edittxt:
                    validate_mobile();
                    break;
                case R.id.address_edittxt:
                    validate_address();
                    break;
                case R.id.village_edittxt:
                    validate_village();
                    break;
                case R.id.mandal_edittxt:
                    validate_mandal();
                    break;
                case R.id.pin_edittxt:
                    validate_pin();
                    break;
                case R.id.dist_edittxt:
                    validate_dist();
                    break;
                case R.id.state_edittxt:
                    validate_state();
                    break;



            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validate_state() {
        if(TextUtils.isEmpty(state_edittxt.getText().toString().trim())){
            state_label.setError(getResources().getString(R.string.err_please_state));
            requestFocus(state_edittxt);

        }
        else {
            state_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_dist() {
        if(TextUtils.isEmpty(dist_edittxt.getText().toString().trim())){
            dist_label.setError(getResources().getString(R.string.err_please_district));
            requestFocus(dist_edittxt);

        }
        else {
            dist_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_pin() {
        if(TextUtils.isEmpty(pin_edittxt.getText().toString().trim())){
            pin_label.setError(getResources().getString(R.string.err_please_pin));
            requestFocus(pin_edittxt);

        }
        else {
            pin_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_mandal() {
        if(TextUtils.isEmpty(mandal_edittxt.getText().toString().trim())){
            mandal_label.setError(getResources().getString(R.string.err_please_mandal));
            requestFocus(mandal_edittxt);

        }
        else {
            mandal_label.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validate_village() {
        if(TextUtils.isEmpty(village_edittxt.getText().toString().trim())){
            village_label.setError(getResources().getString(R.string.err_please_village));
            requestFocus(village_edittxt);
        }
        else {
            village_label.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validate_address() {
        if(TextUtils.isEmpty(address_edittxt.getText().toString().trim())){
            address_label.setError(getResources().getString(R.string.err_please_address));
            requestFocus(address_edittxt);

        }
        else {
            address_label.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validate_mobile() {
        if(TextUtils.isEmpty(phone_numer.getText().toString().trim())){
            mobile_label.setError(getResources().getString(R.string.err_please_phone));
            requestFocus(phone_numer);

        }
        else {
            mobile_label.setErrorEnabled(false);
        }
        return true;
    }
}