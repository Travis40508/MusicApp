package com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ArtistSearchPresenter implements BasePresenter<ArtistSearchView> {

    private final MusicApiService musicApiService;
    private final RepositoryService repositoryService;
    private ArtistSearchView view;
    private CompositeDisposable disposable;
    private boolean isSearching;
    private String searchText;

    @Inject
    public ArtistSearchPresenter(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(ArtistSearchView view) {
        this.view = view;
    }


    private Observable<MusicApi.SearchResponse> getArtistSearchResults(String artistSearchText) {
        return musicApiService.getArtistSearchResults(artistSearchText, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(Observable.empty())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Consumer<List<MusicApi.Artist>> updateViewWithTopArtist() {
        return topArtists -> {
            view.loadArtists(topArtists);
            view.hideProgressBar();
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            view.showErrorLoadingToast();
            repositoryService.resetDate();
            subscribe();
        };
    }

    private Consumer<MusicApi.SearchResponse> getSearchResponse() {
        return searchResponse -> {
            view.hideProgressBar();
            view.clearList();
            view.loadArtists(searchResponse.getSearchResults().getArtistMatches().getArtistList());
        };
    }

    @Override
    public void subscribe() {
        view.setActionBarTitle(Constants.SEARCH_TITLE);
        if (disposable == null) {
            view.showProgressBar();
        }
        disposable = new CompositeDisposable();

        if (!isSearching) {
            if (repositoryService.isSameWeekSinceLastLaunch()) {
                repositoryService.clearCache();
                disposable.add(repositoryService.getTopArtists().subscribe(updateViewWithTopArtist(), updateUiWithError()));
                repositoryService.saveDate();
            } else {
                disposable.add(repositoryService.getTopArtists().subscribe(
                        updateViewWithTopArtist()
                ));
            }
        } else {
            artistSearchTextChanged(searchText, true);
        }
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistSearchTextChanged(String artistSearchText, boolean adapterHasItems) {
        this.isSearching = artistSearchText.length() > 0;
        this.searchText = artistSearchText;
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }

        if (!adapterHasItems) {
            view.showProgressBar();
        }

        if (!artistSearchText.isEmpty()) {
            view.showSearchTextValue(artistSearchText);
            disposable.add(getArtistSearchResults(artistSearchText)
                    .subscribe(getSearchResponse(), updateUiWithError()));

        } else {
            view.showSearchTextTopArtists();
            disposable.add(repositoryService.getTopArtists().subscribe(updateViewWithTopArtist(), updateUiWithError()));
        }

    }

    public void onArtistClicked(MusicApi.Artist artist) {
        view.clearSearchText();
        view.showMainProgressBar();
        view.showMainArtistScreen(artist);
    }
}
