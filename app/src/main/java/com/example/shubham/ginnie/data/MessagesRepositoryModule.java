package com.example.shubham.ginnie.data;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class MessagesRepositoryModule {

    @Singleton
    @Provides
    BriteDatabase provideBriteDatabase(Context context, Scheduler ioScheduler) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return sqlBrite.wrapDatabaseHelper(new MessageDbHelper(context), ioScheduler);
    }

}
