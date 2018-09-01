package com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface AlbumTracksView extends BaseView {
    void showTrackListForAlbum(List<MusicApi.Track> trackList, String imageUrl);

    void showPlayTracksFragment(String trackName, String trackUid, String artistName);

    void showLoadingLayout();
}
