package com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.TracksAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_TRACKS_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class AlbumTracksFragment extends Fragment implements AlbumTracksView {

    @Inject protected AlbumTracksPresenter presenter;
    private AlbumTracksFragment albumTracksFragment;
    private TracksAdapter adapter;

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
        presenter.artistRetrieved(getArguments().getString(ARTIST_NAME_KEY), getArguments().getString(ARTIST_UID_KEY));
        presenter.albumRetrieved(getArguments().getString(ALBUM_NAME_KEY), getArguments().getString(ALBUM_UID_KEY));
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
}
