package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import android.os.Bundle;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class YoutubePresenter implements BasePresenter<YoutubeView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private YoutubeView view;
    private String trackName;
    private String artistName;
    private String trackUid;
    private String videoId;
    private static final String STATE_YOUTUBE_VIDEO_POSITION = "state_youtube_video_position";
    private int currentVideoTime;
    private boolean isInPictureInPictureMode;

    @Inject
    public YoutubePresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(YoutubeView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchVideoId();
        fetchSongLyrics();
    }

    private void fetchVideoId() {
        disposable.add(repositoryService.getYoutubeVideoId(trackUid, artistName + trackName).subscribe(storeYoutubeVideoId(), throwErrorWhenNoYoutubeVideoId()));
    }

    private void fetchSongLyrics() {
        disposable.add(repositoryService.getLyrics(artistName, trackName, trackUid).subscribe(updateViewWithLyrics(), throwErrorWhenLyricsNotAvailable()));
    }

    private Consumer<String> storeYoutubeVideoId() {
        return videoId -> {
            this.videoId = videoId;
            view.initializeYouTubeVideo();
        };
    }

    private Consumer<Throwable> throwErrorWhenNoYoutubeVideoId() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
        };
    }

    private Consumer<String> updateViewWithLyrics() {
        return lyrics -> {
            if (lyrics != null) {
                view.showSongLyrics(lyrics);
            } else {
                view.showNoLyricsAvailableTitle(Constants.NO_LYRICS);
            }
            view.hideLoadingLayout();
        };
    }

    private Consumer<Throwable> throwErrorWhenLyricsNotAvailable() {
        return throwable -> {
            Log.d("@@@@-YoutubePresenter", throwable.getMessage());
            view.showNoLyricsAvailableTitle(Constants.NO_LYRICS);
            view.hideLoadingLayout();
        };
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

//    public void screenRotated(boolean onSavedInstanceStateIsNull, boolean youtubeFragmentIsNull) {
//        if (!onSavedInstanceStateIsNull) {
//            if (!youtubeFragmentIsNull) {
//                view.reAttachYoutubeFragment();
//            }
//        }
//    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
    }

    public void getVideoId(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public void onPause(boolean youtubeSupportFragmentIsNull) {
        if (!youtubeSupportFragmentIsNull) {
            view.pauseVideo();
        }
    }


    public void onDestroy(boolean youtubeSupportFragmentIsNull) {
        if (!youtubeSupportFragmentIsNull) {
            view.destroyVideo();
        }
    }

    public void youTubePlayerInitializeSuccess(boolean wasRestored) {
        view.setYouTubePlayerStyle();
        view.loadYouTubeVideo(videoId, currentVideoTime);

    }

    public void getState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int currentTimeMillis = savedInstanceState.getInt(STATE_YOUTUBE_VIDEO_POSITION);
            this.currentVideoTime = currentTimeMillis;
        }
    }

    public void viewDestroyed(boolean youTubeSupportFragmentIsNull) {
        if (!youTubeSupportFragmentIsNull) {
            view.destroyYouTubeSupportFragmentView();
        }
    }

    public void onDetach() {
        view.releaseYouTubePlayer();
    }

    public void configurationChanged(boolean isInLandScapeMode, boolean youtubePlayerIsVisible) {
        if (youtubePlayerIsVisible) {
            if (isInLandScapeMode) {
                view.enterFullScreenMode();
            } else {
                view.exitFullScreenMode();
            }
        }
    }

}
