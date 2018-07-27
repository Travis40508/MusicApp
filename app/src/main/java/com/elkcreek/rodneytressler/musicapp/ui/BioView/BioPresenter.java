package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

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

    public void artistRetrieved(String artistName) {
        musicApiService.getArtistBio(artistName, Constants.API_KEY)
                .subscribe(artistBioResponse -> {
                    view.showArtistImage(artistBioResponse.getArtist().getArtistImages().get(3).getImageUrl());
                    view.showArtistBio(artistBioResponse.getArtist().getArtistBio().getBioContent());
                });
    }
}
