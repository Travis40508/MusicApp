package com.elkcreek.rodneytressler.musicapp.di;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.elkcreek.rodneytressler.musicapp.di.components.DaggerApplicationComponent;
import com.elkcreek.rodneytressler.musicapp.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.CacheModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.MusicDatabaseModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.NetworkModule;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

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
                .networkModule(new NetworkModule(Constants.BASE_URL, Constants.YOUTUBE_BASE_URL, Constants.LYRICS_BASE_URL))
                .musicDatabaseModule(new MusicDatabaseModule())
                .cacheModule(new CacheModule())
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
