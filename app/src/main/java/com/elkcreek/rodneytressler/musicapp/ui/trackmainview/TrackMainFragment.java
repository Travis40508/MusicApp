package com.elkcreek.rodneytressler.musicapp.ui.trackmainview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentTrackMainBinding;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import com.elkcreek.rodneytressler.musicapp.utils.TrackViewPagerAdapter;
import dagger.android.support.AndroidSupportInjection;

public class TrackMainFragment extends Fragment {

    private FragmentTrackMainBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_main, container, false);
        return binding.getRoot();
    }

    public static TrackMainFragment newInstance() {

        Bundle args = new Bundle();

        TrackMainFragment fragment = new TrackMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String artistName = getArguments().getString(Constants.ARTIST_NAME_KEY);
        String trackName = getArguments().getString(Constants.TRACK_NAME_KEY);

        TrackViewPagerAdapter adapter = new TrackViewPagerAdapter(getChildFragmentManager(),
                getArguments().getString(Constants.TRACK_UID_KEY), trackName, artistName);
        binding.setAdapter(adapter);

        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setActionBarTitle(artistName + " - " + trackName);
    }
}
