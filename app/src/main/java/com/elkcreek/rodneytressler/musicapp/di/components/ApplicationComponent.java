package com.elkcreek.rodneytressler.musicapp.di.components;

import com.elkcreek.rodneytressler.musicapp.di.MusicApplication;
import com.elkcreek.rodneytressler.musicapp.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.CacheModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.MusicDatabaseModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.NetworkModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.ViewModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ViewModule.class, NetworkModule.class, MusicDatabaseModule.class, CacheModule.class})
public interface ApplicationComponent {
    void inject(MusicApplication musicApplication);
}
