package com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView.PlayTrackFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.utils.AlbumTracksAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.TracksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_TRACKS_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.PLAY_TRACK_FRAGMENT_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class AlbumTracksFragment extends Fragment implements AlbumTracksView {

    @Inject protected AlbumTracksPresenter presenter;
    @BindView(R.id.recycler_view_album_tracks)
    protected RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    private AlbumTracksFragment albumTracksFragment;
    private AlbumTracksAdapter adapter;
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
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_tracks, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.artistRetrieved(getArguments().getString(ARTIST_NAME_KEY), getArguments().getString(ARTIST_UID_KEY));
        presenter.albumRetrieved(getArguments().getString(ALBUM_NAME_KEY), getArguments().getString(ALBUM_UID_KEY), getArguments().getString(ALBUM_IMAGE_URL_KEY));
        presenter.onScreenRotated(savedInstanceState == null, getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_TRACKS_TAG) == null);
        return view;
    }

    public static AlbumTracksFragment newInstance() {

        Bundle args = new Bundle();

        AlbumTracksFragment fragment = new AlbumTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachAlbumTracksFragment() {
        albumTracksFragment = (AlbumTracksFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_TRACKS_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, albumTracksFragment, ALBUM_TRACKS_TAG).commit();
    }

    @Override
    public void showTrackListForAlbum(List<MusicApi.Track> trackList, String imageUrl) {
        adapter = new AlbumTracksAdapter(trackList, imageUrl);
        adapter.setPlayCallback(new AlbumTracksAdapter.AlbumTracksCallback() {
            @Override
            public void onPlayClicked(MusicApi.Track track) {
                presenter.trackClicked(track);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPlayTracksFragment(String trackName, String trackUid, String artistName) {
        Bundle bundle = new Bundle();
        bundle.putString(TRACK_NAME_KEY, trackName);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(TRACK_UID_KEY, trackUid);
        TrackMainFragment trackMainFragment = TrackMainFragment.newInstance();
        trackMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, trackMainFragment, TRACK_MAIN_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
    }
}
