package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface BioView extends BaseView {
    void showArtistImage(String artistImages);

    void showArtistBio(String artistBio);

    void hideLoadingLayout();

    void setReadMoreText(String readMoreTextCollapse);

    void showSimilarArtists(List<MusicApi.Artist> artistList);

    void showSimilarArtistScreen(String artistUID, String artistName);

    void showLoadingLayout();

    void setTitle(String artistName);
}
