package com.cis.easyfarm.ui.userAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.InsuranceDetailsObject;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.PlotCropCycleObject;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.AnimationUtil;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.RecyclerItemClickListener;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.RecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CplotAdapter extends RecyclerView.Adapter< CplotAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    public static String codee;
    private String cropcyclecode;
    private int insuranceStatusID;

    Context context;
    boolean resultOfComparison, resultOfComparison1, resultOfComparison2;
    private static final String LOG_TAG = RecyclerAdapter.class.getName();
    private DataAccessHandler dataAccessHandler;
    private List<PlotCropCycleObject> plotCropCycleObject = new ArrayList<>();
    private List<InsuranceDetailsObject> insuranceDetails = new ArrayList<>();
    private LinkedHashMap<String, Pair> stateDataMap = null;
    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap= null,  villagesDataMap= null;
    List<Plot> data;
    String seriesOfinsuranceStatus;
    private RecyclerItemClickListener recyclerItemClickListener;

    public  CplotAdapter(Context ctx,List<Plot> data) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.data = data;
    }

    //    public FarmerRecyclerAdapter(Context context, List<Farmer> mList) {
//        this.context = context;
//        this.data = mList;
//    }
    public void addItems(List<Plot> list) {
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CplotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cplotadapter, parent, false);

        return new CplotAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final CplotAdapter.ViewHolder holder, final int position) {

        holder.code.setText(data.get(position).getCode());
        holder.tarea.setText(data.get(position).getTotalPlotArea() + "");
        holder.gpsarea.setText(data.get(position).getGPSPlotArea() + "");
        holder.adoptedarea.setText(data.get(position).getAdaptedArea() + "");

        dataAccessHandler = new DataAccessHandler(context);
        CommonConstants.stateId =data.get(position).getStateId()+"";
        CommonConstants.districtId =data.get(position).getDistrictId()+"";
        CommonConstants.mandalId =data.get(position).getMandalId()+"";
        CommonConstants.villageId =data.get(position).getVillageId()+"";
        CommonConstants.plotStatusId =data.get(position).getPlotStatusId()+"";

        CommonConstants.insuranceStatus = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCPlotsbasedonStatus(data.get(position).getCode()));

        Log.d("InsuranceStatus",  CommonConstants.insuranceStatus + "");

        if (CommonConstants.insuranceStatus != null ){

            resultOfComparison = CommonConstants.insuranceStatus.equals("182");
            resultOfComparison1 = CommonConstants.insuranceStatus.equals("183");
            resultOfComparison2 = CommonConstants.insuranceStatus.equals("184");

        }


        Log.d("resultOfComparison", resultOfComparison + "");
        Log.d("resultOfComparison1", resultOfComparison1 + "");
        Log.d("resultOfComparison2", resultOfComparison2 + "");


        if(data.get(position).getPlotStatusId() == 180 || data.get(position).getPlotStatusId() == 181){

           // holder.insurance.setVisibility(View.VISIBLE);

            if (resultOfComparison) {
                holder.insurance.setVisibility(View.GONE);
            }else{

                holder.insurance.setVisibility(View.VISIBLE);
            }

        }
        else{
            holder.insurance.setVisibility(View.GONE);
        }

        //CommonConstants.SinsuranceStatus =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getinsuranceStatusQuery(CommonConstants.insuranceStatus));


