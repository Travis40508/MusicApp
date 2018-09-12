package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_BIO_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;

public class AlbumBioFragment extends BaseFragment implements AlbumBioView {

    @Inject protected AlbumBioPresenter presenter;
    @BindView(R.id.image_album_bio)
    protected ImageView albumImage;
    @BindView(R.id.text_album_bio)
    protected TextView albumBio;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;
    @BindView(R.id.bio_scroll_view)
    protected ScrollView scrollView;
    private AlbumBioFragment albumBioFragment;

    @OnClick(R.id.read_more_layout)
    protected void onReadMoreClicked(View view) {
        presenter.readMoreClicked(readMoreText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.albumUidRetrieved(getArguments().getString(ALBUM_UID_KEY));
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
        presenter.storeScrollViewPosition(scrollView.getScrollY());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static AlbumBioFragment newInstance() {

        Bundle args = new Bundle();

        AlbumBioFragment fragment = new AlbumBioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState, scrollView.getScrollY());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    @Override
    public void showAlbumImage(String trackImageUrl) {
        Glide.with(this)
                .asBitmap()
                .load(trackImageUrl)
                .apply(RequestOptions.overrideOf(250, 300))
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(albumImage);
    }

    @Override
    public void showAlbumBio(String trackSummary) {
        this.albumBio.setText(trackSummary);
        presenter.bioLoaded();
    }

    @Override
    public void setReadMoreText(String readMoreText) {
        this.readMoreText.setText(readMoreText);
    }

    @Override
    public void hideParentLoadingLayout() {
        hideMainLoadingLayout();
    }

    @Override
    public void showGenericAlbumImage() {
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.generic_album)
                .apply(RequestOptions.overrideOf(250, 300))
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(albumImage);
    }

    @Override
    public void setImageBackgroundWhite() {
        albumImage.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setScrollViewState(int scrollPosition) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.setScrollY(scrollPosition);
            }
        });
    }
}
