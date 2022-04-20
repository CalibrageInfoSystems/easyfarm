package com.cis.easyfarm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class EasyFarmDatabse extends SQLiteOpenHelper {


    public static final String LOG_TAG = EasyFarmDatabse.class.getName();
    public final static int DATA_VERSION = 8;
    private final static String DATABASE_NAME = "easyfarm.sqlite";
    public static String Lock = "dblock";
    private static EasyFarmDatabse palm3FoilDatabase;
    private static String DB_PATH;
    private static SQLiteDatabase mSqLiteDatabase = null;
    private Context mContext;
    File rootDirectory;




    public EasyFarmDatabse(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
        this.mContext = context;
        File dbDirectory = new File(CommonUtils.get3FFileRootPath() + "easyfarm_Database");
        DB_PATH = dbDirectory.getAbsolutePath() + File.separator;
        Log.v("The Database Path", DB_PATH);
        CommonUtils.appendLog(LOG_TAG, "EasyFarmDatabse",  DB_PATH);
}

    public static synchronized EasyFarmDatabse getEasyFarmDatabase(Context context) {
        synchronized (Lock) {
            if (palm3FoilDatabase == null) {
                palm3FoilDatabase = new EasyFarmDatabse(context);
            }
            return palm3FoilDatabase;
        }

    }

    public static SQLiteDatabase openDataBaseNew() throws SQLException {
        // Open the database
        if (mSqLiteDatabase == null) {
            mSqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } else if (!mSqLiteDatabase.isOpen()) {
            mSqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }
        return mSqLiteDatabase;
    }

//    public static void copy(File src, File dst) throws IOException {
//        InputStream in = new FileInputStream(src);
//        OutputStream out = new FileOutputStream(dst);
//
//        // Transfer bytes from in to out
//        byte[] buf = new byte[1024];
//        int len;
//        while ((len = in.read(buf)) > 0) {
//            out.write(buf, 0, len);
//        }
//        in.close();
//        out.close();
//    }

    /* create an empty database if data base is not existed */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

            Log.d("SplashActivity","===> Analysis ==>  createDataBase ==> Database Already Exist ");

            String check_Path = DB_PATH + DATABASE_NAME;

          //  Log.e("database=============="," database already exist");
           // this.deleteDatabase(check_Path);

            //do nothing - database already exist
//            try{
//                File dbDirectory = new File(DB_PATH);
//
//                if (dbDirectory.isDirectory())
//                {
//                    Log.d("SplashActivity","===> Analysis ==> Delete Databse Folders info Start Deleting Directory");
//                    String[] children = dbDirectory.list();
//                    for (int i = 0; i < children.length; i++)
//                    {
//                        new File(dbDirectory, children[i]).delete();
//                        Log.d("SplashActivity","===> Analysis ==> Delete Databse Folders info :"+children[i]);
//                    }
//                }
//                dbDirectory.delete();
//            }catch (Exception e) {
//                Log.e("SplashActivity","===> Analysis ==> Delete Databse Folders Errror :"+e.getMessage());
//                e.getMessage();
//            }
        } else {

            Log.d("SplashActivity","===> Analysis ==>  createDataBase==> Database Already NOT Exist ");

            Log.e("database==============","new");

            try {
                copyDataBase();
                Log.v("dbcopied:::", "true");

            } catch (SQLiteException ex) {
                ex.printStackTrace();
                throw new Error("Error copying database");
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
            try {
                openDataBase();
            } catch (SQLiteException ex) {
                ex.printStackTrace();
                throw new Error("Error opening database");
            } catch (Exception e) {
                e.printStackTrace();
                throw new Error("Error opening database");
            }
        }

    }

    /* checking the data base is existed or not */
    /* return true if existed else return false */
    private boolean checkDataBase() {
        boolean dataBaseExisted = false;
        try {
            String check_Path = DB_PATH + DATABASE_NAME;
            mSqLiteDatabase = SQLiteDatabase.openDatabase(check_Path, null, SQLiteDatabase.OPEN_READWRITE);
            File dbfile = new File(check_Path);
            dataBaseExisted = dbfile.exists();

            Log.e("database=============",dataBaseExisted+"");
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
            dataBaseExisted = false;
        }
        return mSqLiteDatabase != null;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void copyDataBase() throws IOException {
        File dbDir = new File(DB_PATH);
        if (!dbDir.exists()) {
            dbDir.mkdir();

        }
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DATABASE_NAME);
        copyFile(myInput, myOutput);

        try {
            String check_Path = DB_PATH + DATABASE_NAME;
            mSqLiteDatabase = SQLiteDatabase.openDatabase(check_Path, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d("SplashActivity","===> Analysis ==> copyDataBase Databse with out error Path:"+check_Path);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.e("SplashActivity","===> Analysis ==> copyDataBase Databse Errror :"+e.getMessage());

        }


    }

//    public boolean UpdateGeoTagLatLng(String UpdatedByUserId,  String UpdatedDate,double Latitude,double Longitude) {
////        SQLiteDatabase db = this.getWritableDatabase();
//
//        try {
//            openDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("UpdatedByUserId",UpdatedByUserId);
//            contentValues.put("UpdatedDate",UpdatedDate);
//            contentValues.put("Latitude",Latitude);
//            contentValues.put("Longitude",Longitude);
//            contentValues.put("ServerUpdatedStatus",0);
//
//            mSqLiteDatabase.update(DatabaseKeys.TABLE_GEOBOUNDARIES, contentValues,
//                    "PlotCode" + "=?" + " and "  +
//                            "GeoCategoryTypeId" + "=?",
//                    new String[] {
//                            CommonConstants.PLOT_CODE,
//                            "207"}) ;
////            mSqLiteDatabase.update(DatabaseKeys.TABLE_GEOBOUNDARIES, contentValues, " PlotCode = " + CommonConstants.PLOT_CODE + " AND " + " GeoCategoryTypeId = 207 ", null);
////            mSqLiteDatabase.insert(DatabaseKeys.TABLE_Location_TRACKING_DETAILS, null, contentValues);
//            Log.v("userdata","data for user"+contentValues);
//        }catch (Exception e){
//            Log.v("ReTagDeoTag","Data Update failed due to"+e);
//        }
//
//        return true;
//    }
//
//    public boolean UpdateFarmerhistory(String UpdatedByUserId,  String UpdatedDate,String IsActive) {
////        SQLiteDatabase db = this.getWritableDatabase();
//
//        try {
//            openDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("UpdatedByUserId",UpdatedByUserId);
//            contentValues.put("UpdatedDate",UpdatedDate);
//            contentValues.put("IsActive",IsActive);
//
//            mSqLiteDatabase.update("FarmerHistory", contentValues, "PlotCode = " + CommonConstants.PLOT_CODE + " AND IsActive = 1", null);
////            mSqLiteDatabase.insert(DatabaseKeys.TABLE_Location_TRACKING_DETAILS, null, contentValues);
//            Log.v("userdata","data for user"+contentValues);
//        }catch (Exception e){
//            Log.v("Farmer History","Data Update failed due to"+e);
//        }
//
//        return true;
//    }
//
//
    //FLOG_TRACKING......
    public boolean insertLatLong (double Latitude, double Longitude, String address, String UpdatedDate, String ServerUpdatedStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();

        try {
            openDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Id", 0);
            contentValues.put("UserId", SyncHomeActivity.User_id);
            contentValues.put("Latitude", Latitude);
            contentValues.put("Longitude", Longitude);
            contentValues.put("Address", address);
            contentValues.put("LogDate", UpdatedDate);
            contentValues.put("ServerUpdatedStatus", 0);
//            contentValues.put("UserId",CreatedByUserId);
//            contentValues.put(DatabaseKeys.LATITUDE,Latitude);
//            contentValues.put(DatabaseKeys.LONGITUDE,Longitude);
//            contentValues.put("Address", "Testin");
//            contentValues.put("LogDate",UpdatedDate);
//            contentValues.put("ServerUpdatedStatus",0);


            mSqLiteDatabase.insert(DatabaseKeys.TABLE_Location_TRACKING_DETAILS, null, contentValues);
            Log.v("userdata","data for user"+contentValues);
        }catch (Exception e){
            Log.v("UserDetails","Data insert failed due to"+e);
        }

        return true;
    }

    /* Open the database */
    public void openDataBase() throws SQLException {

        String check_Path = DB_PATH + DATABASE_NAME;
        if (mSqLiteDatabase != null) {
            mSqLiteDatabase.close();
            mSqLiteDatabase = null;
            mSqLiteDatabase = SQLiteDatabase.openDatabase(check_Path, null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            mSqLiteDatabase = SQLiteDatabase.openDatabase(check_Path, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


     //   db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY, "+ COLUMN_NAME+" TEXT, "+COLUMN_COUNTRY+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        if(mSqLiteDatabase != null && mSqLiteDatabase.isOpen()) {
            return;
        }
        mSqLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public boolean UpdateFarmerhistory(String gps,  String UpdatedDate,String IsActive) {
//        SQLiteDatabase db = this.getWritableDatabase();

        try {
            openDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("GPSPlotArea",gps);


            mSqLiteDatabase.update("Plot", contentValues, "Code = " + "APGNTRAL00100001"+ " AND IsActive = 1", null);
//            mSqLiteDatabase.insert(DatabaseKeys.TABLE_Location_TRACKING_DETAILS, null, contentValues);
            Log.v("userdata","data for user"+contentValues);
        }catch (Exception e){
            Log.v("Farmer History","Data Update failed due to"+e);
        }

        return true;
    }

}
