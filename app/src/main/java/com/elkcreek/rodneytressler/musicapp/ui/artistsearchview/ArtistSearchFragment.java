package com.elkcreek.rodneytressler.musicapp.ui.artistsearchview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentArtistSearchBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.RecyclerViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class ArtistSearchFragment extends BaseFragment {

    @Inject ArtistSearchFactory factory;
    private ArtistSearchViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentArtistSearchBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_search, container, false);
        return binding.getRoot();
    }

    public static ArtistSearchFragment newInstance() {

        Bundle args = new Bundle();

        ArtistSearchFragment fragment = new ArtistSearchFragment();
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
        viewModel.setMainViewModel(mainViewModel);
    }

    private ArtistSearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ArtistSearchViewModel.class);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
