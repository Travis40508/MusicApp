package com.elkcreek.rodneytressler.musicapp.ui.mainview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.elkcreek.rodneytressler.musicapp.utils.Constants;

public class MainViewModel extends ViewModel {
    public ObservableField<String> actionBarTitle = new ObservableField<>(Constants.DEFAULT_ACTION_BAR_TITLE);
    public ObservableBoolean showLoadingLayout = new ObservableBoolean(false);
    public MutableLiveData<String> errorToastMessage = new MutableLiveData<>();

    public void setActionBarTitle(String title) {
        actionBarTitle.set(title);
    }

    public LiveData<String> getErrorToastMessage() {
        return errorToastMessage;
    }
}
