package com.elkcreek.rodneytressler.musicapp.ui.TracksView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TracksPresenter implements BasePresenter<TracksView> {

    private final RepositoryService repositoryService;
    private TracksView view;
    private CompositeDisposable disposable;
    private String artistUid;

    @Inject
    public TracksPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
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
        disposable.add(repositoryService.getArtistTopTracks(artistUid).subscribe(updateUiWithTopTracks(), updateUiOnError()));
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

    public void screenRotated(boolean savedInstanceStateIsNull, boolean playTracksFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!playTracksFragmentIsNull) {
                view.reAttachPlayTracksFragment();
            }
        }
    }

    public void trackSearchTextChanged(String searchText, boolean adapterHasItems) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }

        if (!adapterHasItems) {
            view.showProgressBar();
        }

        if (!searchText.isEmpty()) {
            view.showSearchTextValue(searchText);
            view.showSearchedTracks(searchText);
        } else {
            disposable.add(repositoryService.getArtistTopTracks(artistUid)
                    .subscribe(updateUiWithTopTracks(), updateUiOnError()));
            view.showAllTracksText();
        }
    }
}
