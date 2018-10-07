package com.elkcreek.rodneytressler.musicapp.ui.albumsview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentAlbumsBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.RecyclerViewAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class AlbumsFragment extends BaseFragment  {

    @Inject AlbumsFactory factory;
    private AlbumsViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentAlbumsBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums, container, false);
        return binding.getRoot();
    }

    public static AlbumsFragment newInstance() {

        Bundle args = new Bundle();

        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>(), mainViewModel);

        binding.setViewModel(viewModel);
        binding.setRecyclerViewAdapter(recyclerViewAdapter);

        viewModel.fetchItems(getArguments().getString(Constants.ARTIST_UID_KEY));
    }

    private AlbumsViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AlbumsViewModel.class);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
