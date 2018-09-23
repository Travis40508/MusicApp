package com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.AllTracksView.AllTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView.ArtistMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SimilarArtistAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class ArtistBioFragment extends BaseFragment implements ArtistBioView {

    @Inject
    protected ArtistBioPresenter presenter;
    @BindView(R.id.image_artist_bio)
    protected ImageView artistBioImage;
    @BindView(R.id.text_artist_bio)
    protected TextView artistBioText;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;
    @BindView(R.id.bio_similar_artist_recycler_view)
    protected RecyclerView similarArtistRecyclerView;
    @BindView(R.id.text_similar_artists)
    protected TextView similarArtistText;
    @BindView(R.id.bio_scroll_view)
    protected NestedScrollView scrollView;
    private SimilarArtistAdapter adapter;

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
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
        presenter.storeScrollViewState(scrollView.getScrollY());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState, scrollView != null ? scrollView.getScrollY() : 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    public static ArtistBioFragment newInstance() {

        Bundle args = new Bundle();

        ArtistBioFragment fragment = new ArtistBioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showArtistImage(String imageUrl) {
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.overrideOf(250, 300))
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(artistBioImage);
    }

    @Override
    public void showArtistBio(String artistBio) {
        artistBioText.setText(artistBio);
        presenter.bioShown();
    }

    @Override
    public void setReadMoreText(String readMoreText) {
        this.readMoreText.setText(readMoreText);
    }

    @Override
    public void showSimilarArtists(List<MusicApi.Artist> artistList) {
        adapter = new SimilarArtistAdapter(Glide.with(this), artistList);
        similarArtistRecyclerView.setAdapter(adapter);
        similarArtistRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter.notifyDataSetChanged();
        adapter.setCallback(new SimilarArtistAdapter.BiosCallback() {
            @Override
            public void onSimilarArtistClicked(MusicApi.Artist artist) {
                presenter.similarArtistClicked(artist);
            }
        });
    }

    @Override
    public void showSimilarArtistScreen(String artistUID, String artistName) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_UID_KEY, artistUID);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        Navigation.findNavController(getView()).navigate(R.id.artistMainFragment, bundle, new NavOptions.Builder()
                .setEnterAnim(R.anim.enter_from_right)
                .setExitAnim(R.anim.exit_to_left)
                .setPopEnterAnim(R.anim.enter_from_left)
                .setPopExitAnim(R.anim.exit_to_right)
                .build());
    }

    @Override
    public void setTitle(String artistName) {
        getActivity().setTitle(artistName);
    }

    @Override
    public void hideMainProgressBar() {
        hideMainLoadingLayout();
    }

    @Override
    public void showLoadingLayout() {
        this.showMainLoadingLayout();
    }

    @Override
    public void showGenericArtistImage() {
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.generic_band)
                .apply(RequestOptions.overrideOf(250, 300))
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(artistBioImage);
    }

    @Override
    public void showNoSimilarArtistText() {
        similarArtistText.setText(Constants.NO_SIMILAR_ARTISTS);
    }

    @Override
    public void setImageBackgroundWhite() {
        artistBioImage.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setScrollViewPosition(int scrollYPosition) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.setScrollY(scrollYPosition);
            }
        });
    }

    @Override
    public void toastConnectionFailedToast() {
        Toast.makeText(getContext(), Constants.CONNECTION_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void detachFragment() {
        Navigation.findNavController(getView()).popBackStack();
    }
}
