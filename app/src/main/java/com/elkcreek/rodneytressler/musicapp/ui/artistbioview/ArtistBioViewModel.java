package com.elkcreek.rodneytressler.musicapp.ui.artistbioview;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ArtistBioViewModel extends ViewModel {

    private final RepositoryService repositoryService;
    private CompositeDisposable disposable;
    private MainViewModel mainViewModel;
    public ObservableBoolean bioIsExpanded = new ObservableBoolean(false);
    public ObservableField<String> bioSummary = new ObservableField<>();
    public ObservableField<String> bioContent = new ObservableField<>();
    public ObservableField<MusicApi.Artist> artist = new ObservableField<>();
    public ObservableField<List<Object>> similarArtists = new ObservableField<>(new ArrayList<>());
    public ObservableField<String> similarArtistText = new ObservableField<>(Constants.NO_SIMILAR_ARTISTS);

    public ArtistBioViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

        disposable = new CompositeDisposable();

    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        mainViewModel.showLoadingLayout.set(true);
    }

    public void fetchArtistBio(String artistUid, String artistName) {
        if(artistUid != null) {
            disposable.add(repositoryService.getArtistBio(artistUid, artistName).subscribe(updateUiWithArtist(), updateUiOnError()));
        } else {
            disposable.add(repositoryService.getArtistBioWithName(artistName, Constants.API_KEY).subscribe(updateUiWithArtist(), updateUiOnError()));
        }
    }

    private Consumer<MusicApi.Artist> updateUiWithArtist() {
        return artist -> {
            mainViewModel.showLoadingLayout.set(false);
            this.artist.set(artist);
            bioSummary.set(artist.getArtistBio() != null ? artist.getArtistBio().getBioSummary() : Constants.NO_ARTIST_BIO_AVAILABLE);
            bioContent.set(artist.getArtistBio() != null ? artist.getArtistBio().getBioContent() : Constants.NO_ARTIST_BIO_AVAILABLE);
            if(artist.getSimilar() != null && !artist.getSimilar().getArtistList().isEmpty()) {
                similarArtists.set(Arrays.asList(artist.getSimilar().getArtistList().toArray()));
                similarArtistText.set(Constants.SIMILAR_ARTISTS);
            }
        };
    }

    private Consumer<Throwable> updateUiOnError() {
        return throwable -> {
            mainViewModel.showLoadingLayout.set(false);
            Log.d("@@@@-ArtistBioViewModel", throwable.getMessage());
            if (throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
                mainViewModel.errorToastMessage.postValue(Constants.CONNECTION_ERROR);
                mainViewModel.shouldPopFragment.postValue(true);
            } else {
                bioSummary.set(Constants.NO_ARTIST_BIO_AVAILABLE);
                bioContent.set(Constants.NO_ARTIST_BIO_AVAILABLE);
            }
        };
    }

    public void readMoreClicked() {
        bioIsExpanded.set(!bioIsExpanded.get());
    }
}
