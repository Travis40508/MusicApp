package com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView;

import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AlbumBioPresenter implements BasePresenter<AlbumBioView> {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private AlbumBioView view;

    @Inject
    public AlbumBioPresenter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void attachView(AlbumBioView view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    public void screenRotated(boolean savedInstanceStateIsNull, boolean albumBioFragmentIsNull) {
        if(!savedInstanceStateIsNull) {
            if(!albumBioFragmentIsNull) {
                view.reAttachFragment();
            }
        }
    }
}
