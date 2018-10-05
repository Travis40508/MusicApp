package com.elkcreek.rodneytressler.musicapp.ui.youtubeview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class YoutubeViewModel extends ViewModel {

    private CompositeDisposable disposable;
    public final RepositoryService repositoryService;
    public MutableLiveData<String> youtubeVideoId = new MutableLiveData<>();
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(true);
    public ObservableField<String> songLyrics = new ObservableField<>(Constants.NO_LYRICS);

    public YoutubeViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void getVideoIdAndLyrics(String trackUid, String artistName, String trackName) {
        disposable.add(repositoryService.getYoutubeVideoId(trackUid, artistName + trackName).subscribe(storeYoutubeVideoId(), throwErrorWhenNoYoutubeVideoId()));
        disposable.add(repositoryService.getLyrics(artistName, trackName, trackUid).subscribe(updateViewWithLyrics(), throwErrorWhenLyricsNotAvailable()));
    }

    private Consumer<String> storeYoutubeVideoId() {
        return videoId -> {
            youtubeVideoId.postValue(videoId);
            showLoadingLayout.set(false);
        };
    }

    private Consumer<Throwable> throwErrorWhenNoYoutubeVideoId() {
        return throwable -> {
            Log.d("@@@@-YoutubeVM", throwable.getMessage());
            showLoadingLayout.set(false);
        };
    }
    private Consumer<String> updateViewWithLyrics() {
        return lyrics -> {
            songLyrics.set(lyrics);
        };
    }

    private Consumer<Throwable> throwErrorWhenLyricsNotAvailable() {
        return throwable -> {
            Log.d("@@@@-YoutubeVM", throwable.getMessage());
        };
    }


    public LiveData<String> getYoutubeVideoId() {
        return youtubeVideoId;
    }
}
