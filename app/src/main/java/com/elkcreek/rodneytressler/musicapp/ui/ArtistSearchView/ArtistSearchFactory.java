package com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import javax.inject.Inject;

public class ArtistSearchFactory implements ViewModelProvider.Factory {

    private final MusicApiService musicApiService;
    private final RepositoryService repositoryService;

    @Inject
    public ArtistSearchFactory(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ArtistSearchViewModel.class)) {
            return (T) new ArtistSearchViewModel(musicApiService, repositoryService);
        }
        return null;
    }
}
