package com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface ArtistMainView extends BaseView {
    void showScreens(String artistUid, String artistName);

    void reAttachFragment();

    void setActionBarTitle(String artistName);
}
