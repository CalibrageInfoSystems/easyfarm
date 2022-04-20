package com.cis.easyfarm.ui.sync;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.Notifications;
import com.cis.easyfarm.R;
import com.cis.easyfarm.cloudHelper.ApplicationThread;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.common.ProgressBar;
import com.cis.easyfarm.common.UiUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.DatabaseKeys;
import com.cis.easyfarm.database.Queries;
import com.cis.easyfarm.sync.DataSynchelper;
import com.cis.easyfarm.ui.Adapters.NotificationDisplayAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cis.easyfarm.sync.DataSynchelper.refreshtableNamesList;
import static com.cis.easyfarm.sync.DataSynchelper.refreshtransactionsDataMap;

public class NotificationsScreen extends AppCompatActivity {
    public static final String LOG_TAG = NotificationsScreen.class.getName();
    private ImageView refreshBtn;
    private RecyclerView notiRecyclerView;
    private NotificationDisplayAdapter notificationDisplayAdapter;
    private LinearLayoutManager layoutManager;
    Toolbar toolbar;
    Context context;
    private List<Notifications> notificationlist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        settoolbar();
        intview();
        settoolbar();
        setViews();

        refreshBtn = (ImageView) findViewById(R.id.refresh);
        layoutManager = new LinearLayoutManager(this);
        notiRecyclerView.setLayoutManager(layoutManager);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DataSynchelper.getAlertsData(NotificationsScreen.this, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                          //  renderNotifications();
//                        } else {
//                            UiUtils.showCustomToastMessage("Error while getting alerts Data", NotificationsScreen.this, 1);
//                        }
//                    }
//                });
            }
        });

        dataAccessHandler = new DataAccessHandler(this);

        //renderNotifications();

        updateNotificationStatus();
    }

    private void intview() {

        notiRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerView);
    }

    private void setViews() {
        notiRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dataAccessHandler = new DataAccessHandler(this);

        notificationlist = (List<Notifications>) dataAccessHandler.getNotification(Queries.getInstance().Notification(), 1);


        for (int i = 0; i < notificationlist.size(); i++) {

            Log.d("notification===", notificationlist.get(i).getIsRead() + "");
            CommonUtils.appendLog(LOG_TAG, "setViews", notificationlist.get(i).getIsRead() + "");


        }

        notificationDisplayAdapter = new NotificationDisplayAdapter(this, notificationlist);
        notiRecyclerView.setAdapter(notificationDisplayAdapter);

    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Select Godown");
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

    //    private void renderNotifications() {
//
//        ProgressBar.showProgressBar(this, "Please wait...");
//        ApplicationThread.bgndPost(LOG_TAG, "", new Runnable() {
//            @Override
//            public void run() {
//                ProgressBar.hideProgressBar();
//                final List<Alerts> alertsList = (List<Alerts>) dataAccessHandler.getAlertsDetails(Queries.getInstance().getAlertsDetailsQueryToRender(), 1, false);
//
//                Collections.reverse(alertsList);
//                ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
//                    @Override
//                    public void run() {
//                        if (null != alertsList && !alertsList.isEmpty()) {
//                            notificationDisplayAdapter = new NotificationDisplayAdapter(alertsList);
//                            notiRecyclerView.setAdapter(notificationDisplayAdapter);
//                        }
//                    }
//                });
//            }
//        });
//    }
    private void updateNotificationStatus() {
        // dataAccessHandler.upNotificationStatus();

//        List<Alerts> dataToSendCloud = (List<Alerts>) dataAccessHandler.getAlertsDetails(Queries.getInstance()
//                .getAlertsDetailsQueryToSendCloud(), 1, true);
//
//        if (dataToSendCloud == null || dataToSendCloud.isEmpty()) {
//            return;
//        }
//
//        DataSynchelper.reverseSyncTransCount = 0;
//        DataSynchelper.transactionsCheck = 0;
//        refreshtableNamesList.clear();
//        refreshtransactionsDataMap.clear();
//        refreshtableNamesList.add(DatabaseKeys.TABLE_ALERTS);
//        refreshtransactionsDataMap.put(DatabaseKeys.TABLE_ALERTS, dataToSendCloud);
//
//        DataSynchelper.postTransactionsDataToCloud(this, DatabaseKeys.TABLE_ALERTS, dataAccessHandler, new ApplicationThread.OnComplete() {
//            @Override
//            public void execute(boolean success, Object result, String msg) {
//                super.execute(success, result, msg);
//                refreshtableNamesList.clear();
//                refreshtransactionsDataMap.clear();
//            }
//        });
//
//    }
    }
}
