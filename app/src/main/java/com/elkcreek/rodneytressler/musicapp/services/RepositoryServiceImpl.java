package com.elkcreek.rodneytressler.musicapp.services;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.LyricsApi;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepositoryServiceImpl implements RepositoryService {

    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;
    private final YoutubeApiService youtubeApiService;
    private final LyricsApiService lyricsApiService;

    public RepositoryServiceImpl(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService, YoutubeApiService youtubeApiService, LyricsApiService lyricsApiService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
        this.youtubeApiService = youtubeApiService;
        this.lyricsApiService = lyricsApiService;
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid) {
        return getArtistTopTracksFromDatabase(artistUid)
                .subscribeOn(Schedulers.io())
                .flatMap(tracks -> tracks.isEmpty() ? Observable.error(Throwable::new) : Observable.just(tracks))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistTracksFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid) {
        return musicDatabaseService.getTrackList(artistUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TopTracksResponse::getTopTracks)
                .map(MusicApi.TopTracks::getTrackList)
                .doOnNext(musicDatabaseService::insertTopTracks);
    }

    @Override
    public Observable<List<MusicApi.SearchedTrack>> getSearchedTracksFromNetwork(String searchedTrack) {
        return musicApiService.getSearchedTracks(searchedTrack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid, String artistName) {
        return getArtistBioFromDatabase(artistUid)
                .subscribeOn(Schedulers.io())
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromDatabase(String artistUid) {
        return musicDatabaseService.getArtistBio(artistUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioFromNetwork(String artistUid) {
        return musicApiService.getArtistBio(artistUid, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse)
                .doOnNext(musicDatabaseService::updateTopArtist);
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithName(String artistName, String apiKey) {
        return getArtistBioWithNameFromDatabase(artistName)
                .subscribeOn(Schedulers.io())
                .flatMap(artist -> artist.getArtistBio() == null ? Observable.error(Throwable::new) : Observable.just(artist))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistBioWithNameFromNetwork(artistName))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromDatabase(String artistName) {
        return musicDatabaseService.getArtistBioWithName(artistName).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithNameFromNetwork(String artistName) {
        return musicApiService.getArtistBioWithName(artistName, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.ArtistBioResponse::getArtist)
                .doOnNext(musicDatabaseService::insertBioResponse);
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtists() {
        return getTopArtistsFromDatabase()
                .flatMap(artists -> artists.get(0) == null ? Observable.error(Throwable::new) : Observable.just(artists))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTopArtistsFromNetwork())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromDatabase() {
        return musicDatabaseService.getTopArtists().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtistsFromNetwork() {
        return musicApiService.getTopArtists(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TopArtistsResponse::getTopArtists)
                .doOnNext(musicDatabaseService::insertTopArtist)
                .map(MusicApi.TopArtists::getTopArtistList);
    }

    @Override
    public void deleteTopArtists() {
        musicDatabaseService.deleteTopArtists();
    }

    @Override
    public Observable<List<MusicApi.Track>> getTopTracks() {
        return getTopTracksFromDatabase()
                .flatMap(trackList -> trackList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(trackList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTopTracksFromNetwork())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getTopTracksFromDatabase() {
        return musicDatabaseService.getTopChartTracks()
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TopChartTracks::getTrackList);
    }

    @Override
    public Observable<List<MusicApi.Track>> getTopTracksFromNetwork() {
        return musicApiService.getTopTracksList(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .doOnNext(musicDatabaseService::insertTopTracks)
                .map(MusicApi.TopChartTracks::getTrackList);
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName) {
        return musicApiService.getTrackInfoWithName(trackName, artistName, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TrackInfoResponse::getTrackInfo)
                .observeOn(AndroidSchedulers.mainThread());
//                .doOnNext(trackInfo -> musicDatabaseService.updateTopTracksList(trackInfo.getArtistInfo().getArtistName(), trackInfo.getTrackName(), trackInfo.getTrackUid(), trackList));
    }

    //TODO make sure that wiki is what we should be null-checking here
    @Override
    public Observable<MusicApi.TrackInfo> getTrack(String trackUid) {
        return getTrackFromDatabase(trackUid)
                .subscribeOn(Schedulers.io())
                .flatMap(track -> track.getWiki() == null ? Observable.error(Throwable::new) : Observable.just(track))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTrackFromNetwork(trackUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackFromDatabase(String trackUid) {
        return musicDatabaseService.getTrackInfo(trackUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<String> getYoutubeVideoId(String trackUid, String searchQuery) {
        return getTrackInfoYoutubeIdFromDatabase(trackUid)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getYoutubeVideoFromNetwork(trackUid, searchQuery))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> getTrackInfoYoutubeIdFromDatabase(String trackUid) {
        return musicDatabaseService.getTrackInfoYoutubeId(trackUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<String> getYoutubeVideoFromNetwork(String trackUid, String searchQuery) {
        return youtubeApiService.getYoutubeVideo(Constants.YOUTUBE_API_KEY, searchQuery)
                .subscribeOn(Schedulers.io())
                .map(youtubeResponse -> youtubeResponse.getYoutubeItemsList().get(0).getYoutubeItemId().getYoutubeVideoId())
                .doOnNext(id -> musicDatabaseService.updateTrackInfoWithYoutubeIdViaTrackUid(id, trackUid));
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbums(String artistUid) {
        return getAlbumsFromDatabase(artistUid)
                .subscribeOn(Schedulers.io())
                .flatMap(albumList -> albumList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(albumList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getAlbumsFromNetwork(artistUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromDatabase(String artistUid) {
        return musicDatabaseService.getAlbumList(artistUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromNetwork(String artistUid) {
        return musicApiService.getTopAlbums(Constants.API_KEY, artistUid)
                .subscribeOn(Schedulers.io())
                .doOnNext(musicDatabaseService::insertAlbums);
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbum(String albumUid) {
        return getTracksFromAlbumFromDatabase(albumUid)
                .subscribeOn(Schedulers.io())
                .flatMap(trackList -> trackList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(trackList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTracksFromAlbumFromNetwork(albumUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromDatabase(String albumUid) {
        return musicDatabaseService.getAlbumByUid(albumUid)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.Album::getTrackList);
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromNetwork(String albumUid) {
        return musicApiService.getAlbumInfo(Constants.API_KEY, albumUid)
                .subscribeOn(Schedulers.io())
                .map(albumInfo -> albumInfo.getTracksResponse().getTrackList())
                .doOnNext(trackList -> musicDatabaseService.updateAlbumWithAlbumUid(trackList, albumUid));
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName, List<MusicApi.Track> trackList, String albumUid) {
        return musicApiService.getTrackInfoWithName(trackName, artistName, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TrackInfoResponse::getTrackInfo)
                .doOnNext(trackInfo -> musicDatabaseService.updateTrackWithUid(trackInfo, trackList, albumUid))
                .doOnNext(musicDatabaseService::insertTrackInfo)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid) {
        return getAlbumInfoFromDatabase(albumUid)
                .subscribeOn(Schedulers.io())
                .flatMap(albumInfo -> albumInfo.getAlbumName() == null ? Observable.error(Throwable::new) : Observable.just(albumInfo))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getAlbumInfoFromNetwork(albumUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfoFromDatabase(String albumUid) {
        return musicDatabaseService.getAlbumInfo(albumUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfoFromNetwork(String albumUid) {
        return musicApiService.getAlbumInfo(Constants.API_KEY, albumUid)
                .subscribeOn(Schedulers.io())
                .doOnNext(musicDatabaseService::insertAlbumInfo);
    }

    @Override
    public Observable<String> getLyrics(String artistName, String songTitle, String trackUid) {
        return getLyricsFromDatabase(trackUid)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(getLyricsFromNetwork(artistName, songTitle, trackUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> getLyricsFromDatabase(String trackUid) {
        return musicDatabaseService.getSongLyrics(trackUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<String> getLyricsFromNetwork(String artistName, String songTitle, String trackUid) {
        return lyricsApiService.getLyrics(artistName, songTitle)
                .subscribeOn(Schedulers.io())
                .map(LyricsApi.LyricsResponse::getSongLyrics)
                .doOnNext(lyrics -> musicDatabaseService.updateTrackInfoWithSongLyrics(lyrics, trackUid));
    }

    @Override
    public Observable<List<MusicApi.Track>> getSimilarTrackList(String trackUid) {
        return getSimilarTrackListFromDatabase(trackUid)
                .subscribeOn(Schedulers.io())
                .flatMap(trackList -> trackList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(trackList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getSimilarTrackListFromNetwork(trackUid))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<MusicApi.Track>> getSimilarTrackListFromDatabase(String trackUid) {
        return musicDatabaseService.getSimilarTracks(trackUid).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Track>> getSimilarTrackListFromNetwork(String trackUid) {
        return musicApiService.getListOfSimilarTracks(trackUid)
                .subscribeOn(Schedulers.io())
                .doOnNext(trackList -> musicDatabaseService.updateTrackInfoWithSimilarArtists(trackList, trackUid));
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
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TrackInfoResponse::getTrackInfo)
                .doOnNext(musicDatabaseService::updateTrack)
                .doOnNext(musicDatabaseService::insertTrackInfo);
    }
}
