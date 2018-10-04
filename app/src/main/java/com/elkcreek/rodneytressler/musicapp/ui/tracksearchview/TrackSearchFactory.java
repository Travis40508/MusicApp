package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class TrackSearchFactory implements ViewModelProvider.Factory {

    public final RepositoryService repositoryService;

    @Inject
    public TrackSearchFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TrackSearchViewModel.class)) {
            return (T) new TrackSearchViewModel(repositoryService);
        }
        return null;
    }
}
