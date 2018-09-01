package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface AlbumBioView extends BaseView {
    void reAttachFragment();

    void showAlbumImage(String trackImage);

    void showAlbumName(String albumName);

    void showAlbumBio(String trackSummary);

    void hideLoadingLayout();

    void setReadMoreText(String readMoreTextCollapse);
}
