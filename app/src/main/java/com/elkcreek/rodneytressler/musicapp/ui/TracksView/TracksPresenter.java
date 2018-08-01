package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.CacheService;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TracksPresenter implements BasePresenter<TracksView> {

    private final CacheService cacheService;
    private TracksView view;
    private CompositeDisposable disposable;
    private String artistUid;

    @Inject
    public TracksPresenter(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void attachView(TracksView view) {
        this.view = view;
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTopTracks() {
        return tracks -> {
            view.showTopTracks(tracks);
            view.hideProgressBar();
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            view.removeFragment();
            view.toastNoTracksError();
        };
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(cacheService.getArtistTopTracks(artistUid).subscribe(updateUiWithTopTracks(), updateUiOnError()));
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        view.showArtistName(artistName);
    }

    public void onPlayClicked(String trackUrl) {
        view.showPlayTrackFragment(trackUrl);
    }

}
