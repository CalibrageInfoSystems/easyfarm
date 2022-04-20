package com.cis.easyfarm.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cis.easyfarm.Fragments.Adduser_fourthpage;
import com.cis.easyfarm.Fragments.Adduser_fragment;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.BaseActivity;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.DataManager;
import com.cis.easyfarm.common.GeoBoundaries;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DataSavingHelper;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.AddplotActivity;
import com.cis.easyfarm.ui.userAccount.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cis.easyfarm.common.CommonUtils.*;
import static com.cis.easyfarm.ui.FieldCalculatorActivity.firstFourCoordinates;
import static com.cis.easyfarm.ui.FieldCalculatorActivity.recordedBoundries;

public class PreViewAreaCalScreen extends BaseActivity {

    public static String gpsArea;
    private Button saveBtn, gpsStartBtn;
    private RelativeLayout gpsPointsLayout;
    private ActionBar actionBar;
    private TextView lat1p1, lat2p2, lat3p3, lat4p4, gpsAreaText;
    private TextView long1p1, long2p2, long3p3, long4p4;
    Plot plot = null;
    Toolbar toolbar;
    List<Farmer> farmerDetails;
    private AlertDialog alertDialog;

    public  String UpdatedDate,UpdatedByUserId,ServerUpdatedStatus,Is_Active;
    private EasyFarmDatabse easyfarmDatabase;
String farmerCode;
    List<Plot> plotDetails;
    public static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss";
    DataAccessHandler dataAccessHandler;
    List<LinkedHashMap> boundrilist = new ArrayList<>();
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.activity_pre_view_area_cal_screen, null);
        gpsPointsLayout = (RelativeLayout) parentView.findViewById(R.id.gpsPointsLL);
        baseLayout.addView(gpsPointsLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        settoolbar();

        dataAccessHandler = new DataAccessHandler(this);
        saveBtn = (Button) parentView.findViewById(R.id.land_save_btn);
        gpsStartBtn = (Button) parentView.findViewById(R.id.start_gps_btn);
        Intent i = getIntent();
        farmerCode = i.getStringExtra("code");

      Log.d("Code", farmerCode + "");

        easyfarmDatabase = EasyFarmDatabse.getEasyFarmDatabase(this);

       // easyfarmDatabase = EasyFarmDatabse.getPalm3FoilDatabase(this);

        gpsStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PreViewAreaCalScreen.this, FieldCalculatorActivity.class), 100);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if (null != firstFourCoordinates && !firstFourCoordinates.isEmpty() && !TextUtils.isEmpty(gpsArea)) {

               farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails(farmerCode), 1);

               String timeStamp = new SimpleDateFormat(DATE_FORMAT1).format(Calendar.getInstance().getTimeInMillis());
               plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getFarmerplotDetails(farmerCode), 1);
               LinkedHashMap GeoBoundriesData = new LinkedHashMap();

//               Log.e("recordedBoundriessize===", firstFourCoordinates.size() + "");
//               for (int i=0;i<firstFourCoordinates.size();i++) {
//                   GeoBoundriesData.put("Id", i);
//                   GeoBoundriesData.put("PlotCode", plotDetails.get(0).getCode());
//
//                   GeoBoundriesData.put("Latitude", firstFourCoordinates.get(i).latitude);
//                   GeoBoundriesData.put("Longitude", firstFourCoordinates.get(i).longitude);
////    GeoBoundriesData.put("Latitude", 17.5002321);
////                  GeoBoundriesData.put("Longitude",78.3933972);
//
//                   GeoBoundriesData.put("GeoCategoryTypeId", 110);
//                   GeoBoundriesData.put("IsActive", 1);
//                   GeoBoundriesData.put("CreatedByUserId", plotDetails.get(0).getCreatedByUserId());
//                   GeoBoundriesData.put("CreatedDate", timeStamp);
//                   GeoBoundriesData.put("UpdatedByUserId", plotDetails.get(0).getUpdatedByUserId());
//                   GeoBoundriesData.put("UpdatedDate", timeStamp);
//                   GeoBoundriesData.put("ServerUpdatedStatus", 0);
//
//                   Log.e("GeoBoundriesData====", GeoBoundriesData + "");
//                    boundrilist.add(GeoBoundriesData);
//
//               }
//                   dataAccessHandler.insertMyData("GeoBoundaries", boundrilist, new ApplicationThread.OnComplete<String>() {
//                       @Override
//                       public void execute(boolean success, String result, String msg) {
//
//                           if (success) {
//
//                               Log.d("GeoBoundaries", "Inserted Successfully");
//                               farmerDetails.get(0).setServerupdatedstatus(0);
//
//                           }
//                       }
//                   });

