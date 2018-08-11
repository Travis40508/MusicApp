package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.PLAY_TRACK_FRAGMENT_TAG;

public class PlayTrackFragment extends Fragment implements PlayTrackView {

    @Inject protected PlayTrackPresenter presenter;
    protected WebView trackWebView;
    @BindView(R.id.progress_bar_play_track)
    protected ProgressBar progressBar;

    private PlayTrackFragment playTrackFragment;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
    public void showTrackWebView(String trackUrl) {
        trackWebView.getSettings().setUserAgentString(Constants.USER_AGENT);
        trackWebView.getSettings().setJavaScriptEnabled(true);
        trackWebView.setWebChromeClient(new WebChromeClient());
        trackWebView.loadUrl(trackUrl);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void reAttachPlayTracksFragment() {
        playTrackFragment = (PlayTrackFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLAY_TRACK_FRAGMENT_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, playTrackFragment, PLAY_TRACK_FRAGMENT_TAG).commit();
    }

}
