package com.elkcreek.rodneytressler.musicapp.ui.youtubeview;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface YoutubeView extends BaseView {
    void pauseVideo();

    void destroyVideo();

    void initializeYouTubeVideo();

    void setYouTubePlayerStyle();

    void loadYouTubeVideo(String videoId, int currentVideoTime);

    void showSongLyrics(String lyrics);

    void destroyYouTubeSupportFragmentView();

    void hideLoadingLayout();

    void showNoLyricsAvailableTitle(String noLyrics);

    void enterFullScreenMode();

    void exitFullScreenMode();
}
