package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface PlayTrackView extends BaseView {
    void showLoadingLayout();

    void showTrackSummary(String trackSummary);

    void showTrackContent(String trackContent);

    void hideLoadingLayout();

    void showTrackAlbumCover(String imageUrl);

    void setReadMoreText(String readMoreTextCollapse);

    void showNoSummaryAvailableText(String noSummaryAvailableText);

    void showNoContentAvailableText(String noContentAvailableText);
}
