package com.cis.easyfarm.cloudHelper;

import android.util.Log;

import com.cis.easyfarm.Objects.DataCountModel;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cis.easyfarm.common.CommonUtils.toList;

public class CloudDataHandler {

    private static final String LOG_TAG = CloudDataHandler.class.getName();


    public static synchronized void placeDataInCloud(final JSONObject values, final String url, final ApplicationThread.OnComplete<String> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "placeDataInCloud..", new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient.postDataToServerjson(url, values, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                try {
                                    onComplete.execute(success, result.toString(), msg);
                                    CommonUtils.appendLog(LOG_TAG, "placeDataInCloud", success + "");
                                    CommonUtils.appendLog(LOG_TAG, "placeDataInCloud", result);
                                    CommonUtils.appendLog(LOG_TAG, "placeDataInCloud", msg);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    onComplete.execute(success, result, msg);
                                    CommonUtils.appendLog(LOG_TAG, "placeDataInCloud", e.getMessage() + "");
                                }
                            } else
                                onComplete.execute(success, result, msg);
                        }
                    });
                } catch (Exception e) {
                    Log.v(LOG_TAG, "@Error while getting " + e.getMessage());
                    CommonUtils.appendLog(LOG_TAG, "placeDataInCloud", e.getMessage() + "");

                }
            }
        });

    }


    public static void getRecordStatus(final String url, final ApplicationThread.OnComplete<String> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getRecordStatus...", new Runnable() {
            @Override
            public void run() {
                HttpClient.get(url, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            try {
                                onComplete.execute(success, result, msg);

                            } catch (Exception e) {
                                e.printStackTrace();
                                onComplete.execute(false, result, msg);
                            }
                        } else

                            onComplete.execute(success, result, msg);
                    }
                });
            }
        });

    }

    public static void getGenericData(final String url, final ApplicationThread.OnComplete<String> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getGenericData...", new Runnable() {
            @Override
            public void run() {
                HttpClient.get(url, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            try {
                                onComplete.execute(success, result, msg);
                                CommonUtils.appendLog(LOG_TAG, "getGenericData", success + "");
                                CommonUtils.appendLog(LOG_TAG, "getGenericData", result);
                                CommonUtils.appendLog(LOG_TAG, "getGenericData", msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                                onComplete.execute(success, result, msg);
                                CommonUtils.appendLog(LOG_TAG, "getGenericData", e.getMessage());

                            }
                        } else {
                            onComplete.execute(success, result, msg);
                        }
                    }
                });
            }
        });

    }
    public static void getGenericData(final String url, final LinkedHashMap dataMap, final ApplicationThread.OnComplete<List<DataCountModel>> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getGenericData...", new Runnable() {
            @Override
            public void run() {
                HttpClient.post(url, dataMap, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {

                                JSONObject parentMasterDataObject = null;
                                Object value = null;
                                JSONArray jsonArray = new JSONArray();

                                try {
                                    parentMasterDataObject = new JSONObject(result);

                                    JSONObject parentData = parentMasterDataObject.getJSONObject("Result");

                                    CommonUtils.appendLog(LOG_TAG, "getGenericData", result);

                                    // JSONObject parentData = new JSONObject(result);
                                List<DataCountModel> dataCountModelList = new ArrayList<DataCountModel>();
                                Log.v(LOG_TAG, "@@@@ dataCountModelList " +dataCountModelList);
                                CommonUtils.appendLog(LOG_TAG, "getGenericData", dataCountModelList + "");
                                    Iterator keysToCopyIterator = parentData.keys();
                                List<String> keysList = new ArrayList<>();
                                while (keysToCopyIterator.hasNext()) {
                                    String key = (String) keysToCopyIterator.next();
                                    keysList.add(key);
                                    Log.v(LOG_TAG, "@@@@ keysList " +keysList);
                                    CommonUtils.appendLog(LOG_TAG, "getGenericData", keysList + "");

                                }
                                for (String tableName : keysList) {
                                    Log.v(LOG_TAG, "@@@@ tableName " +tableName);
                                    CommonUtils.appendLog(LOG_TAG, "getGenericData", tableName + "");

                                    Gson gson = new Gson();
                                    DataCountModel dataCountModel = gson.fromJson(parentData.getJSONObject(tableName).toString(), DataCountModel.class);
                                    //dataCountModel.setCount(2); // no to remove this before release
//                                    if (dataCountModel.getTableName().equalsIgnoreCase("Plot") || dataCountModel.getTableName().equalsIgnoreCase("GeoBoundaries")) {
//                                        if (dataCountModel.getCount() > 0) {
//                                            dataCountModelList.add(dataCountModel);
//                                        }
//                                    }
                                    if (dataCountModel.getCount() > 0) {
                                        dataCountModelList.add(dataCountModel);

                                    }

                                }
                                onComplete.execute(success, dataCountModelList, msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                                onComplete.execute(success, null, msg);
                                    CommonUtils.appendLog(LOG_TAG, "getGenericData", e.getMessage());

                                }
                        } else {
                            onComplete.execute(success, null, msg);
                        }
                    }
                });
            }
        });
    }

    public static void getMasterData(final String url, final LinkedHashMap dataMap, final ApplicationThread.OnComplete<HashMap<String, List>> onComplete) {
        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getMasterData...", new Runnable() {
            @Override
            public void run() {
                HttpClient.post(url, dataMap, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        Log.e("result==", result);
                        CommonUtils.appendLog(LOG_TAG, "getMasterData", result);
                        CommonUtils.appendLog(LOG_TAG, "getMasterData", success + "");
                        CommonUtils.appendLog(LOG_TAG, "getMasterData", msg);


                        if (success) {

                            JSONObject parentMasterDataObject = null;
                            Object value = null;
                            JSONArray jsonArray = new JSONArray();

                            try {
                                parentMasterDataObject = new JSONObject(result);

                                JSONObject resultArray = parentMasterDataObject.getJSONObject("Result");
                                JSONObject json = resultArray.getJSONObject("ClassType");
                                Log.e("resultArray===2", json + "");
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", json + "");

//                                List<HashMap<String, String>> keysList2 = new ArrayList<>();
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                // Add child node to HashMap key & value
//                                map.put("Count", "1");
//                                map.put("TableName", "ClassType");
//                                map.put("MethodName", "SyncClassTypeMaster");
//
//                                keysList2.add(map);
                                Iterator keysToCopyIterator = resultArray.keys();


                                List<String> keysList = new ArrayList<>();
                                while (keysToCopyIterator.hasNext()) {
                                    String key = (String) keysToCopyIterator.next();
                                    keysList.add(key);
                                    jsonArray.put(resultArray.get(key));

                                }
                                Log.e("jsonArray===151", jsonArray + "");
                                Log.e("jsonArray===152", jsonArray.getString(1) + "");
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", jsonArray + "");
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", jsonArray.getString(1) + "");


//                                ArrayList<String> carList = new ArrayList<String>();
//                                carList.add("toyota");
//                                carList.add("bmw");
//                                carList.add("honda");
                                Log.v(LOG_TAG, "@@@@ Tables Size " + keysList.size());
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", keysList.size() + "");

                                LinkedHashMap<String, List> masterDataMap = new LinkedHashMap<>();
                                for (String tableName : keysList) {

                                    masterDataMap.put("", toList(jsonArray));

                                }

//                                LinkedHashMap<String, List<String>> myMaps = new LinkedHashMap<String, List<String>>();
//                                for (DataObject item : myList) {
//                                    if (!myMaps.containsKey(item.getKey())) {
//                                        myMaps.put(item.getKey(), new ArrayList<String>());
//                                    }
//                                    myMaps.get(item.getKey()).add(item.getValue());
//                                }

                                Log.v(LOG_TAG, "@@@@ Tables Data " + masterDataMap.size());
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", masterDataMap.size() + "");

                                getmasterData(onComplete, masterDataMap);
                                // onComplete.execute(success, masterDataMap, msg);

                            } catch (Exception e) {
                                e.printStackTrace();
                                onComplete.execute(success, null, msg);
                                CommonUtils.appendLog(LOG_TAG, "getMasterData", e.getMessage());
                            }
                        } else
                            onComplete.execute(success, null, msg);
                    }
                });
            }
        });
    }

    private static void getmasterData(final ApplicationThread.OnComplete<HashMap<String, List>> onComplete, final LinkedHashMap<String, List> masterDataMap) {


        final JSONObject requestObject = new JSONObject();
        final Calendar calendar = Calendar.getInstance();
        ;

        try {

            requestObject.put("LastUpdatedDate", "");
            requestObject.put("Index", 0);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

        }
        placeDataInCloud(requestObject, Config.live_url + Config.syncClassTypeMaster, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                Log.v(LOG_TAG, "@@@@ response.." + result);
                CommonUtils.appendLog(LOG_TAG, "getmasterData", success + "");
                CommonUtils.appendLog(LOG_TAG, "getmasterData", result);
                CommonUtils.appendLog(LOG_TAG, "getmasterData", msg);


                if (success) {

                    JSONObject myJson = null;
                    try {
                        myJson = new JSONObject(result);
                        Log.e(LOG_TAG, "===============" + myJson);
                        JSONArray resultArray = myJson.getJSONArray("ListResult");

                        Log.e("data===2", toList(resultArray) + "");
                        CommonUtils.appendLog(LOG_TAG, "getmasterData", toList(resultArray) + "");

                        masterDataMap.put("ClassType", toList(resultArray));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                    }

                    final JSONObject requestObject2 = new JSONObject();
                    try {
                        requestObject2.put("Index", 0);
                        requestObject2.put("LastUpdatedDate", "");
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "" + e.getMessage());
                        CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                    }
                    placeDataInCloud(requestObject2, Config.live_url + Config.syncTypeCdDmtMaster, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            Log.v(LOG_TAG, "@@@@ response.2." + result);
                            CommonUtils.appendLog(LOG_TAG, "getmasterData", result);

                            JSONObject myJson2 = null;
                            if (success) {
                                try {
                                    myJson2 = new JSONObject(result);
                                    Log.e(LOG_TAG, "===============" + myJson2);
                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", myJson2 + "");

                                    JSONArray resultArray = myJson2.getJSONArray("ListResult");


                                    masterDataMap.put("TypeCdDmt", toList(resultArray));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                }
                                final JSONObject requestObject3 = new JSONObject();
                                try {
                                    requestObject3.put("Index", 0);
                                    requestObject3.put("LastUpdatedDate", "");
                                } catch (JSONException e) {
                                    Log.e(LOG_TAG, "" + e.getMessage());
                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                }
                                placeDataInCloud(requestObject3, Config.live_url + Config.syncLookUpMaster, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {
                                        Log.v(LOG_TAG, "@@@@ response.3." + result);
                                        CommonUtils.appendLog(LOG_TAG, "getmasterData", result);

                                        if (success) {
                                            JSONObject myJson3 = null;
                                            try {
                                                myJson3 = new JSONObject(result);
                                                Log.e(LOG_TAG, "===============" + myJson3);
                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", myJson3 + "");

                                                JSONArray resultArray = myJson3.getJSONArray("ListResult");
                                                masterDataMap.put("LookUp", toList(resultArray));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());
                                            }

                                            final JSONObject requestObject4 = new JSONObject();
                                            try {
                                                requestObject4.put("Index", 0);
                                                requestObject4.put("LastUpdatedDate", "");
                                            } catch (JSONException e) {
                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                            }
                                            placeDataInCloud(requestObject4, Config.live_url + Config.syncCountryMaster, new ApplicationThread.OnComplete<String>() {
                                                @Override
                                                public void execute(boolean success, String result, String msg) {
                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                    if (success) {
                                                        JSONObject myJson4 = null;
                                                        try {
                                                            myJson4 = new JSONObject(result);
                                                            Log.e(LOG_TAG, "===============" + myJson4);
                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", myJson4 + "");
                                                            JSONArray resultArray = myJson4.getJSONArray("ListResult");
                                                            masterDataMap.put("Country", toList(resultArray));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                        }
                                                        final JSONObject requestObject5 = new JSONObject();
                                                        try {
                                                            requestObject5.put("Index", 0);
                                                            requestObject5.put("LastUpdatedDate", "");
                                                        } catch (JSONException e) {
                                                            Log.e(LOG_TAG, "" + e.getMessage());
                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                        }
                                                        placeDataInCloud(requestObject5, Config.live_url + Config.syncStateMaster, new ApplicationThread.OnComplete<String>() {
                                                            @Override
                                                            public void execute(boolean success, String result, String msg) {
                                                                Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                if (success) {
                                                                    JSONObject myJson4 = null;
                                                                    try {
                                                                        myJson4 = new JSONObject(result);
                                                                        Log.e(LOG_TAG, "===============" + myJson4);
                                                                        CommonUtils.appendLog(LOG_TAG, "getmasterData", myJson4 + "");

                                                                        JSONArray resultArray = myJson4.getJSONArray("ListResult");
                                                                        masterDataMap.put("State", toList(resultArray));
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                        CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                    }
                                                                    final JSONObject requestObject6 = new JSONObject();
                                                                    try {
                                                                        requestObject6.put("Index", 0);
                                                                        requestObject6.put("LastUpdatedDate", "");
                                                                    } catch (JSONException e) {
                                                                        Log.e(LOG_TAG, "" + e.getMessage());
                                                                        CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                    }
                                                                    placeDataInCloud(requestObject6, Config.live_url + Config.syncDistrictMaster, new ApplicationThread.OnComplete<String>() {
                                                                        @Override
                                                                        public void execute(boolean success, String result, String msg) {
                                                                            Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                            if (success) {
                                                                                JSONObject myJson4 = null;
                                                                                try {
                                                                                    myJson4 = new JSONObject(result);
                                                                                    Log.e(LOG_TAG, "===============" + myJson4);
                                                                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", myJson4 + "");

                                                                                    JSONArray resultArray = myJson4.getJSONArray("ListResult");
                                                                                    masterDataMap.put("District", toList(resultArray));
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                }
                                                                                final JSONObject requestObject7 = new JSONObject();
                                                                                try {
                                                                                    requestObject7.put("Index", 0);
                                                                                    requestObject7.put("LastUpdatedDate", "");
                                                                                } catch (JSONException e) {
                                                                                    Log.e(LOG_TAG, "" + e.getMessage());
                                                                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                }
                                                                                placeDataInCloud(requestObject7, Config.live_url + Config.syncMandalMaster, new ApplicationThread.OnComplete<String>() {
                                                                                    @Override
                                                                                    public void execute(boolean success, String result, String msg) {
                                                                                        Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                        if (success) {
                                                                                            JSONObject myJson7 = null;
                                                                                            try {
                                                                                                myJson7 = new JSONObject(result);
                                                                                                //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                JSONArray resultArray = myJson7.getJSONArray("ListResult");
                                                                                                masterDataMap.put("Mandal", toList(resultArray));
                                                                                            } catch (JSONException e) {
                                                                                                e.printStackTrace();
                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                            }
                                                                                            final JSONObject requestObject8 = new JSONObject();
                                                                                            try {
                                                                                                requestObject8.put("Index", 0);
                                                                                                requestObject8.put("LastUpdatedDate", "");
                                                                                            } catch (JSONException e) {
                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                            }
                                                                                            final JSONObject finalMyJson = myJson7;
                                                                                            placeDataInCloud(requestObject8, Config.live_url + Config.syncVillageMaster, new ApplicationThread.OnComplete<String>() {
                                                                                                @Override
                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                    if (success) {
                                                                                                        JSONObject myJson8 = null;
                                                                                                        try {
                                                                                                            myJson8 = new JSONObject(result);

                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                            masterDataMap.put("Village", toList(resultArray));
                                                                                                        } catch (JSONException e) {
                                                                                                            e.printStackTrace();
                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                        }
                                                                                                    }
                                                                                                    final JSONObject requestObject9 = new JSONObject();
                                                                                                    try {
                                                                                                        requestObject9.put("Index", 0);
                                                                                                        requestObject9.put("LastUpdatedDate", "");
                                                                                                    } catch (JSONException e) {
                                                                                                        Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                        CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                    }
                                                                                                    placeDataInCloud(requestObject9, Config.live_url + Config.syncBankMaster, new ApplicationThread.OnComplete<String>() {
                                                                                                        @Override
                                                                                                        public void execute(boolean success, String result, String msg) {
                                                                                                            Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                            if (success) {
                                                                                                                JSONObject myJson8 = null;
                                                                                                                try {
                                                                                                                    myJson8 = new JSONObject(result);
                                                                                                                    //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                    JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                    masterDataMap.put("Bank", toList(resultArray));
                                                                                                                } catch (JSONException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                    CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                }
                                                                                                            }

                                                                                                            final JSONObject requestObject9 = new JSONObject();
                                                                                                            try {
                                                                                                                requestObject9.put("Index", 0);
                                                                                                                requestObject9.put("LastUpdatedDate", "");
                                                                                                            } catch (JSONException e) {
                                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                            }
                                                                                                            placeDataInCloud(requestObject9, Config.live_url + Config.SyncCountryMaster, new ApplicationThread.OnComplete<String>() {
                                                                                                                @Override
                                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                                    if (success) {
                                                                                                                        JSONObject myJson8 = null;
                                                                                                                        try {
                                                                                                                            myJson8 = new JSONObject(result);
                                                                                                                            //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                            masterDataMap.put("Country", toList(resultArray));
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                        }
                                                                                                                    }


//
//


                                                                                                                                        }
                                                                                                                                    });

                                                                                                            final JSONObject requestObject10 = new JSONObject();
                                                                                                            try {
                                                                                                                requestObject9.put("Index", 0);
                                                                                                                requestObject9.put("LastUpdatedDate", "");
                                                                                                            } catch (JSONException e) {
                                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                            }


                                                                                                            placeDataInCloud(requestObject10, Config.live_url + Config.SyncRoleMaster, new ApplicationThread.OnComplete<String>() {
                                                                                                                @Override
                                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                                    if (success) {
                                                                                                                        JSONObject myJson8 = null;
                                                                                                                        try {
                                                                                                                            myJson8 = new JSONObject(result);
                                                                                                                            //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                            masterDataMap.put("Role", toList(resultArray));
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                        }
                                                                                                                    }

                                                                                                                }
                                                                                                            });

                                                                                                            final JSONObject requestObject11 = new JSONObject();
                                                                                                            try {
                                                                                                                requestObject9.put("Index", 0);
                                                                                                                requestObject9.put("LastUpdatedDate", "");
                                                                                                            } catch (JSONException e) {
                                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                            }


                                                                                                            placeDataInCloud(requestObject11, Config.live_url + Config.SyncCropInfo, new ApplicationThread.OnComplete<String>() {
                                                                                                                @Override
                                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                                    if (success) {
                                                                                                                        JSONObject myJson8 = null;
                                                                                                                        try {
                                                                                                                            myJson8 = new JSONObject(result);
                                                                                                                            //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                            masterDataMap.put("CropInfo", toList(resultArray));
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                        }
                                                                                                                    }


                                                                                                                }
                                                                                                            });

                                                                                                            final JSONObject requestObject12 = new JSONObject();
                                                                                                            try {
                                                                                                                requestObject9.put("Index", 0);
                                                                                                                requestObject9.put("LastUpdatedDate", "");
                                                                                                            } catch (JSONException e) {
                                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                            }


                                                                                                            placeDataInCloud(requestObject12, Config.live_url + Config.SyncInsuranceProvider, new ApplicationThread.OnComplete<String>() {
                                                                                                                @Override
                                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                                    if (success) {
                                                                                                                        JSONObject myJson8 = null;
                                                                                                                        try {
                                                                                                                            myJson8 = new JSONObject(result);
                                                                                                                            //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                            masterDataMap.put("InsuranceProvider", toList(resultArray));
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                        }
                                                                                                                    }

                                                                                                                    //onComplete.execute(true, masterDataMap, "Master data");


                                                                                                                }
                                                                                                            });

                                                                                                            final JSONObject requestObject13 = new JSONObject();
                                                                                                            try {
                                                                                                                requestObject9.put("Index", 0);
                                                                                                                requestObject9.put("LastUpdatedDate", "");
                                                                                                            } catch (JSONException e) {
                                                                                                                Log.e(LOG_TAG, "" + e.getMessage());
                                                                                                                CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                            }


                                                                                                            placeDataInCloud(requestObject13, Config.live_url + Config.SyncSeed, new ApplicationThread.OnComplete<String>() {
                                                                                                                @Override
                                                                                                                public void execute(boolean success, String result, String msg) {
                                                                                                                    Log.v(LOG_TAG, "@@@@ response.2." + result);
                                                                                                                    if (success) {
                                                                                                                        JSONObject myJson8 = null;
                                                                                                                        try {
                                                                                                                            myJson8 = new JSONObject(result);
                                                                                                                            //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
                                                                                                                            JSONArray resultArray = myJson8.getJSONArray("ListResult");
                                                                                                                            masterDataMap.put("Seed", toList(resultArray));
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            CommonUtils.appendLog(LOG_TAG, "getmasterData", e.getMessage());

                                                                                                                        }
                                                                                                                    }

                                                                                                                    onComplete.execute(true, masterDataMap, "Master data");


                                                                                                                }
                                                                                                            });


                                                                                                                                }
                                                                                                                            });
                                                                                                                        }

                                                                                                                });


                                                                                                         //   onComplete.execute(true, masterDataMap, "Master data");

                                                                                                        }

                                                                                                }
                                                                                            });
                                                                                        }


                                                                                    }
                                                                                });

                                                                            }
                                                                        }
                                                                    });
                                                                }

                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }


                                });

                            }
