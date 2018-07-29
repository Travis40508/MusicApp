package com.elkcreek.rodneytressler.musicapp.services;

import com.elkcreek.rodneytressler.musicapp.repo.database.MusicDatabase;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
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

    public void insertTrack(MusicApi.Track track) {
        disposable.add(Observable.just(database)
                .subscribeOn(Schedulers.io())
                .subscribe(musicDatabase -> {
                    musicDatabase.musicDao().insertTrack(track);
                }));
    }

    public Flowable<List<MusicApi.Track>> getTrackList() {
        return database.musicDao().getTrackList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void deleteTracks() {
        disposable.add(Observable.just(database)
        .subscribeOn(Schedulers.io()).subscribe(musicDatabase -> musicDatabase.musicDao().deleteTracks()));
    }
}
