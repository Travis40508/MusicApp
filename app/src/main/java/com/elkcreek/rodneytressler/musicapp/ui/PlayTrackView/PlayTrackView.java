package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface PlayTrackView extends BaseView {
    void reAttachPlayTracksFragment();

    void showLoadingLayout();

    void showTrackSummary(String trackSummary);

    void showTrackContent(String trackContent);

    void hideLoadingLayout();

    void showTrackAlbumCover(String imageUrl);

    void showArtistName(String artistName);

    void showTrackName(String trackName);

    void setReadMoreText(String readMoreTextCollapse);
}
