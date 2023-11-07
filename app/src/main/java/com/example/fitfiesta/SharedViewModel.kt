package com.example.fitfiesta

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var totalSteps = MutableLiveData<Int>(0)
}