package com.cis.easyfarm.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;


import com.cis.easyfarm.Objects.Alerts;
import com.cis.easyfarm.Objects.BankDetails;
import com.cis.easyfarm.Objects.BankDetailsHistory;
import com.cis.easyfarm.Objects.BuyerDetails;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.FileRepository;
import com.cis.easyfarm.Objects.FileRepositoryRefresh;
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
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.ImageUtility;
import com.cis.easyfarm.model.ComplaintRepository;
import com.cis.easyfarm.model.ComplaintStatusHistory;
import com.cis.easyfarm.model.ComplaintTypeXref;
import com.cis.easyfarm.model.Complaints;
import com.cis.easyfarm.model.GetStates;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataAccessHandler<T> {

    private static final String LOG_TAG = DataAccessHandler.class.getName();

    private Context context;
    private SQLiteDatabase mDatabase;
    String var="";
    String queryForLookupTable="select Name from LookUp where id="+var ;
    private int value;

    public DataAccessHandler() {

    }

    public DataAccessHandler(final Context context) {
        this.context = context;
        try {
           mDatabase = EasyFarmDatabse.openDataBaseNew();

            DataBaseUpgrade.upgradeDataBase(context, mDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "DataAccessHandler", e.getMessage());

        }


    }

    public DataAccessHandler(final Context context, boolean firstTime) {
        this.context = context;
        try {
            mDatabase = EasyFarmDatabse.openDataBaseNew();
        } catch (SQLException e) {
            e.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "DataAccessHandler", e.getMessage());

        }
    }
    public T getSelectedPlotData(final String query, final int type) {
        List<Plot> plotList = new ArrayList<>();
        Plot mPlot = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getSelectedPlotData", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlot = new Plot();
                    mPlot.setId(cursor.getInt(0));
                    mPlot.setCode(cursor.getString(1));
                    mPlot.setFarmerCode(cursor.getString(2));
                    mPlot.setTotalPlotArea((cursor.getDouble(3) == 0.0) ? null : cursor.getDouble(3));
                    mPlot.setAdaptedArea((cursor.getDouble(4) == 0.0) ? null : cursor.getDouble(4));
                    mPlot.setGPSPlotArea((cursor.getDouble(5) == 0.0) ? null : cursor.getDouble(5));
                    mPlot.setSurveyNumber(cursor.getString(6));
                    mPlot.setPassbookNumber(cursor.getString(7));
                    mPlot.setPlotOwnerShipTypeId((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mPlot.setPlotStatusId((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mPlot.setOwnerName(cursor.getString(10));
                    mPlot.setOwnerContactNumber(cursor.getString(11));
                    mPlot.setAddress1(cursor.getString(12));
                    mPlot.setAddress2(cursor.getString(13));
                    mPlot.setStateId((cursor.getInt(14)));
                    mPlot.setDistrictId((cursor.getInt(15)));
                    mPlot.setMandalId((cursor.getInt(16)));
                    mPlot.setVillageId((cursor.getInt(17)));
                    mPlot.setIsActive(cursor.getInt(18));
                    mPlot.setCreatedByUserId((cursor.getInt(19) == 0) ? null : cursor.getInt(19));
                    mPlot.setCreatedDate(cursor.getString(20));
                    mPlot.setUpdatedByUserId((cursor.getInt(21) == 0) ? null : cursor.getInt(21));
                    mPlot.setUpdatedDate(cursor.getString(22));
                    mPlot.setServerUpdatedStatus(cursor.getInt(23));

                    if (type == 1) {
                        plotList.add(mPlot);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSelectedPlotData", e.getMessage());

        }
        return (T) ((type == 0) ? mPlot : plotList);
    }


    public T getBuyerDetails(final String query, final int type) {
        List<BuyerDetails> buyerDetails = new ArrayList<>();
        BuyerDetails mBuyerDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getBuyerDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mBuyerDetails = new BuyerDetails();
                    mBuyerDetails.setId(cursor.getInt(0));
                    mBuyerDetails.setUserCode(cursor.getString(1));
                    mBuyerDetails.setBuyerTypeId(cursor.getInt(2));
                    mBuyerDetails.setGSTNumber(cursor.getString(3));
                    mBuyerDetails.setCompanyName(cursor.getString(4));
                    mBuyerDetails.setAddress1(cursor.getString(5));
                    mBuyerDetails.setAddress2(cursor.getString(6));
                    mBuyerDetails.setStateId(cursor.getInt(7));
                    mBuyerDetails.setDistrictId(cursor.getInt(8));
                    mBuyerDetails.setMandalId(cursor.getInt(9));
                    mBuyerDetails.setVillageId(cursor.getInt(10));
                    mBuyerDetails.setROCFileName(cursor.getString(11));
                    mBuyerDetails.setROCFileLocation(cursor.getString(12));
                    mBuyerDetails.setROCFileExtension(cursor.getString(13));
                    mBuyerDetails.setCINNumber(cursor.getString(14));
                    mBuyerDetails.setINCFileName(cursor.getString(15));
                    mBuyerDetails.setINCFileLocation(cursor.getString(16));
                    mBuyerDetails.setINCFileExtension(cursor.getString(17));
                    mBuyerDetails.setIsActive(true);
                    mBuyerDetails.setCreatedByUserId(cursor.getInt(19));
                    mBuyerDetails.setCreatedDate(cursor.getString(20));
                    mBuyerDetails.setUpdatedByUserId(cursor.getInt(21));
                    mBuyerDetails.setUpdatedDate(cursor.getString(22));
                    mBuyerDetails.setROCFileBytes(cursor.getString(23));
                    mBuyerDetails.setINCFileBytes(cursor.getString(24));
                    mBuyerDetails.setServerUpdatedStatus(true);

                    if (type == 1) {
                        buyerDetails.add(mBuyerDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getBuyerDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mBuyerDetails : buyerDetails);
    }

    public T getVendorDetails(final String query, final int type) {
        List<VendorDetails> vendorDetails = new ArrayList<>();
        VendorDetails mVendorDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getVendorDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mVendorDetails = new VendorDetails();
                    mVendorDetails.setId(cursor.getInt(0));
                    mVendorDetails.setUserCode(cursor.getString(1));
                    mVendorDetails.setVendorTypeId(cursor.getInt(2));
                    mVendorDetails.setVendorCategoryId(cursor.getInt(3));
                    mVendorDetails.setGSTNumber(cursor.getString(4));
                    mVendorDetails.setCompanyName(cursor.getString(5));
                    mVendorDetails.setAddress1(cursor.getString(6));
                    mVendorDetails.setAddress2(cursor.getString(7));
                    mVendorDetails.setStateId(cursor.getInt(8));
                    mVendorDetails.setDistrictId(cursor.getInt(9));
                    mVendorDetails.setMandalId(cursor.getInt(10));
                    mVendorDetails.setVillageId(cursor.getInt(11));
                    mVendorDetails.setROCFileName(cursor.getString(12));
                    mVendorDetails.setROCFileLocation(cursor.getString(13));
                    mVendorDetails.setROCFileExtension(cursor.getString(14));
                    mVendorDetails.setCINNumber(cursor.getString(15));
                    mVendorDetails.setINCFileName(cursor.getString(16));
                    mVendorDetails.setINCFileLocation(cursor.getString(17));
                    mVendorDetails.setINCFileExtension(cursor.getString(18));
                    mVendorDetails.setIsActive(true);
                    mVendorDetails.setCreatedByUserId(cursor.getInt(20));
                    mVendorDetails.setCreatedDate(cursor.getString(21));
                    mVendorDetails.setUpdatedByUserId(cursor.getInt(22));
                    mVendorDetails.setUpdatedDate(cursor.getString(23));
                    mVendorDetails.setROCFileBytes(cursor.getString(24));
                    mVendorDetails.setINCFileBytes(cursor.getString(25));
                    mVendorDetails.setServerUpdatedStatus(true);

                    if (type == 1) {
                        vendorDetails.add(mVendorDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getvendorDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mVendorDetails : vendorDetails);
    }


    public T getInsuranceDetails(final String query, final int type) {
        List<InsuranceDetailsObject> insuranceDetails = new ArrayList<>();
        InsuranceDetailsObject mInsuranceDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getInsuranceDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mInsuranceDetails = new InsuranceDetailsObject();
                    mInsuranceDetails.setId(cursor.getInt(0));
                    mInsuranceDetails.setCropCycleCode(cursor.getString(1));
                    mInsuranceDetails.setProviderId(cursor.getInt(2));
                    mInsuranceDetails.setInsurancePlan(cursor.getString(3));
                    mInsuranceDetails.setStartDate(cursor.getString(4));
                    mInsuranceDetails.setEndDate(cursor.getString(5));
                    mInsuranceDetails.setSumIssued(cursor.getDouble(6));
                    mInsuranceDetails.setPremiumAmount(cursor.getDouble(7));
                    mInsuranceDetails.setFarmerPencentage(cursor.getDouble(8));
                    mInsuranceDetails.setVFarmerPencentage(cursor.getDouble(9));
                    mInsuranceDetails.setBondNumber(cursor.getString(10));
                    mInsuranceDetails.setFileName(cursor.getString(11));
                    mInsuranceDetails.setFileLocation(cursor.getString(12));
                    mInsuranceDetails.setFileExtention(cursor.getString(13));
                    mInsuranceDetails.setStatusTypeId(cursor.getInt(14));
                    mInsuranceDetails.setComments(cursor.getString(15));
                    mInsuranceDetails.setStatusChangedBy(cursor.getInt(16));
                    mInsuranceDetails.setStatusChangedDate(cursor.getString(17));
                    mInsuranceDetails.setIsActive(true);
                    mInsuranceDetails.setCreatedByUserId(cursor.getInt(19));
                    mInsuranceDetails.setCreatedDate(cursor.getString(20));
                    mInsuranceDetails.setUpdatedByUserId(cursor.getInt(21));
                    mInsuranceDetails.setUpdatedDate(cursor.getString(22));
                    mInsuranceDetails.setFileBytes(cursor.getString(23));
                    mInsuranceDetails.setServerUpdatedStatus(true);

                    if (type == 1) {
                        insuranceDetails.add(mInsuranceDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getinsuranceDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mInsuranceDetails : insuranceDetails);
    }

//    public T getPlotCropCycle(final String query, final int type) {
//        List<PlotCropCycleObject> plotCropCycleObject = new ArrayList<>();
//        PlotCropCycleObject mPlotCropCycleObject = null;
//        Cursor cursor = null;
//        Log.v(LOG_TAG, "@@@ address details query " + query);
//        CommonUtils.appendLog(LOG_TAG, "getPlotCropCycleObject", query);
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    mPlotCropCycleObject = new PlotCropCycleObject();
//                    mPlotCropCycleObject.setId(cursor.getInt(0));
//                    mPlotCropCycleObject.setPlotCode(cursor.getString(1));
//                    mPlotCropCycleObject.setCycleCode(cursor.getString(2));
//                    mPlotCropCycleObject.setStatusTypeId(cursor.getInt(3));
//                    mPlotCropCycleObject.setCropId(cursor.getInt(4));
//                    mPlotCropCycleObject.setMinDuration(cursor.getInt(5));
//                    mPlotCropCycleObject.setMaxDuration(cursor.getInt(6));
//                    mPlotCropCycleObject.setAmount(cursor.getDouble(7));
//                    mPlotCropCycleObject.setFileName(cursor.getString(8));
//                    mPlotCropCycleObject.setFileLocation(cursor.getString(9));
//                    mPlotCropCycleObject.setFileExtention(cursor.getString(10));
//                    mPlotCropCycleObject.setIsActive(true);
//                    mPlotCropCycleObject.setCreatedByUserId(cursor.getInt(12));
//                    mPlotCropCycleObject.setCreatedDate(cursor.getString(13));
//                    mPlotCropCycleObject.setUpdatedByUserId(cursor.getInt(14));
//                    mPlotCropCycleObject.setUpdatedDate(cursor.getString(15));
//                    mPlotCropCycleObject.setServerUpdatedStatus(true);
//
//                    if (type == 1) {
//                        plotCropCycleObject.add(mPlotCropCycleObject);
//                    }
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
//            CommonUtils.appendLog(LOG_TAG, "getPlotCropCycle", e.getMessage());
//        }
//        return (T) ((type == 0) ? mPlotCropCycleObject : plotCropCycleObject);
//    }

    public T getPlotCropCycle(final String query, final int type) {
        List<PlotCropCycleObject> plotCropCycleObjects = new ArrayList<>();
        PlotCropCycleObject mPlotCropCycleObject = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getSelectedPlotData", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotCropCycleObject = new PlotCropCycleObject();
                    mPlotCropCycleObject.setId(cursor.getInt(0));
                    mPlotCropCycleObject.setPlotCode(cursor.getString(1));
                    mPlotCropCycleObject.setCycleCode(cursor.getString(2));
                    mPlotCropCycleObject.setStatusTypeId(cursor.getInt(3));
                    mPlotCropCycleObject.setCropId(cursor.getInt(4));
                    mPlotCropCycleObject.setMinDuration(cursor.getInt(5));
                    mPlotCropCycleObject.setMaxDuration(cursor.getInt(6));
                    mPlotCropCycleObject.setAmount(cursor.getDouble(7));
                    mPlotCropCycleObject.setFileName(cursor.getString(8));
                    mPlotCropCycleObject.setFileLocation(cursor.getString(9));
                    mPlotCropCycleObject.setFileExtention(cursor.getString(10));
                    mPlotCropCycleObject.setIsActive(true);
                    mPlotCropCycleObject.setCreatedByUserId(cursor.getInt(12));
                    mPlotCropCycleObject.setCreatedDate(cursor.getString(13));
                    mPlotCropCycleObject.setUpdatedByUserId(cursor.getInt(14));
                    mPlotCropCycleObject.setUpdatedDate(cursor.getString(15));
                    mPlotCropCycleObject.setServerUpdatedStatus(true);

                    if (type == 1) {
                        plotCropCycleObjects.add(mPlotCropCycleObject);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSelectedPlotData", e.getMessage());

        }
        return (T) ((type == 0) ? mPlotCropCycleObject : plotCropCycleObjects);
    }



    public T getSoilTestDetails(final String query, final int type) {
        List<SoilTestDetails> soilTestDetails = new ArrayList<>();
        SoilTestDetails mSoilTestDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getSoilTestDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mSoilTestDetails = new SoilTestDetails();
                    mSoilTestDetails.setId(cursor.getInt(0));
                    mSoilTestDetails.setPlotCode(cursor.getString(1));
                    mSoilTestDetails.setCultureTypeId(cursor.getInt(2));
                    mSoilTestDetails.setNitrogen(cursor.getDouble(3));
                    mSoilTestDetails.setProsperous(cursor.getDouble(4));
                    mSoilTestDetails.setPotassium(cursor.getDouble(5));
                    mSoilTestDetails.setCarbon(cursor.getDouble(6));
                    mSoilTestDetails.setHydrogen(cursor.getDouble(7));
                    mSoilTestDetails.setOxygen(cursor.getDouble(8));
                    mSoilTestDetails.setSulphur(cursor.getDouble(9));
                    mSoilTestDetails.setCalcium(cursor.getDouble(10));
                    mSoilTestDetails.setMagnesium(cursor.getDouble(11));
                    mSoilTestDetails.setFileName(cursor.getString(12));
                    mSoilTestDetails.setFileLocation(cursor.getString(13));
                    mSoilTestDetails.setFileExtension(cursor.getString(14));
                    mSoilTestDetails.setIsActive(true);
                    mSoilTestDetails.setCreatedByUserId(cursor.getInt(16));
                    mSoilTestDetails.setCreatedDate(cursor.getString(17));
                    mSoilTestDetails.setUpdatedByUserId(cursor.getInt(18));
                    mSoilTestDetails.setUpdatedDate(cursor.getString(19));
                    mSoilTestDetails.setFileBytes(cursor.getString(20));
                    mSoilTestDetails.setServerUpdatedStatus(true);

                    if (type == 1) {
                        soilTestDetails.add(mSoilTestDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSoilTestDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mSoilTestDetails : soilTestDetails);
    }


    public T getActivityLogs(final String query, final int type) {
        List<SyncActivityLog> syncActivityLog = new ArrayList<>();
        SyncActivityLog mSyncActivityLog = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getActivityLogs", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mSyncActivityLog = new SyncActivityLog();
                    mSyncActivityLog.setId(cursor.getInt(0));
                    mSyncActivityLog.setFarmerCode(cursor.getString(1));
                    mSyncActivityLog.setPlotCode(cursor.getString(2));
                    mSyncActivityLog.setActivityTypeId(cursor.getInt(3));
                    mSyncActivityLog.setCreatedByUserId(cursor.getInt(4));
                    mSyncActivityLog.setCreatedDate(cursor.getString(5));
                    mSyncActivityLog.setServerUpdatedStatus(cursor.getInt(6));

                    if (type == 1) {
                        syncActivityLog.add(mSyncActivityLog);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getActivityLogs", e.getMessage());
        }
        return (T) ((type == 0) ? mSyncActivityLog : syncActivityLog);
    }



    public T getSoilDetails(final String query, final int type) {
        List<SoilDetails> soilDetails = new ArrayList<>();
        SoilDetails mSoilDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getSoilDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mSoilDetails = new SoilDetails();
                    mSoilDetails.setId(cursor.getInt(0));
                    mSoilDetails.setPlotCode(cursor.getString(1));
                    mSoilDetails.setSoilTypeId(cursor.getInt(2));
                    mSoilDetails.setIsActive(cursor.getInt(3));
                    mSoilDetails.setCreatedByUserId(cursor.getInt(4));
                    mSoilDetails.setCreatedDate(cursor.getString(5));
                    mSoilDetails.setUpdatedByUserId(cursor.getInt(6));
                    mSoilDetails.setUpdatedDate(cursor.getString(7));
                    mSoilDetails.setServerUpdatedStatus(cursor.getInt(8));

                    if (type == 1) {
                        soilDetails.add(mSoilDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSoilDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mSoilDetails : soilDetails);
    }


    public T getPowerDetails(final String query, final int type) {
        List<PowerDetails> powerDetails = new ArrayList<>();
        PowerDetails mPowerDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getPowerDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPowerDetails = new PowerDetails();
                    mPowerDetails.setId(cursor.getInt(0));
                    mPowerDetails.setPlotCode(cursor.getString(1));
                    mPowerDetails.setIsAvailable(cursor.getInt(2));
                    mPowerDetails.setServiceNumber(cursor.getString(3));
                    mPowerDetails.setIsActive(cursor.getInt(4));
                    mPowerDetails.setCreatedByUserId(cursor.getInt(5));
                    mPowerDetails.setCreatedDate(cursor.getString(6));
                    mPowerDetails.setUpdatedByUserId(cursor.getInt(7));
                    mPowerDetails.setUpdatedDate(cursor.getString(8));
                    mPowerDetails.setServerUpdatedStatus(cursor.getInt(9));

                    if (type == 1) {
                        powerDetails.add(mPowerDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getPowerDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mPowerDetails : powerDetails);
    }


    public T getIrrigationDetails(final String query, final int type) {
        List<IrrigationDetails> irrigationDetails = new ArrayList<>();
        IrrigationDetails mirrigationDetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getIrrigationDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mirrigationDetails = new IrrigationDetails();
                    mirrigationDetails.setId(cursor.getInt(0));
                    mirrigationDetails.setPlotCode(cursor.getString(1));
                    mirrigationDetails.setIrrigationTypeId(cursor.getInt(2));
                    mirrigationDetails.setIsActive(cursor.getInt(3));
                    mirrigationDetails.setCreatedByUserId(cursor.getInt(4));
                    mirrigationDetails.setCreatedDate(cursor.getString(5));
                    mirrigationDetails.setUpdatedByUserId(cursor.getInt(6));
                    mirrigationDetails.setUpdatedDate(cursor.getString(7));
                    mirrigationDetails.setServerUpdatedStatus(cursor.getInt(8));

                    if (type == 1) {
                        irrigationDetails.add(mirrigationDetails);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getIrrigationDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mirrigationDetails : irrigationDetails);
    }

    public T getUserRoleXrefDetails(final String query, final int type) {
        List<UserRoleXref> userRoleXref = new ArrayList<>();
        UserRoleXref muserRoleXref = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getUserRoleXrefDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    muserRoleXref = new UserRoleXref();
                    muserRoleXref.setId(cursor.getInt(0));
                    muserRoleXref.setUserId(cursor.getInt(1));
                    muserRoleXref.setRoleId(cursor.getInt(2));
                    muserRoleXref.setStatusTypeId(cursor.getInt(3));
                    muserRoleXref.setServerUpdatedStatus(true);
                    muserRoleXref.setUpdatedDate(cursor.getString(5));


                    if (type == 1) {
                        userRoleXref.add(muserRoleXref);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getUserRoleXrefDetails", e.getMessage());
        }
        return (T) ((type == 0) ? muserRoleXref : userRoleXref);
    }


    public T getfarmerDetails(final String query, final int type) {
        List<Farmer> farmerdetailsList = new ArrayList<>();
        Farmer mFarmer = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getfarmerDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmer = new Farmer();
                    mFarmer.setId(cursor.getInt(0));
                    mFarmer.setCode(cursor.getString(1));
                    mFarmer.setUserId(cursor.getString(2));
                    mFarmer.setFirstname(cursor.getString(3));
                    mFarmer.setMiddlename(cursor.getString(4));
                    mFarmer.setLastname(cursor.getString(5));
                    mFarmer.setFatherName_GuardianName(cursor.getString(6));
                    mFarmer.setAddress1((cursor.getString(7)));
                    mFarmer.setAddress2(cursor.getString(8));
                    mFarmer.setStateid((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mFarmer.setDistictid((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mFarmer.setMandalid((cursor.getInt(11) == 0) ? null : cursor.getInt(11));
                    mFarmer.setVillageid((cursor.getInt(12) == 0) ? null : cursor.getInt(12));
                    mFarmer.setGendertypeid((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    mFarmer.setDOB((cursor.getString(14)));
                    mFarmer.setPrimaryPhoneNumber(cursor.getString(15));
                    mFarmer.setSecondaryPhoneNumber(cursor.getString(16));

                    Integer annualincometypeid = cursor.getInt(17);
                    if(annualincometypeid==0 || annualincometypeid == null){

                        mFarmer.setAnnualincometypeid(-1);
                    }else{

                        mFarmer.setAnnualincometypeid(cursor.getInt(17));

                    }

                    Integer categotytypeid = cursor.getInt(18);
                    if(categotytypeid==0 || categotytypeid == null){

                        mFarmer.setCategorytypeid(-1);
                    }else{

                        mFarmer.setCategorytypeid(cursor.getInt(18));

                    }



                    mFarmer.setEmail(cursor.getString(19));

                    Integer educationypeid = cursor.getInt(20);
                    if(educationypeid==0 || educationypeid == null){

                        mFarmer.setEducationtypeid(-1);
                    }else{

                        mFarmer.setEducationtypeid(cursor.getInt(20));

                    }

                    Integer educationDeegreypeid = cursor.getInt(21);
                    if(educationDeegreypeid==0 || educationDeegreypeid == null){

                        mFarmer.setEducationDegreeTypeId(-1);
                    }else{

                        mFarmer.setEducationDegreeTypeId(cursor.getInt(21));

                    }

                    Integer sourceTypeid = cursor.getInt(22);
                    if(sourceTypeid==0 || sourceTypeid == null){

                        mFarmer.setSourceTypeId(-1);
                    }else{

                        mFarmer.setSourceTypeId(cursor.getInt(22));

                    }


                    mFarmer.setPPFileName(cursor.getString(23));
                    mFarmer.setPPFileLocation(cursor.getString(24));
                    mFarmer.setPPFileExtension(cursor.getString(25));
                    mFarmer.setMAFileName(cursor.getString(26));
                    mFarmer.setMAFileLocation(cursor.getString(27));
                    mFarmer.setMAFileExtension(cursor.getString(28));
                    mFarmer.setIsactive(1);
                    mFarmer.setCreatedbyuserid((cursor.getInt(30) == 0) ? null : cursor.getInt(30));
                    mFarmer.setCreateddate((cursor.getString(31)));
                    mFarmer.setUpdatedbyuserid((cursor.getInt(32) == 0) ? null : cursor.getInt(32));
                    mFarmer.setUpdatedDate(cursor.getString(33));
                    mFarmer.setUserName(cursor.getString(34));
                    mFarmer.setPassword(cursor.getString(35));

                    Integer employeeTypeid = cursor.getInt(36);
                    if(employeeTypeid==0 || employeeTypeid == null){

                        mFarmer.setEmployeeTypeId(-1);
                    }else{

                        mFarmer.setEmployeeTypeId(cursor.getInt(36));

                    }

                    Integer reportingManagerTypeid = cursor.getInt(37);
                    if(reportingManagerTypeid==0 || reportingManagerTypeid == null){

                        mFarmer.setReportingManagerId(-1);
                    }else{

                        mFarmer.setReportingManagerId(cursor.getInt(37));

                    }

                    mFarmer.setExpInYears((cursor.getInt(38)));
                    mFarmer.setExpInMonths(cursor.getInt(39));
                    mFarmer.setAccessToken(cursor.getString(40));
                    mFarmer.setServerupdatedstatus(cursor.getInt(41));
                    mFarmer.setProfilePicFileBytes(cursor.getString(42));
                    mFarmer.setMutualAgreementFileBytes(cursor.getString(43));

                    mFarmer.setNRI(cursor.getInt(44));
                    mFarmer.setCountryName(cursor.getString(45));
                    mFarmer.setPostalCode(cursor.getString(46));

                    mFarmer.setIsWillingtoConvert(1);

                    if (type == 1) {
                        farmerdetailsList.add(mFarmer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getfarmerDetails", e.getMessage());

        }
        return (T) ((type == 0) ? mFarmer : farmerdetailsList);
    }

        public LinkedHashMap<String, String> getGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);

            CommonUtils.appendLog(LOG_TAG, "getGenericData", query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSelectedPlotData", e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }



    public LinkedHashMap<String, String> getGenericDataa(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGenericDataa", query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGenericDataa", e.getMessage());

        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }
    public LinkedHashMap<String, String> getMoreGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);

        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1) + "-" + genericDataQuery.getString(2) + "-" + genericDataQuery.getString(3));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String getTwoValues(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = CommonUtils.twoDForm.format(genericDataQuery.getDouble(0)) + "-" + CommonUtils.twoDForm.format(genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String getplotAgeandSateId(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = (genericDataQuery.getInt(0)) + "-" + (genericDataQuery.getInt(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String  getYPHvaluefromBenchMark(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    public LinkedHashMap<String, Pair> getPairData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        CommonUtils.appendLog(LOG_TAG, "getPairData", query);

        LinkedHashMap<String, Pair> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), Pair.create(genericDataQuery.getString(1), genericDataQuery.getString(2)));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getPairData", e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }



    public boolean checkValueExistedInDatabase(final String query) {
        boolean isexist = false;
        Cursor mOprQuery = mDatabase.rawQuery(query, null);
        try {
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                if(mOprQuery.getInt(0) > 0){
                    Log.d("@@ mahesh", " Record Exist Count :"+mOprQuery.getInt(0));
                    isexist = true;
                }

            } else {
                isexist = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "getPairData", e.getMessage());

        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return isexist;
    }

    /*    public String  getAreaUnderPalm(String query) {
            Log.v(LOG_TAG, "@@@ query " + query);
            Cursor mOprQuery = null;
            try {
                mOprQuery = mDatabase.rawQuery(query, null);
                if (mOprQuery != null && mOprQuery.moveToFirst()) {
                    return mOprQuery.getString(5);
                }

                return null;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != mOprQuery)
                    mOprQuery.close();

                closeDataBase();
            }
            return "";
        }*/
    public String  getOnlyOneValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        CommonUtils.appendLog(LOG_TAG, "getOnlyOneValueFromDb", query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.appendLog(LOG_TAG, "getOnlyOneValueFromDb", e.getMessage());
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

//    public String getGeneratedFarmerCode(final String query) {
//        String maxNum = getOnlyOneValueFromDb(query);
//        String convertedNum = "";
//        if (!TextUtils.isEmpty(maxNum)) {
//            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 4);
//        } else {
//            convertedNum = CommonUtils.serialNumber(1, 4);
//        }
//        StringBuffer farmerCoder = new StringBuffer();
//
//        farmerCoder.append(CommonConstants.stateCode)
//                .append(CommonConstants.districtCode)
//                .append(CommonConstants.mandalCode)
//                .append(CommonConstants.villageCode)
//                .append(CommonConstants.TAB_ID)
//                .append(convertedNum);
//        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
//        return farmerCoder.toString();
//    }
//
//    public String getGeneratedMarketSurveyCode(final String query) {
//        String maxNum = getOnlyOneValueFromDb(query);
////        String convertedNum = "";
////        String maxNum = getOnlyOneValueFromDb(ccQuery);
//        int convertedNum = 0;
//        if (!TextUtils.isEmpty(maxNum)) {
////            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 6);
//            convertedNum = Integer.parseInt(maxNum) + 1;
//        } else {
//            convertedNum = 1;
//        }
//        StringBuffer farmerCoder = new StringBuffer();
//
//        farmerCoder.append(CommonConstants.MARKET_SURVEY_CODE_PREFIX).append(CommonConstants.FARMER_CODE).append("-").append(String.valueOf(convertedNum));
//
//        Log.v(LOG_TAG, "@@@ MarketSurvey code " + farmerCoder.toString());
//        return farmerCoder.toString();
//    }
//
//    public String getGeneratedPlotId(final String query, final String mandalID) {
//        String maxNum = getOnlyOneValueFromDb(query);
//        String convertedNum = "";
//        if (!TextUtils.isEmpty(maxNum)) {
//            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 6);
//        } else {
//            convertedNum = CommonUtils.serialNumber(1, 6);
//        }
//        StringBuffer farmerCoder = new StringBuffer();
//        farmerCoder.append(CommonConstants.stateCodePlot).append(mandalID).
//                append(CommonConstants.TAB_ID).
//                append(convertedNum.isEmpty()==false?convertedNum:CommonUtils.serialNumber(1, 6));
//        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
//        return farmerCoder.toString();
//    }


    public void updateMasterSyncDate(final boolean isNotFirstTime, String userId) {
        final List<LinkedHashMap> listMap = new ArrayList<>();
        final LinkedHashMap dataMap = new LinkedHashMap();
        dataMap.put(DatabaseKeys.COLUMN_USERID, (null == userId) ? "1" : userId);
        final String finalUserId = userId;
        dataMap.put(DatabaseKeys.COLUMN_UPDATEDON, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY));
        listMap.add(dataMap);
        ApplicationThread.dbPost("MasterVersionTrackingSystem Saving..", "insert", new Runnable() {
            @Override
            public void run() {
                if (isNotFirstTime) {
                    insertData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void run() {
                            if (success) {
                             //   PrefUtil.putBool(context, CommonConstants.IS_MASTER_SYNC_SUCCESS, true);
                                Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem inserted ");
                            }
                        }
                    });
                } else {
                    String whereCondition = "  where " + DatabaseKeys.COLUMN_USERID + "='" + finalUserId + "'";
                    updateData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, false, whereCondition, null);
                    Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem updated ");
                }
            }
        });
    }

    /**
     * Inserting data into database
     *
     * @param tableName ---> Table name to insert data
     * @param mapList   ---> map list which contains data
     */
    public synchronized void insertDataOld(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());
        int checkCount = 0;
        boolean errorMessageSent = false;
        try {
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());
                String query = "insert into " + tableName;
                String namestring, valuestring;
                StringBuffer values = new StringBuffer();
                StringBuffer columns = new StringBuffer();
                for (LinkedHashMap.Entry temp : entryList) {
                    if (temp.getKey().equals("Id"))
                        continue;
                    columns.append(temp.getKey());
                    columns.append(",");
                    values.append("'");
                    values.append(temp.getValue());
                    values.append("'");
                    values.append(",");
                }
                namestring = "(" + columns.deleteCharAt(columns.length() - 1).toString() + ")";
                valuestring = "(" + values.deleteCharAt(values.length() - 1).toString() + ")";
                query = query + namestring + "values" + valuestring;
                Log.v(getClass().getSimpleName(), "query.." + query);
                CommonUtils.appendLog(LOG_TAG, "insertDataOld", query);
                Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + mapList.size());
                try {
                    mDatabase.execSQL(query);
                } catch (Exception e) {
                    Log.v(LOG_TAG, "@@@ Error while inserting data " + e.getMessage());
                    CommonUtils.appendLog(LOG_TAG, "insertDataOld", e.getMessage());
                    if (checkCount == mapList.size()) {
                        errorMessageSent = true;
                        if (null != oncomplete)
                            oncomplete.execute(false, "failed to insert data", "");
                    }
                }
                if (checkCount == mapList.size() && !errorMessageSent) {
                    if (null != oncomplete)
                        oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    public synchronized void insertData(boolean fromMaster, String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        //Log.e("mapList====",mapList+"");
        CommonUtils.appendLog(LOG_TAG, "insertData", mapList + "");
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());
               // Log.e("   entryList====",entryList+"");
                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                   // Log.e("keyToInsert====",keyToInsert+"");
//                    if (!fromMaster) {
//                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS))
//                            continue;
//                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "1");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1.size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                CommonUtils.appendLog(LOG_TAG, "insertData", "Error while inserting data");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                    CommonUtils.appendLog(LOG_TAG, "insertData", "Failed to insert data");

                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                CommonUtils.appendLog(LOG_TAG, "insertData", "data inserted successfully for table :" + tableName);

                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                    CommonUtils.appendLog(LOG_TAG, "insertData", "data inserted successfully");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());

            CommonUtils.appendLog(LOG_TAG, "insertData", e.getMessage());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    public synchronized void insertData(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        insertData(false, tableName, mapList, oncomplete);
    }

    public synchronized void insertMyData(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {

        //Log.e("mapList====",mapList+"");
        CommonUtils.appendLog(LOG_TAG, "insertMyData", mapList+"");
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());
              //  Log.e("   entryList====",entryList+"");
                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                    Log.e("keyToInsert====",keyToInsert+"");
//                    if (!fromMaster) {
//                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS))
//                            continue;
//                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "0");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1.size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                CommonUtils.appendLog(LOG_TAG, "insertMyData", "Error while inserting data");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                    CommonUtils.appendLog(LOG_TAG, "insertMyData", "Failed to insert data");
                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                CommonUtils.appendLog(LOG_TAG, "insertMyData", "data inserted successfully for table :" + tableName);

                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                    CommonUtils.appendLog(LOG_TAG, "insertMyData", "data inserted successfully");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }

    }

    /**
     * Updating database records
     *
     * @param tableName      ---> Table name to update
     * @param list           ---> List which contains data values
     * @param isClaues       ---> Checking where condition availability
     * @param whereCondition ---> condition
     */
    public synchronized void updateData(String tableName, List<LinkedHashMap> list, Boolean isClaues, String whereCondition, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                checkCount++;
                List<Map.Entry> entryList = new ArrayList<Map.Entry>((list.get(i)).entrySet());
                String query = "update " + tableName + " set ";
                String namestring = "";

               // System.out.println("\n==> Size of Entry list: " + entryList.size());
                StringBuffer columns = new StringBuffer();
                for (Map.Entry temp : entryList) {
                    columns.append(temp.getKey());
                    columns.append("='");
                    columns.append(temp.getValue());
                    columns.append("',");
                }

                namestring = columns.deleteCharAt(columns.length() - 1).toString();
                query = query + namestring + "" + whereCondition;
                Log.d("=====Query======", query);
                CommonUtils.appendLog(LOG_TAG, "updateData", query);
                mDatabase.execSQL(query);
                isUpdateSuccess = true;
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (checkCount == list.size()) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for " + tableName);
                    CommonUtils.appendLog(LOG_TAG, "updateData", "data updated successfully for " + tableName);
                    oncomplete.execute(true, null, "data updated successfully for " + tableName);
                } else {
                    oncomplete.execute(false, null, "data updation failed for " + tableName);
                    CommonUtils.appendLog(LOG_TAG, "updateData", "data updation failed for " + tableName);

                }
            }
        }
    }

    public synchronized void updateserverupdatesStatusforUserInfo( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update UserInfo set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserInfo", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for userInfo" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserInfo", "data updated successfully for userInfo");
                    oncomplete.execute(true, null, "data updated successfully for userInfo" );
                } else {
                    oncomplete.execute(false, null, "data updation failed for userInfo"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserInfo", "data updation failed for userInfo");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforIdentityProofs( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update IdentityProofs set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIdentityProofs", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for IdentityProofs" );
                    oncomplete.execute(true, null, "data updated successfully for IdentityProofs" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIdentityProofs", "data updated successfully for IdentityProofs");

                } else {
                    oncomplete.execute(false, null, "data updation failed for IdentityProofs"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIdentityProofs", "data updation failed for IdentityProofs");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforBankDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update BankDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBankDetails", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for BankDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBankDetails", "data updated successfully for BankDetails");
                    oncomplete.execute(true, null, "data updated successfully for BankDetails" );
                } else {
                    oncomplete.execute(false, null, "data updation failed for BankDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBankDetails", "data updation failed for BankDetails");
                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforPlot( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update Plot set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlot", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for Plot" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlot", "data updated successfully for Plot");
                    oncomplete.execute(true, null, "data updated successfully for Plot" );
                } else {
                    oncomplete.execute(false, null, "data updation failed for Plot"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlot", "data updation failed for Plot");
                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforGeoBoundaries( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update GeoBoundaries set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforGeoBoundaries", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for GeoBoundaries" );
                    oncomplete.execute(true, null, "data updated successfully for GeoBoundaries" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforGeoBoundaries", "data updated successfully for GeoBoundaries");

                } else {
                    oncomplete.execute(false, null, "data updation failed for GeoBoundaries"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforGeoBoundaries", "data updation failed for GeoBoundaries");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforSoilDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update SoilDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for SoilDetails" );
                    oncomplete.execute(true, null, "data updated successfully for SoilDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilDetails", "data updated successfully for SoilDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for SoilDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilDetails", "data updation failed for SoilDetails");


                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforPowerDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update PowerDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPowerDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for PowerDetails" );
                    oncomplete.execute(true, null, "data updated successfully for PowerDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPowerDetails", "data updated successfully for PowerDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for PowerDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPowerDetails", "data updation failed for PowerDetails");

                }                }

        }
    }
    public synchronized void updateserverupdatesStatusforIrrigationDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update IrrigationDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIrrigationDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for IrrigationDetails" );
                    oncomplete.execute(true, null, "data updated successfully for IrrigationDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIrrigationDetails", "data updated successfully for IrrigationDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for IrrigationDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforIrrigationDetails", "data updation failed for IrrigationDetails");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforNotificationDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update Notification set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforNotificationDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for NotificationDetails" );
                    oncomplete.execute(true, null, "data updated successfully for NotificationDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforNotificationDetails", "data updated successfully for NotificationDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for NotificationDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforNotificationDetails", "data updation failed for NotificationDetails");

                }                }

        }
    }


    public synchronized void updateserverupdatesStatusforBuyerDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update BuyerDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBuyerDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for BuyerDetails" );
                    oncomplete.execute(true, null, "data updated successfully for BuyerDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBuyerDetails", "data updated successfully for BuyerDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for BuyerDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforBuyerDetails", "data updation failed for BuyerDetails");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforVendorDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update VendorDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforVendorDetails", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for VendorDetails" );
                    oncomplete.execute(true, null, "data updated successfully for VendorDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforVendorDetails", "data updated successfully for VendorDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for VendorDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforVendorDetails", "data updation failed for VendorDetails");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforActivityLog( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update ActivityLog set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforActivityLog", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for ActivityLog" );
                    oncomplete.execute(true, null, "data updated successfully for ActivityLog" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforActivityLog", "data updated successfully for ActivityLog");


                } else {
                    oncomplete.execute(false, null, "data updation failed for ActivityLog"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforActivityLog", "data updation failed for ActivityLog");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforUserRoles( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update UserRoleXref set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query=====", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoles", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for UserRoles" );
                    oncomplete.execute(true, null, "data updated successfully for UserRoles" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoles", "data updated successfully for UserRoles");

                } else {
                    oncomplete.execute(false, null, "data updation failed for UserRoles"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoles", "data updation failed for UserRoles");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforSoilTestDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update SoilTestDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilTestDetails", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for SoilTestDetails" );
                    oncomplete.execute(true, null, "data updated successfully for SoilTestDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilTestDetails", "data updated successfully for SoilTestDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for SoilTestDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforSoilTestDetails", "data updation failed for SoilTestDetails");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforComplaints( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update Complaints set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaints", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for Complaints" );
                    oncomplete.execute(true, null, "data updated successfully for Complaints" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaints", "data updated successfully for Complaints");

                } else {
                    oncomplete.execute(false, null, "data updation failed for Complaints"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaints", "data updation failed for Complaints");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforComplaintRepisitory( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update ComplaintRepository set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintRepository", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for ComplaintRepository" );
                    oncomplete.execute(true, null, "data updated successfully for ComplaintRepository" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintRepository", "data updated successfully for ComplaintRepository");

                } else {
                    oncomplete.execute(false, null, "data updation failed for ComplaintRepository"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintRepository", "data updation failed for ComplaintRepository");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforComplaintStatusHistory( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update ComplaintStatusHistory set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintStatusHistory", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for ComplaintStatusHistory" );
                    oncomplete.execute(true, null, "data updated successfully for ComplaintStatusHistory" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintStatusHistory", "data updated successfully for ComplaintStatusHistory");

                } else {
                    oncomplete.execute(false, null, "data updation failed for ComplaintStatusHistory"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintStatusHistory", "data updation failed for ComplaintStatusHistory");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforComplaintTypeXref( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update ComplaintTypeXref set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintTypeXref", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for ComplaintTypeXref" );
                    oncomplete.execute(true, null, "data updated successfully for ComplaintTypeXref" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintTypeXref", "data updated successfully for ComplaintTypeXref");

                } else {
                    oncomplete.execute(false, null, "data updation failed for ComplaintTypeXref"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforComplaintTypeXref", "data updation failed for ComplaintTypeXref");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforInsuranceDetails( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update InsuranceDetails set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforInsuranceDetails", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for InsuranceDetails" );
                    oncomplete.execute(true, null, "data updated successfully for InsuranceDetails" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforInsuranceDetails", "data updated successfully for InsuranceDetails");

                } else {
                    oncomplete.execute(false, null, "data updation failed for InsuranceDetails"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforInsuranceDetails", "data updation failed for InsuranceDetails");

                }                }

        }
    }

    public synchronized void updateserverupdatesStatusforPlotCropCycle( final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update PlotCropCycle set ServerUpdatedStatus = 1 where ServerUpdatedStatus = 0";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlotCropCycle", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for PlotCropCycle" );
                    oncomplete.execute(true, null, "data updated successfully for PlotCropCycle" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlotCropCycle", "data updated successfully for PlotCropCycle");

                } else {
                    oncomplete.execute(false, null, "data updation failed for PlotCropCycle"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforPlotCropCycle", "data updation failed for PlotCropCycle");

                }                }

        }
    }



    public synchronized void updateUserRoleXrefData(String id, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update  UserRoleXref set StatusTypeId = 105 , ServerUpdatedStatus = 0 where UserId = "+id+"  and StatusTypeId =104 and RoleId =8";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for UserRoleXref" );
                    oncomplete.execute(true, null, "data updated successfully for UserRoleXref" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", "data updated successfully for UserRoleXref");

                } else {
                    oncomplete.execute(false, null, "data updation failed for UserRoleXref"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", "data updation failed for UserRoleXref");

                }                }

        }
    }

    public synchronized void updateUserRoleXrefDataforVendor(String id, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update  UserRoleXref set StatusTypeId = 105 , ServerUpdatedStatus = 0 where UserId = "+id+"  and StatusTypeId =104 and RoleId =9";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", query);
            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for UserRoleXref" );
                    oncomplete.execute(true, null, "data updated successfully for UserRoleXref" );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", "data updated successfully for UserRoleXref");

                } else {
                    oncomplete.execute(false, null, "data updation failed for UserRoleXref"  );
                    CommonUtils.appendLog(LOG_TAG, "updateserverupdatesStatusforUserRoleXref", "data updation failed for UserRoleXref");

                }                }

        }
    }



    public synchronized void updateUserInfoData(String id, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update  UserRoleXref set StatusTypeId = 105 , ServerUpdatedStatus = 0 where UserId = "+id+"  and StatusTypeId =104 and RoleId =2";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for UserRoleXref" );
                    oncomplete.execute(true, null, "data updated successfully for UserRoleXref" );
                    CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", "data updated successfully for UserRoleXref");

                } else {
                    oncomplete.execute(false, null, "data updation failed for UserRoleXref"  );
                    CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", "data updation failed for UserRoleXref");

                }                }

        }
    }

    public synchronized void updateUserRoleXrefDate(String date, String id, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {

            String   query = "Update  UserRoleXref set UpdatedDate = "+"'" + date + "' where UserId = "+"'" + id + "'";
            Log.d("=====Query======", query);
            CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", query);

            mDatabase.execSQL(query);
            isUpdateSuccess = true;

        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (true) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for UserRoleXref" );
                    oncomplete.execute(true, null, "data updated successfully for UserRoleXref" );
                    CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", "data updated successfully for UserRoleXref");

                } else {
                    oncomplete.execute(false, null, "data updation failed for UserRoleXref"  );
                    CommonUtils.appendLog(LOG_TAG, "updateUserInfoData", "data updation failed for UserRoleXref");

                }                }

        }
    }

    /**
     * Deleting records from database table
     *
     * @param tableName  ---> Table name
     * @param columnName ---> Column name to deleting
     * @param value      ---> Value for where condition
     * @param isWhere    ---> Checking where condition is required or not
     */
    public synchronized void deleteRow(String tableName, String columnName, String value, boolean isWhere, final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;

        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            String query = "delete from " + tableName;
            if (isWhere) {
                query = query + " where " + columnName + " = '" + value + "'";
            }
            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ master data deletion failed for " + tableName + " error is " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "deleteRow", "Master Data Deletion failed for " + tableName + " error is " +e.getMessage());

            //Todo mahesh need to check table exist or not before delete
            onComplete.execute(true, null, "master data deletion failed for " + tableName + " error is " + e.getMessage());
        } finally {
            closeDataBase();

            if (isDataDeleted) {
                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + tableName);
                onComplete.execute(true, null, "master data deleted successfully for " + tableName);
                CommonUtils.appendLog(LOG_TAG, "deleteRow", "Master Data Deleted for " + tableName);

            }

        }
    }

    public synchronized void deleteUserXrefRow(int userId, int roleId ,final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            String query = " delete from UserRoleXref  where UserId = "+"'" + userId + "'"+" AND RoleId = "+"'" + roleId + "'";
            Log.d("deleteUserXrefRowQuery",query + "");
//            if (isWhere) {
//                query = query + " where " + columnName + " = '" + value + "'";
//            }
            Log.v(LOG_TAG, "@@@data deletion failed for " + "Query" + query);
            CommonUtils.appendLog(LOG_TAG, "deleteUserXrefRow", query);
            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ data deletion failed for " + "UserXref" + " error is " + e.getMessage());
            onComplete.execute(false, null, "master data deletion failed for " + "UserXref" + " error is " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "deleteUserXrefRow", "Master Data Deletion failed for UserXref" + " error is " + e.getMessage());

        } finally {
            closeDataBase();

            if (isDataDeleted) {
                Log.v(LOG_TAG, "@@@ data deleted successfully for " + "UserXref");
                onComplete.execute(true, null, "data deleted successfully for " + "UserXref");
                CommonUtils.appendLog(LOG_TAG, "deleteUserXrefRow", "Master Data Deleted for UserXref");

            }

        }
    }







    public synchronized void deleteIDProfRow(int id,String userCode, String typeid ,final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            String query = " delete from IdentityProofs  where Id "+ " = '" + id + "'"+" AND UserCode = "+"'" + userCode + "'"+" AND IdProofTypeId = "+"'" + typeid + "'";
//            if (isWhere) {
//                query = query + " where " + columnName + " = '" + value + "'";
//            }
            Log.v(LOG_TAG, "deleteIDProfRow" + "Query" + query);
            CommonUtils.appendLog(LOG_TAG, "deleteIDProfRow", query);

            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ master data deletion failed for " + "IdentityProofs" + " error is " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "deleteIDProfRow", "Master Data Deletion failed for IdentityProofs" + " error is " + e.getMessage());
            onComplete.execute(false, null, "master data deletion failed for " + "IdentityProofs" + " error is " + e.getMessage());
        } finally {
            closeDataBase();

            if (isDataDeleted) {
                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + "IdentityProofs");
                onComplete.execute(true, null, "master data deleted successfully for " + "IdentityProofs");
                CommonUtils.appendLog(LOG_TAG, "deleteIDProfRow", "Master Data Deleted for IdentityProofs");

            }

        }
    }
    public synchronized void updateFarmerStatus(int statuscode,String userCode,final ApplicationThread.OnComplete<String> onComplete) {
            boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
            String query = "UPDATE UserInfo set ServerUpdatedStatus = "+statuscode+" where code ='"+userCode+"'";

            Log.v(LOG_TAG, "@@@ updateFarmerStatus " + "Query" + query);
            CommonUtils.appendLog(LOG_TAG, "updateFarmerStatus", query);

            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ updateFarmerStatus" + " error is " + e.getMessage());
            onComplete.execute(false, null, "updateFarmerStatus" + " error is " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "updateFarmerStatus", e.getMessage());

        } finally {
            closeDataBase();

            if (isDataDeleted) {
//                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + "IdentityProofs");
                onComplete.execute(true, null, "FarmerStatus Updated");
            }

        }
    }

    public synchronized void updatePlotStatus(int statuscode,String plotCode,final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
            String query = "UPDATE Plot set PlotStatusId = "+statuscode+" where Code ='"+plotCode+"'";

            Log.v(LOG_TAG, "@@@ updatePlotStatus " + "Query" + query);
            CommonUtils.appendLog(LOG_TAG, "updatePlotStatus", query);

            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ updatePlotStatus" + " error is " + e.getMessage());
            onComplete.execute(false, null, "updatePlotStatus" + " error is " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "updatePlotStatus", e.getMessage());

        } finally {
            closeDataBase();

            if (isDataDeleted) {
//                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + "IdentityProofs");
                onComplete.execute(true, null, "PlotStatus Updated");
            }

        }
    }