//    public static void getTransactionData(final String url, final LinkedHashMap dataMap, final ApplicationThread.OnComplete<HashMap<String, List>> onComplete) {
//        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getMasterData...", new Runnable() {
//            @Override
//            public void run() {
//                HttpClient.post(url, dataMap, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        Log.e("result==", result);
//                        if (success) {
//
//                            JSONObject parentMasterDataObject = null;
//                            Object value = null;
//                            JSONArray jsonArray = new JSONArray();
//
//                            try {
//                                parentMasterDataObject = new JSONObject(result);
//
//                                JSONObject resultArray = parentMasterDataObject.getJSONObject("Result");
//
////
//                                Iterator keysToCopyIterator = resultArray.keys();
//
//
//                                List<String> keysList = new ArrayList<>();
//                                while (keysToCopyIterator.hasNext()) {
//                                    String key = (String) keysToCopyIterator.next();
//                                    keysList.add(key);
//                                    jsonArray.put(resultArray.get(key));
//
//                                }
//                                Log.e("jsonArray===151", jsonArray + "");
//                                Log.e("jsonArray===152", jsonArray.getString(1) + "");
//
////                                ArrayList<String> carList = new ArrayList<String>();
////                                carList.add("toyota");
////                                carList.add("bmw");
////                                carList.add("honda");
//                                Log.v(LOG_TAG, "@@@@ Tables Size " + keysList.size());
//                                LinkedHashMap<String, List> masterDataMap = new LinkedHashMap<>();
//                                for (String tableName : keysList) {
//
//                                    masterDataMap.put("", toList(jsonArray));
//
//                                }
//
////
//
//                                Log.v(LOG_TAG, "@@@@ Tables Data " + masterDataMap.size());
//                                gettransactionData(onComplete, masterDataMap);
//                                // onComplete.execute(success, masterDataMap, msg);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                onComplete.execute(success, null, msg);
//                            }
//                        } else
//                            onComplete.execute(success, null, msg);
//                    }
//                });
//            }
//        });
//    }

