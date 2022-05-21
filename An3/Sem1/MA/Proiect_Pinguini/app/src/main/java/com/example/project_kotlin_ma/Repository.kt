package com.example.project_kotlin_ma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.Serializable

interface Repository : Serializable {

    fun add(pinguin: Pinguin){

    }

    fun update(id: Int, pinguin: Pinguin){

    }

    fun delete(id: Int){

    }

    fun getAll(): ArrayList<Pinguin>? {
        return null;
    }
}