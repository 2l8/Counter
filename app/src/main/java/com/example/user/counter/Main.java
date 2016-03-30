package com.example.user.counter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class Main extends AppCompatActivity implements View.OnClickListener {
    public EnergyDbHelper dbh;
    public  final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new EnergyDbHelper(getApplicationContext());
    }

    @Override
    public void onBackPressed() {

        Log.d(LOG_TAG, "tm- " + Calendar.getInstance().getTimeInMillis());

        if (dbh==null) return;

        dbh.impuls();
        Log.d(LOG_TAG, "Записей" + dbh.getImpulsCount());
        //dbh.getData();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSettings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.btnGraph:
                startActivity(new Intent(this, Graph.class));
                break;
            case R.id.btnClearTable:
                dbh.deleteRecordsFromTable();
                break;
        }


    }
}
