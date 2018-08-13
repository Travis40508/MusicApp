package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;

public class RepositoryServiceImpl implements RepositoryService {

    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;

    public RepositoryServiceImpl(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid) {
        return getArtistTopTracksFromDatabase(artistUid)
                .flatMap(tracks -> tracks.isEmpty() ? Observable.error(Throwable::new) : Observable.just(tracks))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistTracksFromNetwork(artistUid));
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid) {
        return musicDatabaseService.getTrackList(artistUid);
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY)
                .map(MusicApi.TopTracksResponse::getTopTracks)
                .map(MusicApi.TopTracks::getTrackList)
                .doOnNext(musicDatabaseService::insertTopTracks);
    }


    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid, String artistName) {
        return getArtistBioFromDatabase(artistUid)
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
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
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .doOnNext(musicDatabaseService::updateTopArtist);
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithName(String artistName, String apiKey) {
        return getArtistBioWithNameFromDatabase(artistName)
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioWithNameFromNetwork(artistName));
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromDatabase(String artistName) {
        return musicDatabaseService.getArtistBioWithName(artistName);
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromNetwork(String artistName) {
        return musicApiService.getArtistBioWithName(artistName, Constants.API_KEY)
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse);
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtists() {
        return getTopArtistsFromDatabase()
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTopArtistsFromNetwork());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromDatabase() {
        return musicDatabaseService.getTopArtists();
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromNetwork() {
        return musicApiService.getTopArtists(Constants.API_KEY)
                .map(topArtistsResponse -> topArtistsResponse.getArtists().getArtistList())
                .doOnNext(artists -> Observable.fromIterable(artists)
                .forEach(musicDatabaseService::insertTopArtist));
    }

    @Override
    public void deleteTopArtists() {
        musicDatabaseService.deleteTopArtists();
    }

    @Override
    public Observable<MusicApi.Track> getTrack(String trackUid) {
        return getTrackFromDatabase(trackUid)
                .flatMap(track -> track.getWiki() == null ? Observable.error(Throwable::new) : Observable.just(track))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTrackFromNetwork(trackUid));
    }

    @Override
    public Observable<MusicApi.Track> getTrackFromDatabase(String trackUid) {
        return musicDatabaseService.getTrack(trackUid);
    }

    @Override
    public Observable<MusicApi.Track> getTrackFromNetwork(String trackUid) {
        return musicApiService.getTrackInfo(trackUid, Constants.API_KEY)
                .map(MusicApi.TrackInfoResponse::getTrack)
                .doOnNext(musicDatabaseService::insertTrack)
                .doOnNext(musicDatabaseService::updateTrack);
    }
}
