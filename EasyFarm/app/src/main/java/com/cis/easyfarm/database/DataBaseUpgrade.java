package com.cis.easyfarm.database;



import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;


import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.UiUtils;

import static android.content.Context.MODE_PRIVATE;

class DataBaseUpgrade {

    private static final String LOG_TAG = DataBaseUpgrade.class.getName();

    static void upgradeDataBase(final Context context, final SQLiteDatabase db) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
        boolean result = true;
        try {
            boolean isFreshInstall = sharedPreferences.getBoolean(CommonConstants.IS_FRESH_INSTALL, true);
            if (isFreshInstall) {
                upgradeDb1(db);
//                upgradeDb2(db);
//                upgradeDb3(db);
//               upgradeDb4(db);
//                upgradeDb6(db);
//                upgradeDb7(db);
//                upgradeDb8(db);

            } else {
                boolean isDbUpgradeFinished =  sharedPreferences.getBoolean(String.valueOf(EasyFarmDatabse.DATA_VERSION), false);
                Log.v(LOG_TAG, "@@@@ database....."+isDbUpgradeFinished);
                if (!isDbUpgradeFinished) {
                    switch (EasyFarmDatabse.DATA_VERSION) {
                        case 8:
                            Log.d("--db ---", "---- analysis --- >> upgrade1 -->");
                            upgradeDb1(db);
                            break;
//
//                        case 2:
//                            upgradeDb2(db);
//                            break;
//
//                        case 3:
//                            upgradeDb3(db);
//                            break;
//                        case 4:
//                            upgradeDb4(db);
//                            break;
//                        case 5:
//                            upgradeDb4(db);
//                            break;
//                        case 6:
//                            UiUtils.showCustomToastMessage("Updating database 6-->"+EasyFarmDatabse.DATA_VERSION, context, 0);
//                            upgradeDb6(db);
//                            break;
//                        case 7:
//                            upgradeDb7(db);
////                            Log.pushLogToCrashlytics("Updating database 7 :"+"\n "+" "+CommonConstants.TAB_ID+" "+ CommonUtils.getAppVersion(context));
//                            UiUtils.showCustomToastMessage("Updating database 7 -->"+EasyFarmDatabse.DATA_VERSION, context, 0);
//                            break;
//                        case 8:
//                            upgradeDb8(db);
//
//                            UiUtils.showCustomToastMessage("Updating database 8 -->"+EasyFarmDatabse.DATA_VERSION,context,0);
//                            break;
                    }
                } else {
                    Log.v(LOG_TAG, "@@@@ database is already upgraded "+EasyFarmDatabse.DATA_VERSION);
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
            result = false;
        } finally {
            if (result) {
                Log.v(LOG_TAG, "@@@@ database is upgraded "+EasyFarmDatabse.DATA_VERSION);
            } else {
                Log.e(LOG_TAG, "@@@@ database is upgrade failed or already upgraded");
            }
            sharedPreferences.edit().putBoolean(CommonConstants.IS_FRESH_INSTALL, false).apply();
            sharedPreferences.edit().putBoolean(String.valueOf(EasyFarmDatabse.DATA_VERSION), true).apply();
        }
    }

    public static void upgradeDb1(final SQLiteDatabase db) {




        String CREATE_Class_Type = "CREATE TABLE IF NOT EXISTS ClassType(\n" +
                "ClassTypeId INTEGER   \n" +
                "                                       ,\n" +
                "Name varchar(100) ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT,\n"+
                "UpdatedDate DATETIME )";

//        CREATE TABLE TypeCdDmt(
//                TypeCdId  INT PRIMARY KEY,
//                ClassTypeId INT  NOT NULL FOREIGN KEY REFERENCES  ClassType(ClassTypeId),
//                [Desc] VARCHAR(100) NOT NULL,
//        TableName varchar(100),
//                ColumnName varchar(100),
//                SortOrder INT NOT NULL,
//                IsActive  BIT NOT NUll DEFAULT 1,
//                CreatedByUserId  INT NOT NULL  FOREIGN KEY REFERENCES UserInfo(Id),
//                CreatedDate  DATETIME  NOT  NULL,
//                UpdatedByUserId INT NOT NULL  FOREIGN KEY REFERENCES UserInfo(Id),
//                UpdatedDate  DATETIME  NOT  NULL
        //)
        String CREATE_TYPECDDMT = "CREATE TABLE IF NOT EXISTS TypeCdDmt(\n" +
                "TypeCdId INTEGER   \n" +
                "                                    ,\n" +
                "ClassTypeId INT  ,\n"+
                "DESC varchar(100) ,\n" +
                "TableName VARCHAR(100) ,\n" +
                "ColumnName VARCHAR(100) ,\n" +
                "SortOrder INT, \n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME )";
        String CREATE_LOOKUP = "CREATE TABLE IF NOT EXISTS LookUp(\n" +
                "Id INTEGER  \n" +
                "                                ,\n" +
                "LookUpTypeId INT ,\n"+
                "Name varchar(250) ,\n" +
                "Remarks VARCHAR(1000) ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME ) ";


        String CREATE_COUNTRY = "CREATE TABLE IF NOT EXISTS Country(\n" +
                "Id INTEGER  \n" +
                "                                     ,\n" +
                "Name varchar(250),\n" +
                "Code VARCHAR(10) ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT,\n"+
                "UpdatedDate DATETIME )";

        String CREATE_STATE = "CREATE TABLE IF NOT EXISTS State(\n" +
                "Id INTEGER  \n" +
                "                                    ,\n" +
                "Name varchar(250),\n" +
                "Code VARCHAR(10)  ,\n" +
                "CountryId INT ,\n"+
                "IsActive  BOOLEAN ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";
        String CREATE_DISTRICT= "CREATE TABLE IF NOT EXISTS District(\n" +
                "Id INTEGER   \n" +
                "                                    ,\n" +
                "Name varchar(250) ,\n" +
                "Code VARCHAR(10)  ,\n" +
                "StateId INT ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";

        String CREATE_MANDAL= "CREATE TABLE IF NOT EXISTS Mandal(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "Name varchar(250),\n" +
                "Code VARCHAR(10)  ,\n" +
                "DistrictId INT  ,\n"+
                "IsCity  BOOLEAN  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";

        String CREATE_VILLAGE= "CREATE TABLE IF NOT EXISTS Village( \n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "Name varchar(250) ,\n" +
                "Code VARCHAR(10)  ,\n" +
                "MandalId INT  ,\n"+
                "PinCode varchar(10),\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";

        String CREATE_BANK= "CREATE TABLE IF NOT EXISTS Bank(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "BankId INT  ,\n"+
                "BranchName varchar(255) ,\n" +
                "IFSCCode VARCHAR(50)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";

        String CREATE_Role= "CREATE TABLE IF NOT EXISTS Role(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "ParentRoleId INT  ,\n"+
                "Code varchar(100) ,\n" +
                "Name varchar(100) ,\n" +
                "Desc varchar(100) ,\n" +
                "GeoAccess  BOOLEAN  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME ,\n"+
                "ExternalAccess  BOOLEAN )";

        String CREATE_InsuranceProvider= "CREATE TABLE IF NOT EXISTS InsuranceProvider(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "Name varchar(100) ,\n" +
                "HelpLineContactNumber varchar(15) ,\n" +
                "Email varchar(15) ,\n" +
                "WebSiteUrl nvarchar ,\n" +
                "Address varchar(15) ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate  DATETIME )";

        String CREATE_CropInfo= "CREATE TABLE IF NOT EXISTS CropInfo(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "CropId INT ,\n" +
                "SeedId INT ,\n" +
                "SeasonTypeId INT ,\n" +
                "DistrictId INT ,\n" +
                "Amount float ,\n" +
                "MinDuration INT ,\n" +
                "MaxDuration INT ,\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtention varchar(15)  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate  DATETIME )";

        String CREATE_Seed= "CREATE TABLE IF NOT EXISTS Seed(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "CropId INT ,\n" +
                "Name varchar(250) ,\n" +
                "IsActive  BOOLEAN   ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate  DATETIME )";



        String CREATE_USER_INFO = "CREATE TABLE IF NOT EXISTS UserInfo(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "Code varchar(100),\n" +
                "UserId nvarchar(128) ,\n" +
                "FirstName varchar(50) ,\n" +
                "MiddleName varchar(50),\n" +
                "LastName varchar(50),\n" +
                "FatherName_GuardianName varchar(250),\n" +
                "Address1 nvarchar(100),\n" +
                "Address2 nvarchar(100),\n" +
                "StateId INT ,\n" +
                "DistrictId INT ,\n" +
                "MandalId INT ,\n" +
                "VillageId INT ,\n" +
                "GenderTypeId INT ,\n" +
                "DOB DATETIME  ,\n"+
                "PrimaryPhoneNumber varchar(15),\n" +
                "SecondaryPhoneNumber varchar(15) ,\n" +
                "AnnualIncomeTypeId INT ,\n" +
                "CategoryTypeId INT ,\n" +
                "Email varchar(100)  ,\n" +
                "EducationTypeId INT ,\n" +
                "EducationDegreeTypeId INT ,\n" +
                "SourceTypeId INT ,\n" +
                "PPFileName varchar(100)  ,\n" +
                "PPFileLocation varchar(250)  ,\n" +
                "PPFileExtension varchar(15)  ,\n" +
                "MAFileName varchar(100)  ,\n" +
                "MAFileLocation varchar(250)  ,\n" +
                "MAFileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN   ,\n" +
                "CreatedByUserId INT  , \n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT   ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "UserName varchar(50)  ,\n" +
                "Password varchar(50) ,\n" +
                "EmployeeTypeId INT ,\n" +
                "ReportingManagerId INT,\n" +
                "ExpInYears INT ,\n" +
                "ExpInMonths INT  ,\n"+
                "AccessToken NVARCHAR(2000)  ,\n"+
                "ServerUpdatedStatus BOOLEAN  ,\n"+
                "ProfilePicFileBytes VARCHAR  ,\n"+
                "MutualAgreementFileBytes VARCHAR   ,\n"+
                "IsNRI BOOLEAN  ,\n"+
                "CountryName VARCHAR  ,\n"+
                "PostalCode  VARCHAR  ,\n"+
                "IsWillingtoConvert  BOOLEAN )";




        String CREATE_TABLE_BANK_DETAILS= "CREATE TABLE IF NOT EXISTS BankDetails(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserCode VARCHAR(100) ,\n"+
                "BankId INT ,\n"+
                "BranchName varchar(100) ,\n" +
                "BPFileName varchar(100) ,\n" +
                "BPFileLocation varchar(250)  ,\n" +
                "BPFileExtension varchar(15)  ,\n" +
                "AccountHolderName varchar(250) ,\n" +
                "AccountNumber varchar(50) ,\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250) ,\n" +
                "FileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "FileBytes VARCHAR  ,\n"+
                "BankPassbookFileBytes VARCHAR   ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_IDENTITYPROOFS= "CREATE TABLE IF NOT EXISTS IdentityProofs(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserCode VARCHAR(100) ,\n"+
                "IdProofTypeId INT ,\n"+
                "IdProofNumber NVARCHAR(50),\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN   ,\n" +
                "IsVerified  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "fileBytes VARCHAR  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_PLOT= "CREATE TABLE IF NOT EXISTS Plot(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "Code VARCHAR(100)  ,\n" +
                "FarmerCode VARCHAR(10)  ,\n" +
                "TotalPlotArea float ,\n" +
                "AdaptedArea float ,\n" +
                "GPSPlotArea float ,\n" +
                "SurveyNumber varchar(500) ,\n" +
                "PassbookNumber varchar(500) ,\n" +
                "PlotOwnerShipTypeId INT  ,\n"+
                "PlotStatusId INT  ,\n"+
                "OwnerName varchar(150),\n" +
                "OwnerContactNumber varchar(15),\n" +
                "Address1 NVARCHAR(100)  ,\n" +
                "Address2 NVARCHAR(100)  ,\n" +
                "StateId INT ,\n"+
                "DistrictId INT ,\n"+
                "MandalId INT ,\n"+
                "VillageId INT ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_PLOTStatusHistory= "CREATE TABLE IF NOT EXISTS PlotStatusHistory(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode VARCHAR(100)  ,\n" +
                "PlotStatusId INT  ,\n"+
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_FileRepository= "CREATE TABLE IF NOT EXISTS FileRepository(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode VARCHAR(100)  ,\n" +
                "DocTypeId INT  ,\n"+
                "FileName VARCHAR(100)  ,\n" +
                "FileLocation VARCHAR(250)  ,\n" +
                "FileExtension VARCHAR(250)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_BANK_DETAILSHistory= "CREATE TABLE IF NOT EXISTS BankDetailsHistory(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "UserCode VARCHAR(100) ,\n"+
                "BankId INT ,\n"+
                "BranchName varchar(100) ,\n" +
                "BPFileName varchar(100) ,\n" +
                "BPFileLocation varchar(250)  ,\n" +
                "BPFileExtension varchar(15)  ,\n" +
                "AccountHolderName varchar(250) ,\n" +
                "AccountNumber varchar(50) ,\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250) ,\n" +
                "FileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";



        String CREATE_TABLE_SoilDetails= "CREATE TABLE IF NOT EXISTS SoilDetails(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode  VARCHAR  ,\n" +
                "SoilTypeId INTEGER  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_PowerDetails = "CREATE TABLE IF NOT EXISTS PowerDetails(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode  VARCHAR  ,\n" +
                "IsAvailable  BOOLEAN  ,\n" +
                "ServiceNumber VARCHAR  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_IrrigationDetails = "CREATE TABLE IF NOT EXISTS IrrigationDetails(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode  VARCHAR  ,\n" +
                "IrrigationTypeId INTEGER  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";



        String CreateI_Table_Notifications= "CREATE TABLE IF NOT EXISTS Notification(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserId INT ,\n"+
                "Desc NVARCHAR(1000)  ,\n" +
                "RaisedByUserId INTEGER  ,\n" +
                "IsRead  BOOLEAN  ,\n" +
                "NotificationTypeId INT ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "Header  VARCHAR  ,\n" +
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_BuyerDetails= "CREATE TABLE IF NOT EXISTS BuyerDetails(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserCode VARCHAR(100)  ,\n" +
                "BuyerTypeId INT  ,\n" +
                "GSTNumber VARCHAR(50) ,\n" +
                "CompanyName VARCHAR(500) ,\n" +
                "Address1 NVARCHAR(100) ,\n" +
                "Address2 NVARCHAR(100) ,\n" +
                "StateId INT ,\n" +
                "DistrictId INT  ,\n"+
                "MandalId INT  ,\n"+
                "VillageId INT  ,\n" +
                "ROCFileName VARCHAR(100),\n" +
                "ROCFileLocation VARCHAR(250)  ,\n" +
                "ROCFileExtension VARCHAR(15)  ,\n" +
                "CINNumber VARCHAR(50) ,\n"+
                "INCFileName VARCHAR(100) ,\n"+
                "INCFileLocation VARCHAR(250) ,\n"+
                "INCFileExtension VARCHAR(15) ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ROCFileBytes VARCHAR  ,\n"+
                "INCFileBytes VARCHAR   ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_ActivityLog= "CREATE TABLE IF NOT EXISTS ActivityLog(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "FarmerCode VARCHAR(100),\n" +
                "PlotCode  VARCHAR(100),\n" +
                "ActivityTypeId INT ,\n" +
                "CreatedByUserId INT  , \n"+
                "CreatedDate DATETIME ,\n"+
                "ServerUpdatedStatus BOOLEAN )";

        String CreateI_Table_GeoBoundaries= "CREATE TABLE IF NOT EXISTS GeoBoundaries(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "PlotCode varchar(1000)  ,\n" +
                "Latitude float  ,\n" +
                "Longitude  float  ,\n" +
                "GeoCategoryTypeId INT ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CreateI_Table_LocationTracker= "CREATE TABLE IF NOT EXISTS LocationTracker(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserId INT ,\n"+
                "Latitude float  ,\n" +
                "Longitude  float  ,\n" +
                "Address NVARCHAR ,\n" +
                "LogDate DATETIME ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CreateI_Table_UserRoleXref= "CREATE TABLE IF NOT EXISTS UserRoleXref(\n" +
                "Id INTEGER   \n" +
                "                                      ,\n" +
                "UserId INT ,\n"+
                "RoleId INT  ,\n"+
                "StatusTypeId  INT   ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_SOILTEST_DETAILS= "CREATE TABLE IF NOT EXISTS SoilTestDetails(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "PlotCode VARCHAR(100) ,\n"+
                "CultureTypeId INT ,\n"+
                "Nitrogen DECIMAL(12,9) ,\n" +
                "Prosperous DECIMAL(12,9) ,\n" +
                "Potassium DECIMAL (12,9)  ,\n" +
                "Carbon DECIMAL(12,9)  ,\n" +
                "Hydrogen DECIMAL(12,9) ,\n" +
                "Oxygen DECIMAL(12,9) ,\n" +
                "Sulphur DECIMAL(12,9)  ,\n" +
                "Calcium DECIMAL(12,9) ,\n" +
                "Magnesium DECIMAL(12,9)  ,\n" +
                "FileName VARCHAR(100)  ,\n" +
                "FileLocation VARCHAR(250)  ,\n" +
                "FileExtension VARCHAR(250)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "FileBytes VARCHAR  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";
        String CREATE_TABLE_Complaints= "CREATE TABLE IF NOT EXISTS Complaints(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "Code VARCHAR(100) ,\n"+
                "PlotCode VARCHAR(100) ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_VendorDetails= "CREATE TABLE IF NOT EXISTS VendorDetails(\n" +
                "Id INTEGER \n" +
                " ,\n" +
                "UserCode VARCHAR(100) ,\n" +
                "VendorTypeId INT ,\n" +
                "VendorCategoryId INT ,\n" +
                "GSTNumber VARCHAR(50) ,\n" +
                "CompanyName VARCHAR(500) ,\n" +
                "Address1 NVARCHAR(100) ,\n" +
                "Address2 NVARCHAR(100) ,\n" +
                "StateId INT ,\n" +
                "DistrictId INT ,\n"+
                "MandalId INT ,\n"+
                "VillageId INT ,\n" +
                "ROCFileName VARCHAR(100),\n" +
                "ROCFileLocation VARCHAR(250) ,\n" +
                "ROCFileExtension VARCHAR(15) ,\n" +
                "CINNumber VARCHAR(50) ,\n"+
                "INCFileName VARCHAR(100) ,\n"+
                "INCFileLocation VARCHAR(250) ,\n"+
                "INCFileExtension VARCHAR(15) ,\n"+
                "IsActive BOOLEAN ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME ,\n"+
                "ROCFileBytes VARCHAR ,\n"+
                "INCFileBytes VARCHAR ,\n"+
                "ServerUpdatedStatus BOOLEAN )";
//
        String CREATE_TABLE_ComplaintRepository= "CREATE TABLE IF NOT EXISTS ComplaintRepository(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "ComplaintCode VARCHAR(100) ,\n"+
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtension varchar(25)  ,\n" +
                "IsAudioRecording  BOOLEAN  ,\n" +
                "IsResult  BOOLEAN  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "FileBytes VARCHAR  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_ComplaintStatusHistory = "CREATE TABLE IF NOT EXISTS ComplaintStatusHistory(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "ComplaintCode VARCHAR(100) ,\n"+
                "StatusTypeId INTEGER  ,\n" +
                "AssigntoUserId INTEGER  ,\n" +
                "Comments varchar(25)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";
//

        String CREATE_TABLE_ComplaintTypeXref = "CREATE TABLE IF NOT EXISTS ComplaintTypeXref(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "ComplaintCode VARCHAR(100) ,\n"+
                "ComplaintTypeId INTEGER  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_InsuranceDetails= "CREATE TABLE IF NOT EXISTS InsuranceDetails(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "CropCycleCode VARCHAR(100) ,\n"+
                "ProviderId INT  ,\n" +
                "InsurancePlan VARCHAR(250) ,\n"+
                "StartDate DATETIME ,\n"+
                "EndDate DATETIME ,\n"+
                "SumIssued float ,\n" +
                "PremiumAmount float ,\n" +
                "FarmerPencentage float ,\n" +
                "VFarmerPencentage float ,\n" +
                "BondNumber VARCHAR(100) ,\n"+
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtention varchar(25)  ,\n" +
                "StatusTypeId INT  ,\n" +
                "Comments VARCHAR(250)  ,\n" +
                "StatusChangedBy INT  ,\n" +
                "StatusChangedDate DATETIME ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "FileBytes VARCHAR ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String CREATE_TABLE_PlotCropCycle = "CREATE TABLE IF NOT EXISTS PlotCropCycle(\n" +
                "Id INTEGER  \n" +
                "                                      ,\n" +
                "PlotCode VARCHAR(100) ,\n"+
                "CycleCode VARCHAR(100) ,\n"+
                "StatusTypeId INT  ,\n" +
                "CropId INT  ,\n" +
                "MinDuration INT  ,\n" +
                "MaxDuration INT  ,\n" +
                "Amount float ,\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtention varchar(25)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";

        String ALTER_TABLE_UserRoleXref = "ALTER TABLE UserRoleXref ADD UpdatedDate DATETIME NULL";


        try {
            db.execSQL(CREATE_Class_Type);
            db.execSQL(CREATE_TYPECDDMT);
            db.execSQL(CREATE_LOOKUP);
            db.execSQL(CREATE_COUNTRY);
            db.execSQL(CREATE_STATE);
            db.execSQL(CREATE_DISTRICT);
            db.execSQL(CREATE_MANDAL);
            db.execSQL(CREATE_VILLAGE);
            db.execSQL(CREATE_BANK);
            db.execSQL(CREATE_Role);
            db.execSQL(CREATE_InsuranceProvider);
            db.execSQL(CREATE_CropInfo);
            db.execSQL(CREATE_Seed);
            db.execSQL(CREATE_USER_INFO);
            db.execSQL(CREATE_TABLE_BANK_DETAILS);
            db.execSQL(CREATE_TABLE_IDENTITYPROOFS);
            db.execSQL(CREATE_TABLE_PLOT);
            db.execSQL(CREATE_TABLE_PLOTStatusHistory);
            db.execSQL(CREATE_TABLE_FileRepository);
            db.execSQL(CREATE_TABLE_BANK_DETAILSHistory);
            db.execSQL(CREATE_TABLE_SoilDetails);
            db.execSQL(CREATE_TABLE_PowerDetails);
            db.execSQL(CREATE_TABLE_IrrigationDetails);
            db.execSQL(CreateI_Table_Notifications);
            db.execSQL(CreateI_Table_GeoBoundaries);
            db.execSQL(CreateI_Table_UserRoleXref);
            db.execSQL(CREATE_TABLE_BuyerDetails);
            db.execSQL(CREATE_TABLE_ActivityLog);
            db.execSQL(CreateI_Table_LocationTracker);
            db.execSQL(CREATE_TABLE_SOILTEST_DETAILS);
            db.execSQL(CREATE_TABLE_Complaints);
            db.execSQL(CREATE_TABLE_VendorDetails);
            db.execSQL(CREATE_TABLE_ComplaintRepository);
            db.execSQL(CREATE_TABLE_ComplaintStatusHistory);
            db.execSQL(CREATE_TABLE_ComplaintTypeXref);
            db.execSQL(CREATE_TABLE_InsuranceDetails);
            db.execSQL(CREATE_TABLE_PlotCropCycle);
            db.execSQL(ALTER_TABLE_UserRoleXref);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb2(final SQLiteDatabase db) {



//
    }

    public static void upgradeDb3(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase " + EasyFarmDatabse.DATA_VERSION);


        String CREATE_TABLE_BANK_DETAILS= "CREATE TABLE BankDetails(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "UserCode VARCHAR(100) ,\n"+
                "BankId INT ,\n"+
                "BranchName varchar(100) ,\n" +
                "BPFileName varchar(100) ,\n" +
                "BPFileLocation varchar(250)  ,\n" +
                "BPFileExtension varchar(15)  ,\n" +
                "AccountHolderName varchar(250) ,\n" +
                "AccountNumber varchar(50) ,\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250) ,\n" +
                "FileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";



        String CREATE_TABLE_IDENTITYPROOFS= "CREATE TABLE IdentityProofs(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "UserCode VARCHAR(100) ,\n"+
                "IdProofTypeId INT ,\n"+
                "IdProofNumber NVARCHAR(50),\n" +
                "FileName varchar(100)  ,\n" +
                "FileLocation varchar(250)  ,\n" +
                "FileExtension varchar(15)  ,\n" +
                "IsActive  BOOLEAN   ,\n" +
                "IsVerified  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";

        try {
            db.execSQL(CREATE_TABLE_BANK_DETAILS);
            db.execSQL(CREATE_TABLE_IDENTITYPROOFS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb4(final SQLiteDatabase db) {
        String CREATE_TABLE_PLOT= "CREATE TABLE Plot(\n" +
                "Id INTEGER   \n" +
                "                                     ,\n" +
                "Code VARCHAR(100)  ,\n" +
                "FarmerCode VARCHAR(10)  ,\n" +
                "TotalPlotArea float ,\n" +
                "AdaptedArea float ,\n" +
                "GPSPlotArea float ,\n" +
                "SurveyNumber varchar(500) ,\n" +
                "PassbookNumber varchar(500) ,\n" +
                "PlotOwnerShipTypeId INT  ,\n"+
                "OwnerName varchar(150),\n" +
                "OwnerContactNumber varchar(15),\n" +
                "PlotStatusId INT  ,\n"+
                "Address1 NVARCHAR(100)  ,\n" +
                "Address2 NVARCHAR(100)  ,\n" +
                "StateId INT ,\n"+
                "DistrictId INT ,\n"+
                "MandalId INT ,\n"+
                "VillageId INT ,\n"+
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  )";


        String CREATE_TABLE_PLOTStatusHistory= "CREATE TABLE PlotStatusHistory(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "PlotCode VARCHAR(100)  ,\n" +
                "PlotStautusId INT  ,\n"+
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";


        String CREATE_TABLE_FileRepository= "CREATE TABLE FileRepository(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "PlotCode VARCHAR(100)  ,\n" +
                "DocTypeId INT  ,\n"+
                "FileName VARCHAR(100)  ,\n" +
                "FileLocation VARCHAR(250)  ,\n" +
                "FileExtension VARCHAR(250)  ,\n" +
                "IsActive  BOOLEAN  ,\n" +
                "CreatedByUserId INT ,\n"+
                "CreatedDate DATETIME ,\n"+
                "UpdatedByUserId INT ,\n"+
                "UpdatedDate DATETIME  ,\n"+
                "ServerUpdatedStatus  BOOLEAN )";





        try {
            db.execSQL(CREATE_TABLE_PLOT);
            db.execSQL(CREATE_TABLE_PLOTStatusHistory);
            db.execSQL(CREATE_TABLE_FileRepository);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb6(final SQLiteDatabase db) {
//        Log.d(LOG_TAG, "******* upgradeDataBase 6 " + EasyFarmDatabse.DATA_VERSION);
//
//        String alertPlotTable7 = "ALTER TABLE Plot ADD COLUMN IsRetakeGeoTagRequired INTEGER DEFAULT 0";
//
//        try {
//
//            db.execSQL(alertPlotTable7);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public static void upgradeDb7(final SQLiteDatabase db) {
//        Log.d(LOG_TAG, "******* upgradeDataBase 7 " + EasyFarmDatabse.DATA_VERSION);
//        String alterPestTable1 = "ALTER TABLE Pest ADD COLUMN RecommendedChemicalId INT  NULL ";
//        String alterPestTable2 = "ALTER TABLE Pest ADD COLUMN Dosage  Float Null";
//        String alterPestTable3 = "ALTER TABLE Pest ADD COLUMN UOMId  INT NULL";
//        String alterNutrientTable1 = "ALTER TABLE Nutrient ADD COLUMN RecommendedFertilizerId INT  NULL";
//        String alterNutrientTable2 = "ALTER TABLE Nutrient ADD COLUMN Dosage  Float Null";
//        String alterNutrientTable3 = "ALTER TABLE Nutrient ADD COLUMN UOMId  INT NULL";
//        String alterDiseaseTable1 = "ALTER TABLE Disease ADD COLUMN RecommendedChemicalId INT  NULL";
//        String alterDiseaseTable2 = "ALTER TABLE Disease ADD COLUMN Dosage  Float Null";
//        String alterDiseaseTable3 = "ALTER TABLE Disease ADD COLUMN UOMId  INT NULL";
//
//        String FertilizerRecommendationsTable="CREATE TABLE FertilizerRecommendations(        " +
//                "    Id             INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
//                "    PlotCode       VARCHAR (60)   NOT NULL,\n" +
//                "    CropMaintenanceCode            Varchar(60)            NOT NULL ,\n" +
//                "    RecommendedFertilizerId            INTEGER             ,\n" +
//                "    Dosage        Float              NULL ,\n" +
//                "    UOMId            INTEGER            NULL ,\n" +
//                "    Comments            VARCHAR (150),\n" +
//                "    IsActive            BOOLEAN            NOT NULL,\n" +
//                "    CreatedByUserId     INT            NOT NULL,\n" +
//                "    CreatedDate         VARCHAR       NOT NULL,\n" +
//                "    UpdatedByUserId     INT            NOT NULL,\n" +
//                "    UpdatedDate         VARCHAR       NOT NULL,\n" +
//                "    ServerUpdatedStatus BOOLEAN            DEFAULT 0\n" +
//
//                ");";
//
//
//        String BenchMarkTable="CREATE TABLE Benchmark(        " +
//                "    Id                  INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
//                "    Year                   INT        NOT NULL,\n" +
//                "    MonthName            Varchar(10)  NOT NULL ,\n" +
//                "    MonthSequenceNumber    INT        NOT NULL ,\n" +
//                "    MonthlyPercentage      FLOAT      NOT NULL ,\n" +
//                "    Age                    INT        NOT NULL,\n" +
//                "    YieldPerHectar        FLOAT       NOT NULL,\n" +
//                "    StateId                INT            NULL\n" +
//                ");";
//
//        String alterActivityLog = "ALTER TABLE ActivityLog  ADD COLUMN ConsignmentCode   Varchar(50) NULL ";
//
//        String DropHarvestTable = "Drop table Harvest";
//        db.execSQL(DropHarvestTable);
//
//        String createHarvestTable = "CREATE TABLE Harvest (         "+
//                "   Id                     INTEGER       PRIMARY KEY AUTOINCREMENT,\n"+
//                "   PlotCode               VARCHAR (50),\n"+
//                "   PlotYield              FLOAT,\n"+
//                "   YieldPerHactor         FLOAT,\n"+
//                "   CollectionCenterId     INT,\n"+
//                "   TransportModeTypeId    INT,\n"+
//                "   VehicleTypeId          INT,\n"+
//                "   TransportPaidAmount    INT,\n"+
//                "   HarvestingMethodTypeId INT,\n"+
//                "   WagesPerDay            FLOAT         NOT NULL,\n"+
//                "   HarvestingTypeId       INT,\n"+
//                "   Comments               VARCHAR (150),\n"+
//                "   IsActive               BIT           NOT NULL,\n"+
//                "   CreatedByUserId        INT,\n"+
//                "   CreatedDate            VARCHAR       NOT NULL,\n"+
//                "   UpdatedByUserId        INT,\n"+
//                "   UpdatedDate            VARCHAR       NOT NULL,\n"+
//                "   ServerUpdatedStatus    INT,\n"+
//                "   CropMaintenanceCode    VARCHAR,\n"+
//                "   WagesUnitTypeId        INT          NULL , \n"+
//                "   ContractAmount         FLOAT          NULL   \n"+
//                ");";
//
//        try {
//
//            db.execSQL(alterPestTable1);
//            db.execSQL(alterPestTable2);
//            db.execSQL(alterPestTable3);
//            db.execSQL(alterNutrientTable1);
//            db.execSQL(alterNutrientTable2);
//            db.execSQL(alterNutrientTable3);
//            db.execSQL(alterDiseaseTable1);
//            db.execSQL(alterDiseaseTable2);
//            db.execSQL(alterDiseaseTable3);
//            db.execSQL(FertilizerRecommendationsTable);
//            db.execSQL(BenchMarkTable);
//            db.execSQL(alterActivityLog);
//            db.execSQL(createHarvestTable);
//
//
//
//            /*db.execSQL(alterDiseaseTable);*/
//
//
//        } catch (Exception e) {
//            Log.i("DataBaseUpgrade","error Updating database 7-->"+EasyFarmDatabse.DATA_VERSION+e.getMessage());
//
//            e.printStackTrace();
        //    }
    }

    public static void upgradeDb8(final SQLiteDatabase db){
//        Log.d(LOG_TAG,"****** upgradeDataBase 8 "+ EasyFarmDatabse.DATA_VERSION);
//
//        String drop_Plot_Table = "Drop table Plot";
//        db.execSQL(drop_Plot_Table);
//        String create_Plot_table = "CREATE TABLE Plot   (          "+
//                " Id                          INTEGER       PRIMARY KEY,\n"+
//                " Code                        VARCHAR (50)  NOT NULL,\n"+
//                " FarmerCode                  VARCHAR (50)  NOT NULL,\n"+
//                " IsBoundryFencing            BOOLEAN,\n"+
//                " TotalPlotArea               DECIMAL       NOT NULL,\n"+
//                " TotalPalmArea               DECIMAL,\n"+
//                " GPSPlotArea                 DECIMAL,\n"+
//                " CropIncomeTypeId            INT,\n"+
//                " AddressCode                 VARCHAR (60)  NOT NULL,\n"+
//                " SurveyNumber                VARCHAR (30),\n"+
//                " AdangalNumber               VARCHAR (30),\n"+
//                " LeftOutArea                 DECIMAL,\n"+
//                " LeftOutAreaCropId           INT,\n"+
//                " PlotOwnerShipTypeId         INT,\n"+
//                " IsPlotHandledByCareTaker    INT           DEFAULT (NULL),\n"+
//                " CareTakerName               VARCHAR (150),\n"+
//                " CareTakerContactNumber      VARCHAR (15),\n"+
//                " IsActive                    BOOLEAN       NOT NULL,\n"+
//                " CreatedByUserId             INT           NOT NULL,\n"+
//                " CreatedDate                 VARCHAR       NOT NULL,\n"+
//                " UpdatedByUserId             INT           NOT NULL,\n"+
//                " UpdatedDate                 VARCHAR       NOT NULL,\n"+
//                " ServerUpdatedStatus         BOOLEAN       NOT NULL  DEFAULT (0),\n"+
//                " Comments                    VARCHAR (500),\n"+
//                " IsPLotSubsidySubmission     BOOLEAN,\n"+
//                " IsPLotHavingIdCard          BOOLEAN,\n"+
//                " IsGeoBoundariesVerification BOOLEAN,\n"+
//                " SuitablePalmOilArea         FLOAT,\n"+
//                " DateofPlanting              VARCHAR,\n"+
//                " SwapingReasonId             INTEGER,\n"+
//                " OriginCode                  VARCHAR (60),\n"+
//                " ReasonTypeId                INT,\n"+
//                " InactivatedByUserId         INT,\n"+
//                " InactivatedDate             DATETIME,\n"+
//                " InActiveReasonTypeId        INT,\n"+
//                " PlansToPlanInFuture         BOOLEAN       DEFAULT NULL,\n"+
//                " IsRetakeGeoTagRequired      INTEGER       DEFAULT 0\n"+
//                ");";
//
//        String crate_Table_AdvancedDetails =  "CREATE TABLE AdvancedDetails(    "+
//                " Id                                INTEGER     PRIMARY KEY AUTOINCREMENT ,\n"+
//                " PlotCode                         VARCHAR(50)             NOT NULL,\n"+
//                " AdvanceAmountReceived              DOUBLE,\n"+
//                " DateOfAdvanceReceived             DATETIME,\n"+
//                " ExpectedMonthOfPlanting           DATETIME,\n"+
//                " NoOfSaplingsAdvancePaidFor         INT                    NULL,\n"+
//                " NoOfImportedSaplingsToBeIssued     INT                    NULL,\n"+
//                " NoOfIndigenousSaplingsToBeIssued   INT                    NULL, \n"+
//                " AdvanceReceivedArea               FLOAT                   NULL,\n"+
//                " SurveyNumber                     VARCHAR(50)              NULL,\n"+
//                " ReceiptNumber                    VARCHAR(50)              NULL,\n"+
//                " Comments                         VARCHAR(500)             NULL,\n"+
//                " CreatedByUserId                     INT                 NOT NULL,\n"+
//                " CreatedDate                      DATETIME               NOT NULL,\n"+
//                " UpdatedByUserId                    INT                  NOT NULL,\n"+
//                " UpdatedDate                      DATETIME               NOT NULL\n"+
//                " );";
//
//        String Create_Table_NurserySaplingDetails =  "CREATE TABLE NurserySaplingDetails("+
//                " Id                                  INTEGER       PRIMARY KEY AUTOINCREMENT ,\n"+
//                " PlotCode                          VARCHAR(50)                     NOT NULL,\n"+
//                " SaplingPickUpDate                 DATETIME                        NOT NULL,\n"+
//                " NoOfSaplingsDispatched              INT                           NOT NULL,\n"+
//                " NoOfImportedSaplingsDispatched      INT                           NOT NULL,\n"+
//                " NoOfIndigenousSaplingsDispatched    INT                           NOT NULL,\n"+
//                " ReceiptNumber                     VARCHAR(50)                     NOT NULL,\n"+
//                " CreatedByUserId                     INT                           NOT NULL,\n"+
//                " CreatedDate                       DATETIME                        NOT NULL,\n"+
//                " UpdatedByUserId                     INT                           NOT NULL,\n"+
//                " UpdatedDate                       DATETIME                        NOT NULL\n"+
//                ");";
//
//        String CollectionCenter_Table1 = "ALTER TABLE CollectionCenter ADD COLUMN Latitude   FLOAT";
//        String CollectionCenter_Table2 = "ALTER TABLE CollectionCenter ADD COLUMN Longitude  FLOAT";
//
//
//        try {
//            db.execSQL(create_Plot_table);
//            db.execSQL(crate_Table_AdvancedDetails);
//            db.execSQL(Create_Table_NurserySaplingDetails);
//            db.execSQL(CollectionCenter_Table1);
//            db.execSQL(CollectionCenter_Table2);
//        }catch (Exception e){
//
//            Log.i("DataBaseUpgrade","error Updating database 8-->"+EasyFarmDatabse.DATA_VERSION+e.getMessage());
//            e.printStackTrace();
//        }
//
    }


}
