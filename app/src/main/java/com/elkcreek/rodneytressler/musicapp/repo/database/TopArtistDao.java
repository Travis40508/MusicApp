package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TopArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopArtists(MusicApi.Artist... artist);

    @Query("SELECT * FROM Artist")
    Flowable<List<MusicApi.Artist>> getTopArtists();
}