//    public ArrayList<String> getListOfCodes(final String query) {
//        ArrayList<String> plotCodes = new ArrayList<>();
//        Cursor paCursor = null;
//        try {
//            paCursor = mDatabase.rawQuery(query, null);
//            if (paCursor.moveToFirst()) {
//                do {
//                    plotCodes.add(paCursor.getString(0).trim());
//                } while (paCursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, e.getMessage());
//        } finally {
//            if (null != paCursor)
//                paCursor.close();
//
//            closeDataBase();
//        }
//        return plotCodes;
//    }

    public String getCountValue(String query) {
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }
    public String getdeleteDuplicateValue(String query) {
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }
    public synchronized void insertImageData(String code, String farmercode, String imagepath, String serverUpdatedStatus) {
        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            ContentValues update_values = new ContentValues();
            update_values.put(DatabaseKeys.COLUMN_CODE, code);
            update_values.put(DatabaseKeys.COLUMN_FARMERCODE, farmercode);
            update_values.put(DatabaseKeys.COLUMN_MODULEID, 100);
//            update_values.put(DatabaseKeys.COLUMN_PLOTCODE, "");
            update_values.put(DatabaseKeys.COLUMN_PHOTO, imagepath);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_CREATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_UPDATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_UPDATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_SERVERUPDATEDSTATUS, serverUpdatedStatus);
            mDatabase.insert(DatabaseKeys.TABLE_PICTURE_REPORTING, null, update_values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase();
        }
    }

    public void closeDataBase() {
//        if (mDatabase != null)
//            mDatabase.close();
    }

    public void executeRawQuery(String query) {
        try {
            if (mDatabase != null) {
                mDatabase.execSQL(query);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


//    public List<Pair> getOnlyPairData(final String query) {
//        Log.v(LOG_TAG, "@@@ Generic Query " + query);
//        List<Pair> mGenericData = new ArrayList<>();
////        mDatabase = palm3FoilDatabase.getWritableDatabase();
//        Cursor genericDataQuery = null;
//        try {
//            genericDataQuery = mDatabase.rawQuery(query, null);
//            if (genericDataQuery.moveToFirst()) {
//                do {
//                    mGenericData.add(Pair.create(genericDataQuery.getString(0), genericDataQuery.getString(1)));
//                } while (genericDataQuery.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, e.getMessage());
//        } finally {
//            if (null != genericDataQuery)
//                genericDataQuery.close();
//
//            closeDataBase();
//        }
//        return mGenericData;
//    }
//
//    public void upNotificationStatus() {
//        ContentValues update_values = new ContentValues();
//        update_values.put("ServerUpdatedStatus", "0");
//        update_values.put("isRead", 1);
//        update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
//        String where = " isRead ='" + 0 + "'";
//       // mDatabase.update("Alerts", update_values, where, null);
//    }




    public boolean bulkinserttoTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);
                long id = mDatabase.insert(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }

    public boolean bulkUpdateToTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);
                long id = mDatabase.replaceOrThrow(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }




    public T getFarmerHistoryData(final String query, final int type) {
        FarmerHistory mFarmerHistory = null;
        List<FarmerHistory> mFarmerHistoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerHistory = new FarmerHistory();
                    mFarmerHistory.setFarmercode(cursor.getString(1));
                    mFarmerHistory.setPlotcode(cursor.getString(2));
                    mFarmerHistory.setStatustypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFarmerHistory.setIsactive(cursor.getInt(4));
                    mFarmerHistory.setCreatedbyuserid(cursor.getInt(5));
                    mFarmerHistory.setCreateddate(cursor.getString(6).replace("T"," "));
                    mFarmerHistory.setUpdatedbyuserid(cursor.getInt(7));
                    mFarmerHistory.setUpdateddate(cursor.getString(8));
                    mFarmerHistory.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mFarmerHistoryList.add(mFarmerHistory);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerHistory : mFarmerHistoryList);
    }

