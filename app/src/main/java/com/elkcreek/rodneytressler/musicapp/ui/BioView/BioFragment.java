package com.elkcreek.rodneytressler.musicapp.ui.BioView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class BioFragment extends Fragment implements BioView {

    @Inject protected BioPresenter presenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bio, container, false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        return view;
    }

    public static BioFragment newInstance() {

        Bundle args = new Bundle();

        BioFragment fragment = new BioFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
