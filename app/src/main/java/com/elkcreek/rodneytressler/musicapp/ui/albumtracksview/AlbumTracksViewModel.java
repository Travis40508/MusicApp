package com.elkcreek.rodneytressler.musicapp.ui.albumtracksview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumTracksViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private MainViewModel mainViewModel;
    public ObservableField<List<MusicApi.Track>> albumTrackList = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showProgressBar = new ObservableBoolean(true);

    public AlbumTracksViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void fetchAlbumTracks(String albumUid) {
        disposable.add(repositoryService.getTracksFromAlbum(albumUid).subscribe(updateUiWithTracks(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTracks() {
        return trackList -> {
            albumTrackList.set(trackList);
            showProgressBar.set(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@-AlbumTracksViewModel", throwable.getMessage());
            if(throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                mainViewModel.errorToastMessage.postValue(Constants.CONNECTION_ERROR);
            } else {
                showProgressBar.set(false);
            }
        };
    }
}
