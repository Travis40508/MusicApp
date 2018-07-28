package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

public interface MainView extends BaseView {
    void attachSearchFragment();

    void detachImmediateFragment();

    void closeApp();
}
