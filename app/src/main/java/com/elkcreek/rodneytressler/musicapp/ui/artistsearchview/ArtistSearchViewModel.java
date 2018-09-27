package com.elkcreek.rodneytressler.musicapp.ui.artistsearchview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import io.reactivex.disposables.CompositeDisposable;

public class ArtistSearchViewModel extends ViewModel {

    public final MusicApiService musicApiService;
    public final RepositoryService repositoryService;
    public CompositeDisposable disposable;

    public ArtistSearchViewModel(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }
}
