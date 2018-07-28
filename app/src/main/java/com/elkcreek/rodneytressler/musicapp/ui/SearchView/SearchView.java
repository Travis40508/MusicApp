package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface SearchView extends BaseView {
    void loadArtists(List<MusicApi.Artist> artistList);

    void showProgressBar();

    void hideProgressBar();

    void showBioFragment(String artistUID);
}
