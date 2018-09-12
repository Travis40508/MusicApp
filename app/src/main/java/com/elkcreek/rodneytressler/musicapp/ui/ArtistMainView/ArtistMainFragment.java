package com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.utils.ArtistViewPagerAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.BIO_FRAGMENT_TAG;

public class ArtistMainFragment extends Fragment implements ArtistMainView {

    @Inject protected ArtistMainPresenter presenter;
    @BindView(R.id.view_pager_artist_main)
    protected ViewPager viewPager;
    @BindView(R.id.tab_layout_artist_main)
    protected TabLayout tabLayout;
    private ArtistViewPagerAdapter adapter;
    private ArtistMainFragment artistMainFragment;


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.artistRetrieved(getArguments().getString(Constants.ARTIST_UID_KEY));
        presenter.artistNameRetrieved(getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.subscribe();
        presenter.screenRestored();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
        presenter.screenPaused(viewPager.getCurrentItem());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState, viewPager.getCurrentItem());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_main, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.checkSavedInstanceState(savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(ARTIST_MAIN_TAG) == null);
        return view;
    }

    public static ArtistMainFragment newInstance() {

        Bundle args = new Bundle();

        ArtistMainFragment fragment = new ArtistMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showScreens(String artistUid, String artistName) {
        adapter = new ArtistViewPagerAdapter(getChildFragmentManager(), artistUid, artistName);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.viewPagerCreated();
    }

    @Override
    public void reAttachFragment() {
        artistMainFragment = (ArtistMainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ARTIST_MAIN_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, artistMainFragment, ARTIST_MAIN_TAG).commit();
    }

    @Override
    public void setActionBarTitle(String artistName) {
        getActivity().setTitle(artistName);
    }

    @Override
    public void setViewPagerItem(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }

    @Override
    public void setViewPagerState(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }
}
