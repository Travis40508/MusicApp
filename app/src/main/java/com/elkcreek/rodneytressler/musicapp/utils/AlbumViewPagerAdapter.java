package com.elkcreek.rodneytressler.musicapp.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkcreek.rodneytressler.musicapp.ui.albumbioview.AlbumBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.albumtracksview.AlbumTracksFragment;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class AlbumViewPagerAdapter extends FragmentPagerAdapter {

    private final String artistName;
    private final String artistUid;
    private final String albumName;
    private final String albumUid;
    private final String imageUrl;

    public AlbumViewPagerAdapter(FragmentManager fm, String artistName, String artistUid, String albumName, String albumUid, String imageUrl) {
        super(fm);
        this.artistName = artistName;
        this.artistUid = artistUid;
        this.albumName = albumName;
        this.albumUid = albumUid;
        this.imageUrl = imageUrl;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //Album Bio
            Bundle bundle = new Bundle();
            bundle.putString(ALBUM_UID_KEY, albumUid);
            AlbumBioFragment albumBioFragment = AlbumBioFragment.newInstance();
            albumBioFragment.setArguments(bundle);
            return albumBioFragment;
        } else if (position == 1){
            //Album Tracks
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST_NAME_KEY, artistName);
            bundle.putString(ARTIST_UID_KEY, artistUid);
            bundle.putString(ALBUM_NAME_KEY, albumName);
            bundle.putString(ALBUM_UID_KEY, albumUid);
            bundle.putString(ALBUM_IMAGE_URL_KEY, imageUrl);
            AlbumTracksFragment albumTracksFragment = AlbumTracksFragment.newInstance();
            albumTracksFragment.setArguments(bundle);
            return albumTracksFragment;
        } else {
            //return Similar Artists Fragment
//            return new NatureFragment();
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "Bio";
            case 1 :
                return "Tracks";
            default :
                return null;
        }
    }
}
