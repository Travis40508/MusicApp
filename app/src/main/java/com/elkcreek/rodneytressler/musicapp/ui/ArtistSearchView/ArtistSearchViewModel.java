package com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView;

import android.arch.lifecycle.ViewModel;

import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import io.reactivex.disposables.CompositeDisposable;

public class ArtistSearchViewModel extends ViewModel {

    private final MusicApiService musicApiService;
    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;

    public ArtistSearchViewModel(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }
}
