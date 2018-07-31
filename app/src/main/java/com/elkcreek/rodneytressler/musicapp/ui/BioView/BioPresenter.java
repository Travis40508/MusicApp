package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.media.MediaPlayer;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BioPresenter implements BasePresenter<BioView> {
    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;
    private BioView view;
    private CompositeDisposable disposable;
    private String artistUid;
    private String artistImage;

    @Inject
    public BioPresenter(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
    }

    @Override
    public void attachView(BioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(getArtistBio(artistUid).subscribe(updateUiWithArtist(), updateUiOnError()));
    }

    private Observable<MusicApi.Artist> getArtistBio(String artistUid) {
        return getArtistBioFromDatabase(artistUid)
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioFromNetwork(artistUid));
    }

    private Observable<MusicApi.Artist> getArtistBioFromDatabase(String artistUid) {
        return musicDatabaseService.getArtistBio(artistUid);
    }

    private Observable<MusicApi.Artist> getArtistBioFromNetwork(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .map(MusicApi.ArtistBioResponse::getArtist);
    }

    private Consumer<MusicApi.Artist> updateUiWithArtist() {
        return artist -> {
          view.hideProgressBar();
          view.showArtistBio(artist.getArtistBio().getBioContent());
          view.showArtistImage(artist.getArtistImages().get(2).getImageUrl());
          view.showArtistName(artist.getArtistName());
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }


    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
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
