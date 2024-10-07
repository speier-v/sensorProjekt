package com.albsig.sensorikprojekt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SensorsViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedText = new MutableLiveData<>();
    private final MutableLiveData<List<String>> textData = new MutableLiveData<>();
    public static List<String> sensorValuesList = new ArrayList<>();

    public void setText(Integer text) {
        selectedText.setValue(text);
    }

    public void setTextData() {
        textData.postValue(sensorValuesList);
    }

    public LiveData<Integer> getText() {
        return selectedText;
    }

    public LiveData<List<String>> getTextData() {
        return textData;
    }
}