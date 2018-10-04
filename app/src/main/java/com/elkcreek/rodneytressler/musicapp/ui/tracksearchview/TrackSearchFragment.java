package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentTrackSearchBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SearchedTracksAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.TopTracksAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class TrackSearchFragment extends BaseFragment implements TrackSearchView{

    @Inject TrackSearchFactory factory;
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
    private TrackSearchViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentTrackSearchBinding binding;

    @OnTextChanged(value = R.id.input_track_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onArtistSearchChange(Editable editable) {
//        presenter.trackSearchTextChanged(editable.toString(), adapter != null && adapter.getItemCount() > 0);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
//        presenter.unsubscribe();
//        presenter.storeRecyclerViewPosition(recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_search, container, false);
//        presenter.attachView(this);

        return binding.getRoot();
    }

    public static TrackSearchFragment newInstance() {

        Bundle args = new Bundle();

        TrackSearchFragment fragment = new TrackSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        presenter.saveState(outState, recyclerView != null  && recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        presenter.getState(savedInstanceState);

        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        viewModel.setMainViewModel(mainViewModel);

        binding.setViewModel(viewModel);
        binding.setMainViewModel(mainViewModel);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private TrackSearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(TrackSearchViewModel.class);
    }

    @Override
    public void showTracks(List<MusicApi.Track> trackList) {
//        adapter = new TopTracksAdapter(Glide.with(this), trackList);
//
//        adapter.setAdapterCallback(new TopTracksAdapter.TopTrackCallback() {
//            @Override
//            public void onTopTrackClicked(MusicApi.Track track) {
//                presenter.trackClicked(track);
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter.notifyDataSetChanged();
//        presenter.listLoaded();
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
        Navigation.findNavController(getView()).navigate(R.id.trackMainFragment, bundle);
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
//                presenter.searchedTrackClicked(track);
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

    @Override
    public void setRecyclerViewPosition(Parcelable recyclerViewPosition) {
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewPosition);
    }
}