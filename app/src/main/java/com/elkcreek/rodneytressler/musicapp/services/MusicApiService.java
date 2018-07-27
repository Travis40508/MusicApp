package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import io.reactivex.Observable;

public interface MusicApiService {
    Observable<MusicApi.SearchResponse> getArtistSearchResults(String artist, String apiKey);
    Observable<MusicApi.ArtistBioResponse> getArtistBio(String artist, String apiKey);
}
