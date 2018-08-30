package com.elkcreek.rodneytressler.musicapp.ui.TrackMainView;

import android.os.Bundle;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TrackMainPresenter implements BasePresenter<TrackMainView> {

    private CompositeDisposable disposable;
    private TrackMainView view;
    private String trackUid;
    private String trackName;
    private String artistName;
    private int currentItem;
    private static final String STATE_VIEW_PAGER_POSITION = "state_view_pager_position";

    @Inject
    public TrackMainPresenter() {

    }

    @Override
    public void attachView(TrackMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        view.setActionBarTitle(artistName + " - " + trackName);
        view.showScreens(trackUid, trackName, artistName);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void screenRotated(boolean screenRotated) {
        if(screenRotated) {
            view.reAttachFragment();
        }
    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
    }

    public void searchRetrieved(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public void saveState(Bundle outState, int currentItem) {
        outState.putInt(STATE_VIEW_PAGER_POSITION, currentItem);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            int currentItem = savedInstanceState.getInt(STATE_VIEW_PAGER_POSITION);
            this.currentItem = currentItem;
        }
    }

    public void viewPagerCreated() {
        view.setViewPagerState(currentItem);
    }

    public void storeViewPagerState(int currentItem) {
        this.currentItem = currentItem;
    }
}
