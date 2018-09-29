package com.elkcreek.rodneytressler.musicapp.ui.basefragment;

import android.support.v4.app.Fragment;

import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainActivity;

public abstract class BaseFragment extends Fragment {

    protected void showMainLoadingLayout() {
//        ((MainActivity)getActivity()).showLoadingLayout();
    }

    protected void hideMainLoadingLayout() {
//        ((MainActivity)getActivity()).hideLoadingLayout();
    }
}
