package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import android.os.Bundle;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumBioPresenter implements BasePresenter<AlbumBioView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private AlbumBioView view;
    private String albumUid;
    private boolean isExpanded;
    private static final String STATE_SCROLL_VIEW_POSITION = "state_scroll_view_position";
    private int scrollPosition;

    @Inject
    public AlbumBioPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AlbumBioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchAlbumInfo();
    }

    private void fetchAlbumInfo() {
        disposable.add(repositoryService.getAlbumInfo(albumUid).subscribe(updateUiWithAlbumInfo(), updateUiWithError()));
    }

    private Consumer<MusicApi.AlbumInfo> updateUiWithAlbumInfo() {
        return albumInfo -> {
            if (isExpanded) {
                view.showAlbumBio(albumInfo.getWiki() != null ? albumInfo.getWiki().getTrackContent() : Constants.NO_ALBUM_BIO_AVAILABLE);
            } else {
                view.showAlbumBio(albumInfo.getWiki() != null ? albumInfo.getWiki().getTrackSummary() : Constants.NO_ALBUM_BIO_AVAILABLE);
            }

            if (albumInfo.getTrackImageList() != null) {
                view.showAlbumImage(albumInfo.getTrackImageList().get(2).getImageUrl());
            } else {
                view.setImageBackgroundWhite();
                view.showGenericAlbumImage();
            }
            view.hideParentLoadingLayout();
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@-AlbumBioPresenter", throwable.getMessage());
            if(throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                view.detachFragment();
                view.toastConnectionFailedToast();
            } else {
                view.showAlbumBio(Constants.NO_ALBUM_BIO_AVAILABLE);
                view.setImageBackgroundWhite();
                view.showGenericAlbumImage();
                view.hideParentLoadingLayout();
            }
        };
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void albumUidRetrieved(String albumUid) {
        this.albumUid = albumUid;
    }

    public void readMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase(Constants.READ_MORE)) {
            view.setReadMoreText(Constants.COLLAPSE);
        } else {
            view.setReadMoreText(Constants.READ_MORE);
        }
        fetchAlbumInfo();
    }

    public void storeScrollViewPosition(int scrollY) {
        this.scrollPosition = scrollY;
    }

    public void saveState(Bundle outState, int scrollY) {
        outState.putInt(STATE_SCROLL_VIEW_POSITION, scrollY);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL_VIEW_POSITION);
        }
    }

    public void bioLoaded() {
        view.setScrollViewState(scrollPosition);
    }
}
