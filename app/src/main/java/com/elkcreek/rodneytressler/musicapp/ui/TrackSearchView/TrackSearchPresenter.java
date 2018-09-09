package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackSearchPresenter implements BasePresenter<TrackSearchView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private TrackSearchView view;
    private List<MusicApi.Track> trackList;

    @Inject
    public TrackSearchPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(TrackSearchView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchTopTracks();
    }

    private void fetchTopTracks() {
        disposable.add(repositoryService.getTopTracks().subscribe(updateUiWithTracks(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTracks() {
        return trackList -> {
            this.trackList = trackList;
            view.showTracks(trackList);
            view.hideLoadingLayout();
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@@", throwable.getMessage());
            view.hideLoadingLayout();
        };
    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return trackInfo -> {
          view.showPlayTracksFragment(trackInfo.getTrackName(), trackInfo.getTrackUid(), trackInfo.getArtistInfo().getArtistName());
        };
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void trackClicked(MusicApi.Track track) {
        if (track.getTrackUid() == null) {
            disposable.add(repositoryService.getTrackWithName(track, trackList).subscribe(updateUiWithTrack(), updateUiWithError()));
        } else {
            view.showPlayTracksFragment(track.getTrackName(), track.getTrackUid(), track.getArtist().getArtistName());
        }
    }
}
