package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.YoutubeApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class YoutubeApiServiceImpl implements YoutubeApiService {

    private YoutubeApi youtubeApi;

    public YoutubeApiServiceImpl(YoutubeApi youtubeApi) {
        this.youtubeApi = youtubeApi;
    }

    @Override
    public Observable<YoutubeApi.YoutubeResponse> getYoutubeVideo(String youtubeApiKey, String searchQuery) {
        return youtubeApi.getYoutubeVideo(youtubeApiKey, searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
