package com.elkcreek.rodneytressler.musicapp.ui.SearchMainView;

import android.os.Bundle;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.logging.Handler;

import javax.inject.Inject;

public class SearchMainPresenter implements BasePresenter<SearchMainView> {


    private SearchMainView view;
    private static final String STATE_VIEW_PAGER_POSITION = "state_view_pager_position";
    private int currentItem;

    @Inject
    public SearchMainPresenter() {

    }

    @Override
    public void attachView(SearchMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        view.showScreens();
    }

    @Override
    public void unsubscribe() {

    }

    public void saveState(Bundle outState, int currentItem) {
        outState.putInt(STATE_VIEW_PAGER_POSITION, currentItem);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            this.currentItem = savedInstanceState.getInt(STATE_VIEW_PAGER_POSITION);
        }
    }

    public void viewPagerCreated() {
        view.setViewPagerPage(currentItem);
    }

    public void storeViewPagerState(int currentItem) {
        this.currentItem = currentItem;
    }
}
