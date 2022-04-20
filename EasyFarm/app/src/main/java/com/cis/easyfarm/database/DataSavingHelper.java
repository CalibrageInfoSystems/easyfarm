package com.cis.easyfarm.database;



import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.cis.easyfarm.Objects.ActivityLog;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.SyncActivityLog;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.DataManager;
import com.cis.easyfarm.common.GeoBoundaries;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cis.easyfarm.common.CommonUtils.isNewPlotRegistration;



public class DataSavingHelper {

    private static final String LOG_TAG = DataSavingHelper.class.getName();
    private static String initialPestCode;
    private static int saveCropCount;

    public static void updatePlotSliptFarmerGeoboundaries(final Context context, final ApplicationThread.OnComplete<String> oncomplete) {

            List dataToSave = getGeoBoundriesData();

            Log.e("dataToSave=========",dataToSave+"");
             CommonUtils.appendLog(LOG_TAG, "updatePlotSliptFarmerGeoboundaries", dataToSave+"");
            boolean isRecordExistedInDb = false;
            DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
//            if (!TextUtils.isEmpty(CommonConstants.PLOT_CODE)) {
//                isRecordExistedInDb = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance()
//                        .checkRecordStatusInTable(DatabaseKeys.TABLE_GEOBOUNDARIES, "PlotCode", "GeoCategoryTypeId", CommonConstants.PLOT_CODE, 110));
//            }

                if (null != dataToSave && !dataToSave.isEmpty()) {
                //    saveRecordIntoActivityLog(context, CommonConstants.Plot_Point_Retake_Geo_Boundaries);

                    dataAccessHandler.insertMyData(DatabaseKeys.TABLE_GEOBOUNDARIES, dataToSave, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                Log.v(LOG_TAG, "@@@ saveNeighBourCropsData data saved successfully");
                                CommonUtils.appendLog(LOG_TAG, "updatePlotSliptFarmerGeoboundaries", "saveNeighBourCropsData data saved successfully");

                                DataManager.getInstance().deleteData(DataManager.PLOT_GEO_TAG);
                               // saveRecordIntoFarmerHistory(context, oncomplete);
                            } else {
                                Log.e(LOG_TAG, "@@@ saveNeighBourCropsData data saving failed due to " + msg);
                                CommonUtils.appendLog(LOG_TAG, "updatePlotSliptFarmerGeoboundaries", "saveNeighBourCropsData data saving failed due to " + msg);
                                oncomplete.execute(false, "data saving failed for saveGeoTagData", "");
                            }
                        }
                    });
                } else {

                }
            }




    private static void saveRecordIntoFarmerHistory(final Context context, final ApplicationThread.OnComplete<String> oncomplete) {
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        FarmerHistory farmerHistory = new FarmerHistory();
        farmerHistory.setFarmercode(CommonConstants.FARMER_CODE);
        farmerHistory.setPlotcode(CommonConstants.PLOT_CODE);

        if (CommonUtils.isNewRegistration() || CommonUtils.isNewPlotRegistration() || CommonUtils.isFromFollowUp()) {
            FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
            if (null != followUp) {
                if (null != followUp.getIsfarmerreadytoconvert()) {
                    if (followUp.getIsfarmerreadytoconvert() == 1) {
                        farmerHistory.setStatustypeid(Integer.parseInt(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotStatuesId(CommonConstants.STATUS_TYPE_ID_READY_TO_CONVERT))));
                    } else if (followUp.getIsfarmerreadytoconvert() == 0) {
                        farmerHistory.setStatustypeid(Integer.parseInt(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotStatuesId(CommonConstants.STATUS_TYPE_ID_PROSPECTIVE))));
                    }
                }
            } else {
                oncomplete.execute(true, "no data to save", "");
                return;
            }
        } else if (CommonUtils.isFromConversion()) {
            farmerHistory.setStatustypeid(Integer.parseInt(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotStatuesId(CommonConstants.STATUS_TYPE_ID_CONVERTED))));
        } else if (CommonUtils.isFromCropMaintenance()) {
            farmerHistory.setStatustypeid(Integer.parseInt(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotStatuesId(CommonConstants.STATUS_TYPE_ID_UPROOTED))));
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            farmerHistory.setStatustypeid(Integer.parseInt(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotStatuesId(CommonConstants.STATUS_TYPE_GEO_BOUNDARIES_TAKEN))));
        }

        farmerHistory.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        farmerHistory.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        farmerHistory.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        farmerHistory.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        farmerHistory.setServerUpdatedStatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
        farmerHistory.setIsactive(1);

        JSONObject ccData = null;
        List dataToInsert = new ArrayList();
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            ccData = new JSONObject(gson.toJson(farmerHistory));
            dataToInsert.add(CommonUtils.toMap(ccData));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "@@@ error while converting saveRecordIntoFarmerHistory data");
        }

