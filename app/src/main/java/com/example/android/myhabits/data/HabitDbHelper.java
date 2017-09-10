package com.example.android.myhabits.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.myhabits.data.HabitContract.HabitEntry;
import com.example.android.myhabits.data.HabitContract.DateEntry;

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Habits.db";

    private static final String SQL_CREATE_HABIT_ENTRIES =
            "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                    HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " +
                    HabitEntry.COLUMN_HABIT_REMINDER + " INTEGER NOT NULL DEFAULT 0, " +
                    HabitEntry.COLUMN_HABIT_K_TIMES + " INTEGER NOT NULL DEFAULT 1, " +
                    HabitEntry.COLUMN_HABIT_IN_N_DAYS + " INTEGER NOT NULL DEFAULT 1);";
    private static final String SQL_CREATE_DATE_ENTRIES =
            "CREATE TABLE " + DateEntry.TABLE_NAME + " (" +
                    DateEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DateEntry.COLUMN_DATE_HABIT_ID + " INTEGER NOT NULL, " +
                    DateEntry.COLUMN_DATE_DATE + " INTEGER NOT NULL);";

    private static final String SQL_DELETE_HABIT_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
    private static final String SQL_DELETE_DATE_ENTRIES =
            "DROP TABLE IF EXISTS " + DateEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HABIT_ENTRIES);
        db.execSQL(SQL_CREATE_DATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_HABIT_ENTRIES);
        db.execSQL(SQL_DELETE_DATE_ENTRIES);
        onCreate(db);
    }
}
