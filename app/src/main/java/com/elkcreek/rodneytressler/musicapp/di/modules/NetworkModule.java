package com.elkcreek.rodneytressler.musicapp.di.modules;

import com.elkcreek.rodneytressler.musicapp.repo.network.LyricsApi;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.repo.network.YoutubeApi;
import com.elkcreek.rodneytressler.musicapp.services.LyricsApiService;
import com.elkcreek.rodneytressler.musicapp.services.LyricsApiServiceImpl;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiService;
import com.elkcreek.rodneytressler.musicapp.services.MusicApiServiceImpl;
import com.elkcreek.rodneytressler.musicapp.services.YoutubeApiService;
import com.elkcreek.rodneytressler.musicapp.services.YoutubeApiServiceImpl;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private final String baseUrl;
    private final String youtubeBaseUrl;
    private final String lyricsBaseUrl;

    public NetworkModule(String baseUrl, String youtubeBaseUrl, String lyricsBaseUrl) {
        this.baseUrl = baseUrl;
        this.youtubeBaseUrl = youtubeBaseUrl;
        this.lyricsBaseUrl = lyricsBaseUrl;
    }

    @Provides
    OkHttpClient providesOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    }

    @Provides
    @Named(Constants.MUSIC_RETROFIT)
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Named(Constants.YOUTUBE_RETROFIT)
    Retrofit providesYoutubeRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(youtubeBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    @Provides
    @Named(Constants.LYRICS_RETROFIT)
    Retrofit providesLyricsRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(lyricsBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    @Provides
    MusicApi providesMusicApi(@Named(Constants.MUSIC_RETROFIT) Retrofit retrofit) {
        MusicApi musicApi = retrofit.create(MusicApi.class);

        return musicApi;
    }

    @Provides
    MusicApiService providesMusicApiService(MusicApi musicApi) {
        return new MusicApiServiceImpl(musicApi);
    }

    @Provides
    YoutubeApi providesYoutubeApi(@Named(Constants.YOUTUBE_RETROFIT) Retrofit retrofit) {
        YoutubeApi youtubeApi = retrofit.create(YoutubeApi.class);

        return youtubeApi;
    }

    @Provides
    YoutubeApiService providesYoutubeApiService(YoutubeApi youtubeApi) {
        return new YoutubeApiServiceImpl(youtubeApi);
    }

    @Provides
    LyricsApi providesLyricsApi(@Named(Constants.LYRICS_RETROFIT) Retrofit retrofit) {
        LyricsApi lyricsApi = retrofit.create(LyricsApi.class);

        return lyricsApi;
    }

    @Provides
    LyricsApiService providesLyricsApiService(LyricsApi lyricsApi) {
        return new LyricsApiServiceImpl(lyricsApi);
    }
}
