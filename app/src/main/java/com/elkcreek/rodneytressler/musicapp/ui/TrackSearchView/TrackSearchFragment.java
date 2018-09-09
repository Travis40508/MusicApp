package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;
import com.elkcreek.rodneytressler.musicapp.utils.TracksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class TrackSearchFragment extends BaseFragment implements TrackSearchView{

    @Inject protected TrackSearchPresenter presenter;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    protected FrameLayout loadingLayout;
    private TracksAdapter adapter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_search, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);

        return view;
    }

    public static TrackSearchFragment newInstance() {

        Bundle args = new Bundle();

        TrackSearchFragment fragment = new TrackSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showTracks(List<MusicApi.Track> trackList) {
        adapter = new TracksAdapter(Glide.with(this), trackList);

        adapter.setPlayCallback(new TracksAdapter.TracksCallback() {
            @Override
            public void onPlayClicked(MusicApi.Track track) {
                presenter.trackClicked(track);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }
}
