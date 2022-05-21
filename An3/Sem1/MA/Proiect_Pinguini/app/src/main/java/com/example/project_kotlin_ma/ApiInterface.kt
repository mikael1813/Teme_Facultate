package com.example.project_kotlin_ma

import android.icu.number.IntegerWidth
import retrofit2.Call
import retrofit2.http.*

//import retrofit2.http.*

interface ApiInterface {

    @GET("penguins")
    fun getData(): Call<List<PinguinDataItem>>

    @Headers("Content-Type: application/json")
    @POST("penguins")
    fun addPengunin(@Body pinguin: Pinguin): Call<Pinguin>

    @Headers("Content-Type: application/json")
    @PUT("penguins/{id}")
    fun updatePengunin(@Path("id") id: Int, @Body pinguin: Pinguin): Call<Pinguin>

    @DELETE("penguins/{id}")
    fun deletePenguin(@Path("id") id: Int): Call<Pinguin>
}