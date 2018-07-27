package com.elkcreek.rodneytressler.musicapp.di;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.elkcreek.rodneytressler.musicapp.di.components.DaggerApplicationComponent;
import com.elkcreek.rodneytressler.musicapp.di.modules.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MusicApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    protected DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    protected DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }
}
