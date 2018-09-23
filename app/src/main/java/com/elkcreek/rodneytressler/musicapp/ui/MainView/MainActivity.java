package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistSearchView.ArtistSearchFragment;
import com.elkcreek.rodneytressler.musicapp.ui.SearchMainView.SearchMainFragment;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.SEARCH_FRAGMENT_TAG;
import static com.elkcreek.rodneytressler.musicapp.utils.Constants.SEARCH_MAIN_TAG;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    protected MainPresenter presenter;
    @BindView(R.id.main_loading_layout)
    protected FrameLayout loadingLayout;
    @BindView(R.id.my_toolbar)
    protected Toolbar toolbar;
    private SearchMainFragment searchMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        presenter.attachView(this);
    }
    public void returnHome() {
        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment)).popBackStack(R.id.searchMainFragment2, false);
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
                returnHome();
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


}
