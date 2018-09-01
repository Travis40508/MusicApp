package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.LyricsApi;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LyricsApiServiceImpl implements LyricsApiService {

    private final LyricsApi lyricsApi;

    public LyricsApiServiceImpl(LyricsApi lyricsApi) {
        this.lyricsApi = lyricsApi;
    }

    @Override
    public Observable<LyricsApi.LyricsResponse> getLyrics(String artistName, String songTitle) {
        return lyricsApi.getLyrics(artistName, songTitle)
                .subscribeOn(Schedulers.io());
    }
}
