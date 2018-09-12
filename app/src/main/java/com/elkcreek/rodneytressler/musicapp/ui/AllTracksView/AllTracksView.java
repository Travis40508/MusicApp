package com.elkcreek.rodneytressler.musicapp.ui.AllTracksView;

import android.os.Bundle;
import android.os.Parcelable;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface AllTracksView extends BaseView {
    void showTopTracks(List<MusicApi.Track> trackList);

    void toastNoTracksError();

    void hideProgressBar();

    void showProgressBar();

    void showSearchedTracks(String searchText);

    void showSearchTextValue(String searchText);

    void showAllTracksText();

    void showTrackMainFragment(String trackName, String artistName, String trackUid);

    void showParentLoadingLayout();

    void showNoTracksAvailableMessage();

    void hideTrackSearch();

    void hideShowingTracks();

    void setRecyclerViewPosition(Parcelable recyclerViewPosition);

    void storeLayoutManagerState();

    void getLayoutManagerPosition(Bundle outState);
}
