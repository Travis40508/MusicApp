package com.elkcreek.rodneytressler.musicapp.di.modules;


import com.elkcreek.rodneytressler.musicapp.services.RepositoryService;
import com.elkcreek.rodneytressler.musicapp.services.RepositoryServiceImpl;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicDatabaseService;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Provides
    RepositoryService providesCacheService(MusicApiService musicApiService, MusicDatabaseService musicDatabaseService) {
        return new RepositoryServiceImpl(musicApiService, musicDatabaseService);
    }
}
