package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;

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
    public Observable<String> getYoutubeVideoId(String trackUid, String searchQuery) {
        return getYoutubeVideoFromDatabase(trackUid)
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getYoutubeVideoFromNetwork(trackUid, searchQuery));
    }

    @Override
    public Observable<String> getYoutubeVideoFromDatabase(String trackUid) {
        return musicDatabaseService.getTrack(trackUid)
                .map(MusicApi.Track::getYoutubeId);
    }

    @Override
    public Observable<String> getYoutubeVideoFromNetwork(String trackUid, String searchQuery) {
        return youtubeApiService.getYoutubeVideo(Constants.YOUTUBE_API_KEY, searchQuery)
                .map(youtubeResponse -> youtubeResponse.getYoutubeItemsList().get(0).getYoutubeItemId().getYoutubeVideoId())
                .doOnNext(videoId -> musicDatabaseService.updateTrackWithYoutubeId(videoId, trackUid));
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbums(String artistUid) {
        return getAlbumsFromDatabase(artistUid)
                .flatMap(albumList -> albumList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(albumList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getAlbumsFromNetwork(artistUid));
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromDatabase(String artistUid) {
        return musicDatabaseService.getAlbumList(artistUid);
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumsFromNetwork(String artistUid) {
        return musicApiService.getTopAlbums(Constants.API_KEY, artistUid)
                .doOnNext(musicDatabaseService::insertAlbums);
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbum(String albumUid) {
        return getTracksFromAlbumFromDatabase(albumUid)
                .flatMap(trackList -> trackList.get(0) == null ? Observable.error(Throwable::new) : Observable.just(trackList))
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getTracksFromAlbumFromNetwork(albumUid));
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromDatabase(String albumUid) {
        return musicDatabaseService.getAlbumByUid(albumUid)
                .map(MusicApi.Album::getTrackList);
    }

    @Override
    public Observable<List<MusicApi.Track>> getTracksFromAlbumFromNetwork(String albumUid) {
        return musicApiService.getAlbumInfo(Constants.API_KEY, albumUid)
                .map(albumInfo -> albumInfo.getTracksResponse().getTrackList())
                .doOnNext(trackList -> musicDatabaseService.updateAlbumWithAlbumUid(trackList, albumUid));
    }

    @Override
    public Observable<MusicApi.Track> getTrackWithName(String trackName, String artistName) {
        return musicApiService.getTrackInfoWithName(trackName, artistName, Constants.API_KEY)
                .map(MusicApi.TrackInfoResponse::getTrack)
                .doOnNext(musicDatabaseService::updateTrackWithUid);
    }


    @Override
    public Observable<MusicApi.Track> getTrackFromNetwork(String trackUid) {
        return musicApiService.getTrackInfo(trackUid, Constants.API_KEY)
                .map(MusicApi.TrackInfoResponse::getTrack)
                .doOnNext(musicDatabaseService::updateTrack);
    }
}
