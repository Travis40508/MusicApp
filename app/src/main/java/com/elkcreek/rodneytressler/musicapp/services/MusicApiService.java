package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface MusicApiService {
    Observable<List<MusicApi.Artist>> getArtistSearchResults(String artist, String apiKey);
    Observable<MusicApi.ArtistBioResponse> getArtistBio(String artistUid, String apiKey);
    Observable<MusicApi.TopArtistsResponse> getTopArtists(String apiKey, int pageNumber);
    Observable<MusicApi.TopTracksResponse> getTopTracks(String artistUid, String apiKey);
    Observable<MusicApi.ArtistBioResponse> getArtistBioWithName(String artistName, String apiKey);
    Observable<MusicApi.TrackInfoResponse> getTrackInfo(String trackUid, String apiKey);
    Observable<List<MusicApi.Album>> getTopAlbums(String apiKey, String artistUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfo(String apiKey, String albumUid);
    Observable<MusicApi.TrackInfoResponse> getTrackInfoWithName(String trackName, String artistName, String apiKey);
    Observable<List<MusicApi.Track>> getListOfSimilarTracks(String trackUid);
    Observable<MusicApi.TopChartTracks> getTopTracksList(String apiKey);
    Observable<List<MusicApi.SearchedTrack>> getSearchedTracks(String searchedTrack);
    Observable<List<MusicApi.Track>> getSimilarTracksByName(String artist, String track, String apiKey);
}
