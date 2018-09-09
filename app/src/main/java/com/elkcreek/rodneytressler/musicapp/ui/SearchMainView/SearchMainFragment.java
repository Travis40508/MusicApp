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
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.checkSavedInstanceState(savedInstanceState == null,
                getActivity().getSupportFragmentManager().findFragmentByTag(Constants.SEARCH_MAIN_TAG) == null);
        return view;
    }

    public static SearchMainFragment newInstance() {

        Bundle args = new Bundle();

        SearchMainFragment fragment = new SearchMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reAttachFragment() {
        searchMainFragment = (SearchMainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constants.SEARCH_MAIN_TAG);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, searchMainFragment, Constants.SEARCH_MAIN_TAG).commit();
    }

    @Override
    public void showScreens() {
        adapter = new SearchViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
