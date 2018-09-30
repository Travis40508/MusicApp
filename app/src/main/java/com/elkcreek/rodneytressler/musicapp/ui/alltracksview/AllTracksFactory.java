package com.elkcreek.rodneytressler.musicapp.ui.alltracksview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class AllTracksFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public AllTracksFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AllTracksViewModel.class)) {
            return (T) new AllTracksViewModel(repositoryService);
        }
        return null;
    }
}
