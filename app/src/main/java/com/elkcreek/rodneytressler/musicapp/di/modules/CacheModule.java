package com.elkcreek.rodneytressler.musicapp.di.modules;


import com.elkcreek.rodneytressler.musicapp.services.CacheService;
import com.elkcreek.rodneytressler.musicapp.services.CacheServiceImpl;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Provides
    CacheService providesCacheService(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        return new CacheServiceImpl(musicApiService, musicDatabaseService);
    }
}
