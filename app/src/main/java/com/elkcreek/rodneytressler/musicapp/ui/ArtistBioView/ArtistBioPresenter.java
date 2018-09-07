package com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ArtistBioPresenter implements BasePresenter<ArtistBioView> {

    private final RepositoryService repositoryService;
    private ArtistBioView view;
    private CompositeDisposable disposable;
    private String artistUid;
    private boolean isExpanded;
    private String artistName;

    @Inject
    public ArtistBioPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(ArtistBioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        isExpanded = false;
        fetchBio();
    }

    private void fetchBio() {
        disposable.add(repositoryService.getArtistBio(artistUid, artistName).subscribe(updateUiWithArtist(), updateUiOnError()));
    }


    private Consumer<MusicApi.Artist> updateUiWithArtist() {
        return artist -> {
            if (!isExpanded) {
                view.showArtistBio(artist.getArtistBio() != null ? artist.getArtistBio().getBioSummary() : Constants.NO_ARTIST_BIO_AVAILABLE);
            } else {
                view.showArtistBio(artist.getArtistBio() != null ? artist.getArtistBio().getBioContent() : Constants.NO_ARTIST_BIO_AVAILABLE);
            }

            if(artist.getArtistImages() != null) {
                view.showArtistImage(artist.getArtistImages().get(2).getImageUrl());
            } else {
                view.setImageBackgroundWhite();
                view.showGenericArtistImage();
            }

            if(artist.getSimilar() != null) {
                view.showSimilarArtists(artist.getSimilar().getArtistList());
            } else {
                view.showNoSimilarArtistText();
            }

            view.hideMainProgressBar();
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }


    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
            view.showArtistBio(Constants.NO_ARTIST_BIO_AVAILABLE);
            view.setImageBackgroundWhite();
            view.showGenericArtistImage();
            view.showNoSimilarArtistText();
            view.hideMainProgressBar();
        };
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
    }

    public void readMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase(Constants.READ_MORE)) {
            view.setReadMoreText(Constants.COLLAPSE);
        } else {
            view.setReadMoreText(Constants.READ_MORE);
        }
        fetchBio();
    }

    public void similarArtistClicked(MusicApi.Artist artist) {
        view.showLoadingLayout();
        view.setTitle(artist.getArtistName());

        //TODO is this necessary? Can't I just pass the information to the viewpager and let it handle the rest?
        disposable.add(repositoryService.getArtistBioWithName(artist.getArtistName(), Constants.API_KEY).subscribe(
                similarArtist -> {
                    view.showSimilarArtistScreen(similarArtist.getArtistUID(), similarArtist.getArtistName());
                }, throwable -> {
                    Log.d("@@@@", throwable.getMessage());
                }
        ));
    }
}
