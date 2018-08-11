package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BioPresenter implements BasePresenter<BioView> {

    private final RepositoryService repositoryService;
    private BioView view;
    private CompositeDisposable disposable;
    private String artistUid;
    private boolean isExpanded;
    private static final String READ_MORE_TEXT_COLLAPSE = "Collapse";
    private static final String READ_MORE_TEXT_EXPAND = "Read More";
    private String artistName;

    @Inject
    public BioPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(BioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        isExpanded = false;
        view.showLoadingLayout();
        fetchBio();
    }

    private void fetchBio() {
        disposable.add(repositoryService.getArtistBio(artistUid, artistName).subscribe(updateUiWithArtist(), updateUiOnError()));
    }


    private Consumer<MusicApi.Artist> updateUiWithArtist() {
        return artist -> {
            if (!isExpanded) {
                view.showArtistBio(artist.getArtistBio().getBioSummary());
            } else {
                view.showArtistBio(artist.getArtistBio().getBioContent());
            }
            view.hideLoadingLayout();
            view.showArtistImage(artist.getArtistImages().get(2).getImageUrl());
            view.showArtistName(artist.getArtistName());
            view.showSimilarArtists(artist.getSimilar().getArtistList());
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
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
        this.artistName = artistName;
        view.showArtistName(artistName);
    }

    public void readMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase("Read More")) {
            view.setReadMoreText(READ_MORE_TEXT_COLLAPSE);
        } else {
            view.setReadMoreText(READ_MORE_TEXT_EXPAND);
        }
        fetchBio();
    }

    public void similarArtistClicked(MusicApi.Artist artist) {
        disposable.add(repositoryService.getArtistBioWithName(artist.getArtistName(), Constants.API_KEY).subscribe(
                similarArtist -> {
                    view.showSimilarArtistScreen(similarArtist.getArtistUID(), similarArtist.getArtistName());
                }
        ));
    }

    public void viewTracksClicked() {
        view.showTracksFragment(artistUid, artistName);
    }

    public void checkSavedInstanceState(boolean saveInstanceStateIsNull, boolean tracksFragmentIsNull, boolean bioFragmentIsNull) {
        if (!saveInstanceStateIsNull) {
            if (!tracksFragmentIsNull) {
                view.reAttachTracksFragment();
            } else if (!bioFragmentIsNull) {
                view.reAttachBioFragment();
            }
        }
    }

    public void homeClicked() {
        view.clearBackStack();
    }
}
