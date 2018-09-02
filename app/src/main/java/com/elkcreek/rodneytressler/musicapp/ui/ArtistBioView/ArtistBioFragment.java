package com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.AllTracksView.AllTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView.ArtistMainFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SimilarArtistAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class ArtistBioFragment extends Fragment implements ArtistBioView {

    @Inject
    protected ArtistBioPresenter presenter;
    @BindView(R.id.image_artist_bio)
    protected ImageView artistBioImage;
    @BindView(R.id.text_artist_bio)
    protected TextView artistBioText;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;
    @BindView(R.id.bio_similar_artist_recycler_view)
    protected RecyclerView similarArtistRecyclerView;
    private SimilarArtistAdapter adapter;
    private AllTracksFragment allTracksFragment;
    private ArtistBioFragment artistBioFragment;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static ArtistBioFragment newInstance() {

        Bundle args = new Bundle();

        ArtistBioFragment fragment = new ArtistBioFragment();
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
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
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
        ArtistMainFragment artistMainFragment = ArtistMainFragment.newInstance();
        artistMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, artistMainFragment, ARTIST_MAIN_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(String artistName) {
        getActivity().setTitle(artistName);
    }
}
