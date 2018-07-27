package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

public class BioPresenter implements BasePresenter<BioView> {
    private final MusicApiService musicApiService;
    private BioView view;

    @Inject
    public BioPresenter(MusicApiService musicApiService) {
        this.musicApiService = musicApiService;
    }

    @Override
    public void attachView(BioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
