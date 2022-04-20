package com.cis.easyfarm.ui.userAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.cis.easyfarm.ui.RecyclerAdapter;

import java.util.LinkedHashMap;
import java.util.List;

public class ConvertedVendorsAdapter extends RecyclerView.Adapter<ConvertedVendorsAdapter.ViewHolder> {


    private LayoutInflater layoutInflater;
    public static String codee;
    Context context;
    private static final String LOG_TAG = RecyclerAdapter.class.getName();
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap<String, Pair> stateDataMap = null;
    private LinkedHashMap<String, Pair> districtDataMap = null, mandalDataMap= null,  villagesDataMap= null;
    List<Farmer> data;
    public ConvertedVendorsAdapter(Context ctx,List<Farmer> data) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.data = data;
    }



    @NonNull
    @Override
    public ConvertedVendorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.convertedvendorsrecycleview, parent, false);

        return new ConvertedVendorsAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ConvertedVendorsAdapter.ViewHolder holder, final int position) {

        holder.code.setText(data.get(position).getCode());
        holder.farmerName.setText(data.get(position).getUserName());
        holder.email.setText(data.get(position).getEmail());
        holder.mobileNumber.setText(data.get(position).getPrimaryPhoneNumber());
        holder.address.setText(data.get(position).getAddress1());

        if (data.get(position).getIsactive() == 0){

            holder.active.setText("false");
        }else
        {
            holder.active.setText("true");
        }


        dataAccessHandler = new DataAccessHandler(context);

        CommonConstants.villageId =data.get(position).getVillageid()+"";

        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(context.getColor(R.color.white2));

        }
        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Edit_converted_Vendors.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code",data.get(position).getCode());
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

        TextView code,farmerName,email,mobileNumber, address,active;
        LinearLayout l_code,l_farmerName,l_email,l_mobileNumber, l_addresss, l_active;
        CardView card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.Code);
            farmerName = itemView.findViewById(R.id.farmerName);
            email = itemView.findViewById(R.id.email);
            mobileNumber = itemView.findViewById(R.id.mobileNumber);
            address = itemView.findViewById(R.id.address);
            active = itemView.findViewById(R.id.active);
            card_view = itemView.findViewById(R.id.card_view);

            l_code = itemView.findViewById(R.id.lyt_code);
            l_farmerName = itemView.findViewById(R.id.lyt_farmerName);
            l_email = itemView.findViewById(R.id.lyt_email);
            l_mobileNumber = itemView.findViewById(R.id.lyt_mobileNumber);
            l_addresss = itemView.findViewById(R.id.lyt_address);
            l_active = itemView.findViewById(R.id.lyt_active);

        }
    }

}
