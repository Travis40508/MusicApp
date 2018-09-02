package com.elkcreek.rodneytressler.musicapp.ui.TrackBioView;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.utils.SimilarTracksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class TrackBioFragment extends Fragment implements TrackBioView {

    @Inject protected TrackBioPresenter presenter;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.image_album_cover)
    protected ImageView albumCover;
    @BindView(R.id.text_track_bio)
    protected TextView trackBio;
    @BindView(R.id.read_more_layout)
    protected LinearLayout readMoreLayout;
    @BindView(R.id.text_read_more)
    protected TextView readMoreText;
    @BindView(R.id.bio_similar_tracks_recycler_view)
    protected RecyclerView recyclerView;
    private SimilarTracksAdapter adapter;

    @OnClick(R.id.read_more_layout)
    protected void onReadMoreLayoutClicked(View view) {presenter.onReadMoreClicked(readMoreText.getText().toString());}

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static TrackBioFragment newInstance() {

        Bundle args = new Bundle();

        TrackBioFragment fragment = new TrackBioFragment();
        fragment.setArguments(args);
        return fragment;
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
    public void setReadMoreText(String readMoreText) {
        this.readMoreText.setText(readMoreText);
    }

    @Override
    public void showSimilarTracks(List<MusicApi.Track> trackList) {
        adapter = new SimilarTracksAdapter(Glide.with(this), trackList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter.notifyDataSetChanged();
        adapter.setCallback(new SimilarTracksAdapter.SimilarTracksAdapterCallback() {
            @Override
            public void similarTrackClicked(MusicApi.Track track) {
                presenter.similarTrackClicked(track);
            }
        });
    }

    @Override
    public void setTitle(String trackTitle) {
        getActivity().setTitle(trackTitle);
    }

    @Override
    public void showSimilarArtistScreen(String trackUid, String trackName, String artistName) {
        Bundle bundle = new Bundle();
        bundle.putString(TRACK_NAME_KEY, trackName);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(TRACK_UID_KEY, trackUid);
        TrackMainFragment trackMainFragment = TrackMainFragment.newInstance();
        trackMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_holder, trackMainFragment, TRACK_MAIN_TAG).addToBackStack(null).commit();
    }
}
