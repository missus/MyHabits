package com.example.android.myhabits;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.myhabits.data.HabitContract;
import com.example.android.myhabits.data.HabitDbHelper;

import com.example.android.myhabits.data.HabitContract.HabitEntry;
import com.example.android.myhabits.data.HabitContract.DateEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
        insertHabit("Test 1", 0, 1, 7);
        insertHabit("Test 2", 1, 1, 3);
        insertHabitDate(1, currentTimeMillis());
        displayHabitInfo();
        displayHabitDateInfo();
    }

    private void displayHabitInfo() {
        Cursor cursor = readHabits();
        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_REMINDER + " - " +
                    HabitEntry.COLUMN_HABIT_K_TIMES + " - " +
                    HabitEntry.COLUMN_HABIT_IN_N_DAYS + "\n");
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int reminderColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_REMINDER);
            int kTimesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_K_TIMES);
            int inNDaysColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_IN_N_DAYS);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentReminder = cursor.getInt(reminderColumnIndex);
                int currentKTimes = cursor.getInt(kTimesColumnIndex);
                int currentInNDays = cursor.getInt(inNDaysColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentReminder + " - " +
                        currentKTimes + " - " +
                        currentInNDays));
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor readHabits() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_REMINDER,
                HabitEntry.COLUMN_HABIT_K_TIMES,
                HabitEntry.COLUMN_HABIT_IN_N_DAYS
        };
        return db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
    }

    private void displayHabitDateInfo() {
        Cursor cursor = readDates();
        TextView displayView = (TextView) findViewById(R.id.text_view_date);
        try {
            displayView.setText("The date table contains " + cursor.getCount() + " date.\n\n");
            displayView.append(DateEntry._ID + " - " +
                    DateEntry.COLUMN_DATE_HABIT_ID + " - " +
                    DateEntry.COLUMN_DATE_DATE + "\n");
            int idColumnIndex = cursor.getColumnIndex(DateEntry._ID);
            int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(DateEntry.COLUMN_DATE_DATE);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(habitColumnIndex);
                long currentDateInt = cursor.getLong(dateColumnIndex);
                Date currentDate = new java.util.Date(currentDateInt);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentDate)));
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor readDates() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String DATE_QUERY = "SELECT D." + DateEntry._ID + ", H." + HabitEntry.COLUMN_HABIT_NAME + ", D." + DateEntry.COLUMN_DATE_DATE +
                " FROM " + DateEntry.TABLE_NAME + " D INNER JOIN " + HabitEntry.TABLE_NAME + " H ON D." + DateEntry.COLUMN_DATE_HABIT_ID + " = H." + HabitEntry._ID;
        return db.rawQuery(DATE_QUERY, null);
    }

    private void insertHabit(String name, int reminder, int kTimes, int nDays) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitEntry.COLUMN_HABIT_REMINDER, reminder);
        values.put(HabitEntry.COLUMN_HABIT_K_TIMES, kTimes);
        values.put(HabitEntry.COLUMN_HABIT_IN_N_DAYS, nDays);
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    private void insertHabitDate(int habit, long date) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DateEntry.COLUMN_DATE_HABIT_ID, habit);
        values.put(DateEntry.COLUMN_DATE_DATE, date);
        long newRowId = db.insert(DateEntry.TABLE_NAME, null, values);
    }
}
