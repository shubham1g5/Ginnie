package com.example.shubham.ginnie.model;

import android.content.ContentValues;

import com.example.shubham.ginnie.data.MessageContract;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Message {
    private String author;
    private String message;
    private long time;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageContract.MessageEntry.COL_AUTHOR, author);
        contentValues.put(MessageContract.MessageEntry.COL_MESSAGE, message);
        contentValues.put(MessageContract.MessageEntry.COL_TIME, time);
        return contentValues;
    }

}
