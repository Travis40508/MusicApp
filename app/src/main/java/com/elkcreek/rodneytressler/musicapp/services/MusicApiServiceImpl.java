package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MusicApiServiceImpl implements MusicApiService {

    private final MusicApi musicApi;

    public MusicApiServiceImpl(MusicApi musicApi) {
        this.musicApi = musicApi;
    }


    @Override
    public Observable<MusicApi.SearchResponse> getArtistSearchResults(String artist, String apiKey) {
        return musicApi.getArtistSearchResults(artist, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
