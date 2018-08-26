package com.elkcreek.rodneytressler.musicapp.ui.TrackMainView;

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

    public void screenPaused(int currentItem) {
        this.currentItem = currentItem;
    }

    public void screenRestored() {
        if(currentItem != 0) {
            view.setViewPagerItem(currentItem);
        }
    }
}
