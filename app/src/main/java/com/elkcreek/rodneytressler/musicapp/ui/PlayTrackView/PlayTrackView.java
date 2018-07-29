package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface PlayTrackView extends BaseView {
    void showTrackWebView(String trackUrl);

    void hideProgressBar();
}
