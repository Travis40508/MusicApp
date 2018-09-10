package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SearchedTracksAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.TopTracksAdapter;

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

public class TrackSearchFragment extends BaseFragment implements TrackSearchView{

    @Inject protected TrackSearchPresenter presenter;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.text_search_value)
    protected TextView searchedText;
    @BindView(R.id.input_track_search)
    protected EditText searchInput;
    private TopTracksAdapter adapter;
    private SearchedTracksAdapter searchAdapter;

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
        View view = inflater.inflate(R.layout.fragment_track_search, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);

        return view;
    }

    public static TrackSearchFragment newInstance() {

        Bundle args = new Bundle();

        TrackSearchFragment fragment = new TrackSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showTracks(List<MusicApi.Track> trackList) {
        adapter = new TopTracksAdapter(Glide.with(this), trackList);

        adapter.setAdapterCallback(new TopTracksAdapter.TopTrackCallback() {
            @Override
            public void onTopTrackClicked(MusicApi.Track track) {
                presenter.trackClicked(track);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showPlayTracksFragment(String trackName, String artistName, String trackUid) {
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
    public void showParentLoadingLayout() {
        showMainLoadingLayout();
    }

    @Override
    public void showProgressBar() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchTextValue(String searchedText) {
        this.searchedText.setText(searchedText);
    }

    @Override
    public void showSearchTextTopArtists() {
        searchedText.setText("Today's Top Artists");
    }

    @Override
    public void showSearchedTracks(List<MusicApi.SearchedTrack> trackList) {
        searchAdapter = new SearchedTracksAdapter(trackList, Glide.with(this));
        searchAdapter.setSearchedCallback(new SearchedTracksAdapter.SearchedTrackCallback() {
            @Override
            public void onSearchedTrackClicked(MusicApi.SearchedTrack track) {
                presenter.searchedTrackClicked(track);
            }
        });

        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearSearch() {
        searchInput.setText(Constants.EMPTY_TEXT);
    }

    @Override
    public void showSearchTextTopTracks(String topTrackText) {
        searchedText.setText(topTrackText);
    }
}
