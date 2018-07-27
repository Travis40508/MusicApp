package com.elkcreek.rodneytressler.musicapp.di.components;

import com.elkcreek.rodneytressler.musicapp.di.MusicApplication;
import com.elkcreek.rodneytressler.musicapp.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.musicapp.di.modules.ViewModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, ViewModule.class})
public interface ApplicationComponent {
    void inject(MusicApplication musicApplication);
}
