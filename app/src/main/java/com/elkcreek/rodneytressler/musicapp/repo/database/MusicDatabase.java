package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

@Database(version = 1, entities = {MusicApi.Track.class, MusicApi.ArtistBio.class}, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase{

    public abstract MusicDao musicDao();
}
