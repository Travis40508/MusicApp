package com.elkcreek.rodneytressler.musicapp.ui.trackbioview;

import android.arch.lifecycle.ViewModel;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class TrackBioViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private MainViewModel mainViewModel;

    public TrackBioViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}
