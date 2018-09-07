package com.elkcreek.rodneytressler.musicapp.ui.TrackBioView;

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
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
        fetchTrack();
    }

    private void fetchTrack() {
        compositeDisposable.add(repositoryService.getTrack(trackUid).subscribe(updateUiWithTrack(), updateUiOnError()));
        compositeDisposable.add(repositoryService.getSimilarTrackList(trackUid).subscribe(updateUiWithSimilarTracks(), updateUiWithSimilarTrackError()));
    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return track -> {
            if (!isExpanded) {
                view.showTrackSummary(track.getWiki().getTrackSummary());
            } else {
                view.showTrackContent(track.getWiki().getTrackContent());
            }
            view.showTrackAlbumCover(track.getTrackAlbum().getTrackImage().get(2).getImageUrl());
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
            view.showTrackSummary(Constants.NO_CONTENT_AVAILABLE_TEXT);
            view.hideParentLoadingLayout();
//           Maybe later -  view.showNoPreviewAvailable();
        };
    }

    private Consumer<List<MusicApi.Track>> updateUiWithSimilarTracks() {
        return trackList -> {
            view.showSimilarTracks(trackList);
            view.hideParentLoadingLayout();
        };
    }

    private Consumer<Throwable> updateUiWithSimilarTrackError() {
        return throwable -> {
            Log.d("@@@@-TrackBioPresenter", throwable.getMessage());
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
}
