package com.cis.easyfarm.ui.userAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cis.easyfarm.Objects.User;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApiService;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.cloudHelper.Config;
import com.cis.easyfarm.cloudHelper.HttpClient;

import com.cis.easyfarm.cloudHelper.ServiceFactory;
import com.cis.easyfarm.common.CommonActivity;

import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.Constants;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.EasyFarmDatabse;
import com.cis.easyfarm.localData.SharedPrefsData;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.model.Loginobject;
import com.cis.easyfarm.service.FalogService;
import com.cis.easyfarm.sync.DataSynchelper;


import com.cis.easyfarm.ui.SignUpActicity;
import com.cis.easyfarm.ui.sync.SyncHomeActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends CommonActivity {
    private static final String LOG_TAG = LoginActivity.class.getName();
    Button login;
    EditText username, password;
    TextView signup;
    TextInputLayout name_layout,pass_label;
    private SpotsDialog mdilogue;
    String IsSuccess,end_user;
    TextView changepassword,forgot_password;
    //Declaration SqliteHelper
    EasyFarmDatabse sqliteHelper;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setViews();
        sqliteHelper = new EasyFarmDatabse(this);
    }
    private void init() {
        changepassword =findViewById(R.id.changepassword);
        forgot_password = findViewById(R.id.forgot_password);
        name_layout =findViewById(R.id.name_layout);
        pass_label =findViewById(R.id.pass_label);
        login = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username_edittxt);
        password = findViewById(R.id.pass_edittxt);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }
    private void setViews() {
        String UserName = username.getText().toString();
        String Password = password.getText().toString();

        sqliteHelper = new EasyFarmDatabse(this);

        //  sqliteHelper.addUser(new User(null, UserName, Password));
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validations()) {
                   user_login();
                }


//                    mdilogue.show();
//                    final JSONObject requestObject = new JSONObject();
//                    try {
//
//                        requestObject.put("UserName", username.getText().toString());
//                        requestObject.put("Password", password.getText().toString());
//
////                        requestObject.put("UserName", "mahesh");
////                        requestObject.put("Password", "Abcd@123");
//
//                    } catch (JSONException e) {
//                        Log.e(LOG_TAG, "" + e.getMessage());
//                    }
//                    loginWithCloud(requestObject, Config.live_url+Config.login_url, new ApplicationThread.OnComplete<String>() {
//                        @Override
//                        public void execute(boolean success, String result, String msg) {
//                            mdilogue.dismiss();
////                            super.execute(success, result, msg);
//                            Log.v(LOG_TAG,"is Success :"+success +"\n"+ "result :"+result+"\n"+"msg :"+msg);
//                            JSONObject myJson = null;
//                            try {
//                                myJson = new JSONObject(result);
//                                Log.e(LOG_TAG, "===============" + myJson);
//
//                                 end_user = myJson.optString("EndUserMessage");
//                                IsSuccess= myJson.optString("IsSuccess");
//
//                                JSONObject userInfos =myJson.optJSONObject("userInfos");
//
//                                HashMap<String, Object> yourHashMap = new Gson().fromJson(myJson.toString(), HashMap.class);
//                                HashMap<String, Object> yourHashMapUSER = yourHashMap.get("userInfos"[])
//                                //  j userInfos = myJson.getJSONArray("userInfos");
//                                Log.e(LOG_TAG, "===============userInfos" + userInfos);
//
//                                Log.e(LOG_TAG, "===============userid" + userid);
//                               Log.e("IsSuccess==",IsSuccess);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//
//                            if(IsSuccess.equalsIgnoreCase("true") && IsSuccess.contains("true")){
//                                Intent intent = new Intent(getApplicationContext(), SyncHomeActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(intent);
//                                finish();
//
//                            }else{
//                                UiUtils.showCustomToastMessage(end_user, LoginActivity.this, 1);
//                            }
//
//                        }
//                    });
//                }
            }

            private void crashnow() {

                throw new RuntimeException("App Crashed");
            }


            private void user_login() {
                mdilogue.show();
                JsonObject object = loginObject();
                ApiService service = ServiceFactory.createRetrofitService(LoginActivity.this, ApiService.class);
                mSubscription = service.getLoginPage(object)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginResponse>() {
                            @Override
                            public void onCompleted() {
                                mdilogue.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    ((HttpException) e).code();
                                    ((HttpException) e).message();
                                    ((HttpException) e).response().errorBody();
                                    try {
                                        ((HttpException) e).response().errorBody().string();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                        CommonUtils.appendLog(LOG_TAG, "Login API", e1.getMessage());

                                    }
                                    e.printStackTrace();
                                    CommonUtils.appendLog(LOG_TAG, "Login API", e.getMessage());

                                }
                                mdilogue.dismiss();
                                showDialog(LoginActivity.this, getString(R.string.server_error));
                            }

                            @Override
                            public void onNext(final LoginResponse loginResponse) {
                                mdilogue.dismiss();
                                if (loginResponse.getIsSuccess()) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            /* Create an Intent that will start the Menu-Activity. */
                                            SharedPrefsData.putBool(LoginActivity.this, Constants.IS_LOGIN, true);
                                            SharedPrefsData.saveCreatedUser(LoginActivity.this, loginResponse);
                                            SharedPrefsData.getInstance(LoginActivity.this).updateStringValue(LoginActivity.this, Constants.USER_ID, loginResponse.getResult().getUserInfos().getId()+"");
                                            Log.e("created_useid==", loginResponse.getResult().getUserInfos().getId()+"");
                                            startActivity(new Intent(getApplicationContext(), SyncHomeActivity.class));
                                            CommonUtils.appendLog(LOG_TAG, "Login API", "Login Success");

                                            finish();
                                        }
                                    }, 300);

                                } else {

                                    showDialog(LoginActivity.this,loginResponse.getEndUserMessage() );
                                }
                            }


                        });}


        });
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActicity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //       finish();
            }
        });


        changepassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChangePassword();

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ForgotPassword();

            }
        });

    }

    private JsonObject loginObject() {
        Loginobject requestModel = new Loginobject();
        requestModel.setUserName(username.getText().toString());
        requestModel.setPassword(password.getText().toString());


        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private void ChangePassword() {

    }

    private void ForgotPassword() {

    }

    private boolean validations() {

        if (TextUtils.isEmpty(username.getText().toString())) {
            // userNameEdt.setError(getString(R.string.err_please_enter_username));
            name_layout.setError(getResources().getString(R.string.err_please_enter_username));
            //  showDialog(SignupActivity.this, getResources().getString(R.string.err_please_enter_username));
            return false;
        }
        else{

            name_layout.setErrorEnabled(false);
        }


        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            pass_label.setError(getResources().getString(R.string.err_please_password));
            return false;
        }
        else {
            pass_label.setErrorEnabled(false);
        }
        return true;
    }

    public synchronized void loginWithCloud(final JSONObject values, final String url, final ApplicationThread.OnComplete<String> onComplete) {
        ApplicationThread.bgndPost(LoginActivity.class.getName(), "placeDataInCloud..", new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient.postDataToServerjson(url, values, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {

                                //  DataSynchelper.performMasterSync(LoginActivity.this, true, onComplete);
                                try {
                                    sqliteHelper.createDataBase();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
                }
            }
        });

    }
}
