package com.cis.easyfarm.sync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cis.easyfarm.R;

public class SyncActivity extends AppCompatActivity {

    Button syncAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        syncAllBtn = findViewById(R.id.syncallBtn);
        syncAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("SYNNNNNNCCCC", "SYnc Buttn CLicked");

            }
        });
    }
}
