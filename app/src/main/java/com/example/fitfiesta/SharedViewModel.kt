package com.example.fitfiesta

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var totalSteps = MutableLiveData<Int>(0)

    //To add completed workout to the progress list
    val itemList = MutableLiveData<List<ProgressItemModel>>(emptyList())


    fun addItem(item: ProgressItemModel) {
        Log.d("SharedViewModel", "Adding item: $item")
        itemList.value = itemList.value.orEmpty().toMutableList().apply { add(item) }

//
//        // Get the current list from itemList's value
//        val currentList = itemList.value?.toMutableList() ?: mutableListOf()
//
//        // Add the new item to the mutable list
//        currentList.add(item)
//
//        // Update the itemList with the new mutable list
//        itemList.value = currentList.toList()
    }
}