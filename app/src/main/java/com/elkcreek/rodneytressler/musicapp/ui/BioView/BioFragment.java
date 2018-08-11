package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.TracksView.TracksFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SimilarArtistAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity.BIO_FRAGMENT_TAG;
import static com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity.TRACKS_FRAGMENT_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class BioFragment extends Fragment implements BioView {

    @Inject
    protected BioPresenter presenter;
    @BindView(R.id.image_artist_bio)
    protected ImageView artistBioImage;
    @BindView(R.id.text_artist_bio)
    protected TextView artistBioText;
    @BindView(R.id.text_bio_artist_name)
    protected TextView artistName;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;
    @BindView(R.id.bio_similar_artist_recycler_view)
    protected RecyclerView similarArtistRecyclerView;
    private SimilarArtistAdapter adapter;
    private TracksFragment tracksFragment;
    private BioFragment bioFragment;

    @OnClick(R.id.read_more_layout)
    protected void onReadMoreClicked(View view) {
        presenter.readMoreClicked(readMoreText.getText().toString());
    }

    @OnClick(R.id.bio_text_view_tracks)
    protected void onViewTracksClicked(View view) {
        presenter.viewTracksClicked();
    }

    @OnClick(R.id.image_home_button)
    protected void homeButtonClicked(View view) {
        presenter.homeClicked();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.checkSavedInstanceState(savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(BIO_FRAGMENT_TAG) == null);
        return view;
    }

    public static BioFragment newInstance() {

        Bundle args = new Bundle();

        BioFragment fragment = new BioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showArtistImage(String imageUrl) {
        Glide.with(getContext()).load(imageUrl).into(artistBioImage);
    }

    @Override
    public void showArtistBio(String artistBio) {
        artistBioText.setText(artistBio);
    }

    @Override
    public void detachFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void showNoBioToast() {
        Toast.makeText(getContext(), R.string.no_bio_found_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showArtistName(String artistName) {
        this.artistName.setText(artistName);
    }


    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void setReadMoreText(String readMoreText) {
        this.readMoreText.setText(readMoreText);
    }

    @Override
    public void showSimilarArtists(List<MusicApi.Artist> artistList) {
        adapter = new SimilarArtistAdapter(artistList);
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
        bioFragment = BioFragment.newInstance();
        bioFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, bioFragment, BIO_FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showTracksFragment(String artistUid, String artistName) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_UID_KEY, artistUid);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        tracksFragment = TracksFragment.newInstance();
        tracksFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, tracksFragment, TRACKS_FRAGMENT_TAG).addToBackStack(null).commit();
    }


    @Override
    public void clearBackStack() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    public void reAttachBioFragment() {
        bioFragment = (BioFragment) getActivity().getSupportFragmentManager().findFragmentByTag(BIO_FRAGMENT_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, bioFragment, BIO_FRAGMENT_TAG).commit();
    }

    @Override
    public void showLoadingLayout() {
    }
}
