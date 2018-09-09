package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

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

    @Query("SELECT * FROM TopArtists")
    Flowable<List<MusicApi.TopArtists>> getTopArtists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopArtists(MusicApi.TopArtists topArtists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopTracks(MusicApi.TopChartTracks topChartTracks);

    @Query("SELECT * FROM TopChartTracks")
    Flowable<List<MusicApi.TopChartTracks>> getTopChartTracks();

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

    @Query("UPDATE Track SET wikiTrackSummary = :summary, wikiTrackContent = :content, albumtrackImage = :trackImage WHERE trackUid = :trackUid")
    void updateTrack(String trackUid, String summary, String content, List<MusicApi.TrackImage> trackImage);

    @Query("UPDATE Track SET youtubeId = :youtubeId WHERE trackUid = :trackUid")
    void updateTrackWithYoutubeId(String youtubeId, String trackUid);

    @Query("UPDATE Album SET trackList = :trackList WHERE albumUid = :albumUid")
    void updateTrackWithUid(List<MusicApi.Track> trackList, String albumUid);

    @Query("DELETE FROM Artist WHERE isTopArtist = :isTopArtist")
    void deleteTopArtists(boolean isTopArtist);

    @Query("DELETE FROM Track WHERE trackUid = :trackUid")
    void deleteTrack(String trackUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopTracks(List<MusicApi.Track> trackList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<MusicApi.Album> albumList);

    @Query("SELECT * FROM Album WHERE artistartistUID LIKE :artistUid")
    Flowable<List<MusicApi.Album>> getAlbumList(String artistUid);

    @Query("UPDATE Album SET trackList = :trackList WHERE albumUid = :albumUid")
    void updateAlbumWithAlbumUid(List<MusicApi.Track> trackList, String albumUid);

    @Query("SELECT * FROM Album WHERE albumUid LIKE :albumUid")
    Flowable<List<MusicApi.Album>> getAlbumByUid(String albumUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrackInfo(MusicApi.TrackInfo trackInfo);

    @Query("SELECT * FROM TrackInfo WHERE trackUid LIKE :trackUid")
    Flowable<List<MusicApi.TrackInfo>> getTrackInfo(String trackUid);

    @Query("UPDATE TrackInfo SET youtubeId = :youtubeId WHERE trackUid = :trackUid")
    void updateTrackInfoWithYoutubeIdViaTrackUid(String youtubeId, String trackUid);

    @Query("SELECT youtubeId FROM TrackInfo WHERE trackUid = :trackUid")
    Single<String> getTrackInfoYoutubeId(String trackUid);

    @Query("UPDATE TrackInfo SET lyrics = :lyrics WHERE trackUid = :trackUid")
    void updateTrackInfoWithLyrics(String lyrics, String trackUid);

    @Query("SELECT lyrics FROM TrackInfo WHERE trackUid = :trackUid")
    Single<String> getTrackInfoSongLyrics(String trackUid);

    @Query("UPDATE TrackInfo SET similarTrackList = :similarTrackList WHERE trackUid = :trackUid")
    void updateTrackInfoWithSimilarTracksList(List<MusicApi.Track> similarTrackList, String trackUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumInfo(MusicApi.AlbumInfo albumInfo);

    @Query("SELECT * FROM AlbumInfo WHERE albumUid = :albumUid")
    Flowable<List<MusicApi.AlbumInfo>> getAlbumInfo(String albumUid);

    //Clearing Cache
    @Query("DELETE FROM Track")
    void deleteAllTracks();

    @Query("DELETE FROM Artist")
    void deleteAllArtists();

    @Query("DELETE FROM Album")
    void deleteAllAlbums();

    @Query("DELETE FROM AlbumInfo")
    void deleteAllAlbumInfo();

    @Query("DELETE FROM TrackInfo")
    void deleteAllTrackInfo();


}
