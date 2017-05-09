package com.example.shubham.ginnie;

import com.example.shubham.ginnie.common.SchedulerModule;
import com.example.shubham.ginnie.data.MessagesRepository;
import com.example.shubham.ginnie.data.MessagesRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MessagesRepositoryModule.class, SchedulerModule.class, AppModule.class})
public interface AppComponent {
    MessagesRepository getMessagesRepository();
}
