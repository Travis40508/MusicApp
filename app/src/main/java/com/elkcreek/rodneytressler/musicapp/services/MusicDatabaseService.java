package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface MusicDatabaseService {

    void insertTrack(MusicApi.Track track);

    Flowable<List<MusicApi.Track>> getTrackList(String artistUid);

    void insertBioResponse(MusicApi.Artist artist);

    Observable<MusicApi.Artist> getArtistBio(String artistUid);

    void insertTopArtist(MusicApi.Artist artist);
    Observable<List<MusicApi.Artist>> getTopArtists();


}
