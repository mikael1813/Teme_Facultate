package com.example.project_kotlin_ma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examen25inventar.Produs

class ProdusViewModel : ViewModel() {
    val produse = MutableLiveData<ArrayList<Produs>>()

//    val pinguin: LiveData<ArrayList<Pinguin>>
//        get() = _pinguin
}