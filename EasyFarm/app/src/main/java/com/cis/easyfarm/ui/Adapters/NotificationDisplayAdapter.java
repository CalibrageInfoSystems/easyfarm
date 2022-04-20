package com.cis.easyfarm.ui.Adapters;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cis.easyfarm.Objects.Alerts;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Notifications;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.AnimationUtil;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.RecyclerItemClickListener;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;

import com.cis.easyfarm.ui.RecyclerAdapter;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.AddplotActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public class NotificationDisplayAdapter extends RecyclerView.Adapter<NotificationDisplayAdapter.ViewHolder> {

   // private List<Alerts> alertsList;
    private LayoutInflater layoutInflater;
    public static String codee;
    Context context;
    String datetimevaluereq;
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
    private static final String LOG_TAG = RecyclerAdapter.class.getName();
    private DataAccessHandler dataAccessHandler;
    String timeStamp;
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    List<Notifications> notifications;
     int row_index = -1;
//     int row_index_old = -1;

    public NotificationDisplayAdapter(Context ctx,List<Notifications> data) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.notifications = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.noti_display_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
         timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
         Log.e("timeStamp====",timeStamp);
        dataAccessHandler = new DataAccessHandler(context);
        try {
            Date oneWayTripDate = input.parse(notifications.get(position).getCreatedDate());
           int isRead  =  notifications.get(position).getIsRead();
            Log.d("notification===", isRead + "");
            if (notifications.get(position).getIsRead() == 1) {
                holder.createdDateTextView.setTextColor(Color.parseColor("#a6a6a6"));//gray
            } else {
                holder.createdDateTextView.setTextColor(Color.parseColor("#1CD6EC"));

            }
            datetimevaluereq = output.format(oneWayTripDate);

//
            Log.e("===============", "data=" + notifications.get(position).getNotificationTypeId()+ notifications.get(position).getCreatedByUserId());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.headerTextView.setText(notifications.get(position).getHeader()+"");
        holder.descriptionTextView.setText(notifications.get(position).getDesc());

        holder.createdDateTextView.setText(datetimevaluereq);
        if(row_index== position)
        {
            holder.descriptionTextView.setMaxLines(5);
            holder.headerTextView.setMaxLines(5);
           // holder.createdDateTextView.setVisibility(View.VISIBLE);

        }else{
            holder.descriptionTextView.setMaxLines(1);
            holder.headerTextView.setMaxLines(1);
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

                LinkedHashMap map= new LinkedHashMap();

                map.put("Id",notifications.get(position).getId());
                map.put("UserId", notifications.get(position).getUserId());
                map.put("Desc",notifications.get(position).getDesc());
                map.put("RaisedByUserId",notifications.get(position).getRaisedByUserId());
                map.put("IsRead",true);
                map.put("NotificationTypeId",notifications.get(position).getNotificationTypeId());
                map.put("IsActive",true);
                map.put("CreatedByUserId", notifications.get(position).getCreatedByUserId());
                map.put("CreatedDate",notifications.get(position).getCreatedDate());
                map.put("UpdatedByUserId", notifications.get(position).getUpdatedByUserId());
                map.put("UpdatedDate", timeStamp);
                map.put("Header",notifications.get(position).getHeader());
                map.put("ServerUpdatedStatus",0);


                List<LinkedHashMap> list = new ArrayList<>();
                Log.e("list==========",map+"");
                list.add(map);
              //
                notifications.get(position).setIsRead(1);
                notifyItemChanged(position);
//
             dataAccessHandler.executeRawQuery(Queries.getInstance().updateisread( +notifications.get(position).getId()+""));
//                notifications.get(position).setServerUpdatedStatus(0);
//                holder.createdDateTextView.setTextColor(Color.parseColor("#a6a6a6"));

                dataAccessHandler.updateData("Notification", list, true, " where Id = " + "'"+notifications.get(position).getId()+"'", new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        super.execute(success, result, msg);
                        notifications.get(position).setServerUpdatedStatus(0);


                        }
                });
                String unreadNotificationsCount = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUnreadNotificationsCountQuery(SyncHomeActivity.User_id));
                Log.e("unreadt====",unreadNotificationsCount);
                Intent in = new Intent("data_action");
                in.putExtra("category",unreadNotificationsCount );
                LocalBroadcastManager.getInstance(context).sendBroadcast(in);

//                dataAccessHandler.updateData("Notification", notifications, true, " where Id = " + "'"+notifications.get(position).getId()+"'", new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        super.execute(success, result, msg);
//                        holder.createdDateTextView.setTextColor(Color.parseColor("#a6a6a6"));
//                        notifications.get(position).setServerUpdatedStatus(0);
//                        notifications.get(position).setIsRead(0);
//                    }
//                });


            }

        });
//        AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {

        return notifications.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextView;
        TextView descriptionTextView;
        TextView createdDateTextView;
        ImageView thumbnail;
        CardView card_view ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headerTextView = (TextView) itemView.findViewById(R.id.notiHeaderTxt);
            descriptionTextView = (TextView) itemView.findViewById(R.id.notiDesc);
            createdDateTextView = (TextView) itemView.findViewById(R.id.notiDate);
            thumbnail =(ImageView)itemView.findViewById(R.id.imageView);
            card_view =(CardView)itemView.findViewById(R.id.card_view);
        }

        }
    }

