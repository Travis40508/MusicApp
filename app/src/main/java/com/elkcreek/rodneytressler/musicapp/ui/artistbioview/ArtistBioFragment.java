package com.elkcreek.rodneytressler.musicapp.ui.artistbioview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentArtistBioBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.SimilarArtistAdapter;
import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class ArtistBioFragment extends BaseFragment {

    @Inject
    protected ArtistBioFactory factory;
    protected NestedScrollView scrollView;
    private SimilarArtistAdapter adapter;
    private ArtistBioViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentArtistBioBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_bio, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(ArtistBioViewModel.class);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.setMainViewModel(mainViewModel);
        binding.setViewModel(viewModel);
        binding.setMainViewModel(mainViewModel);


        viewModel.fetchArtistBio(
                getArguments().getString(Constants.ARTIST_UID_KEY),
                getArguments().getString(Constants.ARTIST_NAME_KEY));
    }

    public static ArtistBioFragment newInstance() {

        Bundle args = new Bundle();

        ArtistBioFragment fragment = new ArtistBioFragment();
        fragment.setArguments(args);
        return fragment;
    }
}