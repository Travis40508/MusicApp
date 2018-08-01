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



}
