package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface MusicDatabaseService {

    void insertTrack(MusicApi.Track track);

    Flowable<List<MusicApi.Track>> getTrackList();

    void deleteTracks();
}
