package com.cis.easyfarm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.CommonConstants;
import com.cis.easyfarm.common.CommonUtils;
import com.cis.easyfarm.database.DataAccessHandler;
import com.cis.easyfarm.database.Queries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PlotsActivity extends AppCompatActivity {
    //getPlots

    String Farmer_code;
    String  select_plot;
    List<String> get_plot = new ArrayList<String>();
    public TextView plotcode,status,totalarea,adopted_area,gps_area,plot_ownership;
    Spinner spin_plot;
    LinearLayout plot_data,new_complaint,exit_complaint;
    DecimalFormat df = new DecimalFormat("####0.00");
    private LinkedHashMap plotdata;
    DataAccessHandler dataAccessHandler;
    List<Plot> plotDetails;
    Integer plotstatus_id,ownership_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);
        intviews();
        setviews();
    }

    private void intviews() {

        spin_plot = (Spinner) findViewById(R.id.spin_plot);
        plot_data =(LinearLayout) findViewById(R.id.plot_data);
        status = (TextView)findViewById(R.id.status);
        totalarea = (TextView)findViewById(R.id.totalarea);
        adopted_area = (TextView) findViewById(R.id.adopted_area);
        gps_area = (TextView) findViewById(R.id.gps_area);
        plot_ownership = (TextView) findViewById(R.id.plot_ownership);
        new_complaint = findViewById(R.id.new_complaint);
        exit_complaint = findViewById(R.id.exit_complaint);
        dataAccessHandler = new DataAccessHandler(this);
    }

    private void setviews() {

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            Farmer_code = extras.getString("code");



        }
       // String branchname = dataAccessHandler.getCountValue(Queries.getInstance().getbanchname(BANK_ID));
        plotdata = dataAccessHandler.getGenericData(Queries.getInstance().getPlots(Farmer_code));
        String[] plots = CommonUtils.fromMap(plotdata, "plots");


        Log.e("plots===========", plots[1] + "");
        ArrayAdapter<String> plotadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plots);
        plotadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_plot.setAdapter(plotadapter);

        spin_plot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select_plot = spin_plot.getItemAtPosition(spin_plot.getSelectedItemPosition()).toString();
                Log.e("select_plot===2",select_plot+"");
                if(i!=0) {
                    plot_data.setVisibility(View.VISIBLE);
                    plotDetails = (List<Plot>) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getcompleteplotdetails(select_plot), 1);
                    plotstatus_id =plotDetails.get(0).getPlotStatusId();
                    ownership_id=plotDetails.get(0).getPlotOwnerShipTypeId();
                    status.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatusid(plotstatus_id+"")));
                    totalarea.setText(df.format(plotDetails.get(0).getTotalPlotArea()));
                    adopted_area.setText(df.format(plotDetails.get(0).getAdaptedArea()));
                    gps_area.setText(df.format(plotDetails.get(0).getGPSPlotArea()));
                    plot_ownership.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getstatusid(ownership_id+"")));

                }else{
                    plot_data.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        new_complaint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlotsActivity.this, Complaints_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code",select_plot);
                intent.putExtra("totalarea",totalarea.getText());
                intent.putExtra("status",status.getText().toString());
                intent.putExtra("ownership",plot_ownership.getText());

                startActivity(intent);

            }

        });
        exit_complaint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlotsActivity.this, Exit_Complaints_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code",select_plot);
                intent.putExtra("totalarea",totalarea.getText());
                intent.putExtra("status",status.getText().toString());
                intent.putExtra("ownership",plot_ownership.getText().toString());

                startActivity(intent);

            }

        });

    }
}
