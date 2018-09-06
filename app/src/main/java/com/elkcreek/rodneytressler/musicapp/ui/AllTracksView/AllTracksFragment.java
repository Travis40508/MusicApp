package com.elkcreek.rodneytressler.musicapp.ui.AllTracksView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.TracksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class AllTracksFragment extends BaseFragment implements AllTracksView {
    @Inject
    protected AllTracksPresenter presenter;
    @BindView(R.id.recycler_view_tracks)
    protected RecyclerView recyclerView;
    @BindView(R.id.tracks_search_value)
    protected TextView searchedTracksText;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    private TracksAdapter adapter;



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
        View view = inflater.inflate(R.layout.fragment_all_tracks, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        return view;
    }

    public static AllTracksFragment newInstance() {

        Bundle args = new Bundle();

        AllTracksFragment fragment = new AllTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showTopTracks(List<MusicApi.Track> trackList) {
        adapter = new TracksAdapter(Glide.with(this), trackList);
        adapter.setPlayCallback(new TracksAdapter.TracksCallback() {
            @Override
            public void onPlayClicked(MusicApi.Track track) {
                presenter.onPlayClicked(track);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showTrackMainFragment(String trackName, String artistName, String trackUid) {
        Bundle bundle = new Bundle();
        bundle.putString(TRACK_NAME_KEY, trackName);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(TRACK_UID_KEY, trackUid);
        TrackMainFragment trackMainFragment = TrackMainFragment.newInstance();
        trackMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_holder, trackMainFragment, TRACK_MAIN_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showParentLoadingLayout() {
        showMainLoadingLayout();
    }

    @Override
    public void toastNoTracksError() {
        Toast.makeText(getContext(), R.string.no_tracks_found_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        loadingLayout.setVisibility(View.VISIBLE);
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
}
