package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TrackSearchPresenter implements BasePresenter<TrackSearchView> {

    private CompositeDisposable disposable;
    private TrackSearchView view;

    @Inject
    public TrackSearchPresenter() {

    }

    @Override
    public void attachView(TrackSearchView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }
}
