package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TracksPresenter implements BasePresenter<TracksView> {

    private final MusicApiService musicApiService;
    private TracksView view;
    private CompositeDisposable disposable;
    private String artistUid;

    @Inject
    public TracksPresenter(MusicApiService musicApiService) {
        this.musicApiService = musicApiService;
    }

    @Override
    public void attachView(TracksView view) {
        this.view = view;
    }

    private Observable<MusicApi.TopTracksResponse> getArtistTopTracks(String artistUid) {
        return musicApiService.getTopTracks(artistUid, Constants.API_KEY);
    }

    private Consumer<MusicApi.TopTracksResponse> updateUiWithTopTracks() {
        return topTracksResponse -> {
            view.showTopTracks(topTracksResponse.getTopTracks().getTrackList());
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
