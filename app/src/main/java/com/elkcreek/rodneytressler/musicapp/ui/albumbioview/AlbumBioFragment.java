package com.elkcreek.rodneytressler.musicapp.ui.albumbioview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentAlbumBioBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class AlbumBioFragment extends BaseFragment {

    @Inject protected AlbumBioFactory factory;
    private AlbumBioFragment albumBioFragment;
    private AlbumBioViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentAlbumBioBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_bio, container, false);
        return binding.getRoot();
    }

    public static AlbumBioFragment newInstance() {

        Bundle args = new Bundle();

        AlbumBioFragment fragment = new AlbumBioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        binding.setViewModel(viewModel);
        mainViewModel = getMainViewModel();

        viewModel.setMainViewModel(mainViewModel);
        viewModel.fetchAlbumBio(getArguments().getString(Constants.ALBUM_UID_KEY));
    }

    private AlbumBioViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AlbumBioViewModel.class);
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
