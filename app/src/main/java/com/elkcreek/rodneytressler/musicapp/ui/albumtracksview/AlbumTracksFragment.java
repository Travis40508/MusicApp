package com.elkcreek.rodneytressler.musicapp.ui.albumtracksview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.FragmentAlbumTracksBinding;
import com.elkcreek.rodneytressler.musicapp.ui.basefragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.elkcreek.rodneytressler.musicapp.utils.AlbumTracksAdapter;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;
import javax.inject.Inject;
import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;

public class AlbumTracksFragment extends BaseFragment {

    @Inject protected AlbumTracksFactory factory;
    @BindView(R.id.recycler_view_album_tracks)
    protected RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.no_tracks_text)
    protected TextView noTracksText;
    private AlbumTracksFragment albumTracksFragment;
    private AlbumTracksAdapter adapter;

    private AlbumTracksViewModel viewModel;
    private MainViewModel mainViewModel;
    private FragmentAlbumTracksBinding binding;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_tracks, container, false);
        return binding.getRoot();
    }

    public static AlbumTracksFragment newInstance() {

        Bundle args = new Bundle();

        AlbumTracksFragment fragment = new AlbumTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        mainViewModel = getMainViewModel();

        viewModel.setMainViewModel(mainViewModel);
        binding.setViewModel(viewModel);
        binding.setMainViewModel(mainViewModel);
        viewModel.fetchAlbumTracks(getArguments().getString(Constants.ALBUM_UID_KEY));
    }

    private MainViewModel getMainViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private AlbumTracksViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AlbumTracksViewModel.class);
    }
}
