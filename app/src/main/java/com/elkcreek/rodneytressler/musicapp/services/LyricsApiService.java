package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.LyricsApi;

import io.reactivex.Observable;

public interface LyricsApiService {
    Observable<LyricsApi.LyricsResponse> getLyrics(String artistName, String songTitle);
}
