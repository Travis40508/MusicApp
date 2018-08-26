package com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AlbumMainPresenter implements BasePresenter<AlbumMainView> {

    private CompositeDisposable disposable;
    private AlbumMainView view;
    private String artistName;
    private String artistUid;
    private String albumName;
    private String albumUid;
    private String albumImage;
    private int currentItem;

    @Inject
    public AlbumMainPresenter() {

    }

    @Override
    public void attachView(AlbumMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        view.setActionBarTitle(artistName + " - " + albumName);
        view.showScreens(artistName, artistUid, albumName, albumUid, albumImage);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void checkSavedInstanceState(boolean savedInstanceStateIsNull, boolean albumMainFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!albumMainFragmentIsNull) {
                view.reAttachFragment();
            }
        }
    }

    public void artistRetrieved(String artistName, String artistUid) {
        this.artistName = artistName;
        this.artistUid = artistUid;
    }

    public void albumRetrieved(String albumName, String albumUid, String albumImage) {
        this.albumName = albumName;
        this.albumUid = albumUid;
        this.albumImage = albumImage;
    }

    public void screenPaused(int currentItem) {
        this.currentItem = currentItem;
    }

    public void screenRestored() {
        if(currentItem != 0) {
            view.setViewPagerItem(currentItem);
        }
    }
}
