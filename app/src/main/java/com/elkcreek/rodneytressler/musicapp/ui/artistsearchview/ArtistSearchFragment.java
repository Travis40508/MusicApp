package com.elkcreek.rodneytressler.musicapp.ui.artistsearchview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentArtistSearchBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.EventHandlers;
import com.elkcreek.rodneytressler.musicapp.utils.ArtistAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class ArtistSearchFragment extends BaseFragment implements ArtistSearchView {

    @Inject ArtistSearchFactory factory;
    @BindView(R.id.input_artist_search)
    protected AutoCompleteTextView artistInput;
    @BindView(R.id.text_search_value)
    protected TextView searchText;
//    @BindView(R.id.loading_layout)
//    protected FrameLayout loadingLayout;
//    private ArtistAdapter adapter;
//    private LinearLayoutManager linearLayoutManager;
    private ArtistSearchViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentArtistSearchBinding binding;

    @OnTextChanged(value = R.id.input_artist_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onArtistSearchChange(Editable editable) {
//        presenter.artistSearchTextChanged(editable.toString(), adapter != null && adapter.getItemCount() > 0);
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
//        presenter.storeState(recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Override
    public void onStart() {
        super.onStart();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
////                presenter.scrollStateChanged(recyclerView.canScrollVertically(1));
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_search, container, false);
//        presenter.attachView(this);
//        adapter = new ArtistAdapter(Glide.with(this), new ArrayList<>());
//        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static ArtistSearchFragment newInstance() {

        Bundle args = new Bundle();

        ArtistSearchFragment fragment = new ArtistSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadArtists(List<MusicApi.Artist> artistList) {
//        adapter.setArtistList(artistList);
//        presenter.listSet();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        presenter.saveState(outState, recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        presenter.getState(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        binding.setViewModel(viewModel);
        binding.setMainViewModel(mainViewModel);
        viewModel.setMainViewModel(mainViewModel);
    }

    private ArtistSearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ArtistSearchViewModel.class);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public void showProgressBar() {
//        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMainProgressBar() {
        showMainLoadingLayout();
    }

    @Override
    public void hideProgressBar() {
//        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showSearchTextValue(String searchText) {
        this.searchText.setText("Showing Results For '" + searchText + "'");
    }

    @Override
    public void showSearchTextTopArtists() {
        this.searchText.setText(R.string.top_artists_text);
    }

    @Override
    public void showErrorLoadingToast() {
        Toast.makeText(getContext(), R.string.network_error_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearList() {
//        adapter.clearList();
    }

    @Override
    public void setActionBarTitle(String artistsTitle) {
//        getActivity().setTitle(artistsTitle);
    }

    @Override
    public void clearSearchText() {
        artistInput.setText(Constants.EMPTY_TEXT);
    }

    @Override
    public void setRecyclerViewPosition(Parcelable recyclerViewPosition) {
//        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewPosition);
    }

    @Override
    public void detachFragment() {
        getActivity().onBackPressed();
    }


    @Override
    public void toastConnectionFailedToast() {
        Toast.makeText(getContext(), Constants.CONNECTION_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scrollRecyclerViewToTop() {
//        recyclerView.getLayoutManager().scrollToPosition(0);
    }

    @Override
    public void addArtists(List<MusicApi.Artist> artists) {
//        adapter.addToList(artists);
    }

    @Override
    public void showMainArtistScreen(MusicApi.Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artist.getArtistName());
        bundle.putString(ARTIST_UID_KEY, artist.getArtistUID());
        Navigation.findNavController(getView()).navigate(R.id.artistMainFragment, bundle);
    }


    public void onArtistClicked(String artistName, String artistUid) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(ARTIST_UID_KEY, artistUid);
        Navigation.findNavController(getView()).navigate(R.id.artistMainFragment, bundle);
    }
}
