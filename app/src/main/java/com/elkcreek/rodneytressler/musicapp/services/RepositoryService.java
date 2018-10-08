package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.LyricsApi;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface RepositoryService {

    //Tracks
    Observable<List<MusicApi.ArtistTrack>> getArtistTopTracks(String artistUid);
    Observable<List<MusicApi.ArtistTrack>> getArtistTopTracksFromDatabase(String artistUid);
    Observable<List<MusicApi.ArtistTrack>> getArtistTracksFromNetwork(String artistUid);
    Observable<List<MusicApi.SearchedTrack>> getSearchedTracksFromNetwork(String searchedTrack);

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

    //TopTracks
    Observable<List<MusicApi.TopTrack>> getTopTracks();
    Observable<List<MusicApi.TopTrack>> getTopTracksFromDatabase();
    Observable<List<MusicApi.TopTrack>> getTopTracksFromNetwork();
    Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName);

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
    Observable<List<MusicApi.AlbumTrack>> getTracksFromAlbum(String albumUid);
    Observable<List<MusicApi.AlbumTrack>> getTracksFromAlbumFromDatabase(String albumUid);
    Observable<List<MusicApi.AlbumTrack>> getTracksFromAlbumFromNetwork(String albumUid);

    Observable<MusicApi.TrackInfo> getTrackWithName(String trackName, String artistName, List<MusicApi.Track> trackList, String albumUid);

    //AlbumInfo
    Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfoFromDatabase(String albumUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfoFromNetwork(String albumUid);

    //Lyrics
    Observable<String> getLyrics(String artistName, String songTitle, String trackUid);
    Observable<String> getLyricsFromDatabase(String trackUid);
    Observable<String> getLyricsFromNetwork(String artistName, String songTitle, String trackUid);

    //Similar Tracks
    Observable<List<MusicApi.SimilarTrack>> getSimilarTrackList(String trackUid);
    Observable<List<MusicApi.SimilarTrack>> getSimilarTrackListFromDatabase(String trackUid);
    Observable<List<MusicApi.SimilarTrack>> getSimilarTrackListFromNetwork(String trackUid);
    Observable<List<MusicApi.SimilarTrack>> getSimilarTracksByName(String artist, String track, String apiKey);

    //Clear Cache

    void clearCache();

    //Date
    void saveDate();
    boolean isSameWeekSinceLastLaunch();
    void resetDate();
}
