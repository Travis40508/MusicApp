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
import dagger.android.support.AndroidSupportInjection;

public class YoutubeFragment extends Fragment {

    @Inject protected YoutubeFactory factory;
    private YoutubeViewModel viewModel;
    private FragmentYoutubeBinding binding;
    private YouTubePlayer videoPlayer;
    private int videoTime;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_youtube, container, false);
        return binding.getRoot();
    }

    public static YoutubeFragment newInstance() {

        Bundle args = new Bundle();

        YoutubeFragment fragment = new YoutubeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        binding.setViewModel(viewModel);

        viewModel.getVideoIdAndLyrics(getArguments().getString(Constants.TRACK_UID_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY),
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
                        YoutubeFragment.this.videoPlayer = youTubePlayer;
                        youTubePlayer.cueVideo(videoId, videoTime);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.YOUTUBE_VIDEO_TAG, videoPlayer.getCurrentTimeMillis());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        videoTime = savedInstanceState == null ? 0 : savedInstanceState.getInt(Constants.YOUTUBE_VIDEO_TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        videoPlayer.release();
    }

    private YoutubeViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(YoutubeViewModel.class);
    }
}
