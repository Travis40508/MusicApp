package com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AlbumTracksPresenter implements BasePresenter<AlbumTracksView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private AlbumTracksView view;
    private String artistName;
    private String artistUid;
    private String albumName;
    private String albumUid;
    private String imageUrl;

    @Inject
    public AlbumTracksPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AlbumTracksView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchTracks();
    }

    private void fetchTracks() {
        disposable.add(repositoryService.getTracksFromAlbum(albumUid).subscribe(updateUiWithTracks(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Track>> updateUiWithTracks() {
        return trackList -> {
            view.showTrackListForAlbum(trackList, imageUrl);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@", throwable.getMessage());
        };
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void onScreenRotated(boolean savedInstanceStateIsNull, boolean albumTracksIsNull) {
        if (!savedInstanceStateIsNull) {
            if (!albumTracksIsNull) {
                view.reAttachAlbumTracksFragment();
            }
        }
    }

    public void artistRetrieved(String artistName, String artistUid) {
        this.artistName = artistName;
        this.artistUid = artistUid;
    }

    public void albumRetrieved(String albumName, String albumUid, String imageUrl) {
        this.albumName = albumName;
        this.albumUid = albumUid;
        this.imageUrl = imageUrl;
    }

    public void albumClicked(MusicApi.Track track) {

    }
}
