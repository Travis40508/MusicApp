package com.elkcreek.rodneytressler.musicapp.utils;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void subscribe();
    void unsubscribe();
}
