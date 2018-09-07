package com.elkcreek.rodneytressler.musicapp.ui.AlbumsView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView.AlbumMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView.AlbumTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.AlbumsAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUMS_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_TRACKS_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class AlbumsFragment extends BaseFragment implements AlbumsView {

    @Inject protected AlbumsPresenter presenter;
    private AlbumsFragment albumsFragment;
    @BindView(R.id.recycler_view_albums)
    protected RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.no_albums_text)
    protected TextView noAlbumsText;
    private AlbumsAdapter adapter;

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
        return view;
    }

    public static AlbumsFragment newInstance() {

        Bundle args = new Bundle();

        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showTopAlbums(List<MusicApi.Album> albumList) {
        adapter = new AlbumsAdapter(Glide.with(this), albumList);
        adapter.setAlbumCallback(new AlbumsAdapter.AlbumCallback() {
            @Override
            public void albumClicked(MusicApi.Album album) {
                presenter.albumClicked(album);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumTracks(String artistName, String artistUID, String albumName, String albumUid, String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(ARTIST_UID_KEY, artistUID);
        bundle.putString(ALBUM_NAME_KEY, albumName);
        bundle.putString(ALBUM_UID_KEY, albumUid);
        bundle.putString(ALBUM_IMAGE_URL_KEY, imageUrl);
        AlbumMainFragment albumMainFragment = AlbumMainFragment.newInstance();
        albumMainFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, albumMainFragment, ALBUM_MAIN_TAG).addToBackStack(null).commit();
    }

    @Override
    public void showParentLoadingLayout() {
        showMainLoadingLayout();
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoAlbumsMessage() {
        noAlbumsText.setVisibility(View.VISIBLE);
    }
}
