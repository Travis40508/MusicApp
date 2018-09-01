package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumBioPresenter implements BasePresenter<AlbumBioView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private AlbumBioView view;
    private String albumUid;
    private boolean isExpanded;

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
            if(isExpanded) {
                view.showAlbumBio(albumInfo.getWiki().getTrackContent());
            } else {
                view.showAlbumBio(albumInfo.getWiki().getTrackSummary());
            }
            view.hideLoadingLayout();
            view.showAlbumImage(albumInfo.getTrackImageList().get(2).getImageUrl());
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
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
}
