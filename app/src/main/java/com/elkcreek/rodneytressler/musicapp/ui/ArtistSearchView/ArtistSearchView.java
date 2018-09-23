package com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView;

import android.os.Parcelable;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface ArtistSearchView extends BaseView {
    void loadArtists(List<MusicApi.Artist> artistList);

    void showProgressBar();

    void showMainProgressBar();

    void hideProgressBar();

    void showSearchTextValue(String s);

    void showSearchTextTopArtists();

    void showErrorLoadingToast();

    void showMainArtistScreen(MusicApi.Artist artist);

    void clearList();

    void setActionBarTitle(String artistsTitle);

    void clearSearchText();

    void setRecyclerViewPosition(Parcelable recyclerViewPosition);

    void detachFragment();

    void toastConnectionFailedToast();

    void scrollRecyclerViewToTop();

    void addArtists(List<MusicApi.Artist> artists);

    void showLoadingMessage();
}
