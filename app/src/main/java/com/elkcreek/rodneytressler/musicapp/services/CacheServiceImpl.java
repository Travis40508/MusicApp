package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;

public class CacheServiceImpl implements CacheService {

    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;

    public CacheServiceImpl(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid) {
        return getArtistTopTracksFromDatabase(artistUid)
                .flatMap(tracks -> tracks.isEmpty() ? getArtistTracksFromNetwork(artistUid) : Observable.just(tracks));
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid) {
        return musicDatabaseService.getTrackList(artistUid).toObservable();
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY)
                .map(MusicApi.TopTracksResponse::getTopTracks)
                .map(MusicApi.TopTracks::getTrackList)
                .doOnNext(tracks -> {
                    for (MusicApi.Track item : tracks) {
                        musicDatabaseService.insertTrack(item);
                    }
                });
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid) {
        return getArtistBioFromDatabase(artistUid)
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioFromNetwork(artistUid));
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromDatabase(String artistUid) {
        return musicDatabaseService.getArtistBio(artistUid);
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromNetwork(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .map(MusicApi.ArtistBioResponse::getArtist);
    }
}