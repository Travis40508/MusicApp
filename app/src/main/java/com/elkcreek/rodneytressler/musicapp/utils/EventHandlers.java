package com.elkcreek.rodneytressler.musicapp.utils;

import android.os.Bundle;
import android.view.View;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.squareup.haha.perflib.Main;

import androidx.navigation.Navigation;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class EventHandlers {

    public void onArtistClicked(View view, String artistName, String artistUid, MainViewModel mainViewModel) {
        mainViewModel.showLoadingLayout.set(true);
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(ARTIST_UID_KEY, artistUid);
        Navigation.findNavController(view).navigate(R.id.artistMainFragment, bundle);
    }

    public void onAlbumClicked(View view, String artistName, String artistUID, String albumName, String albumUid, String imageUrl, MainViewModel mainViewModel) {
        mainViewModel.showLoadingLayout.set(true);
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(ARTIST_UID_KEY, artistUID);
        bundle.putString(ALBUM_NAME_KEY, albumName);
        bundle.putString(ALBUM_UID_KEY, albumUid);
        bundle.putString(ALBUM_IMAGE_URL_KEY, imageUrl);
        Navigation.findNavController(view).navigate(R.id.albumMainFragment, bundle);
    }

    public void onTrackClicked(View view, String trackName, String trackUid, String artistName, MainViewModel mainViewModel) {
        mainViewModel.showLoadingLayout.set(true);
        Bundle bundle = new Bundle();
        bundle.putString(TRACK_NAME_KEY, trackName);
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(TRACK_UID_KEY, trackUid);
        Navigation.findNavController(view).navigate(R.id.trackMainFragment, bundle);
    }
}
