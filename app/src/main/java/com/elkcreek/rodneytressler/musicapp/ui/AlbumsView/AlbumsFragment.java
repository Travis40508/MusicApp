package com.elkcreek.rodneytressler.musicapp.ui.AlbumsView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUMS_TAG;

public class AlbumsFragment extends Fragment implements AlbumsView {

    @Inject protected AlbumsPresenter presenter;
    private AlbumsFragment albumsFragment;

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
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        presenter.screenRotated(
                savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(ALBUMS_TAG) == null);
        return view;
    }

    public static AlbumsFragment newInstance() {

        Bundle args = new Bundle();

        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachAlbumsFragment() {
        albumsFragment = (AlbumsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ALBUMS_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, albumsFragment, ALBUMS_TAG).commit();
    }
}
