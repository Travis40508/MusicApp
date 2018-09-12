package com.elkcreek.rodneytressler.musicapp.ui.AlbumsView;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumsPresenter implements BasePresenter<AlbumsView> {
    private final RepositoryService repositoryService;
    private AlbumsView view;
    private String artistName;
    private String artistUid;
    private CompositeDisposable disposable;
    private static final String STATE_RECYCLER_VIEW_POSITION = "state_recycler_view_position";
    private Parcelable recyclerViewPosition;

    @Inject
    public AlbumsPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AlbumsView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchAlbums();
    }

    private void fetchAlbums() {
        disposable.add(repositoryService.getAlbums(artistUid).subscribe(updateUiWithAlbum(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Album>> updateUiWithAlbum() {
        return albumList -> {
            if (!albumList.isEmpty()) {
                view.showTopAlbums(albumList);
            } else {
                view.showNoAlbumsMessage();
            }

            view.hideLoadingLayout();
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
            view.showNoAlbumsMessage();
            view.hideLoadingLayout();
        };
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void albumClicked(MusicApi.Album album) {
        view.showParentLoadingLayout();
        view.showAlbumTracks(artistName, artistUid, album.getAlbumName(), album.getAlbumUid(), album.getTrackImage().get(2).getImageUrl());
    }

    public void storeRecyclerViewPositionState(Parcelable parcelable) {
        this.recyclerViewPosition = parcelable;
    }

    public void saveState(Bundle outState, Parcelable parcelable) {
        outState.putParcelable(STATE_RECYCLER_VIEW_POSITION, parcelable);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            recyclerViewPosition = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW_POSITION);
        }
    }

    public void listLoaded() {
        view.setRecyclerViewPosition(recyclerViewPosition);
    }
}
