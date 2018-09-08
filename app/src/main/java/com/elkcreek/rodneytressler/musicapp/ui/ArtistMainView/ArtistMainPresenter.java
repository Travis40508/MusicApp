package com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ArtistMainPresenter implements BasePresenter<ArtistMainView> {

    private CompositeDisposable disposable;
    private ArtistMainView view;
    private String artistUid;
    private String artistName;
    private int currentItem;

    @Inject
    public ArtistMainPresenter() {

    }

    @Override
    public void attachView(ArtistMainView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        view.setActionBarTitle(artistName);
        view.showScreens(artistUid, artistName);
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public void artistRetrieved(String artistUid) {
        this.artistUid = artistUid;
    }

    public void artistNameRetrieved(String artistName) {
        this.artistName = artistName;
    }

    public void checkSavedInstanceState(boolean savedInstanceStateIsNull, boolean artistMainFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!artistMainFragmentIsNull) {
                view.reAttachFragment();
            }
        }
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
