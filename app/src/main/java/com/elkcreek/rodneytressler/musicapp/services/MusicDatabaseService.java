package com.elkcreek.rodneytressler.musicapp.services;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface MusicDatabaseService {

    void insertTrack(MusicApi.Track track);

    Flowable<List<MusicApi.Track>> getTrackList(String artistUid);

    void insertBio(MusicApi.ArtistBio artistBio);

    Flowable<MusicApi.ArtistBio> getArtistBio(String artistUid);
}
