package com.elkcreek.rodneytressler.musicapp.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkcreek.rodneytressler.musicapp.ui.artistsearchview.ArtistSearchFragment;
import com.elkcreek.rodneytressler.musicapp.ui.tracksearchview.TrackSearchFragment;

public class SearchViewPagerAdapter extends FragmentPagerAdapter {

    public SearchViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            ArtistSearchFragment artistSearchFragment = ArtistSearchFragment.newInstance();
            return artistSearchFragment;
        } else if (position == 1){
            TrackSearchFragment trackSearchFragment = TrackSearchFragment.newInstance();
            return trackSearchFragment;
        } else {
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
                return "Artists";
            case 1 :
                return "Tracks";
            default :
                return null;
        }
    }
}
