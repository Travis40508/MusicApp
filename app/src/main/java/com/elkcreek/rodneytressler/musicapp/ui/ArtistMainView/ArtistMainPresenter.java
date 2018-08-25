package com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView;

import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ArtistMainPresenter implements BasePresenter<ArtistMainView> {

    private CompositeDisposable disposable;
    private ArtistMainView view;
    private String artistUid;
    private String artistName;

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
        view.showScreens(artistUid, artistName);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
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
}
