package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SearchPresenter implements BasePresenter<SearchView> {

    private final MusicApiService musicApiService;
    private SearchView view;

    @Inject
    public SearchPresenter(MusicApiService musicApiService) {
        this.musicApiService = musicApiService;
    }

    @Override
    public void attachView(SearchView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void artistSearchTextChanged(String artistSearchText, boolean adapterHasItems) {
        if(!adapterHasItems) {
            view.showProgressBar();
        }
        musicApiService.getArtistSearchResults(artistSearchText, Constants.API_KEY)
                .onErrorResumeNext(Observable.empty())
                .subscribe(searchResponse -> {
                    view.hideProgressBar();
                    view.loadArtists(searchResponse.getSearchResults().getArtistMatches().getArtistList());
                });
    }

    public void artistClicked(MusicApi.Artist artist) {
        view.showBioFragment(artist);
    }
}
