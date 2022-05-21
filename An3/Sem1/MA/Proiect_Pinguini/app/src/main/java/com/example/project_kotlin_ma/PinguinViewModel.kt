package com.example.project_kotlin_ma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinguinViewModel: ViewModel() {
    val pinguin = MutableLiveData<ArrayList<Pinguin>>()

//    val pinguin: LiveData<ArrayList<Pinguin>>
//        get() = _pinguin
}