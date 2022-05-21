package com.example.project_kotlin_ma

import java.io.Serializable
import java.time.LocalDate
import java.util.*


class Pinguin : Serializable {
    var id: Int = 0
    var nume: String
    var inaltime: Float
    var greutate: Float
    var stare: Stare
    var pret: Float
    var specie: String
    var dataNasterii: String


    constructor(
        nume: String,
        inaltime: Float,
        greutate: Float,
        stare: Stare,
        pret: Float,
        specie: String,
        dataNasterii: String
    ) {
        this.nume = nume
        this.inaltime = inaltime
        this.greutate = greutate
        this.stare = stare
        this.pret = pret
        this.specie = specie
        this.dataNasterii = dataNasterii
    }

    override fun toString(): String {
        return "Pinguin(id=$id, nume='$nume', inaltime=$inaltime, greutate=$greutate, stare=$stare, pret=$pret, specie='$specie', dataNasterii=$dataNasterii)"
    }


}