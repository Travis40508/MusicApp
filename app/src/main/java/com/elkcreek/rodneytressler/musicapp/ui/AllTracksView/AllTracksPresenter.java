package com.elkcreek.rodneytressler.musicapp.ui.AllTracksView;

import android.os.Bundle;
import android.os.Parcelable;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AllTracksPresenter implements BasePresenter<AllTracksView> {

    private final RepositoryService repositoryService;
    private AllTracksView view;
    private CompositeDisposable disposable;
    private String artistUid;
    private String artistName;
    private static final String STATE_RECYCLER_VIEW_POSITION = "state_recycler_view_position";
    private Parcelable recyclerViewPosition;

    @Inject
    public AllTracksPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AllTracksView view) {
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
            view.showNoTracksAvailableMessage();
            view.hideTrackSearch();
            view.hideShowingTracks();
            view.hideProgressBar();
        };
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(repositoryService.getArtistTopTracks(artistUid).subscribe(updateUiWithTopTracks(), updateUiOnError()));
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
    }

    public void onPlayClicked(MusicApi.Track track) {
        view.showParentLoadingLayout();
        view.showTrackMainFragment(track.getTrackName(), artistName, track.getTrackUid());
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

    public void storeState(boolean layoutManagerIsNull) {
        if(!layoutManagerIsNull) {
            view.storeLayoutManagerState();
        }
    }

    public void saveState(Bundle outState, Parcelable parcelable) {
        outState.putParcelable(STATE_RECYCLER_VIEW_POSITION, parcelable);
    }

    public void getState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            recyclerViewPosition = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW_POSITION);
        }
    }

    public void listLoaded() {
        view.setRecyclerViewPosition(recyclerViewPosition);
    }

    public void storeLayoutManagerState(Parcelable parcelable) {
        this.recyclerViewPosition = parcelable;
    }

    public void checkLayoutManagerAndSaveState(boolean layoutManagerIsNull, Bundle outState) {
        if(!layoutManagerIsNull) {
            view.getLayoutManagerPosition(outState);
        }
    }
}
