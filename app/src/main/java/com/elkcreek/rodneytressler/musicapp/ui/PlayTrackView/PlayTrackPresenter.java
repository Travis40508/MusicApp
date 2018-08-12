package com.elkcreek.rodneytressler.musicapp.ui.PlayTrackView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.services.YoutubeApiService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PlayTrackPresenter implements BasePresenter<PlayTrackView> {

    private final YoutubeApiService youtubeApiService;
    private final RepositoryService repositoryService;
    private final MusicApiService musicApiService;
    private PlayTrackView view;
    private CompositeDisposable compositeDisposable;
    private boolean isExpanded;
    private static final String READ_MORE_TEXT_COLLAPSE = "Collapse";
    private static final String READ_MORE_TEXT_EXPAND = "Read More";
    private String trackUid;
    private String videoId;

    @Inject
    public PlayTrackPresenter(YoutubeApiService youtubeApiService, RepositoryService repositoryService, MusicApiService musicApiService) {
        this.youtubeApiService = youtubeApiService;
        this.repositoryService = repositoryService;
        this.musicApiService = musicApiService;
    }

    @Override
    public void attachView(PlayTrackView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
        view.showLoadingLayout();
    }

    @Override
    public void unsubscribe() {

    }

    public void screenRotated(boolean savedInstanceStateIsNull, boolean playTracksFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!playTracksFragmentIsNull) {
                view.reAttachPlayTracksFragment();
            }
        }
    }

    public void getVideoId(String trackName, String artistName) {
        compositeDisposable.add(youtubeApiService.getYoutubeVideo(Constants.YOUTUBE_API_KEY, trackName + artistName)
                .subscribe(youtubeResponse -> {
                   this.videoId = youtubeResponse.getYoutubeItemsList().get(0).getYoutubeItemId().getYoutubeVideoId();
                }));
    }

    public void trackRetrieved(String trackUid) {
        this.trackUid = trackUid;
        fetchTrack();
    }

    private void fetchTrack() {
        //        compositeDisposable.add(repositoryService.getTrack(trackUid).subscribe(updateUiWithTrack(), updateUiOnError()));
        compositeDisposable.add(musicApiService.getTrackInfo(trackUid, Constants.API_KEY).map(MusicApi.TrackInfoResponse::getTrack)
                .subscribe(updateUiWithTrack(), updateUiOnError()));
    }

    private Consumer<MusicApi.Track> updateUiWithTrack() {
        return track -> {
            if (!isExpanded) {
                view.showTrackSummary(track.getWiki().getTrackSummary());
            } else {
                view.showTrackContent(track.getWiki().getTrackContent());
            }
            view.hideLoadingLayout();
            view.showTrackAlbumCover(track.getAlbum().getTrackImage().get(2).getImageUrl());
            view.showArtistName(track.getArtist().getArtistName());
            view.showTrackName(track.getTrackName());
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
          Log.d("@@@@", throwable.getMessage());
        };
    }

    public void onReadMoreClicked(String readMoreText) {
        isExpanded = !isExpanded;
        if (readMoreText.equalsIgnoreCase("Read More")) {
            view.setReadMoreText(READ_MORE_TEXT_COLLAPSE);
        } else {
            view.setReadMoreText(READ_MORE_TEXT_EXPAND);
        }
        fetchTrack();
    }

    public void imageAlbumCoverClicked() {
        view.showVideo(videoId);
    }
}
