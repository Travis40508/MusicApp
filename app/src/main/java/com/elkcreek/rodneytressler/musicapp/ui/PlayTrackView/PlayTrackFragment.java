package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import android.content.Context;
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

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class PlayTrackFragment extends Fragment implements PlayTrackView {

    @Inject protected PlayTrackPresenter presenter;
    @BindView(R.id.track_web_view)
    protected WebView trackWebView;

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
        presenter.trackUrlRetrieved(getArguments().getString(Constants.TRACK_URL_KEY));
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
}
