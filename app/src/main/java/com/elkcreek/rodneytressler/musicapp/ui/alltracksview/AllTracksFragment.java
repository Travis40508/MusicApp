package com.elkcreek.rodneytressler.musicapp.ui.alltracksview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentAllTracksBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Adapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class AllTracksFragment extends BaseFragment {

    @Inject AllTracksFactory factory;
    private FragmentAllTracksBinding binding;
    private AllTracksViewModel viewModel;
    private MainViewModel mainViewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_tracks, container, false);
        return binding.getRoot();
    }

    public static AllTracksFragment newInstance() {

        Bundle args = new Bundle();

        AllTracksFragment fragment = new AllTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        Adapter adapter = new Adapter(new ArrayList<>(), mainViewModel);
        binding.setViewModel(viewModel);
        binding.setAdapter(adapter);

        viewModel.fetchTopTracks(getArguments().getString(Constants.ARTIST_UID_KEY));
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private AllTracksViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AllTracksViewModel.class);
    }
}
