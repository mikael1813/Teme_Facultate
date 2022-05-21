package com.example.project_kotlin_ma

import com.example.examen.Produs
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