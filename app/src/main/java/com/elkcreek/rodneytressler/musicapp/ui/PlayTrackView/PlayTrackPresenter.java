package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

public class PlayTrackPresenter implements BasePresenter<PlayTrackView> {

    PlayTrackView view;

    @Inject
    public PlayTrackPresenter() {

    }

    @Override
    public void attachView(PlayTrackView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void trackUrlRetrieved(String trackUrl) {
        view.showTrackWebView(trackUrl);
    }
}
