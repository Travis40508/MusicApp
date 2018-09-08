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
        compositeDisposable.clear();
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
            Log.d("@@@@", throwable.getMessage());
            view.showTrackSummary(Constants.NO_TRACK_BIO_AVAILABLE);
            view.setImageBackgroundColorWhite();
            view.showGenericTrackImage();
        };
    }

    private Consumer<List<MusicApi.Track>> updateUiWithSimilarTracks() {
        return trackList -> {
            if(trackList != null) {
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
}
