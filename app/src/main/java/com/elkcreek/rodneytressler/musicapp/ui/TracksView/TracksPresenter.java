package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
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

    private final MusicApiService musicApiService;
    private final MusicDatabaseService musicDatabaseService;
    private TracksView view;
    private CompositeDisposable disposable;
    private String artistUid;

    @Inject
    public TracksPresenter(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        this.musicApiService = musicApiService;
        this.musicDatabaseService = musicDatabaseService;
    }

    @Override
    public void attachView(TracksView view) {
        this.view = view;
    }

//    private Observable<MusicApi.TopTracksResponse> getArtistTopTracks(String artistUid) {
//        return musicApiService.getTopTracks(artistUid, Constants.API_KEY);
//    }
    private Observable<List<MusicApi.Track>> getArtistTopTracksFromDatabase() {
        return musicDatabaseService.getTrackList().toObservable();
    }

    private Observable<List<MusicApi.Track>> getArtistTracksFromNetwork(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY)
                .map(MusicApi.TopTracksResponse::getTopTracks)
                .map(MusicApi.TopTracks::getTrackList)
                .doOnNext(tracks -> {
                    for(MusicApi.Track item : tracks) {
                        if(item.getImageUrl() == null || item.getImageUrl().isEmpty()) {
                            item.setImageUrl(item.getArtistImage().get(2).getImageUrl());
                        }
                        musicDatabaseService.insertTrack(item);
                    }
                })
                .map(tracks -> tracks);
    }

    private Observable<List<MusicApi.Track>> getArtistTopTracks(String artistUid) {
        return getArtistTopTracksFromDatabase()
                .onErrorResumeNext(Observable.empty())
                .switchIfEmpty(getArtistTracksFromNetwork(artistUid));
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
        disposable.add(getArtistTopTracks(artistUid).subscribe(updateUiWithTopTracks(), updateUiOnError()));
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
