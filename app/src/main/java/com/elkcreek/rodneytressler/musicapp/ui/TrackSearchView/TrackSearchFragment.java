package com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.BaseFragment.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class TrackSearchFragment extends BaseFragment implements TrackSearchView{

    @Inject protected TrackSearchPresenter presenter;

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
}
