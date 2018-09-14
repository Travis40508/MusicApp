package com.elkcreek.rodneytressler.musicapp.ui.TrackBioView;

import android.os.Bundle;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackBioPresenter implements BasePresenter<TrackBioView> {

    private final RepositoryService repositoryService;
    private TrackBioView view;
    private CompositeDisposable compositeDisposable;
    private boolean isExpanded;
    private static final String READ_MORE_TEXT_COLLAPSE = "Collapse";
    private static final String READ_MORE_TEXT_EXPAND = "Read More";
    private String trackUid;
    private String trackName;
    private String artistName;
    private static final String STATE_SCROLL_VIEW_POSITION = "state_scroll_view_position";
    private int scrollPosition;

    @Inject
    public TrackBioPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(TrackBioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
        fetchTrack();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
    }

    private void fetchTrack() {
        if(trackUid != null && !trackUid.isEmpty()) {
            compositeDisposable.add(repositoryService.getTrack(trackUid).subscribe(updateUiWithTrack(), updateUiOnError()));
            compositeDisposable.add(repositoryService.getSimilarTrackList(trackUid).subscribe(updateUiWithSimilarTracks(), updateUiWithSimilarTrackError()));
        } else {
            compositeDisposable.add(repositoryService.getTrackWithName(trackName, artistName).subscribe(updateUiWithTrack(), updateUiOnError()));
            compositeDisposable.add(repositoryService.getSimilarTracksByName(artistName, trackName, Constants.API_KEY).subscribe(updateUiWithSimilarTracks(), updateUiWithSimilarTrackError()));
        }

    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return track -> {
            if (!isExpanded) {
                view.showTrackSummary(track.getWiki() != null ? track.getWiki().getTrackSummary() : Constants.NO_TRACK_BIO_AVAILABLE);
            } else {
                view.showTrackContent(track.getWiki() != null ? track.getWiki().getTrackContent() : Constants.NO_TRACK_BIO_AVAILABLE);
            }
            if(track.getTrackAlbum() != null && track.getTrackAlbum().getTrackImage() != null) {
                view.showTrackAlbumCover(track.getTrackAlbum().getTrackImage().get(2).getImageUrl());
            } else {
                view.setImageBackgroundColorWhite();
                view.showGenericTrackImage();
            }
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@-TrackBioPresenter", throwable.getMessage());
            view.showTrackSummary(Constants.NO_TRACK_BIO_AVAILABLE);
            view.setImageBackgroundColorWhite();
            view.showGenericTrackImage();
        };
    }

    private Consumer<List<MusicApi.Track>> updateUiWithSimilarTracks() {
        return trackList -> {
            if(trackList != null && !trackList.isEmpty()) {
                view.showSimilarTracks(trackList);
            } else {
                view.showNoSimilarTracksText(Constants.NO_SIMILAR_TRACKS);
            }
            view.hideParentLoadingLayout();
        };
    }

    private Consumer<Throwable> updateUiWithSimilarTrackError() {
        return throwable -> {
            Log.d("@@@@-TrackBioPresenter", throwable.getMessage());
            view.showNoSimilarTracksText(Constants.NO_SIMILAR_TRACKS);
            view.hideParentLoadingLayout();
        };
    }

    public void onReadMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase(Constants.READ_MORE)) {
            view.setReadMoreText(READ_MORE_TEXT_COLLAPSE);
        } else {
            view.setReadMoreText(READ_MORE_TEXT_EXPAND);
        }
        fetchTrack();
    }

    public void similarTrackClicked(MusicApi.Track track) {
        view.showParentLoadingLayout();
        view.setTitle(track.getArtist().getArtistName() + " - " + track.getTrackName());
        view.showSimilarArtistScreen(track.getTrackUid(), track.getTrackName(), track.getArtist().getArtistName());
    }

    public void namesRetrieved(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public void storeScrollViewPosition(int scrollY) {
        scrollPosition = scrollY;
    }

    public void saveState(Bundle outState, int scrollY) {
        outState.putInt(STATE_SCROLL_VIEW_POSITION, scrollY);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL_VIEW_POSITION);
        }
    }

    public void bioLoaded() {
        view.setScrollPosition(scrollPosition);
    }
}
