package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface CacheService {

    Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid);
    Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid);
    Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid);
}
