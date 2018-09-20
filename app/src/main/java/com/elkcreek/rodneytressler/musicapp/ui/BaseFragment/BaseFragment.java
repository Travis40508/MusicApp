package com.elkcreek.rodneytressler.musicapp.ui.BaseFragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.elkcreek.rodneytressler.musicapp.ui.MainView.MainActivity;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

public abstract class BaseFragment extends Fragment {

    protected void showMainLoadingLayout() {
        ((MainActivity)getActivity()).showLoadingLayout();
    }

    protected void hideMainLoadingLayout() {
        ((MainActivity)getActivity()).hideLoadingLayout();
    }
}
