package com.example.user.counter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by user on 29.03.16.
 */
public class Graph extends Activity implements View.OnClickListener {
    public static final String LOG_TAG = "myLogs";
    private GraphView graphView;
    private LinearLayout graphLayout;

    private EnergyDbHelper dbh;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_graph);

        dbh = new EnergyDbHelper(getApplicationContext());

        graphView = new GraphView(this);
        graphLayout= (LinearLayout) findViewById(R.id.GraphLayout);

        RedrawGraph3();


    }

    public void RedrawGraph(){
        DataPoint[] data = {new DataPoint(1.1d, 2.5d)
                , new DataPoint(2.2d, 4.0d)
                , new DataPoint(3.3d, 1.0d)
                , new DataPoint(4.5d, 1.0d)
        };
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        graphView.addSeries(series);

        graphLayout.addView(graphView);
    }

    public void RedrawGraph2() {
        long minDT = dbh.getMinDT();
        long maxDT = dbh.getMaxDT();
        Log.d(LOG_TAG, ""+minDT);
        Log.d(LOG_TAG, ""+maxDT);

        Log.d(LOG_TAG," "+((maxDT-minDT)/1000));

        Cursor cursor = dbh.getReadableDatabase().query(dbh.TABLE_NAME, null, null, null, null, null, null);

        if (!cursor.moveToFirst()) return;

        DataPoint[] data = new DataPoint[cursor.getCount()];
        do{
            double x = ((cursor.getLong(cursor.getColumnIndex("dtl"))-minDT)/1000)/60;
            double y = cursor.getPosition();
            data[cursor.getPosition()] = new DataPoint(x,y);
        }while (cursor.moveToNext());
        cursor.close();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        graphView.addSeries(series);

        graphLayout.addView(graphView);

        return;
    }

    public void RedrawGraph3() {

        long minDT = dbh.getMinDT();
        long maxDT = dbh.getMaxDT();
        long total = (maxDT-minDT)/1000;
        double prev_x = 0;

        Cursor cursor = dbh.getReadableDatabase().query(dbh.TABLE_NAME, null, null, null, null, null, null);

        if (!cursor.moveToFirst()) return;

        DataPoint[] data = new DataPoint[cursor.getCount()];
        do{
            double x = ((cursor.getLong(cursor.getColumnIndex("dtl"))-minDT)/1000);
            double y = 1000/(cursor.getLong(cursor.getColumnIndex("dtl"))-prev_x);

            data[cursor.getPosition()] = new DataPoint(x,y);

            prev_x = cursor.getLong(cursor.getColumnIndex("dtl"));
        }while (cursor.moveToNext());
        cursor.close();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

        graphView.addSeries(series);
        graphLayout.addView(graphView);
    }
}
