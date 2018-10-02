package com.elkcreek.rodneytressler.musicapp.ui.albumtracksview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class AlbumTracksFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public AlbumTracksFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AlbumTracksViewModel.class)) {
            return (T) new AlbumTracksViewModel(repositoryService);
        }
        return null;
    }
}