//    public String getCropVariety(final String query, final int type) {
//        ArrayList<String> arrCropVariety=new ArrayList<>();
//        String resultCropVariety="";
//        Cursor cursor = null;
//        Log.v(LOG_TAG, "@@@ farmer details query " + query);
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//
//                    arrCropVariety.add(cursor.getString(0));
//
//
//                } while (cursor.moveToNext());
//            }
//            Set<String> hs = new HashSet<>();
//            hs.addAll(arrCropVariety);
//            arrCropVariety.clear();
//            arrCropVariety.addAll(hs);
//
//            for(int i=0;i<arrCropVariety.size();i++)
//            {
//
//                cursor = mDatabase.rawQuery("select Name from LookUp where id="+arrCropVariety.get(i), null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    do {
//
//                        resultCropVariety=resultCropVariety+cursor.getString(0)+"\n";
//
//
//                    } while (cursor.moveToNext());
//                }
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
//        }
//        // resultCropVariety.replace("@",",");
//        return resultCropVariety;
//    }
//
//    public T getFollowupData(final String query, final int type) {
//        FollowUp mFollowUp = null;
//        List<FollowUp> mFollowUpList = new ArrayList<>();
//        Cursor cursor = null;
//        Log.v(LOG_TAG, "@@@ FollowUp query " + query);
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    mFollowUp = new FollowUp();
//                    mFollowUp.setPlotcode(cursor.getString(1));
//                    mFollowUp.setIsfarmerreadytoconvert(cursor.getInt(2));
//                    mFollowUp.setIssuedetails(cursor.getString(3));
//                    mFollowUp.setComments(cursor.getString(4));
//                    mFollowUp.setPotentialscore((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
//                    mFollowUp.setHarvestingmonth(cursor.getString(6));
//                    mFollowUp.setCreatedbyuserid(cursor.getInt(7));
//                    mFollowUp.setCreateddate(cursor.getString(8));
//                    mFollowUp.setUpdatedbyuserid(cursor.getInt(9));
//                    mFollowUp.setUpdateddate(cursor.getString(10));
//                    mFollowUp.setServerupdatedstatus(cursor.getInt(11));
//                    if (type == 1) {
//                        mFollowUpList.add(mFollowUp);
//                    }
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
//        }
//        return (T) ((type == 0) ? mFollowUp : mFollowUpList);
//    }
//
//
//
//
//
//
//    public String getFalogLatLongs(final String query) {
//        Log.v(LOG_TAG, "@@@ Generic Query " + query);
//        String latlongData = "";
//        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
//        try {
//            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
//                do {
//                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
//                } while (genericDataQuery.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, e.getMessage());
//        } finally {
//            if (null != genericDataQuery)
//                genericDataQuery.close();
//
//            closeDataBase();
//        }
//        return latlongData;
//    }
//
//    public String getLatLongs(final String query) {
//        Log.v(LOG_TAG, "@@@ Generic Query " + query);
//        String latlongData = "";
//        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
//        try {
//            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
//                do {
//                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
//                } while (genericDataQuery.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, e.getMessage());
//        } finally {
//            if (null != genericDataQuery)
//                genericDataQuery.close();
//
//            closeDataBase();
//        }
//        return latlongData;
//    }



    public T getAlertsDetails(final String query, int dataReturnType, boolean fromRefresh) {
        Cursor cursor = null;
        Alerts alertDetails = null;
        List alertsDataList = new ArrayList();
        Log.v(LOG_TAG, "@@@ alertDetails  query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertDetails = new Alerts();
                    alertDetails.setId(cursor.getInt(0));
                    alertDetails.setName(cursor.getString(1));
                    alertDetails.setDesc(cursor.getString(2));
                    alertDetails.setUserId(cursor.getInt(3));
                    alertDetails.setHTMLDesc(cursor.getString(4));
                    alertDetails.setIsRead(cursor.getInt(5));
                    alertDetails.setPlotCode(cursor.getString(6));
                    alertDetails.setComplaintCode(cursor.getString(7));
                    alertDetails.setAlertTypeId(cursor.getInt(8));
                    alertDetails.setCreatedByUserId(cursor.getInt(9));
                    alertDetails.setCreatedDate(cursor.getString(10));
                    alertDetails.setUpdatedByUserId(cursor.getInt(11));
                    alertDetails.setUpdatedDate(cursor.getString(12));
                    alertDetails.setServerUpdatedStatus(cursor.getInt(13));
                    if (!fromRefresh) {
                        alertDetails.setHTMLDesc(cursor.getString(14));
                    }
                    if (dataReturnType == 1) {
                        alertsDataList.add(alertDetails);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting  alertDetails " + e.getMessage());
        }
        return (T) ((dataReturnType == 0) ? alertDetails : alertsDataList);
    }
    public T getSelectedIdProofsData(final String query, final int type) {
        IdentityProof mIdentityProof = null;
        List<IdentityProof> mIdentityProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ IdentityProof query " + query);
        CommonUtils.appendLog(LOG_TAG, "getSelectedIdProofsData", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProof = new IdentityProof();
                    mIdentityProof.setId(cursor.getInt(0));
                    mIdentityProof.setUserCode(cursor.getString(1));
                    mIdentityProof.setIdProofTypeId(cursor.getInt(2));
                    mIdentityProof.setIdProofNumber(cursor.getString(3));
                    mIdentityProof.setFileName(cursor.getString(4));
                    mIdentityProof.setFileLocation(cursor.getString(5));
                    mIdentityProof.setFileExtension(cursor.getString(6));
                    mIdentityProof.setIsActive(true);
                    mIdentityProof.setIsVerified(false);
                    mIdentityProof.setCreatedByUserId((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mIdentityProof.setCreatedDate(cursor.getString(10));
                    mIdentityProof.setUpdatedByUserId((cursor.getInt(11) == 0) ? null : cursor.getInt(11));
                    mIdentityProof.setUpdatedDate(cursor.getString(12));
                    mIdentityProof.setFileBytes(cursor.getString(13));
                    mIdentityProof.setServerUpdatedStatus(true);

                    if (type == 1) {
                        mIdentityProofList.add(mIdentityProof);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getSelectedIdProofsData", e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProof : mIdentityProofList);
    }

    public T getFileRepositoryData(final String query, final int type) {
        List<FileRepositoryRefresh> mFileRepositoryList = new ArrayList<>();
        FileRepositoryRefresh mFileRepository = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository query " + query);
        CommonUtils.appendLog(LOG_TAG, "getFileRepositoryData", query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFileRepository = new FileRepositoryRefresh();
                    mFileRepository.setId((cursor.getInt(0) == 0) ? null : cursor.getInt(0));
                    mFileRepository.setFarmerCode(cursor.getString(1));
                    int moduleTypeId = ((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFileRepository.setModuleTypeId(moduleTypeId);
                    if (moduleTypeId == 193) {
                        mFileRepository.setPlotCode(null);
                        mFileRepository.setCropMaintenanceCode(null);
                    }else if(moduleTypeId == 303){
                        mFileRepository.setPlotCode(cursor.getString(2));
                        mFileRepository.setCropMaintenanceCode(null);
                    }else {
                        mFileRepository.setPlotCode(cursor.getString(2));

                        mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    }
                    mFileRepository.setFileName(cursor.getString(4));
                    mFileRepository.setFileLocation(cursor.getString(5));
                    mFileRepository.setFileExtension(cursor.getString(6));
                    mFileRepository.setIsActive(cursor.getInt(7));
                    mFileRepository.setCreatedByUserId((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFileRepository.setCreatedDate(cursor.getString(9));
                    mFileRepository.setUpdatedByUserId((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mFileRepository.setUpdatedDate(cursor.getString(11));
                    mFileRepository.setServerUpdatedStatus(cursor.getInt(12));

                    // mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    File imgFile = new File(mFileRepository.getFileLocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mFileRepository.setByteImage(base64string);
                    } else {
                        mFileRepository.setByteImage("");
                    }
                    if (type == 1) {
                        mFileRepositoryList.add(mFileRepository);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getFileRepositoryData", e.getMessage());
        }
        return (T) ((type == 0) ? mFileRepository : mFileRepositoryList);
    }
    public FileRepository getSelectedFileRepository(String query) {
        FileRepository savedPictureData = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    savedPictureData = new FileRepository();
                    savedPictureData.setId(cursor.getInt(1));
                    savedPictureData.setPlotCode(cursor.getString(2));
                    savedPictureData.setDocTypeId(cursor.getInt(3));
                    savedPictureData.setFileName(cursor.getString(4));
                    savedPictureData.setFileLocation(cursor.getString(5));
                    savedPictureData.setFileExtension(cursor.getString(6));
                    savedPictureData.setIsActive(cursor.getInt(7));
                    savedPictureData.setCreatedByUserId(cursor.getInt(8));
                    savedPictureData.setCreatedDate(cursor.getString(9));
                    savedPictureData.setUpdatedByUserId(cursor.getInt(10));
                    savedPictureData.setUpdatedDate(cursor.getString(11));
                    savedPictureData.setServerUpdatedStatus(cursor.getInt(12));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting getSelectedFileRepository " + e.getMessage());
        }
        return savedPictureData;
    }
    public T getFarmerBankData(final String query, final int type) {
        BankDetails mFarmerBank = null;
        List<BankDetails> mFarmerBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getFarmerBankData", query);
        try {
//
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerBank = new BankDetails();
                    mFarmerBank.setId(cursor.getInt(0));
                    mFarmerBank.setUserCode(cursor.getString(1));
                    mFarmerBank.setBankId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFarmerBank.setBPFileName(cursor.getString(4));
                    mFarmerBank.setBPFileLocation(cursor.getString(5));
                    mFarmerBank.setBPFileExtension(cursor.getString(6));
                    mFarmerBank.setAccountHolderName(cursor.getString(7));
                    mFarmerBank.setAccountNumber(cursor.getString(8));
                    mFarmerBank.setFileName(cursor.getString(9));
                    mFarmerBank.setFileLocation(cursor.getString(10));
                    mFarmerBank.setFileExtension(cursor.getString(11));
                    mFarmerBank.setIsActive(1);
                    mFarmerBank.setCreatedByUserId((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    mFarmerBank.setCreatedDate(cursor.getString(14));
                    mFarmerBank.setUpdatedByUserId((cursor.getInt(15) == 0) ? null : cursor.getInt(15));
                    mFarmerBank.setUpdatedDate(cursor.getString(16));
                    mFarmerBank.setFileBytes(cursor.getString(17));
                    mFarmerBank.setBankPassbookFileBytes(cursor.getString(18));
                    mFarmerBank.setServerUpdatedStatus(1);

                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    if (type == 1) {
                        mFarmerBankList.add(mFarmerBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getFarmerBankData", e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerBank : mFarmerBankList);
    }


    public T getBankDetailsHistoryData(final String query, final int type) {
        BankDetailsHistory mBankHistory = null;
        List<BankDetailsHistory> mBankDetailsHistory = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getBankDetailsHistoryData", query);
        try {
//
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mBankHistory = new BankDetailsHistory();
                    mBankHistory.setId(cursor.getInt(0));
                    mBankHistory.setUserCode(cursor.getString(1));
                    mBankHistory.setBankId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mBankHistory.setBPFileName(cursor.getString(4));
                    mBankHistory.setBPFileLocation(cursor.getString(5));
                    mBankHistory.setBPFileExtension(cursor.getString(6));
                    mBankHistory.setAccountHolderName(cursor.getString(7));
                    mBankHistory.setAccountNumber(cursor.getString(8));
                    mBankHistory.setFileName(cursor.getString(9));
                    mBankHistory.setFileLocation(cursor.getString(10));
                    mBankHistory.setFileExtension(cursor.getString(11));
                    mBankHistory.setIsActive(cursor.getInt(12));
                    mBankHistory.setCreatedByUserId((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    mBankHistory.setCreatedDate(cursor.getString(14));
                    mBankHistory.setUpdatedByUserId((cursor.getInt(15) == 0) ? null : cursor.getInt(15));
                    mBankHistory.setUpdatedDate(cursor.getString(16));
                    mBankHistory.setServerUpdatedStatus(cursor.getInt(17));

                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    if (type == 1) {
                        mBankDetailsHistory.add(mBankHistory);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getBankDetailsHistoryData", e.getMessage());
        }
        return (T) ((type == 0) ? mBankHistory : mBankDetailsHistory);
    }


    public T getPlotplotStatusHistories(final String query, final int type) {
        PlotStatusHistory mPlotLandlord = null;
        List<PlotStatusHistory> mPlotLandlordList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getPlotplotStatusHistories", query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotLandlord = new PlotStatusHistory();
                    mPlotLandlord.setId(cursor.getInt(0));
                    mPlotLandlord.setPlotCode(cursor.getString(1));
                    mPlotLandlord.setPlotStatusId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlotLandlord.setCreatedByUserId((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlotLandlord.setCreatedDate(cursor.getString(4));
                    mPlotLandlord.setUpdatedByUserId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mPlotLandlord.setUpdatedDate(cursor.getString(6));
                    mPlotLandlord.setServerUpdatedStatus(cursor.getInt(7));
                    if (type == 1) {
                        mPlotLandlordList.add(mPlotLandlord);
                    }
                  //  Log.e(LOG_TAG, "@@@mPlotLandlord " +mPlotLandlord);
                   // Log.e(LOG_TAG, "@@@mPlotLandlordList " +mPlotLandlordList);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getPlotplotStatusHistories", e.getMessage());

        }
        return (T) ((type == 0) ? mPlotLandlord : mPlotLandlordList);
    }


    public void getFarmerDetailsForSearch(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<Farmer> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromConversion()) {
            query = Queries.getInstance().getFilterBasedFarmers(83, key, offset, limit);
        } else if (CommonUtils.isFromCropMaintenance()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key, offset, limit);
        } else if (CommonUtils.isViewProspectiveFarmers()) {
            query = Queries.getInstance().getFilterBasedProspectiveFarmers(81, key, offset, limit);
        } else if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getFilterBasedFarmersFollowUp(key, offset, limit);
        } else if (CommonUtils.isComplaint()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key, offset, limit);
        } else if(CommonUtils.isPlotSplitFarmerPlots()){
            query = Queries.getInstance().getFilterBasedFarmersCropRetake(key, offset, limit);
        }else{
            query = Queries.getInstance().getFarmersDataForWithOffsetLimit(key, offset, limit);
        }

        Log.v(LOG_TAG, "Query for getting farmers " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Farmer farmlanddetails = new Farmer();
                    farmlanddetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFirstname(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setMiddlename(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setLastname(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setStateid(cursor.getInt(cursor.getColumnIndex("StateId")));
                    farmlanddetails.setPrimaryPhoneNumber(cursor.getString(cursor.getColumnIndex("PrimaryPhoneNumber")));
                    farmlanddetails.setVillageid(cursor.getInt(cursor.getColumnIndex("VillageId")));
                    farmlanddetails.setPPFileLocation(cursor.getString(cursor.getColumnIndex("PPFileLocation")));
                    farmlanddetails.setPPFileName(cursor.getString(cursor.getColumnIndex("PPFileName")));
                    farmlanddetails.setPPFileExtension(cursor.getString(cursor.getColumnIndex("PPFileExtension")));
                    farmlanddetails.setFatherName_GuardianName(cursor.getString(cursor.getColumnIndex("FatherName_GuardianName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG,"getting failed fromLocalDb"+e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, farmerDetails, "getting data");
        }
    }
    public T getNotification(final String query, final int type) {
        List<Notifications> notificationlist = new ArrayList<>();
        Notifications notificationdetails = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address getNotification query " + query);
        CommonUtils.appendLog(LOG_TAG, "getNotification", query);
        try {
//
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    notificationdetails = new Notifications();
                    notificationdetails.setId(cursor.getInt(0));
                    notificationdetails.setUserId(cursor.getInt(1));
                    notificationdetails.setDesc(cursor.getString(2));
                    notificationdetails.setRaisedByUserId(cursor.getInt(3));
                    Log.d("DATA", " Analysis =============>  ISREAD  :"+cursor.getString(4));
                    if(cursor.getString(4).equalsIgnoreCase("false"))
                        notificationdetails.setIsRead(0);
                    else{
                        notificationdetails.setIsRead(1);
                    }

                    notificationdetails.setNotificationTypeId(cursor.getInt(5));
                    notificationdetails.setIsActive(cursor.getInt(6));
                    notificationdetails.setCreatedDate(cursor.getString(8));

                    notificationdetails.setUpdatedDate(cursor.getString(10));
                    notificationdetails.setCreatedByUserId(cursor.getInt(7));
                    notificationdetails.setUpdatedByUserId(cursor.getInt(9));
                    notificationdetails.setHeader(cursor.getString(11));
                    notificationdetails.setServerUpdatedStatus(cursor.getInt(12));
                    Log.d("DATA", " Analysis =============>  notification  :"+notificationdetails);
                    if (type == 1) {
                        notificationlist.add(notificationdetails);
                    }

                } while (cursor.moveToNext());
            }
            Log.d("database","==================>notifcations Count :"+notificationlist.size());
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getNotification", e.getMessage());

        }
        return (T) ((type == 0) ? notificationdetails : notificationlist);
    }



    public T getGeoboudryDetails(final String query, final int type) {
        List<geoBoundaries> GeoBoudaries = new ArrayList<>();
        geoBoundaries mgeoBoundaries = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ geo details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mgeoBoundaries = new geoBoundaries();
                    mgeoBoundaries.setId(cursor.getInt(0));
                    mgeoBoundaries.setPlotCode(cursor.getString(1));
                    mgeoBoundaries.setLatitude(cursor.getDouble(2));
                    mgeoBoundaries.setLongitude(cursor.getDouble(3));
                    mgeoBoundaries.setGeoCategoryTypeId(cursor.getInt(4));
                    mgeoBoundaries.setCreatedByUserId(cursor.getInt(6));
                    mgeoBoundaries.setCreatedDate(cursor.getString(7));
                    mgeoBoundaries.setUpdatedByUserId(cursor.getInt(8));
                    mgeoBoundaries.setUpdatedDate(cursor.getString(9));
                    mgeoBoundaries.setServerUpdatedStatus(cursor.getInt(10));

                    if (type == 1) {
                        GeoBoudaries.add(mgeoBoundaries);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting boundries details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mgeoBoundaries : GeoBoudaries);
    }
    public String getFalogLatLongs(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        CommonUtils.appendLog(LOG_TAG, "getFalogLatLongs", query);

        String latlongData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
                do {
                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getFalogLatLongs", e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return latlongData;
    }

    public T getLocationtracting(final String query, final int type) {
        List<LocationTracker> locationTracker = new ArrayList<>();
        LocationTracker locationtracker = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getLocationtracking", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    locationtracker = new LocationTracker();
                    locationtracker.setId(cursor.getInt(0));
                    locationtracker.setUserId(cursor.getInt(1));
                    locationtracker.setLatitude(cursor.getDouble(2));
                    locationtracker.setLongitude(cursor.getDouble(3));
                    locationtracker.setAddress(cursor.getString(4));
                    locationtracker.setLogDate(cursor.getString(5));
                    locationtracker.setServerUpdatedStatus(cursor.getInt(6));

                    if (type == 1) {
                        locationTracker.add(locationtracker);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getLocationtracking", e.getMessage());
        }
        return (T) ((type == 0) ? locationtracker : locationTracker);
    }



    public T getComplaintsDetails(final String query, final int type) {
        List<Complaints> GetComplaints = new ArrayList<>();
        Complaints  mcomplaints = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ geo details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mcomplaints = new Complaints();
                    mcomplaints.setId(cursor.getInt(0));
                    mcomplaints.setCode(cursor.getString(1));
                    mcomplaints.setPlotCode(cursor.getString(2));
                    mcomplaints.setIsActive(cursor.getInt(3));

                    mcomplaints.setCreatedByUserId(cursor.getInt(4));
                    mcomplaints.setCreatedDate(cursor.getString(5));
                    mcomplaints.setUpdatedByUserId(cursor.getInt(6));
                    mcomplaints.setUpdatedDate(cursor.getString(7));
                    mcomplaints.setServerUpdatedStatus(cursor.getInt(8));

                    if (type == 1) {
                        GetComplaints.add(mcomplaints);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting boundries details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mcomplaints : GetComplaints);
    }


    public T getComplaintrepoDetails(final String query, final int type) {
        List<ComplaintRepository> GetComplaintsRepo = new ArrayList<>();
        ComplaintRepository  mcomplaints = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ geo details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mcomplaints = new ComplaintRepository();
                    mcomplaints.setId(cursor.getInt(0));
                    mcomplaints.setComplaintCode(cursor.getString(1));
                    mcomplaints.setFileName(cursor.getString(2));
                    mcomplaints.setFileLocation(cursor.getString(3));
                    mcomplaints.setFileExtension(cursor.getString(4));

                    Log.d("DATA", " Analysis =============>  ISAudioRecording  :"+cursor.getString(5));
                    if( cursor.getString(5).equalsIgnoreCase("false"))
                        mcomplaints.setIsAudioRecording(0);
                    else{
                        mcomplaints.setIsAudioRecording(1);
                    }


                    // mcomplaints.setIsAudioRecording(cursor.getInt(5));
                    mcomplaints.setIsResult(cursor.getInt(6));
                    mcomplaints.setIsActive(cursor.getInt(7));
                    mcomplaints.setCreatedByUserId(cursor.getInt(8));
                    mcomplaints.setCreatedDate(cursor.getString(9));
                    mcomplaints.setUpdatedByUserId(cursor.getInt(10));
                    mcomplaints.setUpdatedDate(cursor.getString(11));
                    mcomplaints.setFileBytes(cursor.getString(12));
                    mcomplaints.setServerUpdatedStatus(cursor.getInt(13));

                    if (type == 1) {
                        GetComplaintsRepo.add(mcomplaints);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting boundries details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mcomplaints : GetComplaintsRepo);
    }


    public T getComplaintstatusDetails(final String query, final int type) {
        List<ComplaintStatusHistory> GetComplaintstatus = new ArrayList<>();
        ComplaintStatusHistory  mcomplaints = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ geo details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mcomplaints = new ComplaintStatusHistory();
                    mcomplaints.setId(cursor.getInt(0));
                    mcomplaints.setComplaintCode(cursor.getString(1));
                    mcomplaints.setStatusTypeId(cursor.getInt(2));
                    mcomplaints.setAssigntoUserId(cursor.getInt(3));
                    mcomplaints.setComments(cursor.getString(4));
                    mcomplaints.setIsActive(cursor.getInt(5));

                    mcomplaints.setCreatedByUserId(cursor.getInt(6));
                    mcomplaints.setCreatedDate(cursor.getString(7));
                    mcomplaints.setUpdatedByUserId(cursor.getInt(8));
                    mcomplaints.setUpdatedDate(cursor.getString(9));
                    mcomplaints.setServerUpdatedStatus(cursor.getInt(10));

                    if (type == 1) {
                        GetComplaintstatus.add(mcomplaints);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting boundries details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mcomplaints : GetComplaintstatus);
    }

    public T getComplaintxrefDetails(final String query, final int type) {
        List<ComplaintTypeXref> GetComplaints = new ArrayList<>();
        ComplaintTypeXref mcomplaints = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ geo details query " + query);
        CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mcomplaints = new ComplaintTypeXref();
                    mcomplaints.setId(cursor.getInt(0));
                    mcomplaints.setComplaintCode(cursor.getString(1));
                    mcomplaints.setComplaintTypeId(cursor.getInt(2));
                    mcomplaints.setCreatedByUserId(cursor.getInt(3));
                    mcomplaints.setCreatedDate(cursor.getString(4));
                    mcomplaints.setUpdatedByUserId(cursor.getInt(5));
                    mcomplaints.setUpdatedDate(cursor.getString(6));
                    mcomplaints.setServerUpdatedStatus(cursor.getInt(7));

                    if (type == 1) {
                        GetComplaints.add(mcomplaints);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting boundries details " + e.getMessage());
            CommonUtils.appendLog(LOG_TAG, "getGeoboudryDetails", e.getMessage());
        }
        return (T) ((type == 0) ? mcomplaints : GetComplaints);
    }

}
