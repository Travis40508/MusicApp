package com.elkcreek.rodneytressler.musicapp.di.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application;
    }
}
