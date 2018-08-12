package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.YoutubeApi;

import io.reactivex.Observable;

public interface YoutubeApiService {

    Observable<YoutubeApi.YoutubeResponse> getYoutubeVideo(String youtubeApiKey, String searchQuery);
}
