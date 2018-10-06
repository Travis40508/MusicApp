package com.elkcreek.rodneytressler.musicapp.ui.albumsview;

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

public class AlbumsViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    public ObservableField<List<Object>> albumList = new ObservableField<>(new ArrayList<>());
    public ObservableBoolean shouldShowLoadingLayout = new ObservableBoolean(true);

    public AlbumsViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        disposable = new CompositeDisposable();
    }

    public void fetchItems(String artistUid) {
        disposable.add(repositoryService.getAlbums(artistUid).subscribe(updateUiWithAlbum(), updateUiWithError()));
    }

    private Consumer<List<MusicApi.Album>> updateUiWithAlbum() {
        return albumList -> {
            this.albumList.set(Arrays.asList(albumList.toArray()));
            shouldShowLoadingLayout.set(false);
        };
    }

    private Consumer<Throwable> updateUiWithError() {
        return throwable -> {
            Log.d("@@@@-AlbumsViewModel", throwable.getMessage());
            shouldShowLoadingLayout.set(false);
        };
    }
}
