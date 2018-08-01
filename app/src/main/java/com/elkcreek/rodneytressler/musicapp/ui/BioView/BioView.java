package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface BioView extends BaseView {
    void showArtistImage(String artistImages);

    void showArtistBio(String artistBio);

    void detachFragment();

    void showNoBioToast();

    void showArtistName(String artistName);

    void hideLoadingLayout();

    void setReadMoreText(String readMoreTextCollapse);
}
