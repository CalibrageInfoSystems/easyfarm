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
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.AnimationUtil;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_Farmer;
import com.cis.easyfarm.ui.RecyclerAdapter;

import java.util.LinkedHashMap;
import java.util.List;

public class BuyersAdapter extends RecyclerView.Adapter<BuyersAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    public static String codee;
    Context context;
    private static final String LOG_TAG = RecyclerAdapter.class.getName();
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap<String, Pair> stateDataMap = null;
    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap= null,  villagesDataMap= null;
    List<Farmer> data;
    public BuyersAdapter(Context ctx, List<Farmer> data) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public BuyersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.buyersadapter, parent, false);

        return new BuyersAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final BuyersAdapter.ViewHolder holder, final int position) {

        holder.country.setText("INDIA");
        holder.code.setText(data.get(position).getCode());
        holder.farmerName.setText(data.get(position).getUserName());
        holder.email.setText(data.get(position).getEmail());
        holder.mobileNumber.setText(data.get(position).getPrimaryPhoneNumber());
        holder.address.setText(data.get(position).getAddress1());
        holder.createdDate.setText(data.get(position).getCreateddate());
        dataAccessHandler = new DataAccessHandler(context);
        CommonConstants.stateId =data.get(position).getStateid()+"";
        CommonConstants.districtId =data.get(position).getDistictid()+"";
        CommonConstants.mandalId =data.get(position).getMandalid()+"";
        CommonConstants.villageId =data.get(position).getVillageid()+"";


        CommonConstants.stateName =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatesQuery(CommonConstants.stateId));
        holder.state.setText(CommonConstants.stateName );

        CommonConstants.districtName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getdistrictsQuery(CommonConstants.districtId));
        Log.v(LOG_TAG, "@@@ districtName " +  CommonConstants.districtName);

        holder.district.setText( CommonConstants.districtName);
        CommonConstants.mandalName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getmandalQuery(CommonConstants.mandalId));

        holder.mandal.setText(CommonConstants.mandalName);

        CommonConstants.villageName =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getvillageQuery(CommonConstants.villageId));
        Log.v(LOG_TAG, "@@@ villageName " +  CommonConstants.villageName);

//
        holder.village.setText(CommonConstants.villageName);
        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white2));

        }
        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditBuyer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("buyercode",data.get(position).getCode());
                context.startActivity(intent);

            }

        });
        AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public void updateAdapter(List<Farmer> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code,farmerName,email,mobileNumber, address, state, district, mandal, village, country, createdDate;
        LinearLayout l_code,l_farmerName,l_email,l_mobileNumber, l_addresss, l_state, l_district, l_mandal, l_village, l_country, l_createdDate;
        CardView card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.Code);
            farmerName = itemView.findViewById(R.id.farmerName);
            email = itemView.findViewById(R.id.email);
            mobileNumber = itemView.findViewById(R.id.mobileNumber);
            address = itemView.findViewById(R.id.address);
            state = itemView.findViewById(R.id.state);
            district = itemView.findViewById(R.id.district);
            mandal = itemView.findViewById(R.id.mandal);
            village = itemView.findViewById(R.id.village);
            card_view = itemView.findViewById(R.id.card_view);
            country = itemView.findViewById(R.id.country);
            createdDate = itemView.findViewById(R.id.createdDate);

            l_code = itemView.findViewById(R.id.lyt_code);
            l_farmerName = itemView.findViewById(R.id.lyt_farmerName);
            l_email = itemView.findViewById(R.id.lyt_email);
            l_mobileNumber = itemView.findViewById(R.id.lyt_mobileNumber);
            l_addresss = itemView.findViewById(R.id.lyt_address);
            l_state = itemView.findViewById(R.id.lyt_state);
            l_district = itemView.findViewById(R.id.lyt_district);
            l_mandal = itemView.findViewById(R.id.lyt_mandal);
            l_village = itemView.findViewById(R.id.lyt_village);
            l_mandal = itemView.findViewById(R.id.lyt_mandal);
            l_village = itemView.findViewById(R.id.lyt_village);
            l_country = itemView.findViewById(R.id.lyt_country);
            l_createdDate = itemView.findViewById(R.id.lyt_createdDate);

        }
    }
}
