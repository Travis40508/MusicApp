package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MusicApiServiceImpl implements MusicApiService {

    private final MusicApi musicApi;

    public MusicApiServiceImpl(MusicApi musicApi) {
        this.musicApi = musicApi;
    }


    @Override
    public Observable<MusicApi.SearchResponse> getArtistSearchResults(String artist, String apiKey) {
        return musicApi.getArtistSearchResults(artist, apiKey)
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<MusicApi.ArtistBioResponse> getArtistBio(String artistUid, String apiKey) {
        return musicApi.getArtistBio(artistUid, apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.TopArtistsResponse> getTopArtists(String apiKey) {
        return musicApi.getTopArtists(apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.TopTracksResponse> getTopTracks(String artistUid, String apiKey) {
        return musicApi.getTopTracks(artistUid, apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.ArtistBioResponse> getArtistBioWithName(String artistName, String apiKey) {
        return musicApi.getArtistBioWithName(artistName, apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MusicApi.TrackInfoResponse> getTrackInfo(String trackUid, String apiKey) {
        return musicApi.getTrackInfo(trackUid, apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Album>> getTopAlbums(String apiKey, String artistUid) {
        return musicApi.getTopAlbums(apiKey, artistUid)
                .subscribeOn(Schedulers.io())
                .map(albumResponse -> albumResponse.getTopAlbums().getAlbumList());
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfo(String apiKey, String albumUid) {
        return musicApi.getAlbumInfo(apiKey, albumUid)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.AlbumInfoResponse::getAlbumInfo);
    }

    @Override
    public Observable<MusicApi.TrackInfoResponse> getTrackInfoWithName(String trackName, String artistName, String apiKey) {
        return musicApi.getTrackInfoWithName(trackName, artistName, apiKey)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MusicApi.Track>> getListOfSimilarTracks(String trackUid) {
        return musicApi.getSimilarTracks(trackUid, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.SimilarTrackResponse::getSimilarTracks)
                .map(MusicApi.SimilarTracks::getSimilarTrackList);
    }

    @Override
    public Observable<MusicApi.TopChartTracks> getTopTracksList(String apiKey) {
        return musicApi.getTopChartTracks(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TopChartTracksResponse::getTopChartTracks);
    }

    @Override
    public Observable<List<MusicApi.SearchedTrack>> getSearchedTracks(String searchedTrack) {
        return musicApi.getSearchedTracks(searchedTrack, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(MusicApi.TrackSearchResult::getTrackSearchResults)
                .map(MusicApi.TrackSearchResults::getSearchedTracksResponse)
                .map(MusicApi.SearchedTracksResponse::getTrackList);
    }
}
