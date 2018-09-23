package com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView;

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
import com.elkcreek.rodneytressler.musicapp.utils.AlbumViewPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ARTIST_UID_KEY;

public class AlbumMainFragment extends Fragment implements AlbumMainView {

    @Inject AlbumMainPresenter presenter;
    @BindView(R.id.view_pager_album_main)
    protected ViewPager viewPager;
    @BindView(R.id.tab_layout_album_main)
    protected TabLayout tabLayout;
    private AlbumViewPagerAdapter adapter;
    private AlbumMainFragment albumMainFragment;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.artistRetrieved(getArguments().getString(ARTIST_NAME_KEY), getArguments().getString(ARTIST_UID_KEY));
        presenter.albumRetrieved(getArguments().getString(ALBUM_NAME_KEY), getArguments().getString(ALBUM_UID_KEY), getArguments().getString(ALBUM_IMAGE_URL_KEY));
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
        presenter.saveState(outState,  viewPager != null ?viewPager.getCurrentItem() : 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_main, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.checkSavedInstanceState(savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_MAIN_TAG) == null);
        return view;
    }

    public static AlbumMainFragment newInstance() {

        Bundle args = new Bundle();

        AlbumMainFragment fragment = new AlbumMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachFragment() {
        albumMainFragment = (AlbumMainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ALBUM_MAIN_TAG);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, albumMainFragment, ALBUM_MAIN_TAG).commit();
    }

    @Override
    public void showScreens(String artistName, String artistUid, String albumName, String albumUid, String albumImage) {
        adapter = new AlbumViewPagerAdapter(getChildFragmentManager(), artistName, artistUid, albumName, albumUid, albumImage);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.viewPagerCreated();
    }

    @Override
    public void setActionBarTitle(String title) {
        getActivity().setTitle(title);
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
