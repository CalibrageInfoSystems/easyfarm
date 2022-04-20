package com.cis.easyfarm.ui.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Notifications;

import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.ProgressDialogFragment;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.model.ComplaintRepository;
import com.cis.easyfarm.model.ComplaintStatusHistory;
import com.cis.easyfarm.model.Complaints;

import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.Complaints_Activity;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cis.easyfarm.ui.Adapters.NotificationDisplayAdapter.DATE_FORMAT1;

public class GetcomplaintsAdapter extends RecyclerView.Adapter<GetcomplaintsAdapter.ViewHolder> {

    List<Complaints> list_complaints;
    public Context mContext;
    String datetimevaluereq, currentDate;
    String selectedItemID;
    int selectedPO;
    Button ok_btn, submitBtn;
    String AudioURL, imageur1;
    LinearLayout linearLayout_Play;

    TextInputLayout commentslbl;
    EditText comment_et;
    Boolean isreopen = false;

    //	private Chronometer chronometer;
    private List<String> image_list = new ArrayList<String>();
    ImageView iv1, iv2, iv3, imageViewPlay;
    LinearLayout voice_layout, imageLayout;
    SeekBar seekBar;
    private MediaPlayer mPlayer;
    private int lastProgress = 0;
    private Handler mHandler = new Handler();
    private int RECORD_AUDIO_REQUEST_CODE = 123;
    private boolean isPlaying = false;
    DecimalFormat df = new DecimalFormat("####0.00");
    private Subscription mSubscription;
    LayoutInflater mInflater;
    String reopencomments;

    int User_id;
    String Assigned_userid,Complaint_id;
    String Comments,plotcode;
    boolean setIsVideoRecording = false;
    private DataAccessHandler dataAccessHandler;

    List<ComplaintRepository> ComplaintRepo;
    // RecyclerView recyclerView;
    public GetcomplaintsAdapter(List<Complaints> list_loan, Context ctx) {
        this.list_complaints = list_loan;
        this.mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.complaint_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list_complaints.get(position).getCreatedDate());

            datetimevaluereq = output.format(oneWayTripDate);


            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.requestCode.setText(list_complaints.get(position).getCode());
        holder.req_date.setText(datetimevaluereq);
        holder.PlotId.setText(list_complaints.get(position).getPlotCode());
        dataAccessHandler = new DataAccessHandler(mContext);
//        if(list_complaints.get(position).getPlotSize()!=null) {
//            holder.plot_size.setText(df.format(list_complaints.get(position).getPlotSize()) +  " Acre");
//
//        }
      //  holder.location.setText(list_complaints.get(position).getVillageName());



