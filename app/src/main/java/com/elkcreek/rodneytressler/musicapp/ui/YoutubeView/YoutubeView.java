package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface YoutubeView extends BaseView {
    void reAttachYoutubeFragment();

    void toastUnableToLoadVideo(String unableToLoadVideo);

    void showVideo(String videoId);
}
