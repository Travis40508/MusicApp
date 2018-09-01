package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import android.content.res.Configuration;
import android.os.Bundle;

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
    }

    private void fetchVideoId() {
        disposable.add(repositoryService.getYoutubeVideoId(trackUid, trackName + artistName).subscribe(storeYoutubeVideoId(), throwErrorWhenNoYoutubeVideoId()));
    }

    private Consumer<String> storeYoutubeVideoId() {
        return videoId -> {
            this.videoId = videoId;
            view.initializeYouTubeVideo();
        };
    }

    private Consumer<Throwable> throwErrorWhenNoYoutubeVideoId() {
        return throwable -> {
            view.toastUnableToLoadVideo(Constants.UNABLE_TO_LOAD_VIDEO);
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void screenRotated(boolean onSavedInstanceStateIsNull, boolean youtubeFragmentIsNull) {
        if (!onSavedInstanceStateIsNull) {
            if (!youtubeFragmentIsNull) {
                view.reAttachYoutubeFragment();
            }
        }
    }

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

    public void saveInstanceState(Bundle outState, int currentTimeMillis) {
        outState.putInt(STATE_YOUTUBE_VIDEO_POSITION, currentTimeMillis);
    }

    public void getState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int currentTimeMillis = savedInstanceState.getInt(STATE_YOUTUBE_VIDEO_POSITION);
            this.currentVideoTime = currentTimeMillis;
        }
    }
}
