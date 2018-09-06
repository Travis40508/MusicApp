package com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface ArtistBioView extends BaseView {
    void showArtistImage(String artistImages);

    void showArtistBio(String artistBio);

    void setReadMoreText(String readMoreTextCollapse);

    void showSimilarArtists(List<MusicApi.Artist> artistList);

    void showSimilarArtistScreen(String artistUID, String artistName);

    void setTitle(String artistName);

    void hideMainProgressBar();

    void showLoadingLayout();
}
