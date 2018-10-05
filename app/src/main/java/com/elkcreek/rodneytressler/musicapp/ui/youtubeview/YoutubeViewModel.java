package com.elkcreek.rodneytressler.musicapp.ui.youtubeview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class YoutubeViewModel extends ViewModel {

    private CompositeDisposable disposable;
    public final RepositoryService repositoryService;
    public MutableLiveData<String> youtubeVideoId = new MutableLiveData<>();
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(true);

    public YoutubeViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();
    }

    public void getVideoId(String trackUid, String artistName, String trackName) {
        disposable.add(repositoryService.getYoutubeVideoId(trackUid, artistName + trackName).subscribe(storeYoutubeVideoId(), throwErrorWhenNoYoutubeVideoId()));
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

    public LiveData<String> getYoutubeVideoId() {
        return youtubeVideoId;
    }
}
