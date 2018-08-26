package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_BIO_TAG;

public class AlbumBioFragment extends Fragment implements AlbumBioView {

    @Inject protected AlbumBioPresenter presenter;
    private AlbumBioFragment albumBioFragment;

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
        View view = inflater.inflate(R.layout.fragment_album_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.screenRotated(
                savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_BIO_TAG) == null);
        return view;
    }

    public static AlbumBioFragment newInstance() {

        Bundle args = new Bundle();

        AlbumBioFragment fragment = new AlbumBioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachFragment() {
        albumBioFragment = (AlbumBioFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_BIO_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, albumBioFragment, ALBUM_BIO_TAG).commit();
    }
}
