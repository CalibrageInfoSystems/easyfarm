package com.cis.easyfarm.sync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.cis.easyfarm.Objects.BankDetails;
import com.cis.easyfarm.Objects.BankDetailsHistory;
import com.cis.easyfarm.Objects.BuyerDetails;
import com.cis.easyfarm.Objects.DataCountModel;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.FileRepository;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.Objects.InsuranceDetailsObject;
import com.cis.easyfarm.Objects.IrrigationDetails;
import com.cis.easyfarm.Objects.LocationTracker;
import com.cis.easyfarm.Objects.Notifications;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.PlotCropCycleObject;
import com.cis.easyfarm.Objects.PlotStatusHistory;
import com.cis.easyfarm.Objects.PowerDetails;
import com.cis.easyfarm.Objects.SoilDetails;
import com.cis.easyfarm.Objects.SoilTestDetails;
import com.cis.easyfarm.Objects.SyncActivityLog;
import com.cis.easyfarm.Objects.UserRoleXref;
import com.cis.easyfarm.Objects.VendorDetails;
import com.cis.easyfarm.Objects.geoBoundaries;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.cloudHelper.CloudDataHandler;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.HttpClient;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.GeoBoundaries;
import com.cis.easyfarm.common.ProgressBar;
import com.cis.easyfarm.common.ProgressDialogFragment;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DatabaseKeys;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.model.ComplaintRepository;
import com.cis.easyfarm.model.ComplaintStatusHistory;
import com.cis.easyfarm.model.ComplaintTypeXref;
import com.cis.easyfarm.model.Complaints;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.cis.easyfarm.ui.userAccount.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.cis.easyfarm.cloudHelper.HttpClient.getOkHttpClient;
import static com.cis.easyfarm.cloudHelper.HttpClient.post;


public class DataSynchelper {

    public static String jsonString;
    public static String reversesyncdate;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String LOG_TAG = DataSynchelper.class.getName();
    public static String PREVIOUS_SYNC_DATE = "previous_sync_date";
    public static String PREVIOUS_RSYNC_DATE = "previous_rsync_date";
    public static String nCount = "nCount";

    public static Set<String> transDataTableNames;
    public static LinkedHashMap<String, List> dataToUpdate = new LinkedHashMap<>();
    public static int countCheck, transactionsCheck = 0, imagesCount = 0, reverseSyncTransCount = 0, innerCountCheck = 0;
    public static List<String> refreshtableNamesList = new ArrayList<>();
    public static LinkedHashMap<String, List> refreshtransactionsDataMap = new LinkedHashMap<>();
    public static String unreadNotificationsCount;

    EasyFarmDatabse sqliteHelper;