//                    plot = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);
//                    if (plot == null) {
//                        DataAccessHandler dataAccessHandler = new DataAccessHandler(PreViewAreaCalScreen.this);
//                        plot = (Plot) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getSelectedPlot(CommonConstants.PLOT_CODE), 0);
//                    }
//                    try {
//                       plot.setGPSPlotArea(Double.parseDouble(gpsArea));
//                    } catch (NumberFormatException nfe) {
//                        Toast.makeText(PreViewAreaCalScreen.this, nfe.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("Crashed====", nfe.getLocalizedMessage() + "");
//                        Log.d("Crashingggg====", nfe.getMessage() + "");
//                    }
////
                 //  DataManager.getInstance().addData(DataManager.PLOT_DETAILS, plot);
                    DataManager.getInstance().addData(DataManager.PLOT_GEO_TAG, getGeoBoundriesData());
           //
               DataSavingHelper.updatePlotSliptFarmerGeoboundaries(getApplicationContext(), new ApplicationThread.OnComplete<String>() {
                   @Override
                   public void execute(boolean success, String result, String msg) {
                       super.execute(success, result, msg);
                       if(success){
                           firstFourCoordinates.clear();
                           recordedBoundries.clear();
                           gpsArea = null;
                           Toast.makeText(PreViewAreaCalScreen.this, "sucess", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
             //  displayDialogWindow("GeoBoundaries are  saving in db",alertDialog,PreViewAreaCalScreen.this);
// set Fragmentclass Arguments
              //  updateFarmerHistory();
                   Adduser_fourthpage fragobj = new Adduser_fourthpage();

                Intent intent = new Intent();
                intent.putExtra("edttext", gpsArea);
                setResult( 111,intent); // You can also send result without any data using setResult(int resultCode)
                   finish();
//                    if(isPlotSplitFarmerPlots())
//                    {
//
//                    }
//
//                    if(!isPlotSplitFarmerPlots()){
//                        firstFourCoordinates.clear();/**/
//                        recordedBoundries.clear();
//                        gpsArea = null;
//                        finish();
//                    }

                } else {
                    UiUtils.showCustomToastMessage("Please caluculate area", PreViewAreaCalScreen.this, 1);
                }
          }
        });

        gpsAreaText = (TextView) parentView.findViewById(R.id.gps_text);

        lat1p1 = (TextView) parentView.findViewById(R.id.lat1p1);
        lat2p2 = (TextView) parentView.findViewById(R.id.lat2p2);
        lat3p3 = (TextView) parentView.findViewById(R.id.lat3p3);
        lat4p4 = (TextView) parentView.findViewById(R.id.lat4p4);


        long1p1 = (TextView) parentView.findViewById(R.id.long1p1);
        long2p2 = (TextView) parentView.findViewById(R.id.long2p2);
        long3p3 = (TextView) parentView.findViewById(R.id.long3p3);
        long4p4 = (TextView) parentView.findViewById(R.id.long4p4);
    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gps Points");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateFarmerHistory() {

        UpdatedDate = getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS);
        ServerUpdatedStatus = CommonConstants.ServerUpdatedStatus;
        UpdatedByUserId = String.valueOf(Integer.parseInt(SyncHomeActivity.User_id+""));
        Is_Active = "0" ;
    easyfarmDatabase.UpdateFarmerhistory(gpsArea,UpdatedDate,Is_Active);
        displayDialogWindow("GeoBoundaries are  saving in db",alertDialog,PreViewAreaCalScreen.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) return;
        try {
            updateGpsData(data);
        } catch (Exception e) {
            Log.pushExceptionToCrashlytics(new OilPalmException(e.getMessage()));
            UiUtils.showCustomToastMessage("Retake the GeoBundries", PreViewAreaCalScreen.this, 1);

            e.printStackTrace();
        }
    }

    public void updateGpsData(Intent data) throws Exception {

        gpsArea = String.valueOf(data.getDoubleExtra("area", 0));
        gpsAreaText.setText("" + gpsArea + " Ha");

        if (null != firstFourCoordinates && firstFourCoordinates.size() > 4) {
            lat1p1.setText("" + firstFourCoordinates.get(0).latitude);
            lat2p2.setText("" + firstFourCoordinates.get(1).latitude);
            lat3p3.setText("" + firstFourCoordinates.get(2).latitude);
            lat4p4.setText("" + firstFourCoordinates.get(3).latitude);

            long1p1.setText("" + firstFourCoordinates.get(0).longitude);
            long2p2.setText("" + firstFourCoordinates.get(1).longitude);
            long3p3.setText("" + firstFourCoordinates.get(2).longitude);
            long4p4.setText("" + firstFourCoordinates.get(3).longitude);
        } else {
            lat1p1.setText("");
            lat2p2.setText("");
            lat3p3.setText("");
            lat4p4.setText("");

            long1p1.setText("");
            long2p2.setText("");
            long3p3.setText("");
            long4p4.setText("");
        }
    }

    public List<GeoBoundaries> getGeoBoundriesData() {
        List<GeoBoundaries> geoBoundaries = new ArrayList<>();
        if (null != recordedBoundries && !recordedBoundries.isEmpty())  {
            for (int i = 0; i <recordedBoundries.size() ; i++) {
                GeoBoundaries geoBoundary = new GeoBoundaries();
                geoBoundary.setLatitude(recordedBoundries.get(i).latitude);
                geoBoundary.setLongitude(recordedBoundries.get(i).longitude);
                geoBoundary.setGeocategorytypeid(110);
                geoBoundaries.add(geoBoundary);
            }
//            for (GPSCoordinate gpsCoordinate : recordedBoundries) {
//
//                geoBoundary.setLatitude(gpsCoordinate.latitude);
//                geoBoundary.setLongitude(gpsCoordinate.longitude);
//                geoBoundary.setGeocategorytypeid(206);
//                geoBoundaries.add(geoBoundary);
//            }

        }
        return geoBoundaries;
    }

    public   void  displayDialogWindow(String s, AlertDialog alertDialog, final Context context) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogRootView = layoutInflater.inflate(R.layout.dialog_custom,null );
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogRootView);
        this.alertDialog = alertDialogBuilder.create();
        final TextView textView = (TextView)dialogRootView.findViewById(R.id.description);
        final TextView okBtn = (TextView)dialogRootView.findViewById(R.id.okBtn);
        final AlertDialog finalAlertDialog = this.alertDialog;
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSavingHelper.updatePlotSliptFarmerGeoboundaries(context, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        super.execute(success, result, msg);
                        if(success){
                            firstFourCoordinates.clear();
                            recordedBoundries.clear();
                            gpsArea = null;
                            Toast.makeText(PreViewAreaCalScreen.this, "sucess", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finalAlertDialog.dismiss();
                finish();
            }
        });
        textView.setText(s);
        // create an alert dialog

//        alertDialog.getWindow()
//                .getAttributes().windowAnimations = R.style.DialogAnimation;
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
        this.alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                }
                return true;
            }
        });

    }

}


