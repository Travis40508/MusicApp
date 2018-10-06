package com.elkcreek.rodneytressler.musicapp.ui.mainview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.elkcreek.rodneytressler.musicapp.utils.Constants;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> actionBarTitle = new MutableLiveData<String>();
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(false);
    public MutableLiveData<String> errorToastMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldPopFragment = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldCloseApp = new MutableLiveData<>();

    public void setActionBarTitle(String title) {
        actionBarTitle.postValue(title);
    }

    public LiveData<String> getErrorToastMessage() {
        return errorToastMessage;
    }

    public LiveData<Boolean> getShouldPopFragment() {
        return shouldPopFragment;
    }

    public LiveData<Boolean> getShouldCloseApp() {
        return shouldCloseApp;
    }

    public LiveData<String> getActionBarTitle() {
        return actionBarTitle;
    }
}