    public static synchronized void performMasterSync(final Context context, final boolean firstTimeInsertFinished, final ApplicationThread.OnComplete onComplete) {
        LinkedHashMap<String, String> syncDataMap = new LinkedHashMap<>();

        syncDataMap.put("LastUpdatedDate", "");
//        syncDataMap.put("LastUpdatedDate", "");
//        syncDataMap.put("Index", "0");
        countCheck = 0;

        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        ProgressBar.showProgressBar(context, "Making data ready for you...");
        CloudDataHandler.getMasterData(Config.live_url + Config.masterSyncUrl, syncDataMap, new ApplicationThread.OnComplete<HashMap<String, List>>() {
            @Override
            public void execute(boolean success, final HashMap<String, List> masterData, String msg) {
                if (success) {
                    if (masterData != null && masterData.size() > 0) {
                        Log.v(LOG_TAG, "@@@ Master sync is success and data size is " + masterData.size());
                        CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Master sync is success and data size is " + masterData.size());
                        final Set<String> tableNames = masterData.keySet();
                        for (final String tableName : tableNames) {
                            Log.v(LOG_TAG, "@@@ Delete Query " + String.format(Queries.getInstance().deleteTableData(), tableName));
                            CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Delete Query " + String.format(Queries.getInstance().deleteTableData(), tableName));

                            ApplicationThread.dbPost("Master Data Sync..", "master data", new Runnable() {
                                @Override
                                public void run() {
                                    countCheck++;
                                    if (!firstTimeInsertFinished) {
                                        dataAccessHandler.deleteRow(tableName, null, null, false, new ApplicationThread.OnComplete<String>() {
                                            @Override
                                            public void execute(boolean success, String result, String msg) {
                                                if (success) {
                                                    dataAccessHandler.insertData(true, tableName, masterData.get(tableName), new ApplicationThread.OnComplete<String>() {
                                                        @Override
                                                        public void execute(boolean success, String result, String msg) {
                                                            if (success) {
                                                                Log.v(LOG_TAG, "@@@ sync success for " + tableName);
                                                                CommonUtils.appendLog(LOG_TAG, "performMasterSync", "sync success for " + tableName);

                                                            } else {
                                                                Log.v(LOG_TAG, "@@@ check 1 " + masterData.size() + "...pos " + countCheck);
                                                                Log.v(LOG_TAG, "@@@ sync failed for " + tableName + " message " + msg);
                                                                CommonUtils.appendLog(LOG_TAG, "performMasterSync", "sync failed for " + tableName + " message " + msg);

                                                            }
                                                            if (countCheck == masterData.size()) {
                                                                Log.v(LOG_TAG, "@@@ Done with master sync " + countCheck);
                                                                CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Done with master sync " + countCheck);

                                                                ProgressBar.hideProgressBar();
                                                                // CommonUtils.appendLog("Master Data Synced Successfully");
                                                                CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Master Data synced successfully");
                                                                UiUtils.showCustomToastMessage("Master Data synced successfully", context, 0);
                                                                onComplete.execute(true, null, "Sync is success");
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Log.v(LOG_TAG, "@@@ Master table deletion failed for " + tableName);
                                                    CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Master table deletion failed for " + tableName);

                                                }
                                            }
                                        });
                                    } else {
                                        dataAccessHandler.insertData(tableName, masterData.get(tableName), new ApplicationThread.OnComplete<String>() {
                                            @Override
                                            public void execute(boolean success, String result, String msg) {
                                                if (success) {
                                                    Log.v(LOG_TAG, "@@@ sync success for " + tableName);
                                                    CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Sync success for " + tableName);

                                                } else {
                                                    Log.v(LOG_TAG, "@@@ check 2 " + masterData.size() + "...pos " + countCheck);
                                                    Log.v(LOG_TAG, "@@@ sync failed for " + tableName + " message " + msg);
                                                    CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Sync failed for " + tableName);

                                                }
                                                if (countCheck == masterData.size()) {
                                                    Log.v(LOG_TAG, "@@@ Done with master sync " + countCheck);
                                                    CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Done with master sync " + countCheck);
                                                    ProgressBar.hideProgressBar();
                                                    onComplete.execute(true, null, "Sync is success");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {
                        ProgressBar.hideProgressBar();
                        Log.v(LOG_TAG, "@@@ Sync is up-to-date");
                        CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Sync is up-to-date");
                        onComplete.execute(true, null, "Sync is up-to-date");
                    }
                } else {
                    ProgressBar.hideProgressBar();
                    onComplete.execute(false, null, "Master sync failed. Please try again");
                    CommonUtils.appendLog(LOG_TAG, "performMasterSync", "Master sync failed. Please try again");

                }
            }
        });
    }

    public static synchronized void performRefreshTransactionsSync(final Context context, final ApplicationThread.OnComplete onComplete) {
        countCheck = 0;
        transactionsCheck = 0;
        reverseSyncTransCount = 0;
        imagesCount = 0;
        refreshtableNamesList.clear();
        refreshtransactionsDataMap.clear();
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        ProgressBar.showProgressBar(context, "Sending data to server...");
        ApplicationThread.bgndPost(LOG_TAG, "getting transactions data", new Runnable() {
            @Override
            public void run() {
                getRefreshSyncTransDataMap(context, new ApplicationThread.OnComplete<LinkedHashMap<String, List>>() {
                    @Override
                    public void execute(boolean success, final LinkedHashMap<String, List> transDataMap, String msg) {
                        if (success) {
                            if (transDataMap != null && transDataMap.size() > 0) {
                                Log.v(LOG_TAG, "transactions data size " + transDataMap.size());
                                transDataTableNames = transDataMap.keySet();
                                Log.v(LOG_TAG, "transDataTableNames" + transDataTableNames);
                                refreshtableNamesList.addAll(transDataTableNames);
                                Log.v(LOG_TAG, "refreshtableNamesList  " + refreshtableNamesList);
                                refreshtransactionsDataMap = transDataMap;
                                Log.v(LOG_TAG, "refreshtransactionsDataMap  " + refreshtransactionsDataMap);
                                //  sendTrackingData(context, onComplete);
                                postTransactionsDataToCloud(context, refreshtableNamesList.get(transactionsCheck), dataAccessHandler, onComplete);

                            }
                        } else {
                            ProgressBar.hideProgressBar();
                            Log.v(LOG_TAG, "@@@ Transactions sync failed due to data retrieval error");
                            onComplete.execute(false, null, "Transactions sync failed due to data retrieval error");
                        }
                    }
                });
            }
        });

    }

    public static void postTransactionsDataToCloud(final Context context, final String tableName, final DataAccessHandler dataAccessHandler, final ApplicationThread.OnComplete onComplete) {
        Log.v(LOG_TAG, "tableName======" + tableName);
        List cctransDataList = refreshtransactionsDataMap.get(tableName);
        Log.v(LOG_TAG, "transDataTableNames" + cctransDataList);
        if (null != cctransDataList && cctransDataList.size() > 0) {
            Type listType = new TypeToken<List>() {
            }.getType();

            Log.v(LOG_TAG, "listType" + listType);
            Gson gson = null;
            if (tableName.equalsIgnoreCase("Consignment")) {
                gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
            } else {
                gson = new GsonBuilder().serializeNulls().create();
            }
            String dat = gson.toJson(cctransDataList, listType);
            dat = dat.substring(1, dat.length() - 1);


            JSONObject transObj = new JSONObject();
            try {
                transObj.put(tableName, new JSONArray(dat));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "@@@@ check.." + transObj.toString());
            CloudDataHandler.placeDataInCloud(transObj, Config.live_url + Config.SyncTransactions, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    if (success) {

                        dataAccessHandler.executeRawQuery(String.format(Queries.getInstance().updateServerUpdatedStatus(), tableName));
                        Log.v(LOG_TAG, "@@@ Transactions sync success for " + tableName);
                        transactionsCheck++;
                        if (transactionsCheck == refreshtransactionsDataMap.size()) {
                            Log.v(LOG_TAG, "@@@ Done with transactions sync " + transactionsCheck);
                            onComplete.execute(true, null, "Sync is success");
//                            final List<ImageDetails> imagesData = dataAccessHandler.getImageDetails();
//                p            if (null != imagesData && !imagesData.isEmpty()) {
//                                sendImageDetails(context, imagesData, dataAccessHandler, onComplete);
//                            } else {
//                                ProgressBar.hideProgressBar();
//                                onComplete.execute(true, null, "Sync is success");
//                            }
                        } else {
                            postTransactionsDataToCloud(context, refreshtableNamesList.get(transactionsCheck), dataAccessHandler, onComplete);
                        }
                    } else {
                        ApplicationThread.uiPost(LOG_TAG, "Sync is failed", new Runnable() {
                            @Override
                            public void run() {
                                UiUtils.showCustomToastMessage("Sync failed for " + tableName, context, 1);
                            }
                        });
                        transactionsCheck++;
                        if (transactionsCheck == refreshtransactionsDataMap.size()) {
                            Log.v(LOG_TAG, "@@@ Done with transactions sync " + transactionsCheck);
//                            final List<ImageDetails> imagesData = dataAccessHandler.getImageDetails();
//                            if (null != imagesData && !imagesData.isEmpty()) {
//                                sendImageDetails(context, imagesData, dataAccessHandler, onComplete);
//                            } else {
//                                ProgressBar.hideProgressBar();
//                                onComplete.execute(true, null, "Sync is success");
//                            }
                        } else {
                            postTransactionsDataToCloud(context, refreshtableNamesList.get(transactionsCheck), dataAccessHandler, onComplete);
                        }
                        Log.v(LOG_TAG, "@@@ Transactions sync failed for " + tableName);
                    }
                }
            });
        } else {
            transactionsCheck++;
            if (transactionsCheck == refreshtransactionsDataMap.size()) {
                Log.v(LOG_TAG, "@@@ Done with transactions sync " + transactionsCheck);
//                final List<ImageDetails> imagesData = dataAccessHandler.getImageDetails();
//                if (null != imagesData && !imagesData.isEmpty()) {
//                    sendImageDetails(context, imagesData, dataAccessHandler, onComplete);
//                } else {
//                    ProgressBar.hideProgressBar();
//                    onComplete.execute(true, null, "Sync is success");
//                    Log.v(LOG_TAG, "@@@ Done with transactions sync " + transactionsCheck);
//
//                }
            } else {
                postTransactionsDataToCloud(context, refreshtableNamesList.get(transactionsCheck), dataAccessHandler, onComplete);
            }
        }
    }

    public static void updateSyncDate(Context context, String date) {
        Log.v(LOG_TAG, "@@@ saving date into");
        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
        sharedPreferences.edit().putString(PREVIOUS_SYNC_DATE, date).apply();
        Log.d("PreviousSyncDate", PREVIOUS_SYNC_DATE + "");
        Log.d("PreviousSyncDateeeee", date + "");

        CommonUtils.appendLog(LOG_TAG, "updateSyncDate", PREVIOUS_SYNC_DATE + "");
        CommonUtils.appendLog(LOG_TAG, "updateSyncDate", date + "");

    }

    public static void updateRSyncDate(Context context, String date) {
        Log.v(LOG_TAG, "@@@ saving date into");
        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
        sharedPreferences.edit().putString(PREVIOUS_RSYNC_DATE, date).apply();
        Log.d("PreviousSyncDate", PREVIOUS_RSYNC_DATE + "");
        Log.d("PreviousSyncDateeeee", date + "");

        CommonUtils.appendLog(LOG_TAG, "updateSyncDate", PREVIOUS_RSYNC_DATE + "");
        CommonUtils.appendLog(LOG_TAG, "updateSyncDate", date + "");

    }

    public static void updateNotificationCount(Context context, String notiCount) {

//        Log.d("updateNotificationCount", nCount + "");
//        Log.d("updateNotificationCount", notiCount + "");

//        CommonUtils.appendLog(LOG_TAG, "unreadNotificationsCount", nCount + "");
//        CommonUtils.appendLog(LOG_TAG, "unreadNotificationsCount", notiCount + "");

    }

    public static void getRefreshSyncTransDataMap(final Context context, final ApplicationThread.OnComplete onComplete) {

        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        List<Farmer> farmerDetails = (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerDetailsServerUpdatedStatus(), 1);
        List<IdentityProof> identityProofList = (List<IdentityProof>) dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getIdentityProofsServerUpdatedStatus(), 1);
        List<BankDetails> farmerBankList = (List<BankDetails>) dataAccessHandler.getFarmerBankData(Queries.getInstance().getBankDetailsServerUpdatedStatus(), 1);
//        List<BankDetailsHistory> BankDetailsHistorylist = (List<BankDetailsHistory>) dataAccessHandler.getBankDetailsHistoryData(Queries.getInstance().getBankDetailshistoryRefresh(), 1);
        List<Plot> plotList = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getplotsServerUpdatedStatus(), 1);
        //List<PlotStatusHistory> PlotStatusHistoryList = (List<PlotStatusHistory>) dataAccessHandler.getPlotplotStatusHistories(Queries.getInstance().getplotStatusServerUpdatedStatus(), 1);
        List<FileRepository> fileRepoList = (List<FileRepository>) dataAccessHandler.getFileRepositoryData(Queries.getInstance().getFileRepositoryRefresh(), 1);
        List<SoilDetails> soilDetails = (List<SoilDetails>) dataAccessHandler.getSoilDetails(Queries.getInstance().getSoilDetailsServerUpdatedStatus(), 1);
        List<PowerDetails> powerDetails = (List<PowerDetails>) dataAccessHandler.getPowerDetails(Queries.getInstance().getPowerDetailsServerUpdatedStatus(), 1);
        List<IrrigationDetails> irrigationDetails = (List<IrrigationDetails>) dataAccessHandler.getIrrigationDetails(Queries.getInstance().getIrrigationDetailsServerUpdatedStatus(), 1);
        List<Notifications> notificationsList = (List<Notifications>) dataAccessHandler.getNotification(Queries.getInstance().getnotificationServerUpdatedStatus(), 1);


        List<GeoBoundaries> geoBoundariesList = (List<GeoBoundaries>) dataAccessHandler.getGeoboudryDetails(Queries.getInstance().getgeoboundryServerUpdatedStatus(), 1);
        // List<geoBoundaries> GeoBoundariesList = (List<geoBoundaries>) dataAccessHandler.getGeoboudryDetails(Queries.getInstance().getgeoboundryServerUpdatedStatus(), 1);
        List<BuyerDetails> buyerDetails = (List<BuyerDetails>) dataAccessHandler.getBuyerDetails(Queries.getInstance().getBuyerDetailsServerUpdatedStatus(), 1);
        List<SyncActivityLog> activityLogList = (List<SyncActivityLog>) dataAccessHandler.getActivityLogs(Queries.getInstance().getActivityServerUpdatedStatus(), 1);
        // List<SyncActivityLog> activityLogList = dataAccessHandler.getActivityLogs();
        List<UserRoleXref> userRoleXref = (List<UserRoleXref>) dataAccessHandler.getUserRoleXrefDetails(Queries.getInstance().getUserInfoXrefServerUpdatedStatus(), 1);
        List<SoilTestDetails> soilTestReportList = (List<SoilTestDetails>) dataAccessHandler.getSoilTestDetails(Queries.getInstance().getSoilTestDetailsServerUpdatedStatus(), 1);
        List<VendorDetails> vendorDetails = (List<VendorDetails>) dataAccessHandler.getVendorDetails(Queries.getInstance().getVendorDetailsServerUpdatedStatus(), 1);
        List<Complaints> ComplaintsDetails = (List<Complaints>) dataAccessHandler.getComplaintsDetails(Queries.getInstance().getComplaintrDetailsServerUpdatedStatus(), 1);
        List<ComplaintRepository> ComplaintsrepoDetails = (List<ComplaintRepository>) dataAccessHandler.getComplaintrepoDetails(Queries.getInstance().getComplaintrepoDetailsServerUpdatedStatus(), 1);
        List<ComplaintStatusHistory> ComplaintstatusDetails = (List<ComplaintStatusHistory>) dataAccessHandler.getComplaintstatusDetails(Queries.getInstance().getComplaintstatusDetailsServerUpdatedStatus(), 1);
        List<ComplaintTypeXref> ComplaintxrefDetails = (List<ComplaintTypeXref>) dataAccessHandler.getComplaintxrefDetails(Queries.getInstance().getComplaintxrfsDetailsServerUpdatedStatus(), 1);
        List<InsuranceDetailsObject> insuranceDetails = (List<InsuranceDetailsObject>) dataAccessHandler.getInsuranceDetails(Queries.getInstance().getInsuranceDetailsServerUpdatedStatus(), 1);
        List<PlotCropCycleObject> plotCropCycleObject = (List<PlotCropCycleObject>) dataAccessHandler.getPlotCropCycle(Queries.getInstance().getPlotCropCycleServerUpdatedStatus(), 1);


        LinkedHashMap<String, List> allRefreshDataMap = new LinkedHashMap<>();

        allRefreshDataMap.put("farmer", farmerDetails);
        allRefreshDataMap.put("identityProofs", identityProofList);
        allRefreshDataMap.put("bankDetails", farmerBankList);
        allRefreshDataMap.put("plots", plotList);
        allRefreshDataMap.put("GeoBoundaries", geoBoundariesList);
        allRefreshDataMap.put("fileRepositories", fileRepoList);
        allRefreshDataMap.put("soilDetails", soilDetails);
        allRefreshDataMap.put("powerDetails", powerDetails);
        allRefreshDataMap.put("irrigationDetails", irrigationDetails);
        allRefreshDataMap.put("notificationDetails", notificationsList);
        allRefreshDataMap.put("buyerDetails", buyerDetails);
        allRefreshDataMap.put("ActivityLog", activityLogList);
        allRefreshDataMap.put("userRoles", userRoleXref);
        allRefreshDataMap.put("soilTestDetails", soilTestReportList);
        allRefreshDataMap.put("vendorDetails", vendorDetails);
        allRefreshDataMap.put("complaints", ComplaintsDetails);
        allRefreshDataMap.put("complaintRepository", ComplaintsrepoDetails);
        allRefreshDataMap.put("complaintStatusHistory", ComplaintstatusDetails);
        allRefreshDataMap.put("complaintTypeXref", ComplaintxrefDetails);
        allRefreshDataMap.put("InsuranceDetails", insuranceDetails);
        allRefreshDataMap.put("PlotCropCycle", plotCropCycleObject);


        jsonString = new Gson().toJson(allRefreshDataMap, Map.class);
        Log.d("JsonString", jsonString);
        CommonUtils.appendLog(LOG_TAG, "getRefreshSyncTransDataMap", jsonString);

        //onComplete.execute(true, allRefreshDataMap, "here is collection center transactions data");

    }


    public static synchronized void performReverseSync(final Context context, final ProgressDialogFragment progressDialogFragment, final ApplicationThread.OnComplete onComplete) {
        LinkedHashMap<String, String> syncDataMap = new LinkedHashMap<>();
        syncDataMap.put("LastUpdatedDate", "");

        getRefreshSyncTransDataMap(context, onComplete);

        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);

         reversesyncdate = sharedPreferences.getString(PREVIOUS_RSYNC_DATE, null);

        final ProgressDialogFragment finalProgressDialogFragment = progressDialogFragment;
        try {
            JSONObject obj = new JSONObject(jsonString);
            syncReverse(obj, Config.live_url + Config.SyncTransactions, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
//                            super.execute(success, result, msg);
                    if (success) {

                        updateRSyncDate(context, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));


                        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
                        dataAccessHandler.updateserverupdatesStatusforUserInfo(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS UserInfo", "ServerUpdatedStatus Success for UserInfo");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforBankDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUSBankDetails", "ServerUpdatedStatus Success for BankDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforIdentityProofs(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS IdentityProofs", "ServerUpdatedStatus Success for ID Proofs");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforPlot(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS Plot", "ServerUpdatedStatus Success for Plot");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforGeoBoundaries(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS GeoBoundaries", "ServerUpdatedStatus Success for GeoBoundaries");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforSoilDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS SoilDetails", "ServerUpdatedStatus Success for SoilDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforPowerDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS PowerDetails", "ServerUpdatedStatus Success for PowerDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforIrrigationDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS Irrigation Details", "ServerUpdatedStatus Success for IrrigationDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforNotificationDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS Notifications", "ServerUpdatedStatus Success for Notifications");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforActivityLog(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS Activity Log", "ServerUpdatedStatus Success for Activity Log");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforUserRoles(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS UserRoles", "ServerUpdatedStatus Success for UserRoles");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforBuyerDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS BuyerDetails", "ServerUpdatedStatus Success for BuyerDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforVendorDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS VendorDetails", "ServerUpdatedStatus Success for VendorDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforSoilTestDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS SoilTestDetails", "ServerUpdatedStatus Success for SoilTestDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforComplaints(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS Complaints", "ServerUpdatedStatus Success for Complaints");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforComplaintRepisitory(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS ComplaintRepisitory", "ServerUpdatedStatus Success for ComplaintRepisitory");
                            }
                        });


                        dataAccessHandler.updateserverupdatesStatusforComplaintStatusHistory(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS ComplaintHistory", "ServerUpdatedStatus Success for ComplaintStatusHistory");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforComplaintTypeXref(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS ComplaintTypeXref", "ServerUpdatedStatus Success for ComplaintTypeXref");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforInsuranceDetails(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS InsuranceDetails", "ServerUpdatedStatus Success for InsuranceDetails");
                            }
                        });

                        dataAccessHandler.updateserverupdatesStatusforPlotCropCycle(new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                super.execute(success, result, msg);

                                Log.d("SUS PlotCropCycle", "ServerUpdatedStatus Success for PlotCropCycle");
                            }
                        });

//                        dataAccessHandler.executeRawQuery(String.format(Queries.getInstance().updateServerUpdatedStatus(), transDataTableNames));
                        finalProgressDialogFragment.dismiss();
                        UiUtils.showCustomToastMessage("Successfully data sent to server", context, 0);

                        //   UiUtils.showCustomToastMessage("Successfully data sent to server", context, 1);
                    }
                    Log.v(LOG_TAG, "is Success :" + success + "\n" + "result :" + result + "\n" + "msg :" + msg);
                    finalProgressDialogFragment.dismiss();
                }
            });
        } catch (JSONException e) {
            finalProgressDialogFragment.dismiss();
            CommonUtils.appendLog(LOG_TAG, "performReverseSync", e.getMessage());
            //   Log.e(LOG_TAG, "" + e.getMessage());
        }


    }

    public static synchronized void syncReverse(final JSONObject values, final String url, final ApplicationThread.OnComplete<String> onComplete) {
        ApplicationThread.bgndPost(DataSynchelper.class.getName(), "placeDataInCloud..", new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient.postDataToServerjson(url, values, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                Log.e("============", "Successfully data sent to server");
                                CommonUtils.appendLog(LOG_TAG, "syncReverse", "Successfully data sent to server");
                                // UiUtils.showCustomToastMessage("Successfully data sent to server", ge, 1);
                                try {
                                    onComplete.execute(success, result.toString(), msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    onComplete.execute(success, result, msg);
                                }


                            } else
                                onComplete.execute(success, result, msg);
                        }
                    });
                } catch (Exception e) {
                    Log.v(LOG_TAG, "@Error while getting " + e.getMessage());
                    CommonUtils.appendLog(LOG_TAG, "performReverseSync", e.getMessage());

                }
            }
        });

    }


    public static void startTransactionSync(final Context context, final ProgressDialogFragment progressDialogFragment) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);

        String date = sharedPreferences.getString(PREVIOUS_SYNC_DATE, null);

        final String finalDate = date;
        Log.e(LOG_TAG, "Analysis============869" + date);
        Log.e(LOG_TAG, "Analysis============870" + finalDate);

        CommonUtils.appendLog(LOG_TAG, "startTransactionSync", date + "");
        CommonUtils.appendLog(LOG_TAG, "startTransactionSync", finalDate + "");

        progressDialogFragment.updateText("Getting total records count");
        final ProgressDialogFragment finalProgressDialogFragment = progressDialogFragment;
        getCountOfHits(finalDate, new ApplicationThread.OnComplete() {
            @Override
            public void execute(boolean success, Object result, String msg) {
                if (success) {
                    Log.v(LOG_TAG, "@@@@ count here " + result.toString());
                    List<DataCountModel> dataCountModelList = (List<DataCountModel>) result;
                    prepareIndexes(finalDate, dataCountModelList, context, finalProgressDialogFragment);
                } else {
                    if (null != finalProgressDialogFragment) {
                        finalProgressDialogFragment.dismiss();
                    }
                    UiUtils.showCustomToastMessage("Transaction sync failed due to data issue", context, 1);
                }
            }
        });
    }

    public static void prepareIndexes(final String date, List<DataCountModel> countData, final Context context, ProgressDialogFragment progressDialogFragment) {
        try {
            if (!countData.isEmpty()) {
                Log.d("Mahesh", "@prepareIndexes size :" + countData.size());
                CommonUtils.appendLog(LOG_TAG, "prepareIndexes", countData.size() + "");

                reverseSyncTransCount = 0;
                transactionsCheck = 0;
                dataToUpdate.clear();
                final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
                new DownLoadData(context, date, countData, 0, 0, dataAccessHandler, progressDialogFragment).execute();
            } else {
                Log.d("Mahesh", "@prepareIndexes size in Else :" + countData.size());
                CommonUtils.appendLog(LOG_TAG, "prepareIndexes", countData.size() + "");

                ProgressBar.hideProgressBar();
                if (null != progressDialogFragment) {
                    progressDialogFragment.dismiss();
                }
                CommonUtils.showToast("There is no transactions data to sync", context);
                CommonUtils.appendLog(LOG_TAG, "prepareIndexes", "There is no transactions data to sync");

            }
        } catch (Exception e) {
            Log.d("mahesh", "prepareIndexes: e" + e.toString());
            CommonUtils.appendLog(LOG_TAG, "prepareIndexes", e.getMessage());
            e.printStackTrace();
        }
    }

    // TOdo Check
    public static void getCountOfHits(String date, final ApplicationThread.OnComplete onComplete) {
        String countUrl = "";
        LinkedHashMap<String, String> syncDataMap = new LinkedHashMap<>();
//        if(null == date  || date.isEmpty())
//        {
//            syncDataMap.put("Date", "null");
//        }else {
//            syncDataMap.put("Date", date);
//        }


        syncDataMap.put("Date", TextUtils.isEmpty(date) ? "null" : date);
        syncDataMap.put("UserId", SyncHomeActivity.User_id + "");
        Log.e(LOG_TAG, "Analysis============925" + String.valueOf(syncDataMap));
        CommonUtils.appendLog(LOG_TAG, "getCountOfHits", String.valueOf(syncDataMap));

        //   syncDataMap.put("IsUserDataAccess", CommonConstants.migrationSync);
        countUrl = Config.live_url + String.format(Config.getReverseTranscationSyncCount);
        CloudDataHandler.getGenericData(countUrl, syncDataMap, new ApplicationThread.OnComplete<List<DataCountModel>>() {
            @Override
            public void execute(boolean success, List<DataCountModel> result, String msg) {
                Log.d("@@Mahesh", "getCountOfHits  ===> Success :" + success);
                CommonUtils.appendLog(LOG_TAG, "getCountOfHits", success + "");
                CommonUtils.appendLog(LOG_TAG, "getCountOfHits", result + "");
                CommonUtils.appendLog(LOG_TAG, "getCountOfHits", msg);

                onComplete.execute(success, result, msg);
            }
        });
    }


    public static class DownLoadData extends AsyncTask<String, String, String> {

        private static final MediaType TEXT_PLAIN = MediaType.parse("application/x-www-form-urlencoded");
        private Context context;
        private String date;
        private List<DataCountModel> totalData;
        private int totalDataCount;
        private int currentIndex;
        private DataAccessHandler dataAccessHandler;
        private ProgressDialogFragment progressDialogFragment;

        // Todo save
        public DownLoadData(final Context context, final String date, final List<DataCountModel> totalData, int totalDataCount, int currentIndex, DataAccessHandler dataAccessHandler, ProgressDialogFragment progressDialogFragment) {
            this.context = context;
            this.totalData = totalData;
            this.date = date;
            this.totalDataCount = totalDataCount;
            this.currentIndex = currentIndex;
            this.dataAccessHandler = dataAccessHandler;
            this.progressDialogFragment = progressDialogFragment;
        }

        @Override
        protected String doInBackground(String... params) {
            String resultMessage = null;
            Response response = null;
            String countUrl = Config.live_url + String.format(Config.getTransData, totalData.get(totalDataCount).getMethodName());
            Log.v(LOG_TAG, "countUrl===== " + countUrl);
            CommonUtils.appendLog(LOG_TAG, "doInBackground", countUrl + "");

            final String tableName = totalData.get(totalDataCount).getTableName();
            Log.d("doInBackground Line 927", "====> Analysis ===> sync Table :" + tableName);
            CommonUtils.appendLog(LOG_TAG, "doInBackground", tableName + "");
            progressDialogFragment.updateText("Downloading " + tableName + " (" + currentIndex + "/" + totalData.get(totalDataCount).getCount() + ")" + " data");
            try {
                URL obj = new URL(countUrl);
                Map<String, String> syncDataMap = new LinkedHashMap<>();
                syncDataMap.put("Date", TextUtils.isEmpty(date) ? "null" : date);
                syncDataMap.put("UserId", SyncHomeActivity.User_id + "");
                //syncDataMap.put("UserId",SyncHomeActivity.User_id+"");
                syncDataMap.put("Index", String.valueOf(currentIndex));
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("User-Agent", USER_AGENT);
                Log.d("maheshGeoBoundaries", "@@ ==> Id " + SyncHomeActivity.User_id);
                Log.d(LOG_TAG, "Analysis========982 " + syncDataMap);

                CommonUtils.appendLog(LOG_TAG, "doInBackground", SyncHomeActivity.User_id + "");
                CommonUtils.appendLog(LOG_TAG, "doInBackground", syncDataMap + "");

                final StringBuilder sb = new StringBuilder();
                boolean first = true;
                RequestBody requestBody = null;
                for (Map.Entry<String, String> entry : syncDataMap.entrySet()) {
                    if (first) first = false;
                    else sb.append("&");

                    sb.append(URLEncoder.encode(entry.getKey(), HTTP.UTF_8)).append("=")
                            .append(URLEncoder.encode(entry.getValue().toString(), HTTP.UTF_8));

                    Log.d(LOG_TAG, "\nposting key: " + entry.getKey() + " -- value: " + entry.getValue());
                }
                requestBody = RequestBody.create(TEXT_PLAIN, sb.toString());

                Request request = HttpClient.buildRequest(countUrl, "POST", (requestBody != null) ? requestBody : RequestBody.create(TEXT_PLAIN, "")).build();
                OkHttpClient client = getOkHttpClient();
                response = client.newCall(request).execute();
                int statusCode = response.code();
                Log.d(LOG_TAG, "tableName============1" + tableName);
                CommonUtils.appendLog(LOG_TAG, "doInBackground", tableName + "");

                final String strResponse = response.body().string();


                Log.d(LOG_TAG, " ############# POST RESPONSE ################ (" + statusCode + ")\n\n" + strResponse + "\n\n");
                // JSONArray dataArray = new JSONArray(strResponse);

                JSONObject jsonObject = new JSONObject(strResponse);
                Log.d(LOG_TAG, "jsonObject==========" + jsonObject);
                CommonUtils.appendLog(LOG_TAG, "doInBackground", jsonObject + "");


                JSONArray dataArray = jsonObject.getJSONArray("ListResult");
                Log.d(LOG_TAG, "dataArray==========" + dataArray);
                CommonUtils.appendLog(LOG_TAG, "doInBackground", dataArray + "");

                if (statusCode == HttpURLConnection.HTTP_OK) {
                    Log.d(LOG_TAG, "tableName============3" + tableName);
                    CommonUtils.appendLog(LOG_TAG, "doInBackground", tableName + "");

//

                    if (TextUtils.isEmpty(date)) {
                        // if (false) {
//                        if (tableName.equalsIgnoreCase("BankDetails")) {
//                            Log.v(LOG_TAG, "@@@@ BankDetails ");
                        Log.d("mahesh", "@@ ==> Date is Empty First Time Sync " + tableName);
                        List dataToInsert = new ArrayList();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject eachDataObject = dataArray.getJSONObject(i);
                            dataToInsert.add(CommonUtils.toMap(eachDataObject));
                        }

                        dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                CommonUtils.appendLog(LOG_TAG, "doInBackground", "Data insertion status " + result);


                            }
                        });


                    } else {

                        Log.d("mahesh", "@@ ==> Date is Empty date Time Sync " + tableName);
                        if (tableName.equalsIgnoreCase("BankDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);

                        } else if (tableName.equalsIgnoreCase("BankDetailsHistory")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("IdentityProofs")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("FileRepository")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("UserInfo")) {

                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                                Log.d("Mahesh", "=====>  Userinfo Files Count :" + eachDataObject.get("PPFileLocation") + "   And Table Name:" + tableName);
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>  Userinfo Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                        } else if (tableName.equalsIgnoreCase("Plot")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("PlotStatusHistory")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("PowerDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("SoilDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("IrrigationDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("Notification")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("BuyerDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.trim().equalsIgnoreCase("ActivityLog")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                                dataAccessHandler.deleteRow(tableName, "Id", eachDataObject.get("Id").toString(), true, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
//                                             super.execute(success, result, msg);
                                        Log.d("Data Sync Helper ", "Delete Activity log if data exist id :");
                                    }
                                });
                            }


                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
//                                 dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>   ActivityLog Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                        } else if (tableName.trim().equalsIgnoreCase("GeoBoundaries")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            Log.d("mahesh", "=====>   GeoBoundaries Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.trim().equalsIgnoreCase("LocationTracker")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                            }
                            Log.d("mahesh", "=====>   LocationTracker Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.trim().equalsIgnoreCase("UserRoleXref")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));


                                dataAccessHandler.deleteUserXrefRow(eachDataObject.getInt("UserId"), eachDataObject.getInt("RoleId"), new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
                                        super.execute(success, result, msg);

                                        Log.d("Data Sync Helper ", "Delete UserRoleXRef Log if data exist");
                                    }
                                });

                            }
                            Log.d("mahesh", "=====>   UserRoleXref Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
//                                 dataToUpdate.put(tableName, dataToInsert);
                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
                        } else if (tableName.trim().equalsIgnoreCase("SoilTestDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            Log.d("mahesh", "=====>   SoilTestDetails Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.trim().equalsIgnoreCase("VendorDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            Log.d("mahesh", "=====>   VendorDetails Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                            dataToUpdate.put(tableName, dataToInsert);
                        } else if (tableName.equalsIgnoreCase("Complaints")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);

                        } else if (tableName.equalsIgnoreCase("ComplaintRepository")) {

                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                                dataAccessHandler.deleteRow(tableName, "Id", eachDataObject.get("Id").toString(), true, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
//                                             super.execute(success, result, msg);
                                        Log.d("Data Sync Helper ", "Delete ComplaintRepository if data exist id :");
                                    }
                                });
                            }


                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
