package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface MusicDatabaseService {

    void insertTrack(MusicApi.Track track);

    Observable<List<MusicApi.Track>> getTrackList(String artistUid);

    void insertBioResponse(MusicApi.Artist artist);

    Observable<MusicApi.Artist> getArtistBio(String artistUid);
    Observable<MusicApi.Artist> getArtistBioWithName(String artistName);

    void insertTopArtist(MusicApi.TopArtists topArtists);
    Observable<List<MusicApi.Artist>> getTopArtists();

    void updateTopArtist(MusicApi.Artist artist);
    void updateTrack(MusicApi.TrackInfo trackInfo);
    void updateTrackWithYoutubeId(String youtubeId, String trackUid);
    void deleteTopArtists();
    void deleteTrack(String trackUid);

    void insertTopTracks(List<MusicApi.Track> trackList);
    Observable<MusicApi.Track> getTrack(String trackUid);
    Observable<List<MusicApi.Album>> getAlbumList(String artistUid);
    void insertAlbums(List<MusicApi.Album> albumList);
    void updateAlbumWithAlbumUid(List<MusicApi.Track> trackList, String albumUid);
    Observable<MusicApi.Album> getAlbumByUid(String albumUid);
    void updateTrackWithUid(MusicApi.TrackInfo trackInfo, List<MusicApi.Track> trackList, String albumUid);
    void insertTrackInfo(MusicApi.TrackInfo trackInfo);
    Observable<MusicApi.TrackInfo> getTrackInfo(String trackUid);
    void updateTrackInfoWithYoutubeIdViaTrackUid(String youtubeId, String trackUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid);
    void insertAlbumInfo(MusicApi.AlbumInfo albumInfo);
    Observable<String> getTrackInfoYoutubeId(String trackUid);
    void updateTrackInfoWithSongLyrics(String lyrics, String trackUid);
    Observable<String> getSongLyrics(String trackUid);
    void clearCache();
    void saveDate();
    boolean isSameWeekSinceLastLaunch();
    void resetDate();

    Observable<List<MusicApi.Track>> getSimilarTracks(String trackUid);
    void updateTrackInfoWithSimilarArtists(List<MusicApi.Track> similarTrackList, String trackUid);
    void insertTopTracks(MusicApi.TopChartTracks topChartTracks);
    Observable<MusicApi.TopChartTracks> getTopChartTracks();
    void updateTopTracksList(String artistName, String trackName, String trackUid, List<MusicApi.Track> trackList);
}
