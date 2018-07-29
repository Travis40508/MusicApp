package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
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
        disposable.add(getArtistBio(artistUid)
                .subscribe(updateUiWithArtistBio(), updateUiOnError()));
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    //todo figure out why this can't seem to get passed just querying the database.
    private Observable<MusicApi.ArtistBio> getArtistBio(String artistUid) {
        return getArtistBioFromDatabase(artistUid)
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioFromNetwork(artistUid));
    }

    private Observable<MusicApi.ArtistBio> getArtistBioFromNetwork(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY)
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(artist -> this.artistImage = artist.getArtistImages().get(2).getImageUrl())
                .map(MusicApi.Artist::getArtistBio)
                .doOnNext(artistBio -> {
                    if(artistBio.getArtistImage() == null || artistBio.getArtistImage().isEmpty()) {
                        artistBio.setArtistImage(this.artistImage);
                    } else if (artistBio.getArtistUid() == null || artistBio.getArtistUid().isEmpty()) {
                        artistBio.setArtistUid(artistUid);
                    }
                    musicDatabaseService.insertBio(artistBio);
                });
    }

    private Observable<MusicApi.ArtistBio> getArtistBioFromDatabase(String artistUid) {
        return musicDatabaseService.getArtistBio(artistUid).toObservable();
    }

    private Consumer<MusicApi.ArtistBio> updateUiWithArtistBio() {
        return artistBio -> {
          view.showArtistImage(artistBio.getArtistImage());
          view.showArtistBio(artistBio.getBioContent());
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
