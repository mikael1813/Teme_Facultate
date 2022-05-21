package com.example.examen

import java.io.Serializable

class Produs : Serializable {
    var id: Int = 0
    var nume: String
    var medie: Int
    var etaj: Int
    var orientare: Boolean
    var camera: String
    var status: Boolean


    constructor(
        nume: String,
        medie: Int,
        etaj: Int,
        orientare: Boolean,
        camera: String,
        status: Boolean
    ) {
        this.nume = nume
        this.medie = medie
        this.etaj = etaj
        this.orientare = orientare
        this.camera = camera
        this.status = status
    }

    override fun toString(): String {
        return "Produs(id=$id, nume='$nume', medie=$medie, etaj=$etaj, orientare=$orientare, camera=$camera, status=$status)"
    }

}