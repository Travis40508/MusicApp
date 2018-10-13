package com.elkcreek.rodneytressler.musicapp.ui.tracksearchview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentTrackSearchBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.RecyclerViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class TrackSearchFragment extends BaseFragment {

    @Inject TrackSearchFactory factory;
    private TrackSearchViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentTrackSearchBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_search, container, false);
        return binding.getRoot();
    }

    public static TrackSearchFragment newInstance() {

        Bundle args = new Bundle();

        TrackSearchFragment fragment = new TrackSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>(), mainViewModel);

        binding.setRecyclerViewAdapter(recyclerViewAdapter);
        binding.setViewModel(viewModel);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private TrackSearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(TrackSearchViewModel.class);
    }
}
