package com.elkcreek.rodneytressler.musicapp.repo.network;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MusicApi {
    @GET("/2.0?method=artist.search&format=json&limit=12")
    Observable<SearchResponse> getArtistSearchResults(@Query("artist") String artist, @Query("api_key") String apiKey);

    @GET("/2.0?method=artist.getinfo&format=json")
    Observable<ArtistBioResponse> getArtistBio(@Query("mbid") String mbid, @Query("api_key") String apiKey);

    @GET("/2.0?method=artist.getinfo&format=json")
    Observable<ArtistBioResponse> getArtistBioWithName(@Query("artist") String artistName, @Query("api_key") String apiKey);

    @GET("/2.0?method=chart.gettopartists&format=json")
    Observable<TopArtistsResponse> getTopArtists(@Query("api_key") String apiKey);

    @GET("/2.0?method=artist.gettoptracks&format=json")
    Observable<TopTracksResponse> getTopTracks(@Query("mbid") String mbid, @Query("api_key") String apiKey);

    @GET("/2.0/?method=track.getInfo&format=json")
    Observable<TrackInfoResponse> getTrackInfo(@Query("mbid") String trackUid, @Query("api_key") String apiKey);

    class SearchResponse {
        @SerializedName("results")
        @Expose
        private SearchResults searchResults;

        public SearchResults getSearchResults() {
            return searchResults;
        }
    }

    class SearchResults {
        @SerializedName("artistmatches")
        @Expose
        private ArtistMatches artistMatches;

        public ArtistMatches getArtistMatches() {
            return artistMatches;
        }
    }

    class ArtistMatches {
        @SerializedName("artist")
        @Expose
        private List<Artist> artistList;

        public List<Artist> getArtistList() {
            return artistList;
        }
    }

    @Entity
    class Artist {

        @PrimaryKey(autoGenerate = true)
        int artistPrimaryKey;

        @SerializedName("name")
        @Expose
        private String artistName;


        @TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
        @SerializedName("image")
        @Expose
        private List<ArtistImage> artistImages;


        @Embedded
        @SerializedName("bio")
        @Expose
        private ArtistBio artistBio;

        @SerializedName("mbid")
        @Expose
        private String artistUID;

        @Embedded
        @SerializedName("similar")
        @Expose private Similar similar;

        private boolean isTopArtist;

        public String getArtistName() {
            return artistName;
        }

        public List<ArtistImage> getArtistImages() {
            return artistImages;
        }

        public ArtistBio getArtistBio() {
            return artistBio;
        }

        public String getArtistUID() {
            return artistUID;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public void setArtistImages(List<ArtistImage> artistImages) {
            this.artistImages = artistImages;
        }

        public void setArtistBio(ArtistBio artistBio) {
            this.artistBio = artistBio;
        }

        public void setArtistUID(String artistUID) {
            this.artistUID = artistUID;
        }

        public int getArtistPrimaryKey() {
            return artistPrimaryKey;
        }

        public void setArtistPrimaryKey(int artistPrimaryKey) {
            this.artistPrimaryKey = artistPrimaryKey;
        }

        public Similar getSimilar() {
            return similar;
        }

        public void setSimilar(Similar similar) {
            this.similar = similar;
        }

        public boolean isTopArtist() {
            return isTopArtist;
        }

        public void setTopArtist(boolean topArtist) {
            isTopArtist = topArtist;
        }
    }

    @Entity
    class Similar {
        @TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
        @SerializedName("artist")
        @Expose private List<Artist> artistList;

        public List<Artist> getArtistList() {
            return artistList;
        }

        public void setArtistList(List<Artist> artistList) {
            this.artistList = artistList;
        }
    }

    class ArtistImage {
        @SerializedName("#text")
        @Expose
        private String imageUrl;

        @SerializedName("size")
        @Expose
        private String imageSize;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageSize() {
            return imageSize;
        }
    }


    class ArtistBioResponse {
        @SerializedName("artist")
        @Expose
        private Artist artist;

        public Artist getArtist() {
            return artist;
        }
    }

    class ArtistBio {
        @SerializedName("content")
        @Expose
        private String bioContent;

        @SerializedName("summary")
        @Expose private String bioSummary;


        public String getBioContent() {
            return bioContent;
        }

        public void setBioContent(String bioContent) {
            this.bioContent = bioContent;
        }

        public String getBioSummary() {
            return bioSummary;
        }

        public void setBioSummary(String bioSummary) {
            this.bioSummary = bioSummary;
        }
    }

    class TopArtistsResponse {
        @SerializedName("artists")
        @Expose
        private Artists artists;

        public Artists getArtists() {
            return artists;
        }
    }

    class Artists {
        @SerializedName("artist")
        @Expose
        private List<Artist> artistList;

        public List<Artist> getArtistList() {
            return artistList;
        }
    }

    class TopTracksResponse {
        @SerializedName("toptracks")
        @Expose
        private TopTracks topTracks;

        public TopTracks getTopTracks() {
            return topTracks;
        }
    }

    class TopTracks {
        @TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
        @SerializedName("track")
        @Expose
        private List<Track> trackList;

        public List<Track> getTrackList() {
            return trackList;
        }
    }


    @Entity
    class Track {
        @SerializedName("name")
        @Expose
        private String trackName;

        @SerializedName("url")
        @Expose
        private String trackUrl;

        @TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
        @SerializedName("image")
        @Expose
        private List<ArtistImage> artistImage;

        @Embedded(prefix = "artist")
        @SerializedName("artist")
        @Expose private Artist artist;

        @Embedded(prefix = "album")
        @SerializedName("album")
        @Expose private Album album;

        @Embedded(prefix = "wiki")
        @SerializedName("wiki")
        @Expose private Wiki wiki;

        @SerializedName("mbid")
        @Expose private String trackUid;

        @PrimaryKey(autoGenerate = true)
        private int primaryKey;



        public String getTrackName() {
            return trackName;
        }

        public String getTrackUrl() {
            return trackUrl;
        }

        public List<ArtistImage> getArtistImage() {
            return artistImage;
        }


        public void setTrackName(String trackName) {
            this.trackName = trackName;
        }

        public void setTrackUrl(String trackUrl) {
            this.trackUrl = trackUrl;
        }

        public int getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(int primaryKey) {
            this.primaryKey = primaryKey;
        }

        public void setArtistImage(List<ArtistImage> artistImage) {
            this.artistImage = artistImage;
        }

        public Artist getArtist() {
            return artist;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }

        public String getTrackUid() {
            return trackUid;
        }

        public void setTrackUid(String trackUid) {
            this.trackUid = trackUid;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public Wiki getWiki() {
            return wiki;
        }

        public void setWiki(Wiki wiki) {
            this.wiki = wiki;
        }
    }

    class TrackInfoResponse {
        @SerializedName("track")
        @Expose private Track track;

        public Track getTrack() {
            return track;
        }
    }

    class Album {

        @TypeConverters(com.elkcreek.rodneytressler.musicapp.repo.database.TypeConverters.class)
        @SerializedName("image")
        @Expose
        private List<TrackImage> trackImage;

        public List<TrackImage> getTrackImage() {
            return trackImage;
        }

        public void setTrackImage(List<TrackImage> trackImage) {
            this.trackImage = trackImage;
        }
    }

    class TrackImage {
        @SerializedName("#text")
        @Expose
        private String imageUrl;

        @SerializedName("size")
        @Expose
        private String imageSize;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageSize() {
            return imageSize;
        }
    }

    class Wiki {
        @SerializedName("summary")
        @Expose private String trackSummary;

        @SerializedName("content")
        @Expose private String trackContent;

        public String getTrackSummary() {
            return trackSummary;
        }

        public void setTrackSummary(String trackSummary) {
            this.trackSummary = trackSummary;
        }

        public String getTrackContent() {
            return trackContent;
        }

        public void setTrackContent(String trackContent) {
            this.trackContent = trackContent;
        }
    }
}
