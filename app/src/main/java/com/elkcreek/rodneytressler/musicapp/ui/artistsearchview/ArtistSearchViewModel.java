package com.elkcreek.rodneytressler.musicapp.ui.artistsearchview;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.EventHandlers;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ArtistSearchViewModel extends ViewModel {

    public final MusicApiService musicApiService;
    public final RepositoryService repositoryService;
    public ObservableField<List<MusicApi.Artist>> artists = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showProgressBar = new ObservableBoolean(true);
    public ObservableField<String> artistSearchValue = new ObservableField<>(Constants.CURRENT_TOP_ARTISTS);
    public MutableLiveData<String> errorToastMessage = new MutableLiveData<>();
    public CompositeDisposable disposable;
    private Timer timer;

    public ArtistSearchViewModel(MusicApiService musicApiService, RepositoryService repositoryService) {
        this.musicApiService = musicApiService;
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();

        fetchTopArtists();
        checkCache();
    }

    private void checkCache() {
        if (repositoryService.isSameWeekSinceLastLaunch()) {
            repositoryService.clearCache();
            repositoryService.saveDate();
        }
    }

    private void fetchTopArtists() {
        disposable.add(repositoryService.getTopArtists().subscribe(updateViewWithTopArtist(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Artist>> updateViewWithTopArtist() {
        return topArtists -> {
            artists.set(topArtists);
            setShowProgressBar(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            if (throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                errorToastMessage.postValue(Constants.CONNECTION_ERROR);
                EventHandlers handlers = new EventHandlers();
                handlers.popFragment();
            } else {
                errorToastMessage.postValue(Constants.LOADING_ERROR);
                repositoryService.resetDate();
                fetchTopArtists();
            }
        };
    }

    public void onArtistSearchTextChanged(CharSequence artistSearchText, int start, int before, int count) {
        setShowProgressBar(false);
        if (timer != null) {
            timer.cancel();
        }

        if (artistSearchText.length() == 0) {
            fetchTopArtists();
            artistSearchValue.set(Constants.CURRENT_TOP_ARTISTS);
        } else {
            artistSearchValue.set("Showing results for '" + artistSearchText + "'");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    disposable.add(getArtistSearchResults(artistSearchText.toString()).subscribe(getSearchResponse(), updateUiWithError()));
                }
            }, 750);
        }
    }

    private Observable<List<MusicApi.Artist>> getArtistSearchResults(String artistSearchText) {
        setShowProgressBar(true);
        return musicApiService.getArtistSearchResults(artistSearchText, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(Observable.empty())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Consumer<List<MusicApi.Artist>> getSearchResponse() {
        return searchResponse -> {
            setShowProgressBar(false);
            artists.set(searchResponse);
//            view.scrollRecyclerViewToTop();
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void setShowProgressBar(boolean shouldShowProgressBar) {
        showProgressBar.set(shouldShowProgressBar);
    }
}
