package com.elkcreek.rodneytressler.musicapp.ui.TrackMainView;

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
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.TrackViewPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_MAIN_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.TRACK_UID_KEY;

public class TrackMainFragment extends Fragment implements TrackMainView {

    @Inject protected TrackMainPresenter presenter;
    @BindView(R.id.view_pager_track_main)
    protected ViewPager viewPager;
    @BindView(R.id.tab_layout_track_main)
    protected TabLayout tabLayout;
    private TrackMainFragment trackMainFragment;
    private TrackViewPagerAdapter adapter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.trackRetrieved(getArguments().getString(TRACK_UID_KEY));
        presenter.searchRetrieved(getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));
        presenter.subscribe();
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
        presenter.storeViewPagerState(viewPager.getCurrentItem());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_main, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static TrackMainFragment newInstance() {

        Bundle args = new Bundle();

        TrackMainFragment fragment = new TrackMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState, viewPager != null ? viewPager.getCurrentItem() : 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getState(savedInstanceState);
    }

    @Override
    public void setActionBarTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showScreens(String trackUid, String trackName, String artistName) {
        adapter = new TrackViewPagerAdapter(getChildFragmentManager(), trackUid, trackName, artistName);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.viewPagerCreated();
    }

    @Override
    public void setViewPagerState(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }

}
