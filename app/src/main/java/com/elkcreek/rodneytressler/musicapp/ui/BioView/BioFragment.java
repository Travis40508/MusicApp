package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class BioFragment extends Fragment implements BioView {

    @Inject protected BioPresenter presenter;
    @BindView(R.id.image_artist_bio)
    protected ImageView artistBioImage;
    @BindView(R.id.text_artist_bio)
    protected TextView artistBioText;
    @BindView(R.id.text_bio_artist_name)
    protected TextView artistName;
    @BindView(R.id.bio_progress_bar)
    protected ProgressBar bioProgressBar;

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
        View view = inflater.inflate(R.layout.fragment_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
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
        Log.d("@@@@@", artistBio);
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
    public void hideProgressBar() {
        bioProgressBar.setVisibility(View.GONE);
    }
}
