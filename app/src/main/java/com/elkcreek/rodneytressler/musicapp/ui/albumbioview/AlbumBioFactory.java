package com.elkcreek.rodneytressler.musicapp.ui.albumbioview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class AlbumBioFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public AlbumBioFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AlbumBioViewModel.class)) {
            return (T) new AlbumBioViewModel(repositoryService);
        }
        return null;
    }
}
