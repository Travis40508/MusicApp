package com.elkcreek.rodneytressler.musicapp.ui.SearchView;

import android.content.SharedPreferences;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;


import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SearchPresenter implements BasePresenter<SearchView> {

    private final MusicApiService musicApiService;
    private final SharedPreferences sharedPreferences;
    private final RepositoryService repositoryService;
    private SearchView view;
    private CompositeDisposable disposable;

    @Inject
    public SearchPresenter(MusicApiService musicApiService, SharedPreferences sharedPreferences, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.sharedPreferences = sharedPreferences;
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(SearchView view) {
        this.view = view;
    }


    private Observable<MusicApi.SearchResponse> getArtistSearchResults(String artistSearchText) {
        return musicApiService.getArtistSearchResults(artistSearchText, Constants.API_KEY).onErrorResumeNext(Observable.empty());
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
        };
    }

    private Consumer<MusicApi.SearchResponse> getSearchResponse() {
        return searchResponse -> {
            view.hideProgressBar();
            view.loadArtists(searchResponse.getSearchResults().getArtistMatches().getArtistList());
        };
    }

    @Override
    public void subscribe() {
        if (disposable == null) {
            view.showProgressBar();
        }
        disposable = new CompositeDisposable();
        int weekOffYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        if (sharedPreferences.getInt(Constants.WEEKOFYEAR, 0) == 0 || weekOffYear != sharedPreferences.getInt(Constants.WEEKOFYEAR, 0)) {
            disposable.add(repositoryService.getTopArtistsFromNetwork().subscribe(updateViewWithTopArtist()));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constants.WEEKOFYEAR, Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
            editor.apply();
        } else {
            disposable.add(repositoryService.getTopArtists().subscribe(
                    updateViewWithTopArtist()
            ));
        }

    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void artistSearchTextChanged(String artistSearchText, boolean adapterHasItems) {
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

    public void onArtistInfoClicked(MusicApi.Artist artist) {
        view.showBioFragment(artist);
    }

    public void onArtistMusicClicked(MusicApi.Artist artist) {
        view.showArtistTracks(artist);
    }

    public void checkSavedInstanceState(boolean savedInstanceStateIsNull, boolean tracksFragmentIsNull, boolean bioFragmentIsNull) {
        if (!savedInstanceStateIsNull) {
            if (!tracksFragmentIsNull) {
                view.reAttachTracksFragment();
            } else if (!bioFragmentIsNull) {
                view.reAttachBioFragment();
            }
        }
    }
}
