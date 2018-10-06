package com.elkcreek.rodneytressler.musicapp.ui.albummainview;

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
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentAlbumMainBinding;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.AlbumViewPagerAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import dagger.android.support.AndroidSupportInjection;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_IMAGE_URL_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_NAME_KEY;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.ALBUM_UID_KEY;

public class AlbumMainFragment extends Fragment {

    private FragmentAlbumMainBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlbumViewPagerAdapter adapter = new AlbumViewPagerAdapter(getChildFragmentManager(),
                getArguments().getString(Constants.ARTIST_NAME_KEY),
                getArguments().getString(Constants.ARTIST_UID_KEY),
                getArguments().getString(ALBUM_NAME_KEY),
                getArguments().getString(ALBUM_UID_KEY),
                getArguments().getString(ALBUM_IMAGE_URL_KEY));

        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setActionBarTitle(getArguments().getString(ALBUM_NAME_KEY));

        binding.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_main, container, false);
        return binding.getRoot();
    }

    public static AlbumMainFragment newInstance() {

        Bundle args = new Bundle();

        AlbumMainFragment fragment = new AlbumMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