//                                 dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>   ComplaintRepository Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);

                        }

                        else if (tableName.equalsIgnoreCase("ComplaintStatusHistory")) {

                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                                dataAccessHandler.deleteRow(tableName, "Id", eachDataObject.get("Id").toString(), true, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
//                                             super.execute(success, result, msg);
                                        Log.d("Data Sync Helper ", "Delete ComplaintStatusHistory if data exist id :");
                                    }
                                });
                            }


                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
//                                 dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>   ComplaintStatusHistory Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);

                        }

                        else if (tableName.equalsIgnoreCase("ComplaintTypeXref")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));
                            }
                            dataToUpdate.put(tableName, dataToInsert);
                        }
                        else if (tableName.trim().equalsIgnoreCase("InsuranceDetails")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                                dataAccessHandler.deleteRow(tableName, "Id", eachDataObject.get("Id").toString(), true, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
//                                             super.execute(success, result, msg);
                                        Log.d("Data Sync Helper ", "Delete InsuranceDetails if data exist id :");
                                    }
                                });
                            }


                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
//                                 dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>   InsuranceDetails Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                        }
                        else if (tableName.trim().equalsIgnoreCase("PlotCropCycle")) {
                            List dataToInsert = new ArrayList();

                            for (int j = 0; j < dataArray.length(); j++) {
                                JSONObject eachDataObject = dataArray.getJSONObject(j);
                                dataToInsert.add(CommonUtils.toMap(eachDataObject));

                                dataAccessHandler.deleteRow(tableName, "Id", eachDataObject.get("Id").toString(), true, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
//                                             super.execute(success, result, msg);
                                        Log.d("Data Sync Helper ", "Delete PlotCropCycle if data exist id :");
                                    }
                                });
                            }


                            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    Log.v(LOG_TAG, "@@@@ Data insertion status " + result);
                                }
                            });
