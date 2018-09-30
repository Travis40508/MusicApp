package com.elkcreek.rodneytressler.musicapp.ui.albumsview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class AlbumsFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public AlbumsFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AlbumsViewModel.class)) {
            return (T) new AlbumsViewModel(repositoryService);
        }
        return null;
    }
}
