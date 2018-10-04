package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackSearchPresenter implements BasePresenter<TrackSearchView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private TrackSearchView view;
    private List<MusicApi.Track> trackList;
    private boolean isSearching;
    private Object searchText;
    private static final String STATE_RECYCLER_VIEW_POSITION = "state_recycler_view_position";
    private Parcelable recyclerViewPosition;

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
            Log.d("@@@@-TrackSearchPres", throwable.getMessage());
            view.hideLoadingLayout();
        };
    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return track -> {
            view.showPlayTracksFragment(track.getTrackName(), track.getArtistInfo().getArtistName(), track.getTrackUid());
        };
    }


    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void trackClicked(MusicApi.Track track) {
        view.showParentLoadingLayout();
        if (track.getTrackUid() == null || track.getTrackUid().isEmpty()) {
            view.showPlayTracksFragment(track.getTrackName(), track.getArtist().getArtistName(), track.getTrackUid());
        } else {
            disposable.add(repositoryService.getTrack(track.getTrackUid()).subscribe());
        }
    }

    public void trackSearchTextChanged(String searchedText, boolean adapterHasItems) {
        this.isSearching = searchedText.length() > 0;
        this.searchText = searchedText;
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }

        if (!adapterHasItems) {
            view.showProgressBar();
        }

        if (!searchedText.isEmpty()) {
            view.showSearchTextValue("Showing Results For '" + searchedText + "'");
            disposable.add(repositoryService.getSearchedTracksFromNetwork((searchedText))
                    .subscribe(getSearchResponse(), updateUiWithError()));

        } else {
            view.showSearchTextTopTracks(Constants.TOP_TRACKS);
            disposable.add(repositoryService.getTopTracks().subscribe(updateUiWithTracks(), updateUiWithError()));
        }
    }

    private Consumer<List<MusicApi.SearchedTrack>> getSearchResponse() {
        return trackList -> {
            view.hideLoadingLayout();
            view.showSearchedTracks(trackList);
        };
    }

    public void searchedTrackClicked(MusicApi.SearchedTrack track) {
        view.showParentLoadingLayout();
        view.clearSearch();
        if (track.getTrackUid() == null || track.getTrackUid().isEmpty()) {
            view.showPlayTracksFragment(track.getTrackName(), track.getArtist(), track.getTrackUid());
        } else {
            disposable.add(repositoryService.getTrack(track.getTrackUid()).subscribe(updateUiWithTrack(), updateUiWithSearchError()));
        }
    }

    private Consumer<Throwable> updateUiWithSearchError() {
        return throwable -> {
          Log.d("@@@@-TrackSearchPres", throwable.getMessage());
        };
    }

    public void storeRecyclerViewPosition(Parcelable parcelable) {
        if(parcelable != null) {
            this.recyclerViewPosition = parcelable;
        }
    }

    public void saveState(Bundle outState, Parcelable parcelable) {
        if(outState != null) {
            outState.putParcelable(STATE_RECYCLER_VIEW_POSITION, parcelable);
        }
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            recyclerViewPosition = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW_POSITION);
        }
    }

    public void listLoaded() {
        view.setRecyclerViewPosition(recyclerViewPosition);
    }
}
