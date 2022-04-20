package com.cis.easyfarm.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.Add_converted_Farmer;
import com.cis.easyfarm.ui.Soiltesr_reports;

import java.text.DecimalFormat;
import java.util.List;

import static com.androidquery.util.AQUtility.getContext;


public class PlotDetailsAdapter extends RecyclerView.Adapter<PlotDetailsAdapter.ViewHolder> {

    private List<Plot> recomm_Set;
    private PlotClickListener plot_ClickListener;
    //List<GetPlotsByFarmerCode.ListResult> recomm_Set;
    public Context mContext;
    DataAccessHandler dataAccessHandler;
    DecimalFormat df = new DecimalFormat("####0.00");
    int row_index = -1;

    public PlotDetailsAdapter(List<Plot> recomm_Set, Context context) {
        this.recomm_Set = recomm_Set;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.plot_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        plotcode,status,totalarea,adopted_area,gps_area,plot_ownership,country,state,district,mandal,village,address,
//                address2,passbook,servey_number,owner_contact_number,ownername;
        dataAccessHandler = new DataAccessHandler(mContext);
        holder.plotcode.setText(recomm_Set.get(position).getCode());
        String plotstatus = dataAccessHandler.getCountValue(Queries.getInstance().getstatusid(recomm_Set.get(position).getPlotStatusId()+""));
        holder.status.setText(plotstatus);
        holder.totalarea.setText(df.format(recomm_Set.get(position).getTotalPlotArea() ) + " Acre");
        if (recomm_Set.get(position).getAdaptedArea() != null) {
            holder.adopted_area.setText(df.format(recomm_Set.get(position).getAdaptedArea()) + " Acre");
        }
        if (recomm_Set.get(position).getGPSPlotArea() != null){
            holder.gps_area.setText(df.format(recomm_Set.get(position).getGPSPlotArea() ) + "  Acre");
    }
        String ownership = dataAccessHandler.getCountValue(Queries.getInstance().getstatusid(recomm_Set.get(position).getPlotOwnerShipTypeId()+""));
        holder.plot_ownership.setText( ownership);
        Log.e("status===========",recomm_Set.get(position).getPlotStatusId()+"");
        if (recomm_Set.get(position).getPlotStatusId()==105){
            holder.pros_data.setVisibility(View.VISIBLE);
            holder.pros_dataa.setVisibility(View.VISIBLE);
        }
        else{

            holder.pros_data.setVisibility(View.GONE);
            holder.pros_dataa.setVisibility(View.GONE);
        }
        String villagename = dataAccessHandler.getCountValue(Queries.getInstance().getvilage(recomm_Set.get(position).getVillageId()+""));
        String mandalname = dataAccessHandler.getCountValue(Queries.getInstance().getMandal( recomm_Set.get(position).getMandalId()+""));
        String districtname = dataAccessHandler.getCountValue(Queries.getInstance().getDistric(recomm_Set.get(position).getDistrictId()+""));
        String statename = dataAccessHandler.getCountValue(Queries.getInstance().getstate(recomm_Set.get(position).getStateId()+""));

        holder.village.setText(villagename);
        holder.district.setText(districtname);
        holder.state.setText(statename);
        holder.mandal.setText( mandalname);
        holder.address.setText( recomm_Set.get(position).getAddress1());
        holder.address2.setText( recomm_Set.get(position).getAddress2());

        holder.passbook.setText(recomm_Set.get(position).getPassbookNumber());
        holder.servey_number.setText( recomm_Set.get(position).getSurveyNumber());
        Log.e("ownername==",recomm_Set.get(position).getOwnerName());
        String ownername =recomm_Set.get(position).getOwnerName();

        if(ownername != null && !ownername.isEmpty() && !ownername.equalsIgnoreCase("null"))
        {
            holder.ownername.setText(ownername);
            holder.owner_contact_number.setText( recomm_Set.get(position).getOwnerContactNumber());
            Log.e("ownername==1","Rooja========");
        }
        else{
            holder.lyt_ownername.setVisibility(View.GONE);
           holder.owner_bottom_layout.setVisibility(View.GONE);
            holder.lyt_ownercontactnumer.setVisibility(View.GONE);
            holder.owner_bottom_layout.setVisibility(View.GONE);
            Log.e("ownername==1","111111111111111");
        }
//        if(TextUtils.isEmpty(recomm_Set.get(position).getOwnerName()) ||recomm_Set.get(position).getOwnerName().trim().equals("null")||  recomm_Set.get(position).getOwnerName().isEmpty() ||recomm_Set.get(position).getOwnerName()== null) {
//            Log.e("ownername==1",recomm_Set.get(position).getOwnerName());
//            holder.lyt_ownername.setVisibility(View.GONE);
//            holder.owner_bottom_layout.setVisibility(View.GONE);
//        }
//        else {
//            Log.e("ownername==2",recomm_Set.get(position).getOwnerName());
//            holder.ownername.setText(recomm_Set.get(position).getOwnerName());
//            holder.lyt_ownername.setVisibility(View.VISIBLE);
//        }

        String soiltype =  dataAccessHandler.getCountValue(Queries.getInstance().getsoiltype(recomm_Set.get(position).getCode()));
     //   Log.e("============",soiltype);

        String Irrigationtype =  dataAccessHandler.getCountValue(Queries.getInstance().getirrigationtype(recomm_Set.get(position).getCode()));
     //   Log.e("============",Irrigationtype);
        if(soiltype!=null) {
            String soil = dataAccessHandler.getCountValue(Queries.getInstance().getsoil(soiltype));
            holder.soiltype.setText(soil);
            Log.e("============",soil);
        }
        else{
            holder.lyt_soil.setVisibility(View.GONE);
            holder.soil_layout.setVisibility(View.GONE);
        }
        if(Irrigationtype!=null) {
            String irrigation = dataAccessHandler.getCountValue(Queries.getInstance().getsoil(Irrigationtype));
            Log.e("============", irrigation);
            holder.irrigation_type.setText(irrigation);

        }
        else{
holder.lyt_irrigation.setVisibility(View.GONE);
holder.ill_linear.setVisibility(View.GONE);
        }
String powerAvailability =dataAccessHandler.getCountValue(Queries.getInstance().getpoweravailabity(recomm_Set.get(position).getCode()));

        String meter =dataAccessHandler.getCountValue(Queries.getInstance().getmeter(recomm_Set.get(position).getCode()));


        if(row_index== position)
        {
            holder.linear.setVisibility(View.VISIBLE);

            // holder.createdDateTextView.setVisibility(View.VISIBLE);

        }else{
            holder.linear.setVisibility(View.GONE);
            //  holder.createdDateTextView.setVisibility(View.GONE);
        }


        final int h=  holder.card_view.getHeight();
        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                int oldindex=  row_index;
                row_index = position;
                notifyItemChanged(oldindex);
                notifyItemChanged(position);

            }

        });
        holder.soil_reports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Soiltesr_reports.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code",recomm_Set.get(position).getCode());
                intent.putExtra("Farmercode",recomm_Set.get(position).getFarmerCode());
                mContext.startActivity(intent);

            }

        });
