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

    @Query("SELECT * FROM Track WHERE trackUid LIKE :trackUid")
    Flowable<List<MusicApi.Track>> getTrack(String trackUid);

    @Query("SELECT * FROM Artist WHERE artistName LIKE :artistName")
    Flowable<List<MusicApi.Artist>> getArtistBioWithName(String artistName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopArtist(MusicApi.Artist artist);

    @Query("SELECT * FROM Artist WHERE isTopArtist LIKE :isTopArtist")
    Flowable<List<MusicApi.Artist>> getTopArtists(boolean isTopArtist);

    @Query("UPDATE Artist SET bioSummary = :artistSummary, bioContent = :artistContent, artistList = :similarArtistList WHERE artistUID = :artistUID")
    void updateArtist(String artistSummary, String artistContent, String artistUID, List<MusicApi.Artist> similarArtistList);

    @Query("UPDATE Artist SET artistUID = :artistUID WHERE artistName = :artistName")
    void updateArtistWithName(String artistUID, String artistName);

    @Query("UPDATE Track SET trackUid = :trackUid WHERE trackName = :trackName AND artistartistUID = :artistUid")
    void updateTrack(String trackUid, String trackName, String artistUid);

    @Query("DELETE FROM Artist WHERE isTopArtist = :isTopArtist")
    void deleteTopArtists(boolean isTopArtist);

    @Query("DELETE FROM Track WHERE trackUid = :trackUid")
    void deleteTrack(String trackUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopTracks(List<MusicApi.Track> trackList);
}
