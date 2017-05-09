package com.example.shubham.ginnie;

import android.app.Application;

import com.example.shubham.ginnie.data.MessagesRepositoryModule;

public class GinnieApp extends Application{

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .messagesRepositoryModule(new MessagesRepositoryModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
