package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.services.YoutubeApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PlayTrackPresenter implements BasePresenter<PlayTrackView> {

    private final RepositoryService repositoryService;
    private PlayTrackView view;
    private CompositeDisposable compositeDisposable;
    private boolean isExpanded;
    private static final String READ_MORE_TEXT_COLLAPSE = "Collapse";
    private static final String READ_MORE_TEXT_EXPAND = "Read More";
    private String trackUid;
    private String videoId;

    @Inject
    public PlayTrackPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(PlayTrackView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
        view.showLoadingLayout();
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
    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return track -> {
            if (!isExpanded) {
                view.showTrackSummary(track.getWiki().getTrackSummary());
            } else {
                view.showTrackContent(track.getWiki().getTrackContent());
            }
            view.hideLoadingLayout();
            view.showTrackAlbumCover(track.getTrackAlbum().getTrackImage().get(2).getImageUrl());
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
            view.hideLoadingLayout();
            view.showTrackSummary(Constants.NO_CONTENT_AVAILABLE_TEXT);
//           Maybe later -  view.showNoPreviewAvailable();
        };
    }

    public void onReadMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase("Read More")) {
            view.setReadMoreText(READ_MORE_TEXT_COLLAPSE);
        } else {
            view.setReadMoreText(READ_MORE_TEXT_EXPAND);
        }
        fetchTrack();
    }
}
