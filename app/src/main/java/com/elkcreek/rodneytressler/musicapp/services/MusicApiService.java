package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Observable;

public interface MusicApiService {
    Observable<MusicApi.SearchResponse> getArtistSearchResults(String artist, String apiKey);
    Observable<MusicApi.ArtistBioResponse> getArtistBio(String artistUid, String apiKey);
    Observable<MusicApi.TopArtistsResponse> getTopArtists(String apiKey);
    Observable<MusicApi.TopTracksResponse> getTopTracks(String artistUid, String apiKey);
    Observable<MusicApi.ArtistBioResponse> getArtistBioWithName(String artistName, String apiKey);
    Observable<MusicApi.TrackInfoResponse> getTrackInfo(String trackUid, String apiKey);
    Observable<List<MusicApi.Album>> getTopAlbums(String apiKey, String artistUid);
    Observable<MusicApi.AlbumInfo> getAlbumInfo(String apiKey, String albumUid);
    Observable<MusicApi.TrackInfoResponse> getTrackInfoWithName(String trackName, String artistName, String apiKey);
}
