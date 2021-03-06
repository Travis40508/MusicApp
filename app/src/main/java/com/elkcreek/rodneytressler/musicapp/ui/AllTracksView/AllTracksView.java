package com.elkcreek.rodneytressler.musicapp.ui.AllTracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface AllTracksView extends BaseView {
    void showTopTracks(List<MusicApi.Track> trackList);

    void showArtistName(String artistName);

    void removeFragment();

    void toastNoTracksError();

    void hideProgressBar();

    void showProgressBar();

    void showSearchedTracks(String searchText);

    void showSearchTextValue(String searchText);

    void showAllTracksText();

    void clearBackStack();

    void reattachTracksFragment();

    void showBio(String artistName, String artistUid);

    void showPlayTrackFragment(String trackName, String artistName, String trackUid);
}
