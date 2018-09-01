package com.elkcreek.rodneytressler.musicapp.services;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RepositoryServiceImpl implements RepositoryService {

    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;
    private final YoutubeApiService youtubeApiService;

    public RepositoryServiceImpl(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService, YoutubeApiService youtubeApiService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
        this.youtubeApiService = youtubeApiService;
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid) {
        return getArtistTopTracksFromDatabase(artistUid)
                .flatMap(tracks -> tracks.isEmpty() ? Observable.error(Throwable::new) : Observable.just(tracks))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistTracksFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid) {
        return musicDatabaseService.getTrackList(artistUid).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY)
                .map(MusicApi.TopTracksResponse::getTopTracks)
                .map(MusicApi.TopTracks::getTrackList)
                .doOnNext(musicDatabaseService::insertTopTracks)
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid, String artistName) {
        return getArtistBioFromDatabase(artistUid)
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromDatabase(String artistUid) {
        return musicDatabaseService.getArtistBio(artistUid).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromNetwork(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY)
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .doOnNext(musicDatabaseService::updateTopArtist)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithName(String artistName, String apiKey) {
        return getArtistBioWithNameFromDatabase(artistName)
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioWithNameFromNetwork(artistName))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromDatabase(String artistName) {
        return musicDatabaseService.getArtistBioWithName(artistName)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromNetwork(String artistName) {
        return musicApiService.getArtistBioWithName(artistName, Constants.API_KEY)
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtists() {
        return getTopArtistsFromDatabase()
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTopArtistsFromNetwork())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromDatabase() {
        return musicDatabaseService.getTopArtists()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromNetwork() {
        return musicApiService.getTopArtists(Constants.API_KEY)
                .map(topArtistsResponse -> topArtistsResponse.getArtists().getArtistList())
                .doOnNext(artists -> Observable.fromIterable(artists)
                .forEach(musicDatabaseService::insertTopArtist))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteTopArtists() {
        musicDatabaseService.deleteTopArtists();
    }

    //TODO make sure that wiki is what we should be null-checking here
    @Override
    public Observable<MusicApi.TrackInfo> getTrack(String trackUid) {
        return getTrackFromDatabase(trackUid)
                .flatMap(track -> track.getWiki() == null ? Observable.error(Throwable::new) : Observable.just(track))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTrackFromNetwork(trackUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackFromDatabase(String trackUid) {
        return musicDatabaseService.getTrackInfo(trackUid).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> getYoutubeVideoId(String trackUid, String searchQuery) {
        return getTrackInfoYoutubeIdFromDatabase(trackUid)
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getYoutubeVideoFromNetwork(trackUid, searchQuery))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> getTrackInfoYoutubeIdFromDatabase(String trackUid) {
        return musicDatabaseService.getTrackInfoYoutubeId(trackUid);
    }

    @Override
    public Observable<String> getYoutubeVideoFromNetwork(String trackUid, String searchQuery) {
        return youtubeApiService.getYoutubeVideo(Constants.YOUTUBE_API_KEY, searchQuery)
                .map(youtubeResponse -> youtubeResponse.getYoutubeItemsList().get(0).getYoutubeItemId().getYoutubeVideoId())
                .doOnNext(id -> musicDatabaseService.updateTrackInfoWithYoutubeIdViaTrackUid(id, trackUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbums(String artistUid) {
        return getAlbumsFromDatabase(artistUid)
                .flatMap(albumList -> albumList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(albumList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getAlbumsFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromDatabase(String artistUid) {
        return musicDatabaseService.getAlbumList(artistUid).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromNetwork(String artistUid) {
        return musicApiService.getTopAlbums(Constants.API_KEY, artistUid)
                .doOnNext(musicDatabaseService::insertAlbums)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbum(String albumUid) {
        return getTracksFromAlbumFromDatabase(albumUid)
                .flatMap(trackList -> trackList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(trackList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTracksFromAlbumFromNetwork(albumUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromDatabase(String albumUid) {
        return musicDatabaseService.getAlbumByUid(albumUid)
                .map(MusicApi.Album::getTrackList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromNetwork(String albumUid) {
        return musicApiService.getAlbumInfo(Constants.API_KEY, albumUid)
                .map(albumInfo -> albumInfo.getTracksResponse().getTrackList())
                .doOnNext(trackList -> musicDatabaseService.updateAlbumWithAlbumUid(trackList, albumUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName, List<MusicApi.Track> trackList, String albumUid) {
        return musicApiService.getTrackInfoWithName(trackName, artistName, Constants.API_KEY)
                .map(MusicApi.TrackInfoResponse::getTrackInfo)
                .doOnNext(trackInfo -> musicDatabaseService.updateTrackWithUid(trackInfo, trackList, albumUid))
                .doOnNext(musicDatabaseService::insertTrackInfo)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid) {
        return getAlbumInfoFromDatabase(albumUid)
                .flatMap(albumInfo -> albumInfo.getAlbumName() == null ? Observable.error(Throwable::new) : Observable.just(albumInfo))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getAlbumInfoFromNetwork(albumUid));
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfoFromDatabase(String albumUid) {
        return musicDatabaseService.getAlbumInfo(albumUid);
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfoFromNetwork(String albumUid) {
        return musicApiService.getAlbumInfo(Constants.API_KEY, albumUid)
                .doOnNext(musicDatabaseService::insertAlbumInfo);
    }

    @Override
    public void clearCache() {
        musicDatabaseService.clearCache();
    }

    @Override
    public void saveDate() {
        musicDatabaseService.saveDate();
    }

    @Override
    public boolean isSameWeekSinceLastLaunch() {
        return musicDatabaseService.isSameWeekSinceLastLaunch();
    }

    @Override
    public void resetDate() {
        musicDatabaseService.resetDate();
    }


    @Override
    public Observable<MusicApi.TrackInfo> getTrackFromNetwork(String trackUid) {
        return musicApiService.getTrackInfo(trackUid, Constants.API_KEY)
                .map(MusicApi.TrackInfoResponse::getTrackInfo)
                .doOnNext(musicDatabaseService::updateTrack)
                .doOnNext(musicDatabaseService::insertTrackInfo)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
