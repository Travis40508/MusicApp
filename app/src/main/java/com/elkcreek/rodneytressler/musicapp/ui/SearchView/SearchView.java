package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface SearchView extends BaseView {
    void loadArtists(List<MusicApi.Artist> artistList);

    void showProgressBar();

    void hideProgressBar();

    void showBioFragment(MusicApi.Artist artist);

    void showArtistTracks(MusicApi.Artist artist);

    void showSearchTextValue(String s);

    void showSearchTextTopArtists();

    void showErrorLoadingToast();

    void reAttachTracksFragment();

    void reAttachBioFragment();
}
