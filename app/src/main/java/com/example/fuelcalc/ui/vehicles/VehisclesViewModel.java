package com.example.fuelcalc.ui.vehicles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehisclesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VehisclesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wybierz pojazd");
    }

    public LiveData<String> getText() {
        return mText;
    }
}