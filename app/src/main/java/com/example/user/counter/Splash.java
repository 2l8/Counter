package com.example.user.counter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.sql.Time;
import java.util.concurrent.TimeUnit;


/**
 * Created by user on 29.03.16.
 */
public class Splash extends Activity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                this.startActivity(new Intent(this,Main.class));
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SplashTimer().execute();
    }

    class SplashTimer extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(3);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent  = new Intent (Splash.this, Main.class);
            startActivity(intent);
            finish();
        }
    }
}
