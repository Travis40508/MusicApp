package com.elkcreek.rodneytressler.musicapp.repo.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LyricsApi {

    @GET("{artist}/{title}")
    Observable<LyricsResponse> getLyrics(@Path("artist") String artistName, @Path("title") String songTitle);

    class LyricsResponse {
        @SerializedName("lyrics")
        @Expose private String songLyrics;

        public String getSongLyrics() {
            return songLyrics;
        }
    }
}