        String status= dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComplaintstatusid(list_complaints.get(position).getCode(), list_complaints.get(position).getId()));
        String statuss = dataAccessHandler.getCountValue(Queries.getInstance().getstatusid(status));
        holder.statusType.setText(statuss);
        Log.d("Status", statuss + "");
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if ("Resolution Provided".equals(holder.statusType.getText())) {
           // if ("Open".equals(holder.statusType.getText())) {

            holder.close.setVisibility(View.VISIBLE);
            holder.reopen.setVisibility(View.VISIBLE);

        } else {
            holder.close.setVisibility(View.GONE);
            holder.reopen.setVisibility(View.GONE);

        }

        holder.close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedItemID = list_complaints.get(position).getCode();
                selectedPO = position;
              //  Complaintsstatus =
              //AssigntoUserId from ComplaintStatusHistory WHERE ComplaintCode  ='COM590PAPGNTRALTML00122-1' Group By Id
              Assigned_userid =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getAssignid(selectedItemID));
              Complaint_id = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCompaintid(selectedItemID));

                Log.e("Assigned_userid===",Assigned_userid+"");
              Comments =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComments(selectedItemID));
                plotcode=list_complaints.get(position).getPlotCode();
                showcloseDialog(selectedPO);
            }

        });


        holder.reopen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                    selectedItemID = list_complaints.get(position).getCode();
                    selectedPO = position;
                    showreopenDialog(selectedPO);
            }

        });


        holder.details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedItemID = list_complaints.get(position).getCode();

                selectedPO = position;
                showCondetailsDialog(selectedPO);


            }

        });
    }

    private void close_request() {
        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

        LinkedHashMap complaintsmap = new LinkedHashMap();

        complaintsmap.put("Id", 0);
        complaintsmap.put("ComplaintCode", selectedItemID);
        complaintsmap.put("StatusTypeId", 163);
        complaintsmap.put("AssigntoUserId",Assigned_userid);
        complaintsmap.put("Comments", Comments);
        complaintsmap.put("IsActive", 1);
        complaintsmap.put("CreatedByUserId",list_complaints.get(0).getCreatedByUserId());
        complaintsmap.put("CreatedDate", list_complaints.get(0).getCreatedDate());
        complaintsmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
        complaintsmap.put("UpdatedDate", timeStamp);
        complaintsmap.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> Complaint_list = new ArrayList<>();

        Complaint_list.add(complaintsmap);
        dataAccessHandler.updateData("ComplaintStatusHistory", Complaint_list, true, " where ComplaintCode = " + "'"+selectedItemID+"'", new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                super.execute(success, result, msg);
                list_complaints.get(selectedPO).setServerUpdatedStatus(0);
                Toast.makeText(mContext, "Complaint Closed Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SyncHomeActivity.class);
                mContext.startActivity(intent);

            }
        });

    }

    private void reopen_request() {
        String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());

        Complaint_id = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCompaintid(selectedItemID));
        Assigned_userid =dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getAssignid(selectedItemID));


        LinkedHashMap reopencomplaintmap = new LinkedHashMap();

        reopencomplaintmap.put("Id", 0);
        reopencomplaintmap.put("ComplaintCode", selectedItemID);
        reopencomplaintmap.put("StatusTypeId", 164);
        reopencomplaintmap.put("AssigntoUserId",Assigned_userid);
        reopencomplaintmap.put("Comments", reopencomments);
        reopencomplaintmap.put("IsActive", 1);
        reopencomplaintmap.put("CreatedByUserId",list_complaints.get(0).getCreatedByUserId());
        reopencomplaintmap.put("CreatedDate", list_complaints.get(0).getCreatedDate());
        reopencomplaintmap.put("UpdatedByUserId", SyncHomeActivity.User_id);
        reopencomplaintmap.put("UpdatedDate", timeStamp);
        reopencomplaintmap.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> ReopenComplaint_list = new ArrayList<>();

        ReopenComplaint_list.add(reopencomplaintmap);



        dataAccessHandler.updateData("ComplaintStatusHistory", ReopenComplaint_list, true, " where ComplaintCode = " + "'"+selectedItemID+"'", new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                super.execute(success, result, msg);
                list_complaints.get(selectedPO).setServerUpdatedStatus(0);
                Toast.makeText(mContext, "Complaint Reopened Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SyncHomeActivity.class);
                mContext.startActivity(intent);



            }
        });

    }

    private void showCondetailsDialog(int selectedPO) {

        final Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_images);
        imageViewPlay = (ImageView) dialog.findViewById(R.id.imageViewPlay);
        seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
        ok_btn = (Button) dialog.findViewById(R.id.buttonOk);
        iv1 = (ImageView) dialog.findViewById(R.id.iv);
        iv2 = (ImageView) dialog.findViewById(R.id.iv2);
        iv3 = (ImageView) dialog.findViewById(R.id.iv3);
        //linearLayout_Play=(LinearLayout)dialog.findViewById(R.id.linearLayout_Play)
        voice_layout = dialog.findViewById(R.id.linearLayout_Play);


        iv1.setVisibility(View.GONE);
        iv2.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);
        GetrequestRequestRepository();

