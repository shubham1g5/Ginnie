package com.example.shubham.ginnie.data;

import android.database.Cursor;

import com.example.shubham.ginnie.model.Message;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;

@Singleton
public class MessagesRepository {

    private final BriteDatabase mDb;

    @Inject
    MessagesRepository(BriteDatabase mDb) {
        this.mDb = mDb;
    }

    public Observable<List<Message>> getMessages() {
        return RxJavaInterop.toV2Observable(mDb.createQuery(MessageContract.MessageEntry.TABLE_NAME,
                "select * from " + MessageContract.MessageEntry.TABLE_NAME + " order by " + MessageContract.MessageEntry.COL_TIME)
                .mapToList(this::map));
    }

    public void saveMessage(Message message) {
        mDb.insert(MessageContract.MessageEntry.TABLE_NAME, message.toContentValues());
    }

    private Message map(Cursor cursor) {
        return new Message(cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COL_AUTHOR)),
                cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COL_MESSAGE)),
                cursor.getLong(cursor.getColumnIndex(MessageContract.MessageEntry.COL_TIME)));
    }

    public Observable<List<Message>> getMyMessages(int limit) {
        return RxJavaInterop.toV2Observable(mDb.createQuery(MessageContract.MessageEntry.TABLE_NAME,
                "select * from " + MessageContract.MessageEntry.TABLE_NAME + " where " + MessageContract.MessageEntry.COL_AUTHOR + "=\'You\' " + " order by " + MessageContract.MessageEntry.COL_TIME + " limit " + limit)
                .mapToList(this::map));
    }

}
