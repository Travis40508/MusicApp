package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackSearchViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    public ObservableField<List<Object>> trackList = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(true);
    public ObservableField<String> trackSearchValue = new ObservableField<>(Constants.CURRENT_TOP_TRACKS);
    public ObservableInt scrollPosition = new ObservableInt(0);
    private Timer timer;

    public TrackSearchViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        disposable = new CompositeDisposable();
        fetchTopTracks();
    }

    private void fetchTopTracks() {
        disposable.add(repositoryService.getTopTracks().subscribe(updateUiWithTracks(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.TopTrack>> updateUiWithTracks() {
        return trackList -> {
            this.trackList.set(Arrays.asList(trackList.toArray()));
            showLoadingLayout.set(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@-TrackSearchVM", throwable.getMessage());
            showLoadingLayout.set(false);
        };
    }

    public void onTrackSearchTextChanged(CharSequence trackSearchText, int start, int before, int count) {
        showLoadingLayout.set(false);
        if (timer != null) {
            timer.cancel();
        }

        if (trackSearchText.length() == 0) {
            fetchTopTracks();
            trackSearchValue.set(Constants.CURRENT_TOP_TRACKS);
        } else {
            trackSearchValue.set("Showing results for '" + trackSearchText + "'");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showLoadingLayout.set(true);
                    trackList.set(new ArrayList<>());
                    disposable.add(repositoryService.getSearchedTracksFromNetwork((trackSearchText.toString()))
                            .subscribe(getSearchResponse(), updateUiWithError()));
                }
            }, 750);
        }
    }

    private Consumer<List<MusicApi.SearchedTrack>> getSearchResponse() {
        return trackList -> {
            showLoadingLayout.set(false);
            this.trackList.set(Arrays.asList(trackList.toArray()));
        };
    }
}
