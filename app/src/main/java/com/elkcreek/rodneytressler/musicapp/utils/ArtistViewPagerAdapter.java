package com.elkcreek.rodneytressler.musicapp.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkcreek.rodneytressler.musicapp.ui.AlbumsView.AlbumsFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AllTracksView.AllTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistBioView.ArtistBioFragment;

public class ArtistViewPagerAdapter extends FragmentPagerAdapter {

    private final String artistUid;
    private final String artistName;

    public ArtistViewPagerAdapter(FragmentManager fm, String artistUid, String artistName) {
        super(fm);
        this.artistUid = artistUid;
        this.artistName = artistName;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARTIST_NAME_KEY, artistName);
            bundle.putString(Constants.ARTIST_UID_KEY, artistUid);
            ArtistBioFragment artistBioFragment = ArtistBioFragment.newInstance();
            artistBioFragment.setArguments(bundle);
            return artistBioFragment;
        } else if (position == 1){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARTIST_NAME_KEY, artistName);
            bundle.putString(Constants.ARTIST_UID_KEY, artistUid);
            AlbumsFragment albumsFragment = AlbumsFragment.newInstance();
            albumsFragment.setArguments(bundle);
            return albumsFragment;
        } else if (position == 2){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARTIST_NAME_KEY, artistName);
            bundle.putString(Constants.ARTIST_UID_KEY, artistUid);
            AllTracksFragment allTracksFragment = AllTracksFragment.newInstance();
            allTracksFragment.setArguments(bundle);
            return allTracksFragment;
        } else {
            //return Similar Artists Fragment
//            return new NatureFragment();
            return null;
        }
    }

    @Override
    public int getCount() {
        //this will be 4
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "Bio";
            case 1 :
                return "Albums";
            case 2 :
                return "All Tracks";
                default :
                    return null;
        }
    }
}
