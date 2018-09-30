package com.elkcreek.rodneytressler.musicapp.ui.albumbioview;

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

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumBioViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private MainViewModel mainViewModel;
    private CompositeDisposable disposable;
    public ObservableBoolean bioIsExpanded = new ObservableBoolean(false);
    public ObservableField<String> albumContent = new ObservableField<>(Constants.NO_ALBUM_BIO_AVAILABLE);
    public ObservableField<String> albumSummary = new ObservableField<>(Constants.NO_ALBUM_BIO_AVAILABLE);
    public ObservableField<String> imageUrl = new ObservableField<>("");


    public AlbumBioViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void fetchAlbumBio(String albumUid) {
        disposable.add(repositoryService.getAlbumInfo(albumUid).subscribe(updateUiWithAlbumInfo(), updateUiWithError()));
    }

    private Consumer<MusicApi.AlbumInfo> updateUiWithAlbumInfo() {
        return albumInfo -> {
                albumContent.set(albumInfo.getWiki() != null ? albumInfo.getWiki().getTrackContent() : Constants.NO_ALBUM_BIO_AVAILABLE);
                albumSummary.set(albumInfo.getWiki() != null ? albumInfo.getWiki().getTrackSummary() : Constants.NO_ALBUM_BIO_AVAILABLE);

            if (albumInfo.getTrackImageList() != null) {
                imageUrl.set(albumInfo.getTrackImageList().get(2).getImageUrl());
            }

            mainViewModel.showLoadingLayout.set(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@-AlbumBioViewModel", throwable.getMessage());
            if(throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                mainViewModel.shouldPopFragment.postValue(true);
                mainViewModel.errorToastMessage.postValue(Constants.CONNECTION_ERROR);
            } else {
                mainViewModel.showLoadingLayout.set(false);
            }
        };
    }

    public void bioExpandedClicked() {
        bioIsExpanded.set(!bioIsExpanded.get());
    }
}
