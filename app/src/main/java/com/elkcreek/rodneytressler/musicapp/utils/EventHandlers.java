package com.elkcreek.rodneytressler.musicapp.utils;

import android.os.Bundle;
import android.view.View;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import androidx.navigation.Navigation;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class EventHandlers {

    public void onArtistClicked(View view, String artistName, String artistUid, MainViewModel mainViewModel) {
        mainViewModel.showLoadingLayout.set(true);
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_KEY, artistName);
        bundle.putString(ARTIST_UID_KEY, artistUid);
        Navigation.findNavController(view).navigate(R.id.artistMainFragment, bundle);
    }

}
