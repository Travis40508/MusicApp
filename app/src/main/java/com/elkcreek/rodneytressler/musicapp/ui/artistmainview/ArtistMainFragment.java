package com.elkcreek.rodneytressler.musicapp.ui.artistmainview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentArtistMainBinding;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.ArtistViewPagerAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import dagger.android.support.AndroidSupportInjection;

public class ArtistMainFragment extends Fragment {

    private FragmentArtistMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_main, container, false);
        return binding.getRoot();
    }

    public static ArtistMainFragment newInstance() {

        Bundle args = new Bundle();

        ArtistMainFragment fragment = new ArtistMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArtistViewPagerAdapter adapter = new ArtistViewPagerAdapter(getChildFragmentManager(), getArguments().getString(Constants.ARTIST_UID_KEY)
                ,getArguments().getString(Constants.ARTIST_NAME_KEY));

        binding.setRecyclerViewAdapter(adapter);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setActionBarTitle(getArguments().getString(Constants.ARTIST_NAME_KEY));
    }
}
