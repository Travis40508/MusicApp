package com.elkcreek.rodneytressler.musicapp.ui.MainView;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.ui.SearchView.SearchFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.elkcreek.rodneytressler.musicapp.utils.Constants.SEARCH_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    protected MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
}
