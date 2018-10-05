package com.elkcreek.rodneytressler.musicapp.ui.youtubeview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentYoutubeBinding;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class YoutubeFragment extends Fragment implements YoutubeView{

    @Inject protected YoutubeFactory factory;
    private YoutubeViewModel viewModel;
    private FragmentYoutubeBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.subscribe();
    }

    @Override
    public void onPause() {
//        presenter.unsubscribe();
//        presenter.onPause(youTubePlayerSupportFragment == null);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_youtube, container, false);
//        presenter.attachView(this);
//        presenter.trackRetrieved(getArguments().getString(TRACK_UID_KEY));
//        presenter.getVideoId(getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));
        return binding.getRoot();
    }

    public static YoutubeFragment newInstance() {

        Bundle args = new Bundle();

        YoutubeFragment fragment = new YoutubeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//        presenter.saveInstanceState(outState, youTubePlayer != null ? youTubePlayer.getCurrentTimeMillis() : 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        presenter.getState(savedInstanceState);

        viewModel = getViewModel();
        binding.setViewModel(viewModel);
        viewModel.getVideoId(getArguments().getString(Constants.TRACK_UID_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY),
                getArguments().getString(Constants.TRACK_NAME_KEY));

        listenForVideoKey();
    }

    private void listenForVideoKey() {
        viewModel.getYoutubeVideoId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String videoId) {
                YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.youtube_fragment_holder, youTubePlayerSupportFragment).commit();
                youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }
        });
    }

    private YoutubeViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(YoutubeViewModel.class);
    }

    @Override
    public void pauseVideo() {
//        youTubePlayerSupportFragment.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        presenter.viewDestroyed(youTubePlayerSupportFragment == null);
    }

    @Override
    public void destroyVideo() {
//        youTubePlayerSupportFragment.onDestroy();
    }

    @Override
    public void initializeYouTubeVideo() {
//        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.youtube_fragment_holder, youTubePlayerSupportFragment, YOUTUBE_VIDEO_TAG).commit();
//        youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
//
//
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//                YoutubeFragment.this.youTubePlayer = youTubePlayer;
////                presenter.configurationChanged( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE, getUserVisibleHint());
////                presenter.youTubePlayerInitializeSuccess(wasRestored);
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });
    }

    @Override
    public void setYouTubePlayerStyle() {
//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
    }

    @Override
    public void loadYouTubeVideo(String videoId, int currentVideoTime) {
//        youTubePlayer.loadVideo(videoId, currentVideoTime);
    }


    @Override
    public void showSongLyrics(String lyrics) {
//        songLyrics.setText(lyrics);
    }

    @Override
    public void destroyYouTubeSupportFragmentView() {
//        youTubePlayerSupportFragment.onDestroy();
    }

    @Override
    public void hideLoadingLayout() {
//        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoLyricsAvailableTitle(String noLyrics) {
//        lyricsTitle.setText(noLyrics);
    }

    @Override
    public void enterFullScreenMode() {
        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.GONE);
        getParentFragment().getView().findViewById(R.id.tab_layout_track_main).setVisibility(View.GONE);
    }

    @Override
    public void exitFullScreenMode() {
        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);
        getParentFragment().getView().findViewById(R.id.tab_layout_track_main).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
//        presenter.onDestroy(youTubePlayerSupportFragment == null);
        super.onDestroy();
    }

}