//                                 dataToUpdate.put(tableName, dataToInsert);
                            Log.d("Mahesh", "=====>   PlotCropCycle Files Count :" + dataToInsert.size() + "   And Table Name:" + tableName);
                        }

                    }

                    resultMessage = "success";
                } else {
                    resultMessage = "failed";
                }
            } catch (Exception e) {
                resultMessage = e.getMessage();
                Log.e(LOG_TAG, "@@@ data sync failed for " + tableName);
                CommonUtils.appendLog(LOG_TAG, "doInBackground", "Data sync failed for " + tableName);

            }
            return resultMessage;
        }

        @Override
        protected void onPostExecute(String result) {
            currentIndex++;
            Log.d("mahesh", "@@ second time sync total data count");

            if (currentIndex == totalData.get(totalDataCount).getCount()) {
                currentIndex = 0;
                totalDataCount++;
                if (totalDataCount == totalData.size()) {
                    Log.v(LOG_TAG, "@@@ done with data syncing");
                    if (TextUtils.isEmpty(date)) {
                        //if (false) {
                        ProgressBar.hideProgressBar();
                        progressDialogFragment.dismiss();
//                        if (null != progressDialogFragment && !CommonUtils.currentActivity.isFinishing()) {
//                            progressDialogFragment.dismiss();
//                        }
                        UiUtils.showCustomToastMessage("Data synced successfully", context, 0);
                        CommonUtils.appendLog(LOG_TAG, "onPostExecute", "Data synced successfully");
                        unreadNotificationsCount = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUnreadNotificationsCountQuery(SyncHomeActivity.User_id));
                        Log.d("whatisncount:",  unreadNotificationsCount + "");

                        Intent i = new Intent(context, SyncHomeActivity.class);
                        i.putExtra("nCount", unreadNotificationsCount);
                        context.startActivity(i);

                        updateSyncDate(context, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
//                        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
//                        String date = sharedPreferences.getString(PREVIOUS_SYNC_DATE, null);
//                        Log.d("SharedPrefDate", date + "");
//                        Log.d("CurrentDate&Time", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    } else {
                        reverseSyncTransCount = 0;
                        Set tableNames = dataToUpdate.keySet();
                        List<String> tableNamesList = new ArrayList();
                        tableNamesList.addAll(tableNames);
                        updateTransactionData(dataToUpdate, dataAccessHandler, tableNamesList, progressDialogFragment, new ApplicationThread.OnComplete() {
                            @Override
                            public void execute(boolean success, Object result, String msg) {
                                if (success) {
                                    updateSyncDate(context, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                                    progressDialogFragment.dismiss();
                                    UiUtils.showCustomToastMessage("Data synced successfully", context, 0);
                                    CommonUtils.appendLog(LOG_TAG, "onPostExecute", "Data synced successfully");
                                    unreadNotificationsCount = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUnreadNotificationsCountQuery(SyncHomeActivity.User_id));
                                    Log.d("whatisncount:",  unreadNotificationsCount + "");

                                    Intent i = new Intent(context, SyncHomeActivity.class);
                                    i.putExtra("nCount", unreadNotificationsCount);
                                    context.startActivity(i);

                                } else {
                                    UiUtils.showCustomToastMessage(msg, context, 1);
                                }
//                                if (null != progressDialogFragment && !CommonUtils.currentActivity.isFinishing()) {
//                                    progressDialogFragment.dismiss();
//                                }
                            }
                        });
                    }
                } else {
                    Log.v(LOG_TAG, "@@@ data downloading next count " + currentIndex + " out of " + totalData.size());
                    new DownLoadData(context, date, totalData, totalDataCount, currentIndex, dataAccessHandler, progressDialogFragment).execute();
                }
            } else {
                Log.v(LOG_TAG, "@@@ data downloading next count " + currentIndex + " out of " + totalData.size());
                new DownLoadData(context, date, totalData, totalDataCount, currentIndex, dataAccessHandler, progressDialogFragment).execute();
            }
        }
    }

    public static synchronized void updateTransactionData(final LinkedHashMap<String, List> transactionsData, final DataAccessHandler dataAccessHandler, final List<String> tableNames, final ProgressDialogFragment progressDialogFragment, final ApplicationThread.OnComplete onComplete) {
        progressDialogFragment.updateText("Updating data...");
        ProgressBar.hideProgressBar();
        progressDialogFragment.dismiss();
        if (transactionsData != null && transactionsData.size() > 0) {
            Log.v(LOG_TAG, "@@@ Transactions sync is success and data size is " + transactionsData.size());
            CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Transactions sync is success and data size is " + transactionsData.size());

            final String tableName = tableNames.get(reverseSyncTransCount);
            innerCountCheck = 0;
            updateDataIntoDataBase(transactionsData, dataAccessHandler, tableName, new ApplicationThread.OnComplete() {
                @Override
                public void execute(boolean success, Object result, String msg) {
                    if (success) {
                        reverseSyncTransCount++;
                        if (reverseSyncTransCount == transactionsData.size()) {
                            onComplete.execute(success, "data updated successfully", "");
                            CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Data updated successfully");

                        } else {
                            updateTransactionData(transactionsData, dataAccessHandler, tableNames, progressDialogFragment, onComplete);
                        }
                    } else {
                        reverseSyncTransCount++;
                        if (reverseSyncTransCount == transactionsData.size()) {
                            onComplete.execute(success, "data updated successfully", "");
                            CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Data updated successfully");

                        } else {
                            updateTransactionData(transactionsData, dataAccessHandler, tableNames, progressDialogFragment, onComplete);
                        }
                    }
                }
            });
        } else {
            onComplete.execute(false, "data not found to save", "");
        }
    }

    public static synchronized void updateDataIntoDataBase(final LinkedHashMap<String, List> transactionsData, final DataAccessHandler dataAccessHandler, final String tableName, final ApplicationThread.OnComplete onComplete) {
        final List dataList = transactionsData.get(tableName);
        List dataToInsert = new ArrayList();
        JSONObject ccData = null;
        Gson gson = new GsonBuilder().serializeNulls().create();

        boolean recordExisted = false;
        String whereCondition = null;

        if (dataList.size() > 0) {

            if (tableName.equalsIgnoreCase("KMFKG")) {
                FileRepository fileRepository = (FileRepository) dataList.get(innerCountCheck);
                whereCondition = " where  Code = '" + fileRepository.getPlotCode() + "'";
                recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "PlotCode", fileRepository.getPlotCode()));
            } else if (tableName.equalsIgnoreCase("BNJKJKFJ")) {

                BankDetails bankdata = (BankDetails) dataList.get(innerCountCheck);

                bankdata.setServerUpdatedStatus(1);
                whereCondition = " where  Code = '" + bankdata.getUserCode() + "'";
                try {
                    ccData = new JSONObject(gson.toJson(bankdata));
                    whereCondition = " where  Code = '" + bankdata.getUserCode() + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

                recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "UserCode", bankdata.getUserCode()));
            } else if (tableName.equalsIgnoreCase("UserInfo")) {
                Log.d("Mahesh", "=====> Userinfo Insert :" + dataToInsert.size());
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Code = '" + ccData.get("Code") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getuserInfoCount(ccData.get("Code").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist UserInfo COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }


//                   List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //                  recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Code", ccData.get("Code").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Former ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("BankDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  UserCode = '" + ccData.get("UserCode") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getbankdetailsCount(ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BankDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }
//                    recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "UserCode", ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BankDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("BankDetailsHistory")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getbankdetailsHistoryCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BankDetailsHistory COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "UserCode", ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BankDetailsHistory ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("IdentityProofs")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where IdProofTypeId =" + ccData.get("IdProofTypeId") + "  AND UserCode = '" + ccData.get("UserCode") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIdProofCount(ccData.get("UserCode").toString(), ccData.get("IdProofTypeId").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BankDetailsHistory COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //    recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "IdProofTypeId = "+"'"+ccData.get("IdProofTypeId").toString()+"'"+" AND UserCode", ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist IdentityProofs ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("FileRepository")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  UserCode = '" + ccData.get("UserCode") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "UserCode", ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist UserCode ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("Plot")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Code = '" + ccData.get("Code") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getplotdetailsCount(ccData.get("Code").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Plot COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Code", ccData.get("Code").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Plot ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("PlotStatusHistory")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getplotstatusdetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PlotStatusHistory COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PlotStatusHistory ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("PowerDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getpowerDetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PowerDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }


//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PowerDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("SoilDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getsoilDetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist SoilDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist SoilDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("IrrigationDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getirrigationDetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist IrrigationDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    //recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist IrrigationDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("Notification")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getnotificationCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Notification COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist UserCode ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("GeoBoundaries")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getGeoboundries(ccData.get("Id").toString()));
                    Log.d("@@ maheshGeoBoundaries", "====> Check Record Exist PowerDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ maheshGeoBoundaries", "====> Check Record Exist GeoBoundaries ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }
            } else if (tableName.equalsIgnoreCase("BuyerDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  UserCode = '" + ccData.get("UserCode") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));
     
                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getBuyerDetailsCount(ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BuyerDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist BuyerDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("ActivityLog")) {
                try {


                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getActivityLogCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ActivityLog COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ActivityLog ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }
            } else if (tableName.equalsIgnoreCase("UserRoleXref")) {
                try {

                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where UserId =" + ccData.get("UserId") + "  AND RoleId = '" + ccData.get("RoleId") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUserRoleXrefCount(ccData.get("UserId").toString(), ccData.get("RoleId").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist UserRoleXref COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist UserRoleXref ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }
            } else if (tableName.equalsIgnoreCase("SoilTestDetails")) {
                try {


                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getSoilTestDetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ActivityLog COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist SoilTestDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }
            } else if (tableName.equalsIgnoreCase("VendorDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  UserCode = '" + ccData.get("UserCode") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getVendorDetailsCount(ccData.get("UserCode").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist VendorDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist VendorDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("Complaints")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Code = '" + ccData.get("Code") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComplaintsCount(ccData.get("Code").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Complaints COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist Complaints ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("ComplaintRepository")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComplaintRepositoryCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintRepository COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintRepository ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("ComplaintStatusHistory")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComplaintStatusHistoryCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintStatusHistory COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintStatusHistory ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("ComplaintTypeXref")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getComplaintTypeXrefCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintTypeXref COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist ComplaintTypeXref ==> " + recordExisted);
                } catch (JSONException e) {

                }

            } else if (tableName.equalsIgnoreCase("InsuranceDetails")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getInsuranceDetailsCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist InsuranceDetails COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist InsuranceDetails ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            } else if (tableName.equalsIgnoreCase("PlotCropCycle")) {
                try {
                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
                    dataToInsert.add(CommonUtils.toMap(ccData));

                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPlotCropCycleCount(ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PlotCropCycle COunt==> " + count);
                    if (null != count && Integer.parseInt(count) > 0) {
                        recordExisted = true;
                    }

//                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
                    // recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
                    Log.d("@@ mahesh", "====> Check Record Exist PlotCropCycle ==> " + recordExisted);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
                }

            }


//            else if(tableName.equalsIgnoreCase("BuyerDetails"))
//            {
//                try {
//                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
//                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
//                    dataToInsert.add(CommonUtils.toMap(ccData));
//
////                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getnotificationCount(ccData.get("Id").toString()));
////                    Log.d("@@ mahesh", "====> Check Record Exist Notification COunt==> "+count);
////                    if(null != count && Integer.parseInt(count) > 0)
////                    {
////                        recordExisted = true;
////                    }
//
////                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
//                     recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
//                    Log.d("@@ mahesh", "====> Check Record Exist BuyerDetails ==> "+recordExisted);
//                } catch (JSONException e) {
//                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
//                }
//
//            }

//            else if(tableName.equalsIgnoreCase("ActivityLog"))
//            {
//                try {
//                    ccData = new JSONObject(gson.toJson(dataList.get(innerCountCheck)));
//                    whereCondition = " where  Id = '" + ccData.get("Id") + "'";
//                    dataToInsert.add(CommonUtils.toMap(ccData));
//
////                    String count = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getnotificationCount(ccData.get("Id").toString()));
////                    Log.d("@@ mahesh", "====> Check Record Exist Notification COunt==> "+count);
////                    if(null != count && Integer.parseInt(count) > 0)
////                    {
////                        recordExisted = true;
////                    }
//
////                    List<Farmer> formersexist =  (List<Farmer>) dataAccessHandler.getfarmerDetails(Queries.getInstance().getFarmerPersonalDetails( ccData.get("Code").toString()), 1);
//                    recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Id", ccData.get("Id").toString()));
//                    Log.d("@@ mahesh", "====> Check Record Exist ActivityLog ==> "+recordExisted);
//                } catch (JSONException e) {
//                    Log.e(LOG_TAG, "####" + e.getLocalizedMessage());
//                }
//
//            }

//               recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(tableName, "Code", data.getComplaintCode()));
            if (dataList.size() != innerCountCheck) {
                Log.d("@@@ mahesh", "====> Analysis ==> updateDataIntoDataBase => table :" + tableName);
                CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "updateDataIntoDataBase => table :" + tableName);
                updateOrInsertData(tableName, dataToInsert, whereCondition, recordExisted, dataAccessHandler, new ApplicationThread.OnComplete() {
                    @Override
                    public void execute(boolean success, Object result, String msg) {
                        innerCountCheck++;
                        if (innerCountCheck == dataList.size()) {
                            innerCountCheck = 0;
                            onComplete.execute(true, "", "");
                        } else {
                            updateDataIntoDataBase(transactionsData, dataAccessHandler, tableName, onComplete);
                        }
                    }
                });
            } else {
                onComplete.execute(true, "", "");
            }
        } else {

            final Context context = null;

            onComplete.execute(true, "", "");

            UiUtils.showCustomToastMessage("Data synced successfully", context, 0);
            CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Data synced successfully");


//            innerCountCheck++;
//            if (innerCountCheck == dataList.size()) {
//                innerCountCheck = 0;
//                onComplete.execute(true, "", "");
//            } else {
//                updateDataIntoDataBase(transactionsData, dataAccessHandler, tableName, onComplete);
//            }
        }

    }

    public static void updateOrInsertData(final String tableName, List dataToInsert, String whereCondition, boolean recordExisted, DataAccessHandler dataAccessHandler, final ApplicationThread.OnComplete onComplete) {
        if (recordExisted) {
            dataAccessHandler.updateData(tableName, dataToInsert, true, whereCondition, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    onComplete.execute(success, null, "Sync is " + success + " for " + tableName);
                    CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Sync is " + success + " for " + tableName);

                }
            });
        } else {
            dataAccessHandler.insertData(tableName, dataToInsert, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    onComplete.execute(true, null, "Sync is " + success + " for " + tableName);
                    CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Sync is " + success + " for " + tableName);

                }
            });
        }
    }

