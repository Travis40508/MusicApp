package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.SearchView.SearchFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.YoutubeView.YoutubeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.SEARCH_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    protected MainPresenter presenter;
    @BindView(R.id.main_loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.my_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        presenter.attachView(this);
    }

    @Override
    public void attachSearchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, SearchFragment.newInstance(), SEARCH_FRAGMENT_TAG).commit();
    }

    @Override
    public void detachImmediateFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public void returnHome() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void enterPipMode() {
        enterPictureInPictureMode();
    }

    @Override
    public void hideMainLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed(getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                presenter.homeClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public void hideLoadingLayout() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
//        presenter.systemHomeButtonPressed(Build.VERSION.SDK_INT);
    }
}
