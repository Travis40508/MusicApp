package com.elkcreek.rodneytressler.musicapp.ui.AlbumsView;

import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AlbumsPresenter implements BasePresenter<AlbumsView> {
    private final RepositoryService repositoryService;
    private AlbumsView view;
    private String artistName;
    private String artistUid;
    private CompositeDisposable disposable;

    @Inject
    public AlbumsPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AlbumsView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        fetchAlbums();
    }

    private void fetchAlbums() {
        disposable.add(repositoryService.getAlbums(artistUid).subscribe(
                albumList -> {
                    for(MusicApi.Album item : albumList) {
                        Log.d("@@@@", item.getAlbumName());
                    }
                }, throwable -> {
                    Log.d("@@@@", throwable.getMessage());
                }
        ));
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void screenRotated(boolean savedInstanceStateIsNull, boolean albumsFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!albumsFragmentIsNull) {
                view.reAttachAlbumsFragment();
            }
        }
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }
}
