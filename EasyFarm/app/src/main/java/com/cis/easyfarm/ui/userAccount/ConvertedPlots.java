package com.cis.easyfarm.ui.userAccount;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis.easyfarm.Fragments.ConvertedFarmerFragment;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.R;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Adapters.ConvertedFarmerRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConvertedPlots#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvertedPlots extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = ConvertedPlots.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ConvertedPlots.OnFragmentInteractionListener mListener;
    TextView firstName;
    private List<Farmer> users;

    private List<Farmer> mFarmersList = new ArrayList<>();
    private List<Farmer> mFinalFarmersList = new ArrayList<>();
    private ArrayList<Farmer> mList_sorted;
    // List<Farmer> farmerDetails;
    DataAccessHandler dataAccessHandler;
    Context context;
    private ImageView mIVClear;
    private RecyclerView recyclerView;
    convertedplotadapter convertedplotadapter;
    Toolbar toolbar;
    EditText et_search;
    View view;
    private TextView tvNorecords;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            doSearch(s.toString());
            if (s.toString().length() > 0)
                mIVClear.setVisibility(View.VISIBLE);
            else
                mIVClear.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void doSearch(String searchQuery) {
        int etSearchLength = searchQuery.length();
        if (!TextUtils.isEmpty(searchQuery)) {
            mList_sorted = new ArrayList<>();
            mFinalFarmersList = new ArrayList<>();
            String farmerName = "";
            for (int i = 0; i < mFarmersList.size(); i++) {
                String lastName = "", fatherName = "";
                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname())) {
                    lastName = mFarmersList.get(i).getLastname();
                    Log.d("Farmerlastname===", lastName + "");
                }
                if (!TextUtils.isEmpty(mFarmersList.get(i).getFatherName_GuardianName())) {
                    fatherName = mFarmersList.get(i).getFatherName_GuardianName();
                }

                farmerName = mFarmersList.get(i).getFirstname();

                if (!TextUtils.isEmpty(mFarmersList.get(i).getLastname()) && searchQuery.contains(" ")) {
                    farmerName = farmerName + mFarmersList.get(i).getLastname();
                }
                if (farmerName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || mFarmersList.get(i).getPrimaryPhoneNumber().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || lastName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || mFarmersList.get(i).getCode().toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))
                        || (!TextUtils.isEmpty(fatherName) && fatherName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault())))) {
                    mList_sorted.add(mFarmersList.get(i));
                }
            }

            mFinalFarmersList.addAll(mList_sorted);

            if (convertedplotadapter == null) {
                convertedplotadapter = new  convertedplotadapter(getActivity(), mList_sorted);
            } else {
                convertedplotadapter.updateAdapter(mList_sorted);
            }
            if (mList_sorted != null && mList_sorted.size() == 0) {
                tvNorecords.setVisibility(View.VISIBLE);
            } else {
                tvNorecords.setVisibility(View.GONE);
//
            }
        }



        if (TextUtils.isEmpty(searchQuery)) {
            tvNorecords.setVisibility(View.GONE);
            convertedplotadapter = new  convertedplotadapter(getActivity(),mFarmersList);
            recyclerView.setAdapter(convertedplotadapter);
        }

    }

    public ConvertedPlots() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConvertedPlots.
     */
    // TODO: Rename and change types and number of parameters
    public static ConvertedPlots newInstance(String param1, String param2) {
        ConvertedPlots fragment = new ConvertedPlots();
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
        view =  inflater.inflate(R.layout.fragment_converted_plots, container, false);

        intviews();
        setviews();

        return view;
    }

    private void setviews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(getActivity());
        //  mFarmersList = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerDetails(), 1);
        mFarmersList = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getConvertedFarmers(), 1);

        android.util.Log.d("FarmerDetails====", mFarmersList.size() + "");

        for (int i=0;i<mFarmersList.size();i++) {

            Log.d("FarmerUserNames===", mFarmersList.get(i).getFirstname() + "");

        }

        convertedplotadapter = new convertedplotadapter(getActivity(),mFarmersList);
        recyclerView.setAdapter(convertedplotadapter);

        et_search.addTextChangedListener(mTextWatcher);
        mIVClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
    }

    private void intviews() {
        tvNorecords = (TextView) view.findViewById(R.id.no_records);
        recyclerView = view.findViewById(R.id.recyclerView);
        mIVClear = (ImageView) view.findViewById(R.id.iv_clear);
        et_search = view.findViewById( R.id.et_search);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}