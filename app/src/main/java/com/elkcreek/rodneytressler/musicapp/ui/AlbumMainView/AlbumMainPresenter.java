package com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView;

import android.os.Bundle;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AlbumMainPresenter implements BasePresenter<AlbumMainView> {

    private CompositeDisposable disposable;
    private AlbumMainView view;
    private String artistName;
    private String artistUid;
    private String albumName;
    private String albumUid;
    private String albumImage;
    private int currentItem;
    private static final String STATE_VIEW_PAGER_POSITION = "state_view_pager_position";

    @Inject
    public AlbumMainPresenter() {

    }

    @Override
    public void attachView(AlbumMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        view.setActionBarTitle(artistName + " - " + albumName);
        view.showScreens(artistName, artistUid, albumName, albumUid, albumImage);
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistRetrieved(String artistName, String artistUid) {
        this.artistName = artistName;
        this.artistUid = artistUid;
    }

    public void albumRetrieved(String albumName, String albumUid, String albumImage) {
        this.albumName = albumName;
        this.albumUid = albumUid;
        this.albumImage = albumImage;
    }

    public void screenPaused(int currentItem) {
        this.currentItem = currentItem;
    }

    public void screenRestored() {
        if(currentItem != 0) {
            view.setViewPagerItem(currentItem);
        }
    }

    public void saveState(Bundle outState, int currentItem) {
        outState.putInt(STATE_VIEW_PAGER_POSITION, currentItem);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            currentItem = savedInstanceState.getInt(STATE_VIEW_PAGER_POSITION);
        }
    }

    public void viewPagerCreated() {
        view.setViewPagerState(currentItem);
    }
}
