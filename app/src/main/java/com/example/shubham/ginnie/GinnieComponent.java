package com.example.shubham.ginnie;


import com.example.shubham.ginnie.common.FragmentScoped;
import com.example.shubham.ginnie.common.SchedulerModule;

import dagger.Component;

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = SchedulerModule.class)
public interface GinnieComponent {
    void inject(GinnieFragment ginnieFragment);
}
