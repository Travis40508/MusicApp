package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface RepositoryService {

    //Tracks
    Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid);
    Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase(String artistUid);
    Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid);

    //Bio
    Observable<MusicApi.Artist> getArtistBio(String artistUid, String artistName);
    Observable<MusicApi.Artist> getArtistBioFromDatabase(String artistUid);
    Observable<MusicApi.Artist> getArtistBioFromNetwork(String artistUid);
    Observable<MusicApi.Artist> getArtistBioWithName(String artistName, String apiKey);
    Observable<MusicApi.Artist> getArtistBioWithNameFromDatabase(String artistName);
    Observable<MusicApi.Artist> getArtistBioWithNameFromNetwork(String artistName);

    //TopArtists
    Observable<List<MusicApi.Artist>> getTopArtists();
    Observable<List<MusicApi.Artist>> getTopArtistsFromDatabase();
    Observable<List<MusicApi.Artist>> getTopArtistsFromNetwork();
    void deleteTopArtists();

    //Track
    Observable<MusicApi.TrackInfo> getTrack(String trackUid);
    Observable<MusicApi.TrackInfo> getTrackFromNetwork(String trackUid);
    Observable<MusicApi.TrackInfo> getTrackFromDatabase(String trackUid);

    //Youtube
    Observable<String> getYoutubeVideoId(String trackUid, String searchQuery);
    Observable<String> getTrackInfoYoutubeIdFromDatabase(String trackUid);
    Observable<String> getYoutubeVideoFromNetwork(String trackUid, String searchQuery);

    //Albums
    Observable<List<MusicApi.Album>> getAlbums(String artistUid);
    Observable<List<MusicApi.Album>> getAlbumsFromDatabase(String artistUid);
    Observable<List<MusicApi.Album>> getAlbumsFromNetwork(String artistUid);
    Observable<List<MusicApi.Track>> getTracksFromAlbum(String albumUid);
    Observable<List<MusicApi.Track>> getTracksFromAlbumFromDatabase(String albumUid);
    Observable<List<MusicApi.Track>> getTracksFromAlbumFromNetwork(String albumUid);

    Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName, List<MusicApi.Track> trackList, String albumUid);

    //AlbumInfo
    Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfoFromDatabase(String albumUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfoFromNetwork(String albumUid);

    //Clear Cache

    void clearCache();

    //Date
    void saveDate();
    boolean isSameWeekSinceLastLaunch();
    void resetDate();
}