// update the previous plotstatus..........

//        dataAccessHandler.upDataPlotStatus(farmerHistory.getPlotcode());
        // dataAccessHandler.upDataPlotStatus(CommonConstants.PLOT_CODE);


        dataAccessHandler.insertDataOld(DatabaseKeys.TABLE_FARMERHISTORY, dataToInsert, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                if (success) {
//                    Log.v(LOG_TAG, "@@@ saveRecordIntoFarmerHistory data saved successfully");
////                    oncomplete.execute(true, "data saved successfully", "");
//                    if (CommonUtils.isFromConversion()) {
//                        saveLandLordDetails(context, oncomplete);
//                    } else if(CommonUtils.isPlotSplitFarmerPlots()) {
//                        savePlotData(context,oncomplete);
//                    }else{
//                        saveWaterResourceData(context, oncomplete);
//                    }
                } else {
                    Log.e(LOG_TAG, "@@@ saveRecordIntoFarmerHistory data saving failed due to " + msg);
                    oncomplete.execute(false, "data saving failed saveRecordIntoFarmerHistory", "");
                }
            }
        });
    }


    public static List getLandLordIdProofsData() {
        List<IdentityProof> idProofsLandLordList = (List<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_IDPROOFS_DATA);
        List dataToInsert = new ArrayList();
        if (null != idProofsLandLordList && idProofsLandLordList.size() > 0) {
            for (IdentityProof identityProof : idProofsLandLordList) {
                identityProof.setUserCode(CommonConstants.FARMER_CODE);
                identityProof.setCreatedByUserId(Integer.parseInt(CommonConstants.USER_ID));
                identityProof.setCreatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                identityProof.setIsActive(true);
                identityProof.setUpdatedByUserId(Integer.parseInt(CommonConstants.USER_ID));
                identityProof.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                identityProof.setServerUpdatedStatus(true);

                JSONObject ccData = null;
                try {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    ccData = new JSONObject(gson.toJson(identityProof));
                    dataToInsert.add(CommonUtils.toMap(ccData));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "@@@ error while converting farmerPersonalDetails data");
                    CommonUtils.appendLog(LOG_TAG, "getLandLordIdProofsData", e.getMessage());
                }
            }
        }
        return dataToInsert;
    }

