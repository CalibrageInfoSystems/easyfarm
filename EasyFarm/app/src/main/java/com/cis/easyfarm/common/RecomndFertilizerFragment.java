package com.cis.easyfarm.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.cis.easyfarm.Interface.UpdateUiListener;
import com.cis.easyfarm.R;

public class RecomndFertilizerFragment extends BaseFragment implements View.OnClickListener, UpdateUiListener {
    //    private RecomNDScreen.OnFragmentInteractionListener mListener;
    private Context mContext;
    private View rootView;
    private Toolbar toolbarrecomnd;
    private ActionBar actionBar;
    private Spinner rcmndfertilizerProductNameSpin,rcmnduomSpin;
    private EditText rcmndosageEdt;
    private EditText rcmndedtcmment;
    private RecyclerView rcmndsaveList;
    private Button rcmndsave,historyBtn;
//    private DataAccessHandler dataAccessHandler;
//    private LinkedHashMap<String, String> fertilizerDataMap, fertilizerTypeDataMap, uomDataMap, frequencyOfApplicationDataMap;
//    private ArrayList mFertilizerModelArray;
//    private RecmndGenericTypeAdapter fertilizerDataAdapter;
//    private Fertilizer  mFertilizerModel;
//    private Fertilizer mFertilizerModel1;
//    private ArrayList<Nutrient> mmNutrientModelModelArray;
    private String screenrecmnd;
    private String value;
    private UpdateUiListener updateUiListener;




    public RecomndFertilizerFragment() {
        // Required empty public constructor
    }

/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_recomnd_fertilizert, container, false);
        baseLayout.addView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbarrecomnd = (Toolbar) rootView.findViewById(R.id.toolbarrecomnd);

        activity.setSupportActionBar(toolbarrecomnd);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Recommended Fertilizer");




        initViews();
        setViews();

        bindData();
        mContext=getActivity();
        setHasOptionsMenu(true);
        return rootView;
    }
*/

    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        rootView = inflater.inflate(R.layout.fragment_recomnd_fertilizert, null);
        baseLayout.addView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        toolbarrecomnd = (Toolbar) rootView.findViewById(R.id.toolbarrecomnd);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbarrecomnd);
//        actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        setTile("Recommended Fertilizer");

        mContext = getActivity();
        setHasOptionsMenu(true);
//        initViews();
//        setViews();
//        bindData();
        mContext=getActivity();
    }

//    private void initViews() {
//        dataAccessHandler = new DataAccessHandler(getActivity());
//
//        rcmndfertilizerProductNameSpin = (Spinner) rootView.findViewById(R.id.rcmndfertilizerProductNameSpin);
//        rcmnduomSpin = (Spinner) rootView.findViewById(R.id.rcmnduomSpin);
//        rcmndosageEdt=(EditText)rootView.findViewById(R.id.rcmndosageEdt);
//        rcmndedtcmment=(EditText)rootView.findViewById(R.id.rcmndedtcmment);
//        rcmndsaveList=(RecyclerView)rootView.findViewById(R.id.rcmndsaveList);
//        rcmndsave=(Button)rootView.findViewById(R.id.rcmndsave);
//        historyBtn = (Button) rootView.findViewById(R.id.historyBtn);
//
//    }
//    private void setViews() {
//        rcmndsave.setOnClickListener(this);
//        historyBtn.setOnClickListener(this);
//
//
//        fertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
//        rcmndfertilizerProductNameSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "fertilizer Product Name", fertilizerTypeDataMap));
//        uomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
//        rcmnduomSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select UOM", uomDataMap));
//
//
//    }
//    private void bindData() {
//
//
//        mFertilizerModelArray = (ArrayList<RecommndFertilizer>) DataManager.getInstance().getDataFromManager(DataManager.RECMND_FERTILIZER);
//        if (null == mFertilizerModelArray)
//            mFertilizerModelArray = new ArrayList<RecommndFertilizer>();
//
//        fertilizerDataAdapter = new RecmndGenericTypeAdapter(getActivity(), mFertilizerModelArray, fertilizerTypeDataMap, uomDataMap, GenericTypeAdapter.TYPE_RECOM_FERTILIZER);
//
//        rcmndsaveList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        rcmndsaveList.setAdapter(fertilizerDataAdapter);
//        fertilizerDataAdapter.setEditClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rcmndsave:
//                if (validateUI()) {
//                    RecommndFertilizer mFertilizerModel=new RecommndFertilizer();
//                    mFertilizerModel.setRecommendFertilizerProviderId((Integer.parseInt(getKey(fertilizerTypeDataMap, rcmndfertilizerProductNameSpin.getSelectedItem().toString()))));
//                    mFertilizerModel.setRecommendUOMId(Integer.parseInt(getKey(uomDataMap, rcmnduomSpin.getSelectedItem().toString())));
//                    mFertilizerModel.setRecommendDosage(TextUtils.isEmpty(rcmndosageEdt.getText().toString())==true? 0.0:Double.parseDouble(rcmndosageEdt.getText().toString()));
//
//                    mFertilizerModel.setComments(rcmndedtcmment.getText().toString());
//                    mFertilizerModelArray.add(mFertilizerModel);
//                    DataManager.getInstance().addData(DataManager.RECMND_FERTILIZER, mFertilizerModelArray);
//
//
//                    clearFields();
//                    fertilizerDataAdapter.notifyDataSetChanged();
//
//                }
//                CommonUtilsNavigation.hideKeyBoard(getActivity());
//                break;
//
//            case R.id.historyBtn:
//                CropMaintainanceHistoryFragment newFragment = new CropMaintainanceHistoryFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("screen", RECOM_FERTILIZER_DATA);
//                newFragment.setArguments(bundle);
//                newFragment.show(getActivity().getFragmentManager(), "history");
//                break;
//        }
//    }
//
//    @Override
//    public void updateUserInterface(int refreshPosition) {
//
//    }
//    private void clearFields() {
//        rcmndfertilizerProductNameSpin.setSelection(0);
//
//        rcmnduomSpin.setSelection(0);
//        rcmndosageEdt.setText("");
//        rcmndedtcmment.setText("");
//    }
//    @Override
//    public void onEditClicked(int position) {
//
//        mFertilizerModelArray.remove(position);
//
//
//
//        fertilizerDataAdapter.notifyDataSetChanged();
//    }
//    private boolean validateUI() {
//        return spinnerSelect(rcmndfertilizerProductNameSpin, "Fertilizer product name", mContext)
//                && edittextEampty(rcmndosageEdt, "Dosage given", mContext) && spinnerSelect(rcmnduomSpin, "UOM", mContext);
//
//    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateUserInterface(int refreshPosition) {

    }
}
