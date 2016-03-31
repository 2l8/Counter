package com.example.user.counter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class NoiseActivity extends Activity implements View.OnClickListener {
    String LOG_TAG = "myLogs";
    NoiseMeter nMeter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise);
        if (nMeter==null) nMeter= new NoiseMeter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart_nm:
                Log.d(LOG_TAG, "Run CALC");
                nMeter.calc();
                break;
            case R.id.btnStop_nm:
                nMeter.start_rec();
                break;
        }
    }


}
