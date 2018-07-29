package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BioPresenter implements BasePresenter<BioView> {
    private final MusicApiService musicApiService;
    private BioView view;
    private CompositeDisposable disposable;
    private String artistUid;

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
        disposable = new CompositeDisposable();
        disposable.add(getArtistBio(artistUid)
                .subscribe(updateUiWithArtistBio(), updateUiOnError()));
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    private Observable<MusicApi.ArtistBioResponse> getArtistBio(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY);
    }

    private Consumer<MusicApi.ArtistBioResponse> updateUiWithArtistBio() {
        return artistBioResponse -> {
            view.showArtistImage(artistBioResponse.getArtist().getArtistImages().get(3).getImageUrl());
            view.showArtistBio(artistBioResponse.getArtist().getArtistBio().getBioContent());
            view.hideProgressBar();
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            view.detachFragment();
            view.showNoBioToast();
        };
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        view.showArtistName(artistName);
    }
}