//
//        if(powerAvailability.contains("false") ||powerAvailability.equals(false)  )
//        {
//            holder.powerAvalabilty.setText("No");
//            holder.lyt_meter.setVisibility(View.GONE);
//
//        }
//        else{
        if(meter!=null ){
            holder.meter_number.setText(meter);
        }
        else{
            holder.lyt_meter.setVisibility(View.GONE);
        }
            holder.powerAvalabilty.setText(powerAvailability);
   //     }
        //holder.lyt_meter.setText(meter);




       // AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {
        return recomm_Set.size();
    }

    public void setPlotClickListener(PlotClickListener plotClickListener) {
        this.plot_ClickListener = plotClickListener;
    }
    public interface PlotClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView plotcode,status,totalarea,adopted_area,gps_area,plot_ownership,country,state,district,mandal,village,address,
                address2,passbook,servey_number,owner_contact_number,ownername,soiltype,irrigation_type,powerAvalabilty,meter_number;
        LinearLayout lyt_plot_code,lyt_status,lyt_totalarea,lyt_Adapted,lyt_gps,lyt_plotownership,lyt_ownercontactnumer,lyt_ownername,owner_bottom_layout,lyt_meter,linear,lyt_soil,lyt_irrigation
                ,ill_linear,soil_layout,pros_data,pros_dataa,soil_reports;


        CardView card_view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.plotcode = (TextView) itemView.findViewById(R.id.plotcode);
            this.status = (TextView) itemView.findViewById(R.id.status);
            this.totalarea = (TextView) itemView.findViewById(R.id.totalarea);
            this.adopted_area = (TextView) itemView.findViewById(R.id.adopted_area);
            this.gps_area = (TextView) itemView.findViewById(R.id.gps_area);
            this.plot_ownership = (TextView) itemView.findViewById(R.id.plot_ownership);
            this.country = (TextView) itemView.findViewById(R.id.country);
            this.state = (TextView) itemView.findViewById(R.id.state);
            this.district = (TextView) itemView.findViewById(R.id.district);
            this.mandal = (TextView) itemView.findViewById(R.id.mandal);
            this.village = (TextView) itemView.findViewById(R.id.village);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.address2 = (TextView) itemView.findViewById(R.id.address2);
            this.passbook = (TextView) itemView.findViewById(R.id.passbook);
            this.servey_number = (TextView) itemView.findViewById(R.id.servey_number);
            this.owner_contact_number = (TextView) itemView.findViewById(R.id.owner_contact_number);
            this.ownername = (TextView) itemView.findViewById(R.id.ownername);
           this.soiltype = (TextView) itemView.findViewById(R.id.soiltype);
            this.irrigation_type = (TextView) itemView.findViewById(R.id.irrigation_type);
            this.powerAvalabilty = (TextView) itemView.findViewById(R.id.powerAvalabilty);
            this.meter_number =(TextView) itemView.findViewById(R.id.meternumber);
            this.card_view = (CardView) itemView.findViewById(R.id.card_view);

