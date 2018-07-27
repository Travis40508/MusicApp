package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

public class MainPresenter implements BasePresenter<MainView> {

    private MainView view;

    @Inject
    public MainPresenter() {

    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
        if(view != null) {
            view.attachSearchFragment();
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
