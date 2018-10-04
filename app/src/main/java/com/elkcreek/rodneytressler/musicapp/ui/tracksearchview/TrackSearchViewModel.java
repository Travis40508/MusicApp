package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackSearchViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    public ObservableField<List<MusicApi.Track>> topTrackList = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(true);
    private MainViewModel mainViewModel;

    public TrackSearchViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        disposable = new CompositeDisposable();
        fetchTopTracks();
    }

    private void fetchTopTracks() {
        disposable.add(repositoryService.getTopTracks().subscribe(updateUiWithTracks(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTracks() {
        return trackList -> {
            topTrackList.set(trackList);
            showLoadingLayout.set(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@-TrackSearchVM", throwable.getMessage());
            showLoadingLayout.set(false);
        };
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}