//    public static void getAlertsData(final Context context, final ApplicationThread.OnComplete<String> onComplete) {
//
//
//        CloudDataHandler.getGenericData(Config.live_url + Config.GET_ALERTS + CommonConstants.USER_ID, new ApplicationThread.OnComplete<String>() {
//            @Override
//            public void execute(boolean success, String result, String msg) {
//                if (success) {
//                    final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
//                    dataAccessHandler.executeRawQuery("delete from Alerts");
//                    LinkedHashMap<String, List> dataMap = new LinkedHashMap<>();
//                    JSONArray resultArray = null;
//                    try {
//                        resultArray = new JSONArray(result);
////                        dataMap.put("Alerts", CommonUtils.toList(resultArray));
//                        List dataList = new ArrayList();
//                        dataList.add(CommonUtils.toList(resultArray));
//                        dataAccessHandler.insertData(DatabaseKeys.TABLE_ALERTS, CommonUtils.toList(resultArray), new ApplicationThread.OnComplete<String>() {
//                            @Override
//                            public void execute(boolean success, String result, String msg) {
//                                if (success) {
//                                    onComplete.execute(true, "", "");
//                                } else {
//                                    onComplete.execute(false, "", "");
//                                }
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        onComplete.execute(false, "", "");
//                    }
//                } else {
//                    onComplete.execute(false, "", "");
//                }
//            }
//        });
//    }
    /*public static  synchronized  void  updateFarmerdata(final LinkedHashMap<String, List> transactionsData, final DataAccessHandler dataAccessHandler, final String tableName,){
        final List dataList = transactionsData.get(tableName);
        List dataToInsert = new ArrayList();
        JSONObject ccData = null;
        Gson gson = new GsonBuilder().serializeNulls().create();

        boolean recordExisted = false;
        String whereCondition = null;

        if(dataList.size() > 0)
        {Log.d("Database :","========> Analysis =====> Update Data is not null");

        }

    }*/

    public static void sendTrackingData(final Context context, final ApplicationThread.OnComplete onComplete) {
        final DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        List<LocationTracker> gpsTrackingList = (List<LocationTracker>) dataAccessHandler.getLocationtracting(Queries.getInstance().getGpsTrackingRefresh(), 1);
        if (null != gpsTrackingList && !gpsTrackingList.isEmpty()) {
            Type listType = new TypeToken<List>() {
            }.getType();
            Gson gson = null;
            gson = new GsonBuilder().serializeNulls().create();
            String dat = gson.toJson(gpsTrackingList, listType);
            JSONObject transObj = new JSONObject();
            try {
                transObj.put(DatabaseKeys.TABLE_Location_TRACKING_DETAILS, new JSONArray(dat));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "@@@@ check.." + transObj.toString());
            CloudDataHandler.placeDataInCloud(transObj, Config.live_url + Config.locationTrackingURL, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                dataAccessHandler.executeRawQuery(String.format(Queries.getInstance().updateLocationStatus(), DatabaseKeys.TABLE_Location_TRACKING_DETAILS));
                                Log.v(LOG_TAG, "@@@ Transactions sync success for " + DatabaseKeys.TABLE_Location_TRACKING_DETAILS);
                                CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "@@@ Transactions sync success for " + DatabaseKeys.TABLE_Location_TRACKING_DETAILS);

                                onComplete.execute(true, null, "Sync is success");
                            } else {
                                onComplete.execute(false, null, "Sync is failed");
                                CommonUtils.appendLog(LOG_TAG, "updateTransactionData", "Sync is failed for Location Tracking Details");

                            }
                        }
                    }
            );

        }
    }
}
