package com.elkcreek.rodneytressler.musicapp.ui.searchmainview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentSearchMainBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SearchViewPagerAdapter;
import dagger.android.support.AndroidSupportInjection;

public class SearchMainFragment extends BaseFragment {

    private FragmentSearchMainBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_main, container, false);
        return binding.getRoot();
    }

    public static SearchMainFragment newInstance() {

        Bundle args = new Bundle();

        SearchMainFragment fragment = new SearchMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchViewPagerAdapter adapter = new SearchViewPagerAdapter(getChildFragmentManager());
        binding.setRecyclerViewAdapter(adapter);
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setActionBarTitle(Constants.DEFAULT_ACTION_BAR_TITLE);
    }
}
