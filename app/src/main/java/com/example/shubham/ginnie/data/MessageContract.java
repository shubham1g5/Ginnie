package com.example.shubham.ginnie.data;

import android.provider.BaseColumns;

public class MessageContract {

    // Prevent direct instantiation
    private MessageContract() {
    }

    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COL_AUTHOR = "author";
        public static final String COL_MESSAGE = "message";
        public static final String COL_TIME = "time";
    }
}
