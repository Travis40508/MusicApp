package com.elkcreek.rodneytressler.musicapp.ui.youtubeview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class YoutubeFactory implements ViewModelProvider.Factory {

    private final RepositoryService repositoryService;

    @Inject
    public YoutubeFactory(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(YoutubeViewModel.class)) {
            return (T) new YoutubeViewModel(repositoryService);
        }
        return null;
    }
}
