package com.example.project_kotlin_ma

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable


class ServerRepository : Serializable {

    var retrofitBuilder: ApiInterface

    val BASE_URL = "http://10.0.2.2:8080/"
    //val BASE_URL = "https://jsonplaceholder.typicode.com/"

    constructor() {
        retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun add(pinguin: Pinguin, onResult: (Pinguin?) -> Unit) {
        val retrofitData = retrofitBuilder.addPengunin(pinguin)

        retrofitData.enqueue(object : Callback<Pinguin?> {
            override fun onResponse(call: Call<Pinguin?>, response: Response<Pinguin?>) {
                val addedPenguin = response.body()
                onResult(addedPenguin)
            }

            override fun onFailure(call: Call<Pinguin?>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun update(id: Int, pinguin: Pinguin, onResult: (Pinguin?) -> Unit) {
        val retrofitData = retrofitBuilder.updatePengunin(id, pinguin)

        retrofitData.enqueue(object : Callback<Pinguin?> {
            override fun onResponse(call: Call<Pinguin?>, response: Response<Pinguin?>) {
                val updatedPenguin = response.body()
                onResult(updatedPenguin)
            }

            override fun onFailure(call: Call<Pinguin?>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun delete(id: Int, onResult: (Pinguin?) -> Unit) {
        val retrofitData = retrofitBuilder.deletePenguin(id)

        retrofitData.enqueue(object : Callback<Pinguin?> {
            override fun onResponse(call: Call<Pinguin?>, response: Response<Pinguin?>) {
                val deletedPenguin = response.body()
                onResult(deletedPenguin)
            }

            override fun onFailure(call: Call<Pinguin?>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getAll(onResult: (ArrayList<Pinguin>?) -> Unit) {


        val retrofitData = retrofitBuilder.getData()
        //var ok = retrofitData.execute().isSuccessful


        //txt.text = ""

        retrofitData.enqueue(object : Callback<List<PinguinDataItem>?> {
            override fun onResponse(
                call: Call<List<PinguinDataItem>?>?,
                response: Response<List<PinguinDataItem>?>?
            ) {
                val responseBody = response!!.body()!!
                var list: ArrayList<Pinguin> = ArrayList()
                //txt.text = "AAAAAAAAAAAAAA"

                for (data in responseBody) {
                    Stare.valueOf(data.stare)
                    var p = Pinguin(
                        data.nume,
                        data.inaltime,
                        data.greutate,
                        Stare.valueOf(data.stare),
                        data.pret,
                        data.specie,
                        data.dataNasterii
                    )
                    p.id = data.id
                    list.add(p)
                    //txt.text = txt.text.toString() + data.nume
                }

                onResult(list)
            }

            override fun onFailure(call: Call<List<PinguinDataItem>?>?, t: Throwable?) {

                onResult(null)
                //txt.text = t!!.stackTraceToString()
            }
        })

        //return list
    }

}

//class OnGetPenguinsListener {
//    var list : List<Pinguin> = ArrayList<Pinguin>()
//    var error = ""
//
//    fun onGetPenguinsSuccess(pinguinList: List<Pinguin>) {
//        list = pinguinList
//    }
//
//    fun onGetPenguinsFailure(errorMessage: String) {
//        error = errorMessage
//    }
//}