package com.elkcreek.rodneytressler.musicapp.ui.alltracksview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AllTracksViewModel extends ViewModel {

    private CompositeDisposable disposable;
    private final RepositoryService repositoryService;
    public ObservableField<List<Object>> topTracks = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean showProgressBar = new ObservableBoolean(true);
    public ObservableField<String> searchedText = new ObservableField<>("");

    public AllTracksViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void fetchTopTracks(String artistUid) {
        disposable.add(repositoryService.getArtistTopTracks(artistUid).subscribe(updateUiWithTopTracks(), updateUiOnError()));
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTopTracks() {
        return tracks -> {
            topTracks.set(Arrays.asList(tracks.toArray()));
            showProgressBar.set(false);
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            Log.d("@@@@-AllTracksViewModel", throwable.getMessage());
            showProgressBar.set(false);
        };
    }

    public String getSearchedTextString() {
        return "Showing results for '" + searchedText.get() + "'";
    }
}
