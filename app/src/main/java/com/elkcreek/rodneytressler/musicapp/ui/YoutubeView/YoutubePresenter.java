package com.elkcreek.rodneytressler.musicapp.ui.YoutubeView;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class YoutubePresenter implements BasePresenter<YoutubeView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private YoutubeView view;
    private String trackName;
    private String artistName;
    private String trackUid;

    @Inject
    public YoutubePresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(YoutubeView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchVideoId();
    }

    private void fetchVideoId() {
        disposable.add(repositoryService.getYoutubeVideoId(trackUid, trackName + artistName).subscribe(storeYoutubeVideoId(), throwErrorWhenNoYoutubeVideoId()));
    }

    private Consumer<String> storeYoutubeVideoId() {
        return view::showVideo;
    }

    private Consumer<Throwable> throwErrorWhenNoYoutubeVideoId() {
        return throwable -> {
            view.toastUnableToLoadVideo(Constants.UNABLE_TO_LOAD_VIDEO);
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void screenRotated(boolean onSavedInstanceStateIsNull, boolean youtubeFragmentIsNull) {
        if(!onSavedInstanceStateIsNull) {
            if(!youtubeFragmentIsNull) {
                view.reAttachYoutubeFragment();
            }
        }
    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
    }

    public void getVideoId(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }
}
