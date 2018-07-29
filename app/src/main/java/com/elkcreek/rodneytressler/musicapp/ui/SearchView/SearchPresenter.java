package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SearchPresenter implements BasePresenter<SearchView> {

    private final MusicApiService musicApiService;
    private SearchView view;
    private CompositeDisposable disposable;

    @Inject
    public SearchPresenter(MusicApiService musicApiService) {
        this.musicApiService = musicApiService;
    }

    @Override
    public void attachView(SearchView view) {
        this.view = view;
    }

    private Observable<MusicApi.TopArtistsResponse> getTopArtists() {
        return musicApiService.getTopArtists(Constants.API_KEY).onErrorResumeNext(Observable.empty());
    }

    private Observable<MusicApi.SearchResponse> getArtistSearchResults(String artistSearchText) {
        return musicApiService.getArtistSearchResults(artistSearchText, Constants.API_KEY).onErrorResumeNext(Observable.empty());
    }

    private Consumer<MusicApi.TopArtistsResponse> updateViewWithTopArtist() {
        return topArtists -> {
            view.loadArtists(topArtists.getArtists().getArtistList());
            view.hideProgressBar();
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            view.showErrorLoadingToast();
        };
    }

    private Consumer<MusicApi.SearchResponse> getSearchResponse() {
        return searchResponse -> {
            view.hideProgressBar();
            view.loadArtists(searchResponse.getSearchResults().getArtistMatches().getArtistList());
        };
    }

    @Override
    public void subscribe() {
        if (disposable == null) {
            view.showProgressBar();
        }
        disposable = new CompositeDisposable();
        disposable.add(getTopArtists().subscribe(
                updateViewWithTopArtist()
        ));
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void artistSearchTextChanged(String artistSearchText, boolean adapterHasItems) {
        if (!adapterHasItems) {
            view.showProgressBar();
        }
        if (!artistSearchText.isEmpty()) {
            view.showSearchTextValue(artistSearchText);
            disposable.add(getArtistSearchResults(artistSearchText)
                    .subscribe(getSearchResponse(), updateUiWithError()));

        } else {
            view.showSearchTextTopArtists();
            disposable.add(getTopArtists().subscribe(updateViewWithTopArtist(), updateUiWithError()));
        }

    }

    public void onArtistInfoClicked(MusicApi.Artist artist) {
        view.showBioFragment(artist);
    }

    public void onArtistMusicClicked(MusicApi.Artist artist) {
        view.showArtistTracks(artist);
    }

}
