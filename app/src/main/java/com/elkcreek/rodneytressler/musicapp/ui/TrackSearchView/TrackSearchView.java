package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface TrackSearchView extends BaseView {
    void showTracks(List<MusicApi.Track> trackList);

    void hideLoadingLayout();

    void showPlayTracksFragment(String trackName, String artistName, String trackUid);

    void showParentLoadingLayout();
}
