package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.YOUTUBE_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.YOUTUBE_VIDEO_TAG;

public class YoutubeFragment extends Fragment implements YoutubeView {

    @Inject protected YoutubePresenter presenter;
    private YoutubeFragment youtubeFragment;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private YouTubePlayer youTubePlayer;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        presenter.unsubscribe();
        presenter.onPause(youTubePlayerSupportFragment == null);
//        presenter.storeYouTubeVideoState(youTubePlayer.getCurrentTimeMillis());
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.trackRetrieved(getArguments().getString(TRACK_UID_KEY));
        presenter.getVideoId(getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));

        presenter.screenRotated(savedInstanceState == null, getActivity().getSupportFragmentManager().findFragmentByTag(YOUTUBE_TAG) == null);
        return view;
    }

    public static YoutubeFragment newInstance() {

        Bundle args = new Bundle();

        YoutubeFragment fragment = new YoutubeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachYoutubeFragment() {
        youtubeFragment = (YoutubeFragment) getActivity().getSupportFragmentManager().findFragmentByTag(YOUTUBE_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, youtubeFragment, YOUTUBE_TAG).commit();
    }

    @Override
    public void toastUnableToLoadVideo(String unableToLoadVideo) {
        Toast.makeText(getContext(), unableToLoadVideo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstanceState(outState, youTubePlayer.getCurrentTimeMillis());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    @Override
    public void pauseVideo() {
        youTubePlayerSupportFragment.onPause();
    }


    @Override
    public void destroyVideo() {
        youTubePlayerSupportFragment.onDestroy();
    }

    @Override
    public void initializeYouTubeVideo() {
        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.youtube_fragment_holder, youTubePlayerSupportFragment, YOUTUBE_VIDEO_TAG).commit();
        youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                YoutubeFragment.this.youTubePlayer = youTubePlayer;
                presenter.youTubePlayerInitializeSuccess(wasRestored);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void setYouTubePlayerStyle() {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
    }

    @Override
    public void loadYouTubeVideo(String videoId, int currentVideoTime) {
        youTubePlayer.loadVideo(videoId, currentVideoTime);
    }

    @Override
    public void playYoutubeVideo() {
        youTubePlayer.play();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy(youTubePlayerSupportFragment == null);
        super.onDestroy();
    }
}