//        String imageUrl = "https://via.placeholder.com/500";
//        Picasso.with(mContext).load(imageUrl).error(R.drawable.ic_user).into(iv1);
        //Loading image using Picasso
        // Picasso.get().load(imageUrl).into(iv1);
//
//

//        }
//

        imageViewPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isPlaying && AudioURL != null) {
                    isPlaying = true;
                    if (mPlayer != null) {
                        lastProgress = 0;
                        //chronometer.setBase(SystemClock.elapsedRealtime());
                        startPlaying();
                    } else {
                        startPlaying();
                    }

                } else {
                    isPlaying = false;
                    stopPlaying();
                }

            }
        });
        //  ok_btn = dialog.findViewById(R.id.btn_dialog);


/**
 * @param OnClickListner
 */
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                try {
                    mPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPlayer = null;
            }
        });
        dialog.show();

    }

    private void showreopenDialog(int selectedPO) {

        final Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_comments);
        commentslbl = (TextInputLayout) dialog.findViewById(R.id.comments_lbl);
        comment_et = (EditText) dialog.findViewById(R.id.comment_et);
        submitBtn = (Button) dialog.findViewById(R.id.submitBtn);

/**
 * @param OnClickListner
 */
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reopencomments = comment_et.getText().toString();
                dialog.dismiss();
                reopen_request();
                //Toast.makeText(mContext, "Please click on Re-open to Submit", Toast.LENGTH_SHORT).show();
              //  reopen_request();

            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    private void showcloseDialog(int selectedPO) {

            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.confirm)
                    .setMessage("Do you want to close the complaint")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            close_request();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }


    private void GetrequestRequestRepository() {

        ComplaintRepo =(List<ComplaintRepository>) dataAccessHandler.getComplaintrepoDetails(Queries.getInstance().getComplaitsRepo(selectedItemID), 1);

        final List<String> img = new ArrayList<>();
        if (ComplaintRepo.size() != 0) {

            for (int i = 0; i <ComplaintRepo.size(); i++) {
                int isRead  =  ComplaintRepo.get(i).getIsAudioRecording();
                Log.d("notification===", isRead + "");
                if (ComplaintRepo.get(i).getIsAudioRecording() == 1) {
                 voice_layout.setVisibility(View.VISIBLE);
                    AudioURL = ComplaintRepo.get(i).getFileLocation()+"/"+ ComplaintRepo.get(i).getFileName()+ ComplaintRepo.get(i).getFileExtension();
                    Log.d("GETComplaints==== Audio",AudioURL);
                } else {
                    voice_layout.setVisibility(View.GONE);
                    img.add( ComplaintRepo.get(i).getFileLocation()+"/"+ ComplaintRepo.get(i).getFileName()+ ComplaintRepo.get(i).getFileExtension());
                    Log.d("GETComplaints===images",  ComplaintRepo.get(i).getFileLocation()+"/"+ ComplaintRepo.get(i).getFileName()+ ComplaintRepo.get(i).getFileExtension());
//
                }
//                              if (getVisitRequestRepository.getListResult().get(i).getIsAudioRecording() == true) {
//                                  voice_layout.setVisibility(View.VISIBLE);
//                                  AudioURL = getVisitRequestRepository.getListResult().get(i).getFilePath();
//                                  Log.d("GETVisit",AudioURL);
//                              }
//                                else{
//                                    img.add(getVisitRequestRepository.getListResult().get(i).getFilePath());
//                                    Log.d("GETVisit", getVisitRequestRepository.getListResult().get(i).getFilePath());
////
//                               }

                for (int j = 0; j < img.size(); j++) {
                    final   int finalJ =j;
                    if (j == 0) {
                        iv1.setVisibility(View.VISIBLE);
                        // Picasso.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv1);
                        Glide.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv1);

                        iv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Context context = mContext.getApplicationContext();
                                mInflater = LayoutInflater.from(context);
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                                View mView = mInflater.inflate(R.layout.dialog_custom_layout, null);
                                TextView cancel =mView.findViewById(R.id.cancel);
                                //  Picasso.with(mContext).load(getCollectionInfoById.getResult().getReceiptImg()).error(R.drawable.ic_user).into(photoView);
                                PhotoView photoView = mView.findViewById(R.id.imageView);

                                Glide.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);
                               // Picasso.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);
                                //photoView.setImageResource(Integer.parseInt(getCollectionInfoById.getResult().getReceiptImg()));
                                mBuilder.setView(mView);

                                final AlertDialog mDialog = mBuilder.create();
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                                mDialog.show();
                            }
                        });
                    }
                    if (j == 1) {
                        iv2.setVisibility(View.VISIBLE);
                        //  Picasso.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv2);
                        Glide.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv2);

                        iv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Context context = mContext.getApplicationContext();
                                mInflater = LayoutInflater.from(context);
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                                View mView = mInflater.inflate(R.layout.dialog_custom_layout, null);
                                TextView cancel =mView.findViewById(R.id.cancel);
                                //  Picasso.with(mContext).load(getCollectionInfoById.getResult().getReceiptImg()).error(R.drawable.ic_user).into(photoView);
                                PhotoView photoView = mView.findViewById(R.id.imageView);
                                Glide.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);
                                //Picasso.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);
                                //photoView.setImageResource(Integer.parseInt(getCollectionInfoById.getResult().getReceiptImg()));
                                mBuilder.setView(mView);

                                final AlertDialog mDialog = mBuilder.create();
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                                mDialog.show();
                            }
                        });
                    }
                    if (j == 2) {
                        iv3.setVisibility(View.VISIBLE);
                        // Picasso.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv3);
                        Glide.with(mContext).load(img.get(j)).error(R.drawable.ic_user).into(iv3);
                        iv3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Context context = mContext.getApplicationContext();
                                mInflater = LayoutInflater.from(context);
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                                View mView = mInflater.inflate(R.layout.dialog_custom_layout, null);
                                TextView cancel =mView.findViewById(R.id.cancel);
                                //  Picasso.with(mContext).load(getCollectionInfoById.getResult().getReceiptImg()).error(R.drawable.ic_user).into(photoView);
                                PhotoView photoView = mView.findViewById(R.id.imageView);
                                Glide.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);

                                //Picasso.with(mContext).load(img.get(finalJ)).error(R.drawable.ic_user).into(photoView);
                                //photoView.setImageResource(Integer.parseInt(getCollectionInfoById.getResult().getReceiptImg()));
                                mBuilder.setView(mView);

                                final AlertDialog mDialog = mBuilder.create();
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                                mDialog.show();
                            }
                        });
                    }
                }


            }

        }
    }



    private void stopPlaying() {
        try {
            mPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer = null;
        //showing the play button
        imageViewPlay.setImageResource(R.drawable.ic_play);
        //chronometer.stop();

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(AudioURL);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        imageViewPlay.setImageResource(R.drawable.ic_pause);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);
        seekBar.setMax(mPlayer.getDuration());
        seekUpdation();
        //chronometer.start();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageViewPlay.setImageResource(R.drawable.ic_play);
                seekBar.setVisibility(View.GONE);
                isPlaying = false;
                //	chronometer.stop();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress);
                    //	chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    private void seekUpdation() {
        if (mPlayer != null) {
            int mCurrentPosition = mPlayer.getCurrentPosition();
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }







    @Override
    public int getItemCount() {

        if (list_complaints != null)
            return list_complaints.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView requestCode;
        public TextView req_date, close, reopen;
        public TextView statusType, cancel;
        public TextView PlotId, plot_size, location;
        LinearLayout details;

        public ViewHolder(View itemView) {
            super(itemView);


            requestCode = itemView.findViewById(R.id.requestCode);
            PlotId = itemView.findViewById(R.id.plotId);

            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);

            close = itemView.findViewById(R.id.close);
            reopen = itemView.findViewById(R.id.reopen);
            details = itemView.findViewById(R.id.details);


        }


    }
}