this.soil_layout =(LinearLayout) itemView.findViewById(R.id.soil_layout);
            this.lyt_plot_code =(LinearLayout)itemView.findViewById(R.id.lyt_plot_code) ;
            this.lyt_status =(LinearLayout)itemView.findViewById(R.id.lyt_status) ;
            this.lyt_totalarea =(LinearLayout)itemView.findViewById(R.id.lyt_totalarea) ;
            this.lyt_Adapted =(LinearLayout)itemView.findViewById(R.id.lyt_Adapted) ;
            this.lyt_gps =(LinearLayout)itemView.findViewById(R.id.lyt_gps) ;
            this.lyt_plotownership =(LinearLayout)itemView.findViewById(R.id.lyt_plotownership) ;
            this.lyt_plot_code =(LinearLayout)itemView.findViewById(R.id.lyt_plot_code) ;
            this.lyt_plot_code =(LinearLayout)itemView.findViewById(R.id.lyt_plot_code) ;
            this.lyt_ownercontactnumer =(LinearLayout)itemView.findViewById(R.id.lyt_owner_contact_number) ;
            this.lyt_ownername =(LinearLayout)itemView.findViewById(R.id.lyt_owner_name) ;
            this.owner_bottom_layout =(LinearLayout)itemView.findViewById(R.id.owner_bottom_layout) ;
            this.lyt_meter =(LinearLayout)itemView.findViewById(R.id.lyt_meter) ;

            this.linear=(LinearLayout)itemView.findViewById(R.id.linear) ;
            this.lyt_soil=(LinearLayout)itemView.findViewById(R.id.lyt_soil) ;
            this.lyt_irrigation=(LinearLayout)itemView.findViewById(R.id.lyt_irrigation) ;
            this.ill_linear=(LinearLayout)itemView.findViewById(R.id.ill_linear) ;
            this.pros_data=(LinearLayout)itemView.findViewById(R.id.pros_data) ;

            this.pros_dataa=(LinearLayout)itemView.findViewById(R.id.pros_dataa) ;
            this.soil_reports =(LinearLayout)itemView.findViewById(R.id.soildetails);
        }


    }
}

