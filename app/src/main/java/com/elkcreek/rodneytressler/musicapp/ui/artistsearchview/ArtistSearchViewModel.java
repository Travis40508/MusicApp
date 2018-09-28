package com.elkcreek.rodneytressler.musicapp.ui.artistsearchview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ArtistSearchViewModel extends ViewModel {

    public final MusicApiService musicApiService;
    public final RepositoryService repositoryService;
    public ObservableField<List<MusicApi.Artist>> artists = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showProgressBar = new ObservableBoolean(true);
    public MutableLiveData<String> errorToastMessage = new MutableLiveData<>();
    public CompositeDisposable disposable;

    public ArtistSearchViewModel(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();

        fetchTopArtists();
    }

    private void fetchTopArtists() {
        disposable.add(repositoryService.getTopArtists().subscribe(updateViewWithTopArtist(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Artist>> updateViewWithTopArtist() {
        return topArtists -> {
            artists.set(topArtists);
            setShowProgressBar(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            if(throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                errorToastMessage.postValue(Constants.CONNECTION_ERROR);
                //Detach Fragment
            } else {
                errorToastMessage.postValue(Constants.LOADING_ERROR);
                repositoryService.resetDate();
                fetchTopArtists();
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void setShowProgressBar(boolean shouldShowProgressBar) {
        showProgressBar.set(shouldShowProgressBar);
    }
}