//    public static void saveLandLordDetails(final Context context, final ApplicationThread.OnComplete<String> onComplete) {
//        PlotLandlord plotLandlord = (PlotLandlord) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_LEASED_DATA);
//        if (null != plotLandlord) {
//            final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
//            boolean recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_PLOTLANDLORD, "PlotCode", plotLandlord.getPlotcode()));
//
//            if (!recordExisted && null != plotLandlord.getCreateddate()) {
//                plotLandlord.setCreatedbyuserid(plotLandlord.getCreatedbyuserid());
//                plotLandlord.setCreateddate(plotLandlord.getCreateddate());
//            } else {
//                plotLandlord.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
//                plotLandlord.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
//            }
//
//            plotLandlord.setPlotcode(CommonConstants.PLOT_CODE);
//            plotLandlord.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
//            plotLandlord.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
//            plotLandlord.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
//            plotLandlord.setIsactive(1);
//
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            JSONObject ccData = null;
//            List dataToInsert = null;
//            try {
//                ccData = new JSONObject(gson.toJson(plotLandlord));
//                dataToInsert = new ArrayList();
//                dataToInsert.add(CommonUtils.toMap(ccData));
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, "@@@ error while converting saveLandLordDetails data");
//            }
//            Log.v(LOG_TAG, "@@ entered data " + ccData.toString());
//            if (recordExisted) {
//                String whereCondition = " where  PlotCode = '" + plotLandlord.getPlotcode() + "'";
//                dataAccessHandler.updateData(DatabaseKeys.TABLE_PLOTLANDLORD, dataToInsert, true, whereCondition, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                            Log.v(LOG_TAG, "@@@ saveLandLordDetails data saved successfully");
//                            DataManager.getInstance().deleteData(DataManager.LANDLORD_LEASED_DATA);
//                           // saveLandLordBankDetails(context, onComplete);
//                        } else {
//                            Log.e(LOG_TAG, "@@@ saveLandLordDetails data saving failed due to " + msg);
//                            onComplete.execute(false, "data saving failed for saveLandLordDetails", "");
//                        }
//                    }
//                });
//            } else {
//                dataAccessHandler.insertDataOld(DatabaseKeys.TABLE_PLOTLANDLORD, dataToInsert, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                            Log.v(LOG_TAG, "@@@ saveLandLordDetails data saved successfully");
//                            DataManager.getInstance().deleteData(DataManager.LANDLORD_LEASED_DATA);
//                          //  saveLandLordBankDetails(context, onComplete);
//                        } else {
//                            Log.e(LOG_TAG, "@@@ saveLandLordDetails data saving failed due to " + msg);
//                            onComplete.execute(false, "data saving failed for saveLandLordDetails", "");
//                        }
//                    }
//                });
//            }
//        } else {
////            onComplete.execute(false, "data saving failed for saveLandLordDetails", "");
//           // saveWaterResourceData(context, onComplete);
//        }
//    }

    public static void saveRecordIntoActivityLog(final Context context, final int activityLogTypeId) {
        SyncActivityLog activityLog = new SyncActivityLog();
        activityLog.setFarmerCode(CommonConstants.FARMER_CODE);
        activityLog.setPlotCode(CommonConstants.PLOT_CODE);
        activityLog.setCreatedByUserId(SyncHomeActivity.User_id);
        activityLog.setCreatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
      activityLog.setServerUpdatedStatus(0);
        activityLog.setActivityTypeId(activityLogTypeId);

        JSONObject ccData = null;
        List dataToInsert = new ArrayList();
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            ccData = new JSONObject(gson.toJson(activityLog));
            dataToInsert.add(CommonUtils.toMap(ccData));
            Log.e("Activitylog=",dataToInsert+"");
            CommonUtils.appendLog(LOG_TAG, "saveRecordIntoActivityLog", dataToInsert+"");
        } catch (JSONException e) {
            Log.e(LOG_TAG, "@@@ error while converting saveRecordIntoActivityLog data");
            CommonUtils.appendLog(LOG_TAG, "saveRecordIntoActivityLog", e.getMessage());
        }

        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        dataAccessHandler.insertMyData(DatabaseKeys.TABLE_ACTIVITYLOG, dataToInsert, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                if (success) {
                    Log.v(LOG_TAG, "@@@ saveRecordIntoActivityLog data saved successfully");
                    CommonUtils.appendLog(LOG_TAG, "saveRecordIntoActivityLog","saveRecordIntoActivityLog data saved successfully");
                } else {
                    Log.e(LOG_TAG, "@@@ saveRecordIntoActivityLog data saving failed due to " + msg);
                    CommonUtils.appendLog(LOG_TAG, "saveRecordIntoActivityLog", "saveRecordIntoActivityLog data saving failed due to" + msg);

                }
            }
        });
    }


    public static List<GeoBoundaries> getGeoBoundriesData() {
        List geoBoundaries = new ArrayList<>();
        List<GeoBoundaries> savedGeoBoundaries = (List<GeoBoundaries>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG);
        Log.e("GeoBoundaries======",savedGeoBoundaries+"");
        CommonUtils.appendLog(LOG_TAG, "getGeoBoundriesData", savedGeoBoundaries+"");
        if (null != savedGeoBoundaries && !savedGeoBoundaries.isEmpty()) {
            for (GeoBoundaries gpsCoordinate : savedGeoBoundaries) {
                gpsCoordinate.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                gpsCoordinate.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                gpsCoordinate.setUpdatedbyuserid(SyncHomeActivity.User_id);
                gpsCoordinate.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                gpsCoordinate.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
                gpsCoordinate.setPlotcode(CommonConstants.PLOT_CODE);
                JSONObject ccData = null;
                try {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    ccData = new JSONObject(gson.toJson(gpsCoordinate));
                    geoBoundaries.add(CommonUtils.toMap(ccData));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "@@@ error while converting farmerPersonalDetails data");
                    CommonUtils.appendLog(LOG_TAG, "getGeoBoundriesData", e.getMessage());
                }
            }
        }
        return geoBoundaries;
    }
}
