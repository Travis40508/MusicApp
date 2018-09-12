package com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView;

import android.os.Parcelable;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface AlbumTracksView extends BaseView {
    void showTrackListForAlbum(List<MusicApi.Track> trackList, String imageUrl);

    void showPlayTracksFragment(String trackName, String trackUid, String artistName);

    void showLoadingLayout();

    void showParentLoadingLayout();

    void hideLoadingLayout();

    void showNoTracksAvailableMessage();

    void setRecyclerViewPosition(Parcelable recyclerViewPosition);
}
