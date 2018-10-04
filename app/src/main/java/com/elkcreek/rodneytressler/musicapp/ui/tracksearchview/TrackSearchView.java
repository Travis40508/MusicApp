package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.os.Parcelable;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface TrackSearchView extends BaseView {
    void showTracks(List<MusicApi.Track> trackList);

    void hideLoadingLayout();

    void showPlayTracksFragment(String trackName, String artistName, String trackUid);

    void showParentLoadingLayout();

    void showProgressBar();

    void showSearchTextValue(String searchedText);

    void showSearchTextTopArtists();

    void showSearchedTracks(List<MusicApi.SearchedTrack> trackList);

    void clearSearch();

    void showSearchTextTopTracks(String topTracks);

    void setRecyclerViewPosition(Parcelable recyclerViewPosition);
}