package com.elkcreek.rodneytressler.musicapp.ui.TrackMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface TrackMainView extends BaseView {
    void reAttachFragment();

    void setActionBarTitle(String title);

    void showScreens(String trackUid, String trackName, String artistName);
}
