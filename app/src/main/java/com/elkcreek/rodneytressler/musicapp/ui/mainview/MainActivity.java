package com.elkcreek.rodneytressler.musicapp.ui.mainview;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ActivityMainBinding;
import com.elkcreek.rodneytressler.musicapp.utils.Constants;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        setSupportActionBar(binding.myToolbar);

        listenForErrorToasts();
        listenForFragmentPop();
        listenForAppClose();
    }

    private void listenForAppClose() {
        viewModel.shouldCloseApp.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    private void listenForFragmentPop() {
        viewModel.shouldPopFragment.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment)).popBackStack();
            }
        });
    }

    private void listenForErrorToasts() {
        viewModel.getErrorToastMessage().observe(this, message -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    public void returnHome() {
        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment)).popBackStack(R.id.searchMainFragment, false);
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
                viewModel.setActionBarTitle(Constants.DEFAULT_ACTION_BAR_TITLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
