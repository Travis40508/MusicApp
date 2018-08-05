package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.database.MusicDatabase;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MusicDatabaseServiceImpl implements MusicDatabaseService {

    private final MusicDatabase database;
    private CompositeDisposable disposable;

    public MusicDatabaseServiceImpl(MusicDatabase database) {
        this.database = database;
        disposable = new CompositeDisposable();
    }

    @Override
    public void insertTrack(MusicApi.Track track) {
        disposable.add(Observable.just(database)
                .subscribeOn(Schedulers.io())
                .subscribe(musicDatabase -> {
                    musicDatabase.musicDao().insertTrack(track);
                }));
    }

    @Override
    public Flowable<List<MusicApi.Track>> getTrackList(String artistUid) {
        return database.musicDao().getTrackList(artistUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public void insertBioResponse(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertArtist(artist);
            }
        });
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid) {
        return database.musicDao().getArtistBios(artistUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toObservable().map(artists -> artists.get(0));
    }

    @Override
    public void insertTopArtist(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                artist.setTopArtist(true);
                database.musicDao().insertTopArtist(artist);
            }
        });
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtists() {
        return database.musicDao().getTopArtists(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }

    @Override
    public void updateTopArtist(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                String artistSummary = artist.getArtistBio().getBioSummary();
                String artistContent = artist.getArtistBio().getBioContent();
                String artistUID = artist.getArtistUID();
                List<MusicApi.Artist> similarArtistList = artist.getSimilar().getArtistList();

                database.musicDao().updateArtist(artistSummary, artistContent, artistUID, similarArtistList);
            }
        });
    }

    @Override
    public void deleteTopArtists() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().deleteTopArtists(true);
            }
        });
    }

}