//    private static void gettransactionData(final ApplicationThread.OnComplete<HashMap<String, List>> onComplete, final LinkedHashMap<String, List> masterDataMap) {
//
//
//        final JSONObject requestObject = new JSONObject();
//        final Calendar calendar = Calendar.getInstance();
//
//        final JSONObject requestObject10 = new JSONObject();
//        try {
//            requestObject10.put("Index", 0);
//            requestObject10.put("Date", "");
//            requestObject10.put("UserId", 1);
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, "" + e.getMessage());
//        }
//        placeDataInCloud(requestObject10, Config.live_url + Config.SyncBankDetails, new ApplicationThread.OnComplete<String>() {
//            @Override
//            public void execute(boolean success, String result, String msg) {
//                Log.v(LOG_TAG, "@@@@ response.2." + result);
//                if (success) {
//                    JSONObject myJson8 = null;
//                    try {
//                        myJson8 = new JSONObject(result);
//                        //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
//                        JSONArray resultArray = myJson8.getJSONArray("ListResult");
//                        masterDataMap.put("BankDetails", toList(resultArray));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                final JSONObject requestObject11 = new JSONObject();
//                try {
//                    requestObject11.put("Index", 0);
//                    requestObject11.put("Date", "");
//                    requestObject11.put("UserId", 1);
//                } catch (JSONException e) {
//                    Log.e(LOG_TAG, "" + e.getMessage());
//                }
//                placeDataInCloud(requestObject11, Config.live_url + Config.SyncPlotDetails, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        Log.v(LOG_TAG, "@@@@ response.2." + result);
//                        if (success) {
//                            JSONObject myJson8 = null;
//                            try {
//                                myJson8 = new JSONObject(result);
//                                //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
//                                JSONArray resultArray = myJson8.getJSONArray("ListResult");
//                                masterDataMap.put("Plot", toList(resultArray));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        final JSONObject requestObject12 = new JSONObject();
//                        try {
//                            requestObject12.put("Index", 0);
//                            requestObject12.put("Date", "");
//                            requestObject12.put("UserId", 1);
//                        } catch (JSONException e) {
//                            Log.e(LOG_TAG, "" + e.getMessage());
//                        }
//                        placeDataInCloud(requestObject12, Config.live_url + Config.SyncIdentityProofs, new ApplicationThread.OnComplete<String>() {
//                            @Override
//                            public void execute(boolean success, String result, String msg) {
//                                Log.v(LOG_TAG, "@@@@ response.2." + result);
//                                if (success) {
//                                    JSONObject myJson8 = null;
//                                    try {
//                                        myJson8 = new JSONObject(result);
//                                        //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
//                                        JSONArray resultArray = myJson8.getJSONArray("ListResult");
//                                        masterDataMap.put("IdentityProofs", toList(resultArray));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                final JSONObject requestObject13 = new JSONObject();
//                                try {
//                                    requestObject13.put("Index", 0);
//                                    requestObject13.put("Date", "");
//                                    requestObject13.put("UserId", 1);
//                                } catch (JSONException e) {
//                                    Log.e(LOG_TAG, "" + e.getMessage());
//                                }
//                                placeDataInCloud(requestObject13, Config.live_url + Config.SyncPlotStatusHistory, new ApplicationThread.OnComplete<String>() {
//                                    @Override
//                                    public void execute(boolean success, String result, String msg) {
//                                        Log.v(LOG_TAG, "@@@@ response.2." + result);
//                                        if (success) {
//                                            JSONObject myJson8 = null;
//                                            try {
//                                                myJson8 = new JSONObject(result);
//                                                //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
//                                                JSONArray resultArray = myJson8.getJSONArray("ListResult");
//                                                masterDataMap.put("PlotStatusHistory", toList(resultArray));
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
////                                        final JSONObject requestObject = new JSONObject();
////                                        try {
////                                            requestObject.put("Index", 0);
////                                            requestObject.put("Date", "");
////                                            requestObject.put("UserId", 1);
////                                        } catch (JSONException e) {
////                                            Log.e(LOG_TAG, "" + e.getMessage());
////                                        }
////                                        placeDataInCloud(requestObject, Config.live_url + Config.SyncFileRepository, new ApplicationThread.OnComplete<String>() {
////                                            @Override
////                                            public void execute(boolean success, String result, String msg) {
////                                                Log.v(LOG_TAG, "@@@@ response.2." + result);
////                                                if (success) {
////                                                    JSONObject myJson8 = null;
////                                                    try {
////                                                        myJson8 = new JSONObject(result);
////                                                        //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
////                                                        JSONArray resultArray = myJson8.getJSONArray("ListResult");
////                                                        masterDataMap.put("FileRepository", toList(resultArray));
////                                                    } catch (JSONException e) {
////                                                        e.printStackTrace();
////                                                    }
////                                                }
////
////                                                final JSONObject requestObject = new JSONObject();
////                                                try {
////                                                    requestObject.put("Index", 0);
////                                                    requestObject.put("Date", "");
////                                                    requestObject.put("UserId", 1);
////                                                } catch (JSONException e) {
////                                                    Log.e(LOG_TAG, "" + e.getMessage());
////                                                }
////                                                placeDataInCloud(requestObject, Config.live_url + Config.SyncFarmerDetails, new ApplicationThread.OnComplete<String>() {
////                                                    @Override
////                                                    public void execute(boolean success, String result, String msg) {
////                                                        Log.v(LOG_TAG, "@@@@ response.2." + result);
////                                                        if (success) {
////                                                            JSONObject myJson8 = null;
////                                                            try {
////                                                                myJson8 = new JSONObject(result);
////                                                                //  Village  ,Bank                                                                            Log.e(LOG_TAG, "===============" + myJson7);
////                                                                JSONArray resultArray = myJson8.getJSONArray("ListResult");
////                                                                masterDataMap.put("UserInfo", toList(resultArray));
////                                                            } catch (JSONException e) {
////                                                                e.printStackTrace();
////                                                            }
////                                                        }
//                                        onComplete.execute(true, masterDataMap, "Master data");
//                                    }
//                                });
//
//                            }
//                            //  onComplete.execute(true, masterDataMap, "Master data");
//
//                        });
//
//
//                    }
//                });
//
//            }
//        });
//    }
//                    }
//                });
//            }
//        });
//
//
//    }

