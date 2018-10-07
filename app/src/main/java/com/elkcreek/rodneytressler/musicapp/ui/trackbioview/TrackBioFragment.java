package com.elkcreek.rodneytressler.musicapp.ui.trackbioview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentTrackBioBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.RecyclerViewAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class TrackBioFragment extends BaseFragment {

    @Inject protected TrackBioFactory factory;
    private TrackBioViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentTrackBioBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_bio, container, false);
        return binding.getRoot();
    }

    public static TrackBioFragment newInstance() {

        Bundle args = new Bundle();

        TrackBioFragment fragment = new TrackBioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>(), mainViewModel);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        viewModel.setMainViewModel(mainViewModel);

        binding.setViewModel(viewModel);
        binding.setRecyclerViewAdapter(recyclerViewAdapter);
        binding.setLayoutManager(layoutManager);

        viewModel.fetchTrackBio(getArguments().getString(Constants.TRACK_UID_KEY), getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));
        viewModel.fetchSimilarTracks(getArguments().getString(Constants.TRACK_UID_KEY), getArguments().getString(Constants.TRACK_NAME_KEY), getArguments().getString(Constants.ARTIST_NAME_KEY));
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private TrackBioViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(TrackBioViewModel.class);
    }
}
