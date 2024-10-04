package com.albsig.sensorikprojekt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SensorsViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedText = new MutableLiveData<>();

    public void setText(Integer text) {
        selectedText.setValue(text);
    }

    public LiveData<Integer> getText() {
        return selectedText;
    }

}