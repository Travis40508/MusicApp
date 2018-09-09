package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

@TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
@Database(version = 1, entities = {MusicApi.Track.class, MusicApi.Artist.class, MusicApi.Album.class, MusicApi.TrackInfo.class, MusicApi.AlbumInfo.class, MusicApi.TopArtists.class, MusicApi.TopChartTracks.class}, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase{

    public abstract MusicDao musicDao();
}