//        if (resultOfComparison == true || resultOfComparison1 == true || resultOfComparison2 == true){
//
//            holder.insuranceStatus.setVisibility(View.VISIBLE);
//            holder.insuranceStatus.setText( CommonConstants.SinsuranceStatus);
//
//        }else {
//
//           holder.lyt_insuranceStatus.setVisibility(View.GONE);
//
//        }

        CommonConstants.plotStatusdesc =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getplotStatusQuery(CommonConstants.plotStatusId));

        holder.plotStatus.setText(CommonConstants.plotStatusdesc );



        if (CommonConstants.insuranceStatus != null ) {
            CommonConstants.insuranceStatustext  = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getinsuranceStatusQuery(CommonConstants.insuranceStatus));
            holder.insurancestaustext.setText(CommonConstants.insuranceStatustext);
        }else{

            holder.lyt_insuranceStatus.setVisibility(View.GONE);
        }



        CommonConstants.stateName =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatesQuery(CommonConstants.stateId));
        holder.state.setText(CommonConstants.stateName );

        CommonConstants.districtName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsQuery(CommonConstants.districtId));
        android.util.Log.v(LOG_TAG, "@@@ districtName " +  CommonConstants.districtName);

        holder.district.setText( CommonConstants.districtName);
        CommonConstants.mandalName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalQuery(CommonConstants.mandalId));

        holder.mandal.setText(CommonConstants.mandalName);

        CommonConstants.villageName =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getvillageQuery(CommonConstants.villageId));
        Log.v(LOG_TAG, "@@@ villageName " +  CommonConstants.villageName);

        holder.address.setText(data.get(position).getAddress1() + "," + CommonConstants.villageName + "," +CommonConstants.mandalName + "," + CommonConstants.districtName + "," + CommonConstants.stateName);


//
        holder.village.setText(CommonConstants.villageName);
        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white2));

        }
        holder.insurance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InsuranceDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Plotcode",data.get(position).getCode());
                context.startActivity(intent);

            }

        });
        AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public void updateAdapter(List<Plot> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code,farmerName,email,mobileNumber, address, state, district, mandal, village, insurance, tarea, gpsarea, adoptedarea, plotStatus, insurancestaustext;
        LinearLayout l_code,l_farmerName,l_email,l_mobileNumber, l_addresss, l_state, l_district, l_mandal, l_village, l_tarea, l_gpsarea, l_adoptedarea, l_plotStatus, lyt_insuranceStatus;
        CardView card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.Code);
            tarea = itemView.findViewById(R.id.tarea);
            gpsarea = itemView.findViewById(R.id.gpsarea);
            adoptedarea = itemView.findViewById(R.id.adoptedarea);
            plotStatus = itemView.findViewById(R.id.plotstatus);
            farmerName = itemView.findViewById(R.id.farmerName);
            email = itemView.findViewById(R.id.email);
            mobileNumber = itemView.findViewById(R.id.mobileNumber);
            address = itemView.findViewById(R.id.address);
            state = itemView.findViewById(R.id.state);
            district = itemView.findViewById(R.id.district);
            mandal = itemView.findViewById(R.id.mandal);
            village = itemView.findViewById(R.id.village);
            card_view = itemView.findViewById(R.id.card_view);
            insurance = itemView.findViewById(R.id.insurance);
            insurancestaustext = itemView.findViewById(R.id.insuranceStatus);

            l_code = itemView.findViewById(R.id.lyt_code);
            l_plotStatus = itemView.findViewById(R.id.lyt_plotstatus);
            l_tarea = itemView.findViewById(R.id.lyt_tarea);
            l_gpsarea = itemView.findViewById(R.id.lyt_gpsarea);
            l_adoptedarea = itemView.findViewById(R.id.lyt_adoptedarea);
            l_farmerName = itemView.findViewById(R.id.lyt_farmerName);
            l_email = itemView.findViewById(R.id.lyt_email);
            l_mobileNumber = itemView.findViewById(R.id.lyt_mobileNumber);
            l_addresss = itemView.findViewById(R.id.lyt_address);
            l_state = itemView.findViewById(R.id.lyt_state);
            l_district = itemView.findViewById(R.id.lyt_district);
            l_mandal = itemView.findViewById(R.id.lyt_mandal);
            l_village = itemView.findViewById(R.id.lyt_village);
            lyt_insuranceStatus=itemView.findViewById(R.id.lyt_insuranceStatus);

        }
    }
}
