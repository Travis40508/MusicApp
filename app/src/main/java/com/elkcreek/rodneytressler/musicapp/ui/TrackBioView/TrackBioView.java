package com.elkcreek.rodneytressler.musicapp.ui.TrackBioView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface TrackBioView extends BaseView {

    void showTrackSummary(String trackSummary);

    void showTrackContent(String trackContent);

    void showTrackAlbumCover(String imageUrl);

    void setReadMoreText(String readMoreTextCollapse);

    void showSimilarTracks(List<MusicApi.Track> trackList);

    void setTitle(String trackTitle);

    void showSimilarArtistScreen(String trackUid, String trackName, String artistName);

    void hideParentLoadingLayout();

    void showParentLoadingLayout();

    void showGenericTrackImage();

    void setImageBackgroundColorWhite();

    void showNoSimilarTracksText(String noSimilarTracks);
}
