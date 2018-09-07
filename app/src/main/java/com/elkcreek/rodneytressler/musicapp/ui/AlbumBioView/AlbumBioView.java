package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface AlbumBioView extends BaseView {
    void showAlbumImage(String trackImage);

    void showAlbumBio(String trackSummary);

    void setReadMoreText(String readMoreTextCollapse);

    void hideParentLoadingLayout();

    void showGenericAlbumImage();

    void setImageBackgroundWhite();
}
