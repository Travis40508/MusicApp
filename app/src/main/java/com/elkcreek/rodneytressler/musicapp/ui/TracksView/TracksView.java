package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface TracksView extends BaseView {
    void showTopTracks(List<MusicApi.Track> trackList);

    void showArtistName(String artistName);

    void showPlayTrackFragment(String trackUrl);

    void removeFragment();

    void toastNoTracksError();

    void hideProgressBar();

    void showProgressBar();

    void showSearchedTracks(String searchText);

    void showSearchTextValue(String searchText);

    void showAllTracksText();

    void clearBackStack();

    void reattachTracksFragment();
}
