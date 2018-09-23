package com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView;

import android.os.Bundle;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ArtistMainPresenter implements BasePresenter<ArtistMainView> {

    private CompositeDisposable disposable;
    private ArtistMainView view;
    private String artistUid;
    private String artistName;
    private int currentItem;
    private static final String STATE_VIEW_PAGER_POSITION = "state_view_pager_position";

    @Inject
    public ArtistMainPresenter() {

    }

    @Override
    public void attachView(ArtistMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        view.setActionBarTitle(artistName);
        view.showScreens(artistUid, artistName);
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
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
