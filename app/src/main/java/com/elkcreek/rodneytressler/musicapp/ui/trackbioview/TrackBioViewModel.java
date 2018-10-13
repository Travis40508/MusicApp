package com.elkcreek.rodneytressler.musicapp.ui.trackbioview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.lang.reflect.Array;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrackBioViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private MainViewModel mainViewModel;
    public ObservableBoolean bioIsExpanded = new ObservableBoolean(false);
    public ObservableField<String> trackContent = new ObservableField<>(Constants.NO_TRACK_BIO_AVAILABLE);
    public ObservableField<String> trackSummary = new ObservableField<>(Constants.NO_TRACK_BIO_AVAILABLE);
    public ObservableField<String> imageUrl = new ObservableField<>("");
    public ObservableField<List<Object>> similarTracks = new ObservableField<>(new ArrayList<>());

    public TrackBioViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void fetchTrackBio(String trackUid, String trackName, String artistName) {
        if (trackUid != null && !trackUid.isEmpty()) {
            disposable.add(repositoryService.getTrack(trackUid).subscribe(updateUiWithTrack(), updateUiOnError()));
        } else {
            disposable.add(repositoryService.getTrackWithName(trackName, artistName).subscribe(updateUiWithTrack(), updateUiOnError()));
        }
    }

    public void fetchSimilarTracks(String trackUid, String trackName, String artistName) {
        if (trackUid != null && !trackUid.isEmpty()) {
            disposable.add(repositoryService.getSimilarTrackList(trackUid).subscribe(updateUiWithSimilarTracks(), updateUiWithSimilarTrackError()));
        } else {
            disposable.add(repositoryService.getSimilarTracksByName(artistName, trackName, Constants.API_KEY).subscribe(updateUiWithSimilarTracks(), updateUiWithSimilarTrackError()));
        }
    }

    private Consumer<MusicApi.TrackInfo> updateUiWithTrack() {
        return track -> {
            mainViewModel.showLoadingLayout.set(false);
            trackSummary.set(track.getWiki() != null ? track.getWiki().getTrackSummary() : Constants.NO_TRACK_BIO_AVAILABLE);
            trackContent.set(track.getWiki() != null ? track.getWiki().getTrackContent() : Constants.NO_TRACK_BIO_AVAILABLE);
            if (track.getTrackAlbum() != null && track.getTrackAlbum().getTrackImage() != null) {
                imageUrl.set(track.getTrackAlbum().getTrackImage().get(2).getImageUrl());
            }
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@-TrackBioViewModel", throwable.getMessage());
            mainViewModel.showLoadingLayout.set(false);
            if (throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                mainViewModel.shouldPopFragment.postValue(true);
                mainViewModel.errorToastMessage.postValue(Constants.CONNECTION_ERROR);
            }
        };
    }

    private Consumer<List<MusicApi.SimilarTrack>> updateUiWithSimilarTracks() {
        return trackList -> {
            if (trackList != null && !trackList.isEmpty()) {
                similarTracks.set(Arrays.asList(trackList.toArray()));
            }
        };
    }

    private Consumer<Throwable> updateUiWithSimilarTrackError() {
        return throwable -> {
            mainViewModel.showLoadingLayout.set(false);
            Log.d("@@@@-TrackBioViewModel", throwable.getMessage());
        };
    }

    public void readMoreClicked() {
        bioIsExpanded.set(!bioIsExpanded.get());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
