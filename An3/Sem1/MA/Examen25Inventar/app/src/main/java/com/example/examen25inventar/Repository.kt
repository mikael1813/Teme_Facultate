package com.example.project_kotlin_ma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.examen25inventar.Produs
import com.google.gson.Gson
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.Serializable

interface Repository : Serializable {

    fun add(pinguin: Produs){

    }

    fun update(id: Int, pinguin: Produs){

    }

    fun delete(id: Int){

    }

    fun getAll(): ArrayList<Produs>? {
        return null;
    }
}