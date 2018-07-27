package com.elkcreek.rodneytressler.musicapp.di.modules;

import com.elkcreek.rodneytressler.musicapp.ui.BioView.BioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity;
import com.elkcreek.rodneytressler.musicapp.ui.SearchView.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector
    abstract BioFragment contributesBioFragmentInjector();

    @ContributesAndroidInjector
    abstract SearchFragment contributesSearchFragmentInjector();
}
