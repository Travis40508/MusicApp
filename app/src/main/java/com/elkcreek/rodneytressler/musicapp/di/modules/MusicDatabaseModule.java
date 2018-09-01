package com.elkcreek.rodneytressler.musicapp.di.modules;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;

import com.elkcreek.rodneytressler.musicapp.repo.database.MusicDatabase;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseServiceImpl;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MusicDatabaseModule {

    @Provides
    @Singleton
    MusicDatabase providesMusicDatabase(Context context) {
        MusicDatabase musicDatabase = Room.databaseBuilder
                (context.getApplicationContext(),
                        MusicDatabase.class,
                        Constants.DATABASE_KEY)
                .build();
        return musicDatabase;
    }

    @Provides
    MusicDatabaseService providesMusicDatabaseService(MusicDatabase musicDatabase, SharedPreferences sharedPreferences) {
        return new MusicDatabaseServiceImpl(musicDatabase, sharedPreferences);
    }

    @Provides
    SharedPreferences providesSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFKEY, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}
