package com.elkcreek.rodneytressler.musicapp.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkcreek.rodneytressler.musicapp.ui.TrackBioView.TrackBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.YoutubeView.YoutubeFragment;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class TrackViewPagerAdapter extends FragmentPagerAdapter {

    private final String trackUid;
    private final String trackName;
    private final String artistName;

    public TrackViewPagerAdapter(FragmentManager fm, String trackUid, String trackName, String artistName) {
        super(fm);
        this.trackUid = trackUid;
        this.trackName = trackName;
        this.artistName = artistName;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //Track Bio
            Bundle bundle = new Bundle();
            bundle.putString(TRACK_UID_KEY, trackUid);
            bundle.putString(TRACK_NAME_KEY, trackName);
            bundle.putString(ARTIST_NAME_KEY, artistName);
            TrackBioFragment trackBioFragment = TrackBioFragment.newInstance();
            trackBioFragment.setArguments(bundle);
            return trackBioFragment;
        } else if (position == 1){
            //Youtube Screen
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST_NAME_KEY, artistName);
            bundle.putString(TRACK_UID_KEY, trackUid);
            bundle.putString(TRACK_NAME_KEY, trackName);
            YoutubeFragment youtubeFragment = YoutubeFragment.newInstance();
            youtubeFragment.setArguments(bundle);
            return youtubeFragment;
        } else {
            //potentially lyrics screen
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
                return "Video";
            default :
                return null;
        }
    }
}
