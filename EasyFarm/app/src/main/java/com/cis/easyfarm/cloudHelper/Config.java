package com.cis.easyfarm.cloudHelper;

import com.cis.easyfarm.BuildConfig;


public class Config {

    public static final boolean DEVELOPER_MODE = false;

    //public static final String login_url = "/User/MobileLogin";
//      Test url
  public static String live_url = "http://183.82.111.111/EasyFarm/API/api/";


  public static void initialize() {
        if(BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {
            live_url = "http://183.82.111.111/EasyFarm/API/api/" ;

        }
        else {
            live_url = "http://183.82.111.111/EasyFarm/API/api/";
        }
    }

    public static final String GPS_TRACKING_url = "http://183.82.103.171:8086/API/api/";
    public static final String login_url = "User/Login";
    public static final String mobile_login_url = "User/MobileLogin";
    public static final String masterSyncUrl = "Sync/GetMasterCount";
    public static final String syncClassTypeMaster = "Sync/SyncClassTypeMaster";
    public static final String syncTypeCdDmtMaster = "Sync/SyncTypeCdDmtMaster";
    public static final String syncLookUpMaster = "Sync/SyncLookUpMaster";
    public static final String syncCountryMaster = "Sync/SyncCountryMaster";
    public static final String syncStateMaster = "Sync/SyncStateMaster";
    public static final String syncDistrictMaster = "Sync/SyncDistrictMaster";
    public static final String syncMandalMaster = "Sync/SyncMandalMaster";
    public static final String syncVillageMaster = "Sync/SyncVillageMaster";
    public static final String syncBankMaster = "Sync/SyncBankMaster";
    public static final String SyncCountryMaster = "Sync/SyncCountryMaster";
    public static final String SyncRoleMaster = "Sync/SyncRoleMaster";
    public static final String SyncCropInfo = "Sync/SyncCropInfo";
    public static final String SyncInsuranceProvider = "Sync/SyncInsuranceProvider";
    public static final String SyncSeed = "Sync/SyncSeed";




    public static final String Getstate ="State/GetStates/1/null";
    public static final String Getdistricts ="Districts/GetDistricts/";
    public static final String Getmandals ="Mandal/GetMandals/";
    public static final String Getvillages ="Village/GetVillages/";
    public static final String Getgender="TypeCdDmt/1";
    public static final String Getroles = "Role/GetAllRoles/null/true/true";
    public static final String externalRegistration = "User/ExternalRegistration";
    public static final String syncNotifications = "Sync/SyncNotifications";

    public static final String GetPlotstatus = "TypeCdDmt/14";
    public static final String GetPlotownership = "TypeCdDmt/16";
    public static final String plotRegistration = "Plots/PlotRegistration";
    public static final String SyncBankDetails = "Sync/SyncBankDetails";
 public static final String getTransData = "Sync/%s";//api/TranSync/SyncFarmers/{Date}/{UserId}/{Index}

    public static final String SyncTransactions = "Sync/SyncTransactions";
    public static final String getReverseTranscationSyncCount = "Sync/GetReverseTransactionSyncCount";
    public static final String getAllNotifications = "Notification/AllNotifications";
    public static final String GetAddressByPinCode ="Village/GetAddressByPinCode/";

    public static final String image_url = "http://183.82.111.111/EasyFarmFileRepository/FileRepository/";
    public static final String UpdateDeviseToken = "User/UpdateDeviseTokenByUserId";
    public static final String locationTrackingURL="Tracker/SaveOfflineLocations";




}
