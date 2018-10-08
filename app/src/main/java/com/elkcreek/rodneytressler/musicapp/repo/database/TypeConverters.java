package com.elkcreek.rodneytressler.musicapp.repo.database;

import android.arch.persistence.room.TypeConverter;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class TypeConverters {


    @TypeConverter
    public String fromArtistImageList(List<MusicApi.ArtistImage> artistImages) {
        if (artistImages == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.ArtistImage>>() {
        }.getType();
        String json = gson.toJson(artistImages, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.ArtistImage> toArtistImageList(String artistImageString) {
        if (artistImageString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.ArtistImage>>() {
        }.getType();
        List<MusicApi.ArtistImage> artistImageList = gson.fromJson(artistImageString, type);
        return artistImageList;
    }

    @TypeConverter
    public String fromArtistList(List<MusicApi.Artist> artists) {
        if (artists == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Artist>>() {
        }.getType();
        String json = gson.toJson(artists, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.Artist> toArtistList(String artistsString) {
        if (artistsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Artist>>() {
        }.getType();
        List<MusicApi.Artist> artistList = gson.fromJson(artistsString, type);
        return artistList;
    }

    @TypeConverter
    public String fromSimilarArtistList(List<MusicApi.SimilarArtist> artists) {
        if (artists == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.SimilarArtist>>() {
        }.getType();
        String json = gson.toJson(artists, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.SimilarArtist> toSimilarArtistList(String artistsString) {
        if (artistsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.SimilarArtist>>() {
        }.getType();
        List<MusicApi.SimilarArtist> artistList = gson.fromJson(artistsString, type);
        return artistList;
    }

    @TypeConverter
    public String fromTrackList(List<MusicApi.Track> tracks) {
        if (tracks == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Track>>() {
        }.getType();
        String json = gson.toJson(tracks, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.Track> toTrackList(String tracksString) {
        if (tracksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Track>>() {
        }.getType();
        List<MusicApi.Track> trackList = gson.fromJson(tracksString, type);
        return trackList;
    }

    @TypeConverter
    public String fromSimilarTrackList(List<MusicApi.SimilarTrack> tracks) {
        if (tracks == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.SimilarTrack>>() {
        }.getType();
        String json = gson.toJson(tracks, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.SimilarTrack> toSimilarTrackList(String tracksString) {
        if (tracksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.SimilarTrack>>() {
        }.getType();
        List<MusicApi.SimilarTrack> trackList = gson.fromJson(tracksString, type);
        return trackList;
    }

    @TypeConverter
    public String fromAlbumTrackList(List<MusicApi.AlbumTrack> tracks) {
        if (tracks == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.AlbumTrack>>() {
        }.getType();
        String json = gson.toJson(tracks, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.AlbumTrack> toAlbumTrackList(String tracksString) {
        if (tracksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.AlbumTrack>>() {
        }.getType();
        List<MusicApi.AlbumTrack> trackList = gson.fromJson(tracksString, type);
        return trackList;
    }

    @TypeConverter
    public String fromTopTrackList(List<MusicApi.TopTrack> tracks) {
        if (tracks == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.TopTrack>>() {
        }.getType();
        String json = gson.toJson(tracks, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.TopTrack> toTopTrackList(String tracksString) {
        if (tracksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.TopTrack>>() {
        }.getType();
        List<MusicApi.TopTrack> trackList = gson.fromJson(tracksString, type);
        return trackList;
    }

    @TypeConverter
    public String fromAlbumList(List<MusicApi.Album> albumList) {
        if (albumList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Album>>() {
        }.getType();
        String json = gson.toJson(albumList, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.Album> toAlbumList(String albumString) {
        if (albumString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.Album>>() {
        }.getType();
        List<MusicApi.Album> albumlist = gson.fromJson(albumString, type);
        return albumlist;
    }

    @TypeConverter
    public String fromTrackImageList(List<MusicApi.TrackImage> trackImages) {
        if (trackImages == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.TrackImage>>() {
        }.getType();
        String json = gson.toJson(trackImages, type);
        return json;
    }

    @TypeConverter
    public List<MusicApi.TrackImage> toTrackImages(String trackImageString) {
        if (trackImageString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MusicApi.TrackImage>>() {
        }.getType();
        List<MusicApi.TrackImage> trackImageList = gson.fromJson(trackImageString, type);
        return trackImageList;
    }

}
