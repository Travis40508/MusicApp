package com.elkcreek.rodneytressler.musicapp.ui.trackbioview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class TrackBioFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public TrackBioFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TrackBioViewModel.class)) {
            return (T) new TrackBioViewModel(repositoryService);
        }
        return null;
    }
}
