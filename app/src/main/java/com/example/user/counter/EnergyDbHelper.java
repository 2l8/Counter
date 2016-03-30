package com.example.user.counter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 29.03.16.
 */
public class EnergyDbHelper extends SQLiteOpenHelper implements BaseColumns{
    public String LOG_TAG = "myLogs";

    public static final String TABLE_NAME  = "impuls";

    private static final String DB_NAME     = "energy.db";
    private static final int DB_VERSION     = 1;

    public static String DB_CREATE_TABLE = "create table " + TABLE_NAME +
                                            " (_id  integer primary key autoincrement not null"
                                            +", dtl   long not null"
//                                            +", flag integer "
                                            +");";




    public static String DB_DELETE_DATA = "DELETE from " +TABLE_NAME +";";

    public static String DB_DROP_TABLE  = "DROP TABLE " +TABLE_NAME;

    public static String DB_GET_MAX_DTL = "SELECT MAX(dtl) AS MAX_DTL FROM "+TABLE_NAME;

    public static String DB_GET_MIN_DTL = "SELECT MIN(dtl) AS MIN_DTL FROM "+TABLE_NAME;





    public EnergyDbHelper(Context con) {
        super(con, DB_NAME, null, DB_VERSION);
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DB_CREATE_TABLE);
        }
        catch (Exception e){
            Log.d(LOG_TAG, e.getMessage());
        };
    }

    public void impuls(){
        ContentValues values = new ContentValues();
        values.put("dtl", Calendar.getInstance().getTimeInMillis());
        long id =this.getWritableDatabase().insert(TABLE_NAME, null, values);
        Log.d("", "insert - "+id);
    }

    public int getImpulsCount(){
        Cursor c = this.getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        return c.getCount();
    }

    public void getData(){
        Cursor c = this.getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, " _id ");
        if (c!=null){
            if (c.moveToFirst()){
                do{
                    String str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; "+ c.getDouble(c.getColumnIndex(cn)));
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
            c.close();
        }
    }

    public long getMinDT(){
        long minDT=0;
        Cursor c = this.getWritableDatabase().rawQuery(DB_GET_MIN_DTL, null);
        if (c!=null){
            if (c.moveToFirst()){
                minDT = c.getLong(c.getColumnIndex("MIN_DTL"));
            }
            c.close();
        }
        return minDT;
    }

    public long getMaxDT(){
        long maxDT=0;
        Cursor c = this.getWritableDatabase().rawQuery(DB_GET_MAX_DTL,null);
        if (c!=null){
            if (c.moveToFirst()){
                maxDT = c.getLong(c.getColumnIndex("MAX_DTL"));
            }
            c.close();
        }
        return maxDT;
    }

    public void deleteRecordsFromTable(){
        this.getWritableDatabase().execSQL(DB_DELETE_DATA);
    }

    public void dropTable(){
        this.getWritableDatabase().execSQL(DB_DROP_TABLE);
    }


    public void createTable(){
        this.getWritableDatabase().execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion)
            Log.d(LOG_TAG,"Версии БД разные");
    }
}
