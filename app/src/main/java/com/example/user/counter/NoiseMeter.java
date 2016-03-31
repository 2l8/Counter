package com.example.user.counter;


import java.io.IOException;
import android.media.MediaRecorder;
import android.util.Log;


public class NoiseMeter {
    static final private double EMA_FILTER = 0.6;

    private MediaRecorder mRecorder = null;
    private double mEMA = 0.0;
    private final String LOG_TAG = "myLogs";

    int AudioSource = MediaRecorder.AudioSource.MIC;
    int OutputFormat=MediaRecorder.OutputFormat.THREE_GPP;
    int AudioEncoder = MediaRecorder.AudioEncoder.AMR_NB;
    String OutputFile = "/dev/null/";


    public void start_rec()  {

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(AudioSource);
            mRecorder.setOutputFormat(OutputFormat);
            mRecorder.setAudioEncoder(AudioEncoder);
            mRecorder.setOutputFile(OutputFile);

            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mRecorder.start();
            mEMA = 0.0;
        }
    }

    public void stop_rec() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getTheAmplitude(){
        if(mRecorder != null)
            return (mRecorder.getMaxAmplitude());
        else
            return 1;
    }
    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude()/2700.0);
        else
            return 0;

    }

 /*   public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }*/



    public void calc(){
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        // do something here
                        Log.d(LOG_TAG, "noise THR- "+getTheAmplitude());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(LOG_TAG, "local Thread error", e);
                    }
                }
            }
        };
        thread.start();
    }
}