package com.example.shubham.ginnie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Messages.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = "NOT NULL";
    private static final String PRIMARY_KEY = " PRIMARY KEY";


    private static final String SQL_CREATE_MESSAGE_ENTRY =
            "CREATE TABLE " + MessageContract.MessageEntry.TABLE_NAME + " (" +
                    MessageContract.MessageEntry._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    MessageContract.MessageEntry.COL_AUTHOR + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    MessageContract.MessageEntry.COL_MESSAGE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    MessageContract.MessageEntry.COL_TIME + REAL_TYPE + NOT_NULL +
                    " )";

    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MESSAGE_ENTRY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required for v1
    }
}
