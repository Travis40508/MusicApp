package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

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
        if(view != null) {
            musicApiService.getArtistSearchResults("Pearl Jam", Constants.API_KEY)
                    .subscribe(searchResponse -> {
                        MusicApi.Artist artist = searchResponse.getSearchResults().getArtistMatches().getArtistList().get(0);
                        Log.d("@@@@", artist.getArtistImages().get(3).getImageUrl());
                    });
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
