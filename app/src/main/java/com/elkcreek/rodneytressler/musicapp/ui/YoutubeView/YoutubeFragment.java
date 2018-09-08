package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.BindView;
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
    @BindView(R.id.text_lyrics)
    protected TextView songLyrics;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.text_lyrics_title)
    protected TextView lyricsTitle;
    @BindView(R.id.youtube_fragment_holder)
    protected FrameLayout youtubeVideoLayout;

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
//        presenter.screenRotated(savedInstanceState == null, getActivity().getSupportFragmentManager().findFragmentByTag(YOUTUBE_TAG) == null);
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

        //TODO re-implement later.
//        presenter.saveInstanceState(outState, youTubePlayer.getCurrentTimeMillis());
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
    public void onDestroyView() {
        super.onDestroyView();
        presenter.viewDestroyed(youTubePlayerSupportFragment == null);
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
                presenter.configurationChanged(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE, getUserVisibleHint());
                presenter.youTubePlayerInitializeSuccess(wasRestored);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void setYouTubePlayerStyle() {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
    }

    @Override
    public void loadYouTubeVideo(String videoId, int currentVideoTime) {
        youTubePlayer.loadVideo(videoId, currentVideoTime);
    }


    @Override
    public void showSongLyrics(String lyrics) {
        songLyrics.setText(lyrics);
    }

    @Override
    public void destroyYouTubeSupportFragmentView() {
        youTubePlayerSupportFragment.onDestroy();
    }

    @Override
    public void releaseYouTubePlayer() {
//        youTubePlayer.release();
//        youTubePlayer = null;
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoLyricsAvailableTitle(String noLyrics) {
        lyricsTitle.setText(noLyrics);
    }

    @Override
    public void enterFullScreenMode() {
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void exitFullScreenMode() {
        youTubePlayer.setFullscreen(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy(youTubePlayerSupportFragment == null);
        super.onDestroy();
    }
}
