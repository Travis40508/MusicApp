package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrack(MusicApi.Track track);

    @Query("SELECT * FROM Track WHERE `artistartistUID` LIKE :artistUid")
    Flowable<List<MusicApi.Track>> getTrackList(String artistUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArtist(MusicApi.Artist artist);

    @Query("SELECT * FROM Artist WHERE artistUID LIKE :artistUid")
    Flowable<List<MusicApi.Artist>> getArtistBios(String artistUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopArtist(MusicApi.Artist artist);

    @Query("SELECT * FROM Artist WHERE isTopArtist LIKE :isTopArtist")
    Flowable<List<MusicApi.Artist>> getTopArtists(boolean isTopArtist);
}
