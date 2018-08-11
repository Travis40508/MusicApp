package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.SearchView.SearchFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject protected MainPresenter presenter;
    private static final String SEARCH_FRAGMENT_TAG = "search_fragment_tag";
    public static final String BIO_FRAGMENT_TAG = "bio_fragment_tag";
    public static final String TRACKS_FRAGMENT_TAG = "tracks_fragment_tag";
    public static final String TRACKS_FRAGMENT_FROM_BIO_TAG = "tracks_fragment_from_bio_tag";
    public static final String BIO_FRAGMENT_FROM_BIO_TAG = "bio_fragment_from_bio_tag";
    public static final String PLAY_TRACK_FRAGMENT_TAG = "play_track_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter.attachView(this);
    }

    @Override
    public void attachSearchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, SearchFragment.newInstance(), SEARCH_FRAGMENT_TAG).commit();
    }

    @Override
    public void detachImmediateFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed(getSupportFragmentManager().getBackStackEntryCount());
    }

}
