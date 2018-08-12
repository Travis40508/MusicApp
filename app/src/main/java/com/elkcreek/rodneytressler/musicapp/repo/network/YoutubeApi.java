package com.elkcreek.rodneytressler.musicapp.repo.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApi {

    @GET("v3/search?part=snippet")
    Observable<YoutubeResponse> getYoutubeVideo(@Query("key") String youtubeApiKey, @Query("q") String searchQuery);


    class YoutubeResponse {
        @SerializedName("items")
        @Expose private List<YoutubeItems> youtubeItemsList;

        public List<YoutubeItems> getYoutubeItemsList() {
            return youtubeItemsList;
        }
    }

    class YoutubeItems {
        @SerializedName("id")
        @Expose private YoutubeItemId youtubeItemId;

        public YoutubeItemId getYoutubeItemId() {
            return youtubeItemId;
        }
    }

    class YoutubeItemId {
        @SerializedName("videoId")
        @Expose private String youtubeVideoId;

        public String getYoutubeVideoId() {
            return youtubeVideoId;
        }
    }
}
