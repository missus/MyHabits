package com.example.android.myhabits.data;

import android.provider.BaseColumns;

public final class HabitContract {
    public static abstract class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_REMINDER = "reminder";
        public static final String COLUMN_HABIT_K_TIMES = "repeat_k_times";
        public static final String COLUMN_HABIT_IN_N_DAYS = "in_n_days";

        public static final int REMINDER_NO = 0;
        public static final int REMINDER_YES = 1;
    }
    public static abstract class DateEntry implements BaseColumns {

        public static final String TABLE_NAME = "date";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE_HABIT_ID = "habit";
        public static final String COLUMN_DATE_DATE = "date";
        }
}
