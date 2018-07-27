package com.elkcreek.rodneytressler.musicapp.repo.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicApi {
    @GET("/2.0?method=artist.search&format=json&limit=12")
    Observable<SearchResponse> getArtistSearchResults(@Query("artist") String artist, @Query("api_key") String apiKey);

    @GET("/2.0?method=artist.getinfo&format=json&limit=5")
    Observable<ArtistBioResponse> getArtistBio(@Query("artist") String artist, @Query("api_key") String apiKey);

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

        @SerializedName("bio")
        @Expose private ArtistBio artistBio;

        public String getArtistName() {
            return artistName;
        }

        public List<ArtistImage> getArtistImages() {
            return artistImages;
        }

        public ArtistBio getArtistBio() {
            return artistBio;
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

    class ArtistBioResponse {
        @SerializedName("artist")
        @Expose private Artist artist;

        public Artist getArtist() {
            return artist;
        }
    }

    class ArtistBio {
        @SerializedName("content")
        @Expose private String bioContent;

        public String getBioContent() {
            return bioContent;
        }
    }
}