//    public static void getTransactionsData(final String url, final HashMap dataMap, final ApplicationThread.OnComplete<LinkedHashMap<String, List<MasterDataQuery>>> onComplete) {
//        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getMasterData...", new Runnable() {
//            @Override
//            public void run() {
//                HttpClient.post(url, dataMap, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                            try {
//
//                                JSONObject parentMasterDataObject = new JSONObject(result);
//
//                                Iterator keysToCopyIterator = parentMasterDataObject.keys();
//                                List<String> keysList = new ArrayList<String>();
//                                while (keysToCopyIterator.hasNext()) {
//                                    String key = (String) keysToCopyIterator.next();
//                                    keysList.add(key);
//                                }
//
//                                Log.v(LOG_TAG, "@@@@ Tables Size " + keysList.size());
//
//                                LinkedHashMap<String, List<MasterDataQuery>> masterDataMap = new LinkedHashMap<>();
//                                for (String arrName : keysList) {
//                                    JSONArray childJsonArr = parentMasterDataObject.getJSONArray(arrName);
//                                    Gson gson = new Gson();
//                                    Type type = new TypeToken<List<MasterDataQuery>>() {
//                                    }.getType();
//                                    List<MasterDataQuery> masterDataQueriesList = gson.fromJson(childJsonArr.toString(), type);
//
//                                    Log.v(LOG_TAG, "@@@@ Table name " + arrName + " data size " + masterDataQueriesList.size());
//                                    if (masterDataQueriesList.size() > 0) {
//                                        masterDataMap.put(arrName, masterDataQueriesList);
//                                    }
//                                }
//
//                                onComplete.execute(success, masterDataMap, msg);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                onComplete.execute(success, null, msg);
//                            }
//                        } else
//                            onComplete.execute(success, null, msg);
//                    }
//                });
//            }
//        });
//
//    }


    /**
     * This function upload the large file to server with other POST values.
     *
     * @param file
     * @param targetUrl
     * @return
     */
