package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView.PlayTrackFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.TracksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity.PLAY_TRACK_FRAGMENT_TAG;

public class TracksFragment extends Fragment implements TracksView {
    @Inject TracksPresenter presenter;
    @BindView(R.id.recycler_view_tracks)
    protected RecyclerView recyclerView;
    @BindView(R.id.text_tracks_artist)
    protected TextView artistName;
    @BindView(R.id.progress_bar_tracks)
    protected ProgressBar progressBar;
    @BindView(R.id.tracks_search_value)
    protected TextView searchedTracksText;

    private TracksAdapter adapter;
    private PlayTrackFragment playTrackFragment;

    @OnClick(R.id.image_home_button)
    protected void homeButtonClicked(View view) {
        presenter.homeClicked();
    }


    @OnTextChanged(value = R.id.input_track_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onArtistSearchChange(Editable editable) {
        presenter.trackSearchTextChanged(editable.toString(), adapter != null && adapter.getItemCount() > 0);
    }

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
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        presenter.screenRotated(
                savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(PLAY_TRACK_FRAGMENT_TAG) == null);
        return view;
    }

    public static TracksFragment newInstance() {

        Bundle args = new Bundle();

        TracksFragment fragment = new TracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showTopTracks(List<MusicApi.Track> trackList) {
        adapter = new TracksAdapter(trackList);
        adapter.setPlayCallback(new TracksAdapter.TracksCallback() {
            @Override
            public void onPlayClicked(MusicApi.Track track) {
                presenter.onPlayClicked(track.getTrackUrl());
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showArtistName(String artistName) {
        this.artistName.setText(artistName);
    }

    @Override
    public void showPlayTrackFragment(String trackUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TRACK_URL_KEY, trackUrl);
        playTrackFragment = PlayTrackFragment.newInstance();
        playTrackFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, playTrackFragment, PLAY_TRACK_FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void removeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void toastNoTracksError() {
        Toast.makeText(getContext(), R.string.no_tracks_found_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void reAttachPlayTracksFragment() {
        playTrackFragment = (PlayTrackFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLAY_TRACK_FRAGMENT_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, playTrackFragment, PLAY_TRACK_FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchedTracks(String searchText) {
        adapter.showSearchedTracks(searchText);
    }

    @Override
    public void showSearchTextValue(String searchText) {
        searchedTracksText.setText("Showing Results For '" + searchText + "'");
    }

    @Override
    public void showAllTracksText() {
        searchedTracksText.setText("Showing All Tracks");
    }

    @Override
    public void clearBackStack() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
