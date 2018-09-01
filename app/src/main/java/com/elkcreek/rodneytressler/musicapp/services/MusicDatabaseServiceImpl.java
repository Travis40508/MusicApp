package com.elkcreek.rodneytressler.musicapp.services;

import android.content.SharedPreferences;

import com.elkcreek.rodneytressler.musicapp.repo.database.MusicDatabase;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MusicDatabaseServiceImpl implements MusicDatabaseService {

    private final MusicDatabase database;
    private CompositeDisposable disposable;
    private SharedPreferences sharedPreferences;

    public MusicDatabaseServiceImpl(MusicDatabase database, SharedPreferences sharedPreferences) {
        this.database = database;
        this.sharedPreferences = sharedPreferences;
        disposable = new CompositeDisposable();
    }

    @Override
    public void insertTrack(MusicApi.Track track) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertTrack(track);
            }
        });
    }

    @Override
    public Observable<List<MusicApi.Track>> getTrackList(String artistUid) {
        return database.musicDao().getTrackList(artistUid).toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public void insertBioResponse(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertArtist(artist);
            }
        });
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBio(String artistUid) {
        return database.musicDao().getArtistBios(artistUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toObservable().map(artists -> artists.get(0));
    }

    @Override
    public Observable<MusicApi.Artist> getArtistBioWithName(String artistName) {
        return database.musicDao().getArtistBioWithName(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toObservable()
                .map(artists -> artists.get(0));
    }

    @Override
    public void insertTopArtist(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                artist.setTopArtist(true);
                database.musicDao().insertTopArtist(artist);
            }
        });
    }

    @Override
    public Observable<List<MusicApi.Artist>> getTopArtists() {
        return database.musicDao().getTopArtists(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }

    @Override
    public void updateTopArtist(MusicApi.Artist artist) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                String artistSummary = artist.getArtistBio().getBioSummary();
                String artistContent = artist.getArtistBio().getBioContent();
                String artistUID = artist.getArtistUID();
                List<MusicApi.Artist> similarArtistList = artist.getSimilar().getArtistList();

                database.musicDao().updateArtist(artistSummary, artistContent, artistUID, similarArtistList);
            }
        });
    }

    @Override
    public void updateTrack(MusicApi.TrackInfo track) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                String trackUid = track.getTrackUid();
                String trackSummary = track.getWiki() != null ? track.getWiki().getTrackSummary() : "";
                String trackContent = track.getWiki() != null ? track.getWiki().getTrackContent() : "";
                List<MusicApi.TrackImage> trackImage = track.getTrackAlbum().getTrackImage();

                database.musicDao().updateTrack(trackUid, trackSummary, trackContent, trackImage);
            }
        });
    }

    @Override
    public void updateTrackWithYoutubeId(String youtubeId, String trackUid) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().updateTrackWithYoutubeId(youtubeId, trackUid);
            }
        });
    }

    @Override
    public void deleteTopArtists() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().deleteTopArtists(true);
            }
        });
    }

    @Override
    public void deleteTrack(String trackUid) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().deleteTrack(trackUid);
            }
        });
    }

    @Override
    public void insertTopTracks(List<MusicApi.Track> trackList) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertTopTracks(trackList);
            }
        });
    }

    @Override
    public Observable<MusicApi.Track> getTrack(String trackUid) {
        return database.musicDao().getTrack(trackUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toObservable()
                .map(trackList -> trackList.get(0));
    }

    @Override
    public Observable<List<MusicApi.Album>> getAlbumList(String artistUid) {
        return database.musicDao().getAlbumList(artistUid).toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void insertAlbums(List<MusicApi.Album> albumList) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertAlbums(albumList);
            }
        });
    }

    @Override
    public void updateAlbumWithAlbumUid(List<MusicApi.Track> trackList, String albumUid) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().updateAlbumWithAlbumUid(trackList, albumUid);
            }
        });
    }

    @Override
    public Observable<MusicApi.Album> getAlbumByUid(String albumUid) {
        return database.musicDao().getAlbumByUid(albumUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toObservable()
                .map(albumList -> albumList.get(0));
    }

    @Override
    public void updateTrackWithUid(MusicApi.TrackInfo track, List<MusicApi.Track> trackList, String albumUid) {
        String trackUid = track.getTrackUid();
        String trackUrl = track.getTrackUrl();

        for(MusicApi.Track item : trackList) {
            if(item.getTrackUrl().equals(trackUrl)) {
                item.setTrackUid(trackUid);
            }
        }

        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().updateTrackWithUid(trackList, albumUid);
            }
        });
    }

    @Override
    public void insertTrackInfo(MusicApi.TrackInfo trackInfo) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertTrackInfo(trackInfo);
            }
        });
    }

    @Override
    public Observable<MusicApi.TrackInfo> getTrackInfo(String trackUid) {
        return database.musicDao().getTrackInfo(trackUid)
                .subscribeOn(Schedulers.io())
                .map(trackInfos -> trackInfos.get(0)).toObservable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updateTrackInfoWithYoutubeIdViaTrackUid(String youtubeId, String trackUid) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().updateTrackInfoWithYoutubeIdViaTrackUid(youtubeId, trackUid);
            }
        });
    }

    @Override
    public Observable<MusicApi.AlbumInfo> getAlbumInfo(String albumUid) {
        return database.musicDao().getAlbumInfo(albumUid)
                .subscribeOn(Schedulers.io())
                .map(albumInfos -> albumInfos.get(0)).toObservable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void insertAlbumInfo(MusicApi.AlbumInfo albumInfo) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().insertAlbumInfo(albumInfo);
            }
        });
    }

    @Override
    public Observable<String> getTrackInfoYoutubeId(String trackUid) {
        return database.musicDao().getTrackInfoYoutubeId(trackUid)
                .subscribeOn(Schedulers.io()).toObservable();
    }

    @Override
    public void updateTrackInfoWithSongLyrics(String lyrics, String trackUid) {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().updateTrackInfoWithLyrics(lyrics, trackUid);
            }
        });
    }

    @Override
    public Observable<String> getSongLyrics(String trackUid) {
        return database.musicDao().getTrackInfoSongLyrics(trackUid)
                .subscribeOn(Schedulers.io()).toObservable();
    }

    @Override
    public void clearCache() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                database.musicDao().deleteAllTracks();
                database.musicDao().deleteAllArtists();
                database.musicDao().deleteAllAlbums();
                database.musicDao().deleteAllAlbumInfo();
                database.musicDao().deleteAllTrackInfo();
            }
        });
    }

    @Override
    public void saveDate() {
        int weekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.WEEKOFYEAR, weekOfYear);
        editor.putInt(Constants.YEAR, year);
        editor.apply();
    }

    @Override
    public boolean isSameWeekSinceLastLaunch() {
        int weekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int savedWeek = sharedPreferences.getInt(Constants.WEEKOFYEAR, 0);
        int savedYear = sharedPreferences.getInt(Constants.YEAR, 0);
        return savedYear == 0 || (weekOfYear != savedWeek || year != savedYear);
    }

    @Override
    public void resetDate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.WEEKOFYEAR, 0);
        editor.putInt(Constants.YEAR, 0);
        editor.apply();
    }
}
