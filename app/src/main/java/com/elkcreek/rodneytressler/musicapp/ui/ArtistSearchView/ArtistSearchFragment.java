package com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView.ArtistMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.ArtistAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class ArtistSearchFragment extends BaseFragment implements ArtistSearchView {

    @Inject protected ArtistSearchPresenter presenter;
    @Inject ArtistSearchFactory factory;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.input_artist_search)
    protected AutoCompleteTextView artistInput;
    @BindView(R.id.text_search_value)
    protected TextView searchText;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    private ArtistAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArtistSearchViewModel viewModel;

    @OnTextChanged(value = R.id.input_artist_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onArtistSearchChange(Editable editable) {
        presenter.artistSearchTextChanged(editable.toString(), adapter != null && adapter.getItemCount() > 0);
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
        presenter.storeState(recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.scrollStateChanged(recyclerView.canScrollVertically(1));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_search, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        adapter = new ArtistAdapter(Glide.with(this), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2));
        return view;
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
        adapter.setArtistList(artistList);
        adapter.setAdapterCallback(new ArtistAdapter.Callback() {
            @Override
            public void onArtistInfoClicked(MusicApi.Artist artist) {
                presenter.onArtistClicked(artist);
            }
        });
        presenter.listSet();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState, recyclerView.getLayoutManager() != null ? recyclerView.getLayoutManager().onSaveInstanceState() : null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
        viewModel = getViewModel();
    }

    private ArtistSearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ArtistSearchViewModel.class);
    }

    @Override
    public void showProgressBar() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMainProgressBar() {
        showMainLoadingLayout();
    }

    @Override
    public void hideProgressBar() {
        loadingLayout.setVisibility(View.GONE);
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
        adapter.clearList();
    }

    @Override
    public void setActionBarTitle(String artistsTitle) {
        getActivity().setTitle(artistsTitle);
    }

    @Override
    public void clearSearchText() {
        artistInput.setText(Constants.EMPTY_TEXT);
    }

    @Override
    public void setRecyclerViewPosition(Parcelable recyclerViewPosition) {
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewPosition);
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
        recyclerView.getLayoutManager().scrollToPosition(0);
    }

    @Override
    public void addArtists(List<MusicApi.Artist> artists) {
        adapter.addToList(artists);
    }

    @Override
    public void showMainArtistScreen(MusicApi.Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artist.getArtistName());
        bundle.putString(ARTIST_UID_KEY, artist.getArtistUID());
        Navigation.findNavController(getView()).navigate(R.id.artistMainFragment, bundle);
    }
}
