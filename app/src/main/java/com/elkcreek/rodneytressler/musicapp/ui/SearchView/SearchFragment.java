package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.AlbumsView.AlbumsFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AllTracksView.AllTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView.ArtistBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView.ArtistMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity;
import com.elkcreek.rodneytressler.musicapp.utils.ArtistAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUMS_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.BIO_FRAGMENT_TAG;

public class SearchFragment extends Fragment implements SearchView {

    @Inject protected SearchPresenter presenter;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.input_artist_search)
    protected EditText artistInput;
    @BindView(R.id.text_search_value)
    protected TextView searchText;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    private ArtistAdapter adapter;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        adapter = new ArtistAdapter(Glide.with(this), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
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
    }

    @Override
    public void showProgressBar() {
        loadingLayout.setVisibility(View.VISIBLE);
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
    public void showMainArtistScreen(MusicApi.Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artist.getArtistName());
        bundle.putString(ARTIST_UID_KEY, artist.getArtistUID());
        ArtistMainFragment artistMainFragment = ArtistMainFragment.newInstance();
        artistMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_holder, artistMainFragment, ARTIST_MAIN_TAG).addToBackStack(null).commit();
    }
}
