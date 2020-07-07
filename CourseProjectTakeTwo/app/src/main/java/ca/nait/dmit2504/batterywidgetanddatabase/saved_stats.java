package ca.nait.dmit2504.batterywidgetanddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Calendar;
import java.util.Date;

public class saved_stats extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Stats.db";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "Saved";
    private static String COLUMN_DATE = "Date";
    private static String COLUMN_STATUS = "Status";
    private static String COLUMN_VOLTAGE = "Voltage";
    private static String COLUMN_HEALTH = "Health";

    public saved_stats(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                + COLUMN_STATUS + " TEXT, "
                + COLUMN_HEALTH + " TEXT, "
                + COLUMN_VOLTAGE + " TEXT, "
                + COLUMN_DATE + " TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }


    public long SaveNewData(String status, String voltage,String health){

        Date currentdate = Calendar.getInstance().getTime();
        String now = currentdate.toString();

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_VOLTAGE, voltage);
        values.put(COLUMN_HEALTH,health);
        values.put(COLUMN_DATE, now);

        return db.insert(TABLE_NAME,null,values);

    }

    public Cursor query(){

        SQLiteDatabase db = getReadableDatabase();

        String queryStatement = "SELECT _id, "
                + COLUMN_STATUS + ", "
                + COLUMN_HEALTH + ", "
                + COLUMN_VOLTAGE + ", "
                + COLUMN_DATE
                + " FROM " + TABLE_NAME
                + " ORDER BY "+ BaseColumns._ID;

        return db.rawQuery(queryStatement,null);
    }






}
