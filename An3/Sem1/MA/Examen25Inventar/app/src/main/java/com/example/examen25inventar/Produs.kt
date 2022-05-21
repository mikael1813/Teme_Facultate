package com.example.examen25inventar

import java.io.Serializable

class Produs : Serializable {
    var id: Int = 0
    var nume: String
    var tip: String
    var cantitate: Int
    var pret: Int
    var discount: Int
    var status: Boolean


    constructor(
        nume: String,
        tip: String,
        cantitate: Int,
        pret: Int,
        discount: Int,
        status: Boolean
    ) {
        this.nume = nume
        this.tip = tip
        this.cantitate = cantitate
        this.discount = discount
        this.status = status
        this.pret = pret
    }

    override fun toString(): String {
        return "Produs(id=$id, nume='$nume', tip='$tip', cantitate=$cantitate, discount=$discount, status=$status)"
    }


}