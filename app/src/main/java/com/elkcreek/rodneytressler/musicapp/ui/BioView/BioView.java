package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface BioView extends BaseView {
    void showArtistImage(String artistImages);

    void showArtistBio(String artistBio);

    void detachFragment();

    void showNoBioToast();

    void showArtistName(String artistName);

    void hideLoadingLayout();

    void setReadMoreText(String readMoreTextCollapse);

    void showSimilarArtists(List<MusicApi.Artist> artistList);

    void showSimilarArtistScreen(String artistUID, String artistName);

    void showTracksFragment(String artistUid, String artistName);

    void clearBackStack();

    void reAttachBioFragment();

    void showLoadingLayout();
}
