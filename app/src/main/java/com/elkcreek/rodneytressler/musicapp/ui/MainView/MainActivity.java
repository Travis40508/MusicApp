package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elkcreek.rodneytressler.musicapp.R;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
