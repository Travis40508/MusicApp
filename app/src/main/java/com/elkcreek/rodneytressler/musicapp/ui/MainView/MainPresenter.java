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

    public void backPressed(int backStackEntryCount) {
        if(backStackEntryCount > 0) {
            view.detachImmediateFragment();
            view.hideMainLoadingLayout();
        } else {
            view.closeApp();
        }
    }

    public void homeClicked() {
        view.returnHome();
    }

    public void systemHomeButtonPressed(int sdkInt) {
        if(sdkInt >= 24) {
            view.enterPipMode();
        }
    }
}
