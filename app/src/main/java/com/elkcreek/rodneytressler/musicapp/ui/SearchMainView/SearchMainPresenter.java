package com.elkcreek.rodneytressler.musicapp.ui.SearchMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

public class SearchMainPresenter implements BasePresenter<SearchMainView> {


    private SearchMainView view;

    @Inject
    public SearchMainPresenter() {

    }

    @Override
    public void attachView(SearchMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
