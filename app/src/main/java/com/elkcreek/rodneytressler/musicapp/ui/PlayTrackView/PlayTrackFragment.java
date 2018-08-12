package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.PLAY_TRACK_FRAGMENT_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class PlayTrackFragment extends Fragment implements PlayTrackView {

    @Inject protected PlayTrackPresenter presenter;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.text_track_artist_name)
    protected TextView trackArtistName;
    @BindView(R.id.text_track_name)
    protected TextView trackName;
    @BindView(R.id.image_album_cover)
    protected ImageView albumCover;
    @BindView(R.id.text_track_bio)
    protected TextView trackBio;
    @BindView(R.id.read_more_layout)
    protected LinearLayout readMoreLayout;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;

    @OnClick(R.id.read_more_layout)
    protected void onReadMoreLayoutClicked(View view) {presenter.onReadMoreClicked(readMoreText.getText().toString());}

    @OnClick(R.id.image_album_cover)
    protected void onImageAlbumCoverClicked(View view) {presenter.imageAlbumCoverClicked();}

    private PlayTrackFragment playTrackFragment;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        presenter.trackRetrieved(getArguments().getString(TRACK_UID_KEY));
        presenter.getVideoId(getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_track, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.screenRotated(
                savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(PLAY_TRACK_FRAGMENT_TAG) == null);
        return view;
    }

    public static PlayTrackFragment newInstance() {

        Bundle args = new Bundle();

        PlayTrackFragment fragment = new PlayTrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachPlayTracksFragment() {
        playTrackFragment = (PlayTrackFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLAY_TRACK_FRAGMENT_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, playTrackFragment, PLAY_TRACK_FRAGMENT_TAG).commit();
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTrackSummary(String trackSummary) {
        trackBio.setText(trackSummary);
    }

    @Override
    public void showTrackContent(String trackContent) {
        trackBio.setText(trackContent);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTrackAlbumCover(String imageUrl) {
        Glide.with(this).load(imageUrl).into(albumCover);
    }

    @Override
    public void showArtistName(String artistName) {
        trackArtistName.setText(artistName);
    }

    @Override
    public void showTrackName(String trackName) {
        this.trackName.setText(trackName);
    }

    @Override
    public void setReadMoreText(String readMoreText) {
        this.readMoreText.setText(readMoreText);
    }

    @Override
    public void showVideo(String videoId) {
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.youtube_fragment_holder, youTubePlayerSupportFragment).commit();
        youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if(!wasRestored) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(videoId);
                    youTubePlayer.play();
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("@@@@@", youTubeInitializationResult.toString());
            }
        });
    }

}
