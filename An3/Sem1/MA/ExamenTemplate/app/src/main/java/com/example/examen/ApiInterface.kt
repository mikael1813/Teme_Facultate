package com.example.project_kotlin_ma

import com.example.examen.Produs
import retrofit2.Call
import retrofit2.http.*

//import retrofit2.http.*

interface ApiInterface {

    @GET("toate")
    fun getData(): Call<List<Produs>>

    @Headers("Content-Type: application/json")
    @POST("inregistrare")
    fun addPengunin(@Body produs: Produs): Call<Int>

    @Headers("Content-Type: application/json")
    @PUT("penguins/{id}")
    fun updatePengunin(@Path("id") id: Int, @Body pinguin: Produs): Call<Produs>

    @DELETE("penguins/{id}")
    fun deletePenguin(@Path("id") id: Int): Call<Produs>
}