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
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void backPressed(int backStackEntryCount, boolean toolBarIsGone) {
        if(!toolBarIsGone) {
            if (backStackEntryCount > 0) {
                view.detachImmediateFragment();
                view.hideMainLoadingLayout();
            } else {
                view.closeApp();
            }
        } else {
            view.setOrientationToPortait();
        }
    }

    public void homeClicked() {
        view.returnHome();
    }

    public void screenRotated(boolean screenNeverRotated) {
        if(!screenNeverRotated) {
            view.reAttachSearchFragment();
        } else {
            view.attachSearchFragment();
        }
    }
}
