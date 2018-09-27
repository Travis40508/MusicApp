package com.elkcreek.rodneytressler.musicapp.ui.SearchMainView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.SearchViewPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class SearchMainFragment extends BaseFragment implements SearchMainView {

    @Inject
    protected SearchMainPresenter presenter;
    @BindView(R.id.view_pager_search_main)
    protected ViewPager viewPager;
    @BindView(R.id.tab_layout_search_main)
    protected TabLayout tabLayout;
    private SearchViewPagerAdapter adapter;
    private SearchMainFragment searchMainFragment;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static SearchMainFragment newInstance() {

        Bundle args = new Bundle();

        SearchMainFragment fragment = new SearchMainFragment();
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
    public void showScreens() {
        adapter = new SearchViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.viewPagerCreated();
    }

    @Override
    public void setViewPagerPage(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }
}
