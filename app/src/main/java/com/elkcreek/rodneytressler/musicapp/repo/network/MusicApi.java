package com.elkcreek.rodneytressler.musicapp.repo.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicApi {
    @GET("/2.0?method=artist.search&format=json")
    Observable<SearchResponse> getArtistSearchResults(@Query("artist") String artist, @Query("api_key") String apiKey);

    class SearchResponse {
        @SerializedName("results")
        @Expose private SearchResults searchResults;

        public SearchResults getSearchResults() {
            return searchResults;
        }
    }

    class SearchResults {
        @SerializedName("artistmatches")
        @Expose private ArtistMatches artistMatches;

        public ArtistMatches getArtistMatches() {
            return artistMatches;
        }
    }

    class ArtistMatches {
        @SerializedName("artist")
        @Expose private List<Artist> artistList;

        public List<Artist> getArtistList() {
            return artistList;
        }
    }

    class Artist {
        @SerializedName("name")
        @Expose private String artistName;

        @SerializedName("image")
        @Expose private List<ArtistImage> artistImages;

        public String getArtistName() {
            return artistName;
        }

        public List<ArtistImage> getArtistImages() {
            return artistImages;
        }
    }

    class ArtistImage {
        @SerializedName("#text")
        @Expose private String imageUrl;

        @SerializedName("size")
        @Expose private String imageSize;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageSize() {
            return imageSize;
        }
    }
}
