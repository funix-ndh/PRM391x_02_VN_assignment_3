package com.example.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alarm-manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "alarm";

    private static final String ID_KEY = "id";
    private static final String HOUR_KEY = "hour";
    private static final String MIN_KEY = "min";
    private static final String ENABLE_KEY = "enable";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
                "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER)",
                TABLE_NAME, ID_KEY, HOUR_KEY, MIN_KEY, ENABLE_KEY
        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXIST %s", DATABASE_NAME));
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteAlarm(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_KEY + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(HOUR_KEY, alarm.getHour());
        v.put(MIN_KEY, alarm.getMin());
        v.put(ENABLE_KEY, alarm.getEnable());
        db.insert(TABLE_NAME, null, v);
        db.close();
    }

    public List<Alarm> getAlarms() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Alarm> alarms = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                alarms.add(new Alarm(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            }
            while (cursor.moveToNext());
        }
        return alarms;
    }

    public Alarm getAlarmById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return new Alarm(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
    }

    public void update(Alarm alarm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(HOUR_KEY, alarm.getHour());
        v.put(MIN_KEY, alarm.getMin());
        v.put(ENABLE_KEY, alarm.getEnable());
        db.update(TABLE_NAME, v, ID_KEY + " = ?", new String[]{String.valueOf(alarm.getId())});
        db.close();
    }
}
