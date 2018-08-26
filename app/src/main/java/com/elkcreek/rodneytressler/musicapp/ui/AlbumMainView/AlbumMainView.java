package com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface AlbumMainView extends BaseView {
    void reAttachFragment();

    void showScreens(String artistName, String artistUid, String albumName, String albumUid, String albumImage);

    void setActionBarTitle(String s);
}