//    public static String uploadFileToServer(File file, String targetUrl, final ApplicationThread.OnComplete<String> onComplete) {
//        String response = "error";
//        HttpURLConnection connection = null;
//        DataOutputStream outputStream = null;
//
//        String urlServer = targetUrl;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024;
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            URL url = new URL(urlServer);
//            connection = (HttpURLConnection) url.openConnection();
//
//            // Allow Inputs & Outputs
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//            connection.setChunkedStreamingMode(1024);
//            // Enable POST method
//            connection.setRequestMethod("POST");
//
//            connection.setRequestProperty("Connection", "Keep-Alive");
//            connection.setRequestProperty("Content-Type",
//                    "multipart/form-data; boundary=" + boundary);
//
//            outputStream = new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//
//            String token = CommonConstants.USER_ID;
//            outputStream.writeBytes("Content-Disposition: form-data; name=\"userId\"" + lineEnd);
//            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
//            outputStream.writeBytes("Content-Length: " + token.length() + lineEnd);
//            outputStream.writeBytes(lineEnd);
//            outputStream.writeBytes(token + lineEnd);
//            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//
//            String taskId = CommonConstants.TAB_ID;
//            outputStream.writeBytes("Content-Disposition: form-data; name=\"tabId\"" + lineEnd);
//            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
//            outputStream.writeBytes("Content-Length: " + taskId.length() + lineEnd);
//            outputStream.writeBytes(lineEnd);
//            outputStream.writeBytes(taskId + lineEnd);
//            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//
//            String connstr = null;
//            connstr = "Content-Disposition: form-data; name=\"UploadDatabase\";filename=\""
//                    + file.getAbsolutePath() + "\"" + lineEnd;
//
//            outputStream.writeBytes(connstr);
//            outputStream.writeBytes(lineEnd);
//
//            bytesAvailable = fileInputStream.available();
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//
//            // Read file
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            System.out.println("Image length " + bytesAvailable + "");
//            try {
//                while (bytesRead > 0) {
//                    try {
//                        outputStream.write(buffer, 0, bufferSize);
//                    } catch (OutOfMemoryError e) {
//                        e.printStackTrace();
//                        response = "outofmemoryerror";
//                        onComplete.execute(false, response, response);
//                        return response;
//                    }
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                response = "error";
//                onComplete.execute(false, response, e.getMessage());
//                return response;
//            }
//            outputStream.writeBytes(lineEnd);
//            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
//                    + lineEnd);
//
//            // Responses from the server (code and message)
//            int serverResponseCode = connection.getResponseCode();
//            String serverResponseMessage = connection.getResponseMessage();
//            System.out.println("Server Response Code " + " " + serverResponseCode);
//            System.out.println("Server Response Message " + serverResponseMessage);
//
//            if (serverResponseCode == 200) {
//                response = "true";
//                onComplete.execute(true, response, response);
//            } else {
//                response = "false";
//                onComplete.execute(false, response, response);
//            }
//
//            fileInputStream.close();
//            outputStream.flush();
//
//            connection.getInputStream();
//            //for android InputStream is = connection.getInputStream();
//            java.io.InputStream is = connection.getInputStream();
//
//            int ch;
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1) {
//                b.append((char) ch);
//            }
//
//            String responseString = b.toString();
//            System.out.println("response string is" + responseString); //Here is the actual output
//
//            outputStream.close();
//            outputStream = null;
//
//        } catch (Exception ex) {
//            // Exception handling
//            response = "error";
//            System.out.println("Send file Exception" + ex.getMessage() + "");
//            onComplete.execute(false, response, "Send file Exception" + ex.getMessage() + "");
//            ex.printStackTrace();
//        }
//        return response;
//    }

//    public static void getTabTransData(final List<FileRepository> fileRepositoryList, final String url, final ApplicationThread.OnComplete<List> onComplete) {
//        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getTabTransData...", new Runnable() {
//            @Override
//            public void run() {
//                HttpClient.get(url, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        try {
//                            if (success) {
//                                List dataToInsert = new ArrayList();
//                                JSONArray parentTransactionSyncObject = new JSONArray(result);
//                                int length = parentTransactionSyncObject.length();
//                                for (int i = 0; i < parentTransactionSyncObject.length(); i++) {
//                                    JSONObject eachDataObject = parentTransactionSyncObject.getJSONObject(i);
//                                    dataToInsert.add(CommonUtils.toMap(eachDataObject));
//                                }
//
//                                if (null != fileRepositoryList) {
//                                    Gson gson = new Gson();
//                                    Type type = new TypeToken<List<FileRepository>>() {
//                                    }.getType();
//                                    List<FileRepository> fileRepositoryInnerList = gson.fromJson(result, type);
//                                    fileRepositoryList.addAll(fileRepositoryInnerList);
//                                }
//                                onComplete.execute(success, dataToInsert, msg);
//                            } else {
//                                onComplete.execute(success, null, msg);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            onComplete.execute(success, null, msg);
//                        }
//                    }
//                });
//            }
//        });
//
//    }
//
//
//    public static void getFarmerPhotosData(final String url, final ApplicationThread.OnComplete<List<FarmerPhotos>> onComplete) {
//        ApplicationThread.bgndPost(CloudDataHandler.class.getName(), "getFarmerPhotosData...", new Runnable() {
//            @Override
//            public void run() {
//                HttpClient.get(url, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                            try {
//                                Gson gson = new Gson();
//                                Type type = new TypeToken<List<FarmerPhotos>>() {
//                                }.getType();
//                                List<FarmerPhotos> farmerPhotosList = gson.fromJson(result, type);
//                                onComplete.execute(success, farmerPhotosList, msg);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                onComplete.execute(success, null, msg);
//                            }
//                        } else
//                            onComplete.execute(success, null, msg);
//                    }
//                });
//            }
//        });
//
//    }
}});}}
