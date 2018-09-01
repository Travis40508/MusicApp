package com.elkcreek.rodneytressler.musicapp.ui.TrackBioView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface TrackBioView extends BaseView {
    void showLoadingLayout();

    void showTrackSummary(String trackSummary);

    void showTrackContent(String trackContent);

    void hideLoadingLayout();

    void showTrackAlbumCover(String imageUrl);

    void setReadMoreText(String readMoreTextCollapse);
}
