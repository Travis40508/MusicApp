package com.elkcreek.rodneytressler.musicapp.ui.artistbioview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class ArtistBioFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public ArtistBioFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ArtistBioViewModel.class)) {
            return (T) new ArtistBioViewModel(repositoryService);
        }
        return null;
    }
}
