package com.example.fuelcalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FuelCalc.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "RefuelData";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CAR = "car_id";
    private static final String COLUMN_TRIP_LENGTH = "trip_length";
    private static final String COLUMN_REFUEL_VALUE = "refuel_value";
    private static final String COLUMN_REFUEL_DATE = "refuel_date";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TRIP_LENGTH + " INTEGER, " +
                        COLUMN_REFUEL_VALUE + " INTEGER, " +
                        COLUMN_REFUEL_DATE + " TEXT, " +
                        COLUMN_CAR + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addRefuelData(int trip_length, int refuel_value, String refuel_date, int car_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRIP_LENGTH, trip_length);
        cv.put(COLUMN_REFUEL_VALUE, refuel_value);
        cv.put(COLUMN_REFUEL_DATE, refuel_date);
        cv.put(COLUMN_CAR, car_id);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            //Toast.makeText(context, "nie poszlo :(", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "poszlo do bazy", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
