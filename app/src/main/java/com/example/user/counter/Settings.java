package com.example.user.counter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by user on 29.03.16.
 */
public class Settings extends Activity implements View.OnClickListener {
    EnergyDbHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);

        dbh = new EnergyDbHelper(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnClearTable:
                //Удаление записей из таблицы
                dbh.deleteRecordsFromTable();
                break;

        }
    }
}
